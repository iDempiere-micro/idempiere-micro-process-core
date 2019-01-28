package org.compiere.process;

import java.util.List;
import org.idempiere.common.base.Service;
import org.idempiere.common.util.CLogger;

/**
 * This is a facade class for the Service Locator. It provides simple access to all core services.
 *
 * @author viola
 * @author hengsin
 * @author Silvano Trinchero, www.freepath.it
 *     <li>IDEMPIERE-3243 added getScriptEngine to manage both registered engines and engines
 *         provided by osgi bundles
 */
public class Core {

    /**
   * @param processId Java class name or equinox extension id
   * @return ProcessCall instance or null if processId not found
   */
  public static ProcessCall getProcess(String processId) {
    List<IProcessFactory> factories =
        Service.Companion.locator().list(IProcessFactory.class).getServices();
    if (factories != null && !factories.isEmpty()) {
      for (IProcessFactory factory : factories) {
        ProcessCall process = factory.newProcessInstance(processId);
        if (process != null) return process;
      }
    }
    return new DefaultProcessFactory().newProcessInstance(processId);
  }
}
