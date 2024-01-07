package de.marketsim.util;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.io.*;

public class FileChecker implements java.io.Serializable
{

  public static boolean FileExist(String pFilename)
  {
     try
     {
       java.io.FileInputStream ff = new java.io.FileInputStream(pFilename);
       ff.close();
       return true;
     }
     catch ( IOException ex)
     {
        return false;
     }
  }

}