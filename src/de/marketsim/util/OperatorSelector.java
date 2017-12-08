package de.marketsim.util;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.*;

public class OperatorSelector
{

  private Vector Investor = null;
  private Vector NoiseTrader = null;

  public OperatorSelector()
  {

  }

  public Vector getInvestor()
  {
     return this.Investor;
  }

  public Vector getNoiseTrader()
  {
     return this.NoiseTrader;
  }

  public void setInvestor(Vector pList)
  {
     this.Investor = pList;
  }

  public void setNoiseTrader(Vector pList)
  {
     this.NoiseTrader = pList;
  }

  public Vector RandomSelect(int pRequiredOperator)
  {

     Vector result = new Vector();
     java.util.Random randomgen = new java.util.Random(100);

     String SelectedInvestorIndex = "";
     String SelectedNoiseTraderIndex = "";
     boolean SelectFromInvestor = true;

     int i = 0;
     while (i < pRequiredOperator )
     {
         if ( this.Investor.size()>0 )
         {
                  int index = randomgen.nextInt( this.Investor.size() );
                  if ( SelectedInvestorIndex.indexOf( index+"-") < 0 )
                  {
                    // Select is successful
                    result.add( this.Investor.elementAt(index) );
                    SelectedInvestorIndex = SelectedInvestorIndex + index+"-";
                    i++;
                  }
         }

         if ( this.NoiseTrader.size()>0 )
         {
                  int index = randomgen.nextInt( this.NoiseTrader.size() );
                  if ( SelectedNoiseTraderIndex.indexOf( index+"-") < 0 )
                  {
                    // Select is successful
                    result.add( this.NoiseTrader.elementAt(index) );
                    SelectedNoiseTraderIndex = SelectedNoiseTraderIndex + index+"-";
                    i++;
                  }
         }
     }

     System.out.println("OperatorSelector has selected " + result.size()+ " Operators.");
     return result;
  }

}