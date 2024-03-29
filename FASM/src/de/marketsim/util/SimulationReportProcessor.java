package de.marketsim.util;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import javax.swing.*;

import java.util.*;
import java.text.*;
import java.io.*;
import java.sql.Timestamp;
import de.marketsim.config.Configurator;
import de.marketsim.config.NetworkConfig;
import de.marketsim.gui.FehlerReportFrame;
import org.apache.log4j.*;

public class SimulationReportProcessor
{

   public static Hashtable MINReportData           = new Hashtable();
   public static Hashtable MAXReportData           = new Hashtable();
   public static Hashtable AverageReportData       = new Hashtable();
   public static Hashtable VarianceReportData      = new Hashtable();
   public static Hashtable StdAbweichungReportData = new Hashtable();

   public static Vector AllReportData        = new Vector();
   public static Vector HtmlProfileLinkList  = new Vector();

   public static String mTitel = "";
   public static String mCSVReportFile = "";
   public static String mCSVReportFileForSPSS = "";

   public static String mHTMLReportFile = "";
   public static SimulationReportData mStatisticOfInnererWerte = null;

   public static void setReportTitle( String pTitel, String pCSVReportFile, String pCSVReportFileForSPSS,String pHTMLReportFile )
   {

     mTitel                = pTitel;
     mCSVReportFile        = pCSVReportFile;
     mHTMLReportFile       = pHTMLReportFile;
     mCSVReportFileForSPSS = pCSVReportFileForSPSS;
     System.out.println("Title=" + mTitel);
     System.out.println("pCSVReportFile=" + pCSVReportFile);
     System.out.println("pHTMLReportFile=" + pHTMLReportFile);
  }

   public static void addOneReport( SimulationReportData pReport)
   {
       AllReportData.add( pReport );
   }

   public static void addOneHtmlFileLink( HTMLProfileLink pHtmlFileLink)
   {
       HtmlProfileLinkList.add( pHtmlFileLink );
   }

   public static double CaltVariance( double SerieWert[])
   {

     int n = SerieWert.length;

     if ( n == 1 )
     {
        return SerieWert[0];
     }

     double Variancehight2add = 0;
     double Varianceadd = 0;
     for (int m = 0; m < n; m++)
     {
       Variancehight2add= Variancehight2add + SerieWert[m]*SerieWert[m];
       Varianceadd      = Varianceadd + SerieWert[m];
     }
     double Variance = ( n*Variancehight2add - Varianceadd * Varianceadd )/( n*(n-1) ) ;
     return Variance;

   }

   public static SummaryBaseData createOneSummary( double[]  pDataList)
   {
        SummaryBaseData  temp = new SummaryBaseData();

        double sum = 0.0;
        temp.min = 1000000;
        temp.max = -100000;

        for (int i=0; i<pDataList.length; i++)
        {
            temp.min = Math.min( temp.min, pDataList[i] );
            temp.max = Math.max( temp.max, pDataList[i] );
            sum = sum +  pDataList[i];
        }
        temp.average = sum / pDataList.length;
        temp.variance_old = CaltVariance( pDataList );
        return temp;
   }

   public static void processBaseDataParts(String pTitel, PrintStream pFOS, SimulationReportBaseData[] pBaseDataList)
   {

     DataFormatter nff = new DataFormatter ( Configurator.mConfData.mDataFormatLanguage );

     double datalist[] = new double[ pBaseDataList.length ];
     for ( int i=0; i<pBaseDataList.length; i++ )
     {
       datalist[i] = pBaseDataList[i].Kurtosis;
     }

     SummaryBaseData zusammenfa = createOneSummary( datalist );

     String ss = pTitel+";Kurtosis;" +
                  nff.format2str( zusammenfa.min ) +";" +
                  nff.format2str( zusammenfa.max ) +";" +
                  nff.format2str( zusammenfa.average ) +";" +
                  nff.format2str( zusammenfa.variance_old ) + ";"+
                  nff.format2str( zusammenfa.variance_new ) + ";";
     for ( int i=0; i< datalist.length; i++)
     {
        ss = ss +  nff.format2str( datalist[i] ) +";" ;
     }
     pFOS.println( ss );

     datalist = new double[ pBaseDataList.length ];
     for ( int i=0; i< pBaseDataList.length; i++ )
     {
       datalist[i] = pBaseDataList[i].Maximum_Logrendite;
     }

     zusammenfa = createOneSummary( datalist );

     ss = pTitel+";Maximum_Logrendite;" +
                  nff.format2str( zusammenfa.min ) +";" +
                  nff.format2str( zusammenfa.max ) +";" +
                  nff.format2str( zusammenfa.average ) +";" +
                  nff.format2str( zusammenfa.variance_old )+";"+
                  nff.format2str( zusammenfa.variance_new )+";";
     for ( int i=0; i< datalist.length; i++)
     {
        ss = ss +  nff.format2str( datalist[i] ) +";" ;
     }
     pFOS.println( ss );

     datalist = new double[ pBaseDataList.length ];
     for ( int i=0; i< pBaseDataList.length; i++ )
     {
       datalist[i] = pBaseDataList[i].Minimum_Logrendite;
     }

     zusammenfa = createOneSummary( datalist );

     ss = pTitel+";Minimum_Logrendite;" +
                  nff.format2str( zusammenfa.min ) +";" +
                  nff.format2str( zusammenfa.max ) +";" +
                  nff.format2str( zusammenfa.average ) +";" +
                  nff.format2str( zusammenfa.variance_old )+";"+
                  nff.format2str( zusammenfa.variance_new )+";";
     for ( int i=0; i< datalist.length; i++)
     {
        ss = ss +  nff.format2str( datalist[i] ) +";" ;
     }
     pFOS.println( ss );

     datalist = new double[ pBaseDataList.length ];
     for ( int i=0; i< pBaseDataList.length; i++ )
     {
       datalist[i] = pBaseDataList[i].Skewness;
     }

     zusammenfa = createOneSummary( datalist );

     ss = pTitel+";Skewness;" +
                  nff.format2str( zusammenfa.min ) +";" +
                  nff.format2str( zusammenfa.max ) +";" +
                  nff.format2str( zusammenfa.average ) +";" +
                  nff.format2str( zusammenfa.variance_old )+";"+
                  nff.format2str( zusammenfa.variance_new )+";";
     for ( int i=0; i< datalist.length; i++)
     {
        ss = ss +  nff.format2str( datalist[i] ) +";" ;
     }
     pFOS.println( ss );

     datalist = new double[ pBaseDataList.length ];
     for ( int i=0; i< pBaseDataList.length; i++ )
     {
       datalist[i] = pBaseDataList[i].Standard_Abweichung;
     }

     zusammenfa = createOneSummary( datalist );

     ss =pTitel+";Standard_Abweichung;" +
                  nff.format2str( zusammenfa.min ) +";" +
                  nff.format2str( zusammenfa.max ) +";" +
                  nff.format2str( zusammenfa.average ) +";" +
                  nff.format2str( zusammenfa.variance_old )+";"+
                  nff.format2str( zusammenfa.variance_old )+";";
     for ( int i=0; i< datalist.length; i++)
     {
        ss = ss +  nff.format2str( datalist[i] ) +";" ;
     }
     pFOS.println( ss );
   }

