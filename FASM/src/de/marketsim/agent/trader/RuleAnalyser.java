package de.marketsim.agent.trader;

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

import de.marketsim.agent.trader.LimitErmittelung;
import de.marketsim.util.*;
import de.marketsim.config.Configurator;
import de.marketsim.SystemConstant;

public class RuleAnalyser
{

  static java.util.Random rdgen = new Random();

  //private static java.text.NumberFormat nff = HelpTool.getNumberFormat();
  //private static java.text.NumberFormat germannff = HelpTool.getNumberFormat("Germany");

  private static Object  Filesynobj = new Object();

  private static java.io.PrintWriter pw = null;

  public static void prepareHistoryfile()
  {
      synchronized (Filesynobj)
      {
         if ( pw == null )
         {
           try
           {
               pw = new java.io.PrintWriter ( new java.io.FileWriter("ordervolmegenerationhistory.txt"));
           }
           catch (Exception ex)
           {

           }
         }
      }
  }

  public static void closeHistoryfile()
  {
      synchronized (Filesynobj)
      {
         if ( pw != null )
         {
           try
           {
             pw.close();
             pw = null;
           }
           catch (Exception ex)
           {

           }
         }
      }
  }

 public static InvestorRuleSuggestionRecord InvestorMarketAnalysewithRule( double pCurrentInnererWert, double  pAbschlagProzent)
 {
   DataFormatter nff = new DataFormatter( Configurator.mConfData.mDataFormatLanguage );

   InvestorRuleSuggestionRecord newsuggestion = new InvestorRuleSuggestionRecord();
   newsuggestion.mAction = 'N';
   double dd1 = pCurrentInnererWert * ( 1- pAbschlagProzent/100.0);
   double dd2 = pCurrentInnererWert * ( 1 + pAbschlagProzent/100.0);
   double lastprice = PriceContainer.getLastPrice();
   //  System.out.println( "Investor-R1: Abschlag-Prozent=" + pAbschlagProzent + " InnererWert=" + pCurrentInnererWert + " dd1=" + dd1 + " LastPrice=" + lastprice );
   if ( lastprice < dd1 )
   {
      // Buy Suggestion
      newsuggestion.mAction = 'B';
   }
   else
   if ( lastprice > dd2  )
   {
       // Sell Suggestion
       newsuggestion.mAction = 'S';
   }
   newsuggestion.mSuggestionReason= "LastPrice=" + nff.format2str( lastprice ) +
                                    "  InnerW =" + nff.format2str( pCurrentInnererWert ) +
                                    "  Abschlag=" + nff.format2str( pAbschlagProzent ) + "%" +
                                    "  InnerW-Abschlag=" + nff.format2str( dd1) +
                                    "  InnerW+Abschlag=" + nff.format2str( dd2);
   return newsuggestion;
 }

 private static double AdjustPrice ( double pTempPrice, double pYesterdayPrice)
  {
    if (  ( Math.abs (pTempPrice - pYesterdayPrice ) * 1.0 / pYesterdayPrice ) > 0.03 )
    {
       if  ( pTempPrice > pYesterdayPrice )
       {
          return pYesterdayPrice * (1+0.03);
       }
       else
       {
          return pYesterdayPrice * (1-0.03) ;
       }
    }
    else
    {
       return pTempPrice;
    }
  }

  // make the Limit for Investor
  public static LimitErmittelung makeInvestorLimit( double pInnererWert, double pAbschlagProzent, char pAction )
  {
     LimitErmittelung le = new LimitErmittelung();
     le.mLimit = 0.0;
     if (  pAction == 'B' )
     {
       le.mLimit =  pInnererWert * ( 1 - pAbschlagProzent/100.0 ) ;
       le.mReason ="IW * ( 1- " + pAbschlagProzent +")";
     }
     else
     if (  pAction == 'S' )
     {
        le.mLimit = pInnererWert * ( 1 + pAbschlagProzent/100.0 )  ;
        le.mReason ="IW * ( 1+ " + pAbschlagProzent +")";
     }
     //System.out.println("Investor Limit-Berechnung: Aktion=" + pAction + " Innererwert=" + pInnererWert +" AbschlagProcent=" + pAbschlagProzent + " NewLimit=" + newlimit);
     return le;
  }

  /**
   * check if Besten Sell or Billigsten Buy is possible according to the defintion of MarkerOrder and Grenz
   * @param pLimit
   * @param Avg
   * @return
   */

