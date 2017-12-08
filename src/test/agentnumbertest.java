package test;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */
import java.util.Vector;
import java.io.*;
public class agentnumbertest
{

   public static int   NetworkFileFormatError = -1;
   public static int   NetworkFileNotExist = -2;
   public static int   CanNotReadFile = -3;

   //public String mNetworkFilename = "../test/network300.txt";
   public String mNetworkFilename = "../test/network.txt";
   public agentnumbertest()

  {
  }
  public static String removeLeftspace(String ss)
  {
      if ( ss == null )
      {
         return ss;
      }
      else
      if ( ss.length() == 0 )
      {
        return ss;
      }
      else
      {
        int i=0;
        for ( i=0; i<ss.length(); i++)
        {
           if ( ss.charAt(i) != ' ' )
           {
              break;
           }
        }
        ss = ss.substring(i);
        return ss;
      }
  }


  public int checkAgentNumber()
  {
      Vector AgentName = new Vector();
      java.io.BufferedReader rd = null;
      try
      {
         rd = new java.io.BufferedReader ( new java.io.FileReader( this.mNetworkFilename));
      }
      catch ( java.io.FileNotFoundException ex)
      {
         return this.NetworkFileNotExist;
      }

      // Read 1. Line: Name List of Agents
      String agentlist = "";
      try
      {
        agentlist = rd.readLine();
      }
      catch ( IOException ex )
      {
        return this.CanNotReadFile;
      }
      // count how many agent name occur in the first line
      if ( agentlist.trim().length() == 0 )
      {
         return this.NetworkFileFormatError;
      }
      if ( agentlist.replace( '\t', ' ').trim().startsWith("v"))
      {
        System.out.println(agentlist);

        agentlist = this.removeLeftspace( agentlist );

          while ( agentlist.indexOf("  ")>=0 )
          {
            int i = agentlist.indexOf("  ");
            agentlist = agentlist.substring(0, i+1) + agentlist.substring(i+2);
          }

          //System.out.println( "TOPLine:" + agentlist );

          String ss = agentlist;
          //  It is required that ss has the format
          //  ss="V1 V2 V3 V4 V5 V6"

          int j;
          while ( ss.length() >0 )
          {
            j = ss.indexOf(" ");
            if ( j == -1 )
            {
              AgentName.add(ss);
              ss = "";
             }
            else
            {
              String oneagentname = ss.substring(0,j);
              AgentName.add( oneagentname );
              ss = ss.substring( j +1 );
            }
            System.out.println( "SS=" + ss );

          }
        /*
        if (agentlist.startsWith("*"))
        {
          System.out.println(agentlist);
          try
          {
            agentlist = rd.readLine();
          }
          catch ( IOException ex )
          {
            return this.CanNotReadFile;
          }
          if (agentlist.length()>0)
          {
            for (int n=1; n<agentlist.length()+1; n++)
            {
              String  oneagentname = "v"+ n;
              AgentName.add( oneagentname );
            }
          }
          }
*/
      }


      System.out.println(AgentName.size());
      return AgentName.size();

  }

  public static void main(String[] args)
  {
    agentnumbertest agentnumbertest1 = new agentnumbertest();
    agentnumbertest1.checkAgentNumber();
  }
}