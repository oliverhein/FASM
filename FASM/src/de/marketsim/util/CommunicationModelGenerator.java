package de.marketsim.util;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */
/*
  Pay Attention:
  Only Investor and NosieTrader have taken part in the commnication model
*/

import java.util.*;
import java.sql.*;
import de.marketsim.config.*;

public class CommunicationModelGenerator
{
  Vector  ListOfInvestor     = new Vector();
  Vector  ListOfNoiseTrader  = new Vector();

  public CommunicationModelGenerator()
  {

  }

  public void addOneInvestor(String pName)
  {
      ListOfInvestor.add(pName);
  }

  public void addOneNoiseTrader(String pName)
  {
      ListOfNoiseTrader.add(pName);
  }

  public CommunicationModel createCommunicationModel(int pRequiredPartner)
      throws Exception
  {

     CommunicationModel cmm = new CommunicationModel();
     // Step 1
     // combine two groups into a list
     Hashtable combinedlist = new Hashtable();
     Vector    AllName      = new Vector();

     int k = 0;
     for (int i=0; i< this.ListOfInvestor.size();i++)
     {
         PartnerKandidate  kd = new PartnerKandidate();
         kd.GroupType = "INV";
         kd.AgentName = (String) this.ListOfInvestor.elementAt(i);
         combinedlist.put( ""+k,  kd );
         AllName.add(kd.AgentName);
         k++;
     }

     for (int i=0; i< this.ListOfNoiseTrader.size();i++)
     {
         PartnerKandidate  kd = new PartnerKandidate();
         kd.GroupType = "NTR";
         kd.AgentName = (String) this.ListOfNoiseTrader.elementAt(i);
         combinedlist.put( ""+k, kd );
         AllName.add(kd.AgentName);
         k++;
     }

     if ( AllName.size() == 0 )
     {
        System.out.println("Error:" + "Investor and NoiseTrader are not added to the Generator, Generator can not work!" );
        return cmm;
     }

     int Anzahl_Allagent = k;
     // The number of element in left-down part of Communication Matrix
     // except the elements on the duijiaoxian.
     int MaximalCommunicationParnter = Anzahl_Allagent * ( Anzahl_Allagent - 1 ) / 2;

     if ( pRequiredPartner > MaximalCommunicationParnter )
     {
       String errmsg = "The expected communication parnter " +
                           pRequiredPartner + " is beyond the available limit " + MaximalCommunicationParnter +".";
        System.out.println( errmsg );
        throw new Exception( errmsg );
     }

     // Step 2
     // The left-down matrix will be used for the searching of communication
     // partner
     int i=0;
     java.util.Random  rd = new java.util.Random();

     Vector      RelationList = new Vector();
     Hashtable   RelationCreated = new Hashtable();
     while  (i<pRequiredPartner)
     {
         // X: LIE
         int xInd = rd.nextInt(Anzahl_Allagent);
         // Y: HANG
         int yInd = rd.nextInt(Anzahl_Allagent);
         if ( xInd < yInd )
         {
            // Index is valid
            // Check if it exists already
            if ( RelationCreated.containsKey( "" + xInd +"," + yInd  ) )
            {
               // this parntership has been created.
            }
            else
            {
              i++;
              OneCommunicationRelation pp = new OneCommunicationRelation();
              pp.xInd = xInd;
              pp.yInd = yInd;
              // Translate xInd and yInd to the correct AgentName
              PartnerKandidate kd = (PartnerKandidate) combinedlist.get( ""+ xInd );
              pp.PartnerX = kd.AgentName;
              kd = (PartnerKandidate) combinedlist.get( ""+ yInd );
              pp.PartnerY = kd.AgentName;
              RelationList.add( pp );
              RelationCreated.put("" + xInd +"," + yInd, "");
            }
         }
         else
         {
            // ungültige Partner, wegwerfen
         }
     }

     for (int j=0; j <pRequiredPartner; j++)
     {
        OneCommunicationRelation pp = (OneCommunicationRelation)  RelationList.elementAt(j);
        System.out.println( pp.PartnerX + "<->" + pp.PartnerY);
     }

     // Step 3: listing the related Agent Name
     Hashtable  RelatedAgentIndexForChecking = new Hashtable();
     Vector     SelectedAgentList            = new Vector();

     for (int j=0; j <pRequiredPartner; j++)
     {
        // 1:1 CommunicationRelation
        OneCommunicationRelation pp = (OneCommunicationRelation)  RelationList.elementAt(j);
        if ( ! RelatedAgentIndexForChecking.containsKey( ""+pp.xInd ) )
        {
            RelatedAgentIndexForChecking.put(""+pp.xInd, "");
            SelectedAgent ag = new SelectedAgent();
            ag.AgentIndex = pp.xInd;
            PartnerKandidate kd = (PartnerKandidate)combinedlist.get( ""+ ag.AgentIndex );
            ag.AgentName  = kd.AgentName;
            SelectedAgentList.add( ag );
        }

        if ( !RelatedAgentIndexForChecking.containsKey( ""+pp.yInd ) )
        {
            RelatedAgentIndexForChecking.put(""+pp.yInd, "");
            SelectedAgent ag = new SelectedAgent();
            ag.AgentIndex = pp.yInd;
            PartnerKandidate kd = (PartnerKandidate)combinedlist.get( ""+ ag.AgentIndex );
            ag.AgentName  = kd.AgentName;
            ag.GroupType  = kd.GroupType;
            SelectedAgentList.add( ag );
        }
     }

     System.out.println( "Related Agent List");
     for (int j=0; j <SelectedAgentList.size(); j++)
     {
        System.out.println( ((SelectedAgent) SelectedAgentList.elementAt(j)).AgentName  );
     }

     // Step 4:
     // sorting the communication partner according to the AgentName
     for (int j=0; j <SelectedAgentList.size(); j++)
     {
        SelectedAgent ag = ( SelectedAgent)  SelectedAgentList.elementAt(j);

        One2ManyCommunication one2mm = new One2ManyCommunication(ag.AgentName);
        System.out.println ();
        System.out.print( "Agent " + ag.AgentName + " has following partners " );
        for ( int m = 0 ; m <RelationList.size(); m++)
        {
           OneCommunicationRelation pp = (OneCommunicationRelation)  RelationList.elementAt(m);
           if ( pp.xInd == ag.AgentIndex )
           {
              // This Agent takes part in this communication
              // Another Agent with yInd is his partner
              ag.myAllPartner.add( pp.PartnerY );
              System.out.print ( pp.PartnerY + ",");
              one2mm.addOnePartner( (String) pp.PartnerY );
           }
           else
           if ( pp.yInd == ag.AgentIndex )
           {
              // This Agent takes part in this communication
              // Another Agent with xInd is his partner
              ag.myAllPartner.add( pp.PartnerX );
              System.out.print ( pp.PartnerX +",");
              one2mm.addOnePartner( (String) pp.PartnerX );
           }
           // otherwise, This Agent doesn't take part int this communication
        }
        cmm.addOne2ManyCommunication( one2mm );
     }
     // step 5:  Output a simplified matrix so that man can check the math modell
    this.displayinmatrix(AllName, RelationList);
    return cmm;
  }

