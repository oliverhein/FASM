package de.marketsim.config;

/**
 * Überschrift:   Market Simulator
 * Beschreibung:
 * Copyright:     Copyright (c) 2005
 * Organisation:
 * @author Xining Wang
 * @version 1.0
 */

import java.io.*;
import java.util.Properties;
import java.util.Vector;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class JadeConfig
{
  // Default value
  public static String mHostname = "localhost";
  public static String mPort     = "1099";
  public static Properties mJadeConfigParameter = null;
  public static boolean  mUseGUI = false;
  public static boolean  mFileReadSuccessful;
  public static boolean  mStartSimulator = true;  // Default

  public JadeConfig()
  {

  }

  public static void Init(String pJadeConfigFile )
  {
     mJadeConfigParameter = new Properties();
     try
     {
       java.io.FileInputStream  fins = new java.io.FileInputStream( pJadeConfigFile );
       mJadeConfigParameter.load(fins);

       mFileReadSuccessful = true;
     }
     catch (Exception ex)
     {
          String ss = System.getProperty("DIALOG", "true");
          if ( ss.equalsIgnoreCase("true") )
          {
              //System.out.println("Can not read jade configuration file:" +  pJadeConfigFile );
              JOptionPane.showMessageDialog(null,"Can not read jade configuration file:" +  pJadeConfigFile,"Error", JOptionPane.ERROR_MESSAGE);
              System.exit(-1);
          }
          else
          {

               System.out.println("*******************************************");
               System.out.println("* Error                                   *");
               System.out.println("* Can not read jade config file           *");
               System.out.println("* " + pJadeConfigFile + "*");
               System.out.println("*******************************************");
               System.exit(-1);

          }
     }

     //mJadeConfigParameter.list( new PrintWriter( new java.io.FileWriter("") ) );

     String ss1 = "";
     try
     {
       ss1 = java.net.InetAddress.getLocalHost().getHostName();
     }
     catch (Exception ex)
     {

     }

     //JOptionPane.showMessageDialog(null,ss1,"Error", JOptionPane.INFORMATION_MESSAGE);

     // mHostname = mJadeConfigParameter.getProperty("Hostname", "");

     mHostname = ss1;

     mPort = mJadeConfigParameter.getProperty("Port","1099");
     String ss = mJadeConfigParameter.getProperty("UseGUI","false");
     if ( ss.equalsIgnoreCase("true") )
     {
        mUseGUI = true;
     }
     else
     {
        mUseGUI = false;
     }

     ss = mJadeConfigParameter.getProperty("StartSimulator","true");
     mStartSimulator = Boolean.valueOf(ss).booleanValue();

  }

  public static String getHostname()
  {
     return mHostname;
  }

  public static String getPort()
  {
     return mPort;
  }

  public static boolean getUseGUI()
  {
     return mUseGUI;
  }

  public static boolean getStartSimulator()
  {
     return mStartSimulator;
  }


}