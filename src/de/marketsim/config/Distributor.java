package de.marketsim.config;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.*;

public class Distributor
{
  //  Gleichmaessig Aufteilung
  //  Total:   Die Anzahl aufzuteilen
  //  parts:   Wie viele Teile sollen geteilt werden
  //  return:  Die Array in der die Aufteilungen enthalten sind.

  public static int[] getDistribution(int pTotal, int pParts)
  {
      int tt[] = new int[pParts];
      int jj = pTotal / pParts;
      int rest = pTotal - jj * pParts;
      for ( int i=0; i<pParts; i++)
      {
         tt[i] = jj;
      }
      for ( int i=0; i<rest; i++)
      {
         tt[i] = tt[i] + 1;
      }
      return tt;
  }

  //  Aufteilung as Required Procent
  //  Total:   Die Anzahl aufzuteilen
  //  pProcent[]:  Procenten
  //  return:  Die Array in der die Aufteilungen enthalten sind.

  public static int[] getDistribution(int pTotal, int pProcent[]) throws Exception
  {
      int sum = 0;
      for ( int i=0; i<pProcent.length; i++)
      {
         sum = sum + pProcent[i];
      }

      if ( sum > 100 )
      {
          throw new Exception("The sum of all procent parts is over 100.");
      }
      else
      if ( sum < 100 )
      {
            throw new Exception("The sum of all procent parts is less than 100.");
      }

      int tt[] = new int[pProcent.length];
      sum = 0;

      // try 1: with round

      for ( int i=0; i<pProcent.length; i++)
      {
         tt[i] = (int) Math.round (  pTotal * pProcent[i] / 100.0 );
         sum = sum + tt[i];
      }

      if ( sum == pTotal )
      {
         return tt;
      }
      else
      if ( sum < pTotal )
      {
            int rest = pTotal - sum ;
            for ( int i=0; i<rest; i++)
            {
               tt[i] = tt[i] + 1;
            }
            return tt;
      }
      else
      {
          // erneut Verteilen
          sum = 0;
          for ( int i=0; i<pProcent.length; i++)
          {
             tt[i] =   pTotal * pProcent[i] / 100 ;
             sum = sum + tt[i];
          }
          int rest = pTotal - sum ;
          for ( int i=0; i<rest; i++)
          {
             tt[i] = tt[i] + 1;
          }
          return tt;
      }
  }

  /**
   * anzeige die Aufteilung
   */
  public static void DisplayDistribution( int Data[] )
  {
      for ( int i=0; i<Data.length ; i++)
      {
         System.out.println( i + ". = " +  Data[i] );
      }
  }

    /**
   * anzeige die Aufteilung
   */
  public static void DisplayDistribution( Vector Data )
  {
      for ( int i=0; i<Data.size() ; i++)
      {
         DistributionPair pp = (DistributionPair) Data.elementAt(i);
         System.out.println( i + ". = " +  pp.mName + "-->" + pp.mTarget
);
      }
  }


  public static Vector getDistribution(Vector pList, Vector pParts)
  {
      int tt[] = new int[ pParts.size() ];
      int jj = pList.size() / pParts.size();
      int kk = pList.size() - jj * pParts.size();
      for ( int i=0; i<pParts.size(); i++)
      {
         tt[i] = jj;
      }
      for ( int i=0; i<kk; i++)
      {
         tt[i] = tt[i] + 1;
      }

      Vector Distributionlist = new Vector();

      // Speichern die Aufteilung
      kk = 0;
      for ( int i=0; i<pParts.size(); i++)
      {
         for ( int j=0; j< tt[i]; j++)
         {
             Distributionlist.add( new DistributionPair(
             pList.elementAt(kk),  pParts.elementAt(i) )  );
             kk = kk + 1;
         }
      }
      return Distributionlist;
  }

 // Distribute Agent to different Simulator
 // Create a List like
  // SIMULATOR1;V1;Investor;5;V2,V6
  // SIMULATOR1;V2;Investor;5;V8,V9
  // SIMULATOR2;V3;NoiseTrader;1;V2,V6
  // SIMULATOR2;V4;NoiseTrader;3;V4,V7

