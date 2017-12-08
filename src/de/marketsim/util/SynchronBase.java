package de.marketsim.util;

/**
 * Überschrift:   Market Simulator
 * Beschreibung:
 * Copyright:     Copyright (c) 2004
 * Organisation:
 * @author Xining Wang
 * @version 1.0
 */

public class SynchronBase
{
  private boolean ready = false;
  public SynchronBase()
  {

  }

  public void reset()
  {
    ready = false;
  }

  public synchronized void dowait()
  {
     try
     {
        while ( ! ready )
        {
          this.wait(500);
        }
     }
     catch (Exception ex)
     {

     }
  }

  public synchronized void letsgo()
  {
        this.notifyAll();
        this.ready = true;
  }


}