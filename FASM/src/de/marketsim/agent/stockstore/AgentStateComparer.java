package de.marketsim.agent.stockstore;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.Hashtable;
import de.marketsim.SystemConstant;

public class AgentStateComparer {

  /**
   * Content: Key: AgentName, Value: Type
   * V1<---->I   (Investor)
   * V2<---->N   (NoiseTrader)
   */

  private Hashtable mTodayState     = null;
  private Hashtable mYesterdayState = null;

  private AgentChangeStatistic mAgentChangeStatistic  = new AgentChangeStatistic();

  public AgentStateComparer()
  {

  }

  public void clearMemoryState()
  {
      this.mYesterdayState = null;
      this.mTodayState     = null;
  }

  public AgentChangeStatistic getAgentChangeStatistic()
  {
      return this.mAgentChangeStatistic;
  }

  public void setNewState ( Hashtable pNewState )
  {
     this.mYesterdayState      = this.mTodayState;
     this.mTodayState          = pNewState;
     this.docompare( this.mTodayState, this.mYesterdayState  );
  }

  private void docompare( Hashtable pOldState, Hashtable pNewState )
  {
       if (  (pOldState == null )  || ( pNewState == null )  )
       {
           mAgentChangeStatistic.setChanges(0,0);
           return;
       }

       Object AgentNameList[] =  pNewState.keySet().toArray();
       Object ttttt[]         =  pOldState.keySet().toArray();

       int InvestorChanged2NoiseTrader = 0;
       int NoiseTraderChanged2Investor = 0;

       //System.out.println ("New State List.length="+ AgentNameList.length);
       //System.out.println ("Old state List.length="+ ttttt.length );

       for ( int i=0; i< AgentNameList.length; i++)
       {
           Object oldss = pOldState.get( AgentNameList[i] );
           Object newss = pNewState.get( AgentNameList[i] );
           if ( !oldss.equals( newss ) )
           {
                if (  ((String) newss ).equals("" +  SystemConstant.AgentType_INVESTOR ) )
                {
                    // NoiseTrader --> Investor
                    NoiseTraderChanged2Investor++;
                }
                else
                {
                    // Investor-->NoiseTrader
                    InvestorChanged2NoiseTrader++;
                }
           }
       }
       this.mAgentChangeStatistic.setChanges( InvestorChanged2NoiseTrader, NoiseTraderChanged2Investor);
  }

  /**
   * self test program
   * @param args
   */
  public static void main(String[] args)
  {
      AgentStateComparer pp = new AgentStateComparer();

      Hashtable  p1 = new Hashtable();
      p1.put("V1", "N");
      p1.put("V2", "I");
      p1.put("V3", "N");
      p1.put("V4", "I");

      Hashtable  p2 = new Hashtable();
      p2.put("V1", "N");
      p2.put("V2", "N");
      p2.put("V3", "I");
      p2.put("V4", "N");

      pp.setNewState(p1);
      pp.setNewState(p2);

      System.out.println("changed=" + pp.getAgentChangeStatistic().getInfo()  );

  }

}