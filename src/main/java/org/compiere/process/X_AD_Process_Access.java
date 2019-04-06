package org.compiere.process;

import kotliquery.Row;
import org.compiere.model.I_AD_Process_Access;
import org.compiere.orm.PO;

/**
 * Generated Model for AD_Process_Access
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_Process_Access extends PO implements I_AD_Process_Access {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_Process_Access(int AD_Process_Access_ID) {
        super(AD_Process_Access_ID);
        /**
         * if (AD_Process_Access_ID == 0) { setAD_Process_ID (0); setRoleId (0); setIsReadWrite
         * (false); }
         */
    }

    /**
     * Load Constructor
     */
    public X_AD_Process_Access(Row row) {
        super(row);
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
     * Set Process.
     *
     * @param AD_Process_ID Process or Report
     */
    public void setProcessId(int AD_Process_ID) {
        if (AD_Process_ID < 1) setValueNoCheck(COLUMNNAME_AD_Process_ID, null);
        else setValueNoCheck(COLUMNNAME_AD_Process_ID, Integer.valueOf(AD_Process_ID));
    }

    /**
     * Set Role.
     *
     * @param AD_Role_ID Responsibility Role
     */
    public void setRoleId(int AD_Role_ID) {
        if (AD_Role_ID < 0) setValueNoCheck(COLUMNNAME_AD_Role_ID, null);
        else setValueNoCheck(COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
    }

    /**
     * Set Read Write.
     *
     * @param IsReadWrite Field is read / write
     */
    public void setIsReadWrite(boolean IsReadWrite) {
        setValue(COLUMNNAME_IsReadWrite, Boolean.valueOf(IsReadWrite));
    }

    @Override
    public int getTableId() {
        return I_AD_Process_Access.Table_ID;
    }
}
