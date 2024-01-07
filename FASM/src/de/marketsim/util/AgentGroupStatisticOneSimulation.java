package de.marketsim.util;

/**
 * <p>Überschrift: FASM Frankfurt Articial Simulation Market</p>
 * <p>Beschreibung: Mircomarket Simulator </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.Hashtable;
import de.marketsim.util.AgentGroupStatisticBasicWert;
import de.marketsim.SystemConstant;

public class AgentGroupStatisticOneSimulation
{

  private Hashtable mAllGroupWerte = new Hashtable();
  /**
      Key      Wert
      1        BasicWert
      2        BasicWert
      3        BasicWert
      5        BasicWert
   */

  public AgentGroupStatisticOneSimulation()
  {
      //Initialise  4 empty GroupStatistic into Hashtable
      // for Investor
      AgentGroupStatisticBasicWert bb = new AgentGroupStatisticBasicWert();
      mAllGroupWerte.put( SystemConstant.AgentType_INVESTOR + ""   , bb );

      // for NoiseTrader
      bb = new AgentGroupStatisticBasicWert();
      mAllGroupWerte.put( SystemConstant.AgentType_NOISETRADER + "", bb );

      // for RandomAgent
      bb = new AgentGroupStatisticBasicWert();
      mAllGroupWerte.put( SystemConstant.AgentType_RANDOMTRADER + "", bb );

      // for BlankoAgent
      bb = new AgentGroupStatisticBasicWert();
      mAllGroupWerte.put( SystemConstant.AgentType_BLANKOAGENT+"" , bb );

  }

  public void addOneGroupStatistic(String pAgentType, Object pOneGroupStatistic )
  {
      mAllGroupWerte.put(pAgentType,  pOneGroupStatistic );
  }

  public AgentGroupStatisticBasicWert getOneGroupStatisticBasicWert(String pAgentType)
  {
      return  (AgentGroupStatisticBasicWert ) mAllGroupWerte.get( pAgentType );
  }

}