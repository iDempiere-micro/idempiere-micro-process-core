package org.compiere.process;

import org.compiere.model.I_AD_PInstance_Para;
import org.compiere.orm.MRole;
import org.compiere.orm.Query;
import org.compiere.util.Msg;
import org.idempiere.common.util.Env;
import org.idempiere.common.util.Language;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.executeUpdate;
import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * Process Instance Model
 *
 * @author Jorg Janke
 * @author Teo Sarca, www.arhipac.ro
 * <li>FR [ 2818478 ] Introduce MPInstance.createParameter helper method
 * https://sourceforge.net/tracker/?func=detail&aid=2818478&group_id=176962&atid=879335
 * @version $Id: MPInstance.java,v 1.3 2006/07/30 00:58:36 jjanke Exp $
 */
public class MPInstance extends X_AD_PInstance {
    /**
     * Result OK = 1
     */
    public static final int RESULT_OK = 1;
    /**
     * Result FALSE = 0
     */
    public static final int RESULT_ERROR = 0;
    /**
     *
     */
    private static final long serialVersionUID = 558778359873793799L;
    /**
     * Parameters
     */
    private MPInstancePara[] m_parameters = null;
    /**
     * Log Entries
     */
    private ArrayList<MPInstanceLog> m_log = new ArrayList<MPInstanceLog>();

    /**
     * Standard Constructor
     *
     * @param ctx             context
     * @param AD_PInstance_ID instance or 0
     * @param ignored         no transaction support
     */
    public MPInstance(Properties ctx, int AD_PInstance_ID, String ignored) {
        super(ctx, AD_PInstance_ID);
        //	New Process
        if (AD_PInstance_ID == 0) {
            //	setAD_Process_ID (0);	//	parent
            //	setRecord_ID (0);
            setIsProcessing(false);
        }
    } //	MPInstance

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param ignored no transaction support
     */
    public MPInstance(Properties ctx, ResultSet rs, String ignored) {
        super(ctx, rs);
    } //	MPInstance

    /**
     * Create Process Instance from Process and create parameters
     *
     * @param process   process
     * @param Record_ID Record
     */
    public MPInstance(MProcess process, int Record_ID) {
        this(process.getCtx(), 0, null);
        setAD_Process_ID(process.getAD_Process_ID());
        setRecord_ID(Record_ID);
        setAD_User_ID(Env.getAD_User_ID(process.getCtx()));
        if (!save()) //	need to save for parameters
            throw new IllegalArgumentException("Cannot Save");
        //	Set Parameter Base Info
        MProcessPara[] para = process.getParameters();
        for (int i = 0; i < para.length; i++) {
            MPInstancePara pip = new MPInstancePara(this, para[i].getSeqNo());
            pip.setParameterName(para[i].getColumnName());
            pip.setInfo(para[i].getName());
            pip.saveEx();
        }
    } //	MPInstance

    /**
     * New Constructor
     *
     * @param ctx           context
     * @param AD_Process_ID Process ID
     * @param Record_ID     record
     */
    public MPInstance(Properties ctx, int AD_Process_ID, int Record_ID) {
        this(ctx, 0, null);
        setAD_Process_ID(AD_Process_ID);
        setRecord_ID(Record_ID);
        setAD_User_ID(Env.getAD_User_ID(ctx));
        setIsProcessing(false);
    } //	MPInstance

    /**
     * Get Parameters
     *
     * @return parameter array
     */
    public MPInstancePara[] getParameters() {
        if (m_parameters != null) return m_parameters;
        // FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
        final String whereClause = "AD_PInstance_ID=?";
        List<MPInstancePara> list =
                new Query(
                        getCtx(),
                        I_AD_PInstance_Para.Table_Name,
                        whereClause
                ) // @TODO: Review implications of using transaction
                        .setParameters(getAD_PInstance_ID())
                        .setOrderBy("SeqNo, ParameterName")
                        .list();

        //
        m_parameters = new MPInstancePara[list.size()];
        list.toArray(m_parameters);
        return m_parameters;
    } //	getParameters