   public static int getReportNumber()
   {
       return AllReportData.size();
   }

   public static void  clearOlddata()
   {
       AverageReportData.clear();
       AllReportData.clear();
       HtmlProfileLinkList.clear();
       MINReportData.clear();
       MAXReportData.clear();;
       AverageReportData.clear();;
       VarianceReportData.clear();
   }

   public static SimulationReportData[] getAllRunsReportsOfOneNetwork(String pNetworkUniqueID)
   {
          Vector pp = new Vector();
          for ( int j=0; j< AllReportData.size(); j++)
          {
              SimulationReportData dd = ( SimulationReportData )AllReportData.elementAt(j);
              if ( dd.mNetworkUniqueID.equalsIgnoreCase( pNetworkUniqueID ) )
              {
                pp.add( dd ) ;
              }
          }
          SimulationReportData[]  result = new SimulationReportData[pp.size()] ;
          for ( int j=0; j<pp.size(); j++)
          {
            result[j] = (SimulationReportData ) pp.elementAt(j);
          }
          return result;
   }

   public static void createReport(Logger pLogger)
   {
        // create summary.csv report file
        java.io.PrintStream fos = null;
        try
        {
          if ( pLogger != null )
          {
            pLogger.info("creating summary file : " + mCSVReportFile);
          }
          fos = new java.io.PrintStream( new  java.io.FileOutputStream( mCSVReportFile ) );
        }
        catch (Exception ex)
        {
             ex.printStackTrace();
        }

         // create the summary.csv file:
         fos.println("");
         fos.println( "Summary (result comparation of different networks)" );
         fos.println("");

         String titel = "";

         if ( ! Configurator.istAktienMarket() )
         {
           titel = "Network;Run-No;Tobintax-FestSteuer(%);Tobintax-ExtraSteuer(%);Volatility;Distortion;1Day-Kurtosis;Skewness;Standard_Deviation;Hill_Estimator;Correlation1;Correlation2;TypeChangeIndicator;RunStart;RunEnd;Duration(ms);";

         }
         else
         {
           titel = "Network;Run-No;Volatility;Distortion;1Day-Kurtosis;Skewness;Standard_Deviation;Hill_Estimator;Correlation1;Correlation2;TypeChangeIndicator;Menge_Sum;Volume_Sum;RunStart;RunEnd;Duration(ms);";
         }

         fos.println( titel );

         NetworkConfig list[] = new NetworkConfig[ Configurator.mNetworkConfigManager.getSize() ];

         for ( int i=0; i< Configurator.mNetworkConfigManager.getSize(); i++)
         {
             NetworkConfig netw = Configurator.mNetworkConfigManager.getNetworkConfig(i);
             list[i] = netw;
         }

         NetworkConfig networklistbyfilename[] = NetworkListSortedByFileName( list );

         // At first, Output the result of single network
         for ( int i=0; i< networklistbyfilename.length; i++)
         {
             NetworkConfig netw        =  networklistbyfilename[i] ;
             SimulationReportData[] dd =  getAllRunsReportsOfOneNetwork( netw.mUniqueID );

             for ( int j=0; j<dd.length; j++)
             {
               if ( j==0 )
               {
                  printOneReportData( fos, netw.mNetworkfilenameOhnePfad, dd[j].mRuningNo, dd[j], netw);
               }
               else
               {
                 printOneReportData( fos, "", dd[j].mRuningNo, dd[j], netw);
               }
             }

             System.out.println("There are " + dd.length + " report datas for "  + netw.mNetworkfilenameOhnePfad );

             SimulationReportData min  = createMinReport(dd);
             SimulationReportData max  = createMaxReport(dd);
             SimulationReportData ave  = createAverageReport(dd);
             SimulationReportData vari = createVarianceReport(dd);
             SimulationReportData standardabweichung =  createStandardAbweichung(dd);

             MINReportData          .put( netw.mUniqueID, min  ) ;
             MAXReportData          .put( netw.mUniqueID, max  ) ;
             AverageReportData      .put( netw.mUniqueID, ave  ) ;
             VarianceReportData     .put( netw.mUniqueID, vari ) ;
             StdAbweichungReportData.put( netw.mUniqueID, vari ) ;

             printOneReportData( fos, "", "MIN",      min, netw);
             printOneReportData( fos, "", "MAX",      max, netw);
             printOneReportData( fos, "", "AVERAGE",  ave, netw);
             printOneReportData( fos, "", "VARIANCE", vari, netw);
             printOneReportData( fos, "", "Standard Deviation", standardabweichung, netw);
             // add a blank line
             fos.println("");
         }

         fos.println();
         fos.println();
         fos.println("Comparation of networks");
         fos.println();
         fos.println();

         // At second, Output the comparation of networks

         for ( int i=0; i< networklistbyfilename.length; i++)
         {
             NetworkConfig netw = networklistbyfilename[i];
             SimulationReportData ave = (SimulationReportData)AverageReportData.get( netw.mUniqueID );
             printOneReportData( fos, netw.mNetworkfilenameOhnePfad, "AVERAGE", ave, netw);
             ave.mTobintaxExtraSteuer = netw.mExtraTobinTax;
             ave.mTobintaxFesteSteuer = netw.mFesteTobinTax;
         }

         fos.println();
         fos.println();

         // At end, print the statistic of InnererWerte
         printInnererWertStatisticReportData( fos, mStatisticOfInnererWerte);

         // close the comparation file
         fos.close();

       // create the HTML summary file :  summary.html

       java.io.PrintStream  pw  = null;
       try
       {
         if ( pLogger != null )
         {
            pLogger.info("creating HTML summary file " + mHTMLReportFile );
         }
         pw = new java.io.PrintStream( new  java.io.FileOutputStream( mHTMLReportFile ) );
       }
       catch (Exception ex)
       {
           ex.printStackTrace();
       }

       HTMLCreator.putHtmlHead             ( pw );
       HTMLCreator.putHtmlBodyBegin        ( pw);
       HTMLCreator.putHTMLLine             ( pw, "<Font color='blue' Size='18'>" + mTitel + "</Font><BR>"  );

       HTMLCreator.putFileLinkwithNewWindow( pw, "Click here to check the summary of test report",  Configurator.mConfData.mTestSerienCSVReportFile, "newsummary" );

       HTMLCreator.putFileLinkwithNewWindow( pw, "Click here to show the chart overview of test report",  Configurator.mConfData.mChartOverviewHTML, "newsummary" );

       HTMLCreator.putFileLinkwithNewWindow( pw, "Click here to check the summary of test report of SPSS Format",  Configurator.mConfData.mTestSerienCSVReportFileForSPSS, "newsummaryspss" );

       HTMLCreator.putFileLinkwithNewWindow( pw, "Click here to view the comparation of agent group based statistic",  Configurator.mConfData.mAgentGroupFinalStatisticCompararationFileName, "newsummaryspss" );

       try
       {
          Thread.sleep(300);
       }
       catch (Exception ex)
       {

       }

       for (int i=0; i <HtmlProfileLinkList.size(); i++)
       {
         HTMLProfileLink link = (HTMLProfileLink) HtmlProfileLinkList.elementAt(i);
         HTMLCreator.putFileLinkwithNewWindow(
        		 pw,
        		 "Click here to check the test report of " + link.LinkName,
        		 link.HtmlFilePath,  // URL String
        		 "new"+i );
       }

       HTMLCreator.putHtmlBodyEnd(pw);
       HTMLCreator.putHtmlEnd(pw);
       pw.close();

       Configurator.ResetRunCounterOfNetwork();

       // Prepare the absolute filepath of the HTML Report file
       String FASMRunDir = FileTool.getCurrentAbsoluteDirectory();
       String ss =  FASMRunDir+ "/" + mHTMLReportFile;

       String dialog = System.getProperty("DIALOG","true");
       if ( dialog.equalsIgnoreCase("true") )
       {
             // call IE to show this html report file
             try
             {
                  Runtime.getRuntime().exec("iexplore.exe " + ss);
             }
             catch (Exception ex)
             {
                   FehlerReportFrame ff = new FehlerReportFrame();
                   ff.setFehlertext("IE is not in PATH. Please check the PATH setting of your computer.");
             }
       }
   }

