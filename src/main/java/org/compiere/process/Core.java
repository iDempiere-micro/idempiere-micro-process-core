package org.compiere.process;

/**
 * This is a facade class for the Service Locator. It provides simple access to all core services.
 *
 * @author viola
 * @author hengsin
 * @author Silvano Trinchero, www.freepath.it
 * <li>IDEMPIERE-3243 added getScriptEngine to manage both registered engines and engines
 * provided by osgi bundles
 */
public class Core {

    /**
     * @param processId Java class name or equinox extension id
     * @return ProcessCall instance or null if processId not found
     */
    public static ProcessCall getProcess(String processId) {
        return new DefaultProcessFactory().newProcessInstance(processId);
    }
}
