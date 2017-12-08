package de.marketsim.gui;

import javax.swing.JPanel;
import java.util.*;
import java.text.*;
import de.marketsim.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Organisation: </p>
 * @author unbekannt
 * @version 1.0
 *
 *  This is a common program for showing a panel with 3 charts
 */

public class BasePanel3Chart extends JPanel {

  private BorderLayout borderLayout1 = new BorderLayout();

  private int DataIndexBegin = 0; // Default
  private Vector vc1  = null;
  private Vector vc2  = null;
  private Vector vc3  = null;
  private int mLowLimit  = -100;  // Default, alle Value which are smaller than it, will be chaneged to it.

  private double kmax_default = -30000000;
  private double kmin_default =  30000000;

  private double kmax = kmax_default ;  //Init to Default, this value will be updated by the methode UpdataMaxMin(()
  private double kmin = kmin_default;  //Init to Default, this value will be updated by the methode UpdataMaxMin(()

  private Coordinator mCoordinator = new Coordinator();

  private int XScaleNumber  = 300;
  private int XScaleFactor  = 20;
  private int YScaleNumber  = 5;

  /*
  private String mTrendAgentTitel        = "Trend Agents";
  private String mFundamentalAgentTitel  = "Fundamental Agents";
  private String mBlankoAgentTitel       = "Blanko Agents";
  */

  private String mKurv1_Titel     = "Trend Agents";
  private String mKurv2_Titel     = "Fundamental Agents";
  private String mKurv3_Titel     = "Retail Agents";

  // default Color
  private java.awt.Color mCoordinatorColor = java.awt.Color.black ;

  private java.awt.Color mKurv1_DrawingColor = java.awt.Color.red ;
  private java.awt.Color mKurv2_DrawingColor = java.awt.Color.blue ;
  private java.awt.Color mKurv3_DrawingColor = java.awt.Color.orange ;

  private  DataFormatter dataformat = new DataFormatter("Germany"  );

  private  int mYAxialFractionDigits = 0; // Default 0;

  private  boolean MaxMinUpdatingRequired = true;

