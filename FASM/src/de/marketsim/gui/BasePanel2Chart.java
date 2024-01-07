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
import java.awt.*;
import java.text.*;
import de.marketsim.util.*;

public class BasePanel2Chart extends JPanel
{

  private static int kmax_default = -3000000; // Default MAX,
  private static int kmin_default =  3000000; // Default MIN,

  private int DataIndexBegin = 0; // Default
  private Vector vc  = null;
  private Vector vc2 = null;

  private int mLowLimit  = -100;  // Default, alle Value which are smaller than it, will be chaneged to it.
  private boolean mLowLimitCheck  = false;  // True make LowLimit Check, False No.

  private int kmax  = kmax_default;  // Init to Default, this value will be updated by the methode UpdataMaxMin(()
  private int kmin  = kmin_default;  // Init to Default, this value will be updated by the methode UpdataMaxMin(()
  private double kdoublemax = kmax_default ;  //Init to Default, this value will be updated by the methode UpdataMaxMin(()
  private double kdoublemin = kmin_default ;  //Init to Default, this value will be updated by the methode UpdataMaxMin(()
  private Coordinator mCoordinator = new Coordinator();
  private int XScaleNumber  = 300;
  private int XScaleFactor  = 20;
  private int YScaleNumber  = 5;

  private String mPreiceTitel   = "Preis";
  private String mInnerwertTitel = "InnWert";

  // default Color
  private java.awt.Color mCoordinatorColor = java.awt.Color.black ;
  private java.awt.Color mPreiceDrawingColor    = java.awt.Color.red ;
  private java.awt.Color mInnerwertDrawingColor = java.awt.Color.blue ;
  private  DataFormatter decformat = new DataFormatter("Germany");

  private  boolean MaxMinUpdatingRequired = true;

  private int DataType = -1;

   // -1:  Type not defined
   // 0: Integer,
   // 1: Double

  private int DataType2 = -1;
  private boolean mShowInnererWertEnaled = true;

  public static int IntegerDataType = 0;
  public static int DoubleDataType = 1;

  public BasePanel2Chart()
  {
      decformat.setMaxFractionDigits(2);
  }

  public void setCoordinatorColor( java.awt.Color  pColor )
  {
    this.mCoordinatorColor = pColor;
  }

  public void setPreiceTitel(String pTitel )
  {
     this.mPreiceTitel = pTitel;
  }

  public void setInnerwertTitel(String pTitel )
  {
     this.mInnerwertTitel = pTitel;
  }

  public void setPreiceDrawingColor( java.awt.Color  pColor )
  {
    this.mPreiceDrawingColor = pColor;
  }

  public void setLineBDrawingColor( java.awt.Color  pColor )
  {
    this.mInnerwertDrawingColor = pColor;
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
     this.kmax = pMax;
  }

  public void setDataMax(double pMax)
  {
     this.kdoublemax = pMax;
  }

  public void setDataMin(int pMin)
  {
     this.kmin = pMin;
  }

  public void setDataMin(double pMin)
  {
     this.kdoublemin = pMin;
  }

