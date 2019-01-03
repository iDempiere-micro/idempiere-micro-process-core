package org.compiere.process;

import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.orm.MRole;
import org.compiere.orm.Query;
import org.idempiere.common.util.CCache;
import org.idempiere.orm.PO;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.getSQLValueEx;

/**
 * Process Model
 *
 * @author Jorg Janke
 * @version $Id: MProcess.java,v 1.4 2006/07/30 00:58:04 jjanke Exp $
 * @author Teo Sarca, www.arhipac.ro
 *     <li>BF [ 1757523 ] Server Processes are using Server's context
 *     <li>FR [ 2214883 ] Remove SQL code and Replace for Query
 */
public class MProcess extends X_AD_Process {
  /** */
  private static final long serialVersionUID = 6665942554198058466L;
  private static final String SCRIPT_PREFIX = "@script:";

  /**
   * Get MProcess from Cache
   *
   * @param ctx context
   * @param AD_Process_ID id
   * @return MProcess
   */
  public static MProcess get(Properties ctx, int AD_Process_ID) {
    Integer key = new Integer(AD_Process_ID);
    MProcess retValue = (MProcess) s_cache.get(key);
    if (retValue != null) return retValue;
    retValue = new MProcess(ctx, AD_Process_ID, null);
    if (retValue.getId() != 0) s_cache.put(key, retValue);
    return retValue;
  } //	get

  /**
   * Get MProcess from Menu
   *
   * @param ctx context
   * @param AD_Menu_ID id
   * @return MProcess or null
   */
  public static MProcess getFromMenu(Properties ctx, int AD_Menu_ID) {
    final String whereClause =
        "EXISTS (SELECT 1 FROM AD_Menu m"
            + " WHERE m.AD_Process_ID=AD_Process.AD_Process_ID AND m.AD_Menu_ID=?)";
    MProcess p =
        new Query(ctx, I_AD_Process.Table_Name, whereClause, null)
            .setParameters(AD_Menu_ID)
            .firstOnly();
    if (p != null) {
      s_cache.put(p.getId(), p);
    }
    return p;
  } //	getFromMenu

  /** Cache */
  private static CCache<Integer, MProcess> s_cache =
      new CCache<Integer, MProcess>(I_AD_Process.Table_Name, 20);