  public static LimitErmittelung checkInvestorLimit( double pLimit, double Avg, char pAction )
  {
        LimitErmittelung le = new LimitErmittelung();
        le.mLimit = pLimit;
        double Vortagkurs = PriceContainer.getLastPrice();
        double prozent;

        if ( pAction == 'B' )
        {
            // 2006-10-01
            //           ( myBuyLimit - Vortagkurs ) * 100.0
            // Prozent = -------------------------------------
            //                        myBuyLimit
            prozent = ( pLimit - Vortagkurs ) * 100.0 / pLimit;

            //////////////////////////////
            if ( prozent < Configurator.mConfData.mInvestorKurschangedprocentlimit1 )
            {
               if ( Configurator.mConfData.mInvestorStufe1MarketOrderBilligestKauf == 'B' )
               {
                 le.mLimit  = SystemConstant.BestenOrCheapest;
                 le.mReason = "Prozent ("+ prozent +") < ProzentGrenz1 ("+Configurator.mConfData.mInvestorKurschangedprocentlimit1+") und BilligestKauf";
                 return le;
               }
               else
               {
                 return le;
               }
            }
            else
            if ( prozent < Configurator.mConfData.mInvestorKurschangedprocentlimit2 )
            {
               if ( Configurator.mConfData.mInvestorStufe2MarketOrderBilligestKauf == 'B' )
               {
                 le.mLimit  = SystemConstant.BestenOrCheapest;
                 le.mReason = "Prozent ("+ prozent +") < ProzentGrenz2 ("+Configurator.mConfData.mInvestorKurschangedprocentlimit1+") und BilligestKauf";
                 return le;
               }
               else
               {
                 return le;
               }
           }
           else
           if ( prozent < Configurator.mConfData.mInvestorKurschangedprocentlimit3 )
           {
               if ( Configurator.mConfData.mInvestorStufe1MarketOrderBilligestKauf == 'B' )
               {
                 le.mLimit  = SystemConstant.BestenOrCheapest;
                 le.mReason = "Prozent ("+ prozent +") < ProzentGrenz3 ("+Configurator.mConfData.mInvestorKurschangedprocentlimit2+") und BilligestKauf";
                 return le;
               }
               else
               {
                 return le;
               }
           }
           else
           {
             if ( Configurator.mConfData.mInvestorStufe4MarketOrderBilligestKauf == 'B' )
             {
               le.mLimit  = SystemConstant.BestenOrCheapest;
               le.mReason = "Prozent ("+ prozent +") >= ProzentGrenz3 ("+Configurator.mConfData.mInvestorKurschangedprocentlimit3+") und BilligestKauf";
               return le;
             }
             else
             {
               return le;
             }
           }
        }
        else
        // "Sell"
        {
            // 2006-10-01
            //          ( Vortagkurs - mySellLimit ) * 100.0
            // Prozent = -------------------------------------
            //             mySellLimit
            prozent = ( Vortagkurs - pLimit ) * 100.0 / pLimit;

        //////////////////////////////
        if ( prozent < Configurator.mConfData.mInvestorKurschangedprocentlimit1 )
        {
           if ( Configurator.mConfData.mInvestorStufe1MarketOrderBestVerkauf == 'B' )
           {
             le.mLimit  = SystemConstant.BestenOrCheapest;
             le.mReason = "Prozent ("+ prozent +") < ProzentGrenz1 ("+Configurator.mConfData.mInvestorKurschangedprocentlimit1+") und BestVerkauf";
             return le;
           }
           else
           {
             return le;
           }
        }
        else
        if ( prozent < Configurator.mConfData.mInvestorKurschangedprocentlimit2 )
        {
           if ( Configurator.mConfData.mInvestorStufe2MarketOrderBestVerkauf == 'B' )
           {
             le.mLimit  = SystemConstant.BestenOrCheapest;
             le.mReason = "Prozent ("+ prozent +") < ProzentGrenz2 ("+Configurator.mConfData.mInvestorKurschangedprocentlimit1+") und BestVerkauf";
             return le;
           }
           else
           {
             return le;
           }
       }
       else
       if ( prozent < Configurator.mConfData.mInvestorKurschangedprocentlimit3 )
       {
           if ( Configurator.mConfData.mInvestorStufe1MarketOrderBestVerkauf == 'B' )
           {
             le.mLimit  = SystemConstant.BestenOrCheapest;
             le.mReason = "Prozent ("+ prozent +") < ProzentGrenz3 ("+Configurator.mConfData.mInvestorKurschangedprocentlimit2+") und BestVerkauf";
             return le;
           }
           else
           {
             return le;
           }
       }
       else
       {
         if ( Configurator.mConfData.mInvestorStufe4MarketOrderBestVerkauf == 'B' )
         {
           le.mLimit  = SystemConstant.BestenOrCheapest;
           le.mReason = "Prozent ("+ prozent +") >= ProzentGrenz3 ("+Configurator.mConfData.mInvestorKurschangedprocentlimit3+") und BestVerkauf";
           return le;
         }
         else
         {
           return le;
         }
       }

        }
  }

