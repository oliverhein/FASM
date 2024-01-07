package de.marketsim.util;

/**
 * <p>Überschrift: FASM Frankfurt Articial Simulation Market</p>
 * <p>Beschreibung: Mircomarket Simulator </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 *
 *
 * 2007-09-27/28
 *
 * This class has caused memory OutOfMemory Exception
 *
 */


import java.text.NumberFormat;

import java.util.*;
import de.marketsim.util.AgentGroupStatisticOneNetworkAllSimulationLoop;
import de.marketsim.util.AgentGroupStatisticBasicWert;
import de.marketsim.util.HelpTool;
import de.marketsim.util.HTMLCreator;
import de.marketsim.config.Configurator;

public class AgentGroupStatisticAllNetworks
{


  public static Hashtable mAllNetworkStatistic = new Hashtable();

  public AgentGroupStatisticAllNetworks()
  {



  }


  /**
   *
   * @param pNetworkFileName
   * @param pAgentGroupType:  See SystemConstant Defintion
   * @param pStatistic
   */

  public static void addNewStatistic(String pNetworkFileName, int pAgentGroupType, AgentGroupStatisticBasicWert pStatistic)
  {
       if ( mAllNetworkStatistic.get( pNetworkFileName ) == null )
       {
         AgentGroupStatisticOneNetworkAllSimulationLoop pp = new AgentGroupStatisticOneNetworkAllSimulationLoop();
         mAllNetworkStatistic.put( pNetworkFileName, pp );
       }

      AgentGroupStatisticOneNetworkAllSimulationLoop pp =  ( AgentGroupStatisticOneNetworkAllSimulationLoop )mAllNetworkStatistic.get( pNetworkFileName ) ;

      pp.addAgentGroupStatisticOneSimulation( pStatistic, pAgentGroupType   );
  }

  public static void ClearAllOldData()
  {
       mAllNetworkStatistic.clear();
  }

  public static void CreateFinalAgentGroupComparation( String pFileName )
  {

      java.io.PrintStream fw = null;

      try
      {
        fw = new  java.io.PrintStream( new java.io.FileOutputStream (pFileName) );
      }
      catch (Exception ex)
      {
          ex.printStackTrace();
          return;
      }

      // print HTML Table Title

      HTMLCreator.putHtmlHeadwithTitel(fw, "Agent group comparation" );

      HTMLCreator.putHtmlBodyBegin( fw );

      HTMLCreator.putHtmlHeadwithTitel(fw, "<Font size='20' color='blue'> comparation of agent group statistic </Font>");

      printComparationTableTitle( fw );

      Enumeration allnetworknames = mAllNetworkStatistic.keys();

      // Get Default Number Formatter
      DataFormatter ffm = new DataFormatter ( Configurator.mConfData.mDataFormatLanguage  );

      // Nur zwei Stelle nach Komma
      ffm.setMaximumFractionDigits(2);

      // print Table contents
      String networkname  = "";

      fw.println("Number of Networks = "  + mAllNetworkStatistic.size() );

      while ( allnetworknames.hasMoreElements() )
      {
        networkname = ( String ) allnetworknames.nextElement();
        AgentGroupStatisticOneNetworkAllSimulationLoop
        pp = ( AgentGroupStatisticOneNetworkAllSimulationLoop )mAllNetworkStatistic.get( networkname ) ;

        /**  Debug Code 2007-09-27
         *
        fw.println("<br>networkname = " + networkname );
        fw.println("<br>Number of statistic for fundamental  = "  +  pp.mInvestorSingleSimulationGroupStatistic.size() );
        fw.println("<br>Number of Statistic for Trend        = "  +  pp.mNoiseTraderSingleSimulationGroupStatistic.size() );
        fw.println("<br>Number of Statistic for Blanko       = "  +  pp.mBlankoSingleSimulationGroupStatistic.size() );
        fw.println("<br>Number of Statistic for RandomTrader = "  +  pp.mRandomTraderSingleSimulationGroupStatistic.size() );

        */
      }

      allnetworknames = mAllNetworkStatistic.keys();

      while ( allnetworknames.hasMoreElements() )
      {

        networkname = ( String ) allnetworknames.nextElement();
        AgentGroupStatisticOneNetworkAllSimulationLoop
        pp = ( AgentGroupStatisticOneNetworkAllSimulationLoop )mAllNetworkStatistic.get( networkname ) ;

        // create Final Statistic for Investor
        AgentGroupStatisticBasicWert
        result = pp.createFinalstatistic( pp.mInvestorSingleSimulationGroupStatistic, ffm );

        // Print Final Statistic for Investor
        // 1. zeil mit Network name
        printFinalStatisticWert(1, networkname, "Fundamental", fw, result , ffm );

        // create Final Statistic for NoiseTrader
        // No NetworkName is required.
        result = pp.createFinalstatistic( pp.mNoiseTraderSingleSimulationGroupStatistic, ffm );
        // Print Final Statistic for NoiseTrader
        printFinalStatisticWert(2, "--", "Trend", fw, result , ffm );

        // create Final Statistic for Blanko
        result = pp.createFinalstatistic( pp.mBlankoSingleSimulationGroupStatistic, ffm );
        // Print Final Statistic for Blanko
        printFinalStatisticWert(3, "--","Retail", fw, result , ffm );

        // create Final Statistic for RandomTrader
        result = pp.createFinalstatistic( pp.mRandomTraderSingleSimulationGroupStatistic, ffm );
        // Print Final Statistic for RandomTrader
        printFinalStatisticWert(4, "--","Liquidity", fw, result , ffm );

      }
      HTMLCreator.putHtmlBodyEnd( fw);
      HTMLCreator.putHtmlEnd( fw );

}

