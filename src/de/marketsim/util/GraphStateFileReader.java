package de.marketsim.util;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.io.*;
import java.util.*;
import de.marketsim.SystemConstant;



public class GraphStateFileReader
{

  public static String mVERTEXMARK     ="VERTEX=";
  public static String mNETWORKFILEMARK="NETWORKFILE=";

  int      mVertexCount = 0;
  Vector   mStateDesc   = new Vector();
  String   mNetworkFile = null;

   /** Format
    *  1. Line: GRAPHSTATE VERTEX=20 NETWORKFILE=xxxx.txt
    *
    * 1  V1;I;B;  V2:I:B V3:N:S V3:N:W
    * 2  V1;I;B;  V2:I:B V3:N:S V3:N:W
    * 3
    *
    *  Day AGENTNAME;TYPE;ACTION;
    */

  public GraphStateFileReader(String pFile) throws Exception
  {
      String ss=null;
      try
      {
        java.io.BufferedReader br = new java.io.BufferedReader( new FileReader ( pFile ));
        String firstline = br.readLine();
        int j1 = firstline.indexOf( this.mVERTEXMARK );
        int j2 = firstline.indexOf( this.mNETWORKFILEMARK );

        if ( (  j1 < 0 )  || ( j2 <0 ) )
        {
           br.close();
           throw new Exception("File Format erro, it can not find " + this.mVERTEXMARK + " or " + this.mNETWORKFILEMARK +  " in 1. line.");
        }

        ss = firstline.substring( j1+ mVERTEXMARK.length(), j2 );
        ss = ss.trim();
        this.mVertexCount = Integer.parseInt(ss);

        this.mNetworkFile = firstline.substring( j2 + this.mNETWORKFILEMARK.length() );

        System.out.println( "AAAAAAAAAAAAA" );
        do
        {
           ss = br.readLine();
           if ( ss!= null )
           {
              int k = ss.indexOf(" ");
              this.mStateDesc.add(ss.substring(k+1));
              System.out.println( ss.substring(0,50) );
           }
        }
        while ( ss != null );
        br.close();
      }
      catch (IOException ex)
      {
        throw new Exception("Can not find Graph state file " + pFile );
      }
      catch (Exception ex)
      {
          System.out.println(ss);
          ex.printStackTrace();
          throw ex;
      }
  }

  public int getDays()
  {
      return this.mStateDesc.size();
  }

  public int getVertexCount()
  {
      return this.mVertexCount;
  }

  /**
   *
   * @param pDay: Beginning from 1:
   * @return GraphDailyState
   */
  public GraphDailyState getDailyGraphState(int pDay)
  {
      if ( pDay > this.mStateDesc.size()  )
      {
        return null;
      }
      String pStateString = (String) this.mStateDesc.elementAt( pDay-1 );
      return  parseStateString(pStateString);
  }

  public String getNetworkFile()
  {
    return this.mNetworkFile;
  }

  private GraphDailyState parseStateString(String pState)
  {

    String STATISTICMARK=";STATISTIC:";

    int KK = pState.indexOf(STATISTICMARK);
    String ss =pState.substring(0,KK);
    Vector nodestate = new Vector();

    /** Graphstate History File Format in Text Format
     *
     *  2007-10-17
     *
     *  1. Line: GRAPHSTATE VERTEX=20
     *  2. V1;I;B;  V2;I;B; V3:N:S; V4:N:W;        ;STATISTICI:I:8;0;N:8;0;B:84;0;0;
     *  3. V1;I;B;  V2;I;S; V3:N:B; V5:N:W;        ;STATISTICI:I:8;0;N:8;0;B:84;0;0;
     *
     *  AGENTNAME;TYPE;ACTION;WeiterInfo
     */

     do {
       int j = ss.indexOf(" ");
       String oneagentstate = ss.substring(0,j);
       nodestate.add(oneagentstate);
       ss = ss.substring(j+1);
     }
     while ( ss.length()>0 );

     GraphDailyState dailystate = new GraphDailyState();
     dailystate.mNodeState = nodestate;

     DailyStatisticOfNetwork statistic = new DailyStatisticOfNetwork();

     String statisticinfo = pState.substring( KK + STATISTICMARK.length() );

     System.out.println("statisticinfo=" + statisticinfo );

     // I:8;0;N:16;0;B:84;0;0

     int p1 = statisticinfo.indexOf( ";" );
     int p2 = statisticinfo.indexOf( ";", p1+1 );

     String tt1 = statisticinfo.substring(2   , p1);
     String tt2 = statisticinfo.substring(p1+1, p2);

     statistic.mInvestor           =  Integer.parseInt( tt1 ) ;
     statistic.mInvestorChanged    =  Integer.parseInt( tt2 ) ;

     p1 = statisticinfo.indexOf( "N:" );
     statisticinfo = statisticinfo.substring( p1 +2  );

     System.out.println("statisticinfo N: =" + statisticinfo );

     // statisticinfo = "16;0;B:84;0;0"

     p1 = statisticinfo.indexOf( ";" );
     p2 = statisticinfo.indexOf( ";", p1+1 );

     tt1 = statisticinfo.substring(0  , p1);
     tt2 = statisticinfo.substring(p1+1, p2);

     statistic.mNoiseTrader        = Integer.parseInt( tt1 ) ;
     statistic.mNoiseTraderChanged = Integer.parseInt( tt2 ) ;

     p1 = statisticinfo.indexOf( "B:" );
     statisticinfo = statisticinfo.substring( p1 +2  );
     System.out.println("statisticinfo B: =" + statisticinfo );

     // statisticinfo = "84;0;0"
     p1 = statisticinfo.indexOf( ";" );
     p2 = statisticinfo.indexOf( ";", p1+1 );
     int p3 = statisticinfo.indexOf( ";", p2+1 );

     tt1 = statisticinfo.substring(0  , p1);
     tt2 = statisticinfo.substring(p1+1, p2);
     String tt3 = statisticinfo.substring(p2+1, p3);

     statistic.mBlankoAgent            = Integer.parseInt( tt1 ) ;
     statistic.mBlankoAgentActivated   = Integer.parseInt( tt2 ) ;
     statistic.mBlankoAgentDeactivated = Integer.parseInt( tt3 ) ;

     dailystate.mDailyStatisticNetwork = statistic;

     return dailystate;
  }

}