package de.marketsim.config;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class ParameterNameConst
{
  ///////////////////////////////////////////////////
  //                                               //
  // Network Node Distribution Parameter Group     //
  //                                               //
  ///////////////////////////////////////////////////
  public static String NetworkFile="NetworkFile";

  //public static String InvestorProcent="InvestorProcent";
  //public static String NoiseTraderProcent="NoiseTraderProcent";

  public static String RandomTrader="RandomTrader";
  public static String NetworkNodeDistributionRandomMode="NetworkNodeDistributionRandomMode";  // this is boolean variable with true/false
  public static String RepeatTimes="RepeatTimes";
  public static String UseCommonNetworkNode2AgentTypeDistribution="UseCommonNode2TypeDistribution";  // this is boolean variable with true/false

  //////////////////////////////////////////////////
  //                                              //
  // General Parameter Group                      //
  //                                              //
  //////////////////////////////////////////////////

  public static String Handelsday                   ="Handelsday";;
  public static String MaximalTagAbweichnung        ="MaximalTagAbweichnung" ;
  public static String InnerWert_Max                ="InnerWert_Max" ;
  public static String InnerWert_Min                ="InnerWert_Min" ;
  public static String InnerWert_Begin              ="InnerWert_Begin" ;
  public static String InnerWert_Muster             ="InnerWert_Muster" ;

  public static String GewinnstatusaustauschInterval="GewinnstatusaustauschInterval";
  public static String DaysOfOnePeriod              ="DaysOfOnePeriod";
  public static String AheadDaysForGewinnCalculation="ReferenceDaysForGewinnCalculation";
  public static String HillEstimatorProcent         ="HillEstimatorProcent";
  public static String HillEstimatorLogFile         ="HillEstimatorLogFile";
  public static String MarketMode                   ="MarketMode";
  public static String TobintaxAgentActive          ="TobintaxAgentActive";
  //public static String DepotSaveMode              ="DepotSaveMode";

  public static String DataFormatLanguage           ="DataFormatLanguage";

  public static String AllowedAbweichungPreis2InnererWert="AllowedAbweichungPreis2InnererWert";  //in %

  public static String AutoCorrelation             ="AutoCorrelation";

  //////////////////////////////////////////////////
  //                                              //
  // Kommunikation Parameter Group                //
  //                                              //
  //////////////////////////////////////////////////

  public static String StatusExchangeProbabiliy = "StatusExchangeProbabiliy";
  public static String MaxLostNumberMode        = "MaxLostNumberMode";
  public static String FixedMaxLostNumber       = "FixedMaxLostNumber";
  public static String MaxLostNumberSeed        = "MaxLostNumberSeed";
  public static String BaseDeviation4PriceLimit = "BaseDeviation4PriceLimit";
  public static String AbschlagFactor           = "AbschlagFactor";
  public static String Orders4AverageLimit      = "Orders4AverageLimit";

  //////////////////////////////////////////////
  //                                          //
  // Investor Parameter                       //
  //                                          //
  //////////////////////////////////////////////

  public static String Investor_InitCash_Min="Investor_InitCash_Min";
  public static String Investor_InitCash_Max="Investor_InitCash_Max";
  public static String Investor_InitStock_Min="Investor_InitStock_Min";
  public static String Investor_InitStock_Max="Investor_InitStock_Max";

  public static String Investor_SleepProcent="Investor_SleepProcent";

  public static String Investor_DynamischAbschlageProzent_Min = "Investor_DynamischAbschlagProzent_Min";
  public static String Investor_DynamischAbschlageProzent_Max = "Investor_DynamischAbschlagProzent_Max";

  public static String Investor_AbschlagGaussMean      = "Investor_AbschlagGaussMean";
  public static String Investor_AbschlagGaussDeviation = "Investor_AbschlagGaussDeviation";

  public static String Investor_AnzahlProzent_LinkBereich = "Investor_AnzahlProzent_LinkBereich";
  public static String Investor_AnzahlProzent_MittBereich = "Investor_AnzahlProzent_MittBereich";
  public static String Investor_AnzahlProzent_RechtBereich = "Investor_AnzahlProzent_RechtBereich";

  public static String Investor_KurschangedprocentLimit1="Investor_KurschangedprocentLimit1";
  public static String Investor_KurschangedprocentLimit2="Investor_KurschangedprocentLimit2";
  public static String Investor_KurschangedprocentLimit3="Investor_KurschangedprocentLimit3";

  public static String Investor_Stufe1MarketOrderBilligestKauf="Investor_Stufe1MarketOrderBilligestKauf";
  public static String Investor_Stufe2MarketOrderBilligestKauf="Investor_Stufe2MarketOrderBilligestKauf";
  public static String Investor_Stufe3MarketOrderBilligestKauf="Investor_Stufe3MarketOrderBilligestKauf";
  public static String Investor_Stufe4MarketOrderBilligestKauf="Investor_Stufe4MarketOrderBilligestKauf";

  public static String Investor_Stufe1MarketOrderBestVerkauf="Investor_Stufe1MarketOrderBestVerkauf";
  public static String Investor_Stufe2MarketOrderBestVerkauf="Investor_Stufe2MarketOrderBestVerkauf";
  public static String Investor_Stufe3MarketOrderBestVerkauf="Investor_Stufe3MarketOrderBestVerkauf";
  public static String Investor_Stufe4MarketOrderBestVerkauf="Investor_Stufe4MarketOrderBestVerkauf";

  public static String Investor_Order_Stufe1="Investor_Order_Stufe1" ;
  public static String Investor_Order_Stufe2="Investor_Order_Stufe2" ;
  public static String Investor_Order_Stufe3="Investor_Order_Stufe3" ;
  public static String Investor_Order_Stufe4="Investor_Order_Stufe4" ;

  public static String Investor_InnererWertIntervall_Untergrenz  = "InnererWertIntervall_Untergrenz";  // in Prozent
  public static String Investor_InnererWertIntervall_Obengrenz   = "InnererWertIntervall_Obengrenz";   // in Prozent
  public static String Investor_KursAnderung_Schwelle            = "KursAnderung_Schwelle";            // in Prozent
  public static String Investor_AktuellerInnererWert_Potenzial   = "AktuellerInnererWert_Potenzial";   // in Prozent
  public static String Investor_KursAnderung_ReferenzTag         = "KursAnderung_ReferenzTag";
  public static String InvestorAffectedByOtherNode               = "InvestorAffectedByOtherNode";

  ////////////////////////////////////////////////
  //                                            //
  // NoiseTrader Parameter                      //
  //                                            //
  ////////////////////////////////////////////////

  public static String NoiseTrader_InitCash_Min="NoiseTrader_InitCash_Min";
  public static String NoiseTrader_InitCash_Max="NoiseTrader_InitCash_Max";

  public static String NoiseTrader_InitStock_Min="NoiseTrader_InitStock_Min";
  public static String NoiseTrader_InitStock_Max="NoiseTrader_InitStock_Max";

  public static String NoiseTrader_SleepProcent="NoiseTrader_SleepProcent";

  public static String NoiseTrader_MinMovingDays4AveragePrice="NoiseTrader_MinMovingDays4AveragePrice";
  public static String NoiseTrader_MaxMovingDays4AveragePrice="NoiseTrader_MaxMovingDays4AveragePrice";

  public static String NoiseTrader_MinLimitAdjust="NoiseTrader_MinLimitAdjust";
  public static String NoiseTrader_MaxLimitAdjust="NoiseTrader_MaxLimitAdjust";

  public static String NoiseTrader_KurschangedprocentLimit1="NoiseTrader_KurschangedprocentLimit1";
  public static String NoiseTrader_KurschangedprocentLimit2="NoiseTrader_KurschangedprocentLimit2";
  public static String NoiseTrader_KurschangedprocentLimit3="NoiseTrader_KurschangedprocentLimit3";

  public static String NoiseTrader_Order_Stufe1="NoiseTrader_Order_Stufe1";
  public static String NoiseTrader_Order_Stufe2="NoiseTrader_Order_Stufe2";
  public static String NoiseTrader_Order_Stufe3="NoiseTrader_Order_Stufe3";
  public static String NoiseTrader_Order_Stufe4="NoiseTrader_Order_Stufe4";

  public static String NoiseTrader_Stufe1MarketOrderBilligestKauf="NoiseTrader_Stufe1MarketOrderBilligestKauf";
  public static String NoiseTrader_Stufe2MarketOrderBilligestKauf="NoiseTrader_Stufe2MarketOrderBilligestKauf";
  public static String NoiseTrader_Stufe3MarketOrderBilligestKauf="NoiseTrader_Stufe3MarketOrderBilligestKauf";
  public static String NoiseTrader_Stufe4MarketOrderBilligestKauf="NoiseTrader_Stufe4MarketOrderBilligestKauf";

  public static String NoiseTrader_Stufe1MarketOrderBestVerkauf="NoiseTrader_Stufe1MarketOrderBestVerkauf";
  public static String NoiseTrader_Stufe2MarketOrderBestVerkauf="NoiseTrader_Stufe2MarketOrderBestVerkauf";
  public static String NoiseTrader_Stufe3MarketOrderBestVerkauf="NoiseTrader_Stufe3MarketOrderBestVerkauf";
  public static String NoiseTrader_Stufe4MarketOrderBestVerkauf="NoiseTrader_Stufe4MarketOrderBestVerkauf";
  public static String NoiseTraderAffectedByOtherNode          ="NoiseTraderAffectedByOtherNode";
  ///////////////////////////////////////////////////
  //                                               //
  // RandomTrader Parameter                        //
  //                                               //
  ///////////////////////////////////////////////////

  public static String RandomTrader_InitCash="RandomTrader_InitCash";
  public static String RandomTrader_InitStock="RandomTrader_InitStock";
  public static String RandomTraderBuyProbability="RandomTraderBuyProbability";

  public static String RandomTraderBuyProbabilityChepest="RandomTraderBuyProbabilityChepest";
  public static String RandomTraderBuyProbabilityIndexBased="RandomTraderBuyProbabilityIndexBased";
  public static String RandomTraderSellProbability="RandomTraderSellProbability";
  public static String RandomTraderSellProbabilityIndexBased="RandomTraderSellProbabilityIndexBased";
  public static String RandomTraderSellProbabilityBest="RandomTraderSellProbabilityBest";
  public static String RandomTraderWaitProbability="RandomTraderWaitProbability";
  public static String RandomTraderMinMenge="RandomTraderMinMenge";
  public static String RandomTraderMaxMenge="RandomTraderMaxMenge";
  public static String RandomTraderRandomSeed4Decision="RandomTraderRandomSeed4Decision";

  ////////////////////////////////////////////////
  //                                            //
  // BlankoAgent Parameter                       //
  //                                            //
  ////////////////////////////////////////////////

  public static String BlankoAgent_InitCash_Min="BlankoAgent_InitCash_Min";
  public static String BlankoAgent_InitCash_Max="BlankoAgent_InitCash_Max";
  public static String BlankoAgent_InitStock_Min="BlankoAgent_InitStock_Min";
  public static String BlankoAgent_InitStock_Max="BlankoAgent_InitStock_Max";

  public static String BlankoAgentPlusIndexProcentForActivation_Min    ="BlankoAgentPlusIndexProcentForActivation_Min";
  public static String BlankoAgentPlusIndexProcentForActivation_Max    ="BlankoAgentPlusIndexProcentForActivation_Max";
  public static String BlankoAgentMinusIndexProcentForDeactivation_Min ="BlankoAgentMinusIndexProcentForDeactivation_Min";
  public static String BlankoAgentMinusIndexProcentForDeactivation_Max ="BlankoAgentMinusIndexProcentForDeactivation_Max";

  public static String BlankoAgentMindestActiveDays                ="BlankoAgentMindestActiveDays";
  public static String BlankoAgentDayOfIndexWindow_Min             ="BlankoAgentDayOfIndexWindow_Min";
  public static String BlankoAgentDayOfIndexWindow_Max             ="BlankoAgentDayOfIndexWindow_Max";
  public static String BlankoAgentInactiveDays_Min                 ="BlankoAgentInactiveDays_Min";
  public static String BlankoAgentInactiveDays_Max                 ="BlankoAgentInactiveDays_Max";
  public static String BlankoAgent_SleepProcent                    ="BlankoAgent_SleepProcent";

  public static String BlankoAgent_DaysOfTotalSell                 ="BlankoAgent_DaysOfTotalSell";

  public static String BlankoAgent_CashAppendAllowed               ="BlankoAgent_CashAppendAllowed";
  public static String BlankoAgent_AppendCash                      ="BlankoAgent_AppendCash";

  ///////////////////////////////////////////////////
  //                                               //
  // Tobintax Agent Parameter                      //
  //                                               //
  ///////////////////////////////////////////////////

  public static String TobintaxAgentFestSteuer      ="TobintaxFestTax";
  public static String TobintaxAgentExtraSteuer     ="TobintaxExtraTax";
  public static String TobintaxAgentTag4AverageKurs ="TobintaxTag4AverageKurs";
  public static String TobintaxAgentTradeProzent    ="TobintaxTradeProzent";
  public static String TobintaxInterventionsband    ="TobintaxInterventionsband";
  public static String Cash1_Name                   ="Cash1_Name";
  public static String Cash2_Name                   ="Cash2_Name";

  ///////////////////////////////////////////////////
  //                                               //
  // Logging Parameter                             //
  //                                               //
  ///////////////////////////////////////////////////

  public static String LogDailyTradeBook      ="LogDailyTradeBook";
  public static String LogAgentExchangeHistoy ="LogAgentExchangeHistoy";
  public static String LogAgentDailyDepot     ="LogAgentDailyDepot";

}