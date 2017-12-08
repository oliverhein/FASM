package de.marketsim.config;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

/*

This class contains the init Parameter for starting Agent.

*/


import java.io.Serializable;

public class AgentInitParameter implements Serializable {

 public String mName = null;
  /**
   * AgentType:  Investor or NoiseTrader
   */
  //public int    mInitAgentType      = 0;

  /**
   * MovingDays
   */
  public int    mMovingsday         = 0;

  /**
   * AbschlagProzent
   */
  public double mAbschlagProzent    = 0;

  /**
   *Verlust Limit für Strategie-Wechsel
   */
  public int    mLostNumberLimit    = 3;

  /**
   * Days for calculating of average Limit
   */
  public int    mDays4AverageLimit  = 0;

  /**
   * InitAktien
   */
  public int    mInitAktien  = 0;

  /**
   * InitCash
   */
  public int    mInitCash   = 0;

  /**
   * it will be used when this Agent is changed to NoiseTrader.
   * When the Agent is Investor, this parameter will not be used.
   */
  public double  mLimitAdjust = 0.0;

  /* new special parameter for BlankoAgent */
  public int  mBlankoAgentDayOfIndexWindow = 0;

  public int  mBlankoAgentInactiveDays     = 0;

  public double mBlankoAgentKursUpProcent4Activation     = 3.0;
  public double mBlankoAgentKursDownProcent4Deactivation = 3.0;

  public String toString()
  {
    return "Name=" + this.mName+ ";AbschlagProzent="+this.mAbschlagProzent
            +";Movingsday=" + this.mMovingsday +
           ";Days4AverageLimit="+ this.mDays4AverageLimit+
           ";LostNumberLimit=" + this.mLostNumberLimit +
           ";InitAktien=" + this.mInitAktien +";InitCash=" + this.mInitCash +
           "BlankoAgentDayOfIndexWindow=" + mBlankoAgentDayOfIndexWindow +
           "BlankoAgentInactiveDays=" + mBlankoAgentInactiveDays ;
  }
}