   private static NetworkConfig[] NetworkListSortedByFileName( NetworkConfig  pList[])
   {
       NetworkConfig  result[] = new NetworkConfig[ pList.length ];
       //System.out.println("Original Reihenfolge");

       for ( int i=0; i< pList.length; i++ )
       {
           result[i] = pList[i];
           //System.out.println( pList[i].mUniqueID + "  " + pList[i].mNetworkfilenameOhnePfad );
       }

       for ( int i=0; i< result.length-1; i++)
       {
           for ( int j=i+1; j<result.length; j++)
           {
             String ss1 = result[j].mNetworkfilenameOhnePfad.toUpperCase();
             String ss2 = result[i].mNetworkfilenameOhnePfad.toUpperCase();
             if ( ss1.compareTo( ss2  ) < 0  )
               // swap
             {
               NetworkConfig temp = result[i];
               result[i] = result[j];
               result[j  ] = temp;
             }
           }
       }

       /*
       System.out.println("Sorted Reihenfolge");
       for ( int i=0; i< result.length; i++)
       {
           System.out.println( result[i].mUniqueID + "  " + result[i].mNetworkfilenameOhnePfad );
       }
       */

       return result;
   }

   private static void printOneReportData( PrintStream fos, String pNetwork, String pRun, SimulationReportData pData, NetworkConfig pNetworkConfig )
   {
       DataFormatter nff = new DataFormatter (  Configurator.mConfData.mDataFormatLanguage );


       nff.setMaximumFractionDigits(4);
       nff.setMinimumFractionDigits(4);

       String ss = pNetwork+";"+ pRun + ";" ;
       if ( ! Configurator.istAktienMarket() )
       {
          ss = ss +  nff.format2str( pNetworkConfig.mFesteTobinTax ) +";"+ nff.format2str( pNetworkConfig.mExtraTobinTax ) + ";";
       }
       ss = ss +
                  //nff.format( pData.Volatility_old ) + ";" +
                  nff.format2str( pData.Volatility_new ) + ";" +
                  nff.format2str( pData.Distortion ) +";"  +
                  nff.format2str( pData.mData1DayAbstand.Kurtosis ) + ";"+
                  nff.format2str( pData.mData1DayAbstand.Skewness ) + ";"+
                  nff.format2str( pData.mData1DayAbstand.Standard_Abweichung*100 ) + ";"+
                  nff.format2str( pData.Hill_Estimator ) + ";"+
                  nff.format2str( pData.Korrelation1 ) + ";"+
                  nff.format2str( pData.Korrelation2 ) + ";" +
                  nff.format2str( pData.mTypeChangeIndicator ) + ";" +
                  nff.format2str( pData.mPerformedAktien_Sum  ) + ";" +
                  nff.format2str( pData.mPerformedVolume_Sum  ) + ";" +
                  ( (  pData.mStartTime != 0 ) ? ( (new Timestamp(pData.mStartTime)).toString() + ";" + (new Timestamp(pData.mEndTime)).toString() + ";" + (  pData.mEndTime - pData.mStartTime) ) : (" -; -; -")

                  ) ;

       fos.println(  ss  );
   }

   private static void printInnererWertStatisticReportData( PrintStream fos, SimulationReportData pData )
   {

     DataFormatter nff = new DataFormatter (  Configurator.mConfData.mDataFormatLanguage );

     nff.setMaximumFractionDigits(4);
     nff.setMinimumFractionDigits(4);

     String ss = "";
     if ( Configurator.istAktienMarket() )
     {
       ss = " ;Inner Values;" +
                 //nff.format( pData.Volatility_old ) + ";" +
                 nff.format2str( pData.Volatility_new ) + ";" +
                 nff.format2str( pData.Distortion )     + ";" +
                 nff.format2str( pData.mData1DayAbstand.Kurtosis ) + ";"+
                 nff.format2str( pData.mData1DayAbstand.Skewness ) + ";"+
                 nff.format2str( pData.mData1DayAbstand.Standard_Abweichung ) + ";"+
                 nff.format2str( pData.Hill_Estimator ) + ";"+
                 "  "  + ";"+
                 "  "  + ";"+
                 "  "  + ";";
     }
     else
     {
        ss = " ; Inner Values; ; ;" +
                         //nff.format( pData.Volatility_old ) + ";" +
                         nff.format2str( pData.Volatility_new ) + ";" +
                         nff.format2str( pData.Distortion )     + ";" +
                         nff.format2str( pData.mData1DayAbstand.Kurtosis ) + ";"+
                         nff.format2str( pData.mData1DayAbstand.Skewness ) + ";"+
                         nff.format2str( pData.mData1DayAbstand.Standard_Abweichung ) + ";"+
                         "  "  + ";"+
                         "  "  + ";"+
                         "  "  + ";"+
                         "  "  + ";"+
                          "  "  + ";";
     }
     fos.println( ss );
   }

