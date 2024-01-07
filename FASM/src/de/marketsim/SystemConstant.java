package de.marketsim;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class SystemConstant
{
  //////////////////////////////////////////////////////////////////////////////

  public static int  MessageType_Unsupported = 0;
  public static int  MessageType_300InitData = 1;
  public static int  MessageType_Innererwert = 2;
  public static int  MessageType_AktienTrade_Order = 3;
  public static int  MessageType_FriendStatus = 4;

  public static int  MessageType_CashTrade_Order = 5;
  public static int  MessageType_Register = 6;
  public static int  MessageType_UnRegister = 7;

  public static int  MessageType_AgentList = 8;
  public static int  MessageType_StartCommand = 9;
  public static int  MessageType_Depotstate = 10;
  public static int  MessageType_DailyTobintax = 11;
  public static int  MessageType_Close = 12;
  public static int  MessageType_XState = 13;  // Exchange State History
  public static int  MessageType_INITCOMMAND = 14;  // INIT Command
  public static int  MessageType_AgentStatistik = 15;  // Agent Statistik Info
  public static int  MessageType_QUITCOMMAND   = 16;  // QUIT Command
  public static int  MessageType_RUNTIMEPARAMETER = 17;  // RunTime Parameter
  public static int  MessageType_SIMULATORMANAGERLIST= 18; // Simulator List
  // Register Manager sends this Info to DAY
  public static int  MessageType_StoreDailyTradeSummary = 19;
  public static int  MessageType_RegistetrCommunicationManagerWithName = 20;
  public static int  MessageType_FinishCommand = 21;

  public static int  MessageType_InterruptCommand = 22;

  public static int  MessageType_DepotRecord = 23;
  public static int  MessageType_InvestorDepotstate = 24;
  //////////////////////////////////////////////////////////////////////////////
  // TradeResult
  // Umsatz>0
  // Auf der Zeil mit maximal Umsatz,Verkauf-Menge von allen Verkauf-Orders > Kauf-Menge von allen Kauf-Orders

  public static char TradeResult_Geld       ='G';
  // Umsatz>0
  // Auf der Zeil mit maximal Umsatz,Kauf-Menge von allen Kauf-Orders > Verkauf-Menge von allen Verkauf-Orders

  public static char TradeResult_Brief      ='B';
  //Umsatz>0
  // Auf der Zeil mit maximal Umsatz, Kauf-Menge von allen Kauf-Orders = Verkauf-Menge von allen Verkauf-Orders
  public static char TradeResult_Bezahlt    ='b';

  //gar keine Order oder Umsatz=0
  public static char TradeResult_Taxe       ='T';

  public static char WishType_Mixed = 'M';  // mixed Buy and Sell
  public static char WishType_Buy  = 'B';
  public static char WishType_Sell = 'S';
  public static char WishType_Wait = 'N';
  public static char WishType_Sleep = 'P';
  public static int  Limit_CheapestBuy = -1;
  public static int  Limit_BestenSell  = -1;
  public static int  BestenOrCheapest  = -1;

  //////////////////////////////////////////////////////////////////////////////

  public static int    AgentType_INVESTOR          = 1;
  public static int    AgentType_NOISETRADER       = 2;
  public static int    AgentType_RANDOMTRADER      = 3;
  public static int    AgentType_TobinTax          = 4;
  public static int    AgentType_BLANKOAGENT       = 5;
  public static String AgentTypeName_INVESTOR      = "Investor";
  public static String AgentTypeName_NOISETRADER   = "NoiseTrader";
  public static String AgentTypeName_RANDOMTRADER  = "RandomTrader";
  public static String AgentTypeName_RobinTax      = "TobinTax";
  public static String AgentTypeName_BLANKOAGENT   = "BlankoAgent";

  public static String[] AllTypeName =
  {     AgentTypeName_INVESTOR,
        AgentTypeName_NOISETRADER,
        AgentTypeName_RANDOMTRADER,
        AgentTypeName_RobinTax,
        AgentTypeName_BLANKOAGENT
  };

  public static String getOperatorTypeName(int pType)
  {
     return AllTypeName[ pType -1 ];
  }
  //////////////////////////////////////////////////////////////////////////////

  public static int GewinnStatus_AUSGLEICH = 0;
  public static int GewinnStatus_GEWINN  = 1;
  public static int GewinnStatus_VERLUST = 2;

  //////////////////////////////////////////////////////////////////////////////

  public static int MarketMode_AktienMarket = 1;
  public static int MarketMode_CashMarket   = 2;

  public static String DataFormatLanguage_German  ="German";
  public static String DataFormatLanguage_English ="English";
  public static String DataFormatLanguage_Chinese ="Chinese";

  public static String getLimitString( char pAction, double pLimit)
  {
    if ( pLimit == -1.0 )
    {
        if ( pAction == 'B' )
        {
            return "Cheapest Buy";
        }
        else
        {
            return "Best Sell";
        }
    }
    else
    {
       return ""+pLimit;
    }
  }
} //////////////////////////////////////////////////////////////////////////////
