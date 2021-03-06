package org.compiere.process;

import org.compiere.model.IProcessInfo;
import org.compiere.model.IProcessInfoLog;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.util.DisplayType;
import org.compiere.util.MsgKt;
import org.idempiere.common.util.Util;
import org.idempiere.icommon.model.PersistentObject;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Process Information (Value Object)
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com
 * @version $Id: ProcessInfo.java,v 1.2 2006/07/30 00:54:44 jjanke Exp $
 * @see FR 1906632
 * http://sourceforge.net/tracker/?func=detail&atid=879335&aid=1906632&group_id=176962
 */
public class ProcessInfo implements Serializable, IProcessInfo {

    /**
     *
     */
    private static final long serialVersionUID = -7810177110347837681L;
    /**
     * Title of the Process/Report
     */
    private String m_Title;
    /**
     * Process ID
     */
    private int m_AD_Process_ID;
    /**
     * Table ID if the Process
     */
    private int m_Table_ID;
    /**
     * Record ID if the Process
     */
    private int m_Record_ID;
    /**
     * User_ID
     */
    private Integer m_AD_User_ID;
    /**
     * Client_ID
     */
    private Integer m_AD_Client_ID;
    /**
     * Class Name
     */
    private String m_ClassName = null;
    /**
     * Process Instance ID
     */
    private int m_AD_PInstance_ID = 0;

    //  -- Optional --
    /**
     * Summary of Execution
     */
    private String m_Summary = "";
    /**
     * Execution had an error
     */
    private boolean m_Error = false;
    /*	General Data Object			*/
    private Serializable m_SerializableObject = null;
    /*	General Data Object			*/
    private transient Object m_TransientObject = null;
    /**
     * Log Info
     */
    private ArrayList<IProcessInfoLog> m_logs = null;
    /**
     * Log Info
     */
    private IProcessInfoParameter[] m_parameter = null;
    // FR 1906632
    private File m_pdf_report = null;
    /**
     * Export
     */
    private boolean m_export = false;
    /**
     * Export File
     */
    private File m_exportFile = null;
    private transient PersistentObject m_po = null;

    /**
     * Constructor
     *
     * @param Title         Title
     * @param AD_Process_ID AD_Process_ID
     * @param Table_ID      AD_Table_ID
     * @param Record_ID     Record_ID
     */
    public ProcessInfo(String Title, int AD_Process_ID, int Table_ID, int Record_ID) {
        setTitle(Title);
        setProcessId(AD_Process_ID);
        setTableId(Table_ID);
        setRecordId(Record_ID);
    } //  ProcessInfo

    /**
     * Constructor
     *
     * @param Title         Title
     * @param AD_Process_ID AD_Process_ID
     */
    public ProcessInfo(String Title, int AD_Process_ID) {
        this(Title, AD_Process_ID, 0, 0);
    } //  ProcessInfo

    /**
     * String representation
     *
     * @return String representation
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("ProcessInfo[");
        sb.append(m_Title).append(",Process_ID=").append(m_AD_Process_ID);
        if (m_AD_PInstance_ID != 0) sb.append(",AD_PInstance_ID=").append(m_AD_PInstance_ID);
        if (m_Record_ID != 0) sb.append(",Record_ID=").append(m_Record_ID);
        if (m_ClassName != null) sb.append(",ClassName=").append(m_ClassName);
        sb.append(",Error=").append(isError());
        if (m_TransientObject != null) sb.append(",Transient=").append(m_TransientObject);
        if (m_SerializableObject != null) sb.append(",Serializable=").append(m_SerializableObject);
        sb.append(",Summary=")
                .append(getSummary())
                .append(",Log=")
                .append(m_logs == null ? 0 : m_logs.size());
        //	.append(getLogInfo(false));
        sb.append("]");
        return sb.toString();
    } //  toString

    /**
     * Method getSummary
     *
     * @return String
     */
    public String getSummary() {
        return Util.cleanAmp(m_Summary);
    } //	getSummary

    /**
     * ************************************************************************ Set Summary
     *
     * @param summary summary (will be translated)
     */
    public void setSummary(String summary) {
        m_Summary = summary;
    } //	setSummary

