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

public class InputTool
{

  public InputTool()
  {
  }

  public static void dopause(int pSecond)
  {
      try
      {
         Thread.sleep( pSecond * 1000 );
      }
      catch (Exception ex)
      {
      }
  }

  public static String getCMDLINE()
  {
    try
    {
       java.io.BufferedReader  ins = new java.io.BufferedReader( new java.io.InputStreamReader( System.in ));
       String ss = ins.readLine();
       return ss;
    }
    catch (IOException ex)
    {
      return null;
    }
  }

}