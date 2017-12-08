package de.marketsim.util;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.*;

import de.marketsim.message.FriendStatus;

public class BestOperatorFinder
{

  private java.util.Hashtable allGewinnRegel = new java.util.Hashtable();
  private java.util.Vector    allkeys        = new java.util.Vector();
  private FriendStatus bestgewinner = null;

  public BestOperatorFinder( Vector pStatusReport)
  {
     for ( int i=0; i< pStatusReport.size(); i++)
     {
        FriendStatus fs  = (FriendStatus) pStatusReport.elementAt(i);
        if ( bestgewinner == null )
        {
           bestgewinner = fs;
        }
        else
        {
           /* use the RelativeGewinn-Procent to decide: who is the best gewinner  */
           if ( fs.mGewinnProzent > bestgewinner.mGewinnProzent )
           {
              bestgewinner = fs;
           }
        }
     }
  }

  public FriendStatus getBestGewinner()
  {
     return this.bestgewinner;
  }

  public static void main(String[] args)
  {

    Vector status = new Vector();
    status.add("FRIENDSTATUS,V1,1,R1,100");
    status.add("FRIENDSTATUS,V2,1,R2,120");
    status.add("FRIENDSTATUS,V3,1,R3,-30");
    status.add("FRIENDSTATUS,V4,2,R1,10");
    status.add("FRIENDSTATUS,V5,2,R1,57");
    status.add("FRIENDSTATUS,V6,1,R1,400");
    status.add("FRIENDSTATUS,V7,1,R2,10");
    status.add("FRIENDSTATUS,V8,1,R2,20");
    status.add("FRIENDSTATUS,V9,2,R2,-30");

    BestOperatorFinder pp1 = new BestOperatorFinder( status );
    FriendStatus bb = pp1.getBestGewinner();
    System.out.println("Best Operator :"  + bb.mName +","+bb.mType  );

  }
}