package de.marketsim.util;

/**
 * Überschrift:   Market Simulator
 * Beschreibung:
 * Copyright:     Copyright (c) 2005
 * Organisation:
 * @author Xining Wang
 * @version 1.0
 */

import java.net.*;

public class IPGetter
{

  public IPGetter()
  {
     try
     {
       java.net.InetAddress localip = java.net.InetAddress.getLocalHost();
       System.out.print( localip.getHostName() );
     }
     catch (Exception ex)
     {
         ex.printStackTrace();
     }

  }

  public static void main(String[] args)
  {
    IPGetter IPGetter1 = new IPGetter();
  }
}