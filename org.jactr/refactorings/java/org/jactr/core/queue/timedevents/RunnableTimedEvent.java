package org.jactr.core.queue.timedevents;

/*
 * default logging
 */
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RunnableTimedEvent extends AbstractTimedEvent
{
  /**
   * Logger definition
   */
  static private final transient Log LOGGER = LogFactory
                                                .getLog(RunnableTimedEvent.class);

  private final Runnable _onFire;
  private final Runnable _onAbort;
  
  
  public RunnableTimedEvent(double fireAt, Runnable onFire)
  {
    this(fireAt, onFire, null);
  }
  
  public RunnableTimedEvent(double fireAt, Runnable onFire, Runnable onAbort)
  {
    super(fireAt, fireAt);
    _onFire = onFire;
    _onAbort = onAbort;
  }
  
  public void fire(double currentTime)
  {
    super.fire(currentTime);
    if(_onFire!=null)
      _onFire.run();
  }
  
  public void abort()
  {
    super.abort();
    if(_onAbort!=null)
      _onAbort.run();
  }
}
