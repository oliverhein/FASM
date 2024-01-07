package de.marketsim.util;

/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author
 * @version 1.0
 */

public class OperatorType_todelete
{

  public static int INVESTOR      = 1;
  public static int NOISETRADER   = 2;
  public static int RANDOMTRADER  = 3;
  public static int Other         = 4;

  public static String INVESTOR_STR      = "Investor";
  public static String NOISETRADER_STR   = "NoiseTrader";
  public static String RANDOMTRADER_STR  = "RandomTrader";
  public static String Other_STR  = "Other";

  public static String[] AllTypeName = {INVESTOR_STR,NOISETRADER_STR,RANDOMTRADER_STR, Other_STR};
  public static String getOperatorTypeName(int pType)
  {
     return AllTypeName[ pType -1 ];
  }

}