package org.compiere.process;

import org.compiere.model.IProcessInfo;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.util.MsgKt;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.executeUpdate;
import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * Server Process Template
 *
 * @author Jorg Janke
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * <li>FR [ 1646891 ] SvrProcess - post process support
 * <li>BF [ 1877935 ] SvrProcess.process should catch all throwables
 * <li>FR [ 1877937 ] SvrProcess: added commitEx method
 * <li>BF [ 1878743 ] SvrProcess.getUserId
 * <li>BF [ 1935093 ] SvrProcess.unlock() is setting invalid result
 * <li>FR [ 2788006 ] SvrProcess: change access to some methods
 * https://sourceforge.net/tracker/?func=detail&aid=2788006&group_id=176962&atid=879335
 * @version $Id: SvrProcess.java,v 1.4 2006/08/10 01:00:44 jjanke Exp $
 */
public abstract class SvrProcess implements ProcessCall {
    public static final String PROCESS_INFO_CTX_KEY = "ProcessInfo";
    public static final String PROCESS_UI_CTX_KEY = "ProcessUI";
    /**
     * Common Error Message
     */
    protected static String MSG_SaveErrorRowNotFound = "@SaveErrorRowNotFound@";
    /**
     * Logger
     */
    protected CLogger log = CLogger.getCLogger(getClass());
    protected IProcessUI processUI;
    private List<ProcessInfoLog> listEntryLog;
    private IProcessInfo m_pi;

    /**
     * Server Process. Note that the class is initiated by startProcess.
     */
    public SvrProcess() {
        //	Env.ZERO.divide(Env.ZERO);
    } //  SvrProcess

    /**
     * Add log to buffer, only process total success, flush buffer
     *
     * @param id
     * @param date
     * @param number
     * @param msg
     * @param tableId
     * @param recordId
     */
    public void addBufferLog(
            int id, Timestamp date, BigDecimal number, String msg, int tableId, int recordId) {
        ProcessInfoLog entryLog = new ProcessInfoLog(id, date, number, msg, tableId, recordId);

        if (listEntryLog == null) listEntryLog = new ArrayList<ProcessInfoLog>();

        listEntryLog.add(entryLog);
    }

    /**
     * Start the process. Calls the abstract methods <code>process</code>. It should only return
     * false, if the function could not be performed as this causes the process to abort.
     *
     * @param pi Process Info
     * @return true if the next process should be performed
     * @see org.compiere.process.ProcessCall#startProcess(Properties, ProcessInfo, Trx)
     */
    public final boolean startProcess(IProcessInfo pi) {
        //  Preparation
        m_pi = pi;
        // ***	Trx
        //
        ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        ClassLoader processLoader = getClass().getClassLoader();
        try {
            if (processLoader != contextLoader) {
                Thread.currentThread().setContextClassLoader(processLoader);
            }
            lock();

            try {
                process();
            } finally {
                unlock();

                // outside transaction processing [ teo_sarca, 1646891 ]
                postProcess(!m_pi.isError());

                Thread.currentThread().setContextClassLoader(contextLoader);
            }
        } finally {
            if (processLoader != contextLoader) {
                Thread.currentThread().setContextClassLoader(contextLoader);
            }
        }

        return !m_pi.isError();
    } //  startProcess

    /**
     * ************************************************************************ Process
     *
     * @return true if success
     */
    private boolean process() {
        String msg = null;
        boolean success = true;
        try {
            prepare();
            msg = doIt();
        } catch (Throwable e) {
            msg = e.getLocalizedMessage();
            if (msg == null) msg = e.toString();
            if (e.getCause() != null) log.log(Level.SEVERE, msg, e.getCause());
            else log.log(Level.SEVERE, msg, e);
            success = false;
            //	throw new RuntimeException(e);
        }

        // transaction should rollback if there are error in process
        if (msg != null && msg.startsWith("@Error@")) success = false;

        if (success) flushBufferLog();

        //	Parse Variables
        msg = MsgKt.parseTranslation(msg);
        m_pi.setSummary(msg, !success);

        return success;
    } //  process