  /***
  // Create Order-Menge for Investor
  public static int makeInvestorOrderMenge( double pInnererWert, double pAbschlahProzent, char pSuggestion )
  {
    // Theorie-Basis
    // P = aktueller Preis
    //I = Innerer Wert plus oder minus Premium 0%  3%, 6% (R1, R2, R3) je nach Kauf oder Verkauf
    //D = (I - P) / P

    //If  0    < D < 1% then Kaufe 1 Stück
    //If  1% < D < 2% then Kaufe 3 Stück
    //If  2% < D < 4% then Kaufe 6 Stück
    //If  4% < D <  \u221E  then Kaufe 10 Stück

    //If   0      > D  > -1%  then Verkaufe 1 Stück
    //If  -1%  > D  > -2%  then Verkaufe 3 Stück
    //If  -2%  > D  > -4%  then Verkaufe 6 Stück
    //If  -4%  > D  > - \u221E   then Verkaufe 10 Stück

    double  lastprice = PriceContainer.getLastPrice();
    int     menge = 0;

    // Kurs-Changed-Procent
    double  prozent = 0.0;

    double tempinnerwert = 0.0;

    if ( pSuggestion == 'B' )
    {

       tempinnerwert = pInnererWert * ( 1 + pAbschlahProzent/100 );
       prozent = ( ( tempinnerwert - lastprice ) * 100.0 ) / lastprice ;

    }
    else
    if ( pSuggestion == 'S' )
    {

       tempinnerwert = pInnererWert * ( 1 - pAbschlahProzent/100 );
       prozent = ( ( tempinnerwert - lastprice ) * 100.0 ) / lastprice ;

    }

    //prozent = Math.abs( prozent );

    if ( prozent >= 0 )
    {
        if ( prozent < Configurator.mConfData.mInvestorKurschangedprocentlimit1 )
        {
          menge=Configurator.mConfData.mInvestorOrderMengeStufe1;
        }
        else
        if ( prozent < Configurator.mConfData.mInvestorKurschangedprocentlimit2 )
        {
          menge=Configurator.mConfData.mInvestorOrderMengeStufe2;
        }
        else
        if ( prozent < Configurator.mConfData.mInvestorKurschangedprocentlimit3 )
        {
          menge=Configurator.mConfData.mInvestorOrderMengeStufe3;
        }
        else
        {
          menge=Configurator.mConfData.mInvestorOrderMengeStufe4;
        }
    }
    return menge ;
  }
  **/

