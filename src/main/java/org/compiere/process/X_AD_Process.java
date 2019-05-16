package org.compiere.process;

import kotliquery.Row;
import org.compiere.model.Process;
import org.compiere.orm.BasePONameValue;

/**
 * Generated Model for AD_Process
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_Process extends BasePONameValue implements Process {

    /**
     * All = 7
     */
    public static final String ACCESSLEVEL_All = "7";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_Process(int AD_Process_ID) {
        super(AD_Process_ID);
        /**
         * if (AD_Process_ID == 0) { setAccessLevel (null); setAD_Process_ID (0); setEntityType (null);
         * // @SQL=select get_sysconfig('DEFAULT_ENTITYTYPE','U',0,0) from dual setIsBetaFunctionality
         * (false); setIsReport (false); setIsServerProcess (false); setName (null); setValue (null); }
         */
    }

    /**
     * Load Constructor
     */
    public X_AD_Process(Row row) {
        super(row);
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

    /**
     * Get Data Access Level.
     *
     * @return Access Level required
     */
    public String getProcessAccessLevel() {
        return getValue(COLUMNNAME_AccessLevel);
    }

    /**
     * Set Data Access Level.
     *
     * @param AccessLevel Access Level required
     */
    public void setProcessAccessLevel(String AccessLevel) {

        setValue(COLUMNNAME_AccessLevel, AccessLevel);
    }

    /**
     * Get Special Form.
     *
     * @return Special Form
     */
    public int getFormId() {
        Integer ii = getValue(COLUMNNAME_AD_Form_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Special Form.
     *
     * @param AD_Form_ID Special Form
     */
    public void setFormId(int AD_Form_ID) {
        if (AD_Form_ID < 1) setValue(COLUMNNAME_AD_Form_ID, null);
        else setValue(COLUMNNAME_AD_Form_ID, Integer.valueOf(AD_Form_ID));
    }

    /**
     * Get Print Format.
     *
     * @return Data Print Format
     */
    public int getPrintFormatId() {
        Integer ii = getValue(COLUMNNAME_AD_PrintFormat_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Print Format.
     *
     * @param AD_PrintFormat_ID Data Print Format
     */
    public void setPrintFormatId(int AD_PrintFormat_ID) {
        if (AD_PrintFormat_ID < 1) setValue(COLUMNNAME_AD_PrintFormat_ID, null);
        else setValue(COLUMNNAME_AD_PrintFormat_ID, Integer.valueOf(AD_PrintFormat_ID));
    }

    /**
     * Get Process.
     *
     * @return Process or Report
     */
    public int getProcessId() {
        Integer ii = getValue(COLUMNNAME_AD_Process_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Report View.
     *
     * @return View used to generate this report
     */
    public int getReportViewId() {
        Integer ii = getValue(COLUMNNAME_AD_ReportView_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Report View.
     *
     * @param AD_ReportView_ID View used to generate this report
     */
    public void setReportViewId(int AD_ReportView_ID) {
        if (AD_ReportView_ID < 1) setValue(COLUMNNAME_AD_ReportView_ID, null);
        else setValue(COLUMNNAME_AD_ReportView_ID, AD_ReportView_ID);
    }

    /**
     * Get Workflow.
     *
     * @return Workflow or combination of tasks
     */
    public int getWorkflowId() {
        Integer ii = getValue(COLUMNNAME_AD_Workflow_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Workflow.
     *
     * @param AD_Workflow_ID Workflow or combination of tasks
     */
    public void setWorkflowId(int AD_Workflow_ID) {
        if (AD_Workflow_ID < 1) setValue(COLUMNNAME_AD_Workflow_ID, null);
        else setValue(COLUMNNAME_AD_Workflow_ID, Integer.valueOf(AD_Workflow_ID));
    }

    /**
     * Get Classname.
     *
     * @return Java Classname
     */
    public String getClassname() {
        return getValue(COLUMNNAME_Classname);
    }

    /**
     * Set Classname.
     *
     * @param Classname Java Classname
     */
    public void setClassname(String Classname) {
        setValue(COLUMNNAME_Classname, Classname);
    }

    /**
     * Get Description.
     *
     * @return Optional short description of the record
     */
    public String getDescription() {
        return getValue(COLUMNNAME_Description);
    }

    /**
     * Set Description.
     *
     * @param Description Optional short description of the record
     */
    public void setDescription(String Description) {
        setValue(COLUMNNAME_Description, Description);
    }

    /**
     * Get Entity Type.
     *
     * @return Dictionary Entity Type; Determines ownership and synchronization
     */
    public String getEntityType() {
        return getValue(COLUMNNAME_EntityType);
    }

    /**
     * Set Entity Type.
     *
     * @param EntityType Dictionary Entity Type; Determines ownership and synchronization
     */
    public void setEntityType(String EntityType) {

        setValue(COLUMNNAME_EntityType, EntityType);
    }

    /**
     * Get Comment/Help.
     *
     * @return Comment or Hint
     */
    public String getHelp() {
        return getValue(COLUMNNAME_Help);
    }

    /**
     * Set Comment/Help.
     *
     * @param Help Comment or Hint
     */
    public void setHelp(String Help) {
        setValue(COLUMNNAME_Help, Help);
    }

    /**
     * Set Beta Functionality.
     *
     * @param IsBetaFunctionality This functionality is considered Beta
     */
    public void setIsBetaFunctionality(boolean IsBetaFunctionality) {
        setValue(COLUMNNAME_IsBetaFunctionality, Boolean.valueOf(IsBetaFunctionality));
    }

    /**
     * Get Beta Functionality.
     *
     * @return This functionality is considered Beta
     */
    public boolean isBetaFunctionality() {
        Object oo = getValue(COLUMNNAME_IsBetaFunctionality);
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
        setValue(COLUMNNAME_IsDirectPrint, Boolean.valueOf(IsDirectPrint));
    }

    /**
     * Get Direct print.
     *
     * @return Print without dialog
     */
    public boolean isDirectPrint() {
        Object oo = getValue(COLUMNNAME_IsDirectPrint);
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
        setValue(COLUMNNAME_IsReport, Boolean.valueOf(IsReport));
    }

    /**
     * Get Report.
     *
     * @return Indicates a Report record
     */
    public boolean isReport() {
        Object oo = getValue(COLUMNNAME_IsReport);
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
        setValue(COLUMNNAME_IsServerProcess, Boolean.valueOf(IsServerProcess));
    }

    /**
     * Get Server Process.
     *
     * @return Run this Process on Server only
     */
    public boolean isServerProcess() {
        Object oo = getValue(COLUMNNAME_IsServerProcess);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Get Jasper Report.
     *
     * @return Jasper Report
     */
    public String getJasperReport() {
        return getValue(COLUMNNAME_JasperReport);
    }

    /**
     * Set Jasper Report.
     *
     * @param JasperReport Jasper Report
     */
    public void setJasperReport(String JasperReport) {
        setValue(COLUMNNAME_JasperReport, JasperReport);
    }

    /**
     * Get Procedure.
     *
     * @return Name of the Database Procedure
     */
    public String getProcedureName() {
        return getValue(COLUMNNAME_ProcedureName);
    }

    /**
     * Set Procedure.
     *
     * @param ProcedureName Name of the Database Procedure
     */
    public void setProcedureName(String ProcedureName) {
        setValue(COLUMNNAME_ProcedureName, ProcedureName);
    }

    /**
     * Get Show Help.
     *
     * @return Show Help
     */
    public String getShowHelp() {
        return getValue(COLUMNNAME_ShowHelp);
    }

    /**
     * Set Show Help.
     *
     * @param ShowHelp Show Help
     */
    public void setShowHelp(String ShowHelp) {

        setValue(COLUMNNAME_ShowHelp, ShowHelp);
    }

    /**
     * Get Statistic Count.
     *
     * @return Internal statistics how often the entity was used
     */
    public int getStatisticCount() {
        Integer ii = getValue(COLUMNNAME_Statistic_Count);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Statistic Count.
     *
     * @param Statistic_Count Internal statistics how often the entity was used
     */
    public void setStatisticCount(int Statistic_Count) {
        setValue(COLUMNNAME_Statistic_Count, Integer.valueOf(Statistic_Count));
    }

    /**
     * Get Statistic Seconds.
     *
     * @return Internal statistics how many seconds a process took
     */
    public int getStatisticSeconds() {
        Integer ii = getValue(COLUMNNAME_Statistic_Seconds);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Statistic Seconds.
     *
     * @param Statistic_Seconds Internal statistics how many seconds a process took
     */
    public void setStatisticSeconds(int Statistic_Seconds) {
        setValue(COLUMNNAME_Statistic_Seconds, Integer.valueOf(Statistic_Seconds));
    }

    @Override
    public int getTableId() {
        return Process.Table_ID;
    }
}
