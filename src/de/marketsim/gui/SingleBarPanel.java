package de.marketsim.gui;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Zeichne eine Menge von Balkon
 *   Daten: Vector von  ( 1 DoubleWerte, 1 String Beschreibung  )
 *   AXIS ist in der Mitte
 *   Minus Wert: Rot Farbe
 *   PLUS Wert: Blau Farbe
 *     </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import javax.swing.JPanel;

import java.util.*;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.text.*;
import de.marketsim.util.*;
import de.marketsim.util.SingleBarData;

public class SingleBarPanel extends JPanel
{

  private  DataFormatter decformat = new DataFormatter("Germany");

  private Vector  mGewinnData = null;
  private double kdoublemax_init = -300000.0 ;  //this value will be updated by the methode UpdataMaxMin(()
  private double kdoublemin_init =  300000.0 ;  //this value will be updated by the methode UpdataMaxMin(()

  private double kdoublemax = kdoublemax_init ;  //this value will be updated by the methode UpdataMaxMin(()
  private double kdoublemin = kdoublemin_init ;  //this value will be updated by the methode UpdataMaxMin(()

  private Coordinator mCoordinator = new Coordinator();
  private int XScaleNumber  = 20;
  private int XScaleFactor  = 10;
  private int YScaleNumber  = 5;

  private String mYTitel = "%"; // Default
  // Parameter für Drawing line
  // default Color
  private java.awt.Color mCoordinatorColor = java.awt.Color.black ;
  private java.awt.Color mLineDrawingColor = java.awt.Color.blue ;

