/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author  Xining Wang
 * @version 1.0
 */

package de.marketsim.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import com.borland.jbcl.layout.*;

import de.marketsim.util.HelpTool;
import de.marketsim.gui.BasePanel;
import de.marketsim.config.Configurator;

public class HistoryFrame extends JFrame
{

  private JPanel  mBlankPanel  =  new JPanel();

  // 2 Chart: Price and Innerwert
  private BasePanel2Chart mPricePanel  = new BasePanel2Chart();

  // Chart: Difference = Price - Innerwert
  private BasePanel mDifferencePanel  = new BasePanel();

  // Umsatz Chart
  private BasePanel       mUmsatzPanel = new BasePanel();

  // Investor Number, NoiseTrader Number, BlankoAgent Chart
  //private BasePanel2Chart mAgentAnzahlPanel = new BasePanel2Chart();
  private BasePanel3Chart mAgentAnzahlPanel = new BasePanel3Chart();

  // LogRenditen Chart
  private BasePanel       mLogRenditenPanel = new BasePanel();

  private String mSubTitle = "";
  private String mMaintitel= "Model Index";

  TitledBorder titledBorder1;
  Object  synobj = new Object();
  Vector  Umsatz        = new Vector();
  Vector  Price         = new Vector();
  Vector  Difference    = new Vector();
  Vector  Innerwert     = new Vector();
  Vector  InvestorNumber    = new Vector();
  Vector  NoiseTraderNumber  = new Vector();
  Vector  BlankoAgentNumber  = new Vector();

  Vector  Logrenditen  = new Vector();
  boolean mShowInnererWertEnabled  = true;
  private int databeginindex = -1;
  private PaneLayout paneLayout1 = new PaneLayout();
  private BoxLayout2 boxLayout21 = new BoxLayout2();

  private int mXScalNumber = 300; // Default 300

  public HistoryFrame()
  {
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

    try
    {

       this.mPricePanel.setDataType( "Integer" );
       this.mPricePanel.setDataType2( "Integer" );

       if ( Configurator.istAktienMarket() )
       {
          this.mPricePanel.setPreiceTitel("Price");
       }
       else
       {
          this.mPricePanel.setPreiceTitel("Price");
       }
       if (mShowInnererWertEnabled)
       {
         this.mPricePanel.setInnerwertTitel("Inner Value");
       }
       this.mPricePanel.setLowLimitCheck(true);
       this.mPricePanel.setLowLimit(0);

       this.mDifferencePanel.setDataType( "Integer" );
       this.mDifferencePanel.setDrawForm( 1 );

       if ( Configurator.istAktienMarket() )
       {
          this.mDifferencePanel.setYTitel( "(Price - InnerValue)*100/InnerValue" );
       }
       else
       {
         this.mDifferencePanel.setYTitel( "Distortion" );
       }

       this.mDifferencePanel.setUseTrennwert( true );
       this.mDifferencePanel.setYTrennwert( 0 );

       this.mUmsatzPanel.setDataType( "Integer" );
       // setDrawForm( 0 ):  draw line, Default Form
       // setDrawForm( 1 ):  draw Bar
       this.mUmsatzPanel.setDrawForm( 1 );
       this.mUmsatzPanel.setYTitel("Volume");

       this.mAgentAnzahlPanel.setKurv1_Titel( "Trend Agents");
       this.mAgentAnzahlPanel.setKurv2_Titel( "Fundamental Agents");
       this.mAgentAnzahlPanel.setKurv3_Titel( "Retail Agents");
       this.mAgentAnzahlPanel.setLowLimit( 0 );
       this.mAgentAnzahlPanel.setDataMin ( 0 );

       this.mLogRenditenPanel.setDrawForm(0);
       this.mLogRenditenPanel.setDataType("Double");
       this.mLogRenditenPanel.setYTitel("Daily Log Yield");

    }
    catch (Exception ex)
    {
    }

    this.setSubTitel("");

  }

  public void ClearAllOldChartData()
  {
     Umsatz.clear();
     Price.clear();
     Difference.clear();
     Innerwert.clear();
     InvestorNumber.clear();
     NoiseTraderNumber.clear();
     Logrenditen.clear();
  }

