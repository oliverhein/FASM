/**
 * Überschrift:
 * Beschreibung:
 * Copyright:    Copyright (c) 2003
 * Organisation:
 * @author Wang
 * @version 1.0
 **/

package de.marketsim.agent.stockstore;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.Profile;
import java.awt.Dimension;
import java.awt.Toolkit;

import jade.lang.acl.ACLCodec.*;
import jade.wrapper.PlatformController;
import jade.wrapper.AgentController;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;

import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;

import javax.swing.*;
import java.util.*;
import java.io.*;

import java.text.NumberFormat;
import de.marketsim.gui.*;

import de.marketsim.agent.stockstore.stockdata.*;
import de.marketsim.util.*;
import de.marketsim.config.*;
import de.marketsim.message.*;
import de.marketsim.SystemConstant;
import de.marketsim.agent.AgentCommandlineParameterConst;

public class Store extends Agent
{
    //
    protected HistoryFrame           mChartFrame                = null;

    protected GewinnProcentFrame     mTraderGewinnProzentFrame   = null;
    protected GewinnProcentFrame     mInvestorGewinnProzentFrame = null;
    protected StoreBaseGUI           mMainFrame                  = null ;
    protected StoreDialogNetworkFile mStoreControlFrame          = null;
    protected PriceHistoryGenerator  mPriceGen                   = null;
    protected ProfitGenerator        mProfitGen                  = null;
    protected InterestRateGenerator  mInterestRateGen            = null;

    String  bootmode   = null;

    String  mSimulationConfigFile = null;

    int mInvestor = 0;
    int mTrader   = 0;
    int mNumberOfCommunicationRelation = 0;

    StoreBehavior  mBH = null;
    //---------------------------------
    public Store()
    {

    }

    /**
     * Setup the Store.
     * Registers with the DF
     * add behaviour
     */
    protected void setup()
    {
      System.out.println( getLocalName() + " setting up");
     // gemeinsamer Teil
     try {
            // create the agent description of itself
            DFAgentDescription dfd = new DFAgentDescription();
            AID ss = getAID();
            //System.out.println( "Store AID=" + ss );
            dfd.setName( ss );
            DFService.register( this, dfd );

     }
     catch (Exception ex)
     {
          ex.printStackTrace();
     }

     Object args[] = this.getArguments();
     String tt = "";
     for ( int i=0; i<args.length; i++ )
     {
          tt = tt + (String) args[i] + " ";
     }

     System.out.println("Commandline Parameter ="  + tt);

     CommandlineParameterParser paramparser = new CommandlineParameterParser(tt);

     this.mSimulationConfigFile = paramparser.getParameter( AgentCommandlineParameterConst.SimulationConfig );

     this.setupForDialogMode();

     Configurator.mConfData.mDAXisReady = true;
     Configurator.mConfData.mNeedNotifyDAX = true;

    }

    private void setupForDialogMode()
    {
         System.out.println("setup StockStore in Dialog Mode");
         // add the GUI
         setupGUI();

         mBH = new StoreBehavior( this );
         mBH.setDialog( this.mMainFrame );
         mBH.setChartFrame( this.mChartFrame  );
         mBH.setWorkMode(  this.bootmode );
         addBehaviour( mBH );
    }

    /**
     * Setup the UI, which means creating and showing the main frame.
     */
    private void setupGUI()
    {

       String ss = System.getProperty("DIALOG","true");
       if ( ! ss.equals("true") )
       {
             System.out.println("****** setup FASM in text console mode  *******" );
             this.mMainFrame = new de.marketsim.gui.SimulationInitControl( this, this.mSimulationConfigFile );
             if ( this.mSimulationConfigFile != null )
             {
                this.mMainFrame.loadConfigFile( this.mSimulationConfigFile );
             }
       }
       else
       {
             // Following is the init of the Char-Frame
             mChartFrame = new  HistoryFrame();
             Dimension physicalscreen = Toolkit.getDefaultToolkit().getScreenSize();
             int ww = (int) ( physicalscreen.width * 0.98);
             int hh = (int) ( physicalscreen.height * 0.95);

             mChartFrame.setSize( ww, hh );
             mChartFrame.setLocation(1,1 );
             mChartFrame.validate();

             this.mStoreControlFrame = new StoreDialogNetworkFile( this );
             this.mStoreControlFrame.setSize( ScreenLayout.MainFrame_Width, ScreenLayout.MainFrame_Height );
             this.mStoreControlFrame.setLocation( 50, 50 );
             this.mStoreControlFrame.setVisible(true);
             this.mStoreControlFrame.validate();
             this.mStoreControlFrame.toFront();

             mChartFrame.setShowInnererWertEnabled( this.mStoreControlFrame.getShowInnererWertEnabled());

             if ( this.mSimulationConfigFile != null )
             {
                this.mStoreControlFrame.setSimulationConfig(  this.mSimulationConfigFile, true );
                this.mStoreControlFrame.loadConfigFile( this.mSimulationConfigFile );
             }

             this.mTraderGewinnProzentFrame = new GewinnProcentFrame( "Trend Agent Profit");
             this.mTraderGewinnProzentFrame.setVisible(false);
             this.mInvestorGewinnProzentFrame = new GewinnProcentFrame( "Fundamental Agent Profit" );
             this.mInvestorGewinnProzentFrame.setVisible(false);

             this.mStoreControlFrame.setHistoryFrame( this.mChartFrame );

             this.mStoreControlFrame.setInvestorGewinnProzentFrame( this.mInvestorGewinnProzentFrame);
             this.mStoreControlFrame.setNoiseTraderGewinnProzentFrame( this.mTraderGewinnProzentFrame );
             //this.mStoreControlFrame.setScreenLayout();
             this.mMainFrame = this.mStoreControlFrame;

       }
    }

    public void updateGUI()
    {

    }

    private Vector AIDs2Names(Vector pAIDList)
    {
        Vector namelist = new Vector();
        for ( int i=0;i<pAIDList.size();i++)
        {
           AID aid = (AID) pAIDList.elementAt(i);
           namelist.add(  aid.getLocalName() );
        }
        return namelist;
    }


}