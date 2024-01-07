package de.marketsim.config;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2005
 * Company:
 * @author   Xining Wang
 * @version 1.0
 */

import java.util.*;
import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import de.marketsim.util.HelpTool;
import de.marketsim.config.CFGFilter;

public class ConfigFileCollector
{

  public static String       mConfigdir = null;
  public static ConfigFileValidation[]     mXMLFileList;
  // files gefunden from in the directory

  public static Hashtable    mConfigFileRepeator = new Hashtable();
  public static String[]     mConfigFileList4Simulation;

  public static int          mConfigFileNumber =0;
  public static int          mFileIndex ;

  //  public static String       mConfigFileExtension = ".XML";

  /*
     constructor of the config file checker
  */

  public  ConfigFileCollector( String pConfigDir, String pExtension ) throws Exception
  {
       // change the default extension .cfg of config file
       //mConfigFileExtension = pExtension.toUpperCase();
       mConfigdir = pConfigDir;
       java.io.File  fdir =  new java.io.File(pConfigDir);
       if ( ! fdir.exists() )
       {
           throw new Exception("The directory " + mConfigdir + " does not exist.!" );
       }

       if ( ! fdir.isDirectory() )
       {
           throw new Exception( mConfigdir + " is not a directory." );
       }

       java.io.FilenameFilter myfilter =  new CFGFilter( pExtension );

       String templist[] = fdir.list( myfilter );

       if ( templist.length == 0 )
       {
           throw new Exception( mConfigdir + " does not contain any "+pExtension+" file." );
       }

       mXMLFileList = new ConfigFileValidation [ templist.length ];

       //mConfigFileRepeator.clear();
       for (int i=0; i<templist.length; i++)
       {
          String ss = templist[i];
          if ( this.isXmlConfigFile( ss ) )
          {
            // default 1 mal
            //mConfigFileRepeator.put(templist[i] , new Integer(1));
            mXMLFileList[i] = new ConfigFileValidation(templist[i], true);
          }
          else
          {
            mXMLFileList[i] = new ConfigFileValidation(templist[i], false);
          }
       }

       mFileIndex = 0;
       mConfigFileNumber = mXMLFileList.length;
 }

   /**
   * get the number of config files
   */

  public static int getXmlFileNumber()
  {
       return mConfigFileNumber;
  }

   /**
   * reset FileIndex
   */

  public static void  resetFileIndex()
  {
       mFileIndex = 0;
  }

  /**
   * set the file list for simulation
   */
  public static void setConfigFiles4Simulation(String[] pList, int[] pAnzhalList)
  {
      // clear all old objects
      mConfigFileRepeator.clear();
      mConfigFileList4Simulation = pList;
      for ( int i=0; i<pList.length; i++)
      {
        mConfigFileRepeator.put( mConfigFileList4Simulation[i], new Integer( pAnzhalList[i]) );
      }
  }

  /**
   * get the list of all config files for simulation
   */
  public static String[] getConfigFiles4Simulation()
  {
       return mConfigFileList4Simulation;
  }

  /**
   * get Anzhal der Konfigfiles fuer simulation
   */
  public static int getSimulationTimesOneConfigFile( String pConfigName )
  {
       Integer JJ = ( Integer ) mConfigFileRepeator.get(pConfigName);

       if ( JJ == null )
       {
         return 0;
       }

       return JJ.intValue();
  }

  /**
   * get one real config file name
   */
  public static ConfigFileValidation getXmlFile(int pIndex)
  {
      return  mXMLFileList[ pIndex ];
  }

 public static boolean isXmlConfigFile(String pXmlFileName)
  {
    DocumentBuilderFactory docBFac;
    DocumentBuilder docBuild;
    Document doc = null;
    try
    {
       docBFac = DocumentBuilderFactory.newInstance();
       docBuild = docBFac.newDocumentBuilder();
       doc = docBuild.parse( pXmlFileName );
       Element rootelement = doc.getDocumentElement();
       String ss = rootelement.getNodeName();
       if ( ss.equals("SimulationConfig") )
       {
         return true;
       }
       else
       {
         return false;
       }
    }
    catch (Exception ex)
    {
       return false;
    }
}

}