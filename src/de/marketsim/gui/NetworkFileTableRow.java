package de.marketsim.gui;

public class NetworkFileTableRow
{
  public String   mNetworkFile;
  public int      mNodes;
  public int      mFundamental;
  public int      mTrend;
  public int      mBlanko;
  public int      mRandomTrader;

  public NetworkFileTableRow(String pNetworkFile, int pNodes, int pFundamental, int pTrend, int pBlankoAgent, int pRandomTrader )
  {
    mNetworkFile   =pNetworkFile;
    mNodes         =pNodes;
    mFundamental   =pFundamental;
    mTrend         =pTrend;
    mBlanko        =pBlankoAgent;
    mRandomTrader  =pRandomTrader;
  }

}