  /**
   * Print a HTML Table Head
   * @param fw
   */
  public static void printComparationTableTitle(java.io.PrintStream fw)
  {
    // Print Title of Table
    // Print Title in HTML Format
    fw.println( "<Table Border=2 >");
    fw.println( "<TR>");
    fw.println( "<TH>Network</TH>");
    fw.println( "<TH>Nr</TH>");
    fw.println( "<TH>Group Name   </TH>");
    fw.println( "<TH>Agent Number </TH>");
    fw.println( "<TH>Final AVG Profit(%)</TH>");
    fw.println( "<TH>Final AVG Buy  (Stock)</TH>");
    fw.println( "<TH>Final AVG Sell (Stock)</TH>");
    fw.println( "<TH>SingleSimulation AVGProfit</TH>");
    fw.println( "<TH>SingleSimulation AVGBuy</TH>");
    fw.println( "<TH>SingleSimulation AVGSell</TH>");
    fw.print( "</TR>");
  }

  /**
   * Print a Summary of OneGroup Final Statistic
   * @param fw
   */
  public static void printFinalStatisticWert(int pNr, String pNetworkName, String pGroupName,  java.io.PrintStream fw , AgentGroupStatisticBasicWert pStatistic,  DataFormatter ffm )
  {

    fw.println( "<TR>");
    fw.println( "<TD>" +  pNetworkName + "</TD>");
    fw.println( "<TD>" + pNr +                      "</TD>");
    fw.println( "<TD>" + pGroupName +               "</TD>");
    fw.println( "<TD>" + pStatistic.mAgentAnzahl +  "</TD>");
    fw.println( "<TD>" + ffm.format2str(pStatistic.mAbsoluteGewinnProcent_Average) +  "</TD>");
    fw.println( "<TD>" + ffm.format2str(pStatistic.mBuyMenge_Average ) +"</TD>");
    fw.println( "<TD>" + ffm.format2str(pStatistic.mSellMenge_Average ) + "</TD>");

    // mAbsoluteGewinnProcent_Max_Besitzer is used to store the List of Single AVG Profit
    fw.println( "<TD>" + pStatistic.mAgentNameList +"</TD>");

    // List of single AVG Buy Menge
    fw.println( "<TD>"+ pStatistic.mAbsoluteGewinnProcent_Max_Besitzer+"</TD>");

    // List of single AVG Sell Menge
    fw.println( "<TD>" + pStatistic.mAbsoluteGewinnProcent_Min_Besitzer +"</TD>");
    fw.print( "</TR>");

  }

  public static void main(String[] args)
  {
    AgentGroupStatisticAllNetworks agentGroupStatisticAllNetworks1 = new AgentGroupStatisticAllNetworks();
  }
}