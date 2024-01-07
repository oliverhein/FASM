/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

package de.marketsim.gui;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.borland.jbcl.layout.*;

import de.marketsim.util.*;
import de.marketsim.SystemConstant;

import java.awt.event.*;

public class OperatorStatusFrame extends JFrame
{
  private int mXScale = 1;
  private int mYScale = 1;
  private GitterPanel jPanel1 = new GitterPanel();
  private Border border1;
  private VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  private BorderLayout borderLayout1 = new BorderLayout();

  public OperatorStatusFrame()
  {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public  void clearNameList()
  {
       this.jPanel1.clearNameList();
  }

  public void saveNameList( Vector pNameList  )
  {
     Point pp = this.jPanel1.saveNameList(pNameList);
     if ( pp != null )
     {
        System.out.println("Size will be adjusted.");
        this.repaint();
     }
  }

  public boolean isNameListLoaded( )
  {
     return jPanel1.isNameListLoaded();
  }

  public void DisplayStatus( Vector pData )
  {
      jPanel1.showstatus( pData );
  }

  public static void main(String[] args)
  {
    OperatorStatusFrame pp = new OperatorStatusFrame();

    Vector vv = new Vector();
    vv.add("AA");
    vv.add("BB");
    vv.add("CC");
    vv.add("XX");
    vv.add("ZZ");
    vv.add("88");
    vv.add("KK");
    vv.add("DD");
    vv.add("OO");
    vv.add("33");
    vv.add("TT");
    vv.add("LL");
    vv.add("WW");

    pp.saveNameList( vv );
    pp.setSize(500,400);
    pp.setVisible(true);

        Vector vv2 = new Vector();
        vv2.add("AA;B");
        vv2.add("CC;B");
        vv2.add("WW;S");
        vv2.add("KK;B");
        vv2.add("33;N");
        pp.DisplayStatus( vv2 );
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    this.setTitle("Red:Sold, Blue:Bought, Yellow:Wait, Magenta:N-Sold, Darkblue:N-Bought");
    this.getContentPane().setLayout(borderLayout1);
    jPanel1.setBorder(BorderFactory.createLineBorder(Color.black));
    jPanel1.setLayout(verticalFlowLayout1);
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    this.setIconImage(  Toolkit.getDefaultToolkit().getImage("fasm.gif") );

  }

  public class GitterPanel extends JPanel
  {
    private Color TextColor = Color.cyan;
    private Color BackColor = Color.black;

    private Color BuyColor  = Color.blue;    // Blau -- Buy is performed
    private Color SellColor = Color.red;     // RED -- Sell is performed
    private Color WaitColor = Color.black;   // Black -- Wait

    private Color BuyWishnotfulfilledColor  = SystemColor.inactiveCaption; // BuyOrder is not peformed.
    private Color SellWishnotfulfilledColor = SystemColor.magenta; // SellOrder is not peformed.

    private int mAgentNumber = -1;
    public  int mXScale = 1;
    public  int mYScale = 1;
    private Vector     mCurrentData = new Vector();
    private Hashtable  mPositionmap = new Hashtable();


    public void clearNameList()
    {
         this.mAgentNumber = -1;
         this.mPositionmap.clear();
    }

    private Point Goldspliter(int pAgentNumber )
    {
        double dd = pAgentNumber -(int) Math.sqrt( pAgentNumber ) * (int) Math.sqrt( pAgentNumber );
        if ( dd == 0.0 )
        {
            int j = (int) Math.sqrt( pAgentNumber ) ;
            return new Point(j,j);
        }
        else
        {
          int y;
          int x = (int)Math.sqrt( pAgentNumber );
          int delta = pAgentNumber - (pAgentNumber / x ) * x ;
          if ( delta == 0 )
          {
             y = pAgentNumber / x;
          }
          else
          {
             y = (pAgentNumber / x) + 1;
          }
          return new Point(x,y);
       }
    }

    public boolean isNameListLoaded()
    {
      if ( this.mAgentNumber == -1 )
      {
        return false;
      }
      else
      {
        return true;
      }
    }

    public Point saveNameList(Vector pNameList )
    {
       this.mAgentNumber = pNameList.size();
       Point pp = Goldspliter(this.mAgentNumber);
       this.mXScale = pp.x;
       this.mYScale = pp.y;

       /* only for test
       System.out.println("After Goldschnitt");
       System.out.println("Gitter AgentNumber=" + this.mAgentNumber );
       System.out.println("Gitter XScale=" + this.mXScale );
       System.out.println("Gitter YScale=" + this.mYScale );
       */

       for ( int i=0; i< this.mAgentNumber; i++)
       {
          Point pos = new Point();
          pos.y = i / this.mXScale;
          pos.x = i - ( i/ this.mXScale ) * this.mXScale;
          this.mPositionmap.put( pNameList.elementAt(i) , pos);
          System.out.println( pNameList.elementAt(i) +" X=" + pos.x + " Y=" + pos.y );
       }

       int GitterWidth = this.getWidth() / this.mXScale;
       int GitterHeight = this.getHeight() / this.mYScale;

       if ( (GitterWidth < 40) || ( GitterHeight < 25 ) )
       {
           return new Point(40*this.mXScale+5,25*this.mYScale+5);
       }
       else
       {
          return null;
       }
    }

    private void showstatus( Vector pStatusData)
    {
         // add data into PositionMap
         this.mCurrentData = pStatusData;
         // start to display status
         this.repaint();
         // repaint() call paintComponent
    }

    public void paintComponent( Graphics g )
    {
         updateStatus( g );
    }

    public void  updateStatus( Graphics g )
    {
        System.out.println("Status Frame will be updated");

       // draw Gitter
       this.drawgitter( g );

       // System.out.println("Status Frame Gitter is printed");
       // draw status
       Graphics drawplate = g;
       int xmax = this.getWidth();
       int ymax = this.getHeight();

       // System.out.println(this.mCurrentData.size()+ " DataItem recieved by StatusFrame *****************");

       for ( int j=0; j< this.mCurrentData.size(); j++ )
       {
            // Format AgentName;Type,Action
            String ss = (String) this.mCurrentData.elementAt(j);
            // get the Agent Name
            int j1 = ss.indexOf(";");
            String AgentName = ss.substring(0,j1);
            // get the Agent Type
            int j2 = ss.indexOf(";",j1+1);
            int AgentType = Integer.parseInt( ss.substring(j1+1,j2) );
            // get the Order_Result
            char Result = ss.charAt(j2+1);
            char OriginalWish = ' ';

            if ( Result == 'W' )
            {
              // Wish is not performed
              OriginalWish = ss.charAt(j2+1+2);
            }

            // Set color of fill
            switch  ( Result )
            {
              case 'B':drawplate.setColor( this.BuyColor ); break;
              case 'S':drawplate.setColor( this.SellColor ); break;
              case 'N':drawplate.setColor( this.WaitColor ); break;
              case 'W':
                if ( OriginalWish == 'B' )
                {
                   drawplate.setColor( this.BuyWishnotfulfilledColor );
                }
                else
                {
                   drawplate.setColor( this.SellWishnotfulfilledColor );
                }
                break;
            }
            // Get the position
            Point pp = (Point) this.mPositionmap.get(AgentName);

            //System.out.println("Gitter  "  + AgentName + "Position=" +  pp );

            if ( pp != null )
            {
                  int x1 = (int) (xmax* pp.x / this.mXScale);
                  int y1 = (int) (ymax* pp.y / this.mYScale);
                  int x2 = (int) (xmax* (pp.x+1) / this.mXScale);
                  int y2 = (int) (ymax* (pp.y+1) / this.mYScale);
                  drawplate.fillRect( (x1+2),(y1+2),(x2-x1-2),(y2-y1-2));
                  int centerxx = x1 + ( x2-x1) / 2;
                  int centeryy = y1 + ( y2-y1) / 2;

                  String name = "";
                  if ( AgentType == SystemConstant.AgentType_INVESTOR )
                  {
                      name = "F" ;
                   }
                   else
                   if ( AgentType == SystemConstant.AgentType_NOISETRADER )
                   {
                       name = "N";
                   }
                   else
                   if ( AgentType == SystemConstant.AgentType_RANDOMTRADER )
                   {
                       name = "R";
                   }
                   else
                   if ( AgentType == SystemConstant.AgentType_TobinTax )
                   {
                         name = "T";
                   }

                   drawplate.setColor( this.TextColor );
                   int jj = centerxx-5;
                   if ( jj < 0 )
                   {
                      jj = 1;
                   }
                   drawplate.drawString( name , jj, centeryy );
            }
       }


    }

    private void drawgitter(Graphics g)
    {
      Graphics drawplate = g;
      int xmax = this.getWidth();
      int ymax = this.getHeight();
      drawplate.setColor( BackColor );
      drawplate.clearRect(0,0,xmax, ymax );
      drawplate.setColor( Color.white );
      int x0,y0,x1,y1;
      x0=0;
      y0=0;

      // draw mXScale Vertical Line
      for ( int i=0; i<=this.mXScale; i++)
      {
          x1 = (int) (xmax*i / this.mXScale);
          drawplate.drawLine(x1,y0, x1, ymax );
      }

      // draw mYScale Horizonal Line
      for ( int i=0; i<=this.mYScale; i++)
      {
          y1 = (int) (ymax*i / this.mYScale);
          drawplate.drawLine(x0,y1, xmax, y1 );
      }
    }
  }

}