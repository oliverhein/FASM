package de.marketsim.util;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 *
 * 2005-11-15: Erweiterung: Absolute und Relative Gewinn bze. GewinnProzent
 */

import java.util.Vector;
import de.marketsim.config.Configurator;
import de.marketsim.SystemConstant;

public class DepotRecord implements java.io.Serializable
{

  public String  mAgentName = null;
  public String  mAgentInitTypeName = null;
  public int     mAgentInitType ;

  private Vector HistoryDepot = new Vector();
  public  long   mInitCash = 0;
  public  long   mInitAktienMenge = 0;
  public int     mInitPrice = 0;
  public long    mInitDepot = 0;

  public  long   mCurrentCash = 0;
  public  long   mCurrentAktienMenge = 0;
  public  int    mCurrentPrice = 0;
  public  long   mCurrentDepot = 0;

  // 2007-08-15 daten strukture erweitert
  // für eine neue statistik

  public  long mTotalBuyStuck = 0;
  public  long mTotalSellStuck = 0;

  public  long mTypeChangeCounter = 0;
  // GewinnStatus har 4 Möglichkeiten
  // +
  // -
  // 0
  // *:  nicht berechnbar, weil kein Referenztag da ist.

  public  String mAbsoluteGewinnStatus ="*";
  public  String mRelativeGewinnStatus ="*";

  //AbsoluteGewinn = AktuellerDepot - InitDepot
  public long mAbsoluteGewinn = 0;

  //RelativeGewinn = AktuellerDepot - ReferenzDepot ( Depot vor N Tagen, N ist Parameetr )
  public long mRelativeGewinn = 0;

  //prozent von AbsoluteGewinn
  public double mAbsoluteGewinnProzent = 0.0;
  //prozent von RelativeGewinn
  public double mRelativeGewinnProzent = 0.0;

  // temperary storing of old Depot:
  // It is used to calculate the RelativeGewinn

  public double mMoneyMarket_InitCash1 = 0.0;
  public double mMoneyMarket_InitCash2 = 0.0;
  public double mMoneyMarket_InitKurs  = 1.0;
  public double mMoneyMarket_InitCashDepot = 0.0;

  public double mMoneyMarket_CurrentCash1 = 0.0;
  public double mMoneyMarket_CurrentCash2 = 0.0;
  public double mMoneyMarket_CurrentKurs  = 1.0;
  public double mMoneyMarket_CurrentCashDepot = 0.0;

  public char   mLastWish = SystemConstant.WishType_Wait;
  // Init_Wert: WAIT

  private Vector mDepotHistory = new Vector();

  public DepotRecord()
  {

  }

  /**
   * 2007.03.17
   *
   * Append Cash for BlankoAgent
   */

  public void AppendCash(int pAppendedCash)
  {
     this.mCurrentCash = pAppendedCash + this.mCurrentCash;
  }

  public void AktienMarket_setInitCash(int pCash)
  {
     this.mInitCash    = pCash;
     this.mCurrentCash = pCash;
  }

  public void AktienMarket_setInitAktienMenge(int pAktienMenge)
  {
     this.mInitAktienMenge = pAktienMenge;
     this.mCurrentAktienMenge = pAktienMenge;
  }

  public void AktienMarket_setInitPrice(int pPrice)
  {
     this.mInitPrice    = pPrice;
     updateInitDepot();


     // 2007-10-01
     //
     //this.mCurrentPrice = pPrice;
     //updateCurrentDepot();
     this.mCurrentDepot = this.mInitDepot;

     /* add InitDepot into DepotHistory */
     this.mDepotHistory.add( new Long( this.mInitDepot ) );


  }

  public int AktienMarket_getInitPrice()
  {
     return this.mInitPrice;
  }

  public long AktienMarket_getInitAktienMenge()
  {
     return this.mInitAktienMenge;
  }

  public long AktienMarket_getInitCash()
  {
     return this.mInitCash;
  }

  public long AktienMarket_getInitDepot()
  {
     return this.mInitDepot;
  }

  /**
   * For checking
   * @return
   *
   * 2007-10-01
   */

  public int getDepotHistoryItemNumber()
  {
    return this.mDepotHistory.size();
  }