  public void setDataMinMax2Default()
  {
    kmax       = kmax_default;
    kmin       = kmin_default;
    kdoublemax = kmax_default;
    kdoublemin = kmin_default;
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

  public void setLowLimitCheck(boolean pCheck   )
  {
       mLowLimitCheck = pCheck;
  }

 // Set Data Type for 1. Line
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

  // Set Data Type for 2. Line
  public void setDataType2(String TypeName) throws Exception
  {
     if ( TypeName.equalsIgnoreCase("Integer") )
     {
        this.DataType2 = 0;
     }
     else
     if ( TypeName.equalsIgnoreCase("Double") )
     {
        this.DataType2 = 1;
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

  public String getDataType2Name()
  {
     if ( this.DataType2 == 0 )
     {
        return "Integer";
     }
     else
     {
        return "Double";
     }
  }

  public void UpdateMaxMin()
  {
      if ( this.DataType == -1 )
      {
         //  throw new Exception("Datatype is not defined.");
         System.out.println ( " !!!!!!!!!!!!!!!  Datatype is not defined.");
         return;
      }

      if (  ! this.MaxMinUpdatingRequired )
      {
          System.out.println ( "Updating MinMax is not required.");
          return;
      }

      if ( this.getFinalDataTYpe() == IntegerDataType )
      {
              // Two DataTypes are Integer
              for (int i=0; i<vc.size(); i++)
              {
                 Integer pp = (Integer) vc.elementAt(i);
                 Integer pp2 = (Integer) vc2.elementAt(i);
                 kmax = Math.max(kmax, pp.intValue() );
                 kmin = Math.min(kmin,pp.intValue() );
                 kmax = Math.max(kmax, pp2.intValue() );
                 kmin = Math.min(kmin,pp2.intValue() );
              }
      }
      else
      {
              // Two Datatypes are not same.
              // One DataTYpe is Double, another is Integer
              for (int i=0; i<vc.size();i++)
              {
                if ( this.DataType == DoubleDataType )
                {
                     Double pp = (Double) vc.elementAt(i);
                     kdoublemax = Math.max( kdoublemax,  pp.doubleValue() );
                     kdoublemin = Math.min( kdoublemin,  pp.doubleValue() );
                 }
                 else
                 {
                     Integer pp = (Integer) vc.elementAt(i);
                     kdoublemax = Math.max( kdoublemax,  pp.doubleValue() );
                     kdoublemin = Math.min( kdoublemin,  pp.doubleValue() );
                  }

                  if ( this.DataType2 == DoubleDataType )
                  {
                       Double pp = (Double) vc2.elementAt(i);
                       kdoublemax = Math.max( kdoublemax,  pp.doubleValue() );
                       kdoublemin = Math.min( kdoublemin,  pp.doubleValue() );
                   }
                   else
                   {
                       Integer pp = (Integer) vc2.elementAt(i);
                       kdoublemax = Math.max( kdoublemax, pp.doubleValue() );
                       kdoublemin = Math.min( kdoublemin, pp.doubleValue() );
                    }
              }
      }

      if ( this.mLowLimitCheck )
      {
        if ( kdoublemin < this.mLowLimit  )
        {
          kdoublemin =this.mLowLimit;
        }

        if ( kmin < this.mLowLimit  )
        {
          kmin =this.mLowLimit;
        }
      }

  }

  public void setData(Vector pPreice, Vector pInnerwert, boolean pShowInnererWertEnabled )
  {
     this.vc  = pPreice;
     this.mShowInnererWertEnaled = pShowInnererWertEnabled;
     this.vc2 = pInnerwert;
  }

  public void setInnererWertEnabled(boolean pShowInnererWertEnabled )
  {
     this.mShowInnererWertEnaled = pShowInnererWertEnabled;
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

      //drawplatte.setColor(drawplatte.getColor().red );
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
        drawplatte.setColor( this.mPreiceDrawingColor );
        drawplatte.drawString( this.mPreiceTitel, this.mCoordinator.x0-10, this.mCoordinator.ymax-15);
        if (this.mShowInnererWertEnaled)
        {
          drawplatte.setColor( this.mInnerwertDrawingColor );
          drawplatte.drawString( this.mInnerwertTitel, this.mCoordinator.x0 + this.mPreiceTitel.length()*8, this.mCoordinator.ymax-15);
        }
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
        // When Integer Data, Max/Min is saved to kmax and kmin
        if ( this.getFinalDataTYpe() == IntegerDataType )
        {
           datams= (this.mCoordinator.y0-this.mCoordinator.ymax) / (kmax-kmin) ;
        }
        else
        // When Double Datatype, Max/Min is saved to kdoublemax and kdoublemin
        {
           datams= (this.mCoordinator.y0-this.mCoordinator.ymax) / (kdoublemax-kdoublemin) ;
        }

        for(int m=0; m<=YScaleNumber; m++)
        {
            int pointy = this.mCoordinator.y0-m*(this.mCoordinator.y0-this.mCoordinator.ymax)/YScaleNumber;
            drawplatte.drawLine(this.mCoordinator.x0,
                                pointy,
                                this.mCoordinator.x0-5,
                                pointy);
            // Integer
            String tt ="";
            if ( this.getFinalDataTYpe() == IntegerDataType )
            {
              tt = ""+( kmin + m* ( kmax - kmin) / YScaleNumber);
            }
            else
            // double
            {
              tt = "" + this.decformat.format2str( kdoublemin + m * ( kdoublemax - kdoublemin) / YScaleNumber ) ;
            }

            int neededwidth = drawplatte.getFontMetrics().stringWidth(tt) + 4;
            Font fn = Font.getFont( "BOLD", new Font("BOLD", Font.TRUETYPE_FONT, 10) );
            drawplatte.setFont( fn );
            drawplatte.drawString( tt,this.mCoordinator.x0-neededwidth, pointy);
        }
  }

  private int getFinalDataTYpe()
  {
    int FinalDataType = IntegerDataType;  //IntegerDataType;
    if ( ( this.DataType== DoubleDataType) || (this.DataType2 == DoubleDataType ) )
    {
       FinalDataType = DoubleDataType;
    }
    return FinalDataType;
  }

  private void updateScreenUsingInteger(Graphics g)
  {
        int n = vc.size();
        int PreiceData[] = new int[n];

        int InnerwertData[] =null;

         InnerwertData= new int[n];

        if ( this.mLowLimitCheck )
        {
          for (int m=0; m<n; m++ )
          {
            PreiceData[m]= Math.max ( ( (Integer) vc.elementAt(m) ).intValue(), this.mLowLimit);

            InnerwertData[m]= Math.max ( ( (Integer) vc2.elementAt(m)).intValue(), this.mLowLimit);

          }
        }
        else
        {
            for (int m=0; m<n; m++ )
            {
              PreiceData[m]= ( (Integer) vc.elementAt(m)).intValue()  ;
              InnerwertData[m]= ( (Integer) vc2.elementAt(m)).intValue();

            }
        }

        // draw Preice-kurv LineA using user data vc
        int xx1 = this.mCoordinator.x0 + (int)( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber;
        int yy1 = this.mCoordinator.y0 -  ( PreiceData[0]-kmin)  * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kmax-kmin) ;
        int xx2,yy2;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor( this.mPreiceDrawingColor );
        Stroke previous = g2d.getStroke();
        g2d.setStroke(new BasicStroke( 2 ));

        for (int m=1; m<n ;m++ )
        {
           xx2 = (int) (this.mCoordinator.x0 + (m+1)*( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber);
           yy2 = this.mCoordinator.y0 - ( PreiceData[m]-kmin) * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kmax-kmin) ;
           g2d.drawLine(xx1,yy1,xx2,yy2);
           xx1 = xx2;
           yy1 = yy2;
        }
        g2d.setStroke(previous);

        Graphics drawplatte = g;

        // draw Innerwert chart using user data vc2
        if (this.mShowInnererWertEnaled)
        {
          xx1 = this.mCoordinator.x0 + (int)( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber;
          yy1 = this.mCoordinator.y0 -  ( InnerwertData[0]-kmin)  * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kmax-kmin) ;
          drawplatte.setColor( this.mInnerwertDrawingColor );

          for (int m=1; m<n ;m++ )
          {
            xx2 = (int) (this.mCoordinator.x0 + (m+1)*( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber);
            yy2 = this.mCoordinator.y0 - ( InnerwertData[m]-kmin) * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kmax-kmin) ;
            drawplatte.drawLine(xx1,yy1,xx2,yy2);
            xx1 = xx2;
            yy1 = yy2;
        }
        }
  }

  private void updateScreenUsingDouble(Graphics g)
  {
        // There are 3 combinations:
        // LineA: Integer, Line B: Double
        // LineA: Double,  Line B: Double
        // LineA: Double , Line B: Integer

        int n = vc.size();

        double LineAData[] = new double[n];
        double LineBData[] = new double[n];

        if ( this.DataType == IntegerDataType )
        {
          if ( this.mLowLimitCheck )
          {
                  for (int m=0; m<n; m++ )
                  {
                    Integer dd = (Integer) vc.elementAt(m);
                    LineAData[m]= Math.max(  dd.doubleValue(), this.mLowLimit) ;
                  }
          }
          else
          {
                  for (int m=0; m<n; m++ )
                  {
                    Integer dd = (Integer) vc.elementAt(m);
                    LineAData[m]= dd.doubleValue() ;
                  }
          }
        }
        else
        {
          if ( this.mLowLimitCheck )
          {
                for (int m=0; m<n; m++ )
                {
                  LineAData[m]= Math.max (  ( (Double) vc.elementAt(m)).doubleValue(), this.mLowLimit );
                }
          }
          else
          {
                for (int m=0; m<n; m++ )
                {
                  LineAData[m]= ( (Double) vc.elementAt(m)).doubleValue();
                }
          }
        }
        if (this.mShowInnererWertEnaled)
        {

        if ( this.DataType2 == IntegerDataType )
        {
          if ( this.mLowLimitCheck )
          {
                for (int m=0; m<n; m++ )
                {
                  Integer dd = (Integer) vc2.elementAt(m);
                  LineBData[m]= Math.max( dd.doubleValue(), this.mLowLimit ) ;
                }
          }
          else
          {
                for (int m=0; m<n; m++ )
                {
                  Integer dd = (Integer) vc2.elementAt(m);
                  LineBData[m]= dd.doubleValue() ;
                }
          }
        }
        else
        {
          if ( this.mLowLimitCheck )
          {
                for (int m=0; m<n; m++ )
                {
                  LineBData[m]= Math.max ( ( (Double) vc2.elementAt(m)).doubleValue(), this.mLowLimit);
                }
          }
          else
          {

            for (int m=0; m<n; m++ )
                {
                  LineBData[m]= ( (Double) vc2.elementAt(m)).doubleValue();
                }
          }
        }
        }

        Graphics drawplatte = g;
        // draw 1. Line
        int xx1 = this.mCoordinator.x0 + (int) ( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber;
        int yy1 = this.mCoordinator.y0 - (int) ( (LineAData[0]-kdoublemin)  * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kdoublemax-kdoublemin) ) ;
        int xx2,yy2;
        drawplatte.setColor( this.mPreiceDrawingColor );

        for (int m=1; m<n ;m++ )
        {
           xx2 = (int) (this.mCoordinator.x0 + (m+1)*( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber);
           yy2 = this.mCoordinator.y0 - (int) ( (LineAData[m]-kdoublemin) * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kdoublemax-kdoublemin) );
           drawplatte.drawLine(xx1,yy1,xx2,yy2);
           xx1 = xx2;
           yy1 = yy2;
        }

        if (this.mShowInnererWertEnaled)
        {
            // draw 2. Line
            xx1 = this.mCoordinator.x0 + (int)( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber;
            yy1 = this.mCoordinator.y0 - (int) ( (LineBData[0]-kdoublemin)  * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kdoublemax-kdoublemin) ) ;
            drawplatte.setColor( this.mInnerwertDrawingColor );

            for (int m=1; m<n ;m++ )
            {
               xx2 = (int) (this.mCoordinator.x0 + (m+1)*( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber);
               yy2 = this.mCoordinator.y0 - (int) ( (LineBData[m]-kdoublemin) * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kdoublemax-kdoublemin) );
               drawplatte.drawLine(xx1,yy1,xx2,yy2);
               xx1 = xx2;
               yy1 = yy2;
            }
        }
  }

  private void updateScreen(Graphics g)
  {
    if ( this.vc == null )
    {
      System.out.println("VC == null, nothing is to do");
      return;
    }

    Graphics drawplatte = g;

    // clear old drawing area
    drawplatte.clearRect( 1,1,  this.getWidth()-2, this.getHeight()-2 );
    this.drawCoordination(drawplatte);

    int FinalDataType = 0;  // Integer/

    if ( (this.DataType == 1 ) || (this.DataType2 == 1 ) )
    {
      FinalDataType = 1;  // Double
    }

    if ( FinalDataType == 0 )
    {
      this.updateScreenUsingInteger( g );
    }
    else
    {
      this.updateScreenUsingDouble( g);
    }

  }

}