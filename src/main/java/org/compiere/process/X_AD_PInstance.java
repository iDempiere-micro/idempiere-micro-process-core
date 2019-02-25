package org.compiere.process;

import org.compiere.model.I_AD_PInstance;
import org.compiere.orm.BasePOName;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_PInstance
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_PInstance extends BasePOName implements I_AD_PInstance {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_PInstance(Properties ctx, int AD_PInstance_ID) {
        super(ctx, AD_PInstance_ID);
        /**
         * if (AD_PInstance_ID == 0) { setAD_PInstance_ID (0); setAD_Process_ID (0); setIsProcessing
         * (false); setIsRunAsJob (false); // N setRecordId (0); }
         */
    }

    /**
     * Load Constructor
     */
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
     * Get Process Instance.
     *
     * @return Instance of the process
     */
    public int getPInstanceId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_PInstance_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Process.
     *
     * @return Process or Report
     */
    public int getProcessId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_Process_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Process.
     *
     * @param AD_Process_ID Process or Report
     */
    public void setProcessId(int AD_Process_ID) {
        if (AD_Process_ID < 1) set_Value(COLUMNNAME_AD_Process_ID, null);
        else set_Value(COLUMNNAME_AD_Process_ID, AD_Process_ID);
    }

    /**
     * Set User/Contact.
     *
     * @param AD_User_ID User within the system - Internal or Business Partner Contact
     */
    public void setUserId(int AD_User_ID) {
        if (AD_User_ID < 1) set_Value(COLUMNNAME_AD_User_ID, null);
        else set_Value(COLUMNNAME_AD_User_ID, AD_User_ID);
    }

    /**
     * Get Error Msg.
     *
     * @return Error Msg
     */
    public String getErrorMsg() {
        return (String) getValue(COLUMNNAME_ErrorMsg);
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
        Object oo = getValue(COLUMNNAME_IsProcessing);
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
    public void setRecordId(int Record_ID) {
        if (Record_ID < 0) set_ValueNoCheck(COLUMNNAME_Record_ID, null);
        else set_ValueNoCheck(COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
    }

    /**
     * Get Result.
     *
     * @return Result of the action taken
     */
    public int getResult() {
        Integer ii = (Integer) getValue(COLUMNNAME_Result);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Result.
     *
     * @param Result Result of the action taken
     */
    public void setResult(int Result) {
        set_Value(COLUMNNAME_Result, Integer.valueOf(Result));
    }

    @Override
    public int getTableId() {
        return I_AD_PInstance.Table_ID;
    }
}