    /**
     * Prepare - e.g., get Parameters. <code>
     * ProcessInfoParameter[] para = getParameter();
     * for (int i = 0; i < para.length; i++)
     * {
     * String name = para[i].getParameterName();
     * if (para[i].getParameter() == null)
     * ;
     * else if (name.equals("A_Asset_Group_ID"))
     * p_A_Asset_Group_ID = para[i].getParameterAsInt();
     * else if (name.equals("GuaranteeDate"))
     * p_GuaranteeDate = (Timestamp)para[i].getParameter();
     * else if (name.equals("AttachAsset"))
     * p_AttachAsset = "Y".equals(para[i].getParameter());
     * else
     * log.log(Level.SEVERE, "Unknown Parameter: " + name);
     * }
     * </code>
     */
    protected abstract void prepare();

    /**
     * Perform process.
     *
     * @return Message (variables are parsed)
     * @throws Exception if not successful e.g. throw new AdempiereUserError
     *                   ("@FillMandatory@ @C_BankAccount_ID@");
     */
    protected abstract String doIt() throws Exception;

    /**
     * Post process actions (outside trx). Please note that at this point the transaction is committed
     * so you can't rollback. This method is useful if you need to do some custom work when the
     * process complete the work (e.g. open some windows).
     *
     * @param success true if the process was success
     * @since 3.1.4
     */
    protected void postProcess(boolean success) {
    }


    /**
     * ************************************************************************ Get Process Info
     *
     * @return Process Info
     */
    public IProcessInfo getProcessInfo() {
        return m_pi;
    } //  getProcessInfo

    /**
     * Get Name/Title
     *
     * @return Name
     */
    protected String getName() {
        return m_pi.getTitle();
    } //  getName

    /**
     * Get Process Instance
     *
     * @return Process Instance
     */
    protected int getProcessInstanceId() {
        return m_pi.getPInstanceId();
    } //  getProcessInstance_ID

    /**
     * Get Table_ID
     *
     * @return AD_Table_ID
     */
    protected int getTableId() {
        return m_pi.getPInfoTableId();
    } //  getRecordId

    /**
     * Get Record_ID
     *
     * @return Record_ID
     */
    protected int getRecordId() {
        return m_pi.getRecordId();
    } //  getRecordId

