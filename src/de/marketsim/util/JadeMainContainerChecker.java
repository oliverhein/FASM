package de.marketsim.util;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2005
 * Company:
 * @author
 * @version 1.0
 */

import java.io.*;
import java.net.*;
import de.marketsim.config.*;

public class JadeMainContainerChecker
{

  public JadeMainContainerChecker()
  {

  }

  public void WaitMainContainer()
  {
      boolean connected = false;
      while ( ! connected )
      {
         connected = checkRemoteServer( JadeConfig.getHostname(), JadeConfig.getPort() );
         if ( ! connected )
         {
             try
             {
                Thread.sleep(100);
             }
             catch (Exception ex)
             {

             }
         }
      }
  }

  public boolean checkRemoteServer(String pHost, String pPort  )
  {
     java.net.Socket conn = null;
     int pp = Integer.parseInt(pPort);
     try
     {
        //System.out.println("trying to connect to Port "+ pp );
        conn = new java.net.Socket(pHost, pp );
        //InputStream ins = conn.getInputStream();
        //int j= ins.read();
        try
        {
           conn.close();
        }
        catch (Exception ex)
        {
        }

        //if ( j>0 )
        //{
        //   System.exit(0);
        //}

        return true;
     }
     catch ( Exception ex)
     {
         //ex.printStackTrace();
         return false;
     }
  }

  public static boolean checkServerPortIsUsed(int pPort) throws IOException
  {
     java.net.ServerSocket srvconn = new java.net.ServerSocket(pPort);
     try
     {
       srvconn.close();
     }
     catch (Exception ex)
     {
        // Nothing to do
     }
     return false;
  }

  public static boolean KillPossibleClient(String pHost, String pPort  )
  {
     System.out.println("Enter KillPossibleClient");
     java.net.Socket socketconn = null;
     java.net.ServerSocket srvconn = null;
     int pp = Integer.parseInt(pPort);

     try
     {
        srvconn = new java.net.ServerSocket(pp);
     }
     catch (Exception ex)
     {
        System.exit(0);
        return true;
     }

     try
     {
        CheckThread cht = new CheckThread();
        cht.start();
        while ( true )
        {
          System.out.println("Listening on port " + pp);
          socketconn = srvconn.accept();
          OutputStream os = socketconn.getOutputStream();
          String ss = "QUIT";
          os.write( ss.getBytes() );
          os.flush();
          socketconn.close();
        }
     }
     catch ( Exception ex)
     {
         return false;
     }
  }

  public static class CheckThread extends Thread
  {
    public void run()
    {
        Configurator.waitforcommand( 500 );
        System.exit(0);
    }
  }

}