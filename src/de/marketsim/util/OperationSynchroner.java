package de.marketsim.util;

/**
 * Überschrift:   Market Simulator
 * Beschreibung:
 * Copyright:     Copyright (c) 2004
 * Organisation:
 * @author Xining Wang
 * @version 1.0
 */

public class OperationSynchroner
{

  private static int mAgentNumber = 0;
  private static int mCC = 0;
  private static int mLogoutCC = 0;
  private static SynchronBase synobj = new SynchronBase();

  public OperationSynchroner()
  {
  }

  public static void setAgentNumber(int pNumber)
  {
    mAgentNumber = pNumber;
  }

  public static synchronized  void addOnewait()
  {
    mCC = mCC +1;
  }

  public static void startsynchron()
  {
     addOnewait();
     System.out.print(  " "+mCC+"/"+mAgentNumber+ ".arrived");
     if ( mCC == 1 )
     {
         synobj.reset();
     }
     if ( mCC < mAgentNumber )
     {
         synobj.dowait();
     }
     else
     {
        // this is the last message.
        System.out.println();
        synobj.letsgo();
        mCC = 0;
     }
  }

  public static synchronized void oneagentlogout()
  {
     mLogoutCC=mLogoutCC + 1;
     if ( mLogoutCC == mAgentNumber )
     {
       System.exit(0);
     }
  }

}