  /**
   * For checking
   * @return
   *
   * 2007-10-01
   */
  public String getDepotHistoryItem()
  {
    String ss = "";
    for ( int i=0; i<this.mDepotHistory.size(); i++)
    {
       ss = ss + ((Long) this.mDepotHistory.elementAt(i)).longValue()+";";
    }
    return ss;
  }



  private void updateInitDepot()
  {
    if (Configurator.istAktienMarket() )
    {
        this.mInitDepot = this.mInitCash + this.mInitAktienMenge* this.mInitPrice ;
    }
    else
    {
        this.mMoneyMarket_InitCashDepot = this.mMoneyMarket_InitCash1 +
                                          this.mMoneyMarket_InitCash2 / this.mMoneyMarket_InitKurs;
        // 2 Digit nach Komma
        this.mMoneyMarket_InitCashDepot = HelpTool.DoubleTransfer(this.mMoneyMarket_InitCashDepot,2);
    }
  }

  public void AktienMarket_BuyAktien(int pMenge, int pPrice)
  {
     this.mCurrentPrice = pPrice;
     this.mCurrentCash = this.mCurrentCash - pMenge* pPrice;
     this.mCurrentAktienMenge = this.mCurrentAktienMenge + pMenge;
     this.mTotalBuyStuck      = this.mTotalBuyStuck + pMenge;

     updateCurrentDepot();
  }

  public void AktienMarket_SellAktien(int pMenge, int pPrice)
  {
     this.mCurrentPrice = pPrice;
     this.mCurrentCash  = this.mCurrentCash + pMenge* pPrice;
     this.mCurrentAktienMenge = this.mCurrentAktienMenge - pMenge;

     this.mTotalSellStuck = this.mTotalSellStuck + pMenge;

     updateCurrentDepot();
  }

  public void AktienMarket_setCurrentPrice(int pPrice)
  {
     this.mCurrentPrice = pPrice;
     updateCurrentDepot();
  }

  public int AktienMarket_getCurrentPrice()
  {
     return this.mCurrentPrice;
  }

  public long AktienMarket_getCurrentAktienMenge()
  {
     return this.mCurrentAktienMenge;
  }

  public long AktienMarket_getCurrentCash()
  {
     return this.mCurrentCash;
  }

  public long AktienMarket_getCurrentDepot()
  {
    if (Configurator.istAktienMarket())
    {
       return this.mCurrentDepot;
    }
    else
    {
      return (int) this.mMoneyMarket_CurrentCashDepot;
    }
  }

  // GewinnBerechnung
  private void updateCurrentDepot()
  {
    if (Configurator.istAktienMarket())
    {
        updateDepot4AktienMarket();
    }
    else
    {
        updateDepot4MoneyMarket();
    }
  }

