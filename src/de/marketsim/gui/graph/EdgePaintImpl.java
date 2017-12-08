package de.marketsim.gui.graph;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import edu.uci.ics.jung.graph.decorators.EdgePaintFunction;
import java.awt.Shape;
import java.awt.Paint;
import java.awt.Color;

import edu.uci.ics.jung.graph.decorators.EdgeShapeFunction;
import edu.uci.ics.jung.graph.Edge;


public class EdgePaintImpl implements EdgePaintFunction
{

  public Paint getDrawPaint(Edge edge)
  {
     return Color.red;
  }

  public Paint getFillPaint(Edge edge)
  {
     return Color.red;
   }

}