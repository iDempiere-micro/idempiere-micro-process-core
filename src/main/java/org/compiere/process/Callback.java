package org.compiere.process;

/**
 * @author hengsin
 * @param <T>
 */
public interface Callback<T> {

  public void onCallback(T result);
}
