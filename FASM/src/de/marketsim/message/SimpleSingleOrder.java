package de.marketsim.message;

/**
 * <p>Überschrift: FASM Frankfurt Articial Simulation Market</p>
 * <p>Beschreibung: Mircomarket Simulator </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.io.Serializable;

public class SimpleSingleOrder implements Serializable
{

  public char     mAktion;
  public double   mLimit;
  public int      mMenge;
  public int      mPerformedMenge;
  public boolean  mIsPossible = false;

  public SimpleSingleOrder(char pAktion, double pLimit, int pMenge)
  {
     this.mAktion = pAktion;
     this.mLimit  = pLimit;
     this.mMenge  = pMenge;
  }

  public SimpleSingleOrder()
  {
  }

}