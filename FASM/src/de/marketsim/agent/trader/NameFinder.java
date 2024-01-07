package de.marketsim.agent.trader;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.Date;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;

import jade.wrapper.*;

import de.marketsim.message.*;
import de.marketsim.SystemConstant;

public class NameFinder extends Agent
{
    public static String mNameRequtested = null;
    public NameFinder()
    {

    }

    protected void setup()
    {

      String mode ="remote";
      Object  args[] = this.getArguments();
      if ( args.length == 0 )
      {
        // Wenn kein Parameter definiert ist, ist Remote Mode,
        // Agent is started from a sub-container.
      }
      else
      {
         mode = (String) args[0];
      }

        System.out.println( "Mode=" + mode );

        /** Registration with the DF */
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("NameFinder");
        sd.setName( getName() );
        sd.setOwnership("TILAB");
        dfd.setName( getAID() );
        dfd.addServices(sd);

        try {
            DFService.register(this,dfd);
            System.err.println(getLocalName()+" registered with DF ");
        } catch (FIPAException e) {
            System.err.println(getLocalName()+" registration with DF unsucceeded. Reason: "+e.getMessage());
            doDelete();
            return;
        }

       ACLMessage aclmsg = new ACLMessage( ACLMessage.INFORM );

       MessageWrapper msgwrp = new MessageWrapper();
       msgwrp.mMessageType = SystemConstant.MessageType_RegistetrCommunicationManagerWithName;
       try
       {
         aclmsg.setContentObject(  msgwrp  );
       }
       catch (Exception ex)
       {
         ex.printStackTrace();
       }

       aclmsg.addReceiver( new AID("simulatorregister", false )  );
       this.send(aclmsg);
       System.out.println("send the RequestName commmand");

       ACLMessage response = this.blockingReceive();
       try
       {
          msgwrp = ( MessageWrapper ) response.getContentObject();
       }
       catch (Exception ex)
       {
         ex.printStackTrace();
       }
       System.out.println("got Response ");
       String newname = (String) msgwrp.mMessageContent;
       System.out.println("I get a new name:" +newname);

       // Start CounicationManager with this name
      try
      {
           jade.wrapper.PlatformController container = this.getContainerController();
           // get a container controller for creating new agents

           String cmdparam[] = new String[1];
           cmdparam[0] = mode;
           container.createNewAgent( newname, "de.marketsim.agent.trader.AgentSimulatorManager", cmdparam ).start();
           System.out.println( "AgentSimulatorManager "+ newname + " is started." );
       } catch (Exception any)
       {
           any.printStackTrace();
       }

       dfd = new DFAgentDescription();
       dfd.setName( this.getAID() );
       try
       {
         //  DFService.deregister( this, dfd );
       }
       catch (Exception ex)
       {
          ex.printStackTrace();
       }
       // delay 1 second
       try
       {
          Thread.sleep(1000);
       }
       catch (Exception ex)
       {

       }
        // this.doDelete();
   }

}