 // Create Order-Menge for Investor
 // New Regel of Oliver: 2006-09-28
 public static int makeInvestorOrderMenge( double pInnererWert, double pAbschlahProzent, char pSuggestion )
 {

   double  VortagKurs = PriceContainer.getLastPrice();
   int     menge = 0;
   // Kurs-Changed-Procent
   double  prozent = 0.0;

   double  mybuylimit = 0.0;
   double  myselllimit = 0.0;

   if ( pSuggestion == 'B' )
   {

      mybuylimit = pInnererWert * ( 1 - pAbschlahProzent/100 );

      // 2006-10-01
      //           ( myBuyLimit - Vortagkurs ) * 100.0
      // Prozent = -------------------------------------
      //           myBuyLimit

      prozent = ( mybuylimit - VortagKurs ) * 100.0  / mybuylimit ;

      // Theorie Base
      // je positiver das Prozent ist, desto attraktiver ist der myLimit, dann soll mehr gekauft werden
      // je negativer das Prozent ist, desto nicht attraktiver ist der myLimit, dann soll wenig gekauft werden.

      //  Prozent in  [ negative,               Grenz 1]   MengeStufe1
      //  Prozent in  [ Grenz1,                 Grenz 2]   MengeStufe2
      //  Prozent in  [ Grenz2,                 Grenz 3]   MengeStufe3
      //  Prozent in  [ Grenz3,    +Postive Infinitive ]   MengeStufe4

      if ( prozent < Configurator.mConfData.mInvestorKurschangedprocentlimit1 )
      {
        menge=Configurator.mConfData.mInvestorOrderMengeStufe1;
      }
      else
      if ( prozent < Configurator.mConfData.mInvestorKurschangedprocentlimit2 )
      {
        menge=Configurator.mConfData.mInvestorOrderMengeStufe2;
      }
      else
      if ( prozent < Configurator.mConfData.mInvestorKurschangedprocentlimit3 )
      {
        menge=Configurator.mConfData.mInvestorOrderMengeStufe3;
      }
      else
      {
        menge=Configurator.mConfData.mInvestorOrderMengeStufe4;
      }
   }
   else
   if ( pSuggestion == 'S' )
   {
      // myBuylimit
      myselllimit = pInnererWert * ( 1 + pAbschlahProzent/100 );

      // 2006-10-01
      //           ( Vortagkurs - mySellLimit ) * 100.0 )
      // Prozent = -------------------------------------
      //             mySellLimit

      prozent =  ( VortagKurs - myselllimit  ) * 100.0 / myselllimit ;

      // Theorie Base
      // je positiver das Prozent ist, bedeutet ist myLimit mehr höher als Gestern Kurs,
      // desto attraktiver ist der myLimit für mein Verkauf , dann soll mehr verkauft werden.
      // je negativer das Prozent ist,  bedeutet ist myLimit mehr niederiger  als Gestern Kurs,
      // desto nicht so attraktiver ist der myLimit für mein Verkauf, dann soll wenig verkauft werden.

      //  Prozent in  [ negative,               Grenz 1]   MengeStufe1
      //  Prozent in  [ Grenz1,                 Grenz 2]   MengeStufe2
      //  Prozent in  [ Grenz2,                 Grenz 3]   MengeStufe3
      //  Prozent in  [ Grenz3,    +Postive Infinitive ]   MengeStufe4

      if ( prozent < Configurator.mConfData.mInvestorKurschangedprocentlimit1 )
      {
        menge=Configurator.mConfData.mInvestorOrderMengeStufe1;
      }
      else
      if ( prozent < Configurator.mConfData.mInvestorKurschangedprocentlimit2 )
      {
        menge=Configurator.mConfData.mInvestorOrderMengeStufe2;
      }
      else
      if ( prozent < Configurator.mConfData.mInvestorKurschangedprocentlimit3 )
      {
        menge=Configurator.mConfData.mInvestorOrderMengeStufe3;
      }
      else
      {
        menge=Configurator.mConfData.mInvestorOrderMengeStufe4;
      }
   }
   return menge ;
 }

  public static char NoiseTraderMarketAnalysewithRule( double Avg )
  {
       double VortagKurs = PriceContainer.getLastPrice();
       char suggestition = 'N';
       String tt = "wait";

       if ( VortagKurs >=Avg )
       {
           suggestition = 'B';
           tt="Buy";
       }
       else
       {
           suggestition = 'S';
           tt="Sell";
       }
       return suggestition;
  }

