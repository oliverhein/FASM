package de.marketsim.config;

/**
 * <p>Überschrift: FASM Frankfurt Articial Simulation Market</p>
 * <p>Beschreibung: Mircomarket Simulator </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.*;

/**
 * Jede Netzwerk hat eigene Parameter
 * Diese Parameter ist eine Untermenge von der NetworkCommonParameterDataBase
 */

public class SingleNetworkDynamicGeneratedParameter
{
  private  Hashtable   mAgentInitConfigList  = new Hashtable();
  private Vector      mInvestorNameList;
  private Vector      mNoiseTraderNameList;
  private Vector      mBlankoAgentNameList;
  private Vector      mRandomTraderNameList;
  private NetworkCommonParameterDatabase  mNetworkCommonParameterDatabase;

  public SingleNetworkDynamicGeneratedParameter
        ( Vector pInvestorNameList,
          Vector pNoiseTraderNameList,
          Vector pBlankoAgentNameList,
          Vector pRandomTraderNameList )
  {
       this.mInvestorNameList     = pInvestorNameList;
       this.mNoiseTraderNameList  = pNoiseTraderNameList;
       this.mBlankoAgentNameList  = pBlankoAgentNameList;
       this.mRandomTraderNameList = pRandomTraderNameList;
  }

  public void setParameterDataBase( NetworkCommonParameterDatabase pNetworkCommonParameterDatabase )
  {
       this.mNetworkCommonParameterDatabase = pNetworkCommonParameterDatabase;
  }