  public void setShowInnererWertEnabled(boolean pShowInnererWertEnabled)
  {
     mShowInnererWertEnabled = pShowInnererWertEnabled;
  }

  public void setSubTitel(String pSubTitle)
  {
     this.mSubTitle = pSubTitle;
     this.setTitle( this.mMaintitel + ":" + this.mSubTitle );
  }

  /*
  private void addMenuBar()
  {
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu( "Save Frame Image" );
    menuBar.add( menu );
    menu.addMenuListener( new myMenuListener() );
    this.setJMenuBar( menuBar );
  }
  */

  public void resetData()
  {
    this.Logrenditen.clear();
    this.Price.clear();
    this.Innerwert.clear();
    this.Difference.clear();
    this.Umsatz.clear();
    this.InvestorNumber.clear();
    this.NoiseTraderNumber.clear();

    this.mPricePanel.setDataMinMax2Default();
    this.mAgentAnzahlPanel.setDataMinMax2Default();
    this.mAgentAnzahlPanel.setDataMin(0);

  }

  //New feature 2006-04-29
  public void ResetPanelCoordination()
  {

    this.mPricePanel.setDataMinMax2Default();
    // We can also reset other Panel like this.

  }

  public void saveFrame()
  {
    boolean visiblestate = this.isVisible();
    this.mPricePanel.setInnererWertEnabled(true);
    this.setVisible(true);
    this.toFront();
    //Rectangle oldbunds = this.getBounds();
    //Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    //this.setBounds(0,0, screensize.height-80, screensize.width );
    this.showChart();

    //SaveFrameThread sssh = new SaveFrameThread( this, filename);
    //sssh.start();

    // 3,5s second pause so that the picture is stabil
    HelpTool.pause(3500);

    try
    {
      String filename = Configurator.mConfData.getLogFileDirectory()+
                        Configurator.mConfData.mPfadSeperator+
                        Configurator.mConfData.mChartName;
      ScreenImage.createImage( this, filename );
      // 3 second pause
      HelpTool.pause(3000);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    //this.setVisible(visiblestate);
  }

  public void saveFrame2File(String pTargetFile)
  {
    try
    {
      String filename = Configurator.mConfData.getLogFileDirectory()+
                        Configurator.mConfData.mPfadSeperator+
                        Configurator.mConfData.mChartName;
      ScreenImage.createImage( this, pTargetFile );
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }

  }

  private void jbInit() throws Exception
  {

    boxLayout21.setAxis(1);
    this.getContentPane().setLayout(boxLayout21 );

    titledBorder1 = new TitledBorder("");

    //this.addMenuBar();

    this.getContentPane().add( this.mPricePanel, null);
    this.getContentPane().add( this.mDifferencePanel, null);
    this.getContentPane().add( this.mUmsatzPanel, null);
    this.getContentPane().add( this.mAgentAnzahlPanel, null);
    this.getContentPane().add( this.mLogRenditenPanel, null);
    this.setIconImage(  Toolkit.getDefaultToolkit().getImage("fasm.gif") );

  }

  public void setXScaleNumber(int j)
  {
       this.mXScalNumber = j;
       this.mPricePanel.setXScaleNumber( j );
       this.mDifferencePanel.setXScaleNumber(j);
       this.mUmsatzPanel.setXScaleNumber( j );
       this.mAgentAnzahlPanel.setXScaleNumber(j);
       this.mLogRenditenPanel.setXScaleNumber(j);

  }

  /*
  this methode is only used for debug a Anzeig-Problem
  2007-11-05,06,07
  */
  public void saveAgentAnzahlGraphicData(String pDataFile)
  {
       this.mAgentAnzahlPanel.SaveGraphicBaseData( pDataFile );
  }

  public int getXScaleNumber()
  {
     return this.mXScalNumber;
  }

  public void setUmsatzData(Vector  pVC)
  {
      this.Umsatz.removeAllElements();
      for (int i=0;i<pVC.size();i++)
      {
         Object DD = pVC.elementAt(i);
         this.Umsatz.add( DD );
      }
      this.mUmsatzPanel.setData( this.Umsatz );
      this.databeginindex = 0;
      this.mUmsatzPanel.setDataIndexBegin( databeginindex );
  }

  public void appendOneData(int pUmsatzData, int pPriceData, int pInnerwert, int pInvestor, int pNoiseTrader, int pBlankoAgent, double pLogrenditen)
  {
       // for Preis and Innerwert Kurv
       this.Price.add( new Integer (pPriceData)  );
       this.Innerwert.add( new Integer (pInnerwert)  );

       // for (Preis - Innerwert) Kurv
       // 2007-12-08
       // changed to Prozent = (pPriceData - pInnerwert) *100 / pInnerwert
       //
       // Old code: this.Difference.add( new Integer (pPriceData - pInnerwert )  );

       this.Difference.add( new Integer (  Math.round ( (pPriceData - pInnerwert ) * 100 / pInnerwert ) ) );

       // for Umsatz Kurv
       this.Umsatz.add( new Integer (pUmsatzData)  );

       // for Investor Number, NoiseTraderNumber BlankoAgent Number Kurv

       // call rountine using always double data type
       this.InvestorNumber.add   ( new Double ( pInvestor )  );
       this.NoiseTraderNumber.add( new Double ( pNoiseTrader)  );
       this.BlankoAgentNumber.add( new Double ( pBlankoAgent ) ) ;
       this.Logrenditen.add( new Double( pLogrenditen) );

       if ( this.Umsatz.size() > Configurator.mConfData.mHandelsday  )
       {
         this.Price.removeElementAt(0);
         this.Difference.removeElementAt(0);
         this.Umsatz.removeElementAt(0);
         this.Innerwert.removeElementAt(0);
         this.InvestorNumber.removeElementAt(0);
         this.NoiseTraderNumber.removeElementAt(0);
         this.BlankoAgentNumber.removeElementAt(0);
         this.Logrenditen.removeElementAt(0);
       }

       this.databeginindex = 0;

       this.mPricePanel.setData( this.Price, this.Innerwert, this.mShowInnererWertEnabled );
       this.mPricePanel.setDataIndexBegin( databeginindex );

       this.mDifferencePanel.setData( this.Difference );
       this.mPricePanel.setDataIndexBegin( databeginindex );

       this.mUmsatzPanel.setData( this.Umsatz );
       this.mUmsatzPanel.setDataIndexBegin( databeginindex );

       this.mAgentAnzahlPanel.setData(  this.NoiseTraderNumber, this.InvestorNumber, this.BlankoAgentNumber);
       this.mAgentAnzahlPanel.setDataIndexBegin( databeginindex );

       this.mLogRenditenPanel.setData( this.Logrenditen);
       this.mLogRenditenPanel.setDataIndexBegin( databeginindex );

       if ( this.isVisible() )
       {
          this.showChart();
       }
  }

  /**
   * This methode is used for regenerating of chart
   */
  public void SetChartDaten( Vector pPrice,
                             Vector pInnererWert,
                             Vector pUmsatz,
                             Vector pInvestorAnzahl,
                             Vector pNoiseTraderAnzahl,
                             Vector pBlankoAnzahl,
                             Vector pLogRenditen,
                             int pHandelsday
                             )
  {

    this.resetData();

    for ( int i=0; i<pPrice.size(); i++ )
    {
            // for Preis and Innerwert Kurv
            this.Price.add    ( pPrice.elementAt(i)  );
            this.Innerwert.add( pInnererWert.elementAt(i) ) ;

            int singleprice = ( (Integer) pPrice.elementAt(i) ).intValue();
            int singleinnererwert = ( (Integer) pInnererWert.elementAt(i) ).intValue();

            // for (Preis - Innerwert) Kurv
            this.Difference.add( new Integer ( singleprice - singleinnererwert )  );

            // for Umsatz Kurv
            this.Umsatz.add( pUmsatz.elementAt(i)) ;

            // for Investor Number, NoiseTraderNumber BlankoAgent Number Kurv
            // call rountine using always double data type

            int dd =  ( ( Integer ) pInvestorAnzahl.elementAt(i) ).intValue();
            this.InvestorNumber.add   ( new Double ( dd )  );

            dd =  ( ( Integer ) pNoiseTraderAnzahl.elementAt(i) ).intValue();
            this.NoiseTraderNumber.add( new Double ( dd )  );

            dd =  ( ( Integer ) pBlankoAnzahl.elementAt(i) ).intValue();
            this.BlankoAgentNumber.add( new Double ( dd ) ) ;
            this.Logrenditen.add(  pLogRenditen.elementAt(i) );

            if ( this.Price.size() > this.mXScalNumber )
            {
              System.out.println("removing 0. data");
              this.Price.removeElementAt(0);
              this.Difference.removeElementAt(0);
              this.Umsatz.removeElementAt(0);
              this.Innerwert.removeElementAt(0);
              this.InvestorNumber.removeElementAt(0);
              this.NoiseTraderNumber.removeElementAt(0);
              this.BlankoAgentNumber.removeElementAt(0);
              this.Logrenditen.removeElementAt(0);
            }
    }

    /*
    System.out.println("data status ");
    System.out.println(    this.Price.size() );
    System.out.println(    this.Difference.size() );
    System.out.println(    this.Umsatz.size() );
    System.out.println(    this.Innerwert.size() );
    System.out.println(    this.InvestorNumber.size() );
    System.out.println(    this.NoiseTraderNumber.size() );
    System.out.println(    this.BlankoAgentNumber.size() );
    System.out.println(    this.Logrenditen.size() );
    */

    this.databeginindex = 0;

    this.mPricePanel.setData( this.Price, this.Innerwert, true );
    this.mPricePanel.setDataIndexBegin( databeginindex );

    this.mDifferencePanel.setData( this.Difference );
    this.mDifferencePanel.setDataIndexBegin( databeginindex );

    this.mUmsatzPanel.setData( this.Umsatz );
    this.mUmsatzPanel.setDataIndexBegin( databeginindex );

    this.mAgentAnzahlPanel.setData(  this.NoiseTraderNumber, this.InvestorNumber, this.BlankoAgentNumber);
    this.mAgentAnzahlPanel.setDataIndexBegin( databeginindex );

    this.mLogRenditenPanel.setData( this.Logrenditen);
    this.mLogRenditenPanel.setDataIndexBegin( databeginindex );

    this.showChart();

  }

  public void showChart()
  {
     this.mPricePanel.showChart();
     this.mUmsatzPanel.showChart();
     this.mDifferencePanel.showChart();
     this.mAgentAnzahlPanel.showChart();
     this.mLogRenditenPanel.showChart();
  }

  public static void main(String args[])
  {
      // Standalone Testdata
      HistoryFrame ff = new HistoryFrame();
      ff.setSize(500,600);
      ff.setVisible(true);
      ff.setXScaleNumber(50);
      ff.appendOneData(12310,100,90,10,10,10,1.0);
      ff.appendOneData(14300,100,92,10,10,22,1.2);
      ff.appendOneData(13430,140,94,10,10,22,1.3);
      ff.appendOneData(15300,110,93,10,10,22,1.2);
      ff.appendOneData(14220,100,92,10,10,22,1.0);
      ff.appendOneData(12300,100,94,10,10,22,1.7);
      ff.appendOneData(14023,105,93,10,10,22,1.9);
      ff.appendOneData(13922,120,91,10,10,22,1.7);
  }

  /*
  class myMenuListener implements MenuListener
  {

    public myMenuListener()
    {

    }

    public void menuSelected(MenuEvent e)
    {
       saveFrame();
    }

    public void menuDeselected(MenuEvent e)
    {


    }

    public void menuCanceled(MenuEvent e)
    {

    }
 }
  */
}

class SaveFrameThread extends Thread
{
  String mFilename;
  JFrame  mFrame;
  SaveFrameThread ( JFrame pFrame, String pFilename )
  {
    this.mFilename = pFilename;
    this.mFrame    = pFrame;
  }

  public void run()
  {
        try
        {
           ScreenImage.createImage( this.mFrame, this.mFilename );
        }
        catch (Exception ex)
        {
          ex.printStackTrace();
        }
  }
}
