package de.marketsim.util;

/**
 * Überschrift:   Market Simulator
 * Beschreibung:
 * Copyright:     Copyright (c) 2005
 * Organisation:
 * @author Xining Wang
 * @version 1.0
 */

import java.util.*;

/*

Beschränkung: Der Wert von Parameter darf kein "-" enthalten.

*/

public class CommandlineParameterParser
{

  Properties prop = new Properties();
  Vector     paranamelist = new Vector();

  public CommandlineParameterParser(String pCommandline)
  {
       String ss = pCommandline;
       int j1 = -1;
       int j2 = -1;
       int j3 = -1;

       j1 = ss.indexOf("-");
       while ( j1 >=0 )
       {
           j2 = ss.indexOf(" ");
           String paraname = ss.substring(j1+1,j2);
           j3 = ss.indexOf("-", j2+1);
           if ( j3 > j2+1 )
           {
              String paravalue = ss.substring(j2+1, j3-1);
              ss = ss.substring(j3);
              j1 = ss.indexOf("-");
              prop.setProperty( paraname, paravalue );
              this.paranamelist.add( paraname );
           }
           else
           if ( j3 >=0 )
           {
              prop.setProperty( paraname, "" );
              this.paranamelist.add( paraname );
              ss = ss.substring(j3);
              j1 = ss.indexOf("-");
           }
           else
           {
              String paravalue = ss.substring(j2+1);
              prop.setProperty( paraname, paravalue );
              this.paranamelist.add( paraname );
              j1 = j3;
           }
       }

       /*
       for ( int i=0; i< this.paranamelist.size(); i++)
       {
           System.out.println( (String) this.paranamelist.elementAt(i) + ":" + prop.getProperty( (String) this.paranamelist.elementAt(i) )  );
       }
       */
  }

  public  Properties getProperties()
  {
     return this.prop;
  }

  public  Vector getParameterNames()
  {
     return this.paranamelist;
  }

  public String getParameter(String pParameterName )
  {
     return this.prop.getProperty(pParameterName);
  }

  public String getParameter(String pParameterName, String pDefault )
  {
     return this.prop.getProperty(pParameterName, pDefault);
  }


  public static void main(String args[])
  {
     // self test
     CommandlineParameterParser pp = new CommandlineParameterParser("-User -IP 1.2.3.4 -Key -Host  ");
     System.out.print( pp.getParameter("IP") );
  }


}//end class PingAgent
