package de.marketsim.gui.graph;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.awt.SystemColor;
import java.awt.Paint;
import java.awt.Color;
import java.util.Hashtable;

import java.sql.Timestamp;

import edu.uci.ics.jung.graph.decorators.VertexPaintFunction;
import edu.uci.ics.jung.graph.Vertex;

public class VertexPaintFunctionImpl  implements  VertexPaintFunction
{

private Paint  BuyColor     = Color.BLUE ;
private Paint  SellColor    = Color.RED;
private Paint  WaitColor    = Color.YELLOW ;
private Paint  NothingColor = Color.BLACK ;
private Color BuyWishnotfulfilledColor  = SystemColor.inactiveCaption; // BuyOrder is not peformed.
//private Color SellWishnotfulfilledColor = SystemColor.magenta; // SellOrder is not peformed.

Hashtable  mAgentAktionList  ;

public VertexPaintFunctionImpl(Hashtable pAgentAktionList)
{
    mAgentAktionList = pAgentAktionList;
}

public Paint getDrawPaint(Vertex v)
{
   // check Wechle Status
   String ss = v.toString();
   return  Color.BLACK;
}

public Paint getFillPaint(Vertex v)
{

  String ss = (String) v.getUserDatum("NAME");

  if ( ss == null )
  {
      return Color.black;
  }

  //Timestamp ts = new java.sql.Timestamp( System.currentTimeMillis() );
  //System.out.println( ts.toString().substring(12) + "  getFillPaint(" + ss+ ") is called" );

  String action = (String) this.mAgentAktionList.get( ss );

  if ( action == null )
  {
     return Color.black;
  }

  Color cc ;

  if ( action.equalsIgnoreCase("B")  )
  {
      return this.BuyColor;
  }
  else
  if ( action.equalsIgnoreCase("S")  )
  {
      return this.SellColor;
  }
  else
  if ( action.equalsIgnoreCase("N")  )
  {
      return this.WaitColor;
  }
  else
  if ( action.equalsIgnoreCase("W")  )
  {
      return this.BuyWishnotfulfilledColor;
  }
  else
  {
      return this.NothingColor;
  }
}

public void setActionList (Hashtable pActionList)
{
  this.mAgentAktionList = pActionList;
}

}