  public void PrepareParameter()
  {
      this.mAgentInitConfigList.clear();

      this.prepareInvestorParameter();

      this.prepareNoiseTraderParameter();

      this.prepareBlankoAgentParameter();

      this.prepareRandomTraderParameter();

      this.createInvestor_NoiseTrader_BlankoCommonParameter();
}

public Hashtable getAgentInitConfigList()
{
      return this.mAgentInitConfigList;
}

private void prepareNoiseTraderParameter()
{
     Random RandomGen = new Random();
    int InitCash_Min_Max_Diff  = Configurator.mConfData.mNoiseTraderInitCash_Max -
                               Configurator.mConfData.mNoiseTraderInitCash_Min + 1;
    int InitAktien_Min_Max_Diff = Configurator.mConfData.mNoiseTraderInitAktien_Max -
                                Configurator.mConfData.mNoiseTraderInitAktien_Min + 1;

    Vector MovingDaysDatabase = mNetworkCommonParameterDatabase.getDatabaseMovingDays();

    for ( int i=0; i<mNoiseTraderNameList.size(); i++)
    {
      AgentInitParameter  mm = new AgentInitParameter();
      Integer dd = ( Integer ) MovingDaysDatabase.elementAt(i);
      mm.mMovingsday = dd.intValue();
      mm.mInitAktien = Configurator.mConfData.mNoiseTraderInitAktien_Min +
                                    RandomGen.nextInt( InitAktien_Min_Max_Diff );

      mm.mInitCash   = Configurator.mConfData.mNoiseTraderInitCash_Min +
                                    RandomGen.nextInt( InitCash_Min_Max_Diff );

      mAgentInitConfigList.put( mNoiseTraderNameList.elementAt(i), mm );
    }
}

/***
      *
      * Add Blanko Spezielle Parameters
      *
      *
      */

private void prepareBlankoAgentParameter()
{
      Random RandomGen = new Random();
      int InitCash_Min_Max_Diff  = Configurator.mConfData.mBlankoAgentInitCash_Max -
                                   Configurator.mConfData.mBlankoAgentInitCash_Min + 1;
      int InitAktien_Min_Max_Diff= Configurator.mConfData.mBlankoAgentInitAktien_Max -
                                   Configurator.mConfData.mBlankoAgentInitAktien_Min + 1;

      int BlankoAgentDayOfIndexWindow_Min_Max_Diff =
             Configurator.mConfData.mBlankoAgentDayOfIndexWindow_Max -
             Configurator.mConfData.mBlankoAgentDayOfIndexWindow_Min + 1;

      int BlankoAgentInactiveDays_Min_Max_Diff =
          Configurator.mConfData.mBlankoAgentInactiveDays_Max -
          Configurator.mConfData.mBlankoAgentInactiveDays_Min + 1;

      long activation_procent_diff = Math.round  ( ( Configurator.mConfData.mBlankoAgentPlusIndexProcentForActivation_Max -
                                                     Configurator.mConfData.mBlankoAgentPlusIndexProcentForActivation_Min ) * 100.0  ) + 1 ;

      long deactivation_procent_diff = Math.round ( ( Configurator.mConfData.mBlankoAgentMinusIndexProcentForDeactivation_Max -
                                                      Configurator.mConfData.mBlankoAgentMinusIndexProcentForDeactivation_Min ) * 100.0 ) + 1 ;

     Vector MovingDaysDatabase = mNetworkCommonParameterDatabase.getDatabaseMovingDays();

     for ( int i=0; i< this.mBlankoAgentNameList.size(); i++)
     {
           AgentInitParameter  mm = new AgentInitParameter();

           mm.mInitAktien = Configurator.mConfData.mBlankoAgentInitAktien_Min +
                            RandomGen.nextInt( InitAktien_Min_Max_Diff );

           mm.mInitCash   = Configurator.mConfData.mBlankoAgentInitCash_Min +
                            RandomGen.nextInt( InitCash_Min_Max_Diff );

           mm.mBlankoAgentDayOfIndexWindow =
                Configurator.mConfData.mBlankoAgentDayOfIndexWindow_Min +
                RandomGen.nextInt( BlankoAgentDayOfIndexWindow_Min_Max_Diff );

           mm.mBlankoAgentInactiveDays      =
                Configurator.mConfData.mBlankoAgentInactiveDays_Min +
                RandomGen.nextInt( BlankoAgentInactiveDays_Min_Max_Diff );

           mm.mBlankoAgentKursUpProcent4Activation = Configurator.mConfData.mBlankoAgentPlusIndexProcentForActivation_Min +
                                                     RandomGen.nextInt( (int) activation_procent_diff) / 100.0;

           mm.mBlankoAgentKursDownProcent4Deactivation = Configurator.mConfData.mBlankoAgentMinusIndexProcentForDeactivation_Min +
                                                     RandomGen.nextInt( (int) deactivation_procent_diff) / 100.0;

           mAgentInitConfigList.put( mBlankoAgentNameList.elementAt(i), mm );
     }

}

private void prepareRandomTraderParameter()
{
    //todo !!





}

private void prepareInvestorParameter()
  {
    Random RandomGen = new Random();
    int InitCash_Min_Max_Diff  = Configurator.mConfData.mInvestorInitCash_Max -
                                 Configurator.mConfData.mInvestorInitCash_Min + 1;
    int InitAktien_Min_Max_Diff = Configurator.mConfData.mInvestorInitAktien_Max -
                                  Configurator.mConfData.mInvestorInitAktien_Min + 1;

    // get AbschlagProzent from  DataBase

    Vector AbschlagProcentDatabase = mNetworkCommonParameterDatabase.getDatabaseAbschlagProcent();

    for ( int i=0; i<mInvestorNameList.size(); i++)
    {
         AgentInitParameter  mm = new AgentInitParameter();
         Double dd = ( Double ) AbschlagProcentDatabase.elementAt(i);
         mm.mAbschlagProzent = dd.doubleValue();

         mm.mInitAktien = Configurator.mConfData.mInvestorInitAktien_Min +
                                       RandomGen.nextInt( InitAktien_Min_Max_Diff );

         mm.mInitCash   = Configurator.mConfData.mInvestorInitCash_Min +
                                       RandomGen.nextInt( InitCash_Min_Max_Diff );

         mAgentInitConfigList.put( mInvestorNameList.elementAt(i), mm );
    }
}

private void createInvestor_NoiseTrader_BlankoCommonParameter()
{
    Random mRandom = new Random();

    double LimitAdjustDelta =  Configurator.mConfData.mNoiseTrader_MaxLimitAdjust -
      Configurator.mConfData.mNoiseTrader_MinLimitAdjust;

    int kk = (int ) (LimitAdjustDelta * 10 +  1);

    for ( int i=0; i< this.mInvestorNameList.size(); i++)
    {
      AgentInitParameter agentinit    =  ( AgentInitParameter ) this.mAgentInitConfigList.get( this.mInvestorNameList.elementAt(i) ) ;
      if ( agentinit == null )
      {
         agentinit  = new AgentInitParameter();
      }


      agentinit.mName              =  (String) this.mInvestorNameList.elementAt(i) ;
      agentinit.mLostNumberLimit   = 1 + mRandom.nextInt( Configurator.mConfData.mMaxLostNumberSeed );
      agentinit.mDays4AverageLimit = agentinit.mLostNumberLimit;
      agentinit.mLimitAdjust       = Configurator.mConfData.mNoiseTrader_MinLimitAdjust +
                                     mRandom.nextInt(kk)/10.0;
    }

    for ( int i=0; i< this.mNoiseTraderNameList.size(); i++)
    {
      AgentInitParameter agentinit    =  ( AgentInitParameter ) this.mAgentInitConfigList.get( this.mNoiseTraderNameList.elementAt(i) ) ;
      if ( agentinit == null )
      {
         agentinit  = new AgentInitParameter();
      }
      agentinit.mName              =  (String) this.mNoiseTraderNameList.elementAt(i) ;
      agentinit.mLostNumberLimit   = 1 + mRandom.nextInt( Configurator.mConfData.mMaxLostNumberSeed );
      agentinit.mDays4AverageLimit = agentinit.mLostNumberLimit;
      agentinit.mLimitAdjust       = Configurator.mConfData.mNoiseTrader_MinLimitAdjust +
                                     mRandom.nextInt(kk)/10.0;
    }

    for ( int i=0; i< this.mBlankoAgentNameList.size(); i++)
    {
      AgentInitParameter agentinit    =  ( AgentInitParameter ) this.mAgentInitConfigList.get( this.mBlankoAgentNameList.elementAt(i) ) ;
      if ( agentinit == null )
      {
         agentinit  = new AgentInitParameter();
      }
      agentinit.mName              =  (String) this.mBlankoAgentNameList.elementAt(i) ;
      agentinit.mLostNumberLimit   = 1 + mRandom.nextInt( Configurator.mConfData.mMaxLostNumberSeed );
      agentinit.mDays4AverageLimit = agentinit.mLostNumberLimit;
      agentinit.mLimitAdjust       = Configurator.mConfData.mNoiseTrader_MinLimitAdjust +
                                     mRandom.nextInt(kk)/10.0;
    }

}


}