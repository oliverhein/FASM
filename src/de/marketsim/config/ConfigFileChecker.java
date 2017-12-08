package de.marketsim.config;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2005
 * Company:
 * @author
 * @version 1.0
 */

import java.io.*;
import java.util.*;

/**
// 1. Check if the directory exists
// 2. Gather the files with the extention ".cfg"
// 3. Check if there is at least 1. cfg
// 4. Check fileformat
// Reguired Parameter must exist.
*/

public class ConfigFileChecker
{

  private String mConfigFileName = null;
  private Properties  mParamlist = null;
  private String mRequiredParamList[] = {"",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         ""};


  public ConfigFileChecker( String pConfigFileName ) throws Exception
  {
      java.io.File  ff = new java.io.File( pConfigFileName );
      if ( ! ff.exists() )
      {
        throw new Exception ("File " + pConfigFileName + " does not exist." );
      }
      mConfigFileName = pConfigFileName;
  }

  public void CheckRequiredParameter() throws Exception
  {
      // the following parameter must exist in the config file.
     java.util.Properties paramlist = new Properties();
     java.io.FileInputStream fins = null;
     try
     {
        fins = new java.io.FileInputStream( mConfigFileName );
     }
     catch (Exception e)
     {
       // Nothing, weil File is checked
     }

     // load the parameterlist into
     mParamlist.load( fins );

     String FehlendParameter ="";

     for (int i =0; i < mRequiredParamList.length; i++)
     {
         if ( ! (mParamlist.getProperty( mRequiredParamList[i] ) == null )  )
         {
            FehlendParameter = FehlendParameter + mRequiredParamList[i] ;
         }
         else
         {
            String ss = mParamlist.getProperty( mRequiredParamList[i] );
            ss = ss.trim();
            if ( ss.length() == 0 )
            {
                FehlendParameter = FehlendParameter + mRequiredParamList[i] ;
            }
         }
     }

     if ( FehlendParameter.length() > 0 )
     {
         throw new Exception( "Parameters: " +FehlendParameter + " are not defined in the config file " + mConfigFileName);
     }
  }

}