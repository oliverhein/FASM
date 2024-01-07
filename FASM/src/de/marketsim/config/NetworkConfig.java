package de.marketsim.config;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

import de.marketsim.util.SimulationReportData;
import de.marketsim.config.XmlHelpTool;
import de.marketsim.config.AgentDistributor;

public class NetworkConfig implements java.io.Serializable
{

  public String  mUniqueID;

  public AgentDistributor       mOwnNodeDistributor;

  public SimulationReportData   mSimulationReportData =  null;
  public String               mNetworkFileName         = null;
  public  String              mNetworkfilenameOhnePfad = null;
  public boolean              mNetworkFileLoadSuccess  = false;
  public String               mNetworkFileFormat       = null;
  public NetworkFileLoader    mNetworkFileLoader       = null;

  public int      mNodesInNetwork    = 0;
  public int      mInvestorNumber    = 0;
  public int      mNoiseTraderNumber = 0;
  public int      mRandomTraderNumber = 0;
  public int      mBlankoAgentNumber = 0;

  public int      mCurrentRunningNo  = 1;

  private String[] mAgent2SimulatorMapping = new String[0];

  private String[] mSimulatorList;

  // these two parameters are used only for CashMarket
  public double   mFesteTobinTax = 0.0;
  public double   mExtraTobinTax = 0.0;

  private SingleNetworkDynamicGeneratedParameter mSingleNetworkDynamicGeneratedParameter;

  public NetworkConfig( String pNetworkFileName  )
  {
       this.mNetworkFileName         = pNetworkFileName;
       this.mNetworkfilenameOhnePfad = this.RemoveDirectory( pNetworkFileName  );
  }

  public void setSimulatorList(String[] pSimulatorList)
  {
       this.mSimulatorList = pSimulatorList;
  }

  private Vector getNameListRandomTrader()
  {
       Vector vv = new Vector();
       for ( int i=0; i< this.mRandomTraderNumber; i++)
       {
          vv.add("RANDOMTRADER"+ (i+1));
       }
       return vv;
  }

  public void CreateDynamicGeneratedParameter()
  {
      /*
      System.out.println (" Inv "+ this.mNetworkFileLoader.getNameListFundamentalInvestor()  );
      System.out.println (" Nois "+ this.mNetworkFileLoader.getNameListNoiseTrader()  );
      System.out.println (" Blanko "+ this.mNetworkFileLoader.getNameListBlankoAgent()  );
      System.out.println (" Random "+ this.getNameListRandomTrader()  );
      */

      this.mSingleNetworkDynamicGeneratedParameter =
      new  SingleNetworkDynamicGeneratedParameter( this.mNetworkFileLoader.getNameListFundamentalInvestor(),
                                                   this.mNetworkFileLoader.getNameListNoiseTrader(),
                                                   this.mNetworkFileLoader.getNameListBlankoAgent(),
                                                   this.getNameListRandomTrader()
                                                 );
      this.mSingleNetworkDynamicGeneratedParameter.setParameterDataBase( Configurator.mNetworkCommonParameterDatabase  );

      this.mSingleNetworkDynamicGeneratedParameter.PrepareParameter();
  }

  public Hashtable getAgentInitParameter()
  {
      if (  this.mSingleNetworkDynamicGeneratedParameter == null )
      {
           CreateDynamicGeneratedParameter();
      }
      return this.mSingleNetworkDynamicGeneratedParameter.getAgentInitConfigList();
  }

  public String[]  getAgent2SimulatorMapping()
  {
      if ( this.mAgent2SimulatorMapping.length == 0 )
      {
          this.DistributeAgent2Simulator();
      }
      return this.mAgent2SimulatorMapping;
  }

  /**
   * Assign Agents to different Simulator
   * Create List:
   *
   * SIM1;V1;2;V3;v7
   * SIM1;V2;2;V3;v7
   * SIM1;V3;2;V3;v7
   * SIM1;V4;2;V3;v7
   * SIM2;V5;2;V3;v7
   * SIM2;V6;2;V3;v7
   * SIM2;V7;2;V3;v7
   */


  public void DistributeAgent2Simulator()
  {
      this.mAgent2SimulatorMapping=
      Distributor.createDistributionList
      ( this.mNetworkFileLoader.getNodeCommunicationList(),
        this.mNetworkFileLoader.getAgentListWithType(),
        AgentSimulatorManagerContainer.getAgentSimulatorManagerNames() ,
        this.mRandomTraderNumber );
  }

  /*
     This Hashtable will be used for display of the network
     Different AgentType will be displayed in different Geo Form.
  */
  public Hashtable getAgentTypeList()
  {
       return this.mNetworkFileLoader.getAgentListWithType();
  }

  private String RemoveDirectory(String pFileNameWithDirectory )
  {
    String ss = pFileNameWithDirectory;
    if ( ss != null )
    {
       // replace all "/" (unix stile FileName) to Windows FileName Stile with "\"
       ss= ss.replace('/','\\');
       int pos = ss.indexOf("\\");
       if ( pos <0 )
       {
         return ss;
       }
       else
       {
         ss = ss.substring(  pos + 1 );
         while ( ss.indexOf("\\") >0  )
         {
              pos = ss.indexOf("\\");
              ss = ss.substring(  pos + 1 );
         }
       }
       return ss;
    }
    else
    {
       return null;
    }
  }

  public String[] getXmlOutput()
  {
      Vector dd = new Vector();
      dd.add("<Network>");
      dd.add( "    " + XmlHelpTool.getXmlTagFormat( ParameterNameConst.NetworkFile,             this.mNetworkFileName ) );
      dd.add( "    " + XmlHelpTool.getXmlTagFormat( ParameterNameConst.RandomTrader,            this.mRandomTraderNumber ) );
      dd.add( "    " + XmlHelpTool.getXmlTagFormat( ParameterNameConst.TobintaxAgentFestSteuer, this.mFesteTobinTax ) );
      dd.add( "    " + XmlHelpTool.getXmlTagFormat( ParameterNameConst.TobintaxAgentExtraSteuer,this.mExtraTobinTax ) );
      dd.add("</Network>");
      return XmlHelpTool.Vector2StringArray(dd);

  }


}