    /**
     * Get AD_User_ID
     *
     * @return AD_User_ID of Process owner or -1 if not found
     */
    protected int getUserId() {
        if (m_pi.getUserId() == null || m_pi.getClientId() == null) {
            String sql = "SELECT AD_User_ID, AD_Client_ID FROM AD_PInstance WHERE AD_PInstance_ID=?";
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                pstmt = prepareStatement(sql);
                pstmt.setInt(1, m_pi.getPInstanceId());
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    m_pi.setUserId(rs.getInt(1));
                    m_pi.setADClientID(rs.getInt(2));
                }
            } catch (SQLException e) {
                log.log(Level.SEVERE, sql, e);
            } finally {
                rs = null;
                pstmt = null;
            }
        }
        if (m_pi.getUserId() == null) return -1;
        return m_pi.getUserId();
    } //  getUserId

    /**
     * Get AD_User_ID
     *
     * @return AD_User_ID of Process owner
     */
    protected int getClientId() {
        if (m_pi.getClientId() == null) {
            getUserId(); //	sets also Client
            if (m_pi.getClientId() == null) return 0;
        }
        return m_pi.getClientId();
    } //	getClientId

    /**
     * ************************************************************************ Get Parameter
     *
     * @return parameter
     */
    protected IProcessInfoParameter[] getParameter() {
        IProcessInfoParameter[] retValue = m_pi.getParameter();
    /*if (retValue == null) { TODO DAP allow setting parameters from DB
      ProcessInfoUtil.setParameterFromDB(m_pi);
      retValue = m_pi.getParameter();
    }*/
        if (retValue == null) {
            retValue = new IProcessInfoParameter[]{};
        }
        return retValue;
    } //	getParameter

    /**
     * ************************************************************************ Add Log Entry with
     * table name
     */
    public void addLog(
            int id, Timestamp date, BigDecimal number, String msg, int tableId, int recordId) {
        if (m_pi != null) m_pi.addLog(id, date, number, msg, tableId, recordId);

        if (log.isLoggable(Level.INFO))
            log.info(
                    id + " - " + date + " - " + number + " - " + msg + " - " + tableId + " - " + recordId);
    } //	addLog

    /**
     * ************************************************************************ Add Log Entry
     *
     * @param date   date or null
     * @param id     record id or 0
     * @param number number or null
     * @param msg    message or null
     */
    public void addLog(int id, Timestamp date, BigDecimal number, String msg) {
        if (m_pi != null) m_pi.addLog(id, date, number, msg);
        if (log.isLoggable(Level.INFO)) log.info(id + " - " + date + " - " + number + " - " + msg);
    } //	addLog

    /**
     * Add Log
     *
     * @param msg message
     */
    public void addLog(String msg) {
        if (msg != null) addLog(0, null, null, msg);
    } //	addLog

    private void flushBufferLog() {
        if (listEntryLog == null) return;

        for (ProcessInfoLog entryLog : listEntryLog) {
            if (m_pi != null) m_pi.addLog(entryLog);
            if (log.isLoggable(Level.INFO))
                log.info(
                        entryLog.getPId()
                                + " - "
                                + entryLog.getPDate()
                                + " - "
                                + entryLog.getPNumber()
                                + " - "
                                + entryLog.getPMsg()
                                + " - "
                                + entryLog.getPTableId()
                                + " - "
                                + entryLog.getRecordId());
        }
    }

    /**
     * ************************************************************************ Lock Process Instance
     */
    private void lock() {
        if (log.isLoggable(Level.FINE)) log.fine("AD_PInstance_ID=" + m_pi.getPInstanceId());
        try {
            executeUpdate(
                    "UPDATE AD_PInstance SET IsProcessing='Y' WHERE AD_PInstance_ID="
                            + m_pi.getPInstanceId()
            ); //	outside trx
        } catch (Exception e) {
            log.severe("lock() - " + e.getLocalizedMessage());
        }
    } //  lock

    /**
     * Unlock Process Instance. Update Process Instance DB and write option return message
     */
    private void unlock() {
        boolean noContext = Env.getCtx().isEmpty() && Env.getCtx().getProperty("#AD_Client_ID") == null;
        try {
            // save logging info even if context is lost
            if (noContext) Env.getCtx().put("#AD_Client_ID", m_pi.getClientId());

            MPInstance mpi = new MPInstance(m_pi.getPInstanceId(), null);
            if (mpi.getId() == 0) {
                log.log(Level.SEVERE, "Did not find PInstance " + m_pi.getPInstanceId());
                return;
            }
            mpi.setIsProcessing(false);
            mpi.setResult(!m_pi.isError());
            mpi.setErrorMsg(m_pi.getSummary());
            mpi.saveEx();
            if (log.isLoggable(Level.FINE)) log.fine(mpi.toString());

            ProcessInfoUtil.saveLogToDB(m_pi);
        } catch (Exception e) {
            log.severe("unlock() - " + e.getLocalizedMessage());
        } finally {
            if (noContext) Env.getCtx().remove("#AD_Client_ID");
        }
    } //  unlock

    @Override
    public void setProcessUI(IProcessUI monitor) {
        processUI = monitor;
    }

    /**
     * publish status update message
     *
     * @param message
     */
    protected void statusUpdate(String message) {
        if (processUI != null) {
            processUI.statusUpdate(message);
        }
    }
} //  SvrProcess
