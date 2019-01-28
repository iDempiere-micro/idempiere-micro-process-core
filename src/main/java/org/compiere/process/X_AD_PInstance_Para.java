package org.compiere.process;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.I_AD_PInstance_Para;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

/**
 * Generated Model for AD_PInstance_Para
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_PInstance_Para extends PO implements I_AD_PInstance_Para, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_PInstance_Para(Properties ctx, int AD_PInstance_Para_ID, String trxName) {
    super(ctx, AD_PInstance_Para_ID, trxName);
    /** if (AD_PInstance_Para_ID == 0) { setAD_PInstance_ID (0); setSeqNo (0); } */
  }

  /** Load Constructor */
  public X_AD_PInstance_Para(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
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
    StringBuffer sb = new StringBuffer("X_AD_PInstance_Para[").append(getId()).append("]");
    return sb.toString();
  }

  public org.compiere.model.I_AD_PInstance getAD_PInstance() throws RuntimeException {
    return (org.compiere.model.I_AD_PInstance)
        MTable.get(getCtx(), org.compiere.model.I_AD_PInstance.Table_Name)
            .getPO(getAD_PInstance_ID(), null);
  }

  /**
   * Set Process Instance.
   *
   * @param AD_PInstance_ID Instance of the process
   */
  public void setAD_PInstance_ID(int AD_PInstance_ID) {
    if (AD_PInstance_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_PInstance_ID, null);
    else set_ValueNoCheck(COLUMNNAME_AD_PInstance_ID, Integer.valueOf(AD_PInstance_ID));
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
   * Set Info.
   *
   * @param Info Information
   */
  public void setInfo(String Info) {
    set_Value(COLUMNNAME_Info, Info);
  }

  /**
   * Get Info.
   *
   * @return Information
   */
  public String getInfo() {
    return (String) get_Value(COLUMNNAME_Info);
  }

    /**
   * Get Info To.
   *
   * @return Info To
   */
  public String getInfo_To() {
    return (String) get_Value(COLUMNNAME_Info_To);
  }

  /**
   * Set Parameter Name.
   *
   * @param ParameterName Parameter Name
   */
  public void setParameterName(String ParameterName) {
    set_Value(COLUMNNAME_ParameterName, ParameterName);
  }

  /**
   * Get Parameter Name.
   *
   * @return Parameter Name
   */
  public String getParameterName() {
    return (String) get_Value(COLUMNNAME_ParameterName);
  }

    /**
   * Set Process Date.
   *
   * @param P_Date Process Parameter
   */
  public void setP_Date(Timestamp P_Date) {
    set_Value(COLUMNNAME_P_Date, P_Date);
  }

  /**
   * Get Process Date.
   *
   * @return Process Parameter
   */
  public Timestamp getP_Date() {
    return (Timestamp) get_Value(COLUMNNAME_P_Date);
  }

  /**
   * Set Process Date To.
   *
   * @param P_Date_To Process Parameter
   */
  public void setP_Date_To(Timestamp P_Date_To) {
    set_Value(COLUMNNAME_P_Date_To, P_Date_To);
  }

  /**
   * Get Process Date To.
   *
   * @return Process Parameter
   */
  public Timestamp getP_Date_To() {
    return (Timestamp) get_Value(COLUMNNAME_P_Date_To);
  }

  /**
   * Set Process Number.
   *
   * @param P_Number Process Parameter
   */
  public void setP_Number(BigDecimal P_Number) {
    set_Value(COLUMNNAME_P_Number, P_Number);
  }

  /**
   * Get Process Number.
   *
   * @return Process Parameter
   */
  public BigDecimal getP_Number() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_P_Number);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Process Number To.
   *
   * @param P_Number_To Process Parameter
   */
  public void setP_Number_To(BigDecimal P_Number_To) {
    set_Value(COLUMNNAME_P_Number_To, P_Number_To);
  }

  /**
   * Get Process Number To.
   *
   * @return Process Parameter
   */
  public BigDecimal getP_Number_To() {
    BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_P_Number_To);
    if (bd == null) return Env.ZERO;
    return bd;
  }

  /**
   * Set Process String.
   *
   * @param P_String Process Parameter
   */
  public void setP_String(String P_String) {
    set_Value(COLUMNNAME_P_String, P_String);
  }

  /**
   * Get Process String.
   *
   * @return Process Parameter
   */
  public String getP_String() {
    return (String) get_Value(COLUMNNAME_P_String);
  }

  /**
   * Set Process String To.
   *
   * @param P_String_To Process Parameter
   */
  public void setP_String_To(String P_String_To) {
    set_Value(COLUMNNAME_P_String_To, P_String_To);
  }

  /**
   * Get Process String To.
   *
   * @return Process Parameter
   */
  public String getP_String_To() {
    return (String) get_Value(COLUMNNAME_P_String_To);
  }

  /**
   * Set Sequence.
   *
   * @param SeqNo Method of ordering records; lowest number comes first
   */
  public void setSeqNo(int SeqNo) {
    set_ValueNoCheck(COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
  }

    @Override
  public int getTableId() {
    return I_AD_PInstance_Para.Table_ID;
  }
}