    /**
     * Get Logs
     *
     * @return array of logs
     */
    public MPInstanceLog[] getLog() {
        //	load it from DB
        m_log.clear();
        String sql = "SELECT * FROM AD_PInstance_Log WHERE AD_PInstance_ID=? ORDER BY Log_ID";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, getAD_PInstance_ID());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                m_log.add(new MPInstanceLog(rs));
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, sql, e);
        } finally {
            rs = null;
            pstmt = null;
        }
        MPInstanceLog[] retValue = new MPInstanceLog[m_log.size()];
        m_log.toArray(retValue);
        return retValue;
    } //	getLog

    /**
     * Set AD_Process_ID. Check Role if process can be performed
     *
     * @param AD_Process_ID process
     */
    public void setAD_Process_ID(int AD_Process_ID) {
        int AD_Role_ID = Env.getAD_Role_ID(getCtx());
        if (AD_Role_ID != 0) {
            MRole role = MRole.get(getCtx(), AD_Role_ID);
            Boolean access = role.getProcessAccess(AD_Process_ID);
            if (access == null || !access.booleanValue()) {
                MProcess proc = MProcess.get(getCtx(), AD_Process_ID);
                StringBuilder procMsg = new StringBuilder("[");
                if (!Language.isBaseLanguage(Env.getADLanguage(getCtx()))) {
                    procMsg.append(proc.get_Translation("Name")).append(" / ");
                }
                procMsg.append(proc.getName()).append("]");
                throw new IllegalStateException(
                        Msg.getMsg(
                                getCtx(),
                                "CannotAccessProcess",
                                new Object[]{procMsg.toString(), role.getName()}));
            }
        }
        super.setAD_Process_ID(AD_Process_ID);
    } //	setAD_Process_ID

    /**
     * Set Record ID. direct internal record ID
     *
     * @param Record_ID record
     */
    public void setRecord_ID(int Record_ID) {
        if (Record_ID < 0) {
            if (log.isLoggable(Level.INFO)) log.info("Set to 0 from " + Record_ID);
            Record_ID = 0;
        }
        set_ValueNoCheck("Record_ID", new Integer(Record_ID));
    } //	setRecord_ID

    /**
     * String Representation
     *
     * @return info
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer sb = new StringBuffer("MPInstance[").append(getId()).append(",OK=").append(isOK());
        String msg = getErrorMsg();
        if (msg != null && msg.length() > 0) sb.append(msg);
        sb.append("]");
        return sb.toString();
    } //	toString

    /**
     * Dump Log
     */
    public void log() {
        if (log.isLoggable(Level.INFO)) {
            log.info(toString());
            MPInstanceLog[] pil = getLog();
            for (int i = 0; i < pil.length; i++) log.info(i + "=" + pil[i]);
        }
    } //	log

    /**
     * Is it OK
     *
     * @return Result == OK
     */
    public boolean isOK() {
        return getResult() == RESULT_OK;
    } //	isOK

    /**
     * Set Result
     *
     * @param ok
     */
    public void setResult(boolean ok) {
        super.setResult(ok ? RESULT_OK : RESULT_ERROR);
    } //	setResult

    /**
     * After Save
     *
     * @param newRecord new
     * @param success   success
     * @return success
     */
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (!success) return success;
        //	Update Statistics
        if (!newRecord && !isProcessing() && is_ValueChanged("IsProcessing")) {
            long ms = System.currentTimeMillis() - getCreated().getTime();
            int seconds = (int) (ms / 1000);
            if (seconds < 1) seconds = 1;
            String updsql =
                    "UPDATE AD_Process SET Statistic_Count=Statistic_Count+1, Statistic_Seconds=Statistic_Seconds+? WHERE AD_Process_ID=?";
            int no =
                    executeUpdate(
                            updsql, new Object[]{seconds, getAD_Process_ID()}); // out of trx
            if (no == 1) {
                if (log.isLoggable(Level.FINE))
                    log.fine("afterSave - Process Statistics updated Sec=" + seconds);
            } else {
                log.warning("afterSave - Process Statistics not updated");
            }
        }
        return success;
    } //	afterSave

} //	MPInstance
