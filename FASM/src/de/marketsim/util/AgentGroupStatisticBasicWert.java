package de.marketsim.util;

/**
 * <p>Überschrift: FASM Frankfurt Articial Simulation Market</p>
 * <p>Beschreibung: Mircomarket Simulator </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class AgentGroupStatisticBasicWert
{

  public String  mAgentTypeName;
  public int     mAgentAnzahl;

  public long    mTypeChangeCounter;
  public double  mBuyMenge_Total;
  public double  mSellMenge_Total;

  public double  mBuyMenge_Average;
  public double  mSellMenge_Average;

  public double  mRelativeGewinnProcent_Average;
  public double  mRelativeGewinnProcent_Min = Double.MAX_VALUE;
  public double  mRelativeGewinnProcent_Max = -10000000;
  public double  mRelativeGewinnProcent_Summe;

  public String  mRelativeGewinnProcent_Min_Besitzer;
  public String  mRelativeGewinnProcent_Max_Besitzer;

  public double  mAbsoluteGewinnProcent_Average;
  public double  mAbsoluteGewinnProcent_Min = Double.MAX_VALUE;
  public double  mAbsoluteGewinnProcent_Max = -10000000 ;
  public double  mAbsoluteGewinnProcent_Summe;

  public String  mAbsoluteGewinnProcent_Min_Besitzer;
  public String  mAbsoluteGewinnProcent_Max_Besitzer;


  public String  mAgentNameList =""; // nur Check

  public AgentGroupStatisticBasicWert()
  {


  }

  public Object  clone()
  {

          AgentGroupStatisticBasicWert  temp = new AgentGroupStatisticBasicWert();

          temp.mAgentAnzahl                        =this.mAgentAnzahl;
          temp.mBuyMenge_Total                     =this.mBuyMenge_Total;
          temp.mSellMenge_Total                    =this.mSellMenge_Total;
          temp.mBuyMenge_Average                   =this.mBuyMenge_Average;
          temp.mSellMenge_Average                  =this.mSellMenge_Average;
          temp.mRelativeGewinnProcent_Average      =this.mRelativeGewinnProcent_Average;
          temp.mRelativeGewinnProcent_Min          =this.mRelativeGewinnProcent_Min;
          temp.mRelativeGewinnProcent_Max          =this.mRelativeGewinnProcent_Max;
          temp.mRelativeGewinnProcent_Summe        =this.mRelativeGewinnProcent_Summe;
          temp.mRelativeGewinnProcent_Min_Besitzer ="";
          temp.mRelativeGewinnProcent_Max_Besitzer ="";
          temp.mAbsoluteGewinnProcent_Average      =this.mAbsoluteGewinnProcent_Average;
          temp.mAbsoluteGewinnProcent_Min          =this.mAbsoluteGewinnProcent_Min;
          temp.mAbsoluteGewinnProcent_Max          =this.mAbsoluteGewinnProcent_Max;
          temp.mAbsoluteGewinnProcent_Summe        =this.mAbsoluteGewinnProcent_Summe;
          temp.mAbsoluteGewinnProcent_Min_Besitzer ="";
          temp.mAbsoluteGewinnProcent_Max_Besitzer ="";

          return temp;

  }

}