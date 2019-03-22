package org.compiere.process;

import org.compiere.model.IProcessInfo;
import org.compiere.model.IProcessInfoLog;
import org.idempiere.common.util.CLogger;
import software.hsharp.core.util.DBKt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.executeUpdate;
import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * Process Info with Utilities
 *
 * @author Jorg Janke
 * @version $Id: ProcessInfoUtil.java,v 1.2 2006/07/30 00:54:44 jjanke Exp $
 */
public class ProcessInfoUtil {
    /**
     * Logger
     */
    private static CLogger s_log = CLogger.getCLogger(ProcessInfoUtil.class);

    /**
     * Set Log of Process.
     *
     * @param pi process info
     */
    public static void setLogFromDB(ProcessInfo pi) {
        //	s_log.fine("setLogFromDB - AD_PInstance_ID=" + pi.getProcessInstanceId());
        String sql =
                "SELECT Log_ID, P_ID, P_Date, P_Number, P_Msg, AD_Table_ID,Record_ID "
                        + "FROM AD_PInstance_Log "
                        + "WHERE AD_PInstance_ID=? "
                        + "ORDER BY Log_ID";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, pi.getPInstanceId());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                // int Log_ID, int P_ID, Timestamp P_Date, BigDecimal P_Number, String P_Msg, AD_Table_ID,
                // Record_ID
                pi.addLog(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getTimestamp(3),
                        rs.getBigDecimal(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7));
            }
        } catch (SQLException e) {
            s_log.log(Level.SEVERE, "setLogFromDB", e);
        } finally {
            rs = null;
            pstmt = null;
        }
    } //	getLogFromDB

    /**
     * Create Process Log
     *
     * @param pi process info
     */
    public static void saveLogToDB(IProcessInfo pi) {
        IProcessInfoLog[] logs = pi.getLogs();
        if (logs == null || logs.length == 0) {
            //		s_log.fine("saveLogToDB - No Log");
            return;
        }
        if (pi.getPInstanceId() == 0) {
            //		s_log.log(Level.WARNING,"saveLogToDB - not saved - AD_PInstance_ID==0");
            return;
        }
        for (int i = 0; i < logs.length; i++) {
            StringBuilder sql =
                    new StringBuilder(
                            "INSERT INTO AD_PInstance_Log "
                                    + "(AD_PInstance_ID, Log_ID, P_Date, P_ID, P_Number, P_Msg, AD_Table_ID,Record_ID)"
                                    + " VALUES (");
            sql.append(pi.getPInstanceId()).append(",").append(logs[i].getLogId()).append(",");
            if (logs[i].getPDate() == null) sql.append("NULL,");
            else sql.append(DBKt.convertDate(logs[i].getPDate(), false)).append(",");
            if (logs[i].getPId() == 0) sql.append("NULL,");
            else sql.append(logs[i].getPId()).append(",");
            if (logs[i].getPNumber() == null) sql.append("NULL,");
            else sql.append(logs[i].getPNumber()).append(",");
            if (logs[i].getPMsg() == null) sql.append("NULL,");
            else sql.append(DBKt.convertString(logs[i].getPMsg(), 2000)).append(",");
            if (logs[i].getPTableId() == 0) sql.append("NULL,");
            else sql.append(logs[i].getPTableId()).append(",");
            if (logs[i].getRecordId() == 0) sql.append("NULL)");
            else sql.append(logs[i].getRecordId()).append(")");
            //
            executeUpdate(sql.toString());
        }
        pi.setLogList(null); // 	otherwise log entries are twice
    } //  saveLogToDB

    /**
     * Set Parameter of Process (and Client/User)
     *
     * @param pi Process Info
     */
    public static void setParameterFromDB(IProcessInfo pi) {
        ArrayList<ProcessInfoParameter> list = new ArrayList<ProcessInfoParameter>();
        String sql =
                "SELECT p.ParameterName," //  1
                        + " p.P_String,p.P_String_To, p.P_Number,p.P_Number_To," //  2/3 4/5
                        + " p.P_Date,p.P_Date_To, p.Info,p.Info_To, " //  6/7 8/9
                        + " i.AD_Client_ID, i.AD_Org_ID, i.AD_User_ID " //	10..12
                        + "FROM AD_PInstance_Para p"
                        + " INNER JOIN AD_PInstance i ON (p.AD_PInstance_ID=i.AD_PInstance_ID) "
                        + "WHERE p.AD_PInstance_ID=? "
                        + "ORDER BY p.SeqNo";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, pi.getPInstanceId());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String ParameterName = rs.getString(1);
                //	String
                Object Parameter = rs.getString(2);
                Object Parameter_To = rs.getString(3);
                //	Big Decimal
                if (Parameter == null && Parameter_To == null) {
                    Parameter = rs.getBigDecimal(4);
                    Parameter_To = rs.getBigDecimal(5);
                }
                //	Timestamp
                if (Parameter == null && Parameter_To == null) {
                    Parameter = rs.getTimestamp(6);
                    Parameter_To = rs.getTimestamp(7);
                }
                //	Info
                String Info = rs.getString(8);
                String Info_To = rs.getString(9);
                //
                list.add(new ProcessInfoParameter(ParameterName, Parameter, Parameter_To, Info, Info_To));
                //
                if (pi.getClientId() == null) pi.setADClientID(rs.getInt(10));
                if (pi.getUserId() == null) pi.setUserId(rs.getInt(12));
            }
        } catch (SQLException e) {
            s_log.log(Level.SEVERE, sql, e);
        } finally {
            rs = null;
            pstmt = null;
        }
        //
        ProcessInfoParameter[] pars = new ProcessInfoParameter[list.size()];
        list.toArray(pars);
        pi.setParameter(pars);
    } //  setParameterFromDB
} //	ProcessInfoUtil
