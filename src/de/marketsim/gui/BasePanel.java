/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author
 * @version 1.0
 */

package de.marketsim.gui;

import javax.swing.JPanel;
import java.util.*;
import java.awt.Graphics;
import java.awt.Font;
import java.text.*;
import de.marketsim.util.*;

public class BasePanel extends JPanel
{

  public static int DrawLine   = 0;
  public static int DrawBar    = 1;
  public static int DrawPoint  = 2;

  private int DataIndexBegin = 0;           // Default
  private Vector vc = null;

  private int kmax_init  = -3000000;            // Default, this value will be updated by the methode UpdataMaxMin(()
  private int kmin_init  =  3000000;            // Default, this value will be updated by the methode UpdataMaxMin(()

  private double kdoublemax_init = -300000.0 ;  //this value will be updated by the methode UpdataMaxMin(()
  private double kdoublemin_init =  300000.0 ;  //this value will be updated by the methode UpdataMaxMin(()

  private double kdoublemax = kdoublemax_init ;  //this value will be updated by the methode UpdataMaxMin(()
  private double kdoublemin = kdoublemin_init ;  //this value will be updated by the methode UpdataMaxMin(()

  private int kmax  = kmax_init;            // Default, this value will be updated by the methode UpdataMaxMin(()
  private int kmin  = kmin_init ;            // Default, this value will be updated by the methode UpdataMaxMin(()
  private Coordinator mCoordinator = new Coordinator();
  private int XScaleNumber  = 300;
  private int XScaleFactor  = 20;
  private int YScaleNumber  = 5;

  // Parameter für Drawing Bar
  private int YTrennwert    = 0; // Default
  private java.awt.Color mObenTrennwert_Color = java.awt.Color.blue ;
  private java.awt.Color mUnterTrennwert_Color = java.awt.Color.red ;
  private boolean mUseTrennwert = false;

  private String mYTitel = "Y"; // Default

  // Parameter für Drawing line
  // default Color
  private java.awt.Color mCoordinatorColor = java.awt.Color.black ;
  private java.awt.Color mLineDrawingColor = java.awt.Color.blue ;

  // Default Form
  private int mForm = DrawLine;

  private boolean DebugEnabled = false;
  private boolean mDrawTrennLine = false;
  private  DataFormatter decformat = new DataFormatter("Germany");

  private  boolean MaxMinUpdatingRequired = true;

  private int DataType = -1;
   // -1:  Type not defined
   // 0: Integer,
   // 1: Double

  public BasePanel()
  {

  }

  public void setDebug(boolean pEnabled)
  {
      this.DebugEnabled = pEnabled;
  }

  public void setLeftMargin(int pLeftMargin)
  {
      // ACtung:
      // default: 50
      this.mCoordinator.setLeftmargin( pLeftMargin );
  }

  public void setYTitel(String pYTitel )
  {
     this.mYTitel = pYTitel;
  }

  public void setUseTrennwert(boolean pUse )
  {
     this.mUseTrennwert = pUse;
  }

  public void setYTrennwert(int pTrennwert )
  {
     this.YTrennwert = pTrennwert;
  }

  public void setDrawTrennLine(boolean pDrawed )
  {
     this.mDrawTrennLine = pDrawed;
  }

  //pForm=0 draw Line,  pForm=1 draw Bar
  public void setDrawForm(int pForm )
  {
     this.mForm = pForm;
  }

  public void setCoordinatorColor( java.awt.Color  pColor )
  {
     this.mCoordinatorColor = pColor;
  }

