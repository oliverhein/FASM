package de.marketsim.config;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.*;
import java.io.*;
import de.marketsim.util.FileChecker;
import de.marketsim.util.HelpTool;

public class NetworkFileLoader implements java.io.Serializable
{
  public Hashtable mNodeCommunicationList = new Hashtable();
  private Hashtable  mAgentListWithtype = null;
  private int mNumberFundamentalInvestor;
  private int mNumberNoiseTrader;
  private int mNumberBlankoAgent;

  private int mNumberTotalAgent;
  private int mNumberTotalAgent_FromNodeTypeFile ;

  private  Vector mNameListFundamentalInvestor = new Vector();
  private  Vector mNameListNoiseTrader         = new Vector();
  private  Vector mNameListBlankoAgent         = new Vector();

  public boolean mNetworkFileIsProcessed = false;

  Vector mCommunicationPairList = new Vector();
  Vector AllAgent               = new Vector();
  String mNetworkFilename       = null;
  String mNetworkNodeTypeFilename = null;

  public NetworkFileLoader(String pFilename)
  {

     this.mNetworkFilename = pFilename;
     int j = this.mNetworkFilename.indexOf(".");
     this.mNetworkNodeTypeFilename = this.mNetworkFilename.substring(0,j) + ".clu";

  }

  public boolean checknetworkfile()
  {
     boolean p1 = FileChecker.FileExist( this.mNetworkFilename );
     boolean p2 = FileChecker.FileExist( this.mNetworkNodeTypeFilename );
     return  p1 && p2;
  }

  // Check the total number of nodes of network
  public int checkAgentNumber() throws Exception
  {
    java.io.BufferedReader rd = null;
    try
    {
       rd = new java.io.BufferedReader ( new java.io.FileReader( this.mNetworkFilename));
    }
    catch ( java.io.FileNotFoundException ex)
    {
       throw new Exception("Network file " +  this.mNetworkFilename +" can not be found. Please check (1) if the network file exist (2) if the network file name is your expected file name." );
    }

    // Read until *Vertices

    String ss = null;
    boolean weiter = true ;

    do
    {
          try
          {
            ss = rd.readLine();
            ss = ss.replace('\t', ' ');
            if ( ss.startsWith("*Vertices") )
            {
              weiter = false;
              int j=ss.indexOf(" ");
              String ss2 = ss.substring(j);
              // trime all space (especially left space)
              ss2 = HelpTool.TrimString(ss2, ' ');
              rd.close();
              this.mNumberTotalAgent =Integer.parseInt(ss2);

              System.out.println("Network Nodes =" + this.mNumberTotalAgent );

              return mNumberTotalAgent;
            }
          }
          catch ( IOException ex )
          {
              try
              {
                rd.close();
              }
              catch (Exception ex2)
              {
                 // nothing
              }
              throw new Exception ("Network file " + this.mNetworkFilename +
                                   " has format error. *Vertices can not be found before the beginning of data parts.");
          }
    }  while ( weiter );
    System.out.println("Error, *Vertices can not be found.");
    return 0;
  }

  public int getFundamentalInvestor()
  {
     return mNumberFundamentalInvestor;
  }

  public int getNoiseTrader()
  {
    return mNumberNoiseTrader;
  }

  public int getBlankoAgent()
  {
    return  mNumberBlankoAgent;
  }

  public int getAgentTotal()
  {
    return this.mNumberTotalAgent;
  }

  public Vector getNameListFundamentalInvestor ()
  {
    return mNameListFundamentalInvestor;
  }


  public Vector getNameListNoiseTrader()
  {
    return mNameListNoiseTrader;
  }

  public Vector getNameListBlankoAgent()
  {
    return mNameListBlankoAgent;
  }


  public Hashtable getAgentListWithType()
  {
       if ( this.mAgentListWithtype == null )
       {
           this.mAgentListWithtype = new Hashtable();
           for ( int i=0; i< this.mNameListFundamentalInvestor.size(); i++)
           {
             this.mAgentListWithtype.put( this.mNameListFundamentalInvestor.elementAt(i), "I" );
           }

           for ( int i=0; i< this.mNameListNoiseTrader.size(); i++)
           {
             this.mAgentListWithtype.put( this.mNameListNoiseTrader.elementAt(i), "N" );
           }

           for ( int i=0; i< this.mNameListBlankoAgent.size(); i++)
           {
             this.mAgentListWithtype.put( this.mNameListBlankoAgent.elementAt(i), "B" );
           }
       }
       return this.mAgentListWithtype;

  }

