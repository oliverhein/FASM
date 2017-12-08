package de.marketsim.gui;
/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */
import java.util.Vector;
import de.marketsim.util.DailyStatisticOfNetwork;



public interface StoreBaseGUI
{

  public void registerInvestorAgent();
  public void registerNoiseTraderAgent();
  public void registerRandomTraderAgent();
  public void registerBlankoAgent();
  public void registerTobintaxAgent();

  public void setSimulationCounter(String pSimulationCounterInfo );

  public void unregisterInvestorAgent();
  public void unregisterNoiseTraderAgent();
  public void unregisterRandomTraderAgent();
  public void unregisterBlankoAgent();
  public void updateSimulatorList(String pNewList);
  public void closebusiness();
  public void setStarttimeofFirstday(String pTime);
  public void setCurrentNetwork(String pNetworkFile);
  public void setCurrentday(int pDay);
  public void setFinishedtimeofCurrentday(String pTime);
  public void disableAllSubFrame();
  public void EnableAllMonitorFrame( boolean pEnabled );
  public void displayInvestorGewinnProzent( Vector pInvestorGewinnProzent, int pDay, double pMax, double pMin);
  public void displayNoiserTraderGewinnProzent( Vector pNoiseTraderGewinnProzent, int pDay, double pMax, double pMin);
  public void PrepareLogDirectoryForOneSimulation();
  public void setExpectedAgentAnzahl ( int pTotalAgents, int pInvestorAnzahl, int pNoiseTraderAnzahl, int pBlankoAgentAnzahl,int pRandomTraderAnzahl);
  public boolean getShowInnererWertEnabled();
  public void resetSomeStateFields();
  public void addInterrupted();
  public void setScreenLayout();
  public void SendRuntimeParameter( Vector pSimulatorList);
  // send Runtime Parameter
  public void SendDistributionList( Vector pSimulatorList, String[] pDistributionList);
  public void SetCurrentNetworkParameterFromNetworkList( int pNetworkIndex );
  public void StartNewSimulation();
  public void startTobinTaxAgent();
  public void startDataLogger();
  public boolean isAgentStatusInGraph();
  public boolean isAgentStatusNameLoaded();
  public void setNameList2AgentStatusFrame(Vector pNameList);
  public void setAgentTradeStatus(Vector pAgentStatus, DailyStatisticOfNetwork pStatistic );
  public void EnableButtons4newConfiguration();
  public void showDailyOrderBook(String  pDailyOrderBook);

  public void loadConfigFile(String pConfigFile);


}