  private void updateDepot4AktienMarket()
  {

    this.mCurrentDepot = this.mCurrentCash + this.mCurrentAktienMenge * this.mCurrentPrice ;

    if ( Configurator.mConfData.mAheadDaysForGewinnCalculation == 0 )
    {
        if ( this.mCurrentDepot > this.mInitDepot )
        {
           this.mAbsoluteGewinnStatus = "+";
           this.mRelativeGewinnStatus = "+";
        }
        else
        if ( this.mCurrentDepot >this.mInitDepot )
        {
           this.mAbsoluteGewinnStatus = "-";
           this.mRelativeGewinnStatus = "-";
        }
        else
        {
           this.mAbsoluteGewinnStatus = "0";
           this.mRelativeGewinnStatus = "0";
        }
        this.mAbsoluteGewinn = this.mCurrentDepot - this.mInitDepot ;
        this.mRelativeGewinn = this.mAbsoluteGewinn;

        this.mAbsoluteGewinnProzent = (this.mAbsoluteGewinn * 100.0) / this.mInitDepot ;
        this.mRelativeGewinnProzent = this.mAbsoluteGewinnProzent;
    }
    else
    {
          // 2007-10-01 commented these codes
          //if ( this.mDepotHistory.size() == 0 )
          //{
          //   this.mAbsoluteGewinnStatus = "0";
          //   this.mRelativeGewinnStatus = this.mAbsoluteGewinnStatus;
          //}
          //else
          //{


              if ( this.mDepotHistory.size() >= Configurator.mConfData.mAheadDaysForGewinnCalculation )
              {
                  Long KK = (Long) this.mDepotHistory.elementAt( this.mDepotHistory.size() -
                                                                       Configurator.mConfData.mAheadDaysForGewinnCalculation  );

                  long DepotReferenz = KK.longValue();
                  this.mRelativeGewinn = this.mCurrentDepot - DepotReferenz;
                  this.mRelativeGewinnProzent = (this.mRelativeGewinn * 100.0) / DepotReferenz ;

                  if ( this.mCurrentDepot > DepotReferenz )
                  {
                     this.mRelativeGewinnStatus = "+";
                  }
                  else
                  if ( this.mCurrentDepot == DepotReferenz )
                  {
                     this.mRelativeGewinnStatus = "0";
                  }
                  else
                  {
                    this.mRelativeGewinnStatus = "-";
                  }
              }
              else
              {
                // Keine Möglichkeit für Vergleich, lasse die RelativeGewinn, RelativeGewinnProzent auf 0.0
                // weil es keinen ReferenzWert gibt.

                // RelativeGewinnStatus:  lasse auf Ausgleich
                this.mRelativeGewinnStatus = "*";
                // RelativeGewinn
                this.mRelativeGewinn = 0;
                // RelativeGewinnProzent
                this.mRelativeGewinnProzent = 0.0 ;
              }

              // Element 0 is INITDEPOT
              Long INITDEPOT = (Long) this.mDepotHistory.elementAt( 0 );

              this.mAbsoluteGewinn        = this.mCurrentDepot         -   INITDEPOT.longValue();
              this.mAbsoluteGewinnProzent = this.mAbsoluteGewinn * 100.0 / INITDEPOT.longValue() ;

              if ( this.mCurrentDepot > this.mInitDepot )
              {
                 this.mAbsoluteGewinnStatus = "+";
              }
              else
              if ( this.mCurrentDepot == this.mInitDepot )
              {
                 this.mAbsoluteGewinnStatus = "0";
              }
              else
              {
                this.mAbsoluteGewinnStatus = "-";
              }
          // 2007-10-01 commented
          //}
          // added into History
          this.mDepotHistory.add( new Long(this.mCurrentDepot ) );
   }
  }

  /**
   * This methode will be called by Tobintax Agent
   * @param pAktion
   * @param pCash1
   * @param pCash2
   * @param pKurs
   */
  public void updateRobintaxAgentDepot( char pAktion, double pCash1, double pCash2, double pKurs )
  {
       this.mMoneyMarket_CurrentKurs  = pKurs;
       if ( pAktion == SystemConstant.WishType_Sell )
       {
         // Sell Cash2
         this.mMoneyMarket_CurrentCash1 = HelpTool.DoubleTransfer( this.mMoneyMarket_CurrentCash1 + pCash1 , 2);
         this.mMoneyMarket_CurrentCash2 = HelpTool.DoubleTransfer( this.mMoneyMarket_CurrentCash2 - pCash2 , 2);
       }
       else
       if ( pAktion == SystemConstant.WishType_Buy )
       {
         // Buy Cash2
         this.mMoneyMarket_CurrentCash1 = HelpTool.DoubleTransfer( this.mMoneyMarket_CurrentCash1 - pCash1 , 2);
         this.mMoneyMarket_CurrentCash2 = HelpTool.DoubleTransfer( this.mMoneyMarket_CurrentCash2 + pCash2 , 2);
       }

        this.mMoneyMarket_CurrentCashDepot = this.mMoneyMarket_CurrentCash1 + this.mMoneyMarket_CurrentCash2 / this.mMoneyMarket_CurrentKurs ;
        // Genauigkeit nur bis Cent
        this.mMoneyMarket_CurrentCashDepot = HelpTool.DoubleTransfer(this.mMoneyMarket_CurrentCashDepot, 2);
         // 2 Digit nach Komma
  }

  /**
   * This methode will be called by Tobintax Agent
   * @param pDaliyTobintaxCash1
   * @param pDaliyTobintaxCash2
   */

  public void addDailiyTobintax(double pDaliyTobintaxCash1, double pDaliyTobintaxCash2 )
  {
    this.mMoneyMarket_CurrentCash1 = HelpTool.DoubleTransfer( this.mMoneyMarket_CurrentCash1 + pDaliyTobintaxCash1 , 2);
    this.mMoneyMarket_CurrentCash2 = HelpTool.DoubleTransfer( this.mMoneyMarket_CurrentCash2 + pDaliyTobintaxCash2 , 2);
    this.mMoneyMarket_CurrentCashDepot = this.mMoneyMarket_CurrentCash1 + this.mMoneyMarket_CurrentCash2 / this.mMoneyMarket_CurrentKurs ;
    // Genauigkeit nur bis Cent
    this.mMoneyMarket_CurrentCashDepot = HelpTool.DoubleTransfer(this.mMoneyMarket_CurrentCashDepot, 2);
  }

