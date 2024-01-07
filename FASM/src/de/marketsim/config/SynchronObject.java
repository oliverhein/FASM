package de.marketsim.config;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class SynchronObject implements java.io.Serializable
{

  public SynchronObject()
  {
  }

  public synchronized  void dowait()
  {
    try
    {
      this.wait();
    }
    catch (Exception ex)
    {

    }
  }

  public synchronized  void dowait(int pWaitTime)
  {
    try
    {
      this.wait( pWaitTime );
    }
    catch (Exception ex)
    {

    }
  }



  public synchronized void letsgo()
  {
      this.notifyAll();
  }

}