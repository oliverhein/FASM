package de.marketsim.util;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005,2006,2007,2008 </p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class SimulationReportData
{
   public String                    mNetworkUniqueID ;
   public String                    mConfigFile          = null;
   public String                    mNetwork             = null;

   public double                    mTobintaxFesteSteuer = 0.0;
   public double                    mTobintaxExtraSteuer = 0.0;
   public String                    mRuningNo            = null;

   public SimulationReportBaseData  mData1DayAbstand  = new SimulationReportBaseData();
   public SimulationReportBaseData  mData5DayAbstand  = new SimulationReportBaseData();
   public SimulationReportBaseData  mData10DayAbstand = new SimulationReportBaseData();
   public SimulationReportBaseData  mData25DayAbstand = new SimulationReportBaseData();
   public SimulationReportBaseData  mData50DayAbstand = new SimulationReportBaseData();

   /* 2006-06-02 */
   public double Volatility_old          = 0.0;
   public double Volatility_new          = 0.0;
   public double Distortion          = 0.0;
   /* 2006-06-02 */

   //public double Hill_Min     = 0.0;
   //public double Hill_Max     = 0.0;
   public double Hill_Estimator = 0.0;

   public double Korrelation1   = 0.0;
   public double Korrelation2   = 0.0;

   public double mTypeChangeIndicator = 0;

   // measurement of simulation time
   public long   mStartTime;
   public long   mEndTime;
   public long   mDelay;    // StartTime - EndTime
   public long   mMemoryAvailabe;  // in bytes
   
   // New Statistic values  2008.01
   
   public double  mPerformedAktien_Sum = 0.0;
   public double  mPerformedVolume_Sum = 0.0;

}