  public void updateDepot4MoneyMarket()
  {
        this.mMoneyMarket_CurrentCashDepot = this.mMoneyMarket_CurrentCash1 + this.mMoneyMarket_CurrentCash2 / this.mMoneyMarket_CurrentKurs ;
        // Genauigkeit nur bis Cent
        this.mMoneyMarket_CurrentCashDepot = HelpTool.DoubleTransfer(this.mMoneyMarket_CurrentCashDepot, 2);
         // 2 Digit nach Komma

    if ( Configurator.mConfData.mAheadDaysForGewinnCalculation == 0 )
    {
        if ( this.mMoneyMarket_CurrentCashDepot > this.mMoneyMarket_InitCashDepot )
        {
           this.mAbsoluteGewinnStatus = "+";
           this.mRelativeGewinnStatus = "+";
        }
        else
        if ( this.mMoneyMarket_CurrentCashDepot < this.mMoneyMarket_InitCashDepot )
        {
           this.mAbsoluteGewinnStatus = "-";
           this.mRelativeGewinnStatus = "-";
        }
        else
        {
           this.mAbsoluteGewinnStatus = "0";
           this.mRelativeGewinnStatus = "0";
        }

        this.mAbsoluteGewinn = (long) ( this.mMoneyMarket_CurrentCashDepot - this.mMoneyMarket_InitCashDepot ) ;
        this.mRelativeGewinn = this.mAbsoluteGewinn;

        this.mAbsoluteGewinnProzent = this.mAbsoluteGewinn * 100.0 / this.mMoneyMarket_CurrentCashDepot ;
        this.mAbsoluteGewinnProzent = HelpTool.DoubleTransfer(this.mAbsoluteGewinnProzent, 2);
        this.mRelativeGewinnProzent = this.mAbsoluteGewinnProzent;
    }
    else
    {
          if ( this.mDepotHistory.size() == 0 )
          {
             this.mAbsoluteGewinnStatus = "0";
             this.mRelativeGewinnStatus = this.mAbsoluteGewinnStatus;
          }
          else
          {
              if ( this.mDepotHistory.size() >= Configurator.mConfData.mAheadDaysForGewinnCalculation )
              {
                  Long KK = (Long) this.mDepotHistory.elementAt( this.mDepotHistory.size() -
                                                                       Configurator.mConfData.mAheadDaysForGewinnCalculation  );
                  long DepotReferenz = KK.longValue();

                  this.mRelativeGewinn = ( long ) this.mMoneyMarket_CurrentCashDepot - DepotReferenz;
                  this.mRelativeGewinnProzent = this.mRelativeGewinn * 100.0 / DepotReferenz ;

                  this.mRelativeGewinnProzent = HelpTool.DoubleTransfer(this.mRelativeGewinnProzent, 2);

                  if ( this.mMoneyMarket_CurrentCashDepot > DepotReferenz )
                  {
                     this.mRelativeGewinnStatus = "+";
                  }
                  else
                  if ( this.mMoneyMarket_CurrentCashDepot < DepotReferenz )
                  {
                    this.mRelativeGewinnStatus = "-";
                  }
                  else
                  {
                    this.mRelativeGewinnStatus = "0";
                  }
              }
              else
              {
                // Keine Möglichkeit für Vergleich, lasse die RelativeGewinn, RelativeGewinnProzent auf 0.0
                // weil es keinen ReferenzWert gibt.

                // RelativeGewinnStatus:  lasse auf Ausgleich
                this.mRelativeGewinnStatus = "*";
                // RelativeGewinn
                this.mRelativeGewinn = 0;
                // RelativeGewinnProzent
                this.mRelativeGewinnProzent = 0.0 ;
              }

              this.mAbsoluteGewinn = (long) ( this.mMoneyMarket_CurrentCashDepot - this.mMoneyMarket_InitCashDepot );
              this.mAbsoluteGewinnProzent = this.mAbsoluteGewinn * 100.0 / this.mMoneyMarket_CurrentCashDepot ;
              this.mAbsoluteGewinnProzent = HelpTool.DoubleTransfer(this.mAbsoluteGewinnProzent,2);
              if ( this.mMoneyMarket_CurrentCashDepot > this.mMoneyMarket_InitCashDepot )
              {
                 this.mAbsoluteGewinnStatus = "+";
              }
              else
              if ( (int) this.mMoneyMarket_CurrentCashDepot == this.mMoneyMarket_InitCashDepot )
              {
                 this.mAbsoluteGewinnStatus = "0";
              }
              else
              {
                this.mAbsoluteGewinnStatus = "-";
              }
          }
          this.mDepotHistory.add( new Long( (long) this.mMoneyMarket_CurrentCashDepot  ) );
   }

  }


