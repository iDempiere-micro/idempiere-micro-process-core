package org.compiere.process;

import org.compiere.model.I_AD_PInstance_Para;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for AD_PInstance_Para
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_PInstance_Para extends PO implements I_AD_PInstance_Para {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_PInstance_Para(Properties ctx, int AD_PInstance_Para_ID) {
        super(ctx, AD_PInstance_Para_ID);
        /** if (AD_PInstance_Para_ID == 0) { setAD_PInstance_ID (0); setSeqNo (0); } */
    }

    /**
     * Load Constructor
     */
    public X_AD_PInstance_Para(Properties ctx, ResultSet rs) {
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
        StringBuffer sb = new StringBuffer("X_AD_PInstance_Para[").append(getId()).append("]");
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
     * Set Process Instance.
     *
     * @param AD_PInstance_ID Instance of the process
     */
    public void setPInstanceId(int AD_PInstance_ID) {
        if (AD_PInstance_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_PInstance_ID, null);
        else set_ValueNoCheck(COLUMNNAME_AD_PInstance_ID, AD_PInstance_ID);
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
     * Get Parameter Name.
     *
     * @return Parameter Name
     */
    public String getParameterName() {
        return (String) getValue(COLUMNNAME_ParameterName);
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
     * Get Process Date.
     *
     * @return Process Parameter
     */
    public Timestamp getProcessDate() {
        return (Timestamp) getValue(COLUMNNAME_P_Date);
    }

    /**
     * Set Process Date.
     *
     * @param P_Date Process Parameter
     */
    public void setProcessDate(Timestamp P_Date) {
        set_Value(COLUMNNAME_P_Date, P_Date);
    }

    /**
     * Get Process Date To.
     *
     * @return Process Parameter
     */
    public Timestamp getProcessDateTo() {
        return (Timestamp) getValue(COLUMNNAME_P_Date_To);
    }

    /**
     * Set Process Date To.
     *
     * @param P_Date_To Process Parameter
     */
    public void setProcessDateTo(Timestamp P_Date_To) {
        set_Value(COLUMNNAME_P_Date_To, P_Date_To);
    }

    /**
     * Get Process Number.
     *
     * @return Process Parameter
     */
    public BigDecimal getProcessNumber() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_P_Number);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Process Number.
     *
     * @param P_Number Process Parameter
     */
    public void setProcessNumber(BigDecimal P_Number) {
        set_Value(COLUMNNAME_P_Number, P_Number);
    }

    /**
     * Get Process Number To.
     *
     * @return Process Parameter
     */
    public BigDecimal getProcessNumberTo() {
        BigDecimal bd = (BigDecimal) getValue(COLUMNNAME_P_Number_To);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Process Number To.
     *
     * @param P_Number_To Process Parameter
     */
    public void setProcessNumberTo(BigDecimal P_Number_To) {
        set_Value(COLUMNNAME_P_Number_To, P_Number_To);
    }

    /**
     * Get Process String.
     *
     * @return Process Parameter
     */
    public String getProcessString() {
        return (String) getValue(COLUMNNAME_P_String);
    }

    /**
     * Set Process String.
     *
     * @param P_String Process Parameter
     */
    public void setProcessString(String P_String) {
        set_Value(COLUMNNAME_P_String, P_String);
    }

    /**
     * Get Process String To.
     *
     * @return Process Parameter
     */
    public String getProcessStringTo() {
        return (String) getValue(COLUMNNAME_P_String_To);
    }

    /**
     * Set Process String To.
     *
     * @param P_String_To Process Parameter
     */
    public void setProcessStringTo(String P_String_To) {
        set_Value(COLUMNNAME_P_String_To, P_String_To);
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
