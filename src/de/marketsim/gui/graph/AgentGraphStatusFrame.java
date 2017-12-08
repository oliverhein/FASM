package de.marketsim.gui.graph;

import java.awt.*;
import javax.swing.JFrame;
import java.util.*;
import de.marketsim.SystemConstant;
import de.marketsim.gui.graph.MyGraphPanel;
import de.marketsim.util.DailyStatisticOfNetwork;
import de.marketsim.util.GraphDailyState;



/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class AgentGraphStatusFrame extends JFrame
{
  MyGraphPanel  mGRP = null;
  GridLayout gridLayout1 = new GridLayout();
  boolean mAllowedDefaultClose = true;

  boolean mWishedVisible = true;

  public void setWishedVisible(boolean pWishedVisible)
  {
     this.mWishedVisible = pWishedVisible;
     this.setVisible( pWishedVisible );
  }

  public void enableDefaultClose(boolean pAllowed)
  {
       this.mAllowedDefaultClose = pAllowed;
  }

  public void processEvent( AWTEvent e )
  {
    // 201 is the code of Windows Closing
    if ( e.getID()== 201 )
    {
      if ( this.mAllowedDefaultClose )
      {
        super.processEvent(e);
      }
      else
      {
        // Nothing to do
      }
    }
    else
    {
       super.processEvent(e);
    }
  }

  public MyGraphPanel getGraphPanel()
  {
     return this.mGRP;
  }

  public AgentGraphStatusFrame(String pFilename  )
  {
    this.setTitle("Agent Network");
    this.getContentPane().setLayout(gridLayout1);
    this.mGRP = new MyGraphPanel(pFilename );
    this.mGRP.setBounds(0,0, 400,400);
    this.setSize(400,400);
    this.getContentPane().add( mGRP );
  }

  public AgentGraphStatusFrame( int pNodeNumber, Vector pCommunicationPairList, int pRandomTrader )
  {
      this.getContentPane().setLayout(gridLayout1);
      this.mGRP = new MyGraphPanel( pNodeNumber, pCommunicationPairList, pRandomTrader);
      this.mGRP.setBounds(0,0, 400,400);
      this.setSize(400,400);
      this.getContentPane().add( mGRP );
      this.setTitle("Agent Network");
  }

  public void setActionList(Hashtable pActionList)
  {
      this.mGRP.setActionList(pActionList );
  }

  public void setAgentTypeList(Hashtable pAgentTypeList)
  {
      this.mGRP.setAgentTypeList( pAgentTypeList );
  }

  //  Format of Vector pAgentStatus
  //  AgentName;AgentType;OrderState;ZusatzInfo;
  //  V1;1;S;
  //  V2;2;N;
  //  V3;1;B;
  //  V4;5;N;

  public void DisplayStatus( Vector pNodeState )
  {
      if ( this.mWishedVisible == true )
      {
            //
            // create two Hashtable for Graph-Darstellungen according to the Vector
            //

            Hashtable actionlist    = new Hashtable();
            Hashtable agenttypelist = new Hashtable();
            for ( int i=0; i<pNodeState.size(); i++)
            {
                 String ss = (String ) pNodeState.elementAt(i);
                 System.out.println( ss );
                 int j1 = ss.indexOf(";");
                 int j2 = ss.indexOf(";",j1+1);
                 int j3 = ss.indexOf(";",j2+1);
                 String name   = ss.substring(0, j1);
                 String ss2   = ss.substring(j1+1,j2);
                 int type = Integer.parseInt(ss2);

                 if ( type == SystemConstant.AgentType_INVESTOR )
                 {
                    agenttypelist.put( name, "I" );
                 }
                 else
                 if ( type == SystemConstant.AgentType_NOISETRADER )
                 {
                    agenttypelist.put( name, "N" );
                 }
                 else
                 if ( type == SystemConstant.AgentType_BLANKOAGENT )
                 {
                    agenttypelist.put( name, "B" );
                 }
                 else
                 if ( type == SystemConstant.AgentType_RANDOMTRADER )
                 {
                    agenttypelist.put( name, "R" );
                 }

                 String action = ss.substring( j2+1, j3);
                 actionlist.put( name , action);
            }

            this.mGRP.setActionList   ( actionlist    );
            this.mGRP.setAgentTypeList( agenttypelist );
            this.mGRP.updateState     (               );
            this.setVisible           ( true          );
      }
  }

  //
  //
  // This method is designed for GraphRedisplay
  //
  //
  //  Format of Vector pAgentStatus
  //  AgentName;AgentType;OrderState;ZusatzInfo;
  //  V1;1;S;
  //  V2;2;N;
  //  V3;1;B;
  //  V4;5;N;

  public void DisplayDailyStatus( GraphDailyState pGraphDailyState )
  {
      if ( this.mWishedVisible == true )
      {
            //
            // create two Hashtable for Graph-Darstellungen according to the Vector
            //

            Hashtable actionlist    = new Hashtable();
            Hashtable agenttypelist = new Hashtable();
            for ( int i=0; i<pGraphDailyState.mNodeState.size(); i++)
            {
                 String ss = (String ) pGraphDailyState.mNodeState.elementAt(i);
                 System.out.println( ss );
                 int j1 = ss.indexOf(";");
                 int j2 = ss.indexOf(";",j1+1);
                 int j3 = ss.indexOf(";",j2+1);
                 String name   = ss.substring(0, j1);
                 String ss2   = ss.substring(j1+1,j2);
                 int type = Integer.parseInt(ss2);

                 if ( type == SystemConstant.AgentType_INVESTOR )
                 {
                    agenttypelist.put( name, "I" );
                 }
                 else
                 if ( type == SystemConstant.AgentType_NOISETRADER )
                 {
                    agenttypelist.put( name, "N" );
                 }
                 else
                 if ( type == SystemConstant.AgentType_BLANKOAGENT )
                 {
                    agenttypelist.put( name, "B" );
                 }
                 else
                 if ( type == SystemConstant.AgentType_RANDOMTRADER )
                 {
                    agenttypelist.put( name, "R" );
                 }

                 String action = ss.substring( j2+1, j3);
                 actionlist.put( name , action);
            }

            this.mGRP.setActionList   ( actionlist    );
            this.mGRP.setAgentTypeList( agenttypelist );

            /*
            System.out.println("Investor    = " + pGraphDailyState.mDailyStatisticNetwork.mInvestor);
            System.out.println("NoiseTrader = " + pGraphDailyState.mDailyStatisticNetwork.mNoiseTrader);
            System.out.println("Blanko      = " + pGraphDailyState.mDailyStatisticNetwork.mBlankoAgent);
            */

            this.mGRP.setInvestor     ( pGraphDailyState.mDailyStatisticNetwork.mInvestor  );
            this.mGRP.setInvestorChanged   ( pGraphDailyState.mDailyStatisticNetwork.mInvestorChanged );

            this.mGRP.setNoiseTrader  ( pGraphDailyState.mDailyStatisticNetwork.mNoiseTrader  );
            this.mGRP.setNoiseTraderChanged( pGraphDailyState.mDailyStatisticNetwork.mNoiseTraderChanged  );

            this.mGRP.setBlanko       ( pGraphDailyState.mDailyStatisticNetwork.mBlankoAgent  );
            this.mGRP.setBlankoJustActivated( pGraphDailyState.mDailyStatisticNetwork.mBlankoAgentActivated );
            this.mGRP.setBlankoDeactivated(   pGraphDailyState.mDailyStatisticNetwork.mBlankoAgentDeactivated  );

            this.mGRP.updateState     (               );
            this.setVisible           ( true          );
      }
  }



  public void setCommunicationPairList(Vector pCommunicationPairList)
  {
       this.mGRP.setCommunicationPairList( pCommunicationPairList );
       this.mGRP.updateState();
  }

  public Hashtable CreateDemoActionList(int pVertextNumer)
  {
       return this.mGRP.CreateDemoActionList(pVertextNumer);
  }

  public Hashtable CreateDemoTypeDistribution(int pVertextNumer)
  {
       return this.mGRP.CreateDemoTypeDistribution(pVertextNumer) ;
  }

  /**
   * set the Statistic Info
   * @param pNumber
   */

  public void setDailyStatistic( DailyStatisticOfNetwork pStatistic )
  {
     this.mGRP.setInvestor          (  pStatistic.mInvestor              );
     this.mGRP.setInvestorChanged   (  pStatistic.mInvestorChanged       );
     this.mGRP.setNoiseTrader       (  pStatistic.mNoiseTrader           );
     this.mGRP.setNoiseTraderChanged(  pStatistic.mNoiseTraderChanged    );
     this.mGRP.setBlanko            (  pStatistic.mBlankoAgent           );

     this.mGRP.setBlankoJustActivated   (  pStatistic.mBlankoAgentActivated  );
     //this.mGRP.setBlankoActivatedAgent( pStatistic.mBlankoAgentActivated );
     this.mGRP.setBlankoDeactivated (  pStatistic.mBlankoAgentDeactivated);
     //this.mGRP.setBlankoInactivatedAgent( pStatistic.mBlankoAgentDeactivated  );

  }

}