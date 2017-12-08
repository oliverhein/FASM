package de.marketsim.config;

/**
 * <p>Überschrift: FASM </p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2007 </p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.*;
import  de.marketsim.util.HelpTool;
import  de.marketsim.config.FasmGaussConfig;
import  de.marketsim.config.GaussCreator;

public class NetworkCommonParameterDatabase
{

private   int       mNodeInNetwork     = 0;
private   int       mRandomTrader      = 0;

private  boolean    mInvestorAgentAbschlagProzentCreated = false;
private  boolean    mNoiseTraderAgentMovingDayCreated    = false ;

private  Vector     mDataBaseMovingdays  = new Vector();
private  int        mMovingday_Min       = 0;
private  int        mMovingday_Max       = 0;

private  Vector     mDataBaseAbschlagProcent = new Vector();

private  FasmGaussConfig mFasmGaussConfig = null;

public  NetworkCommonParameterDatabase()
{

}

/*
When only these parameter are changed, call this methode to regenerate
the parameter.
*/

public void setMovingdayMinMax(int pMin, int pMax)
{
    if ( ( this.mMovingday_Min == pMin ) && (this.mMovingday_Max == pMax) )
    {
         // Nothing to do
    }
    else
    {
        this.mMovingday_Min = pMin;
        this.mMovingday_Max = pMax;
        // Regenerate Parameter
        this.generateNoiseTraderAgentMovingDay();
    }
}

public void setMaxNodes(int pNewMaxNodesOfNetwork, int pNewRandomTrader)
{
     this.mNodeInNetwork = pNewMaxNodesOfNetwork;
     this.mRandomTrader  = pNewRandomTrader;
}

/**
 * Achtung:
 * Before call this methode,
 * GaussConfig und MovingDays_Min und Max muss schon gesetzt werden !!!!!
 */

public void changeNodesInNetwork( int pNewMaxNodesOfNetwork )
{
  if ( this.mNodeInNetwork == pNewMaxNodesOfNetwork )
  {
      // Nothing to do;
  }
  else
  {
      this.mNodeInNetwork = pNewMaxNodesOfNetwork;
      this.generateNoiseTraderAgentMovingDay();
      this.GaussGenerateInvestorAgentAbschlagProzentGauss();
  }
}

public void changeRandomderTrader(int pNewRandomTrader)
{
  if ( this.mRandomTrader  != pNewRandomTrader )
  {
      this.mRandomTrader = pNewRandomTrader;
      //??????????????

  }
  else
  {
      // Nothing to do
  }
}

public void generateNoiseTraderAgentMovingDay()
{
    int MovingDays_Min_Max_Diff = this.mMovingday_Max -
                                  this.mMovingday_Min + 1;
    Random RandomGen            = new Random();

    // clear all old datas
    this.mDataBaseMovingdays.clear();
    for (int i=0; i<this.mNodeInNetwork; i++)
    {
      int dd = this.mMovingday_Min +
               RandomGen.nextInt( MovingDays_Min_Max_Diff );
      this.mDataBaseMovingdays.add( new Integer( dd )  );
    }
    this.mNoiseTraderAgentMovingDayCreated = true;
}

public void setInvestorAgentAbschlagProzentGaussConfig( FasmGaussConfig pFasmGaussConfig )
{
   this.mFasmGaussConfig = pFasmGaussConfig;
   // call regenerate
   this.GaussGenerateInvestorAgentAbschlagProzentGauss();
}

/**
 * Create AbschlagProzent mit Gauss Distribution
 * @param pMin
 * @param pMax
 */
public void GaussGenerateInvestorAgentAbschlagProzentGauss( )
{

  this.mFasmGaussConfig.mDataNumber = this.mNodeInNetwork;
  GaussCreator gsc = new GaussCreator(  this.mFasmGaussConfig );
  Vector GaussResult = gsc.getGaussDistribution();
  System.out.println( "AbschlagProzent unter GaussFunktion " );
  gsc.DisplayDD( GaussResult );
  System.out.println( "===========================================" );
  Random RandomGen = new Random();
  // clear all old data
  this.mDataBaseAbschlagProcent.clear();

  // create new  AbschlagProzent
  for (int i=0; i<this.mNodeInNetwork; i++)
  {
    int jj = RandomGen.nextInt( GaussResult.size() );
    double abschlag = ( ( Double )  GaussResult.elementAt( jj ) ).doubleValue();
    this.mDataBaseAbschlagProcent.add( new Double (abschlag) );
  }

  this.mInvestorAgentAbschlagProzentCreated = true;

}

public boolean isInvestorAgentAbschlagProzentCreated()
{
   return mInvestorAgentAbschlagProzentCreated;
}

public boolean isNoiseTraderAgentMovingDayCreated()
{
   return mNoiseTraderAgentMovingDayCreated;
}

public Vector getDatabaseMovingDays()
{
    return this.mDataBaseMovingdays;
}

public Vector getDatabaseAbschlagProcent()
{
    return this.mDataBaseAbschlagProcent;
}

}