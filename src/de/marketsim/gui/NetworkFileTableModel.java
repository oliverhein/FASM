package de.marketsim.gui;

import javax.swing.table.*;
import  java.util.Vector;
import  de.marketsim.config.NetworkFileLoader;

public class NetworkFileTableModel extends  DefaultTableModel
{

  private Vector mNetworkFileLoaderList = new Vector();

  private int mMaxNetworkNodes      = 0;
  private int mMaxRandomTraderNodes = 0;

  public NetworkFileTableModel()
  {
     super();
     this.setColumnCount(6);
     this.setColumnIdentifiers( new String[] {"Networkfile", "Nodes", "Fundamental", "Trend", "Blanko", "Liquidity" } );
  }

  public void removeAllOldDaten()
  {
      // Remove all row Daten
      while ( this.getRowCount() > 0 )
      {
         this.removeRow(0);
      }

      mNetworkFileLoaderList.clear();
      mMaxNetworkNodes      = 0;
      mMaxRandomTraderNodes = 0;

  }

  public int getMaxRandomTraderNodes()
  {
      return mMaxRandomTraderNodes;
  }

  public boolean hasNetworkFileAlreadyExisted(String pNetworkfilename )
  {
      int rows = this.getRowCount();
      if ( rows == 0 )
      {
          return false;
      }
      else
      {
         for ( int j=0;  j < rows; j++)
         {
            String onenetworkfilename = (String) this.getValueAt(j,0);
            if  ( onenetworkfilename.equals( pNetworkfilename ) )
            {
                return true;
            }
         }
      }
      return false;
  }

  public int getMaxNetworkNodes()
  {
      return mMaxNetworkNodes;
  }

  public void addNewNetworkFile(String pFileName,
                                int pNodes,
                                int pFundamental,
                                int pTrend,
                                int  pBlanko,
                                int pRandomTrader,
                                NetworkFileLoader pLoader )
  {
      Object[] tablecols = new Object[ 6 ];
      tablecols[0] = pFileName;
      tablecols[1] = "" + pNodes;
      tablecols[2] = "" + pFundamental;
      tablecols[3] = "" + pTrend;
      tablecols[4] = "" + pBlanko;
      tablecols[5] = "" + pRandomTrader;
      this.addRow( tablecols );
      this.mNetworkFileLoaderList.add( pLoader );
      // Updates zwei Maximal Werte
      if ( pNodes > this.mMaxNetworkNodes )
      {
        this.mMaxNetworkNodes = pNodes;
      }
      if ( pRandomTrader > this.mMaxRandomTraderNodes )
      {
        this.mMaxRandomTraderNodes = pRandomTrader;
      }
  }

  public boolean isTableEmpty()
  {
      return this.getRowCount() == 0;
  }

  public void RemoveNetwork(int pRowNumber)
  {
     this.removeRow                    ( pRowNumber );
     this.mNetworkFileLoaderList.remove( pRowNumber );

     // Update zwei max werte

     this.mMaxNetworkNodes      = 0;
     this.mMaxRandomTraderNodes = 0;

     for ( int i=0; i< this.getRowCount(); i++)
     {
           int     nodes         =  Integer.parseInt  ( ( String ) this.getValueAt( pRowNumber, 1 ) );
           int     randomtrader  =  Integer.parseInt  ( ( String ) this.getValueAt( pRowNumber, 5 ) );

           if ( nodes > this.mMaxNetworkNodes )
           {
             this.mMaxNetworkNodes = nodes;
           }
           if ( randomtrader > this.mMaxRandomTraderNodes )
           {
             this.mMaxRandomTraderNodes = randomtrader;
           }
     }
  }

  public NetworkFileLoader[] getNetworkFileLoaderList()
  {
       NetworkFileLoader[] tt = new NetworkFileLoader[this.mNetworkFileLoaderList.size()];
       for ( int i=0; i<this.mNetworkFileLoaderList.size(); i++)
       {
         tt[i] = ( NetworkFileLoader ) this.mNetworkFileLoaderList.elementAt(i);
       }
       return tt;
  }

  public NetworkFileTableRow[] getAllNetworkFiles()
  {
      int j = this.getRowCount();
      NetworkFileTableRow[] tt = new NetworkFileTableRow[ j ];
      for ( int i=0; i<j; i++)
      {
         String  networkfile   =                      ( String ) this.getValueAt( i, 0 );
         int     nodes         =  Integer.parseInt  ( ( String ) this.getValueAt( i, 1 ) );
         int     fundamental   =  Integer.parseInt  ( ( String ) this.getValueAt( i, 2 ) );
         int     trend         =  Integer.parseInt  ( ( String ) this.getValueAt( i, 3 ) );
         int     blanko        =  Integer.parseInt  ( ( String ) this.getValueAt( i, 4 ) );
         int     randomtrader  =  Integer.parseInt  ( ( String ) this.getValueAt( i, 5 ) );
         tt[i] = new NetworkFileTableRow(networkfile, nodes, fundamental, trend, blanko, randomtrader );
      }
      return tt;
  }

  public NetworkFileTableRow getNetworkFileTableRow(int pRowNumber)
  {
      String  networkfile   =                      ( String ) this.getValueAt( pRowNumber, 0 );
      int     nodes         =  Integer.parseInt  ( ( String ) this.getValueAt( pRowNumber, 1 ) );
      int     fundamental   =  Integer.parseInt  ( ( String ) this.getValueAt( pRowNumber, 2 ) );
      int     trend         =  Integer.parseInt  ( ( String ) this.getValueAt( pRowNumber, 3 ) );
      int     blank         =  Integer.parseInt  ( ( String ) this.getValueAt( pRowNumber, 4 ) );
      int     randomtrader  =  Integer.parseInt  ( ( String ) this.getValueAt( pRowNumber, 5 ) );
      return  new NetworkFileTableRow(networkfile, nodes, fundamental, trend, blank, randomtrader);

  }

  public String getNetworkFile(int pRowNumber)
  {
     return (String) this.getValueAt( pRowNumber, 0 );
  }

  public NetworkFileLoader getNetworkFileLoader(int pRowNumber)
  {
     return (NetworkFileLoader) this.mNetworkFileLoaderList.elementAt( pRowNumber );
  }

  public Class getColumnClass(int c)
  {
        return new String("").getClass();
  }

  // Alle Felder kann man nicht editieren.
  public boolean isCellEditable(int row, int col)
  {
        if ( col == 5 )
        {
          return true;
        }
        else
        {
           return false;
        }
  }

}