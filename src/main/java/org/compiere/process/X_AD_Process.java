package org.compiere.process;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.*;
import org.compiere.orm.BasePONameValue;
import org.idempiere.orm.I_Persistent;

/**
 * Generated Model for AD_Process
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_Process extends BasePONameValue implements I_AD_Process, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_Process(Properties ctx, int AD_Process_ID, String trxName) {
    super(ctx, AD_Process_ID, trxName);
    /**
     * if (AD_Process_ID == 0) { setAccessLevel (null); setAD_Process_ID (0); setEntityType (null);
     * // @SQL=select get_sysconfig('DEFAULT_ENTITYTYPE','U',0,0) from dual setIsBetaFunctionality
     * (false); setIsReport (false); setIsServerProcess (false); setName (null); setValue (null); }
     */
  }

  /** Load Constructor */
  public X_AD_Process(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 4 - System
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_AD_Process[").append(getId()).append("]");
    return sb.toString();
  }

    /** All = 7 */
  public static final String ACCESSLEVEL_All = "7";

    /**
   * Set Data Access Level.
   *
   * @param AccessLevel Access Level required
   */
  public void setProcessAccessLevel(String AccessLevel) {

    set_Value(COLUMNNAME_AccessLevel, AccessLevel);
  }

  /**
   * Get Data Access Level.
   *
   * @return Access Level required
   */
  public String getProcessAccessLevel() {
    return (String) get_Value(COLUMNNAME_AccessLevel);
  }

    /**
   * Get Context Help.
   *
   * @return Context Help
   */
  public int getAD_CtxHelp_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_CtxHelp_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Special Form.
   *
   * @param AD_Form_ID Special Form
   */
  public void setAD_Form_ID(int AD_Form_ID) {
    if (AD_Form_ID < 1) set_Value(COLUMNNAME_AD_Form_ID, null);
    else set_Value(COLUMNNAME_AD_Form_ID, Integer.valueOf(AD_Form_ID));
  }

  /**
   * Get Special Form.
   *
   * @return Special Form
   */
  public int getAD_Form_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Form_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Print Format.
   *
   * @param AD_PrintFormat_ID Data Print Format
   */
  public void setAD_PrintFormat_ID(int AD_PrintFormat_ID) {
    if (AD_PrintFormat_ID < 1) set_Value(COLUMNNAME_AD_PrintFormat_ID, null);
    else set_Value(COLUMNNAME_AD_PrintFormat_ID, Integer.valueOf(AD_PrintFormat_ID));
  }

  /**
   * Get Print Format.
   *
   * @return Data Print Format
   */
  public int getAD_PrintFormat_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_PrintFormat_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Process.
   *
   * @return Process or Report
   */
  public int getAD_Process_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Process_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Report View.
   *
   * @param AD_ReportView_ID View used to generate this report
   */
  public void setAD_ReportView_ID(int AD_ReportView_ID) {
    if (AD_ReportView_ID < 1) set_Value(COLUMNNAME_AD_ReportView_ID, null);
    else set_Value(COLUMNNAME_AD_ReportView_ID, AD_ReportView_ID);
  }

  /**
   * Get Report View.
   *
   * @return View used to generate this report
   */
  public int getAD_ReportView_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_ReportView_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Workflow.
   *
   * @param AD_Workflow_ID Workflow or combination of tasks
   */
  public void setAD_Workflow_ID(int AD_Workflow_ID) {
    if (AD_Workflow_ID < 1) set_Value(COLUMNNAME_AD_Workflow_ID, null);
    else set_Value(COLUMNNAME_AD_Workflow_ID, Integer.valueOf(AD_Workflow_ID));
  }

  /**
   * Get Workflow.
   *
   * @return Workflow or combination of tasks
   */
  public int getAD_Workflow_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Workflow_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Classname.
   *
   * @param Classname Java Classname
   */
  public void setClassname(String Classname) {
    set_Value(COLUMNNAME_Classname, Classname);
  }

  /**
   * Get Classname.
   *
   * @return Java Classname
   */
  public String getClassname() {
    return (String) get_Value(COLUMNNAME_Classname);
  }

    /**
   * Set Description.
   *
   * @param Description Optional short description of the record
   */
  public void setDescription(String Description) {
    set_Value(COLUMNNAME_Description, Description);
  }

  /**
   * Get Description.
   *
   * @return Optional short description of the record
   */
  public String getDescription() {
    return (String) get_Value(COLUMNNAME_Description);
  }

    /**
   * Set Entity Type.
   *
   * @param EntityType Dictionary Entity Type; Determines ownership and synchronization
   */
  public void setEntityType(String EntityType) {

    set_Value(COLUMNNAME_EntityType, EntityType);
  }

  /**
   * Get Entity Type.
   *
   * @return Dictionary Entity Type; Determines ownership and synchronization
   */
  public String getEntityType() {
    return (String) get_Value(COLUMNNAME_EntityType);
  }

  /**
   * Set Comment/Help.
   *
   * @param Help Comment or Hint
   */
  public void setHelp(String Help) {
    set_Value(COLUMNNAME_Help, Help);
  }

  /**
   * Get Comment/Help.
   *
   * @return Comment or Hint
   */
  public String getHelp() {
    return (String) get_Value(COLUMNNAME_Help);
  }

  /**
   * Set Beta Functionality.
   *
   * @param IsBetaFunctionality This functionality is considered Beta
   */
  public void setIsBetaFunctionality(boolean IsBetaFunctionality) {
    set_Value(COLUMNNAME_IsBetaFunctionality, Boolean.valueOf(IsBetaFunctionality));
  }

  /**
   * Get Beta Functionality.
   *
   * @return This functionality is considered Beta
   */
  public boolean isBetaFunctionality() {
    Object oo = get_Value(COLUMNNAME_IsBetaFunctionality);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Direct print.
   *
   * @param IsDirectPrint Print without dialog
   */
  public void setIsDirectPrint(boolean IsDirectPrint) {
    set_Value(COLUMNNAME_IsDirectPrint, Boolean.valueOf(IsDirectPrint));
  }

  /**
   * Get Direct print.
   *
   * @return Print without dialog
   */
  public boolean isDirectPrint() {
    Object oo = get_Value(COLUMNNAME_IsDirectPrint);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Report.
   *
   * @param IsReport Indicates a Report record
   */
  public void setIsReport(boolean IsReport) {
    set_Value(COLUMNNAME_IsReport, Boolean.valueOf(IsReport));
  }

  /**
   * Get Report.
   *
   * @return Indicates a Report record
   */
  public boolean isReport() {
    Object oo = get_Value(COLUMNNAME_IsReport);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Server Process.
   *
   * @param IsServerProcess Run this Process on Server only
   */
  public void setIsServerProcess(boolean IsServerProcess) {
    set_Value(COLUMNNAME_IsServerProcess, Boolean.valueOf(IsServerProcess));
  }

  /**
   * Get Server Process.
   *
   * @return Run this Process on Server only
   */
  public boolean isServerProcess() {
    Object oo = get_Value(COLUMNNAME_IsServerProcess);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  /**
   * Set Jasper Report.
   *
   * @param JasperReport Jasper Report
   */
  public void setJasperReport(String JasperReport) {
    set_Value(COLUMNNAME_JasperReport, JasperReport);
  }

  /**
   * Get Jasper Report.
   *
   * @return Jasper Report
   */
  public String getJasperReport() {
    return (String) get_Value(COLUMNNAME_JasperReport);
  }

  /**
   * Set Procedure.
   *
   * @param ProcedureName Name of the Database Procedure
   */
  public void setProcedureName(String ProcedureName) {
    set_Value(COLUMNNAME_ProcedureName, ProcedureName);
  }

  /**
   * Get Procedure.
   *
   * @return Name of the Database Procedure
   */
  public String getProcedureName() {
    return (String) get_Value(COLUMNNAME_ProcedureName);
  }

    /**
   * Set Show Help.
   *
   * @param ShowHelp Show Help
   */
  public void setShowHelp(String ShowHelp) {

    set_Value(COLUMNNAME_ShowHelp, ShowHelp);
  }

  /**
   * Get Show Help.
   *
   * @return Show Help
   */
  public String getShowHelp() {
    return (String) get_Value(COLUMNNAME_ShowHelp);
  }

  /**
   * Set Statistic Count.
   *
   * @param Statistic_Count Internal statistics how often the entity was used
   */
  public void setStatistic_Count(int Statistic_Count) {
    set_Value(COLUMNNAME_Statistic_Count, Integer.valueOf(Statistic_Count));
  }

  /**
   * Get Statistic Count.
   *
   * @return Internal statistics how often the entity was used
   */
  public int getStatistic_Count() {
    Integer ii = (Integer) get_Value(COLUMNNAME_Statistic_Count);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Statistic Seconds.
   *
   * @param Statistic_Seconds Internal statistics how many seconds a process took
   */
  public void setStatistic_Seconds(int Statistic_Seconds) {
    set_Value(COLUMNNAME_Statistic_Seconds, Integer.valueOf(Statistic_Seconds));
  }

  /**
   * Get Statistic Seconds.
   *
   * @return Internal statistics how many seconds a process took
   */
  public int getStatistic_Seconds() {
    Integer ii = (Integer) get_Value(COLUMNNAME_Statistic_Seconds);
    if (ii == null) return 0;
    return ii;
  }

    @Override
  public int getTableId() {
    return I_AD_Process.Table_ID;
  }
}
