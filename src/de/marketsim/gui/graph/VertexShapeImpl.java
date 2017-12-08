package de.marketsim.gui.graph;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Hashtable;


import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;



import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;

import org.apache.commons.collections.Predicate;

import edu.uci.ics.jung.algorithms.importance.VoltageRanker;
import edu.uci.ics.jung.graph.DirectedEdge;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedEdge;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.AbstractVertexShapeFunction;
import edu.uci.ics.jung.graph.decorators.ConstantEdgeStringer;
import edu.uci.ics.jung.graph.decorators.ConstantVertexStringer;
import edu.uci.ics.jung.graph.decorators.DefaultToolTipFunction;
import edu.uci.ics.jung.graph.decorators.EdgeFontFunction;
import edu.uci.ics.jung.graph.decorators.EdgePaintFunction;
import edu.uci.ics.jung.graph.decorators.EdgeShape;
import edu.uci.ics.jung.graph.decorators.EdgeStringer;
import edu.uci.ics.jung.graph.decorators.EdgeStrokeFunction;
import edu.uci.ics.jung.graph.decorators.GradientEdgePaintFunction;
import edu.uci.ics.jung.graph.decorators.NumberEdgeValue;
import edu.uci.ics.jung.graph.decorators.NumberEdgeValueStringer;

import edu.uci.ics.jung.graph.decorators.NumberVertexValue;

import edu.uci.ics.jung.graph.decorators.NumberVertexValueStringer;
import edu.uci.ics.jung.graph.decorators.PickableEdgePaintFunction;
import edu.uci.ics.jung.graph.decorators.UserDatumNumberEdgeValue;
import edu.uci.ics.jung.graph.decorators.UserDatumNumberVertexValue;
import edu.uci.ics.jung.graph.decorators.VertexAspectRatioFunction;
import edu.uci.ics.jung.graph.decorators.VertexFontFunction;
import edu.uci.ics.jung.graph.decorators.VertexPaintFunction;
import edu.uci.ics.jung.graph.decorators.VertexSizeFunction;
import edu.uci.ics.jung.graph.decorators.VertexStringer;
import edu.uci.ics.jung.graph.decorators.VertexStrokeFunction;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.UndirectedSparseEdge;
import edu.uci.ics.jung.graph.predicates.ContainsUserDataKeyVertexPredicate;
import edu.uci.ics.jung.graph.predicates.SelfLoopEdgePredicate;
import edu.uci.ics.jung.random.generators.BarabasiAlbertGenerator;
import edu.uci.ics.jung.utils.MutableDouble;
import edu.uci.ics.jung.utils.PredicateUtils;
import edu.uci.ics.jung.utils.TestGraphs;
import edu.uci.ics.jung.visualization.FRLayout;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.HasGraphLayout;
import edu.uci.ics.jung.visualization.Layout;
import edu.uci.ics.jung.visualization.PickSupport;
import edu.uci.ics.jung.visualization.PickedInfo;
import edu.uci.ics.jung.visualization.PickedState;
import edu.uci.ics.jung.visualization.PluggableRenderer;
import edu.uci.ics.jung.visualization.ShapePickSupport;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.transform.LayoutTransformer;
import edu.uci.ics.jung.visualization.transform.Transformer;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author unbekannt
 * @version 1.0
 */