  public BasePanel3Chart()
  {
      dataformat.setMaxFractionDigits(mYAxialFractionDigits);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void setFractionDigitsForYAxialValue(int pFractionDigits)
  {
      this.mYAxialFractionDigits = pFractionDigits;
      dataformat.setMaxFractionDigits(mYAxialFractionDigits);
  }

  public void setDataLanguage(String pLanguageName )
  {
     dataformat = new DataFormatter( pLanguageName );
     dataformat.setMaxFractionDigits(mYAxialFractionDigits);
  }

  public void setFractionDigits(int pMaxDigits)
  {
       dataformat.setMaxFractionDigits( pMaxDigits );
  }

  public void setCoordinatorColor( java.awt.Color  pColor )
  {
    this.mCoordinatorColor = pColor;
  }

  public void setKurv1_Titel(String pTitel )
  {
     this.mKurv1_Titel = pTitel;
  }

  public void setKurv2_Titel(String pTitel )
  {
     this.mKurv2_Titel = pTitel;
  }

  public void setKurv3_Titel(String pTitel )
  {
     this.mKurv3_Titel = pTitel;
  }

  public void setKurv3_tDrawingColor( java.awt.Color  pColor )
  {
    this.mKurv3_DrawingColor = pColor;
  }

  public void setKurv1_DrawingColor( java.awt.Color  pColor )
  {
    this.mKurv1_DrawingColor = pColor;
  }

  public void setKurv2_DrawingColor( java.awt.Color  pColor )
  {
    this.mKurv2_DrawingColor = pColor;
  }

  public void setKurv3_DrawingColor( java.awt.Color  pColor )
  {
    this.mKurv3_DrawingColor = pColor;
  }

  public void SaveGraphicBaseData(String pDataFile)
  {
    try
    {
       java.io.PrintWriter ps = new java.io.PrintWriter ( new java.io.FileWriter ( pDataFile ));
       int CC = this.vc1.size();
       ps.println("Data1;Data2;Data3;");
       for ( int i=0; i < CC; i++)
       {
            Double dd1 = (Double) this.vc1.elementAt(i);
            Double dd2 = (Double) this.vc1.elementAt(i);
            Double dd3 = (Double) this.vc1.elementAt(i);
            ps.print( dd1.doubleValue() + ";" + dd2.doubleValue()+";" + dd3.doubleValue()+";" );
       }
       ps.close();
    }
    catch (Exception ex)
    {
    }
  }

  public int getDataIndexBegin()
  {
    return this.DataIndexBegin;
  }

  public void setDataIndexBegin(int pIndex)
  {
    this.DataIndexBegin = pIndex;
  }

  public void setMaxMinUpdatingRequired(boolean pRequired)
  {
     this.MaxMinUpdatingRequired = pRequired;
  }

  public void setDataMax(double pMax)
  {
     this.kmax = pMax;
  }

  public void setDataMin(double pMin)
  {
     this.kmin = pMin;
  }

  public void setDataMinMax2Default()
  {
       kmax       = kmax_default;
       kmin       = kmin_default;
  }

  public void setYScaleNumber(int j)
  {
       YScaleNumber = j;
  }

  public void setXScaleNumber(int j)
  {
       XScaleNumber = j;
       int k = XScaleNumber/XScaleFactor;
       if ( k > 20 )
       {
          XScaleFactor = XScaleNumber / 20;
       }
  }

  public void setXScaleFactor(int j)
  {
       XScaleFactor = j;
  }

  public void setLowLimit(int j)
  {
       mLowLimit = j;
  }


  public String getDataTypeName()
  {
        return "Double";
  }

  public String getDataType2Name()
  {
        return "Double";
  }

  public void UpdateMaxMin()
  {

      //System.out.println("call updateMaxMin");

      if (  ! this.MaxMinUpdatingRequired )
      {
          return;
      }

      for (int i=0; i<vc1.size();i++)
      {
         Double pp = (Double) vc1.elementAt(i);
         kmax = Math.max( kmax,  pp.doubleValue() );
         kmin = Math.min( kmin,  pp.doubleValue() );

         pp = (Double) vc2.elementAt(i);
         kmax = Math.max( kmax,  pp.doubleValue() );
         kmin = Math.min( kmin,  pp.doubleValue() );

         pp = (Double) vc3.elementAt(i);
         kmax = Math.max( kmax,  pp.doubleValue() );
         kmin = Math.min( kmin,  pp.doubleValue() );
      }
  }

  /***
   *  Achtung !!!
   *  pKurv1_Data,pKurv2_Data,pKurv3_Data kann maximal nur
   *  300 Daten enthalten.
   */

  public void setData(Vector pKurv1_Data, Vector pKurv2_Data,Vector pKurv3_Data )
  {
     this.vc1  = pKurv1_Data;
     this.vc2  = pKurv2_Data;
     this.vc3  = pKurv3_Data;

     /** 2007-11-07, Daten sind ok
      * Anzeig Problem scheint in der Draw-Part

     try
     {
       java.io.PrintWriter  fos = new java.io.PrintWriter(  new java.io.FileWriter("agentanzahltrace.txt", true) );
       Double d1 = (Double) this.vc1.lastElement();
       Double d2 = (Double) this.vc2.lastElement();
       Double d3 = (Double) this.vc3.lastElement();
       fos.println (  this.vc1.size() + "; " + d1.doubleValue() + ";" + d2.doubleValue() + ";" + d3.doubleValue()  );
       fos.close();

     }
     catch (Exception ex)
     {


     }

     */

  }

  public void paintComponent( Graphics g )
  {
      updateScreen( g );
  }

  public void showChart()
  {
      this.UpdateMaxMin();
      this.repaint();
  }

  // draw the coordinate
  private void drawCoordination(Graphics g)
  {
      //this.XScaleNumber= this.setXScaleNumber();
      // draw x-axial
      // Graphic physical coordination
      Graphics drawplatte = g;

      // clear old drawing area
      drawplatte.clearRect( 1,1,  this.getWidth()-2, this.getHeight()-2 );

      this.mCoordinator.y0 = this.getHeight()-this.mCoordinator.bottommargin;
      this.mCoordinator.xmax = this.getWidth() - this.mCoordinator.rightmargin;

      drawplatte.setColor( this.mCoordinatorColor );

      drawplatte.setFont(Font.getFont("BOLD"));

      drawplatte.drawLine(this.mCoordinator.x0,
                          this.mCoordinator.y0,
                          this.mCoordinator.xmax+this.mCoordinator.arrowlength,
                          this.mCoordinator.y0);

        drawplatte.drawString("x",
                              this.mCoordinator.xmax+this.mCoordinator.arrowlength,
                              this.mCoordinator.y0);

        drawplatte.drawLine(this.mCoordinator.xmax+this.mCoordinator.arrowlength,
                            this.mCoordinator.y0,
                            this.mCoordinator.xmax,
                            this.mCoordinator.y0-5);
        drawplatte.drawLine(this.mCoordinator.xmax+this.mCoordinator.arrowlength,
                            this.mCoordinator.y0,
                            this.mCoordinator.xmax,
                            this.mCoordinator.y0+5);

        double pixelperscale = ( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber;

        for (int m=0;m<=XScaleNumber;m++)
        {
          int pointx = (int) (this.mCoordinator.x0 + m*( this.mCoordinator.xmax - this.mCoordinator.x0 ) / XScaleNumber);
          if ( m% XScaleFactor == 0)
          {
            drawplatte.drawLine(  pointx, this.mCoordinator.y0, pointx, this.mCoordinator.y0+10);
            drawplatte.drawString(  ""+ ( m + this.DataIndexBegin ), pointx-8, this.mCoordinator.y0+25 );
          }
          else
          {
            drawplatte.drawLine( pointx, this.mCoordinator.y0,pointx, this.mCoordinator.y0+3);
          }
        }

        // draw y

        drawplatte.drawLine( this.mCoordinator.x0,
                             this.mCoordinator.y0,
                             this.mCoordinator.x0,
                             this.mCoordinator.ymax-this.mCoordinator.arrowlength );

        // display title on the top
        drawplatte.setColor( this.mKurv1_DrawingColor );
        drawplatte.drawString( this.mKurv1_Titel, this.mCoordinator.x0-10, this.mCoordinator.ymax-15);

        drawplatte.setColor( this.mKurv2_DrawingColor );
        drawplatte.drawString( this.mKurv2_Titel, this.mCoordinator.x0 + this.mKurv1_Titel.length()*8, this.mCoordinator.ymax-15);

        drawplatte.setColor( this.mKurv3_DrawingColor );
        drawplatte.drawString( this.mKurv3_Titel, this.mCoordinator.x0+this.mCoordinator.x0+this.mKurv1_Titel.length()*8 + this.mKurv2_Titel.length()*8, this.mCoordinator.ymax-15);
        drawplatte.setColor( this.mCoordinatorColor );

        drawplatte.drawLine(this.mCoordinator.x0,
                            this.mCoordinator.ymax-this.mCoordinator.arrowlength,
                            this.mCoordinator.x0-5,
                            this.mCoordinator.ymax );
        drawplatte.drawLine(this.mCoordinator.x0,
                            this.mCoordinator.ymax-this.mCoordinator.arrowlength,
                            this.mCoordinator.x0+5,
                            this.mCoordinator.ymax );

        // draw skalar for the y
        double datams;

        datams= (this.mCoordinator.y0-this.mCoordinator.ymax) / (kmax-kmin) ;

        for(int m=0; m<=YScaleNumber; m++)
        {
            int pointy = this.mCoordinator.y0-m*(this.mCoordinator.y0-this.mCoordinator.ymax)/YScaleNumber;
            drawplatte.drawLine(this.mCoordinator.x0,
                                pointy,
                                this.mCoordinator.x0-5,
                                pointy);
            String tt = "" + this.dataformat.format2str( kmin + m * ( kmax - kmin) / YScaleNumber ) ;

            int neededwidth = drawplatte.getFontMetrics().stringWidth(tt) + 4;
            Font fn = Font.getFont( "BOLD", new Font("BOLD", Font.TRUETYPE_FONT, 10) );
            drawplatte.setFont( fn );
            drawplatte.drawString( tt,this.mCoordinator.x0-neededwidth, pointy);
        }
  }

  private void my_updateScreen(Graphics g)
  {
        int n = vc1.size();

        double Line1Data[] = new double[n];
        double Line2Data[] = new double[n];
        double Line3Data[] = new double[n];

        for (int m=0; m<n; m++ )
        {
             Line1Data[m]= ( (Double) vc1.elementAt(m)).doubleValue();
        }

        for (int m=0; m<n; m++ )
        {
             Line2Data[m]= ( (Double) vc2.elementAt(m)).doubleValue();
        }

        for (int m=0; m<n; m++ )
        {
             Line3Data[m]= ( (Double) vc3.elementAt(m)).doubleValue();
        }

        Graphics drawplatte = g;

        // draw 1. Line
        int xx1 = this.mCoordinator.x0 + (int) ( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber;
        int yy1 = this.mCoordinator.y0 - (int) ( (Line1Data[0]-kmin)  * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kmax-kmin) ) ;
        int xx2,yy2;
        drawplatte.setColor( this.mKurv1_DrawingColor );

        for (int m=1; m<n ;m++ )
        {
           xx2 = (int) (this.mCoordinator.x0 + (m+1)*( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber);
           yy2 = this.mCoordinator.y0 - (int) ( (Line1Data[m]-kmin) * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kmax-kmin) );
           drawplatte.drawLine(xx1,yy1,xx2,yy2);
           xx1 = xx2;
           yy1 = yy2;
        }

        // draw 2. Line
        xx1 = this.mCoordinator.x0 + (int)( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber;
        yy1 = this.mCoordinator.y0 - (int) ( (Line2Data[0]-kmin)  * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kmax-kmin) ) ;
        drawplatte.setColor( this.mKurv2_DrawingColor );

        for (int m=1; m<n ;m++ )
        {
           xx2 = (int) (this.mCoordinator.x0 + (m+1)*( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber);
           yy2 = this.mCoordinator.y0 - (int) ( (Line2Data[m]-kmin) * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kmax-kmin) );
           drawplatte.drawLine(xx1,yy1,xx2,yy2);
           xx1 = xx2;
           yy1 = yy2;
        }

         // draw 3. Line
         xx1 = this.mCoordinator.x0 + (int)( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber;
         yy1 = this.mCoordinator.y0 - (int) ( (Line3Data[0]-kmin)  * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kmax-kmin) ) ;
         drawplatte.setColor( this.mKurv3_DrawingColor );

         for (int m=1; m<n ;m++ )
         {
            xx2 = (int) (this.mCoordinator.x0 + (m+1)*( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber);
            yy2 = this.mCoordinator.y0 - (int) ( (Line3Data[m]-kmin) * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kmax-kmin) );
            drawplatte.drawLine(xx1,yy1,xx2,yy2);
            xx1 = xx2;
            yy1 = yy2;
         }
  }

  private void updateScreen(Graphics g)
  {
          if ( vc1 == null )
          {
            return;
          }
          Graphics drawplatte = g;

          // clear old drawing area
          drawplatte.clearRect( 1,1,  this.getWidth()-2, this.getHeight()-2 );
          this.drawCoordination(drawplatte);
          this.my_updateScreen( g );
  }

  private void jbInit() throws Exception
  {
      this.setLayout(null);
  }

  /**
   * self test
   * @param args
   */

  public static void main(String args[])
  {
     System.out.println("Usage:" );
     System.out.println("java -cp fasm.jar de.marketsim.gui.BasePanel3Chart <datafile> " );
     System.out.println( );
     if ( args.length == 0 )
     {
        return ;
     }

     int datalines = 0;

     JFrame  ff = new JFrame();
     ff.setSize(300,300);
     ff.setVisible(true);

     BasePanel3Chart chart = new BasePanel3Chart();
     chart.setDataMax(100);
     chart.setDataMin(0);
     chart.setXScaleNumber(200);

     ff.getContentPane().add( chart , null);

     Vector v1 = new Vector();
     Vector v2 = new Vector();
     Vector v3 = new Vector();

    try
    {
        java.io.BufferedReader  fins = new java.io.BufferedReader( new java.io.FileReader (  args[0] ) );

        // jump 1. line
        String ss = fins.readLine();

        ss = fins.readLine();
        while ( ss!=null )
        {
            int p1 = ss.indexOf(";");
            int p2 = ss.indexOf(";", p1+1);
            int p3 = ss.indexOf(";", p2+1);
            int p4 = ss.indexOf(";", p3+1);

            double n1 = Double.parseDouble(  ss.substring( p1 +1, p2) );
            double n2 = Double.parseDouble(  ss.substring( p2 +1, p3) );
            double n3 = Double.parseDouble(  ss.substring( p3 +1, p4 ) );

            System.out.println( "n1=" + n1 + " n2=" + n2 + " n3=" + n3 );

            v1.add( new Double (n1) );
            v2.add( new Double (n2) );
            v3.add( new Double (n3) );

            ss = fins.readLine();
            System.out.println( "ss=" + ss );
            datalines = datalines + 1;
        }


    }
    catch (Exception ex)
    {
        ex.printStackTrace();

    }

    chart.setXScaleNumber( datalines );

    chart.setData( v1, v2, v3  );
    chart.repaint();


  try
  {
  System.out.println("Press Enter to exit.");
  java.io.BufferedReader  jj = new java.io.BufferedReader( new java.io.InputStreamReader ( System.in ) );
  jj.readLine();

  System.exit(0);

  }
  catch (Exception ex)
  {

  }


  }



}


