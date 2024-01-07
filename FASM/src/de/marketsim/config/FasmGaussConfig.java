package de.marketsim.config;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author unbekannt
 * @version 1.0
 */

public class FasmGaussConfig
{

  public double  mMean      = 0;
  public double  mDeviation = 0;

  public double  mAllowedMin  = 0;
  public double  mAllowedMax  = 0;

  public int     mDataNumber  = 50; // Default
  public double  mDataPart1Prozent = 50.0;
  public double  mDataPart2Prozent = 35.0;
  public double  mDataPart3Prozent = 15.0;

  public int     mWertGrenzStelle1 = 300;   // Default
  public int     mWertGrenzStelle2 = 300;   // Default
  public int     mWertGrenzStelle3 = 400;  // Default

  // Summe must be 1000

}