   private static SimulationReportData createVarianceReport(SimulationReportData[]  pBasedata)
   {
       if (  pBasedata.length == 0 )
       {
         return new SimulationReportData();
       }

       SimulationReportData result = new SimulationReportData();
       int JJ =pBasedata.length;

       double[] dd = new double[pBasedata.length];
       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].Hill_Estimator ;
       };

       result.Hill_Estimator = CaltVariance( dd );

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].Korrelation1 ;
       };
       result.Korrelation1 = CaltVariance( dd );
       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].Korrelation2 ;
       };
       result.Korrelation2 = CaltVariance( dd );
       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData1DayAbstand.Kurtosis ;
       };
       result.mData1DayAbstand.Kurtosis = CaltVariance( dd );
       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData1DayAbstand.Skewness ;
       };
       result.mData1DayAbstand.Skewness = CaltVariance( dd );
       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData1DayAbstand.Standard_Abweichung ;
       };
       result.mData1DayAbstand.Standard_Abweichung = CaltVariance( dd );

       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData5DayAbstand.Kurtosis ;
       };
       result.mData5DayAbstand.Kurtosis = CaltVariance( dd );

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData5DayAbstand.Skewness ;
       };
       result.mData5DayAbstand.Skewness = CaltVariance( dd );

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData5DayAbstand.Standard_Abweichung ;
       };
       result.mData5DayAbstand.Standard_Abweichung = CaltVariance( dd );

       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData10DayAbstand.Kurtosis ;
       };
       result.mData10DayAbstand.Kurtosis = CaltVariance( dd );

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData10DayAbstand.Skewness ;
       };
       result.mData10DayAbstand.Skewness = CaltVariance( dd );

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData10DayAbstand.Standard_Abweichung ;
       };
       result.mData10DayAbstand.Standard_Abweichung = CaltVariance( dd );

       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData25DayAbstand.Kurtosis ;
       };
       result.mData25DayAbstand.Kurtosis = CaltVariance( dd );

       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData25DayAbstand.Skewness ;
       };
       result.mData25DayAbstand.Skewness = CaltVariance( dd );

       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData25DayAbstand.Standard_Abweichung ;
       };
       result.mData25DayAbstand.Standard_Abweichung = CaltVariance( dd );
       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData50DayAbstand.Kurtosis ;
       };
       result.mData50DayAbstand.Kurtosis = CaltVariance( dd );
       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData50DayAbstand.Skewness ;
       };
       result.mData50DayAbstand.Skewness = CaltVariance( dd );
       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData50DayAbstand.Standard_Abweichung ;
       };
       result.mData50DayAbstand.Standard_Abweichung = CaltVariance( dd );
       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].Volatility_old;
       };
       result.Volatility_old = CaltVariance( dd );
       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].Volatility_new;
       };
       result.Volatility_new = CaltVariance( dd );
       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].Distortion;
       };
       result.Distortion = CaltVariance( dd );
       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mTypeChangeIndicator;
       };

       result.mTypeChangeIndicator = CaltVariance( dd );
       //========================================================================================

       return result;
   }


   private static SimulationReportData createStandardAbweichung( SimulationReportData[]  pBasedata)
   {
       // This class has a methode that implemnets the calculation of standard abweichung
       PriceStatistic mymathtool = new PriceStatistic();

       if (  pBasedata.length == 0 )
       {
         return new SimulationReportData();
       }

       SimulationReportData result = new SimulationReportData();
       int JJ =pBasedata.length;

       double[] dd = new double[pBasedata.length];
       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].Hill_Estimator ;
       };

       result.Hill_Estimator = mymathtool.getstdev( dd );

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].Korrelation1 ;
       };
       result.Korrelation1 = mymathtool.getstdev( dd );
       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].Korrelation2 ;
       };
       result.Korrelation2 = mymathtool.getstdev( dd );
       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData1DayAbstand.Kurtosis ;
       };
       result.mData1DayAbstand.Kurtosis = mymathtool.getstdev( dd );
       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData1DayAbstand.Skewness ;
       };
       result.mData1DayAbstand.Skewness = mymathtool.getstdev( dd );

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData1DayAbstand.Standard_Abweichung ;
       };
       result.mData1DayAbstand.Standard_Abweichung = mymathtool.getstdev( dd );

       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData5DayAbstand.Kurtosis ;
       };
       result.mData5DayAbstand.Kurtosis = mymathtool.getstdev( dd );

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData5DayAbstand.Skewness ;
       };
       result.mData5DayAbstand.Skewness = mymathtool.getstdev( dd );

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData5DayAbstand.Standard_Abweichung ;
       };
       result.mData5DayAbstand.Standard_Abweichung = mymathtool.getstdev( dd );

       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData10DayAbstand.Kurtosis ;
       };
       result.mData10DayAbstand.Kurtosis = mymathtool.getstdev( dd );

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData10DayAbstand.Skewness ;
       };
       result.mData10DayAbstand.Skewness = mymathtool.getstdev( dd );

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData10DayAbstand.Standard_Abweichung ;
       };
       result.mData10DayAbstand.Standard_Abweichung = mymathtool.getstdev( dd );

       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData25DayAbstand.Kurtosis ;
       };
       result.mData25DayAbstand.Kurtosis = mymathtool.getstdev( dd );

       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData25DayAbstand.Skewness ;
       };
       result.mData25DayAbstand.Skewness = mymathtool.getstdev( dd );

       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData25DayAbstand.Standard_Abweichung ;
       };
       result.mData25DayAbstand.Standard_Abweichung = mymathtool.getstdev( dd );
       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData50DayAbstand.Kurtosis ;
       };
       result.mData50DayAbstand.Kurtosis = mymathtool.getstdev( dd );
       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData50DayAbstand.Skewness ;
       };
       result.mData50DayAbstand.Skewness = mymathtool.getstdev( dd );
       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mData50DayAbstand.Standard_Abweichung ;
       };
       result.mData50DayAbstand.Standard_Abweichung = mymathtool.getstdev( dd );
       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].Volatility_old;
       };
       result.Volatility_old = mymathtool.getstdev( dd );
       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].Volatility_new;
       };
       result.Volatility_new = mymathtool.getstdev( dd );
       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].Distortion;
       };
       result.Distortion = mymathtool.getstdev( dd );
       //========================================================================================

       for ( int i=0; i<pBasedata.length; i++)
       {
           dd[i] =  pBasedata[i].mTypeChangeIndicator;
       };

       result.mTypeChangeIndicator = mymathtool.getstdev( dd );
       //========================================================================================

       return result;
   }


   private static SimulationReportData createCopySimulationReportData(SimulationReportData  pBasedata)
   {
       SimulationReportData dd = new SimulationReportData();
       dd.Hill_Estimator = pBasedata.Hill_Estimator;

       dd.Korrelation1  = pBasedata.Korrelation1;
       dd.Korrelation2  = pBasedata.Korrelation2;
       dd.mData1DayAbstand.Kurtosis               = pBasedata.mData1DayAbstand.Kurtosis;
       dd.mData1DayAbstand.Maximum_Logrendite     = pBasedata.mData1DayAbstand.Maximum_Logrendite ;
       dd.mData1DayAbstand.Minimum_Logrendite     = pBasedata.mData1DayAbstand.Minimum_Logrendite ;
       dd.mData1DayAbstand.Skewness               = pBasedata.mData1DayAbstand.Skewness ;
       dd.mData1DayAbstand.Standard_Abweichung    = pBasedata.mData1DayAbstand.Standard_Abweichung ;

       dd.mData5DayAbstand.Kurtosis               = pBasedata.mData5DayAbstand.Kurtosis;
       dd.mData5DayAbstand.Maximum_Logrendite     = pBasedata.mData5DayAbstand.Maximum_Logrendite ;
       dd.mData5DayAbstand.Minimum_Logrendite     = pBasedata.mData5DayAbstand.Minimum_Logrendite ;
       dd.mData5DayAbstand.Skewness               = pBasedata.mData5DayAbstand.Skewness ;
       dd.mData5DayAbstand.Standard_Abweichung    = pBasedata.mData5DayAbstand.Standard_Abweichung ;

       dd.mData10DayAbstand.Kurtosis              = pBasedata.mData10DayAbstand.Kurtosis;
       dd.mData10DayAbstand.Maximum_Logrendite    = pBasedata.mData10DayAbstand.Maximum_Logrendite ;
       dd.mData10DayAbstand.Minimum_Logrendite    = pBasedata.mData10DayAbstand.Minimum_Logrendite ;
       dd.mData10DayAbstand.Skewness              = pBasedata.mData10DayAbstand.Skewness ;
       dd.mData10DayAbstand.Standard_Abweichung   = pBasedata.mData10DayAbstand.Standard_Abweichung ;

       dd.mData25DayAbstand.Kurtosis              = pBasedata.mData25DayAbstand.Kurtosis;
       dd.mData25DayAbstand.Maximum_Logrendite    = pBasedata.mData25DayAbstand.Maximum_Logrendite ;
       dd.mData25DayAbstand.Minimum_Logrendite    = pBasedata.mData25DayAbstand.Minimum_Logrendite ;
       dd.mData25DayAbstand.Skewness              = pBasedata.mData25DayAbstand.Skewness ;
       dd.mData25DayAbstand.Standard_Abweichung   = pBasedata.mData25DayAbstand.Standard_Abweichung ;

       dd.mData50DayAbstand.Kurtosis              = pBasedata.mData50DayAbstand.Kurtosis;
       dd.mData50DayAbstand.Maximum_Logrendite    = pBasedata.mData50DayAbstand.Maximum_Logrendite ;
       dd.mData50DayAbstand.Minimum_Logrendite    = pBasedata.mData50DayAbstand.Minimum_Logrendite ;
       dd.mData50DayAbstand.Skewness              = pBasedata.mData50DayAbstand.Skewness ;
       dd.mData50DayAbstand.Standard_Abweichung   = pBasedata.mData50DayAbstand.Standard_Abweichung ;

       dd.Volatility_old                           = pBasedata.Volatility_old;
       dd.Volatility_new                           = pBasedata.Volatility_new;
       dd.Distortion                               = pBasedata.Distortion;
       dd.mTypeChangeIndicator                     = pBasedata.mTypeChangeIndicator;

       dd.mPerformedAktien_Sum = pBasedata.mPerformedAktien_Sum;
       dd.mPerformedVolume_Sum = pBasedata.mPerformedVolume_Sum;

       return dd;
   }

   private static SimulationReportData createMinReport(SimulationReportData[]  pBasedata)
   {
    if (  pBasedata.length == 0 )
    {
      return new SimulationReportData();
    }

    SimulationReportData  result = createCopySimulationReportData( pBasedata[0] );

    int JJ =pBasedata.length;

    for ( int i=1; i<pBasedata.length; i++)
    {
        result.Hill_Estimator = Math.min( result.Hill_Estimator, pBasedata[i].Hill_Estimator );

        result.Korrelation1       = Math.min( result.Korrelation1 , pBasedata[i].Korrelation1 );
        result.Korrelation2       = Math.min ( result.Korrelation2 , pBasedata[i].Korrelation2 );

        result.mData1DayAbstand.Kurtosis  = Math.min ( result.mData1DayAbstand.Kurtosis  ,  pBasedata[i].mData1DayAbstand.Kurtosis );

        result.mData1DayAbstand.Skewness  = Math.min (result.mData1DayAbstand.Skewness  ,
                                             pBasedata[i].mData1DayAbstand.Skewness );

        result.mData1DayAbstand.Standard_Abweichung  = Math.min (result.mData1DayAbstand.Standard_Abweichung  ,
                                             pBasedata[i].mData1DayAbstand.Standard_Abweichung );

        //========================================================================================

        result.mData5DayAbstand.Kurtosis  = Math.min (result.mData5DayAbstand.Kurtosis ,
                                             pBasedata[i].mData5DayAbstand.Kurtosis );

        result.mData5DayAbstand.Skewness  = Math.min (result.mData5DayAbstand.Skewness  ,
                                             pBasedata[i].mData5DayAbstand.Skewness );

        result.mData5DayAbstand.Standard_Abweichung  = Math.min (result.mData5DayAbstand.Standard_Abweichung  ,
                                             pBasedata[i].mData5DayAbstand.Standard_Abweichung );

        //========================================================================================

        result.mData10DayAbstand.Kurtosis  = Math.min (result.mData10DayAbstand.Kurtosis  ,
                                             pBasedata[i].mData10DayAbstand.Kurtosis );

        result.mData10DayAbstand.Skewness  = Math.min ( result.mData10DayAbstand.Skewness  ,
                                             pBasedata[i].mData10DayAbstand.Skewness );

        result.mData10DayAbstand.Standard_Abweichung  = Math.min ( result.mData10DayAbstand.Standard_Abweichung  ,
                                             pBasedata[i].mData10DayAbstand.Standard_Abweichung );
        //========================================================================================
        result.mData25DayAbstand.Kurtosis  = Math.min (result.mData25DayAbstand.Kurtosis  ,
                                             pBasedata[i].mData25DayAbstand.Kurtosis );

        result.mData25DayAbstand.Skewness  = Math.min (result.mData25DayAbstand.Skewness  ,
                                             pBasedata[i].mData25DayAbstand.Skewness );

        result.mData25DayAbstand.Standard_Abweichung  = Math.min (result.mData25DayAbstand.Standard_Abweichung  ,
                                             pBasedata[i].mData25DayAbstand.Standard_Abweichung );

        //========================================================================================
        result.mData50DayAbstand.Kurtosis  = Math.min (result.mData50DayAbstand.Kurtosis ,
                                             pBasedata[i].mData50DayAbstand.Kurtosis);

        result.mData50DayAbstand.Skewness  = Math.min (result.mData50DayAbstand.Skewness  ,
                                             pBasedata[i].mData50DayAbstand.Skewness);

        result.mData50DayAbstand.Standard_Abweichung  =Math.min ( result.mData50DayAbstand.Standard_Abweichung ,
                                             pBasedata[i].mData50DayAbstand.Standard_Abweichung );

        result.Volatility_old       = Math.min( result.Volatility_old,       pBasedata[i].Volatility_old );
        result.Volatility_new       = Math.min( result.Volatility_new,       pBasedata[i].Volatility_new );
        result.Distortion           = Math.min( result.Distortion,           pBasedata[i].Distortion );
        result.mTypeChangeIndicator = Math.min( result.mTypeChangeIndicator, pBasedata[i].mTypeChangeIndicator );

        result.mPerformedAktien_Sum = Math.min( result.mPerformedAktien_Sum, pBasedata[i].mPerformedAktien_Sum );
        result.mPerformedVolume_Sum = Math.min( result.mPerformedVolume_Sum, pBasedata[i].mPerformedVolume_Sum );

        //========================================================================================
    }
    return result;

  }

  private static SimulationReportData createMaxReport(SimulationReportData[]  pBasedata)
  {

    if (  pBasedata.length == 0 )
    {
      return new SimulationReportData();
    }

    SimulationReportData  result = createCopySimulationReportData( pBasedata[0] );

    int JJ =pBasedata.length;

    for ( int i=1; i<pBasedata.length; i++)
    {
        result.Hill_Estimator = Math.max( result.Hill_Estimator, pBasedata[i].Hill_Estimator );

        result.Korrelation1       = Math.max( result.Korrelation1      , pBasedata[i].Korrelation1 );
        result.Korrelation2       = Math.max ( result.Korrelation2     , pBasedata[i].Korrelation2 );

        result.mData1DayAbstand.Kurtosis  = Math.max ( result.mData1DayAbstand.Kurtosis  ,  pBasedata[i].mData1DayAbstand.Kurtosis );
        result.mData1DayAbstand.Skewness  = Math.max (result.mData1DayAbstand.Skewness  ,
                                                       pBasedata[i].mData1DayAbstand.Skewness );

        result.mData1DayAbstand.Standard_Abweichung  = Math.max (result.mData1DayAbstand.Standard_Abweichung  ,
                                             pBasedata[i].mData1DayAbstand.Standard_Abweichung );

        //========================================================================================

        result.mData5DayAbstand.Kurtosis  = Math.max (result.mData5DayAbstand.Kurtosis ,
                                             pBasedata[i].mData5DayAbstand.Kurtosis );

        result.mData5DayAbstand.Skewness  = Math.max (result.mData5DayAbstand.Skewness  ,
                                             pBasedata[i].mData5DayAbstand.Skewness );

        result.mData5DayAbstand.Standard_Abweichung  = Math.max (result.mData5DayAbstand.Standard_Abweichung  ,
                                             pBasedata[i].mData5DayAbstand.Standard_Abweichung );

        //========================================================================================

        result.mData10DayAbstand.Kurtosis  = Math.max (result.mData10DayAbstand.Kurtosis  ,
                                             pBasedata[i].mData10DayAbstand.Kurtosis );

        result.mData10DayAbstand.Skewness  = Math.max ( result.mData10DayAbstand.Skewness  ,
                                             pBasedata[i].mData10DayAbstand.Skewness );

        result.mData10DayAbstand.Standard_Abweichung  = Math.max ( result.mData10DayAbstand.Standard_Abweichung  ,
                                             pBasedata[i].mData10DayAbstand.Standard_Abweichung );
        //========================================================================================
        result.mData25DayAbstand.Kurtosis  = Math.max (result.mData25DayAbstand.Kurtosis  ,
                                             pBasedata[i].mData25DayAbstand.Kurtosis );

        result.mData25DayAbstand.Skewness  = Math.max (result.mData25DayAbstand.Skewness  ,
                                             pBasedata[i].mData25DayAbstand.Skewness );

        result.mData25DayAbstand.Standard_Abweichung  = Math.max (result.mData25DayAbstand.Standard_Abweichung  ,
                                             pBasedata[i].mData25DayAbstand.Standard_Abweichung );

        //========================================================================================
        result.mData50DayAbstand.Kurtosis  = Math.max (result.mData50DayAbstand.Kurtosis ,
                                             pBasedata[i].mData50DayAbstand.Kurtosis);

        result.mData50DayAbstand.Skewness  = Math.max (result.mData50DayAbstand.Skewness  ,
                                             pBasedata[i].mData50DayAbstand.Skewness);

        result.mData50DayAbstand.Standard_Abweichung  =Math.max ( result.mData50DayAbstand.Standard_Abweichung ,
                                             pBasedata[i].mData50DayAbstand.Standard_Abweichung );

        result.Volatility_old           = Math.max ( result.Volatility_old,       pBasedata[i].Volatility_old);
        result.Volatility_new           = Math.max ( result.Volatility_new,       pBasedata[i].Volatility_new);
        result.Distortion               = Math.max ( result.Distortion,           pBasedata[i].Distortion);
        result.mTypeChangeIndicator     = Math.max ( result.mTypeChangeIndicator, pBasedata[i].mTypeChangeIndicator);

        result.mPerformedAktien_Sum = Math.max( result.mPerformedAktien_Sum, pBasedata[i].mPerformedAktien_Sum );
        result.mPerformedVolume_Sum = Math.max( result.mPerformedVolume_Sum, pBasedata[i].mPerformedVolume_Sum );

        //========================================================================================
    }
    return result;

  }

  private static SimulationReportData createAverageReport(SimulationReportData[]  pBasedata)
  {
      SimulationReportData  result = new SimulationReportData();
      int JJ =pBasedata.length;
      if ( JJ == 0 )
      {
         return  result;
      }

      for ( int i=0; i<pBasedata.length; i++)
      {
          result.Hill_Estimator = result.Hill_Estimator + pBasedata[i].Hill_Estimator;

          result.Korrelation1   = result.Korrelation1  + pBasedata[i].Korrelation1;
          result.Korrelation2   = result.Korrelation2  + pBasedata[i].Korrelation2;

          result.mData1DayAbstand.Kurtosis  = result.mData1DayAbstand.Kurtosis  +
                                               pBasedata[i].mData1DayAbstand.Kurtosis;

          result.mData1DayAbstand.Skewness  = result.mData1DayAbstand.Skewness  +
                                               pBasedata[i].mData1DayAbstand.Skewness;

          result.mData1DayAbstand.Standard_Abweichung  = result.mData1DayAbstand.Standard_Abweichung  +
                                               pBasedata[i].mData1DayAbstand.Standard_Abweichung;

          //========================================================================================

          result.mData5DayAbstand.Kurtosis  = result.mData5DayAbstand.Kurtosis  +
                                               pBasedata[i].mData5DayAbstand.Kurtosis;

          result.mData5DayAbstand.Skewness  = result.mData5DayAbstand.Skewness  +
                                               pBasedata[i].mData5DayAbstand.Skewness;

          result.mData5DayAbstand.Standard_Abweichung  = result.mData5DayAbstand.Standard_Abweichung  +
                                               pBasedata[i].mData5DayAbstand.Standard_Abweichung;

          //========================================================================================

          result.mData10DayAbstand.Kurtosis  = result.mData10DayAbstand.Kurtosis  +
                                               pBasedata[i].mData10DayAbstand.Kurtosis;

          result.mData10DayAbstand.Skewness  = result.mData10DayAbstand.Skewness  +
                                               pBasedata[i].mData10DayAbstand.Skewness;

          result.mData10DayAbstand.Standard_Abweichung  = result.mData10DayAbstand.Standard_Abweichung  +
                                               pBasedata[i].mData10DayAbstand.Standard_Abweichung;
          //========================================================================================
          result.mData25DayAbstand.Kurtosis  = result.mData25DayAbstand.Kurtosis  +
                                               pBasedata[i].mData25DayAbstand.Kurtosis;

          result.mData25DayAbstand.Skewness  = result.mData25DayAbstand.Skewness  +
                                               pBasedata[i].mData25DayAbstand.Skewness;

          result.mData25DayAbstand.Standard_Abweichung  = result.mData25DayAbstand.Standard_Abweichung  +
                                               pBasedata[i].mData25DayAbstand.Standard_Abweichung;

          //========================================================================================
          result.mData50DayAbstand.Kurtosis  = result.mData50DayAbstand.Kurtosis  +
                                               pBasedata[i].mData50DayAbstand.Kurtosis;

          result.mData50DayAbstand.Skewness  = result.mData50DayAbstand.Skewness  +
                                               pBasedata[i].mData50DayAbstand.Skewness;

          result.mData50DayAbstand.Standard_Abweichung  = result.mData50DayAbstand.Standard_Abweichung  +
                                               pBasedata[i].mData50DayAbstand.Standard_Abweichung;

          result.Volatility_old = result.Volatility_old + pBasedata[i].Volatility_old;

          result.Volatility_new = result.Volatility_new + pBasedata[i].Volatility_new;

          result.Distortion = result.Distortion + pBasedata[i].Distortion;

          result.mTypeChangeIndicator = result.mTypeChangeIndicator + pBasedata[i].mTypeChangeIndicator;

          result.mPerformedAktien_Sum = result.mPerformedAktien_Sum + pBasedata[i].mPerformedAktien_Sum ;
          result.mPerformedVolume_Sum = result.mPerformedVolume_Sum + pBasedata[i].mPerformedVolume_Sum ;

          //========================================================================================
      }

      result.Hill_Estimator = result.Hill_Estimator / JJ;

      result.Korrelation1       = result.Korrelation1  /JJ;
      result.Korrelation2       = result.Korrelation2  /JJ;

      result.mData1DayAbstand.Kurtosis  = result.mData1DayAbstand.Kurtosis  /JJ;

      result.mData1DayAbstand.Skewness  = result.mData1DayAbstand.Skewness  / JJ;

      result.mData1DayAbstand.Standard_Abweichung  = result.mData1DayAbstand.Standard_Abweichung / JJ;

      //========================================================================================

      result.mData5DayAbstand.Kurtosis  = result.mData5DayAbstand.Kurtosis / JJ;

      result.mData5DayAbstand.Skewness  = result.mData5DayAbstand.Skewness / JJ;

      result.mData5DayAbstand.Standard_Abweichung  = result.mData5DayAbstand.Standard_Abweichung / JJ;

      //========================================================================================

      result.mData10DayAbstand.Kurtosis  = result.mData10DayAbstand.Kurtosis / JJ;

      result.mData10DayAbstand.Skewness  = result.mData10DayAbstand.Skewness / JJ;

      result.mData10DayAbstand.Standard_Abweichung  = result.mData10DayAbstand.Standard_Abweichung / JJ;
      //========================================================================================
      result.mData25DayAbstand.Kurtosis  = result.mData25DayAbstand.Kurtosis / JJ;

      result.mData25DayAbstand.Skewness  = result.mData25DayAbstand.Skewness / JJ;

      result.mData25DayAbstand.Standard_Abweichung  = result.mData25DayAbstand.Standard_Abweichung / JJ;

      //========================================================================================
      result.mData50DayAbstand.Kurtosis  = result.mData50DayAbstand.Kurtosis / JJ;
      result.mData50DayAbstand.Skewness  = result.mData50DayAbstand.Skewness / JJ;
      result.mData50DayAbstand.Standard_Abweichung  = result.mData50DayAbstand.Standard_Abweichung / JJ;

      result.Volatility_old = result.Volatility_old / JJ;
      result.Volatility_new = result.Volatility_new / JJ;

      result.Distortion = result.Distortion / JJ;
      result.mTypeChangeIndicator = result.mTypeChangeIndicator / JJ;

      result.mPerformedAktien_Sum = result.mPerformedAktien_Sum / JJ ;
      result.mPerformedVolume_Sum = result.mPerformedVolume_Sum / JJ ;

      return result;
  }

  public static void createSPSSData()
  {

    DataFormatter nff = new DataFormatter(  Configurator.mConfData.mDataFormatLanguage );

    nff.setMaximumFractionDigits(4);
    nff.setMinimumFractionDigits(4);

    NetworkConfig list[] = new NetworkConfig[ Configurator.mNetworkConfigManager.getSize() ];
    for ( int i=0; i< Configurator.mNetworkConfigManager.getSize(); i++)
    {
        NetworkConfig netw = Configurator.mNetworkConfigManager.getNetworkConfig(i);
        list[i] = netw;
    }

    NetworkConfig networklistbyfilename[] = NetworkListSortedByFileName( list );

    String  networkfilenamelist[] = new String [networklistbyfilename.length];
    for ( int i=0; i<networklistbyfilename.length; i++ )
    {
      networkfilenamelist[i] = networklistbyfilename[i].mNetworkfilenameOhnePfad;
    }

    int anzahl_networkfiles = networklistbyfilename.length;
    int anzahl_runs         = Configurator.mConfData.mRepeatTimes;

    //double spssdata_double[][] = new double[anzahl_networkfiles][anzahl_runs];
    //int    spssdata_int   [][] = new int   [anzahl_networkfiles][anzahl_runs];


    SimulationReportData  original_data_of_networks[][] = new SimulationReportData[anzahl_networkfiles][anzahl_runs];

    for ( int i=0; i< networklistbyfilename.length; i++)
    {
        NetworkConfig netw        =  networklistbyfilename[i] ;
        SimulationReportData[] dd =  getAllRunsReportsOfOneNetwork( netw.mUniqueID );
        for ( int j=0; j<anzahl_runs; j++)
        {
          original_data_of_networks[i][j] = dd[j];
        }
    }

    // output Kurtosis

    // create testserienspss.csv report file
    java.io.PrintStream fos = null;

    try
    {
      fos = new java.io.PrintStream( new  java.io.FileOutputStream( mCSVReportFileForSPSS ) );
    }
    catch (Exception ex)
    {
         ex.printStackTrace();
    }

     // create the summary csv file:
     fos.println("");
     fos.println( "test result for SPSS tool" );
     fos.println("");
     // String titel = "Network; Run-No; Tobintax-FestSteuer(%); Tobintax-ExtraSteuer(%); Volatilitaet;Distortion; 1Day-Kurtosis;Skewness;Standard_Abweichung;5Day-Kurtosis;Skewness;Standard_Abweichung;10Day-Kurtosis;Skewness;Standard_Abweichung;25Day-Kurtosis;Skewness;Standard_Abweichung;50Day-Kurtosis;Skewness;Standard_Abweichung;Hill_Estimator;Korrelation1;Korrelation2;TypeChangeIndicator;RunStart;RunEnd;Duration(ms);FreeMemory;";

     CreateSPSSDataTitel(fos, "Volatility",  networkfilenamelist );
          try
          {
            for ( int i=0; i< anzahl_runs; i++)
            {
              fos.print("RUN-" + (i+1)+";" );
              for ( int j=0; j< anzahl_networkfiles; j++)
              {
                //original_data_of_networks[][] =
                //new SimulationReportData[anzahl_networkfiles][anzahl_runs];
                fos.print( nff.format2str(  (original_data_of_networks[j][i]).Volatility_new) + ";");
              }
              fos.println("");
            }
            fos.println("");
            fos.println("");

          }
          catch (Exception ex)
          {
             ex.printStackTrace();
     }

     CreateSPSSDataTitel(fos, "Distortion",  networkfilenamelist );
          try
          {
            for ( int i=0; i< anzahl_runs; i++)
            {
              fos.print("RUN-" + (i+1) +";" );
              for ( int j=0; j< anzahl_networkfiles; j++)
              {
                //original_data_of_networks[][] =
                //new SimulationReportData[anzahl_networkfiles][anzahl_runs];
                fos.print( nff.format2str(  (original_data_of_networks[j][i]).Distortion) + ";");
              }
              fos.println("");
            }
            fos.println("");
            fos.println("");
          }
          catch (Exception ex)
          {
             ex.printStackTrace();
     }

     CreateSPSSDataTitel(fos, "Kurtosis",  networkfilenamelist );
     try
     {
       for ( int i=0; i< anzahl_runs; i++)
       {
         fos.print("RUN-" + (i+1)+";" );
         for ( int j=0; j< anzahl_networkfiles; j++)
         {
           //original_data_of_networks[][] =
           //new SimulationReportData[anzahl_networkfiles][anzahl_runs];
           fos.print( nff.format2str(  (original_data_of_networks[j][i]).mData1DayAbstand.Kurtosis ) + ";");
         }
         fos.println("");
       }
       fos.println("");
       fos.println("");
     }
     catch (Exception ex)
     {
        ex.printStackTrace();
     }

     CreateSPSSDataTitel(fos, "Skewness",  networkfilenamelist );
          try
          {
            for ( int i=0; i< anzahl_runs; i++)
            {
              fos.print("RUN-" + (i+1)+";" );
              for ( int j=0; j< anzahl_networkfiles; j++)
              {
                //original_data_of_networks[][] =
                //new SimulationReportData[anzahl_networkfiles][anzahl_runs];
                fos.print( nff.format2str(  (original_data_of_networks[j][i]).mData1DayAbstand.Skewness) + ";");
              }
              fos.println("");
            }
            fos.println("");
            fos.println("");
          }
          catch (Exception ex)
          {
          ex.printStackTrace();
     }

     CreateSPSSDataTitel(fos, "Standard_Deviation",  networkfilenamelist );
     try
     {
       for ( int i=0; i< anzahl_runs; i++)
       {
         fos.print("RUN-" + (i+1)+";" );
         for ( int j=0; j< anzahl_networkfiles; j++)
         {
           //original_data_of_networks[][] =
           //new SimulationReportData[anzahl_networkfiles][anzahl_runs];
           fos.print( nff.format2str(  (original_data_of_networks[j][i]).mData1DayAbstand.Standard_Abweichung ) + ";");
         }
         fos.println("");
       }
       fos.println("");
       fos.println("");
     }
     catch (Exception ex)
     {
        ex.printStackTrace();
     }
     CreateSPSSDataTitel(fos, "Hill_Estimator",  networkfilenamelist );
     try
     {
       for ( int i=0; i< anzahl_runs; i++)
       {
         fos.print("RUN-" + (i+1) +";" );
         for ( int j=0; j< anzahl_networkfiles; j++)
         {
           //original_data_of_networks[][] =
           //new SimulationReportData[anzahl_networkfiles][anzahl_runs];
           fos.print( nff.format2str(  (original_data_of_networks[j][i]).Hill_Estimator ) + ";");
         }
         fos.println("");
       }
       fos.println("");
       fos.println("");
     }
     catch (Exception ex)
     {
        ex.printStackTrace();
     }

     CreateSPSSDataTitel(fos, "Correlation1",  networkfilenamelist );
     try
     {
       for ( int i=0; i< anzahl_runs; i++)
       {
         fos.print("RUN-" + (i+1)+";" );
         for ( int j=0; j< anzahl_networkfiles; j++)
         {
           //original_data_of_networks[][] =
           //new SimulationReportData[anzahl_networkfiles][anzahl_runs];
           fos.print( nff.format2str(  (original_data_of_networks[j][i]).Korrelation1 ) + ";");
         }
         fos.println("");
       }
       fos.println("");
       fos.println("");
     }
     catch (Exception ex)
     {
        ex.printStackTrace();
     }

     CreateSPSSDataTitel(fos, "Correlation2",  networkfilenamelist );
     try
     {
       for ( int i=0; i< anzahl_runs; i++)
       {
         fos.print("RUN-" + (i+1) +";");
         for ( int j=0; j< anzahl_networkfiles; j++)
         {
           //original_data_of_networks[][] =
           //new SimulationReportData[anzahl_networkfiles][anzahl_runs];
           fos.print( nff.format2str(  (original_data_of_networks[j][i]).Korrelation2 ) + ";");
         }
         fos.println("");
       }
       fos.println("");
       fos.println("");
     }
     catch (Exception ex)
     {
        ex.printStackTrace();
     }

     CreateSPSSDataTitel(fos, "TypeChangeIndicator",  networkfilenamelist );
     try
     {
       for ( int i=0; i< anzahl_runs; i++)
       {
         fos.print("RUN-" + (i+1) +";" );
         for ( int j=0; j< anzahl_networkfiles; j++)
         {
           //original_data_of_networks[][] =
           //new SimulationReportData[anzahl_networkfiles][anzahl_runs];
           fos.print( nff.format2str(  (original_data_of_networks[j][i]).mTypeChangeIndicator ) + ";");
         }
         fos.println("");
       }
       fos.println("");
       fos.println("");
     }
     catch (Exception ex)
     {
        ex.printStackTrace();
     }

     try
     {
        fos.close();
     }
     catch (Exception ex)
     {

     }

  }


  public static void CreateSPSSDataTitel( PrintStream fos, String pStatistivaluename,  String[]  pNetworkfilename )
  {
       try
       {
          fos.println(pStatistivaluename);
          fos.println("");
          fos.print("Runs;");
          for ( int i=0; i<pNetworkfilename.length;i++)
          {
             fos.print( pNetworkfilename[i] +";" );
          }
          fos.println("");
       }
       catch (Exception ex)
       {
         ex.printStackTrace();
       }
  }




  /**
   * self check of Sorted Procedure
   * @param args
   */
  public static void main(String args[])
  {
      NetworkConfig list [] = new NetworkConfig[3];
      list[0] = new NetworkConfig("Baraaaaaa");
      list[0].mUniqueID = "Network-001";

      list[1] = new NetworkConfig("SmallHHHHAAAAA");
      list[1].mUniqueID = "Network-002";

      list[2] = new NetworkConfig("aaa");
      list[2].mUniqueID = "Network-003";


      NetworkConfig sortedlist[] = NetworkListSortedByFileName( list );

      for ( int i=0; i<sortedlist.length; i++)
      {
          System.out.println(  sortedlist[i].mUniqueID + " " + sortedlist[i].mNetworkfilenameOhnePfad );
      }
  }

}