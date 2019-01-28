package org.compiere.process;

import java.io.File;

/** @author hengsin */
public interface IProcessUI {

    /**
   * Provide status feedback to user
   *
   * @param message
   */
  public void statusUpdate(String message);

}
