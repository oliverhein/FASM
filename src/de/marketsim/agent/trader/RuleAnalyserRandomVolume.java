package de.marketsim.agent.trader;

/**
 * <p>Überschrift: Markt Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.*;
import de.marketsim.util.*;
import de.marketsim.SystemConstant;

public class RuleAnalyserRandomVolume
{

  private static Hashtable  InvestorMarketDecisionList = new Hashtable();
  private static Hashtable  NoiseTraderMarketDecisionList = new Hashtable();

  public static char getInvestorMarketAnalyse(int pDay, String pRule)
  {
      String ss = (String) InvestorMarketDecisionList.get( pDay+ "." + pRule );
      if ( ss == null )
      {
        // Bis jetzt: the decision for this Day with this Rule has not been decided.
        return ' ';
      }
      else
      {
        return ss.charAt(0);
      }
  }

  public static char getNoiseTraderMarketAnalyse(int pDay, String pRule)
  {
      String ss = (String) NoiseTraderMarketDecisionList.get( pDay+ "." + pRule );
      if ( ss == null )
      {
        // Bis jetzt: the decision for this Day with this Rule has not been decided.
        return ' ';
      }
      else
      {
        return ss.charAt(0);
      }
  }

  // make the decision for this Day with this Rule
  // Multi-threads Models
  // Only the 1. Thread make the calculation really.
  public static synchronized char InvestorMarketAnalyse(int pDay, String pRule, int pCurrentInnererWert, int pAbschlagProzent)
  {
      char checkresult=getInvestorMarketAnalyse(pDay, pRule);
      char newresult;
      if ( checkresult == ' ' )
      {
            // make the calculation really
            if ( pRule.equalsIgnoreCase("R1") )
            {
              newresult = InvestorMarktAnalyse_Rule1( pCurrentInnererWert,  pAbschlagProzent);
            }
            else
            if ( pRule.equalsIgnoreCase("R2") )
            {
              newresult = InvestorMarktAnalyse_Rule2( pCurrentInnererWert,  pAbschlagProzent);
            }
            else
            {
              newresult = InvestorMarktAnalyse_Rule3( pCurrentInnererWert,  pAbschlagProzent);
            }
            String ss = " ".replace(' ', newresult);
            InvestorMarketDecisionList.put( pDay+ "." + pRule, ss );
            // remove the unused decision from the list
            InvestorMarketDecisionList.remove( (pDay-2)+ "." + pRule);
            return newresult;
      }
      else
      {
        return checkresult;
      }
 }

 private static char InvestorMarktAnalyse_Rule1(  int pCurrentInnererWert, int  pAbschlagProzent)
 {
   char suggestition='0';
   double dd1 = pCurrentInnererWert * ( 1- pAbschlagProzent/100.0);
   double dd2 = pCurrentInnererWert * ( 1 + pAbschlagProzent/100.0);

   double    lastprice = PriceContainer.getLastPrice();
   //  System.out.println( "Investor-R1: Abschlag-Prozent=" + pAbschlagProzent + " InnererWert=" + pCurrentInnererWert + " dd1=" + dd1 + " LastPrice=" + lastprice );

   String tt="";
   if ( lastprice < dd1)
   {
      suggestition = 'B';
      tt = "Buy";
      return suggestition;
   }
   else
   if ( lastprice > dd2  )
   {
       suggestition = 'S';
       tt = "Sell";
       return suggestition;
   }
   else
   {
     return suggestition;
   }
 }

 private static char InvestorMarktAnalyse_Rule2(  int pCurrentInnererWert, int pAbschlagProzent )
 {

   char suggestition='0';
   String tt="wait";

   // Untergrenz
   double untergrenz = pCurrentInnererWert * ( 1 - pAbschlagProzent/100.0);

   // Obergrenz
   double obengrenz = pCurrentInnererWert * ( 1 + pAbschlagProzent/100.0);

   double    lastprice = PriceContainer.getLastPrice();

   //System.out.println( "Investor-R2: Abschlag-Prozent=" + pAbschlagProzent + " InnererWert=" + pCurrentInnererWert + " dd1=" + dd1 + " LastPrice=" + lastprice );

   if ( lastprice < untergrenz )
   {
      suggestition = 'B';
      tt = "Buy";
      return suggestition;
   }
   else
   if (  lastprice > obengrenz  )
   {
     suggestition = 'S';
     tt = "Sell";
     return suggestition;
   }
   else
   {
     return suggestition;
   }
 }

 private static char InvestorMarktAnalyse_Rule3( int pCurrentInnererWert, int pAbschlagProzent )
 {
      char suggestition='0';
      String tt="wait";
      // untergrenz
      double untergrenz = pCurrentInnererWert * ( 1- pAbschlagProzent/100.0 );
      // obengrenz
      double obengrenz = pCurrentInnererWert * ( 1 + pAbschlagProzent/100.0 );
      double    lastprice = PriceContainer.getLastPrice();

      if ( lastprice < untergrenz )
      {
        suggestition = 'B';
        tt = "Buy";
        return suggestition;
      }
      else
      if (  lastprice > obengrenz )
      {
        suggestition = 'S';
        tt = "Sell";
        return suggestition;
      }
      else
      {
        return suggestition;
      }
 }

 private static int AdjustPrice ( int pTempPrice, int pYesterdayPrice)
  {
    int adjustedprice = 0;
    if (  ( Math.abs (pTempPrice - pYesterdayPrice ) * 1.0 / pYesterdayPrice ) > 0.03 )
    {
       if  ( pTempPrice > pYesterdayPrice )
       {
          adjustedprice = (int)( pYesterdayPrice * (1+0.03) );
       }
       else
       {
          adjustedprice = (int) ( pYesterdayPrice * (1-0.03) );
       }
       return adjustedprice;
    }
    else
    {
       return pTempPrice;
    }
  }

  // make the Limit for Investor
  public static int makeInvestorLimit( String Rule, int pInnererWert, int pAbschlahProzent, char pSuggestion )
  {
     if (  pSuggestion == 'B' )
     {
       // Before: 19.04.2005 (int) ( pInnererWert * ( 1 + pAbschlahProzent/100.0 ) ) ;
       return (int) ( pInnererWert * ( 1 - pAbschlahProzent/100.0 ) ) ;
     }
     else
     if (  pSuggestion == 'S' )
     {
         // Before: 19.04.2005 war: (int) ( pInnererWert * ( 1 - pAbschlahProzent/100.0 ) ) ;
         // Abschlag: XXX% ,
         // die Prozent wird gesetzt durch Parameter, der von StockStore beim Hochfahren gesendet ist.
        return (int) ( pInnererWert * ( 1 + pAbschlahProzent/100.0 ) ) ;
     }
     else
     {
        return 0;
     }
  }

  // make the decision for this Day with this Rule
  // Multi-threads Models
  // Only the 1. Thread make the calculation really.

  public static synchronized char NoiseTraderMarketAnalyse(int pDay, String pRule )
  {
      char checkresult = getNoiseTraderMarketAnalyse(pDay, pRule);
      char newresult;
      if ( checkresult == ' ' )
      {
            // make the calculation really
            if ( pRule.equalsIgnoreCase("R1") )
            {
              newresult = NoiseTraderMarktAnalyse_R1();
            }
            else
            if ( pRule.equalsIgnoreCase("R2") )
            {
              newresult = NoiseTraderMarktAnalyse_R2();
            }
            else
            {
              newresult = NoiseTraderMarktAnalyse_R3();
            }
            String ss = " ".replace(' ', newresult);
            NoiseTraderMarketDecisionList.put( pDay+ "." + pRule, ss );
            // remove the unused decision from the list
            NoiseTraderMarketDecisionList.remove( (pDay-2)+ "." + pRule);
            return newresult;
      }
      else
      {
        return checkresult;
      }
  }

  private static char NoiseTraderMarktAnalyse_R1( )
  {
      // Average of last 300 Prices
      double sum = 0.0;
      Vector allprice = PriceContainer.getAllPrice();
      for (int i=0;i<300;i++)
      {
         sum = sum + (( Integer ) allprice.elementAt( allprice.size()-1 -i ) ).intValue();
      }

      double Avg300 = sum / 300.0;
      double AktPrice =  PriceContainer.getLastPrice();
      char suggestition = 'O';
      String tt = "wait";
      if ( Avg300 > AktPrice )
      {
         suggestition = 'S';
         tt = "Sell";
      }
      else
      if ( Avg300 < AktPrice )
      {
         suggestition = 'B';
         tt="Buy";
      }
      return suggestition;
  }

  private static char NoiseTraderMarktAnalyse_R2()
  {
      // Average of last 150 Price
      double sum = 0.0;
      Vector allprice = PriceContainer.getAllPrice();
      for (int i=0; i<150;i++)
      {
         sum = sum + (( Integer ) allprice.elementAt( allprice.size() -1 - i) ).intValue();
      }
      double Avg150 = sum / 150.0 ;
      double AktPrice = PriceContainer.getLastPrice();

      char suggestition = 'O';
      String tt = "wait";
      if ( Avg150 > AktPrice )
      {
         suggestition = 'S';
         tt = "Sell";
      }
      else
      if ( Avg150 < AktPrice )
      {
         suggestition = 'B';
         tt="Buy";
      }
      return suggestition;
  }

  private static char NoiseTraderMarktAnalyse_R3( )
  {
      // Average of last 50 Price
      double sum = 0.0;
      Vector allprice = PriceContainer.getAllPrice();
      for (int i=0; i<50;i++)
      {
         sum = sum + (( Integer ) allprice.elementAt( allprice.size()-1 -i ) ).intValue();
      }
      double Avg50 = sum / 50.0;
      double AktPrice = PriceContainer.getLastPrice();

      char suggestition = 'O';
      String tt = "wait";
      if ( Avg50 > AktPrice )
      {
         suggestition = 'S';
         tt = "Sell";
      }
      else
      if ( Avg50 < AktPrice )
      {
         suggestition = 'B';
         tt="Buy";
      }
      return suggestition;
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

  // The new Limit totaly depends on the Kurzzusatz "PerformStatus of Yesterday"
  public static double makeNoiseTraderLimit(char marketdecision, OrderPerformedStatus  pPerformedStatus )
  {
         if (pPerformedStatus.getYesterdayTradeResult() == SystemConstant.TradeResult_Bezahlt )
         {
           // use the price of yesterday
           return pPerformedStatus.getYesterdayPrice();
         }
         else
         if (pPerformedStatus.getYesterdayTradeResult()==SystemConstant.TradeResult_Geld)
         {
           // wanted to buy, but the buy-wish is not fulfiled or only partially fulfiled.
           // so Limit (BUY-LIMIT or SELL_LIMIT) for today will be increased
           return (int) (pPerformedStatus.getYesterdayPrice() * ( 1 + 0.01) );
         }
         else
         if (pPerformedStatus.getYesterdayTradeResult() == SystemConstant.TradeResult_Brief )
         {
           // wanted to sell, but the sell-wish is not fulfiled or only partially fulfiled.
           // so Limit (BUY-LIMIT or SELL_LIMIT) will be decreased
           return (int) (pPerformedStatus.getYesterdayPrice() * ( 1-0.01 ));
         }
         else
         {
           // Kurszusatz ist "T" Taxte
           return pPerformedStatus.getYesterdayPrice();
         }
  }

}