  // create Order-Menge of NoiseTrader
  public static int makeNoiseTraderOrderMenge( double pInnererWert, char pSuggestion, double Avg )
  {
    /** Theorie-Basis

      P   = aktueller Preis
      Avg = Durchschnitt des Preis der letzten 50, 150 oder 300 Tage
      D   = (P - Avg) / P
      /**** Theorie Basis
      P = aktueller Preis
      Avg = Durchschnitt des Preis der letzten 50, 150 oder 300 Tage
      D= (P - Avg) / P

      If  0    < D < 1% then Kaufe 1 Stück
      If  1% < D < 2% then Kaufe 3 Stück
      If  2% < D < 4% then Kaufe 6 Stück
      If  4% < D <  \u221E  then Kaufe 10 Stück

      If   0      > D  > -1%  then Verkaufe 1 Stück
      If  -1%  > D  > -2%  then Verkaufe 3 Stück
      If  -2%  > D  > -4%  then Verkaufe 6 Stück
      If  -4%  > D  > - \u221E   then Verkaufe 10 Stück
    **/

    double Vortagkurs = PriceContainer.getLastPrice();
    double prozent   = 0.0;

    if ( pSuggestion == 'B'  )
    {
         // 2006-10-01 New formel, nachher geändert mit Math.abs()
         //           Math.abs (  AveragePreis - Vortagkurs ) * 100.0
         // Prozent = -----------------------------------------------------
         //                  AveragePreis

        prozent  = Math.abs ( Avg - Vortagkurs ) * 100.0 / Avg;

        if ( prozent < Configurator.mConfData.mNoiseTraderKurschangedprocentlimit1)
        {
          return Configurator.mConfData.mNoiseTraderOrderMengeStufe1;
        }
        else
        if ( prozent < Configurator.mConfData.mNoiseTraderKurschangedprocentlimit2 )
        {
          return Configurator.mConfData.mNoiseTraderOrderMengeStufe2;
        }
        else
        if ( prozent < Configurator.mConfData.mNoiseTraderKurschangedprocentlimit3 )
        {
          return Configurator.mConfData.mNoiseTraderOrderMengeStufe3;
        }
        else
        {
          return Configurator.mConfData.mNoiseTraderOrderMengeStufe4;
        }
    }
    else
    if ( pSuggestion == 'S'  )
    {

        //  new formel: 2006-10-01, nachher geändert mit Math.abs()
        //             Math.abs( Vortagkurs - AveragePreis ) * 100.0
        //  Prozent = ---------------------------------------------------
        //             AveragePreis

        prozent  = Math.abs (  Vortagkurs-  Avg ) * 100.0 / Avg;

        if ( prozent < Configurator.mConfData.mNoiseTraderKurschangedprocentlimit1)
        {
          return Configurator.mConfData.mNoiseTraderOrderMengeStufe1;
        }
        else
        if ( prozent < Configurator.mConfData.mNoiseTraderKurschangedprocentlimit2 )
        {
          return Configurator.mConfData.mNoiseTraderOrderMengeStufe2;
        }
        else
        if ( prozent < Configurator.mConfData.mNoiseTraderKurschangedprocentlimit3 )
        {
          return Configurator.mConfData.mNoiseTraderOrderMengeStufe3;
        }
        else
        {
          return Configurator.mConfData.mNoiseTraderOrderMengeStufe4;
        }
    }
    else
    {
        return 0;
    }
  }

  /*
  Die Kurszusätze von der Börse werden wie folgt berechnet:

        1.) Den Kurs berechnen wie immer
        2.) Gibt es nach der Kursbestimmung noch Agenten die kaufen wollen
                aber nichts oder nicht alles bekommen haben meldet die Börse zum
                Kurs noch ein G (für Geld, es war noch mehr Geld da) (z.B. 45,21G)
        3.) Gibt es nach der Kursbestimmung noch Agenten die verkaufen wollen
                aber nichts oder nicht alles bekommen haben meldet die Börse zum
                Kurs noch ein B (für Brief, es waren noch mehr Aktien da) (z.B. 45,21B)
        4.) Die Agenten wollen gar nicht Kauf oder Einkauf(mit WAIT ), meldet die Börse N (None).

        5.) Haben alle Agenten bekommen was sie wollen meldet die Börse nichts
            oder nur ein 0 (OK).

            Die Kurszusätze G B und T gehen dann in die Limitberechnung ein (siehe oben)
  */

  // The new Limit totaly depends on the Kurzzusatz PerformStatus of Yesterday"

  public static LimitErmittelung makeNoiseTraderLimit( double Avg,  char marketdecision, OrderPerformedStatus  pPerformedStatus )
  {
    /**
    P = aktueller Preis
    Avg = Durchschnitt des Preis der letzten 50, 150 oder 300 Tage (R1, R2, R3)
    D= (P - Avg) / P

    If  0    < D < 3% then Kaufe zum Limit Kurs gestern
    If  3% < D <  \u221E  then Kaufe als Marketorder

    If   0      > D  > -3%  then Verkaufe zum Limit Kurs gestern
    If  -3%  > D  > - \u221E  then Verkaufe als Marketorder

    */

    double    VortagKurs = PriceContainer.getLastPrice();
    double    prozent  = ( VortagKurs - Avg ) / Avg;

    LimitErmittelung  le = new LimitErmittelung();
    le.mLimit = 0.0;

    if ( prozent > 0.0 )
    {
       if ( prozent < 0.03 )
       {
          le.mLimit = pPerformedStatus.getYesterdayPrice();
       }
       else
       {
          le.mLimit = SystemConstant.BestenOrCheapest;
       }
       return le;
    }
    else
    if ( prozent <= 0 )
    {
       if ( prozent > -0.03 )
       {
          le.mLimit = pPerformedStatus.getYesterdayPrice();
       }
       else
       {
          le.mLimit = SystemConstant.BestenOrCheapest;
       }
    }
    return le;
  }

