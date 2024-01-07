package de.marketsim.config;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.*;
import java.awt.TextArea;

import de.marketsim.util.HelpTool;
import de.marketsim.config.Distributor;
import de.marketsim.SystemConstant;

public class AgentDistributor
{

  //public boolean mAgentAlreadyCreated = false;

  Vector mAgentDistributionList = null;

  private java.util.Random rdgen = new Random();

  public String[] mAllAgentNames        = new String[0];
  public String[] mInvestorNameList     = new String[0];
  public String[] mNoiserTraderNameList = new String[0];
  public String[] mRandomTraderNameList = new String[0];

  public int mTotalNodes  =0 ;
  public int mInvestor    =0;
  public int mNoiseTrader =0;
  public int mRandomTrader=0;

  public AgentDistributor()
  {
  }

  public Hashtable getAgentTypeList()
  {
      Hashtable hs = new Hashtable();
      for ( int i=0; i< this.mInvestorNameList.length; i++ )
      {
          hs.put( this.mInvestorNameList[i], "I" );
      }

      for ( int i=0; i< this.mNoiserTraderNameList.length; i++ )
      {
          hs.put( this.mNoiserTraderNameList[i], "N" );
      }
      return hs;
  }

public String[] getInvestorNameList()
{
    return this.mInvestorNameList;
}

public String[] getNoiserTraderNameList()
{
   return this.mNoiserTraderNameList;
}

public int getAgentNumber()
{
   return this.mTotalNodes;
}

public int getInvestorNumber()
{
   return this.mInvestor ;
}

public int getNoiseTraderNumber()
{
   return this.mNoiseTrader;
}

  private void Write2BlackBoard( Object pBlackBoard, String pInfo)
  {
      if ( pBlackBoard != null )
      {
           if (  pBlackBoard instanceof TextArea )
           {
              ((TextArea ) pBlackBoard ).append(pInfo+"\r\n");
           }
           else
           {
             ( (java.io.PrintStream ) pBlackBoard ).println(pInfo );
           }
      }
      else
      {
           System.out.println( pInfo );
      }
  }

  private void Verteiler(boolean pUseRandom, int pInvestor, int pNoiseTrader, Object pBlackBoard )
  {
      this.mInvestorNameList     = new String[ pInvestor ];
      this.mNoiserTraderNameList = new String[ pNoiseTrader ];
      if ( ! pUseRandom )
      {
            // Seriale Verteilung
            this.Write2BlackBoard( pBlackBoard, "Mapping of Vertex to AgentType (Serial Mode) ");

            for ( int i=0; i< pInvestor; i++)
            {
              this.mInvestorNameList [i] ="V"+(i+1);
              this.Write2BlackBoard( pBlackBoard, i + ". " +  this.mInvestorNameList [i] +"-->Fundamental Investor");
            }
            this.Write2BlackBoard( pBlackBoard, " ======================== ");
            for ( int i=0; i< this.mNoiseTrader; i++)
            {
              this.mNoiserTraderNameList[i] ="V"+( pInvestor+i+1);
              this.Write2BlackBoard( pBlackBoard, i+ ". " + this.mNoiserTraderNameList[i] +"-->NoiseTrader");
            }
      }
      else
      {
            this.Write2BlackBoard( pBlackBoard,"Mapping of Vertex to AgentType (Random Mode) ");
            Hashtable tt = new Hashtable();
            for ( int i=0; i< (pInvestor + pNoiseTrader) ; i++)
            {
                 tt.put("V"+(i+1) , " ");
            }
            //this.Write2BlackBoard( pBlackBoard, "Hashtable.size()=" + tt.size() );
            Random rd = new Random();

            Hashtable tempinvestor = new Hashtable();

            // Zufall Verteilung
            int i=0;
            while ( tempinvestor.size()< pInvestor )
            {
              String zufallname = "V"+ (1 + rd.nextInt( (pInvestor + pNoiseTrader )  ) );
              if ( ! tempinvestor.containsKey(zufallname) )
              {
                    this.mInvestorNameList [i] = zufallname;
                    this.Write2BlackBoard( pBlackBoard, i + ". " +  this.mInvestorNameList [i] +"-->Fundamental Investor");
                    tempinvestor.put(zufallname, " ");
                    tt.remove( zufallname );
                    i++;
              }
            }
            this.Write2BlackBoard( pBlackBoard, " ======================== ");
            //System.out.println("Hashtable.size()=" + tt.size() );
            //Rest sind NoiseTrader
            Enumeration en = tt.keys();

            int j=0;
            while ( en.hasMoreElements() )
            {
                String zufallname = (String) en.nextElement();
                this.mNoiserTraderNameList[j] = zufallname;
                this.Write2BlackBoard( pBlackBoard, j + ". " + this.mNoiserTraderNameList [j] +"-->NoiseTrader");
                j=j+1;
            }
      }
  }

}