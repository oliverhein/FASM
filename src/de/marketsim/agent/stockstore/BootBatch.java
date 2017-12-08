package de.marketsim.agent.stockstore;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.Vector;
import de.marketsim.config.*;
import jade.Boot;

public class BootBatch
{

  private boolean mGUI = false;
  private boolean mHasLogger = true;

  public BootBatch(boolean pGUI, boolean pHasLogger)
  {
     mGUI = pGUI;
     mHasLogger = pHasLogger;
  }

  public BootBatch()
  {
  }

  public void start()
  {

    Vector AllParameter = new Vector();

    if ( JadeConfig.getUseGUI() )
    {
       AllParameter.add("-gui");
    }

    AllParameter.add("-host");
    AllParameter.add( JadeConfig.getHostname() );
    AllParameter.add("-port");
    AllParameter.add( JadeConfig.getPort());
    AllParameter.add( "-mtp" );
    AllParameter.add( "jade.mtp.iiop.MessageTransportProtocol" );

    // Start the register manager
    // The name is fixed: simulatorregister
    AllParameter.add("simulatorregister:de.marketsim.agent.stockstore.SimulatorRegisterAgent");

    // Start the task controller
    // The name is fixed: taskcontroller
    AllParameter.add("taskcontroller:de.marketsim.agent.stockstore.BatchTaskController");

    for ( int i=0; i<AllParameter.size();i++)
    {
      System.out.println ( "args["+i+"]=" + AllParameter.elementAt(i)  );
    }

    String args[] = new String[AllParameter.size()];
    for ( int i=0; i<AllParameter.size();i++)
    {
       args[i] = (String) AllParameter.elementAt(i);
    }
    jade.Boot.main( args );
  }

  public static void main(String[] args)
  {
    BootBatch p1 = new BootBatch();
    System.out.println("call BatchBoot start()");
    p1.start();
  }
}