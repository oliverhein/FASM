package de.marketsim.message;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import de.marketsim.SystemConstant;

public class FriendStatus  implements java.io.Serializable
{
  public static int AUSGLEICH = 0;
  public static int GEWINN  = 1;
  public static int VERLUST = 2;

  public String    mName = null;
  public int       mType = 0;
  public double    mAbschlagProzent = 0.0;
  public int       mDayofAveragePrice = 0;
  public double    mGewinn = 0;
  public double    mGewinnProzent = 0.0;

  public int       mOriginalType = 0;

  public FriendStatus( String pName,
                       int pType,
                       double pAbschlagProzent,
                       int pDayofAveragePrice,
                       double pGewinn,
                       double pGewinnProzent,
                       int    pOriginalType )
  {
      this.mName              = pName;
      this.mType              = pType;
      this.mAbschlagProzent   = pAbschlagProzent ;
      this.mDayofAveragePrice = pDayofAveragePrice;
      this.mGewinn            = pGewinn;
      this.mGewinnProzent     = pGewinnProzent;
      this.mOriginalType      = pOriginalType;
  }


}