package org.compiere.process;

/** @author hengsin */
public interface IProcessFactory {

  /**
   * Create new process instance
   *
   * @param className
   * @return new process instance
   */
  public ProcessCall newProcessInstance(String className);
}
