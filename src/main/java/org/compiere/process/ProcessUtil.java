package org.compiere.process;

import org.compiere.model.IProcessInfo;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.rule.MRule;
import org.compiere.util.Msg;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import javax.script.ScriptEngine;
import java.math.BigDecimal;
import java.util.Properties;
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

    @Deprecated
    public static boolean startJavaProcess(ProcessInfo pi) {
        return startJavaProcess(Env.getCtx(), pi);
    }

    /**
     * @param ctx
     * @param pi
     * @param trx
     * @return boolean
     */
    public static boolean startJavaProcess(Properties ctx, IProcessInfo pi) {
        return startJavaProcess(ctx, pi, true);
    }

    /**
     * @param ctx
     * @param pi
     * @param trx
     * @param managedTrx false if trx is managed by caller
     * @return boolean
     */
    public static boolean startJavaProcess(
            Properties ctx, IProcessInfo pi, boolean managedTrx) {
        return startJavaProcess(ctx, pi, managedTrx, null);
    }

    public static boolean startJavaProcess(
            Properties ctx, IProcessInfo pi, boolean managedTrx, IProcessUI processMonitor) {
        String className = pi.getClassName();
        if (className == null) {
            MProcess proc = new MProcess(ctx, pi.getAD_Process_ID());
            if (proc.getJasperReport() != null) className = JASPER_STARTER_CLASS;
        }

        ProcessCall process = null;
        // invoke process factory
        process = Core.getProcess(className);
        if (process == null) {
            pi.setSummary("Failed to create new process instance for " + className, true);
            return false;
        }

        return startJavaProcess(ctx, pi, managedTrx, processMonitor, process);
    }

    /**
     * @param ctx
     * @param pi
     * @param trx
     * @param managedTrx false if trx is managed by caller
     * @return boolean
     */
    public static boolean startJavaProcess(
            Properties ctx,
            IProcessInfo pi,
            boolean managedTrx,
            IProcessUI processMonitor,
            ProcessCall process) {

        if (process == null) {
            pi.setSummary("Failed to create new process instance", true);
            return false;
        }

        boolean success = false;
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(process.getClass().getClassLoader());
            process.setProcessUI(processMonitor);
            success = process.startProcess(ctx, pi);
        } catch (Throwable e) {
            pi.setSummary(Msg.getMsg(Env.getCtx(), "ProcessError") + " " + e.getLocalizedMessage(), true);
            log.log(Level.SEVERE, pi.getClassName(), e);
            success = false;
            return false;
        } finally {
            Thread.currentThread().setContextClassLoader(cl);
        }
        return success;
    }

    public static boolean startScriptProcess(Properties ctx, IProcessInfo pi) {
        String msg = null;
        boolean success = true;
        try {
            String cmd = pi.getClassName();
            MRule rule = MRule.get(ctx, cmd.substring(MRule.SCRIPT_PREFIX.length()));
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
            MRule.setContext(engine, ctx, 0); // no window
            // now add the method arguments to the engine
            engine.put(MRule.ARGUMENTS_PREFIX + "Ctx", ctx);
            engine.put(MRule.ARGUMENTS_PREFIX + "Record_ID", pi.getRecord_ID());
            engine.put(MRule.ARGUMENTS_PREFIX + "AD_Client_ID", pi.getClientId());
            engine.put(MRule.ARGUMENTS_PREFIX + "AD_User_ID", pi.getAD_User_ID());
            engine.put(MRule.ARGUMENTS_PREFIX + "AD_PInstance_ID", pi.getAD_PInstance_ID());
            engine.put(MRule.ARGUMENTS_PREFIX + "Table_ID", pi.getTable_ID());
            // Add process parameters
            IProcessInfoParameter[] para = pi.getParameter();
            if (para == null) {
                ProcessInfoUtil.setParameterFromDB(pi);
                para = pi.getParameter();
            }
            if (para != null) {
                engine.put(MRule.ARGUMENTS_PREFIX + "Parameter", pi.getParameter());
                for (int i = 0; i < para.length; i++) {
                    String name = para[i].getParameterName();
                    if (para[i].getParameter_To() == null) {
                        Object value = para[i].getParameter();
                        if (name.endsWith("_ID") && (value instanceof BigDecimal))
                            engine.put(MRule.PARAMETERS_PREFIX + name, ((BigDecimal) value).intValue());
                        else engine.put(MRule.PARAMETERS_PREFIX + name, value);
                    } else {
                        Object value1 = para[i].getParameter();
                        Object value2 = para[i].getParameter_To();
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
            msg = Msg.parseTranslation(ctx, msg);
            pi.setSummary(msg, !success);

        } catch (Exception e) {
            pi.setSummary("ScriptError", true);
            log.log(Level.SEVERE, pi.getClassName(), e);
            success = false;
        }
        if (success) {
        } else {
            throw new Error("not success");
        }
        return success;
    }

}