    /**
     * Method setSummary
     *
     * @param translatedSummary String
     * @param error             boolean
     */
    @Override
    public void setSummary(String translatedSummary, boolean error) {
        setSummary(translatedSummary);
        setError(error);
    } //	setSummary

    /**
     * Method isError
     *
     * @return boolean
     */
    public boolean isError() {
        return m_Error;
    } //	isError

    /**
     * Method setError
     *
     * @param error boolean
     */
    public void setError(boolean error) {
        m_Error = error;
    } //	setError

    /**
     * Batch
     *
     * @param batch true if batch processing
     */
    public void setIsBatch(boolean batch) {
    } //	setTimeout

    /**
     * Set Log of Process.
     *
     * <pre>
     *  - Translated Process Message
     *  - List of log entries
     *      Date - Number - Msg
     *  </pre>
     *
     * @param html if true with HTML markup
     * @return Log Info
     */
    public String getLogInfo(boolean html) {
        if (m_logs == null) return "";
        //
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat dateFormat = DisplayType.getDateFormat(DisplayType.Date);
        if (html) sb.append("<table width=\"100%\" border=\"1\" cellspacing=\"0\" cellpadding=\"2\">");
        //
        for (int i = 0; i < m_logs.size(); i++) {
            if (html) sb.append("<tr>");
            else if (i > 0) sb.append("\n");
            //
            IProcessInfoLog log = m_logs.get(i);
            /**
             * if (log.getPId() != 0) sb.append(html ? "<td>" : "") .append(log.getPId()) .append(html ?
             * "</td>" : " \t"); *
             */
            //
            if (log.getPDate() != null)
                sb.append(html ? "<td>" : "")
                        .append(dateFormat.format(log.getPDate()))
                        .append(html ? "</td>" : " \t");
            //
            if (log.getPNumber() != null)
                sb.append(html ? "<td>" : "").append(log.getPNumber()).append(html ? "</td>" : " \t");
            //
            if (log.getPMsg() != null)
                sb.append(html ? "<td>" : "")
                        .append(MsgKt.parseTranslation(log.getPMsg()))
                        .append(html ? "</td>" : "");
            //
            if (html) sb.append("</tr>");
        }
        if (html) sb.append("</table>");
        return sb.toString();
    } //	getLogInfo

    /**
     * Get ASCII Log Info
     *
     * @return Log Info
     */
    public String getLogInfo() {
        return getLogInfo(false);
    } //	getLogInfo

    /**
     * Method getProcessInstance_ID
     *
     * @return int
     */
    public int getPInstanceId() {
        return m_AD_PInstance_ID;
    }

    /**
     * Method setAD_PInstance_ID
     *
     * @param AD_PInstance_ID int
     */
    public void setProcessInstanceId(int AD_PInstance_ID) {
        m_AD_PInstance_ID = AD_PInstance_ID;
    }

    /**
     * Method getProcessId
     *
     * @return int
     */
    public int getProcessId() {
        return m_AD_Process_ID;
    }

    /**
     * Method setAD_Process_ID
     *
     * @param AD_Process_ID int
     */
    public void setProcessId(int AD_Process_ID) {
        m_AD_Process_ID = AD_Process_ID;
    }

    /**
     * Method getClassName
     *
     * @return String or null
     */
    public String getClassName() {
        return m_ClassName;
    }

    /**
     * Method setClassName
     *
     * @param ClassName String
     */
    public void setClassName(String ClassName) {
        m_ClassName = ClassName;
        if (m_ClassName != null && m_ClassName.length() == 0) m_ClassName = null;
    } //	setClassName

    /**
     * Method setEstSeconds
     *
     * @param EstSeconds int
     */
    public void setEstSeconds(int EstSeconds) {
    }

    /**
     * Method getTable_ID
     *
     * @return int
     */
    public int getPInfoTableId() {
        return m_Table_ID;
    }

    /**
     * Method setTable_ID
     *
     * @param AD_Table_ID int
     */
    public void setTableId(int AD_Table_ID) {
        m_Table_ID = AD_Table_ID;
    }

    /**
     * Method getRecordId
     *
     * @return int
     */
    public int getRecordId() {
        return m_Record_ID;
    }

