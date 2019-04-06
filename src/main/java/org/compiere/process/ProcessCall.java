package org.compiere.process;

import org.compiere.model.IProcessInfo;

/**
 * Interface for user started processes.
 *
 * <p>ProcessCtrl.startClass creates the Object and calls startProcess before executing the optional
 * SQL procedure and Report.
 *
 * <p>see ProcessCtl#startClass
 *
 * @author Jorg Janke
 * @version $Id: ProcessCall.java,v 1.3 2006/07/30 00:54:44 jjanke Exp $
 */
public interface ProcessCall {
    /**
     * Start the process. Called when pressing the ... button in ... It should only return false, if
     * the function could not be performed as this causes the process to abort.
     *
     * @param ctx Context
     * @param pi  Process Info
     * @param trx transaction
     * @return true if the next process should be performed
     */
    boolean startProcess(IProcessInfo pi);

    /**
     * @param processMonitor
     */
    void setProcessUI(IProcessUI processUI);
} //  ProcessCall
