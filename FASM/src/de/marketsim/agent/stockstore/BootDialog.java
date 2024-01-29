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
import de.marketsim.agent.AgentCommandlineParameterConst;
import de.marketsim.util.FileTool;


public class BootDialog
{
  private boolean mGUI = false;
  private boolean mHasLogger = true;

  public BootDialog(boolean pGUI, boolean pHasLogger)
  {
     mGUI = pGUI;
     mHasLogger = pHasLogger;
  }

  public BootDialog()
  {
  }

  public void start()
  {

    Vector AllParameter = new Vector();

    if ( JadeConfig.getUseGUI() )
    {
       AllParameter.add("-gui");
    }

    /*
    java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());
    String ss = ts.toString().substring(0,19);
    ss = ss.replace(' ', '_');
    ss = ss.replace(':', '_');
    ss = ss.replace('.', '_');
    ss = ss+"dialog";

    Configurator.setTestSerieStarttime( ss );

    Configurator.mConfData.mMainLogDirectory = ss ;

    FileTool.createDirectory(ss);
    */

    String maincontainerhost = JadeConfig.getHostname();
    String maincontainerport = JadeConfig.getPort();

    AllParameter.add("-host");
    AllParameter.add( maincontainerhost );
    AllParameter.add("-port");
    AllParameter.add( maincontainerport );
    AllParameter.add( "-gui" );

    AllParameter.add( "-mtp" );
    AllParameter.add( "jade.mtp.iiop.MessageTransportProtocol" );
    // start two Agents: simulatorregister + StockStore

    // The name is fixed: simulatorregister
    AllParameter.add("simulatorregister:de.marketsim.agent.stockstore.SimulatorRegisterAgent;" + 
    		"DAX:de.marketsim.agent.stockstore.Store("+"-" + AgentCommandlineParameterConst.SimulationConfig +" " + System.getProperty( AgentCommandlineParameterConst.SimulationConfig ) + ");" + "test:de.marketsim.agent.trader.NameFinder( local )");

    
    // Start StockStore
//    AllParameter.add("DAX:de.marketsim.agent.stockstore.Store("+
 //                    "-" + AgentCommandlineParameterConst.SimulationConfig +" " + System.getProperty( AgentCommandlineParameterConst.SimulationConfig ) + ")"
 //                    );

    // Start AgentSimulator SM1 within Börse

//    if  ( JadeConfig.getStartSimulator() )
//    {
//        AllParameter.add( "test:de.marketsim.agent.trader.NameFinder( local )" );
//    }

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
    System.out.println("BootDialog ");
    BootDialog p1 = new BootDialog();
    p1.start();

  }
}