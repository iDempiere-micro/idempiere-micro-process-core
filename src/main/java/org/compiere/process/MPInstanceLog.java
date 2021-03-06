package org.compiere.process;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Process Instance Log Model. (not standard table)
 *
 * @author Jorg Janke
 * @version $Id: MPInstanceLog.java,v 1.3 2006/07/30 00:58:18 jjanke Exp $
 */
public class MPInstanceLog {
    private int m_Log_ID;
    private Timestamp m_P_Date;
    private int m_P_ID;
    private BigDecimal m_P_Number;
    private String m_P_Msg;

    /**
     * Full Constructor
     *
     * @param AD_PInstance_ID instance
     * @param Log_ID          log sequence
     * @param P_Date          date
     * @param P_ID            id
     * @param P_Number        number
     * @param P_Msg           msg
     */
    public MPInstanceLog(
            int AD_PInstance_ID,
            int Log_ID,
            Timestamp P_Date,
            int P_ID,
            BigDecimal P_Number,
            String P_Msg) {
        setProcessInstanceId(AD_PInstance_ID);
        setLogId(Log_ID);
        setP_Date(P_Date);
        setPId(P_ID);
        setP_Number(P_Number);
        setP_Msg(P_Msg);
    } //	MPInstance_Log

    /**
     * Load Constructor
     *
     * @param rs Result Set
     * @throws SQLException
     */
    public MPInstanceLog(ResultSet rs) throws SQLException {
        setProcessInstanceId(rs.getInt("AD_PInstance_ID"));
        setLogId(rs.getInt("Log_ID"));
        setP_Date(rs.getTimestamp("P_Date"));
        setPId(rs.getInt("P_ID"));
        setP_Number(rs.getBigDecimal("P_Number"));
        setP_Msg(rs.getString("P_Msg"));
    } //	MPInstance_Log

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("PPInstance_Log[");
        sb.append(m_Log_ID);
        if (m_P_Date != null) sb.append(",Date=").append(m_P_Date);
        if (m_P_ID != 0) sb.append(",ID=").append(m_P_ID);
        if (m_P_Number != null) sb.append(",Number=").append(m_P_Number);
        if (m_P_Msg != null) sb.append(",").append(m_P_Msg);
        sb.append("]");
        return sb.toString();
    } //	toString

    /**
     * Set AD_PInstance_ID
     *
     * @param AD_PInstance_ID instance id
     */
    public void setProcessInstanceId(int AD_PInstance_ID) {
    }

    /**
     * Set Log_ID
     *
     * @param Log_ID log id
     */
    public void setLogId(int Log_ID) {
        m_Log_ID = Log_ID;
    }

    /**
     * Set P_Date
     *
     * @param P_Date date
     */
    public void setP_Date(Timestamp P_Date) {
        m_P_Date = P_Date;
    }

    /**
     * Set P_ID
     *
     * @param P_ID id
     */
    public void setPId(int P_ID) {
        m_P_ID = P_ID;
    }

    /**
     * Set P_Number
     *
     * @param P_Number number
     */
    public void setP_Number(BigDecimal P_Number) {
        m_P_Number = P_Number;
    }

    /**
     * Set P_Msg
     *
     * @param P_Msg
     */
    public void setP_Msg(String P_Msg) {
        m_P_Msg = P_Msg;
    }
} //	MPInstance_Log