    /**
     * Method setRecordId
     *
     * @param Record_ID int
     */
    public void setRecordId(int Record_ID) {
        m_Record_ID = Record_ID;
    }

    /**
     * Method getTitle
     *
     * @return String
     */
    public String getTitle() {
        return m_Title;
    }

    /**
     * Method setTitle
     *
     * @param Title String
     */
    public void setTitle(String Title) {
        m_Title = Title;
    } //	setTitle

    /**
     * Method setClientId
     *
     * @param AD_Client_ID int
     */
    public void setADClientID(int AD_Client_ID) {
        m_AD_Client_ID = new Integer(AD_Client_ID);
    }

    /**
     * Method getClientId
     *
     * @return Integer
     */
    public Integer getClientId() {
        return m_AD_Client_ID;
    }

    /**
     * Method getUserId
     *
     * @return Integer
     */
    public Integer getUserId() {
        return m_AD_User_ID;
    }

    /**
     * Method setUserId
     *
     * @param AD_User_ID int
     */
    public void setUserId(int AD_User_ID) {
        m_AD_User_ID = new Integer(AD_User_ID);
    }

    /**
     * ************************************************************************ Get Parameter
     *
     * @return Parameter Array
     */
    public IProcessInfoParameter[] getParameter() {
        return m_parameter;
    } //	getParameter

    /**
     * Set Parameter
     *
     * @param parameter Parameter Array
     */
    public void setParameter(IProcessInfoParameter[] parameter) {
        m_parameter = parameter;
    } //	setParameter

    public void addLog(
            int Log_ID,
            int P_ID,
            Timestamp P_Date,
            BigDecimal P_Number,
            String P_Msg,
            int tableId,
            int recordId) {
        addLog(new ProcessInfoLog(Log_ID, P_ID, P_Date, P_Number, P_Msg, tableId, recordId));
    }

    public void addLog(
            int P_ID, Timestamp P_Date, BigDecimal P_Number, String P_Msg, int tableId, int recordId) {
        addLog(new ProcessInfoLog(P_ID, P_Date, P_Number, P_Msg, tableId, recordId));
    }

    /**
     * Add to Log
     *
     * @param P_ID     Process ID
     * @param P_Date   Process Date
     * @param P_Number Process Number
     * @param P_Msg    Process Message
     */
    public void addLog(int P_ID, Timestamp P_Date, BigDecimal P_Number, String P_Msg) {
        addLog(new ProcessInfoLog(P_ID, P_Date, P_Number, P_Msg));
    } //	addLog

    /**
     * Add to Log
     *
     * @param logEntry log entry
     */
    public void addLog(IProcessInfoLog logEntry) {
        if (logEntry == null) return;
        if (m_logs == null) m_logs = new ArrayList<IProcessInfoLog>();
        m_logs.add(logEntry);
    } //	addLog

    /**
     * Method getLogs
     *
     * @return ProcessInfoLog[]
     */
    public ProcessInfoLog[] getLogs() {
        if (m_logs == null) return null;
        ProcessInfoLog[] logs = new ProcessInfoLog[m_logs.size()];
        m_logs.toArray(logs);
        return logs;
    } //	getLogs

    /**
     * Method setLogList
     *
     * @param logs ArrayList
     */
    public void setLogList(ArrayList<IProcessInfoLog> logs) {
        m_logs = logs;
    }

    /**
     * Set transaction name from this process
     *
     * @param trxName
     */
    public void setTransactionName(String trxName) {
    }

    /**
     * Set print preview flag, only relevant if this is a reporting process
     *
     * @param b
     */
    public void setPrintPreview(boolean b) {
    }

    // FR 1906632

    /**
     * Set is this a reporting process
     *
     * @param f
     */
    public void setReportingProcess(boolean f) {
    }

    /**
     * Get PDF file generate to Jasper Report
     */
    public File getPDFReport() {
        return m_pdf_report;
    }

    /**
     * Is this a export or print process?
     *
     * @return
     */
    public boolean isExport() {
        return m_export;
    }

    /**
     * Get Export File
     *
     * @return
     */
    public File getExportFile() {
        return m_exportFile;
    }

    public PersistentObject getPO() {
        return m_po;
    }

    public void setPO(PersistentObject po) {
        m_po = po;
    }

} //  ProcessInfo