  public Vector getCommunicationPairList()
  {
    //System.out.print(this.mCommunicationPairList.size() + " edges");
    //return this.mCommunicationPairList;
    // enthalten die doppelte Kante.

    // filter doppelte Kante
    Hashtable hs = new Hashtable();
    hs.put(this.mCommunicationPairList.elementAt(0), "" );
    for ( int j=1; j<this.mCommunicationPairList.size(); j++)
    {
       String f1 = ( String ) this.mCommunicationPairList.elementAt(j);
       // Format:  V2;V5
       int p = f1.indexOf(";");
       String f2 = f1.substring( p+1 )+";" + f1.substring(0,p);
       // so f2 = "V5;V2"
       if ( ! hs.containsKey( f2 ) )
       {
           hs.put(f1, "");
       }
    }

    Object ll[] = hs.keySet().toArray();
    Vector dd = new Vector();
    for ( int i=0; i< ll.length; i++)
    {
       dd.add(ll[i]);
    }
    System.out.println( dd.size() + " edges");
    return dd;
  }

  public void processnetworkfile() throws Exception
  {
     java.io.BufferedReader rd = null;
     try
     {
        rd = new java.io.BufferedReader ( new java.io.FileReader( this.mNetworkFilename));
     }
     catch ( java.io.FileNotFoundException ex)
     {
         throw new Exception("Network file " +  this.mNetworkFilename +" can not be found. Please check (1) if the network file exist (2) if the network file name is your expected file name." );
     }

     // Read until *Vertices

     String ss = null;
     boolean weiter = true ;
     do
     {
           try
           {
             ss = rd.readLine();
             if ( ss.startsWith("*Vertices") )
             {
               weiter = false;
             }
           }
           catch ( IOException ex )
           {
             throw new Exception ("Network file " + this.mNetworkFilename +
                                  " has format error. *Vertices can not be found before the beginning of data parts.");
           }
     }  while ( weiter );
     ss = ss.replace('\t', ' ');
     int j = ss.indexOf(" ");
     String ss2 = ss.substring(j+1);
     ss2 = HelpTool.TrimString(ss2, ' ' );
     this.mNumberTotalAgent = Integer.parseInt(ss2);

     // Read until *Matrix
     weiter = true ;
     do
     {
           try
           {
             ss = rd.readLine();
             if ( ss.startsWith("*Matrix") )
             {
               weiter = false;
             }
           }
           catch ( IOException ex )
           {
             throw new Exception ("Network file " + this.mNetworkFilename +
                                  " has format error. Mark *Matrix can not be found.");
           }
     }  while ( weiter );

     /////////////////////////////////////////////////////////////////////////////////////
     java.io.BufferedReader rd_nodetype = null;
     try
     {
         rd_nodetype = new java.io.BufferedReader ( new java.io.FileReader( this.mNetworkNodeTypeFilename));
     }
     catch ( java.io.FileNotFoundException ex)
     {
         throw new Exception("Network Node type file " +  this.mNetworkNodeTypeFilename +" can not be found. Please check if the network node type file exist. Pay attention: the extension of  network node type file is .clu. In order to avoid problem with Unix File System, please use extension always with small letter." );
     }

     // jump 1. line: wie  *Vertices	50
     ss = rd_nodetype.readLine();
     ss = ss.replace('\t', ' ');
     j = ss.indexOf(" ");
     ss2 = ss.substring(j+1);

     this.mNumberTotalAgent_FromNodeTypeFile = Integer.parseInt(ss2);

     /* NodeTypeFile Format
     Number Agenttype ...
     *Vertices	50
     1
     2
     3
     4
     5

     1: BlankAgent
     2: Investor
     3: NoiseTrader
     File Format
     !!!! Achtung !!!!
     AgentName faengt mit V1 statt V0 an.

     */

     System.out.println(this.mNumberTotalAgent_FromNodeTypeFile + " Node Type Definition will be read.");

     for ( int i=0; i< this.mNumberTotalAgent_FromNodeTypeFile; i++)
     {
        ss = rd_nodetype.readLine();
        System.out.println( i + ". " + ss );
        String typestr = ss;
        String agentname = "V" + (i+1);

        if ( typestr.charAt(0) == '0' )
        {
           this.mNameListBlankoAgent.add( agentname  );
        }
        else
        if ( typestr.charAt(0) == '1' )
        {
          this.mNameListFundamentalInvestor.add( agentname  );
        }
        else
        {
          this.mNameListNoiseTrader.add( agentname  );
        }
     }
     rd_nodetype.close();
     // End of reading Node Type

     this.mNumberBlankoAgent         = this.mNameListBlankoAgent.size();
     this.mNumberFundamentalInvestor = this.mNameListFundamentalInvestor.size();
     this.mNumberNoiseTrader         = this.mNameListNoiseTrader.size();

     System.out.println("BlankoAgent         =" + this.mNumberBlankoAgent );
     System.out.println("FundamentalInvestor =" + this.mNumberFundamentalInvestor );
     System.out.println("NoiseTrader         =" + this.mNumberNoiseTrader );

     ////////////////////////////////////////////////////////////////////
     int SenderCounter[] = new int[ this.mNumberTotalAgent ] ;
     for ( int i=0; i<this.mNumberTotalAgent;i++ )
     {
         int k = i+1;
         AllAgent.add("V"+k);
         SenderCounter[i] = 0;
     }

     CommunicationModel commmodel = new CommunicationModel();

     this.mCommunicationPairList.clear();
     int Lineno = 1;
     int p;

     ////////////////////////////////////////////////////////////////////
     // Process network node communication relation
     ///////////////////////////////////////////////////////////////////

     // read 1. line after *Matrix
     // Line format:  0 1 1 1 1
     try
     {
       ss = rd.readLine();
     }
     catch (Exception ex)
     {
       throw new Exception ("Network file " + this.mNetworkFilename +
                            " has format error. After *Matrix, there is no any data.");
     }


     while (  (ss != null) && ( ss.length() > 0) )
     {
         ss = this.transfer2RequiredFormat(ss);
         //System.out.println("ss=" + ss);
         String sendername = "V"+Lineno;

         //Beispiel  ss="0 0 1 1 0 0 0 1 "
         SimpleOne2ManyCommunication oneComm = new SimpleOne2ManyCommunication( sendername );

         p = ss.indexOf("1",0);
         while ( p >= 0 )
         {
             int colnumber = p / 2 ;
             //System.out.println( "colnumber = " + colnumber );
             //System.out.println( "AllAgent.size = " + AllAgent.size() );
             String onereceivername = (String) AllAgent.elementAt( colnumber );
             SenderCounter[colnumber] = SenderCounter[colnumber] + 1;
             oneComm.addOnePartner( onereceivername );
             this.mCommunicationPairList.add( sendername+";" + onereceivername );
             p = ss.indexOf("1",p+1);
         }

         commmodel.addOne2ManyCommunication( oneComm );

         try
         {
           ss = rd.readLine();
         }
         catch (Exception ex)
         {
           throw new Exception ("Network file " + this.mNetworkFilename +
                                " has format error. The data is not complete. " + this.mNumberTotalAgent + " lines in data part are expected.");
         }
         Lineno++;
     }

     ///  LineNo must be greater than this.mNumberTotalAgent
     if ( Lineno <= this.mNumberTotalAgent )
     {
        throw new Exception ("Network file " + this.mNetworkFilename +
                          " has format error. The data is not complete. " + this.mNumberTotalAgent +
                          " lines in data part are expected. Only " + (Lineno-1) + " lines are read.");
     }

     Vector commlist = commmodel.getOne2ManyCommunicationList();
     //System.out.println( "commlist.size()=" + commlist.size() );

     //Save the number of Sender of every Agent to its One2ManyCommunication
     this.mNodeCommunicationList.clear();

    System.out.println( "SenderCounter.size() =" + SenderCounter.length );
    System.out.println( "commlist.size()=" +  commlist.size());

     for (int i=0; i<commlist.size(); i++)
     {
         SimpleOne2ManyCommunication oneComm = (SimpleOne2ManyCommunication) commlist.elementAt(i);
         oneComm.setMySenderPartner( SenderCounter[i] );
         this.mNodeCommunicationList.put( oneComm.mMasterName, oneComm  );
     }

     this.mNetworkFileIsProcessed = true;
     System.out.println( "Processing of Network File is finished." );

  }

  public Hashtable getNodeCommunicationList()
  {
     if ( this.mNodeCommunicationList.isEmpty() )
     {
       try
       {
          this.processnetworkfile();
       }
       catch (Exception ex)
       {
         ex.printStackTrace();
       }
     }
     return this.mNodeCommunicationList;
  }

  private String transfer2RequiredFormat(String pOriginal)
  {
     String ss = pOriginal;
     ss = ss.replace( '\t', ' ');
     while ( ss.indexOf("  ") >=0 )
     {
        ss = ss.replaceAll("  ", " ");
     }
     ss = ss.trim();
     ss = ss + " ";
     if ( ss.charAt(0) == ' ' )
     {
       ss = ss.substring(1);
     }
     return ss;
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

  public static void main(String args[])
  {

      NetworkFileLoader pp = new NetworkFileLoader( args[0] );
      try
      {
        pp.checkAgentNumber();
        pp.processnetworkfile();
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
  }

}