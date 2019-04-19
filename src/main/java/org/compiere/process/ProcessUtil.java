package org.compiere.process;

import org.compiere.model.IProcessInfo;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.model.Rule;
import org.compiere.rule.MRule;
import org.compiere.util.MsgKt;
import org.idempiere.common.util.CLogger;

import javax.script.ScriptEngine;
import java.math.BigDecimal;
import java.util.logging.Level;

/**
 * @author Low Heng Sin
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * <li>BF [ 1757523 ] Server Processes are using Server's context
 * <li>BF [ 2528297 ] Poor error message on jasper fail
 * <li>BF [ 2530847 ] Report is displayed even if java process fails
 */
public final class ProcessUtil {

    public static final String JASPER_STARTER_CLASS = "org.adempiere.report.jasper.ReportStarter";

    /**
     * Logger
     */
    private static CLogger log = CLogger.getCLogger(ProcessUtil.class);

    private ProcessUtil() {
    }

    /**
     * @param pi
     * @return boolean
     */
    public static boolean startJavaProcess(IProcessInfo pi) {
        return startJavaProcess(pi, true);
    }

    /**
     * @param pi
     * @param managedTrx false if trx is managed by caller
     * @return boolean
     */
    public static boolean startJavaProcess(
            IProcessInfo pi, boolean managedTrx) {
        return startJavaProcess(pi, null);
    }

    public static boolean startJavaProcess(
            IProcessInfo pi, IProcessUI processMonitor) {
        String className = pi.getClassName();
        if (className == null) {
            MProcess proc = new MProcess(pi.getProcessId());
            if (proc.getJasperReport() != null) className = JASPER_STARTER_CLASS;
        }

        ProcessCall process = null;
        // invoke process factory
        process = Core.getProcess(className);
        if (process == null) {
            pi.setSummary("Failed to create new process instance for " + className, true);
            return false;
        }

        return startJavaProcess(pi, processMonitor, process);
    }

    /**
     * @param pi
     * @return boolean
     */
    public static boolean startJavaProcess(

            IProcessInfo pi,
            IProcessUI processMonitor,
            ProcessCall process) {

        if (process == null) {
            pi.setSummary("Failed to create new process instance", true);
            return false;
        }

        boolean success;
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(process.getClass().getClassLoader());
            process.setProcessUI(processMonitor);
            success = process.startProcess(pi);
        } catch (Throwable e) {
            pi.setSummary(MsgKt.getMsg("ProcessError") + " " + e.getLocalizedMessage(), true);
            log.log(Level.SEVERE, pi.getClassName(), e);
            return false;
        } finally {
            Thread.currentThread().setContextClassLoader(cl);
        }
        return success;
    }

    public static boolean startScriptProcess(IProcessInfo pi) {

        String msg;
        boolean success = true;
        try {
            String cmd = pi.getClassName();
            Rule rule = MRule.get(cmd.substring(MRule.SCRIPT_PREFIX.length()));
            if (rule == null) {
                log.log(Level.WARNING, cmd + " not found");
                pi.setSummary("ScriptNotFound", true);
                return false;
            }
            if (!(rule.getEventType().equals(MRule.EVENTTYPE_Process)
                    && rule.getRuleType().equals(MRule.RULETYPE_JSR223ScriptingAPIs))) {
                log.log(Level.WARNING, cmd + " must be of type JSR 223 and event Process");
                pi.setSummary("ScriptNotFound", true);
                return false;
            }

            ScriptEngine engine = rule.getScriptEngine();

            // Window context are    W_
            // Login context  are    G_
            // Method arguments context are A_
            // Parameter context are P_
            // now add the method arguments to the engine
            engine.put(MRule.ARGUMENTS_PREFIX + "Record_ID", pi.getRecordId());
            engine.put(MRule.ARGUMENTS_PREFIX + "AD_Client_ID", pi.getClientId());
            engine.put(MRule.ARGUMENTS_PREFIX + "AD_User_ID", pi.getUserId());
            engine.put(MRule.ARGUMENTS_PREFIX + "AD_PInstance_ID", pi.getPInstanceId());
            engine.put(MRule.ARGUMENTS_PREFIX + "Table_ID", pi.getPInfoTableId());
            // Add process parameters
            IProcessInfoParameter[] para = pi.getParameter();
            if (para == null) {
                ProcessInfoUtil.setParameterFromDB(pi);
                para = pi.getParameter();
            }
            if (para != null) {
                engine.put(MRule.ARGUMENTS_PREFIX + "Parameter", pi.getParameter());
                for (IProcessInfoParameter iProcessInfoParameter : para) {
                    String name = iProcessInfoParameter.getParameterName();
                    if (iProcessInfoParameter.getParameterTo() == null) {
                        Object value = iProcessInfoParameter.getParameter();
                        if (name.endsWith("_ID") && (value instanceof BigDecimal))
                            engine.put(MRule.PARAMETERS_PREFIX + name, ((BigDecimal) value).intValue());
                        else engine.put(MRule.PARAMETERS_PREFIX + name, value);
                    } else {
                        Object value1 = iProcessInfoParameter.getParameter();
                        Object value2 = iProcessInfoParameter.getParameterTo();
                        if (name.endsWith("_ID") && (value1 instanceof BigDecimal))
                            engine.put(MRule.PARAMETERS_PREFIX + name + "1", ((BigDecimal) value1).intValue());
                        else engine.put(MRule.PARAMETERS_PREFIX + name + "1", value1);
                        if (name.endsWith("_ID") && (value2 instanceof BigDecimal))
                            engine.put(MRule.PARAMETERS_PREFIX + name + "2", ((BigDecimal) value2).intValue());
                        else engine.put(MRule.PARAMETERS_PREFIX + name + "2", value2);
                    }
                }
            }
            engine.put(MRule.ARGUMENTS_PREFIX + "ProcessInfo", pi);

            msg = engine.eval(rule.getScript()).toString();
            // transaction should rollback if there are error in process
            if (msg != null && msg.startsWith("@Error@")) success = false;

            //	Parse Variables
            msg = MsgKt.parseTranslation(msg);
            pi.setSummary(msg, !success);

        } catch (Exception e) {
            pi.setSummary("ScriptError", true);
            log.log(Level.SEVERE, pi.getClassName(), e);
            success = false;
        }
        if (!success) {
            throw new Error("not success");
        }
        return success;
    }

}
