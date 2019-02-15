package org.compiere.process;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_AD_PInstance;
import org.compiere.orm.BasePOName;
import org.compiere.orm.MTable;
import org.idempiere.orm.I_Persistent;

/**
 * Generated Model for AD_PInstance
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_PInstance extends BasePOName implements I_AD_PInstance, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_PInstance(Properties ctx, int AD_PInstance_ID) {
    super(ctx, AD_PInstance_ID);
    /**
     * if (AD_PInstance_ID == 0) { setAD_PInstance_ID (0); setAD_Process_ID (0); setIsProcessing
     * (false); setIsRunAsJob (false); // N setRecord_ID (0); }
     */
  }

  /** Load Constructor */
  public X_AD_PInstance(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  }

  /**
   * AccessLevel
   *
   * @return 6 - System - Client
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("X_AD_PInstance[").append(getId()).append("]");
    return sb.toString();
  }

    /**
   * Get Language ID.
   *
   * @return Language ID
   */
  public int getAD_Language_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Language_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Process Instance.
   *
   * @return Instance of the process
   */
  public int getAD_PInstance_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_PInstance_ID);
    if (ii == null) return 0;
    return ii;
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

  public org.compiere.model.I_AD_Process getAD_Process() throws RuntimeException {
    return (org.compiere.model.I_AD_Process)
        MTable.get(getCtx(), org.compiere.model.I_AD_Process.Table_Name)
            .getPO(getAD_Process_ID());
  }

  /**
   * Set Process.
   *
   * @param AD_Process_ID Process or Report
   */
  public void setAD_Process_ID(int AD_Process_ID) {
    if (AD_Process_ID < 1) set_Value(COLUMNNAME_AD_Process_ID, null);
    else set_Value(COLUMNNAME_AD_Process_ID, AD_Process_ID);
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
   * Set User/Contact.
   *
   * @param AD_User_ID User within the system - Internal or Business Partner Contact
   */
  public void setAD_User_ID(int AD_User_ID) {
    if (AD_User_ID < 1) set_Value(COLUMNNAME_AD_User_ID, null);
    else set_Value(COLUMNNAME_AD_User_ID, AD_User_ID);
  }

  /**
   * Get User/Contact.
   *
   * @return User within the system - Internal or Business Partner Contact
   */
  public int getAD_User_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_User_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Error Msg.
   *
   * @param ErrorMsg Error Msg
   */
  public void setErrorMsg(String ErrorMsg) {
    set_Value(COLUMNNAME_ErrorMsg, ErrorMsg);
  }

  /**
   * Get Error Msg.
   *
   * @return Error Msg
   */
  public String getErrorMsg() {
    return (String) get_Value(COLUMNNAME_ErrorMsg);
  }

  /**
   * Set Processing.
   *
   * @param IsProcessing Processing
   */
  public void setIsProcessing(boolean IsProcessing) {
    set_Value(COLUMNNAME_IsProcessing, Boolean.valueOf(IsProcessing));
  }

  /**
   * Get Processing.
   *
   * @return Processing
   */
  public boolean isProcessing() {
    Object oo = get_Value(COLUMNNAME_IsProcessing);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Get Summary Level.
   *
   * @return This is a summary entity
   */
  public boolean isSummary() {
    Object oo = get_Value(COLUMNNAME_IsSummary);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

    /**
   * Set Record ID.
   *
   * @param Record_ID Direct internal record ID
   */
  public void setRecord_ID(int Record_ID) {
    if (Record_ID < 0) set_ValueNoCheck(COLUMNNAME_Record_ID, null);
    else set_ValueNoCheck(COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
  }

    /**
   * Get Report Type.
   *
   * @return Report Type
   */
  public String getReportType() {
    return (String) get_Value(COLUMNNAME_ReportType);
  }

  /**
   * Set Result.
   *
   * @param Result Result of the action taken
   */
  public void setResult(int Result) {
    set_Value(COLUMNNAME_Result, Integer.valueOf(Result));
  }

  /**
   * Get Result.
   *
   * @return Result of the action taken
   */
  public int getResult() {
    Integer ii = (Integer) get_Value(COLUMNNAME_Result);
    if (ii == null) return 0;
    return ii;
  }

  @Override
  public int getTableId() {
    return I_AD_PInstance.Table_ID;
  }
}
