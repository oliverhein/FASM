/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author
 * @version 1.0
 */

package de.marketsim.agent.trader;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.Profile;

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
import java.text.NumberFormat;
import de.marketsim.gui.*;
import de.marketsim.util.*;
import de.marketsim.SystemConstant;
import de.marketsim.config.Configurator;
import de.marketsim.config.AgentInitParameter;

import de.marketsim.agent.AgentCommandlineParameterConst;

public class Investor extends Agent
{

  protected String AgentLocalName = null;
  protected String Hostname = null;

  public Investor()
  {
  }

  protected void setup()
  {

      Object  args[] = this.getArguments();
      String commandlinestr = "";
      for (int i=0; i<args.length; i++)
      {
        commandlinestr = commandlinestr + args[i] + " ";
      }

      CommandlineParameterParser cpars = new CommandlineParameterParser(commandlinestr);

      AgentInitParameter agentinitconfig = (AgentInitParameter) Configurator.mConfData.mAgentInitParameter.get( this.getLocalName() );

      DepotRecord myDepotRecord = new DepotRecord();

      myDepotRecord.mAgentInitType = SystemConstant.AgentType_INVESTOR;

      /**
      if ( Configurator.istAktienMarket() )
      {
          myDepotRecord.AktienMarket_setInitCash( Configurator.mConfData.mInvestorInitCash );
          myDepotRecord.AktienMarket_setInitAktienMenge( Configurator.mConfData.mInvestorInitAktien );
      }
      else
      {
          myDepotRecord.MoneyMarket_setInitCash1( Configurator.mConfData.mInvestorInitCash );
          myDepotRecord.MoneyMarket_setInitCash2( Configurator.mConfData.mInvestorInitCash );
      }
      */

     /*** new 2006.04.10 */
     if ( Configurator.istAktienMarket() )
     {
         myDepotRecord.AktienMarket_setInitCash( agentinitconfig.mInitCash );
         myDepotRecord.AktienMarket_setInitAktienMenge( agentinitconfig.mInitAktien );
     }
     else
     {
         myDepotRecord.MoneyMarket_setInitCash1(  agentinitconfig.mInitCash );
         myDepotRecord.MoneyMarket_setInitCash2(  agentinitconfig.mInitAktien );
     }

      int SenderPartnerAnzahl    = Integer.parseInt( cpars.getParameter( AgentCommandlineParameterConst.SenderPartnerAnzahl ) );
      String Receiverpartnerlist = cpars.getParameter( AgentCommandlineParameterConst.ReceiverPartnerList  ) ;

      try
      {
          // create the agent description of itself
          DFAgentDescription dfd = new DFAgentDescription();
          AID  mAID = getAID();
          AgentLocalName = mAID.getLocalName();

          myDepotRecord.mAgentName     = AgentLocalName;
          myDepotRecord.mAgentInitType     = SystemConstant.AgentType_INVESTOR;
          myDepotRecord.mAgentInitTypeName = SystemConstant.AgentTypeName_INVESTOR;

          //System.out.println( "Starting " +AgentLocalName + "RuleName=" + RuleName );

          dfd.setName( mAID );
          DFService.register( this, dfd );

          // add the GUI
          setupGUI();

          // add a Behaviour to handle messages from StockOperatorBehavior

          OperatorBaseBehavior  mBH ;

          if ( Configurator.istAktienMarket() )
          {
            mBH= new OperatorBehaviorAktienMarket( this );
          }
          else
          {
            mBH= new OperatorBehaviorMoneyMarket( this );
          }

          mBH.setOperatorType( SystemConstant.AgentType_INVESTOR );
          mBH.setDepotRecord( myDepotRecord );

          // This command has to be performed after the mConsole is set.
          // becuase Mconsole will be used in this method.
          mBH.setSenderPartnerAnzahl( SenderPartnerAnzahl );

          System.out.println("AbschlagProzent=" +  agentinitconfig.mAbschlagProzent );

          mBH.setInitParameter( agentinitconfig );

          System.out.println ( AgentLocalName +" my ReceiverPartner=" +  Receiverpartnerlist );
          mBH.setReceiverPartnerList( Receiverpartnerlist );
          addBehaviour( mBH );
      }
      catch (Exception e)
      {
          System.out.println( "Exception: " + e );
          e.printStackTrace();
      }
   }

   public void setupGUI()
   {
        /*
        this.mConsole = new InvestorConsole( this );
        this.mConsole.setTitle( "Investor Console:" + this.AgentLocalName );
        this.mConsole.setSize(400, 350 );
        this.mConsole.setLocation(710, 400 );
        this.mConsole.setVisible( this.DisplayConsole );
        // start operator
        this.mConsole.InitDepotParameterFromGUI();
     */

   }

 }