package de.marketsim.gui.graph;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */
//import edu.uci.ics.jung.graph.decorators.EdgePaintFunction;

import java.awt.Shape;

import edu.uci.ics.jung.graph.decorators.EdgeShapeFunction;
import edu.uci.ics.jung.graph.Edge;

import edu.uci.ics.jung.graph.decorators.EdgeShape;


public class EdgeShapeImpl implements EdgeShapeFunction
{

  public EdgeShapeImpl()
  {
  }

  public Shape getShape(Edge edge)
  {
     //return  new java.awt.geom.Line2D.Float();
    return  new java.awt.geom.Arc2D.Float();

  }
}