  public int getNumberOfHistoryDepot()
  {
     return this.mDepotHistory.size();
  }

  // folgende Methode für Money Market
  public void MoneyMarket_setInitCash1(double pInitCash1)
  {
     this.mMoneyMarket_InitCash1    = pInitCash1;
     this.mMoneyMarket_CurrentCash1 = pInitCash1;
  }

  public void MoneyMarket_setInitCash2(double pInitCash2)
  {
     this.mMoneyMarket_InitCash2 = pInitCash2;
     this.mMoneyMarket_CurrentCash2 = pInitCash2;
  }

  public void MoneyMarket_setInitKurs(double pInitKurs)
  {
     this.mMoneyMarket_InitKurs    = pInitKurs;
     this.mMoneyMarket_CurrentKurs = pInitKurs;
     this.mMoneyMarket_InitCashDepot = this.mMoneyMarket_InitCash1 +
                                       this.mMoneyMarket_InitCash2 / this.mMoneyMarket_InitKurs;

     this.mMoneyMarket_InitCashDepot = HelpTool.DoubleTransfer(this.mMoneyMarket_InitCashDepot, 2);
     this.mMoneyMarket_CurrentCashDepot = this.mMoneyMarket_InitCashDepot ;
  }

  public void MoneyMarket_setCurrentKurs(double pKurs)
  {
     this.mMoneyMarket_CurrentKurs = pKurs;
     this.updateDepot4MoneyMarket();
  }

  // sell pCash2 (beispiel $), bekommt pCash1 ( euro )
  // 3 variable sind benötigt  wegen TobinTax
  // 3 variable sind benötigt  wegen TobinTax
  // es gibt keine feste Beziehung zwischen pCash1, pCahs2 und pKurs
  // aber wenn TobinTax nicht aktiv ist, dann gibt es die Festbeziehung
  // aber DepotRecord Class muss beide Fälle unterstützen.
  public void MoneyMarket_SellCash2(double pCash1, double pCash2, double pKurs )
  {
       this.mMoneyMarket_CurrentKurs  = pKurs;
       this.mMoneyMarket_CurrentCash1 = HelpTool.DoubleTransfer( this.mMoneyMarket_CurrentCash1 + pCash1 , 2);
       this.mMoneyMarket_CurrentCash2 = HelpTool.DoubleTransfer( this.mMoneyMarket_CurrentCash2 - pCash2 , 2);
       this.updateDepot4MoneyMarket();
  }

  // buy pCash2 (beispiel $), ausgeben pCash1 ( euro )
  // 3 variable sind benötigt  wegen TobinTax
  // es gibt keine feste Beziehung zwischen pCash1, pCahs2 und pKurs
  // aber wenn TobinTax nicht aktiv ist, dann gibt es die Festbeziehung
  // aber DepotRecord Class muss beide Fälle unterstützen.
  public void MoneyMarket_BuyCash2( double pCash1, double pCash2, double pKurs  )
  {
       this.mMoneyMarket_CurrentKurs  = pKurs;
       this.mMoneyMarket_CurrentCash1 = HelpTool.DoubleTransfer( this.mMoneyMarket_CurrentCash1 - pCash1, 2);
       this.mMoneyMarket_CurrentCash2 = HelpTool.DoubleTransfer( this.mMoneyMarket_CurrentCash2 + pCash2, 2);
       this.updateDepot4MoneyMarket();
  }


  public String toString()
  {
    return "InitCash=" + mInitCash+ " mInitAktienMenge" + mInitAktienMenge + " CurCash="+ this.mCurrentCash + " CurAktien=" + this.mCurrentAktienMenge;
  }

}