  public SingleBarPanel()
  {
     mCoordinator.bottommargin = 60;
     mCoordinator.rightmargin = 50;
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

  public void setCoordinatorColor( java.awt.Color  pColor )
  {
     this.mCoordinatorColor = pColor;
  }

  public void setLineDrawingColor( java.awt.Color  pColor )
  {
     this.mLineDrawingColor = pColor;
  }

  public void setYScaleNumber(int j)
  {
       YScaleNumber = j;
  }

  public void setXScaleNumber(int j)
  {
       XScaleNumber = j;
  }

  public void setXScaleFactor(int j)
  {
       XScaleFactor = j;
       int k = XScaleNumber/XScaleFactor;
       if ( k > 20 )
       {
          XScaleFactor = XScaleNumber / 20;
       }

  }

  public void setData(Vector PData, boolean pUpdate_Y_Scale )
  {
     this.mGewinnData = PData;
     if ( PData == null  )
     {
        return;
     }

     this.XScaleNumber = this.mGewinnData.size();

     if ( pUpdate_Y_Scale)
     {
        this.UpdateY_Achse_Scale();
     }
     // Wenn nicht update Y_Scale, man muss Min und Max seperate setzen.

  }

  public void setMin(double pMin)
  {
    this.kdoublemin = pMin;
  }

  public void setMax(double pMax)
  {
    this.kdoublemax = pMax;
  }

  public void showChart()
  {
      this.repaint();
  }

  public void paintComponent( Graphics g )
  {
      updateScreen( g );
  }

  private void updateScreen(Graphics g)
   {
      Graphics drawplatte = g;
      // clear old drawing area
      drawplatte.clearRect( 1,1,  this.getWidth()-2, this.getHeight()-2 );

     if ( this.mGewinnData == null )
     {
       return;
     }

     if ( this.mGewinnData.size() == 0 )
     {
       return;
     }

     this.drawCoordination(drawplatte);
     this.drawDataBar( g );
   }

  // draw the coordination
  private void drawCoordination(Graphics g)
  {
      // draw x-axial
      // Graphic physical coordination
      Graphics drawplatte = g;

      // clear old drawing area
      drawplatte.clearRect( 1,1,  this.getWidth()-2, this.getHeight()-2 );

      this.mCoordinator.y0   = this.getHeight() - this.mCoordinator.bottommargin ;
      this.mCoordinator.xmax = this.getWidth() -  this.mCoordinator.rightmargin;

      drawplatte.setColor( this.mCoordinatorColor );

      // Draw X-AXIS
      drawplatte.drawLine(this.mCoordinator.x0,
                          this.mCoordinator.y0,
                          this.mCoordinator.xmax+this.mCoordinator.arrowlength,
                          this.mCoordinator.y0);

       // Display X-Axis Name
       //drawplatte.drawString("Agent",
       //                       this.mCoordinator.xmax+this.mCoordinator.arrowlength,
       //                       this.mCoordinator.y0);

        // Draw X-AXIS Pfeifer
        //             \
        //  ------------
        //             /

        drawplatte.drawLine(this.mCoordinator.xmax+this.mCoordinator.arrowlength,
                            this.mCoordinator.y0,
                            this.mCoordinator.xmax,
                            this.mCoordinator.y0-5);
        drawplatte.drawLine(this.mCoordinator.xmax+this.mCoordinator.arrowlength,
                            this.mCoordinator.y0,
                            this.mCoordinator.xmax,
                            this.mCoordinator.y0+5);

        double pixelperscale = ( this.mCoordinator.xmax - this.mCoordinator.x0 )/ XScaleNumber;

        Font fn = Font.getFont( "BOLD", new Font("BOLD", Font.PLAIN, 10) );
        drawplatte.setFont( fn );

        for (int m=0;m<=XScaleNumber;m++)
        {
          int pointx = (int) (this.mCoordinator.x0 + m*( this.mCoordinator.xmax - this.mCoordinator.x0 ) / XScaleNumber);
          drawplatte.drawLine( pointx, this.mCoordinator.y0,pointx, this.mCoordinator.y0+50);
        }
        drawplatte.setFont( new  Font(  drawplatte.getFont().getName(), drawplatte.getFont().getStyle()+ Font.BOLD  , 12) );
        //drawplatte.drawString("Linksbalkon: Absoluter GewinnProzent, Rechtbalkon: Relativer GewinnProzent ", this.mCoordinator.x0+ 5, this.mCoordinator.y0 + 60    );
        drawplatte.drawString("Blaue Farbe: PlusGewinn, Rote Farbe: Minus Gewinn"                             ,this.mCoordinator.x0+ 5, this.mCoordinator.y0 + 60    );

        // draw Y-AXIS
        drawplatte.drawLine( this.mCoordinator.x0,
                             this.mCoordinator.y0,
                             this.mCoordinator.x0,
                             this.mCoordinator.ymax-this.mCoordinator.arrowlength );
        drawplatte.drawString( this.mYTitel, this.mCoordinator.x0-10, this.mCoordinator.ymax -15 );

        // draw Y-Arror
        drawplatte.drawLine(this.mCoordinator.x0,
                            this.mCoordinator.ymax-this.mCoordinator.arrowlength,
                            this.mCoordinator.x0-5,
                            this.mCoordinator.ymax );
        drawplatte.drawLine(this.mCoordinator.x0,
                            this.mCoordinator.ymax-this.mCoordinator.arrowlength,
                            this.mCoordinator.x0+5,
                            this.mCoordinator.ymax );

        // draw scale for the y-axis
        double datams= (this.mCoordinator.y0-this.mCoordinator.ymax) / (kdoublemax-kdoublemin) ;

        for(int m=0; m<=YScaleNumber; m++)
        {
            int pointy = this.mCoordinator.y0-m*(this.mCoordinator.y0-this.mCoordinator.ymax)/YScaleNumber ;
            drawplatte.drawLine(this.mCoordinator.x0,
                                pointy,
                                this.mCoordinator.x0-5,
                                pointy);
            // double
            double yscale = kdoublemin + m * ( kdoublemax - kdoublemin) / YScaleNumber;
            yscale = (int) ( yscale*100.0 );
            yscale = yscale / 100.0;
            String tt = "" + this.decformat.format2str( yscale ) ;

            int neededwidth = drawplatte.getFontMetrics().stringWidth(tt) + 4;
            fn = Font.getFont( "BOLD", new Font("BOLD", Font.TRUETYPE_FONT, 10) );
            drawplatte.setFont( fn );
            drawplatte.drawString( tt,this.mCoordinator.x0-neededwidth,pointy+2);
        }

  }

  private void drawDataBar(Graphics g)
  {

        int n = this.mGewinnData.size();

        Graphics drawplatte = g;

        // set the color of line
        drawplatte.setColor( this.mLineDrawingColor );
        // draw data

        int xx1 = this.mCoordinator.x0 ;
        int xx2,xx3,yy2,yy3;
        double OneXScaleLength = ( this.mCoordinator.xmax - this.mCoordinator.x0 ) *1.0 / XScaleNumber ;

        for (int m=0; m<this.mGewinnData.size() ;m++ )
        {
           SingleBarData dd = ( SingleBarData ) this.mGewinnData.elementAt(m);
           xx2 = (int) ( this.mCoordinator.x0 + (m+1) * OneXScaleLength )  ;

           yy2 = this.mCoordinator.y0 -
                 (int) ( ( Math.abs ( dd.mRelativeGewinnProzent ) - kdoublemin ) * (this.mCoordinator.y0-this.mCoordinator.ymax) / (kdoublemax-kdoublemin)  );

           // der erste Bar  (Link Bar )
           if ( dd.mRelativeGewinnProzent >=0.0 )
           {
               // Fill-Color: Blue
               drawplatte.setColor( Color.blue );
           }
           else
           {
             // Fill-Color: Red
             drawplatte.setColor( Color.red  );
           }

           drawplatte.fillRect( xx1, yy2,  ( int ) ( OneXScaleLength ), ( this.mCoordinator.y0 -  yy2 ));

           /* JDK 1.4
           AffineTransform at = new AffineTransform();
           at.setToRotation(Math.PI/2.0);
           drawplatte.setTransform(at);
           drawplatte.drawString("Vertical text", xx1+2, yy3);
           */

           drawplatte.setColor( Color.black );

           /*
           only in JDK 1.4 supported
           at = new AffineTransform();
           at.setToRotation( 0 );
           */

           drawplatte.drawString( dd.mData1Description, xx1+2, this.mCoordinator.y0 + 15 );
           xx1 = xx2;
        }
  }

  private void UpdateY_Achse_Scale()
  {
        // Double
        kdoublemax = kdoublemax_init ;
        kdoublemin = 0.0 ;
        for (int i=0; i< this.mGewinnData.size();i++)
        {
           SingleBarData pp = (SingleBarData) this.mGewinnData.elementAt(i);
           kdoublemax = Math.max( kdoublemax, Math.abs( pp.mRelativeGewinnProzent ) );
        }
  }

}