  public void setLineDrawingColor( java.awt.Color  pColor )
  {
     this.mLineDrawingColor = pColor;
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

  public void setDataMax(int pMax)
  {
     this.kmax_init = pMax;
     this.kmax      = pMax;
     this.kdoublemax= pMax;
  }

  public void setDataMax(double pMax)
  {
     this.kdoublemax_init = pMax;
     this.kdoublemax      = pMax;
  }

  public void setDataMin(int pMin)
  {
     this.kmin_init = pMin;
     this.kmin      = pMin;
     this.kdoublemin = pMin;
  }

  public void setDataMin(double pMin)
  {
     this.kdoublemin_init = pMin;
     this.kdoublemin      = pMin;

  }

  public void setYScaleNumber(int j)
  {
       if ( j==0)
       {
         YScaleNumber = j;
       }
       else
       {
          j = 2;
       }
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

  public void setDataType(int k) throws Exception
  {
     if ( ( k == 0 ) || ( k==1 ) )
     {
        this.DataType = k;
     }
     else
     {
       throw new Exception("Invalid Datatype definition (0,1)!");
     }
  }

  public void setDataType(String TypeName) throws Exception
  {
     if ( TypeName.equalsIgnoreCase("Integer") )
     {
        this.DataType = 0;
     }
     else
     if ( TypeName.equalsIgnoreCase("Double") )
     {
        this.DataType = 1;
     }
     else
     {
       throw new Exception("Invalid Datatype definition (Integer,Double)!");
     }
  }

  public String getDataTypeName()
  {
     if ( this.DataType == 0 )
     {
        return "Integer";
     }
     else
     {
        return "Double";
     }
  }

  public int getDataType()
  {
     return this.DataType;
  }

  private void UpdateMaxMin()
  {
      if ( this.DataType == -1 )
      {
         //  throw new Exception("Datatype is not defined.");
         System.out.println ( " !!!!!!!!!!!!!!!  Datatype is not defined.");
         return;
      }

      if (  ! this.MaxMinUpdatingRequired )
      {
          return;
      }

      // Integer
      if ( this.DataType == 0 )
      {
              kmax  = Integer.MIN_VALUE;
              kmin  = Integer.MAX_VALUE;
              for (int i=0; i<vc.size();i++)
              {
                 Integer pp = (Integer) vc.elementAt(i);
                 kmax = Math.max( kmax,  pp.intValue() );
                 kmin = Math.min( kmin,  pp.intValue() );
                 //System.out.println( i  +" value=" + pp.intValue() );

              }

              if ( kmax == kmin )
              {
                if ( kmax == 0 )
                {
                  kmax = 100;
                }
                else
                if ( kmax > 0 )
                {
                   kmax = kmax * 2;
                }
                else
                {
                   kmax = kmax * (-2);
                }
              }
              //System.out.println("max=" +kmax + " min=" + kmin );

              // Strenge Anpassung von kmax für Bar-Drawing
              if ( this.mForm == 1  )  // draw bar
              {
                if ( this.mUseTrennwert )
                {
                   if ( kmax < this.YTrennwert )
                   {
                     kmax = this.YTrennwert;
                   }
                }
              }
              // YScaleNumber Anpassung
              if ( kmax - kmin < 10 )
              {
                this.YScaleNumber = kmax - kmin;
              }
      }
      else
      {
              // Double
              kdoublemax = Double.MIN_VALUE ;
              kdoublemin = Double.MAX_VALUE ;
              for (int i=0; i<vc.size();i++)
              {
                 Double pp = (Double) vc.elementAt(i);
                 kdoublemax = Math.max( kdoublemax,  pp.doubleValue() );
                 kdoublemin = Math.min (kdoublemin, pp.doubleValue() );
                 //System.out.println( i  +" value=" + pp.doubleValue() );

              }
              if ( kdoublemax == kdoublemin )
              {
                  kdoublemax =  kdoublemax * 1.2;
                  if ( kdoublemax == 0.0 )
                  {
                     kdoublemax = 100.0;
                  }
              }

              //System.out.println("max=" +kdoublemax + " min=" + kdoublemin );

      }


  }

  public void setData(Vector pData)
  {
     this.vc = pData;
  }

  public void paintComponent( Graphics g )
  {
      //System.out.println("paintComponent( Graphics g ) is called.");
      updateScreen( g );
  }

  public void showChart()
  {
      this.UpdateMaxMin();
      this.repaint();
  }

  // draw the coordination
  private void drawCoordination(Graphics g)
  {
      // draw x-axial
      // Graphic physical coordination
      Graphics drawplatte = g;

      // clear old drawing area
      drawplatte.clearRect(0,0,  this.getWidth(), this.getHeight()*2 );

      this.mCoordinator.y0   = this.getHeight()-this.mCoordinator.bottommargin;
      this.mCoordinator.xmax = this.getWidth() - this.mCoordinator.rightmargin;

      //drawplatte.setColor(drawplatte.getColor().red );

      drawplatte.setColor( this.mCoordinatorColor );

      //drawplatte.setFont(Font.getFont("BOLD"));

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

        Font fn = Font.getFont( "BOLD", new Font("BOLD", Font.PLAIN, 10) );
        drawplatte.setFont( fn );

        for (int m=0;m<=XScaleNumber;m++)
        {
          int pointx = (int) (this.mCoordinator.x0 + m*( this.mCoordinator.xmax - this.mCoordinator.x0 ) * 1.0 / XScaleNumber);
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

        //drawplatte.drawString("y", this.mCoordinator.x0-10, this.mCoordinator.ymax);
        drawplatte.drawString( this.mYTitel, this.mCoordinator.x0-10, this.mCoordinator.ymax -15 );

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
        // Integer Data
        if ( this.DataType == 0 )
        {
           //System.out.print(this.mYTitel +" kMAX=" + kmax + " KMIN="+kmin);
           datams= (this.mCoordinator.y0-this.mCoordinator.ymax) / ( this.kmax-this.kmin) ;
        }
        else
        {
           datams= (this.mCoordinator.y0-this.mCoordinator.ymax) / (this.kdoublemax-this.kdoublemin) ;
        }

        for(int m=0; m<=YScaleNumber; m++)
        {
            int pointy = this.mCoordinator.y0-m*(this.mCoordinator.y0-this.mCoordinator.ymax)/YScaleNumber ;
            drawplatte.drawLine(this.mCoordinator.x0,
                                pointy,
                                this.mCoordinator.x0-5,
                                pointy);
            // Integer
            String tt ="";
            if ( this.DataType == 0 )
            {
              tt = ""+( kmin + m* ( kmax - kmin) / YScaleNumber);
            }
            else
            // double
            {
              double yscalewert = kdoublemin + m * ( kdoublemax - kdoublemin) / YScaleNumber;
              yscalewert = (int) (yscalewert *100.0);
              yscalewert = yscalewert /100.0;
              tt = "" + this.decformat.format2str( yscalewert ) ;
            }
            int neededwidth = drawplatte.getFontMetrics().stringWidth(tt) + 4;
            fn = Font.getFont( "BOLD", new Font("BOLD", Font.TRUETYPE_FONT, 10) );

            drawplatte.setFont( fn );

            drawplatte.drawString( tt,this.mCoordinator.x0-neededwidth,pointy+2);
        }

  }

  private void updateScreenUsingInteger(Graphics g)
  {
        if ( this.DebugEnabled )
        {
           System.out.println("KMAX=" + kmax + ", KMIN="  + kmin);
        }

        if ( this.vc == null)
        {
          return;
        }

        if ( this.vc.size() == 0)
        {
          return;
        }

        int n = vc.size();
        int k[] = new int[n];
        for (int m=0; m<n; m++ )
        {
          k[m]= ( (Integer) vc.elementAt(m)).intValue();
        }

        Graphics drawplatte = g;

        // draw Line or Bar using user data

        drawplatte.setColor( this.mLineDrawingColor );

        if ( this.mForm == this.DrawLine )
        {
              int xx1 = this.mCoordinator.x0 + (int) ( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber ;
              int yy1 = this.mCoordinator.y0 -  (k[0]-kmin)  * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kmax-kmin) ;
              int xx2,yy2;

              for (int m=1; m<n ;m++ )
              {
                 xx2 = (int) (this.mCoordinator.x0 + (m + 1)*( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber);
                 yy2 = this.mCoordinator.y0 - (k[m]-kmin) * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kmax-kmin) ;
                 drawplatte.drawLine(xx1,yy1,xx2,yy2);
                 xx1 = xx2;
                 yy1 = yy2;
              }

              if ( this.mDrawTrennLine )
              {
                drawplatte.setColor( java.awt.Color.black );
                int yy_of_trennlinie = this.mCoordinator.y0 - (  this.YTrennwert - kmin) * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kmax-kmin);
                xx1 = this.mCoordinator.x0 ;
                yy1 = yy_of_trennlinie;
                drawplatte.drawLine( xx1, yy1,  this.mCoordinator.xmax, yy1);
              }
        }
        else
        if ( this.mForm == this.DrawBar )
        {
            // Draw Bar without trennline: Umsatz
            if ( ! this.mUseTrennwert )
            // But do not draw Trennlinie
            {
                  // set the color of bar
                  drawplatte.setColor( this.mObenTrennwert_Color );
                  // let the 1.Bar to move to right with 1 pixel
                  int movedx0 = this.mCoordinator.x0 + 1;
                  int xx1 = movedx0;
                  int yy1 = this.mCoordinator.y0;
                  int xx2;
                  int yy2;
                  for (int m=0; m<n ;m++ )
                  {
                     xx2 = movedx0 + (int) ( (m+1)*( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber);
                     if ( k[m] == kmin )
                     {
                       drawplatte.drawLine(xx1,yy1,xx2,yy1);
                     }
                     else
                     {
                         yy2 = this.mCoordinator.y0 - (k[m]-kmin) * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kmax-kmin);
                         drawplatte.fillRect(xx1,yy2,(xx2-xx1), (yy1-yy2)  );
                     }
                     xx1 = xx2;
                  }
            }
            else
            {
              // Draw Bar and TrennLinie: Preis-Innerwert
             drawplatte.setColor( java.awt.Color.black );
             int yy_of_trennlinie = this.mCoordinator.y0 - (  this.YTrennwert - kmin) * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kmax-kmin);
             int xx1 = this.mCoordinator.x0 ;
             int yy1 = yy_of_trennlinie;
             drawplatte.drawLine( xx1, yy1,  this.mCoordinator.xmax, yy1);

             int xx2;
             int yy2;

             for (int m=0; m<n; m++ )
             {
                if ( k[ m ] > this.YTrennwert )
                {
                    drawplatte.setColor( this.mObenTrennwert_Color );
                    xx2 = this.mCoordinator.x0 + (int) ( (m + 1) *( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber);
                    yy2 = this.mCoordinator.y0 - ( k[m]-kmin) * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kmax-kmin);
                    drawplatte.fillRect(xx1, yy2,  (xx2-xx1),  Math.abs ( yy_of_trennlinie - yy2 )  );
                }
                else
                {
                    drawplatte.setColor( this.mUnterTrennwert_Color );
                    xx2 = this.mCoordinator.x0 + (int) ( (m+1)*( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber);
                    yy2 = this.mCoordinator.y0 - (k[m]-kmin) * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kmax-kmin);
                    drawplatte.fillRect(xx1, yy_of_trennlinie, (xx2-xx1), Math.abs( yy2 - yy_of_trennlinie ) );
                }
                xx1 = xx2;
             }
            }
        }
        else
        if ( this.mForm == this.DrawPoint )
        {
          for (int m=0; m<n ;m++ )
          {
            int xx1 = (int) (this.mCoordinator.x0 + (m + 1)*( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber);
            int yy1 = this.mCoordinator.y0 - (int) ( (k[m]-kmin) * (this.mCoordinator.y0-this.mCoordinator.ymax) * 1.0 / (kmax-kmin) ) ;
            drawplatte.fillOval(xx1,yy1,4,4);
            //drawplatte.drawOval(xx1,yy1,3,3);
          }
        }

  }

  private void updateScreenUsingDouble(Graphics g)
  {
        int n = vc.size();
        double k[] = new double[n];
        for (int m=0; m<n; m++ )
        {
          k[m]= ( (Double) vc.elementAt(m)).doubleValue();
        }

        Graphics drawplatte = g;
        // set the color of line
        drawplatte.setColor( this.mLineDrawingColor );

    // draw data
    if ( this.mForm == this.DrawLine )
    {
        int xx1 = this.mCoordinator.x0 + (int) ( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber ;
        int yy1 = this.mCoordinator.y0 - (int) ( (k[0]-kdoublemin)  * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kdoublemax-kdoublemin) ) ;
        int xx2,yy2;

        for (int m=1; m<n ;m++ )
        {
           xx2 = (int) (this.mCoordinator.x0 + (m+1)*( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber);
           yy2 = this.mCoordinator.y0 - (int) ( (k[m]-kdoublemin) * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kdoublemax-kdoublemin) );
           drawplatte.drawLine(xx1,yy1,xx2,yy2);
           xx1 = xx2;
           yy1 = yy2;
        }
    }
    else
    if ( this.mForm == this.DrawPoint )
    {
      for (int m=0; m<n ;m++ )
      {
        int xx1 = (int) ( this.mCoordinator.x0 + (m + 1.0)*( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber );
        int yy1 = this.mCoordinator.y0 - (int) ( (k[m]-kdoublemin) * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kdoublemax-kdoublemin) );
        //drawplatte.drawOval(xx1,yy1,3,3);
        drawplatte.fillOval(xx1,yy1,4,4);
      }
    }
  }

  private void updateScreen( Graphics g )
  {
    if ( vc == null )
    {
        return;
    }
    Graphics drawplatte = g;

    // clear old drawing area

    drawplatte.clearRect( 0, 0, this.getWidth(), this.getHeight() );

    this.drawCoordination(drawplatte);

    if ( this.DataType == 0 )
    {
      this.updateScreenUsingInteger( g );
    }
    else
    if ( this.DataType == 1 )
    {
      this.updateScreenUsingDouble( g );
    }

  }

}