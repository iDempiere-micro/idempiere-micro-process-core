package org.compiere.process;

import java.io.File;

/** @author hengsin */
public interface IProcessUI {

  /**
   * Lock User Interface. Called from the Worker before processing
   *
   * @param pi process info
   */
  public void lockUI(ProcessInfo pi);

  /**
   * Unlock User Interface. Called from the Worker when processing is done
   *
   * @param pi process info
   */
  public void unlockUI(ProcessInfo pi);

  /**
   * Is the UI locked
   *
   * @return true, if UI is locked
   */
  public boolean isUILocked();

  /**
   * Provide status feedback to user
   *
   * @param message
   */
  public void statusUpdate(String message);

  /**
   * Prompt for user input.
   *
   * <p>Example Usage in process:
   *
   * <pre>
   * 	    final StringBuffer answer = new StringBuffer();
   * 	aProcessMonitor.ask(adMessage, new Callback<String>() {
   * 		@Override
   * 		public void onCallback(String result) {
   * 			answer.append(result != null ? result : "-");
   * 		}
   * 	});
   *
   *      //wait for answer
   * 	while (answer.length() == 0) {
   * 		try {
   * 			Thread.sleep(200);
   * 		} catch (InterruptedException e) {}
   * 	}
   *
   *      //process answer from user
   *      ...
   * </pre>
   *
   * @param message
   * @param callback
   */
  public void ask(String message, Callback<Boolean> callback);

  public void askForInput(String message, Callback<String> callback);

  /**
   * add to list of file available for download after process end
   *
   * @param file
   */
  public void download(File file);
}