public class VertexShapeImpl  extends AbstractVertexShapeFunction
       implements VertexSizeFunction, VertexAspectRatioFunction
{
    Hashtable  mAgentTypeList ;
    protected boolean stretch = false;
    protected boolean scale = false;
    protected boolean funny_shapes = false;

    public VertexShapeImpl( Hashtable pAgentTypeList )
    {
        this.mAgentTypeList = pAgentTypeList;
        //this.voltages = voltages;
        setSizeFunction(this);
        setAspectRatioFunction(this);
        /*
        this(new ConstantVertexSizeFunction(10),
         new ConstantVertexAspectRatioFunction(1.0f));
        */
    }

    public void setStretching(boolean stretch)
    {
        this.stretch = stretch;
    }

    public void setScaling(boolean scale)
    {
        this.scale = scale;
    }

    public void useFunnyShapes(boolean use)
    {
        this.funny_shapes = use;
    }

    public int getSize(Vertex v)
    {
        // 5 Stufe
        // Degree      Pixels
        // 0-- 2 ,     5
        // 3 - 5 ,     8
        // 6 --8 ,     10
        // 9 --11,     12
        // 12--16,     15
        // 17 --       18
        int j = v.degree();
        // Je größer ist der OutDegree, desto stärker ist sein Einfluss auf andere Agents

        if ( j <= 2 )
        {
           return 5;
        }
        else
        if ( j <= 5 )
        {
           return 8;
        }
        else
        if ( j <= 8 )
        {
           return 10;
        }
        else
        if ( j <= 11 )
        {
           return 12;
        }
        else
        if ( j <= 16 )
        {
           return 15;
        }
        else
        {
           return 18;
        }
    }

    public float getAspectRatio(Vertex v)
    {
        // ein Circle, wenn <1.0, ein Ellipse
        return 1.0f;
    }

    public Shape getShape(Vertex v)
    {
        String agentname =(String) v.getUserDatum("NAME");
        Object ob = new Object();
        if ( agentname == null )
        {
          // Default Shape:
          Rectangle2D sh = new Rectangle2D.Float();
          float width  = this.getSize(v);
          //float width  = 30;
          float height = width * this.getAspectRatio(v);
          float h_offset = -(width / 2);
          float v_offset = -(height / 2);
          sh.setFrame(h_offset, v_offset, width, height);
          ob =sh;
          try
          {
              throw new Exception("!!!!  Vertex Name is not defined !!!! ");
          }
          catch (Exception ex)
          {
              ex.printStackTrace();
          }
        }
        else
        {
              String agenttype = (String) this.mAgentTypeList.get( agentname );

              if ( agenttype == null )
              {
                System.out.println("AgentName=" + agentname );

                RoundRectangle2D sh = new RoundRectangle2D.Float();
                float width  = this.getSize(v);
                //float width  = 20;

                float height = width * this.getAspectRatio(v);
                float h_offset = -(width / 2);
                float v_offset = -(height / 2);
                sh.setFrame(h_offset, v_offset, width, height);
                ob= sh;

                /***
                ob = null;
                try
                {
                    throw new Exception("!!!!  Vertex " + agentname + "hat kein Typ !!!! ");
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                */
              }
              else
              if (agenttype.equals("I"))
              {
                  // Investor
                  Ellipse2D sh = new Ellipse2D.Float();
                  float width  = this.getSize(v);
                  //float width  = 10;
                  float height = width * this.getAspectRatio(v);
                  float h_offset = -(width / 2);
                  float v_offset = -(height / 2);
                  sh.setFrame(h_offset, v_offset, width, height);
                  ob = sh;
              }
              else
              if ( agenttype.equals("N") )
              {
                  // NoiseTrader
                  Rectangle2D sh = new Rectangle2D.Float();
                  float width  = this.getSize(v);
                  //float width  = 20;
                  float height = width * this.getAspectRatio(v);
                  float h_offset = -(width / 2);
                  float v_offset = -(height / 2);
                  sh.setFrame(h_offset, v_offset, width, height);
                  ob= sh;
              }
              else
              if ( agenttype.equals("B") )
              {
                   // Blank Agent
                  float width  = this.getSize(v);
                  //float width  = 20;
                  float height = width * this.getAspectRatio(v);

                  float h_offset = -(width / 2);
                  float v_offset = -(height / 2);
                  int x,y,r;
                  x =(int) h_offset;
                  y = (int)v_offset;
                  GeneralPath sh = this.getTripleAngle( x,y, 10 ) ;
                  ob = sh;

              }
              else
              if ( agenttype.equals("R") )  // Random Trader
              {

                float width  = this.getSize(v);
                //float width  = 20;
                float height = width * this.getAspectRatio(v);

                float h_offset = -(width / 2);
                float v_offset = -(height / 2);
                int x,y,r;
                x =(int) h_offset;
                y = (int)v_offset;
                GeneralPath sh = this.getStar( x,y, 10 ) ;
                ob = sh;

              }
              else
              {
                RoundRectangle2D sh = new RoundRectangle2D.Float();
                float width  = this.getSize(v);
                //float width  = 20;
                float height = width * this.getAspectRatio(v);
                float h_offset = -(width / 2);
                float v_offset = -(height / 2);
                sh.setFrame(h_offset, v_offset, width, height);
                ob= sh;
              }
        }
        return (Shape)ob;
    }

    private GeneralPath  getTripleAngle( int x, int y, int r )
    {

         /*******************************************
                             A




                      B             C
          *******************************************
          */

         // calculate the coordination of 3 Points:
         java.awt.Point PointA = new java.awt.Point();
         java.awt.Point PointB = new java.awt.Point();
         java.awt.Point PointC = new java.awt.Point();
         PointA.x = x;
         PointA.y = y - r;
         PointB.x = (int)(  x - r * Math.cos(  Math.PI / 6  ) );
         PointB.y = (int) ( y + r * Math.sin(  Math.PI / 6  ) );
         PointC.x = (int)(  x + r * Math.cos(  Math.PI / 6  ) );
         PointC.y = (int) ( y + r * Math.sin(  Math.PI / 6  ) );

         //System.out.println( "PointA: "  + PointA.toString() );
         //System.out.println( "PointB: "  + PointB.toString() );
         //System.out.println( "PointC: "  + PointC.toString() );
         GeneralPath pp = new GeneralPath ();
         pp.moveTo( PointA.x,  PointA.y);
         pp.lineTo( PointB.x,  PointB.y);
         pp.lineTo( PointC.x,  PointC.y);
         pp.closePath();
         return pp;
    }


    private GeneralPath  getStar( int x, int y, int r )
    {

        /*******************************************
                            A

                         B     H
                     C              G
                         D     F
                            E
         *******************************************
         *
         *  A->B->C->D->E->F->G->H->A
         *
         */

         // calculate the coordination of 8 Points:
         java.awt.Point PointA = new java.awt.Point();
         java.awt.Point PointB = new java.awt.Point();
         java.awt.Point PointC = new java.awt.Point();
         java.awt.Point PointD = new java.awt.Point();
         java.awt.Point PointE = new java.awt.Point();
         java.awt.Point PointF = new java.awt.Point();
         java.awt.Point PointG = new java.awt.Point();
         java.awt.Point PointH = new java.awt.Point();

         // change this value to adjust the shape of the Star ( 4 Angles )
         // Wenn WinkeladjustFactor = 4.0, It becomes a Rectangle
         double WinkeladjustFactor = 2.86;
         // Minimal 2.8
         // Maximal 3.3
         // Ideal: 3.0

         PointA.x = x;
         PointA.y = y - r;

         PointB.x = (int)(  x - r * Math.cos(  Math.PI / WinkeladjustFactor  ) * Math.sin(  Math.PI / WinkeladjustFactor ) );
         PointB.y = (int) ( y - r * Math.cos(  Math.PI / WinkeladjustFactor  ) * Math.cos(  Math.PI / WinkeladjustFactor  ));
         PointC.x =  x -r ;
         PointC.y =  y;

         PointD.x = PointB.x;
         PointD.y = (int) ( y + r * Math.cos(  Math.PI / WinkeladjustFactor  ) * Math.cos(  Math.PI / WinkeladjustFactor  ));

         PointE.x = x;
         PointE.y = y + r;

         PointF.x = (int)(  x + r * Math.cos(  Math.PI / WinkeladjustFactor  ) * Math.sin(  Math.PI / WinkeladjustFactor  ) );
         PointF.y = PointD.y;

         PointG.x =  x + r;
         PointG.y =  y;

         PointH.x =  PointF.x;
         PointH.y =  PointB.y;

         //System.out.println( "PointA: "  + PointA.toString() );
         //System.out.println( "PointB: "  + PointB.toString() );
         //System.out.println( "PointC: "  + PointC.toString() );
         GeneralPath pp = new GeneralPath ();
         pp.moveTo( PointA.x,  PointA.y);
         pp.lineTo( PointB.x,  PointB.y);
         pp.lineTo( PointC.x,  PointC.y);
         pp.lineTo( PointD.x,  PointD.y);
         pp.lineTo( PointE.x,  PointE.y);
         pp.lineTo( PointF.x,  PointF.y);
         pp.lineTo( PointG.x,  PointG.y);
         pp.lineTo( PointH.x,  PointH.y);
         pp.closePath();

         return pp;
    }


    public void setAgentTypeList(Hashtable pAgentTypeList)
    {
       this.mAgentTypeList =pAgentTypeList;
    }

}
