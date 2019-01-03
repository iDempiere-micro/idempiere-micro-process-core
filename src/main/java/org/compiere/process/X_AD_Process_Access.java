package org.compiere.process;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_AD_Process_Access;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.orm.I_Persistent;

/**
 * Generated Model for AD_Process_Access
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_Process_Access extends PO implements I_AD_Process_Access, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_Process_Access(Properties ctx, int AD_Process_Access_ID, String trxName) {
    super(ctx, AD_Process_Access_ID, trxName);
    /**
     * if (AD_Process_Access_ID == 0) { setAD_Process_ID (0); setAD_Role_ID (0); setIsReadWrite
     * (false); }
     */
  }

  /** Load Constructor */
  public X_AD_Process_Access(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_AD_Process_Access[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set AD_Process_Access_UU.
   *
   * @param AD_Process_Access_UU AD_Process_Access_UU
   */
  public void setAD_Process_Access_UU(String AD_Process_Access_UU) {
    set_Value(COLUMNNAME_AD_Process_Access_UU, AD_Process_Access_UU);
  }

  /**
   * Get AD_Process_Access_UU.
   *
   * @return AD_Process_Access_UU
   */
  public String getAD_Process_Access_UU() {
    return (String) get_Value(COLUMNNAME_AD_Process_Access_UU);
  }

  public org.compiere.model.I_AD_Process getAD_Process() throws RuntimeException {
    return (org.compiere.model.I_AD_Process)
        MTable.get(getCtx(), org.compiere.model.I_AD_Process.Table_Name)
            .getPO(getAD_Process_ID(), null);
  }

  /**
   * Set Process.
   *
   * @param AD_Process_ID Process or Report
   */
  public void setAD_Process_ID(int AD_Process_ID) {
    if (AD_Process_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_Process_ID, null);
    else set_ValueNoCheck(COLUMNNAME_AD_Process_ID, Integer.valueOf(AD_Process_ID));
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

  public org.compiere.model.I_AD_Role getAD_Role() throws RuntimeException {
    return (org.compiere.model.I_AD_Role)
        MTable.get(getCtx(), org.compiere.model.I_AD_Role.Table_Name)
            .getPO(getAD_Role_ID(), null);
  }

  /**
   * Set Role.
   *
   * @param AD_Role_ID Responsibility Role
   */
  public void setAD_Role_ID(int AD_Role_ID) {
    if (AD_Role_ID < 0) set_ValueNoCheck(COLUMNNAME_AD_Role_ID, null);
    else set_ValueNoCheck(COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
  }

  /**
   * Get Role.
   *
   * @return Responsibility Role
   */
  public int getAD_Role_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_AD_Role_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Read Write.
   *
   * @param IsReadWrite Field is read / write
   */
  public void setIsReadWrite(boolean IsReadWrite) {
    set_Value(COLUMNNAME_IsReadWrite, Boolean.valueOf(IsReadWrite));
  }

  /**
   * Get Read Write.
   *
   * @return Field is read / write
   */
  public boolean isReadWrite() {
    Object oo = get_Value(COLUMNNAME_IsReadWrite);
    if (oo != null) {
      if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
      return "Y".equals(oo);
    }
    return false;
  }

  @Override
  public int getTableId() {
    return I_AD_Process_Access.Table_ID;
  }
}