  /**
   * ************************************************************************ Standard Constructor
   *
   * @param ctx context
   * @param AD_Process_ID process
   * @param trxName transaction name
   */
  public MProcess(Properties ctx, int AD_Process_ID, String trxName) {
    super(ctx, AD_Process_ID, trxName);
    if (AD_Process_ID == 0) {
      setIsReport(false);
      setIsServerProcess(false);
      setProcessAccessLevel(X_AD_Process.ACCESSLEVEL_All);
      setEntityType(PO.ENTITYTYPE_UserMaintained);
      setIsBetaFunctionality(false);
    }
  } //	MProcess

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction name
   */
  public MProcess(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MProcess

  /** Parameters */
  private MProcessPara[] m_parameters = null;

  /**
   * Get Parameters
   *
   * @return parameters
   */
  public MProcessPara[] getParameters() {
    if (m_parameters != null) return m_parameters;
    //
    final String whereClause = MProcessPara.COLUMNNAME_AD_Process_ID + "=?";
    List<MProcessPara> list =
        new Query(getCtx(), I_AD_Process_Para.Table_Name, whereClause, null)
            .setParameters(getId())
            .setOrderBy(MProcessPara.COLUMNNAME_SeqNo)
            .list();
    //
    m_parameters = new MProcessPara[list.size()];
    list.toArray(m_parameters);
    return m_parameters;
  } //	getParameters

  /**
   * Get Parameter with ColumnName
   *
   * @param name column name
   * @return parameter or null
   */
  public MProcessPara getParameter(String name) {
    getParameters();
    for (int i = 0; i < m_parameters.length; i++) {
      if (m_parameters[i].getColumnName().equals(name)) return m_parameters[i];
    }
    return null;
  } //	getParameter

  /**
   * String Representation
   *
   * @return info
   */
  public String toString() {
    StringBuffer sb =
        new StringBuffer("MProcess[").append(getId()).append("-").append(getName()).append("]");
    return sb.toString();
  } //	toString

  /**
   * ************************************************************************ Process w/o parameter
   *
   * @param Record_ID record
   * @param trx transaction
   * @return Process Instance
   */
  public MPInstance processIt(int Record_ID) {
    return processIt(Record_ID, true);
  }

  /**
   * ************************************************************************ Process w/o parameter
   *
   * @param Record_ID record
   * @param trx transaction
   * @return Process Instance
   */
  public MPInstance processIt(int Record_ID, boolean managedTrx) {
    MPInstance pInstance = new MPInstance(this, Record_ID);
    //	Lock
    pInstance.setIsProcessing(true);
    pInstance.saveEx();

    boolean ok = true;

    ProcessInfo processInfo = new ProcessInfo("", this.getAD_Process_ID());
    processInfo.setAD_PInstance_ID(pInstance.getAD_PInstance_ID());
    processInfo.setRecord_ID(Record_ID); // @Trifon - pass Record_Id to ProcessInfo class
    ok = processIt(processInfo, managedTrx);

    //	Unlock
    pInstance.setResult(ok ? MPInstance.RESULT_OK : MPInstance.RESULT_ERROR);
    pInstance.setErrorMsg(processInfo.getSummary());
    pInstance.setIsProcessing(false);
    pInstance.saveEx();
    //
    pInstance.log();
    return pInstance;
  } //	process

  /**
   * Process It (sync)
   *
   * @param pi Process Info
   * @param trx transaction
   * @return true if OK
   */
  public boolean processIt(ProcessInfo pi) {
    return processIt(pi, true);
  }

  /**
   * Process It (sync)
   *
   * @param pi Process Info
   * @param trx transaction
   * @return true if OK
   */
  public boolean processIt(ProcessInfo pi, boolean managedTrx) {
    if (pi.getAD_PInstance_ID() == 0) {
      MPInstance pInstance = new MPInstance(this, pi.getRecord_ID());
      //	Lock
      pInstance.setIsProcessing(true);
      pInstance.saveEx();
    }

    boolean ok = false;

    //	Java Class
    String Classname = getClassname();
    if (Classname != null && Classname.length() > 0) {
      pi.setClassName(Classname);
      ok = startClass(pi, managedTrx);
    } else {
      // BF IDEMPIERE-165
      if (this.isReport()) {
        ok = true;
      } else {
        String msg = "No Classname or ProcedureName for " + getName();
        pi.setSummary(msg, ok);
        log.warning(msg);
      }
    }
    return ok;
  } //	process

  /**
   * Is this a Java Process
   *
   * @return true if java process
   */
  public boolean isJavaProcess() {
    String Classname = getClassname();
    return (Classname != null && Classname.length() > 0);
  } //	is JavaProcess

  /**
   * Start Java Class (sync). instanciate the class implementing the interface ProcessCall. The
   * class can be a Server/Client class (when in Package org adempiere.process or
   * org.compiere.model) or a client only class (e.g. in org.compiere.report)
   *
   * @param Classname name of the class to call
   * @param pi process info
   * @param trx transaction
   * @param managedTrx false if trx is managed by caller
   * @return true if success see ProcessCtl.startClass
   */
  private boolean startClass(ProcessInfo pi, boolean managedTrx) {
    if (log.isLoggable(Level.INFO)) log.info(pi.getClassName());

    if (pi.getClassName().toLowerCase().startsWith(SCRIPT_PREFIX)) {
      return ProcessUtil.startScriptProcess(getCtx(), pi);
    } else {
      return ProcessUtil.startJavaProcess(getCtx(), pi, managedTrx);
    }
  } //  startClass

  /**
   * Is it a Workflow
   *
   * @return true if Workflow
   */
  public boolean isWorkflow() {
    return getAD_Workflow_ID() > 0;
  } //	isWorkflow

  /**
   * Update Statistics
   *
   * @param seconds sec
   * @deprecated - use UPDATE instead
   */
  public void addStatistics(int seconds) {
    setStatistic_Count(getStatistic_Count() + 1);
    setStatistic_Seconds(getStatistic_Seconds() + seconds);
  } //	addStatistics

  /**
   * After Save
   *
   * @param newRecord new
   * @param success success
   * @return success
   */
  protected boolean afterSave(boolean newRecord, boolean success) {
    if (!success) return success;
    if (newRecord) //	Add to all automatic roles
    {
      MRole[] roles = MRole.getOf(getCtx(), "IsManual='N'");
      for (int i = 0; i < roles.length; i++) {

        MProcessAccess pa = new MProcessAccess(this, roles[i].getAD_Role_ID());
        pa.saveEx();
      }
    }
    return success;
  } //	afterSave

  /**
   * Grant independence to GenerateModel from AD_Process_ID
   *
   * @param value
   * @param trxName
   * @return
   */
  public static int getProcess_ID(String value, String trxName) {
    int retValue =
        getSQLValueEx(trxName, "SELECT AD_Process_ID FROM AD_Process WHERE Value=?", value);
    return retValue;
  }

  /**
   * Copy settings from another process overwrites existing data (including translations) and saves.
   * Not overwritten: name, value, entitytype
   *
   * @param source
   */
  public void copyFrom(MProcess source) {

    if (log.isLoggable(Level.FINE)) log.log(Level.FINE, "Copying from:" + source + ", to: " + this);
    setProcessAccessLevel(source.getProcessAccessLevel());
    setAD_Form_ID(source.getAD_Form_ID());
    setAD_PrintFormat_ID(source.getAD_PrintFormat_ID());
    setAD_ReportView_ID(source.getAD_ReportView_ID());
    setAD_Workflow_ID(source.getAD_Workflow_ID());
    setClassname(source.getClassname());
    setDescription(source.getDescription());
    setHelp(source.getHelp());
    setIsBetaFunctionality(source.isBetaFunctionality());
    setIsDirectPrint(source.isDirectPrint());
    setIsReport(source.isReport());
    setIsServerProcess(source.isServerProcess());
    setJasperReport(source.getJasperReport());
    setProcedureName(source.getProcedureName());
    setShowHelp(source.getShowHelp());

    saveEx();

    // copy parameters
    // TODO? Perhaps should delete existing first?
    MProcessPara[] parameters = source.getParameters();
    for (MProcessPara sourcePara : parameters) {
      MProcessPara targetPara = new MProcessPara(this);
      targetPara.copyFrom(sourcePara); // saves automatically
    }
  }

  /**
   * Process It without closing the given transaction - used from workflow engine.
   *
   * @param pi Process Info
   * @param trx transaction
   * @return true if OK
   */
  public boolean processItWithoutTrxClose(ProcessInfo pi) {
    return processIt(pi, false);
  } //	processItWithoutTrxClose
} //	MProcess
