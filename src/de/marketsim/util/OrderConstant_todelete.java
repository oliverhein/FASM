package de.marketsim.util;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class OrderConstant_todelete
{

  // TradeResult
  //Umsatz>0
  // Auf der Zeil mit maximal Umsatz,Verkauf-Menge von allen Verkauf-Orders > Kauf-Menge von allen Kauf-Orders

  public static char TradeResult_Geld       ='G';
  //Umsatz>0
  // Auf der Zeil mit maximal Umsatz,Kauf-Menge von allen Kauf-Orders > Verkauf-Menge von allen Verkauf-Orders

  public static char TradeResult_Brief      ='B';
  //Umsatz>0
  // Auf der Zeil mit maximal Umsatz, Kauf-Menge von allen Kauf-Orders = Verkauf-Menge von allen Verkauf-Orders
  public static char TradeResult_Bezahlt    ='b';

  //gar keine Order oder Umsatz=0
  public static char TradeResult_Taxe     ='T';

  public static char BuyWish  = 'B';
  public static char SellWish = 'S';
  public static char WaitWish = 'N';
  public static int CheapestBuyLimit = -1;
  public static int BestenSellLimit  = -1;

}