/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author Wang
 * @version 1.0
 */

package de.marketsim.test;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.Profile;

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
import java.text.NumberFormat;

public class SimpleAgent extends Agent
{

    SimpleBehavior  mBH = null;
    TestFrame       mDialog = null;
    //---------------------------------
    public SimpleAgent()
    {
    }
    /**
     * Setup the agent. Registers with the DF, and adds a behaviour to
     * process incoming messages.
     */
    protected void setup()
    {

     try {
            System.out.println( getLocalName() + " setting up");
            // create the agent descrption of itself
            DFAgentDescription dfd = new DFAgentDescription();
            AID ss = getAID();
            System.out.println( "Store AID=" + ss );
            dfd.setName( ss );
            DFService.register( this, dfd );

            System.out.println("getLocalName()= " + this.getLocalName())  ;
            System.out.println("getHap()= " + this.getHap())  ;
            System.out.println("getName= " + this.getName() )  ;

            System.out.println("getAMS= " + this.getAMS() );
            System.out.println("QueueSize" + this.getQueueSize() );

            mBH = new SimpleBehavior( this );
            // add the GUI
            setupUI();
            addBehaviour( mBH );
      }
        catch (Exception e) {
            System.out.println( "See:" + e );
            e.printStackTrace();
        }
    }

    /**
     * Setup the UI, which means creating and showing the main frame.
     */
    private void setupUI()
    {
        // Following is the init of the Char-Frame
        mDialog = new  TestFrame();
        mDialog.setMyname(  this.getLocalName()  );
        mDialog.setTitle("SimpleAgent");
        mDialog.setSize( 500, 300 );
        mDialog.setLocation(1,1);
        mDialog.setBehavior( this.mBH );
        mDialog.setVisible(true);

    }

}