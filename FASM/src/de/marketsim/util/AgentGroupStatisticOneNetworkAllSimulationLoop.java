package de.marketsim.util;

/**
 * <p>Überschrift: FASM Frankfurt Articial Simulation Market</p>
 * <p>Beschreibung: Mircomarket Simulator </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.text.NumberFormat;
import java.util.*;
import de.marketsim.SystemConstant;

public class AgentGroupStatisticOneNetworkAllSimulationLoop
{

  /**
   * 2007-09-23 updated
   * New methode verwendet
   * Wegen Memory Loch Problem
   */

  public Vector mInvestorSingleSimulationGroupStatistic     = new Vector();
  public Vector mNoiseTraderSingleSimulationGroupStatistic  = new Vector();
  public Vector mBlankoSingleSimulationGroupStatistic       = new Vector();
  public Vector mRandomTraderSingleSimulationGroupStatistic = new Vector();

  //private AgentGroupStatisticOneSimulation mFinalGroupStatistic = null;

  public AgentGroupStatisticOneNetworkAllSimulationLoop()
  {

  }

  /**
   *
   * Every time, after a Configuration is finished with all Loops,
   * and the statistic is printed into summary html file,
   *
   * This methode must be called so that all data of last configuration
   * is removed.
   *
   */

  public void ClearOldData()
  {
    mInvestorSingleSimulationGroupStatistic.clear();
    mNoiseTraderSingleSimulationGroupStatistic.clear();
    mBlankoSingleSimulationGroupStatistic.clear();
    mRandomTraderSingleSimulationGroupStatistic.clear();
  }

  public void addAgentGroupStatisticOneSimulation( AgentGroupStatisticBasicWert pStatistic, int pAgentType )
  {

      if ( pAgentType == SystemConstant.AgentType_INVESTOR )
      {
         this.mInvestorSingleSimulationGroupStatistic.add( pStatistic );
      }
      else
      if ( pAgentType == SystemConstant.AgentType_NOISETRADER )
      {
         this.mNoiseTraderSingleSimulationGroupStatistic.add( pStatistic );
      }
      else
      if ( pAgentType == SystemConstant.AgentType_BLANKOAGENT )
      {
         this.mBlankoSingleSimulationGroupStatistic.add( pStatistic );
      }
      else
      if ( pAgentType == SystemConstant.AgentType_RANDOMTRADER )
      {
         this.mRandomTraderSingleSimulationGroupStatistic.add( pStatistic );
      }
      else
      {
         System.out.println("Error: This Agent Type "  + pAgentType + " is not supported up to now.");
      }
  }

  /**
   * Basis Funtion for calculation of average of SingleSimulation Group Statistic
   * @param pSingleSimulationStatisticList
   * @return
   */

  public static AgentGroupStatisticBasicWert createFinalstatistic( Vector pSingleSimulationStatisticList, DataFormatter ffm  )
  {

      if ( pSingleSimulationStatisticList.size() == 1 )
      {
         return ( AgentGroupStatisticBasicWert ) pSingleSimulationStatisticList.elementAt(0);
      }


      AgentGroupStatisticBasicWert sum = new  AgentGroupStatisticBasicWert();

      sum.mBuyMenge_Average              =0;
      sum.mSellMenge_Average             =0;
      sum.mRelativeGewinnProcent_Average =0;
      sum.mAbsoluteGewinnProcent_Average =0;
      sum.mAbsoluteGewinnProcent_Max_Besitzer = "";
      sum.mAbsoluteGewinnProcent_Min_Besitzer = "";
      sum.mAgentNameList                      = "";

      for ( int i=0; i<pSingleSimulationStatisticList.size(); i++)
      {
           AgentGroupStatisticBasicWert dd = ( AgentGroupStatisticBasicWert ) pSingleSimulationStatisticList.elementAt(i);

           // Einfach Zuweisung
           sum.mAgentAnzahl                   = dd.mAgentAnzahl;

           sum.mBuyMenge_Average              = sum.mBuyMenge_Average              + dd.mBuyMenge_Average;
           sum.mSellMenge_Average             = sum.mSellMenge_Average             + dd.mSellMenge_Average;
           sum.mRelativeGewinnProcent_Average = sum.mRelativeGewinnProcent_Average + dd.mRelativeGewinnProcent_Average;
           sum.mAbsoluteGewinnProcent_Average = sum.mAbsoluteGewinnProcent_Average + dd.mAbsoluteGewinnProcent_Average;

           // we use mAgentNameList to store the single AVG of every simulation.
           sum.mAgentNameList = sum.mAgentNameList + ffm.format2str( dd.mAbsoluteGewinnProcent_Average ) + "; ";

           // we use mAbsoluteGewinnProcent_Min_Besitzer to store the single SELLAVG of every simulation.
           sum.mAbsoluteGewinnProcent_Min_Besitzer = sum.mAbsoluteGewinnProcent_Min_Besitzer + ffm.format2str( dd.mSellMenge_Average ) + "; ";

           // we use AbsoluteGewinnProcent_Max_Besitzer to store the single BUY AVG of every simulation.
           sum.mAbsoluteGewinnProcent_Max_Besitzer = sum.mAbsoluteGewinnProcent_Max_Besitzer + ffm.format2str( dd.mBuyMenge_Average ) + "; ";

      }

      int j = pSingleSimulationStatisticList.size();
      sum.mBuyMenge_Average              = sum.mBuyMenge_Average              /j;
      sum.mSellMenge_Average             = sum.mSellMenge_Average             /j;
      sum.mRelativeGewinnProcent_Average = sum.mRelativeGewinnProcent_Average /j;
      sum.mAbsoluteGewinnProcent_Average = sum.mAbsoluteGewinnProcent_Average /j;
      return sum;
  }

  public static AgentGroupStatisticBasicWert  SumBasicWert(AgentGroupStatisticBasicWert  p1, AgentGroupStatisticBasicWert p2    )
  {
       AgentGroupStatisticBasicWert  temp = new AgentGroupStatisticBasicWert();

       temp.mAgentAnzahl                        =p1.mAgentAnzahl + p2.mAgentAnzahl ;
       temp.mBuyMenge_Total                     =p1.mBuyMenge_Total + p2.mBuyMenge_Total ;
       temp.mSellMenge_Total                    =p1.mSellMenge_Total + p2.mSellMenge_Total;
       temp.mBuyMenge_Average                   =p1.mBuyMenge_Average + p2.mBuyMenge_Average;
       temp.mSellMenge_Average                  =p1.mSellMenge_Average + p2.mSellMenge_Average;
       temp.mRelativeGewinnProcent_Average      =p1.mRelativeGewinnProcent_Average + p2.mRelativeGewinnProcent_Average;
       temp.mRelativeGewinnProcent_Min          =p1.mRelativeGewinnProcent_Min + p2.mRelativeGewinnProcent_Min ;
       temp.mRelativeGewinnProcent_Max          =p1.mRelativeGewinnProcent_Max + p2.mRelativeGewinnProcent_Max;
       temp.mRelativeGewinnProcent_Summe        =p1.mRelativeGewinnProcent_Summe + p1.mRelativeGewinnProcent_Summe ;
       temp.mRelativeGewinnProcent_Min_Besitzer ="";
       temp.mRelativeGewinnProcent_Max_Besitzer ="";
       temp.mAbsoluteGewinnProcent_Average      =p1.mAbsoluteGewinnProcent_Average + p2.mAbsoluteGewinnProcent_Average ;
       temp.mAbsoluteGewinnProcent_Min          =p1.mAbsoluteGewinnProcent_Min + p2.mAbsoluteGewinnProcent_Min ;
       temp.mAbsoluteGewinnProcent_Max          =p1.mAbsoluteGewinnProcent_Max + p2.mAbsoluteGewinnProcent_Max;
       temp.mAbsoluteGewinnProcent_Summe        =p1.mAbsoluteGewinnProcent_Summe + p2.mAbsoluteGewinnProcent_Summe ;
       temp.mAbsoluteGewinnProcent_Min_Besitzer ="";
       temp.mAbsoluteGewinnProcent_Max_Besitzer ="";

       return temp;
  }


}