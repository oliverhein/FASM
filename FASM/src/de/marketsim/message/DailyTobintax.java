package de.marketsim.message;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class DailyTobintax implements java.io.Serializable
{

  public double mBasetax_Cash1  = 0;
  public double mExtratax_Cash1 = 0;

  public double mBasetax_Cash2  = 0;
  public double mExtratax_Cash2 = 0;

  public double mKurs  = 0;
  public int    mTag   = 0;

  public DailyTobintax( double pKurs,
                        double pBasetax_Cash1,
                        double pExtratax_Cash1,
                        double pBasetax_Cash2,
                        double pExtratax_Cash2
                      )
  {
    mKurs = pKurs;
    mBasetax_Cash1 =pBasetax_Cash1;
    mExtratax_Cash1 =pExtratax_Cash1;
    mBasetax_Cash2 =pBasetax_Cash2;
    mExtratax_Cash2 =pExtratax_Cash2;
  }

}