  public void displayinmatrix(Vector AllAgent, Vector CreatedRelation)
  {
     int  nn = AllAgent.size() + 1;
     // 1. Index is XIndex
     // 2. Index is YIndex
     String[][] MM = new String[nn][nn] ;
     String onespace   = " ";
     String twospace   = "  ";
     String threespaces= "   ";
     String SplitSymbol= ",";
     String SelectedSymbol = " * ";

     //MM[0][0] = threespaces;
     MM[0][0] = "IND";

     // set Martrix X and Y Title
     for (int i=1; i<nn; i++)
     {
          // X - Title
          MM[i][0] = (String) AllAgent.elementAt(i-1);
          // Y - Title
          MM[0][i] = (String) AllAgent.elementAt(i-1);
     }

     // Matrix initialization
     for (int i=1; i<nn; i++)
     for (int j=1; j<nn; j++)
     {
       MM[i][j] = threespaces;
     }

     // Fülle Matrix
     for (int i=0; i<CreatedRelation.size(); i++)
     {
       OneCommunicationRelation pp = (OneCommunicationRelation)  CreatedRelation.elementAt(i);
       MM[pp.xInd+1] [pp.yInd+1] = SelectedSymbol;
     }

     System.out.println();

     // Output
     // Output X Title
     /*
     for (int i=0; i<nn; i++)
     {
       System.out.print( MM[i][0] + twospace );
     }
     */
     System.out.println();

     // Output 1. Line to NN.Line
     for (int j=0; j<nn; j++)
     {
         for (int i=0; i<nn; i++)
         {
            if ( i == nn-1 )
            {
               System.out.print( MM[i][j] );
            }
            else
            {
              System.out.print( MM[i][j] + onespace+SplitSymbol );
            }
         }
         System.out.println();
     }
  }

  public void test()
  {
     addOneInvestor("VV1");
     addOneInvestor("VV2");
     addOneInvestor("VV3");
     addOneInvestor("VV4");
     addOneInvestor("VV5");
     addOneInvestor("VV6");
     addOneInvestor("VV7");
     addOneInvestor("VV8");
     addOneNoiseTrader("NN1");
     addOneNoiseTrader("NN2");
     addOneNoiseTrader("NN3");
     addOneNoiseTrader("NN4");
     addOneNoiseTrader("NN5");
     try
     {
       this.createCommunicationModel(20);
     }
     catch (Exception ex)
     {
       System.out.println(ex.getMessage());
     }
  }

  public static void main(String[] args)
  {
      CommunicationModelGenerator mm = new CommunicationModelGenerator();
      long t1 = System.currentTimeMillis();
      mm.test();
      long t2 = System.currentTimeMillis();
      System.out.println( "computing time:" +(t2-t1) + " ms" );
  }

}