  public static LimitErmittelung checkNoiseTraderLimit( double pLimit, double Avg, char pAction )
  {

    double    VortagKurs = PriceContainer.getLastPrice();
    double    prozent;

    // 2006-10-01 New formel, nachher geändert mit Math.abs()
    //           Math.abs (  AveragePreis - Vortagkurs ) * 100.0
    // Prozent = -----------------------------------------------------
    //                  AveragePreis

    prozent  = Math.abs ( Avg - VortagKurs ) * 100.0 / Avg;

    LimitErmittelung le = new LimitErmittelung();
    le.mLimit = pLimit;

    // buy
    if ( pAction == 'B' )
    {

       if ( prozent < Configurator.mConfData.mNoiseTraderKurschangedprocentlimit1 )
       {
           if ( Configurator.mConfData.mNoiseTraderStufe1MarketOrderBilligestKauf == 'B' )
           {
             le.mLimit = SystemConstant.BestenOrCheapest;
             le.mReason= "Prozent(" + HelpTool.DoubleTransfer(prozent, 4) + ")<Grenz1 und BilligestKauf";
           }
       }
       else
       if ( prozent < Configurator.mConfData.mNoiseTraderKurschangedprocentlimit2 )
       {
           if ( Configurator.mConfData.mNoiseTraderStufe2MarketOrderBilligestKauf == 'B' )
           {
             le.mLimit = SystemConstant.BestenOrCheapest;
             le.mReason= "Prozent(" + HelpTool.DoubleTransfer(prozent, 4) + ")<Grenz2 und BilligestKauf";
           }
       }
       else
       if ( prozent < Configurator.mConfData.mNoiseTraderKurschangedprocentlimit3 )
       {
           if ( Configurator.mConfData.mNoiseTraderStufe1MarketOrderBilligestKauf == 'B' )
           {
             le.mLimit = SystemConstant.BestenOrCheapest;
             le.mReason= "Prozent(" + HelpTool.DoubleTransfer(prozent, 4) + ")<Grenz3 und BilligestKauf";
           }
       }
       else
       {
         if ( Configurator.mConfData.mNoiseTraderStufe4MarketOrderBilligestKauf == 'B' )
         {
           le.mLimit = SystemConstant.BestenOrCheapest;
           le.mReason= "Prozent(" + HelpTool.DoubleTransfer(prozent, 4) + ") >=Grenz3 und BilligestKauf";
         }
       }
    }
    else
    // Verkauf
    {
      if ( prozent < Configurator.mConfData.mNoiseTraderKurschangedprocentlimit1 )
      {
          if ( Configurator.mConfData.mNoiseTraderStufe1MarketOrderBestVerkauf == 'B' )
          {
            le.mLimit = SystemConstant.BestenOrCheapest;
            le.mReason= "Prozent(" + HelpTool.DoubleTransfer(prozent, 4) + ")<Grenz1 und BestVerkauf";
          }
      }
      else
      if ( prozent < Configurator.mConfData.mNoiseTraderKurschangedprocentlimit2 )
      {
          if ( Configurator.mConfData.mNoiseTraderStufe2MarketOrderBestVerkauf == 'B' )
          {
            le.mLimit = SystemConstant.BestenOrCheapest;
            le.mReason= "Prozent(" + HelpTool.DoubleTransfer(prozent, 4) + ")<Grenz2 und BestVerkauf";
          }
      }
      else
      if ( prozent < Configurator.mConfData.mNoiseTraderKurschangedprocentlimit3 )
      {
          if ( Configurator.mConfData.mNoiseTraderStufe1MarketOrderBestVerkauf == 'B' )
          {
            le.mLimit = SystemConstant.BestenOrCheapest;
            le.mReason= "Prozent(" + HelpTool.DoubleTransfer(prozent, 4) + ")<Grenz3 und BestVerkauf";
          }
      }
      else
      {
        if ( Configurator.mConfData.mNoiseTraderStufe4MarketOrderBestVerkauf == 'B' )
        {
          le.mLimit = SystemConstant.BestenOrCheapest;
          le.mReason= "Prozent(" + HelpTool.DoubleTransfer(prozent, 4) + ") >=Grenz3 und BestVerkauf";
        }
      }
    }
    return le;
  }

}