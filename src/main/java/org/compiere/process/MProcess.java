package org.compiere.process;

import kotliquery.Row;
import org.compiere.model.Process;
import org.compiere.model.ProcessParameter;
import org.compiere.orm.MRole;
import org.compiere.orm.Query;
import org.idempiere.common.util.CCache;
import org.idempiere.orm.PO;

import java.util.List;
import java.util.logging.Level;

import static org.compiere.orm.MRoleKt.getFilteredRoles;

/**
 * Process Model
 *
 * @author Jorg Janke
 * @author Teo Sarca, www.arhipac.ro
 * <li>BF [ 1757523 ] Server Processes are using Server's context
 * <li>FR [ 2214883 ] Remove SQL code and Replace for Query
 * @version $Id: MProcess.java,v 1.4 2006/07/30 00:58:04 jjanke Exp $
 */
public class MProcess extends X_AD_Process {
    /**
     *
     */
    private static final long serialVersionUID = 6665942554198058466L;
    private static final String SCRIPT_PREFIX = "@script:";
    /**
     * Cache
     */
    private static CCache<Integer, MProcess> s_cache =
            new CCache<>(Process.Table_Name, 20);
    /**
     * Parameters
     */
    private MProcessPara[] m_parameters = null;

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param AD_Process_ID process
     */
    public MProcess(int AD_Process_ID) {
        super(AD_Process_ID);
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
     */
    public MProcess(Row row) {
        super(row);
    } //	MProcess

    /**
     * Get MProcess from Cache
     *
     * @param AD_Process_ID id
     * @return MProcess
     */
    public static MProcess get(int AD_Process_ID) {
        Integer key = AD_Process_ID;
        MProcess retValue = s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MProcess(AD_Process_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get

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
                new Query(ProcessParameter.Table_Name, whereClause)
                        .setParameters(getId())
                        .setOrderBy(MProcessPara.COLUMNNAME_SeqNo)
                        .list();
        //
        m_parameters = new MProcessPara[list.size()];
        list.toArray(m_parameters);
        return m_parameters;
    } //	getParameters

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        return "MProcess[" + getId() + "-" + getName() + "]";
    } //	toString

    /**
     * Process It (sync)
     *
     * @param pi Process Info
     * @return true if OK
     */
    public boolean processIt(ProcessInfo pi, boolean managedTrx) {
        if (pi.getPInstanceId() == 0) {
            MPInstance pInstance = new MPInstance(this, pi.getRecordId());
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
     * Start Java Class (sync). instanciate the class implementing the interface ProcessCall. The
     * class can be a Server/Client class (when in Package org adempiere.process or
     * org.compiere.model) or a client only class (e.g. in org.compiere.report)
     *
     * @param pi         process info
     * @param managedTrx false if trx is managed by caller
     * @return true if success see ProcessCtl.startClass
     */
    private boolean startClass(ProcessInfo pi, boolean managedTrx) {
        if (log.isLoggable(Level.INFO)) log.info(pi.getClassName());

        if (pi.getClassName().toLowerCase().startsWith(SCRIPT_PREFIX)) {
            return ProcessUtil.startScriptProcess(pi);
        } else {
            return ProcessUtil.startJavaProcess(pi, managedTrx);
        }
    } //  startClass

    /**
     * Update Statistics
     *
     * @param seconds sec
     * @deprecated - use UPDATE instead
     */
    public void addStatistics(int seconds) {
        setStatisticCount(getStatisticCount() + 1);
        setStatisticSeconds(getStatisticSeconds() + seconds);
    } //	addStatistics

    /**
     * After Save
     *
     * @param newRecord new
     * @param success   success
     * @return success
     */
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (!success) return success;
        if (newRecord) //	Add to all automatic roles
        {
            List<MRole> roles = getFilteredRoles("IsManual='N'");
            for (MRole role : roles) {
                MProcessAccess pa = new MProcessAccess(this, role.getRoleId());
                pa.saveEx();
            }
        }
        return success;
    } //	afterSave

    /**
     * Copy settings from another process overwrites existing data (including translations) and saves.
     * Not overwritten: name, value, entitytype
     *
     * @param source
     */
    public void copyFrom(MProcess source) {

        if (log.isLoggable(Level.FINE)) log.log(Level.FINE, "Copying from:" + source + ", to: " + this);
        setProcessAccessLevel(source.getProcessAccessLevel());
        setFormId(source.getFormId());
        setPrintFormatId(source.getPrintFormatId());
        setReportViewId(source.getReportViewId());
        setWorkflowId(source.getWorkflowId());
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
     * @return true if OK
     */
    public boolean processItWithoutTrxClose(ProcessInfo pi) {
        return processIt(pi, false);
    } //	processItWithoutTrxClose
} //	MProcess
