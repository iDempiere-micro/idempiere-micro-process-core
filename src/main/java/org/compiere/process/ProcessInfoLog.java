package org.compiere.process;

import org.compiere.model.IProcessInfoLog;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Process Info Log (VO)
 *
 * @author Jorg Janke
 * @version $Id: ProcessInfoLog.java,v 1.3 2006/07/30 00:54:44 jjanke Exp $
 */
public class ProcessInfoLog implements Serializable, IProcessInfoLog {
    /**
     *
     */
    private static final long serialVersionUID = 2790364724540395200L;
    private static int s_Log_ID = 0;
    private int m_Log_ID;
    private int m_P_ID;
    private Timestamp m_P_Date;
    private BigDecimal m_P_Number;
    private String m_P_Msg;
    private int m_AD_Table_ID;
    private int m_Record_ID;
    /**
     * Create Process Info Log.
     *
     * @param P_ID     Process ID
     * @param P_Date   Process Date
     * @param P_Number Process Number
     * @param P_Msg    Process Messagre
     */
    public ProcessInfoLog(
            int Log_ID,
            int P_ID,
            Timestamp P_Date,
            BigDecimal P_Number,
            String P_Msg,
            int AD_Table_ID,
            int Record_ID) {
        setLog_ID(Log_ID);
        setP_ID(P_ID);
        setP_Date(P_Date);
        setP_Number(P_Number);
        setP_Msg(P_Msg);
        setAD_Table_ID(AD_Table_ID);
        setRecord_ID(Record_ID);
    } //	ProcessInfoLog
    public ProcessInfoLog(
            int P_ID,
            Timestamp P_Date,
            BigDecimal P_Number,
            String P_Msg,
            int AD_Table_ID,
            int Record_ID) {
        this(s_Log_ID++, P_ID, P_Date, P_Number, P_Msg, AD_Table_ID, Record_ID);
    }
    /**
     * Create Process Info Log.
     *
     * @param P_ID     Process ID
     * @param P_Date   Process Date
     * @param P_Number Process Number
     * @param P_Msg    Process Messagre
     */
    public ProcessInfoLog(int P_ID, Timestamp P_Date, BigDecimal P_Number, String P_Msg) {
        this(s_Log_ID++, P_ID, P_Date, P_Number, P_Msg, 0, 0);
    } //	ProcessInfoLog

    public int getAD_Table_ID() {
        return m_AD_Table_ID;
    }

    public void setAD_Table_ID(int tableId) {
        this.m_AD_Table_ID = tableId;
    }

    public int getRecord_ID() {
        return m_Record_ID;
    }

    public void setRecord_ID(int recordId) {
        this.m_Record_ID = recordId;
    }

    /**
     * Get Log_ID
     *
     * @return id
     */
    public int getLog_ID() {
        return m_Log_ID;
    }

    /**
     * Set Log_ID
     *
     * @param Log_ID id
     */
    public void setLog_ID(int Log_ID) {
        m_Log_ID = Log_ID;
    }

    /**
     * Method getP_ID
     *
     * @return int
     */
    public int getP_ID() {
        return m_P_ID;
    }

    /**
     * Method setP_ID
     *
     * @param P_ID int
     */
    public void setP_ID(int P_ID) {
        m_P_ID = P_ID;
    }

    /**
     * Method getP_Date
     *
     * @return Timestamp
     */
    public Timestamp getP_Date() {
        return m_P_Date;
    }

    /**
     * Method setP_Date
     *
     * @param P_Date Timestamp
     */
    public void setP_Date(Timestamp P_Date) {
        m_P_Date = P_Date;
    }

    /**
     * Method getP_Number
     *
     * @return BigDecimal
     */
    public BigDecimal getP_Number() {
        return m_P_Number;
    }

    /**
     * Method setP_Number
     *
     * @param P_Number BigDecimal
     */
    public void setP_Number(BigDecimal P_Number) {
        m_P_Number = P_Number;
    }

    /**
     * Method getP_Msg
     *
     * @return String
     */
    public String getP_Msg() {
        return m_P_Msg;
    }

    /**
     * Method setP_Msg
     *
     * @param P_Msg String
     */
    public void setP_Msg(String P_Msg) {
        m_P_Msg = P_Msg;
    }
} //	ProcessInfoLog