 public static String[] createDistributionList( Hashtable pNetworkNodeCommunicationList, Hashtable pAgentTypeList, String pAgentSimulatorList[], int pRandomTrader  )
 {

   System.out.println("NetworkCommunicationList.size()=" + pNetworkNodeCommunicationList.size() );

   Enumeration en = pAgentTypeList.keys();
   while ( en.hasMoreElements() )
   {
       String agentname = ( String ) en.nextElement();

       SimpleOne2ManyCommunication comm = (SimpleOne2ManyCommunication) pNetworkNodeCommunicationList.get( agentname);

       System.out.println( "agentname = "+ agentname );
       System.out.println( "comm  = "+ comm );
       String agenttype = (String) pAgentTypeList.get( agentname );

       // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
       // Nur consider these three Tpyes;  I,B,N
       // If new type is added, this methode has to be updated.
       // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

       if ( agenttype.equals("I") )
       {
           comm.setAgentType("INVESTOR");
       }
       else
       if ( agenttype.equals("B") )
       {
           comm.setAgentType("BLANKOAGENT");
       }
       if ( agenttype.equals("N") )
       {
           comm.setAgentType("NOISETRADER");
       }
   }

   // Aufruf Distributor fuer Gleichmaessige Aufteilung
   int TotalNodesCount   =  pAgentTypeList.keySet().size();
   int    Aufteilungen[] = getDistribution( TotalNodesCount , pAgentSimulatorList.length );

   Object tt[] = pAgentTypeList.keySet().toArray();
   int k = tt.length;
   String AllAgentNames[] = new String [ k ];
   for ( int i=0; i<k; i++)
   {
     AllAgentNames[i] = ( String ) tt[i];
   }

   Vector tempdistribution = new Vector();

   int kk=0;
   for ( int SimulatorIndex = 0; SimulatorIndex< pAgentSimulatorList.length; SimulatorIndex++)
   {
       for ( int jj=0; jj<Aufteilungen[ SimulatorIndex]; jj++)
       {
         String name = AllAgentNames[kk];
         SimpleOne2ManyCommunication comm = (SimpleOne2ManyCommunication) pNetworkNodeCommunicationList.get( name );
         String simulator = (String ) pAgentSimulatorList [ SimulatorIndex ] ;
         // FileFormat
         // SM1;INVESTOR;V1;3;V6,V8
         // SM1 Simulator Name
         // INVESTOR TYPE
         // V1; AgentName
         // 3 ; 3 Agent will send it Status, The Name of sending Partner is not important.
         // V6,V8 are its partner. V1 will send its status to V6,V8.
         String ts = simulator + ";" + comm.mType + ";" + name + ";" +comm.getMySenderPartner() + ";" + comm.getmyPartner() ;
         tempdistribution.add( ts );
         kk = kk + 1;
       }
   }

   // add RandomTrader to DistributionList

   String LastSimulatorName = pAgentSimulatorList[ pAgentSimulatorList.length -1 ];
   for (int i=0; i< pRandomTrader; i++)
   {
       // Simulator;Agenttype;RuleName,Agentname;SenderPartnerAnzahl;ReceiverpartnerList;...#
       String ss = LastSimulatorName+";RANDOMTRADER;RandomTrader"+(i+1)+";0;0;";
       tempdistribution.add( ss  );
   }
   //String str = "#Simulator;Agenttype;Regel;Agentname;SenderPartnerAnzahl;ReceiverpartnerList;...#" ;
   System.out.println("Distribution of Agents to Simulator is finished.");
   String ss[] = new String [tempdistribution.size()];
   for ( int i=0; i< tempdistribution.size(); i++)
   {
     ss[i] = (String)tempdistribution.elementAt(i);
   }
   return ss;
 }

  // self test code
  public static void main(String[] args)
  {
    Distributor pp = new Distributor();
    System.out.println( " ====================== " );
    pp.DisplayDistribution( pp.getDistribution(100, 6) );
    System.out.println( " ====================== " );
    pp.DisplayDistribution( pp.getDistribution(10, 5) );
    System.out.println( " ====================== " );

    int procent[] = new int[4];
    procent[0] = 10;
    procent[1] = 20;
    procent[2] = 50;
    procent[3] = 20;
    try
    {
    pp.DisplayDistribution( pp.getDistribution(210, procent ) );
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }

    System.out.println ("TEST");
    procent = new int[2];
    procent[0] = 50;
    procent[1] = 50;
    try
    {
       pp.DisplayDistribution( pp.getDistribution(25, procent ) );
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }


    Vector v1 = new Vector();
    v1.add("AA");
    v1.add("BB");
    v1.add("CC");
    v1.add("DD");
    v1.add("EE");
    v1.add("FF");
    v1.add("GG");
    v1.add("HH");
    v1.add("II");
    v1.add("JJ");
    v1.add("KK");
    Vector v2 = new Vector();
    v2.add("MM1");
    v2.add("MM2");
    v2.add("MM3");
    pp.DisplayDistribution( pp.getDistribution(v1,v2) );

  }
}
