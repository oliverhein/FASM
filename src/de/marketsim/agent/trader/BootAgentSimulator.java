package de.marketsim.agent.trader;

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
import de.marketsim.util.*;

import jade.Boot;

public class BootAgentSimulator
{

  private boolean mGUI       = false;
  private boolean mHasLogger = true;

  public BootAgentSimulator()
  {

  }

  public void start()
  {

    // it contains only the jade basic configuration parameters.
    String jadeconfigfile = System.getProperty("JADEBASICCONFIG");
    JadeConfig.Init ( jadeconfigfile );

    Vector AllParameter = new Vector();

    System.out.println( "Waiting for StockStore ....." );
    JadeMainContainerChecker cjk = new JadeMainContainerChecker();

    cjk.WaitMainContainer();

    // wait for 2 seconds
    HelpTool.pause(2000);

    String maincontainerhost = JadeConfig.getHostname();
    String maincontainerport = JadeConfig.getPort();

    AllParameter.add("-container");

    AllParameter.add("-host");
    AllParameter.add( maincontainerhost );
    AllParameter.add("-port");
    AllParameter.add( maincontainerport );
    AllParameter.add( "-mtp" );
    AllParameter.add( "jade.mtp.iiop.MessageTransportProtocol" );

    String tempname = "test";
    try
    {
       tempname = java.net.InetAddress.getLocalHost().getHostAddress() + System.currentTimeMillis();
    }
    catch (Exception ex)
    {

    }

    AllParameter.add( tempname + ":de.marketsim.agent.trader.NameFinder(remote)");

    String args[] = new String[AllParameter.size()];

    for ( int i=0; i<AllParameter.size();i++)
    {
       args[i] = (String) AllParameter.elementAt(i);
    }

    String test[] = new String[0];
    boolean started = false;
    while ( ! started )
    {
          try
          {
             jade.Boot.main( args );
             started = true;
          }
          catch (Exception ex)
          {
            try
            {
               Thread.sleep(100);
            }
            catch (Exception ex2)
            {

            }
          }
    }


  }

  public static void main(String[] args)
  {

    System.out.println("java -DJADEBASICCONFIG=configfile de.marketsim.agent.trader.BootAgentSimulator");
    BootAgentSimulator p1 = new BootAgentSimulator();
    p1.start();

  }
}