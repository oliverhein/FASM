package de.marketsim.gui.graph;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */
import de.marketsim.config.NetworkFileLoader;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

import java.io.IOException;
import java.util.*;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.Edge;

import edu.uci.ics.jung.graph.decorators.DefaultToolTipFunction;
import edu.uci.ics.jung.graph.decorators.EdgeShape;

//import edu.uci.ics.jung.graph.decorators.NumberVertexValue;
//import edu.uci.ics.jung.graph.decorators.UserDatumNumberVertexValue;
import edu.uci.ics.jung.graph.decorators.VertexPaintFunction;

import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.UndirectedSparseEdge;


import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;
import edu.uci.ics.jung.graph.impl.UndirectedSparseGraph;

import edu.uci.ics.jung.graph.impl.DirectedSparseVertex;
import edu.uci.ics.jung.visualization.FRLayout;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationModel;
import edu.uci.ics.jung.visualization.DefaultVisualizationModel;

import edu.uci.ics.jung.visualization.PersistentLayout;
import edu.uci.ics.jung.visualization.PersistentLayoutImpl;
import edu.uci.ics.jung.visualization.PluggableRenderer;

import edu.uci.ics.jung.visualization.contrib.KKLayout;
import edu.uci.ics.jung.visualization.contrib.CircleLayout;

import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
//import edu.uci.ics.jung.visualization.control.LayoutScalingControl;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;

import edu.uci.ics.jung.visualization.ShapePickSupport;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.VertexShapeFactory;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;

import edu.uci.ics.jung.utils.UserDataContainer;
import com.borland.jbcl.layout.*;

public class MyGraphPanel extends JPanel
{

  private GraphData  mCommunicationGraph = new GraphData();
  private int    ControlPanelHight = 120;

  private JPanel jConsolePanel;

  private JPanel jButtonPanel;
  private PanelGraphStatistic  mStatisticPanel;
  private JPanel jGraphPanel;

  //NetworkFileLoader mnetworkload = null;
  //VertexShapeFactory mVertexShapeFactory = new VertexShapeFactory();

  private Hashtable mActionList    = new Hashtable();

  // Hasttable containing the Agent Type
  // Key: Agent Name
  // Value: I--> Investor
  //        N--> NoiseTrader
  //        B--> BlankoAgent
  //        R--> RandomTrader

  private Hashtable mAgentTypeList = new Hashtable();

  VertexShapeImpl shapeimpl = new VertexShapeImpl( new Hashtable() ) ;
  VertexPaintFunctionImpl mVertexPaintFunctionImpl = new VertexPaintFunctionImpl( new Hashtable());

  /*
   * the graph
   */

  public Graph graph;

   /**
    * the name of the file where the layout is saved
    */

   String fileName;

    /**
     * Constructor with Network file name
     * create an instance of grpah
     * @param fileName: network file
     */

    public MyGraphPanel(String pFilename)
    {
          this.fileName = pFilename;
          // load network file
          this.loadnetwork();
          this.jbInit();
          this.generateGraph();
    }

    public MyGraphPanel( int pNormalNodes, Vector pCommunication, int pIndependNodes)
    {
          this.mCommunicationGraph = new GraphData(pNormalNodes, pIndependNodes);
          for (int i=0; i<pCommunication.size(); i++)
          {
               String ss = (String)pCommunication.elementAt(i);
               // Format  V1;V5;
               // V1 is Sender, V5 is Reciever
               int p= ss.indexOf(";");
               String sender = ss.substring(0,p);
               String receiver = ss.substring(p+1);
               mCommunicationGraph.addOneCommunication( new OneCommunication( sender, receiver )  );
          }
          this.jbInit();
          this.generateGraph();
  }

  private void jbInit() //throws Exception
  {
      JPanel jPanel1 = new JPanel();
      BorderLayout  myLayout  = new BorderLayout();
      this.setLayout( myLayout  );
  }

  void generateGraph()
  {
       // create Graph
       // this.graph   = new DirectedSparseGraph();

       //this.graph   = new UndirectedSparseGraph();

       this.graph   = new  edu.uci.ics.jung.graph.impl.SparseGraph();

       //edu.uci.ics.jung.graph.impl
       // add vertex
       for ( int i=0; i< this.mCommunicationGraph.mVertexList.length; i++)
       {
          this.graph.addVertex( this.mCommunicationGraph.mVertexList[i] );
       }

       // add Edges
       for ( int i=0; i< this.mCommunicationGraph.mCommunicationList.size(); i++)
       {
          OneCommunication onecomm = (OneCommunication) this.mCommunicationGraph.mCommunicationList.elementAt(i);
          // Format of Sender Receiver:  V1 .... V200
          int sender_vertex_index   = Integer.parseInt( onecomm.mSender.substring(1) ) - 1 ;
          int receiver_vertex_index = Integer.parseInt( onecomm.mReceiver.substring(1) ) - 1;
          // added a directed edge with arrow
          // graph.addEdge(new DirectedSparseEdge( this.mCommunicationGraph.mVertexList [ sender_vertex_index ], this.mCommunicationGraph.mVertexList [receiver_vertex_index ]));
          // added a undirected edge without arrow
          graph.addEdge(new UndirectedSparseEdge( this.mCommunicationGraph.mVertexList [ sender_vertex_index ], this.mCommunicationGraph.mVertexList [receiver_vertex_index ]));
          // System.out.println("one edge " + onecomm.mSender + "-->" + onecomm.mReceiver + " is added to graph ");
       }

       UnweightedShortestPath pathcalculator = new UnweightedShortestPath( this.graph );

       //final KKLayout layout = new KKLayout( this.graph, pathcalculator );

       //final KKLayout layout = new KKLayout( this.graph, pathcalculator );

    final KKLayout layout = new KKLayout( this.graph );

    //final FRLayout layout = new FRLayout( this.graph );

    // Das geht nicht !!!
    //final PersistentLayout layout = new PersistentLayoutImpl( new FRLayout(graph));

    // Das seiht sehr hässlich !!!
    //final CircleLayout layout = new CircleLayout(graph );


    // create Render
    PluggableRenderer mPR    = new PluggableRenderer();
    mPR.setVertexShapeFunction( this.shapeimpl                );
    mPR.setVertexPaintFunction( this.mVertexPaintFunctionImpl );
    mPR.setEdgeShapeFunction  ( new  EdgeShape.QuadCurve() );

    //Dimension preferredSize = new Dimension(200,200);
    //VisualizationModel vm1 = new DefaultVisualizationModel(layout);

    //final VisualizationViewer mVV = new VisualizationViewer( vm1, mPR, preferredSize );
    //this.mVV = new VisualizationViewer( vm1, this.mPR );

    final VisualizationViewer mVV = new VisualizationViewer( layout , mPR );

    mVV.setToolTipFunction(new DefaultToolTipFunction());

    //DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
    //mVV.setGraphMouse(gm);
    //mVV.setPickSupport( new ShapePickSupport());

    GraphZoomScrollPane mGraphZoomScrollPane = new GraphZoomScrollPane( mVV );
    mGraphZoomScrollPane.setName("Graph");
    //mGraphZoomScrollPane.setLocation(0, this.ControlPanelHight+1);
    //mGraphZoomScrollPane.setSize(500,300);

    this.jConsolePanel = new JPanel(new GridLayout(1,2));

    this.jButtonPanel = new JPanel();
    this.jButtonPanel.setLayout( new GridLayout(3,3)   );
    this.jButtonPanel.setBorder(BorderFactory.createTitledBorder("Control"));
    this.jButtonPanel.setPreferredSize(new Dimension(10, 80));

    final ScalingControl scaler = new CrossoverScalingControl();
    scaler.scale( mVV, 1.0f, mVV.getCenter() );

    JButton plus = new JButton("Zoom+");
    plus.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e) {
            scaler.scale( mVV, 1.1f, mVV.getCenter());
        }
    });

    JButton minus = new JButton("Zoom-");
    minus.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e) {
            scaler.scale( mVV, 0.85f, mVV.getCenter());
        }
    });

    this.jButtonPanel.add( new JLabel("Circle:Fundamental") );
    this.jButtonPanel.add( new JLabel("Rectangle:Trend") );
    JLabel ll = new JLabel("Red: Sale");
    ll.setForeground(Color.red);

    this.jButtonPanel.add( ll );

    ll = new JLabel("Blue: Buy");
    ll.setForeground(Color.blue);
    this.jButtonPanel.add( ll );

    ll =new JLabel("Yellow:Wait");
    ll.setForeground( Color.YELLOW );
    this.jButtonPanel.add( ll );

    ll =new JLabel("Order not executed") ;
    ll.setForeground( SystemColor.inactiveCaption );

    this.jButtonPanel.add( ll);
    this.jButtonPanel.add( plus );
    this.jButtonPanel.add( minus );


    this.jGraphPanel = new JPanel(new GridLayout(1,1));
    this.jGraphPanel.add( mGraphZoomScrollPane );

    this.mStatisticPanel = new PanelGraphStatistic();
    this.mStatisticPanel.setBorder(BorderFactory.createTitledBorder("Statistic"));

    this.jConsolePanel.add( this.jButtonPanel, BorderLayout.WEST );
    this.jConsolePanel.add( this.mStatisticPanel, BorderLayout.EAST );

    this.add( this.jConsolePanel, BorderLayout.NORTH );
    this.add( this.jGraphPanel, BorderLayout.CENTER );

  }



  void loadnetwork()
  {
    NetworkFileLoader mnetworkload = new NetworkFileLoader( this.fileName );
    int JJ;
    try
    {
        JJ = mnetworkload.checkAgentNumber();
        mCommunicationGraph = new GraphData( JJ, 0 );
    }
    catch (Exception ex)
    {
        ex.printStackTrace();
        return;
    }

    try
    {
        mnetworkload.processnetworkfile();
    }
    catch (Exception ex)
    {
        ex.printStackTrace();
        return;
    }

    Vector VertexPair = mnetworkload.getCommunicationPairList();

    // Format  V1;V2;
    // V1 is sender
    // V2 is receiver
    for (int i=0; i<VertexPair.size(); i++)
    {
         String ss = (String) VertexPair.elementAt(i);
         int p= ss.indexOf(";");
         String sender = ss.substring(0,p);
         String receiver = ss.substring(p+1);
         mCommunicationGraph.addOneCommunication( new OneCommunication( sender, receiver )  );
    }

    System.out.println("network is loaded");
    System.out.println("network hast " + mCommunicationGraph.getTotalVertexNumber() +" Vertex, " + mCommunicationGraph.mCommunicationList.size()+ " Edges" );

  }

  public void updateState()
  {
       this.jGraphPanel.repaint();
  }

  public void setCommunicationPairList(Vector pCommunicationPairList)
  {
       mCommunicationGraph.mCommunicationList.clear();
       for (int i=0; i<pCommunicationPairList.size(); i++)
       {
         String ss = (String) pCommunicationPairList.elementAt(i);
         int p= ss.indexOf(";");
         String sender = ss.substring(0,p);
         String receiver = ss.substring(p+1);
         if( mCommunicationGraph.mCommunicationList.size()>0)
         {
           for (int n = 0; n<mCommunicationGraph.mCommunicationList.size();n++)
           {
             OneCommunication onecomm = new OneCommunication( sender, receiver );
             if ( !onecomm.equals((OneCommunication)mCommunicationGraph.mCommunicationList.elementAt(n)))
             {
               mCommunicationGraph.addOneCommunication( onecomm  );
             }
           }

         }
       }

    this.graph.removeAllEdges();
    // add new Edges
    for ( int i=0; i< this.mCommunicationGraph.mCommunicationList.size(); i++)
    {
       OneCommunication onecomm = (OneCommunication) this.mCommunicationGraph.mCommunicationList.elementAt(i);
       // Format of Sender Receiver:  V1 .... V200
       int sender_vertex_index   = Integer.parseInt( onecomm.mSender.substring(1) ) - 1 ;
       int receiver_vertex_index = Integer.parseInt( onecomm.mReceiver.substring(1) ) - 1;
       //graph.addEdge(new DirectedSparseEdge( this.mCommunicationGraph.mVertexList [ sender_vertex_index ], this.mCommunicationGraph.mVertexList [receiver_vertex_index ]));

       graph.addEdge(new UndirectedSparseEdge( this.mCommunicationGraph.mVertexList [ sender_vertex_index ], this.mCommunicationGraph.mVertexList [receiver_vertex_index ]));

       //System.out.println("one edge " + onecomm.mSender + "-->" + onecomm.mReceiver + " is added to graph ");
    }

  }

  public void setActionList(Hashtable pActionList)
  {
     this.mActionList = pActionList;
     this.mVertexPaintFunctionImpl.setActionList( pActionList );
  }

  public void setAgentTypeList(Hashtable pAgentTypeList)
  {
     this.shapeimpl.setAgentTypeList( pAgentTypeList );
  }

  public Hashtable CreateDemoActionList(int pVertextNumer)
  {
      Hashtable actionlist = new Hashtable();
      Random rd = new Random();
      for ( int i=0; i<pVertextNumer; i++)
      {
        String action = null;
        int k = rd.nextInt(30);
        if ( k % 3 == 0 )
        {
           action="B";
        }
        else
        if ( k % 3 == 1 )
        {
          action="S";
        }
        else
        {
          action="W";
        }
        actionlist.put( "V"+(i+1), action );
        System.out.println("V"+(i+1)+ "  "+ action);
    }
    return actionlist;
  }

  public Hashtable CreateDemoTypeDistribution(int pVertextNumer)
  {
    Hashtable agenttypelist = new Hashtable();
    Random rd = new Random();
    for ( int i=0; i<pVertextNumer; i++)
    {
      String type = "I";
      int k = rd.nextInt(30);
      if ( k % 3 == 0 )
      {
         type="I";
      }
      else
      {
          type="N";
      }
      agenttypelist.put( "V"+(i+1), type );
    }
    return agenttypelist;
  }



  void checkGraph()
  {
     Set vertexlist = this.graph.getVertices();
     Iterator  it = vertexlist.iterator();
     int i=0;
     while ( it.hasNext() )
     {
       i++;
       Vertex vt = (Vertex) it.next();
       System.out.println( i + ".Vertex " +  vt.getUserDatum("NAME"));
     }

     Set edgelist = this.graph.getEdges();
     it = edgelist.iterator();
     i=0;
     while ( it.hasNext() )
     {
       i++;
       Edge ed = (Edge) it.next();
       System.out.println( i + ".Edge " );
     }
  }

  public JPanel  getPureGraphPanel()
  {
     return this.jGraphPanel;
  }

  /**
   * set the total number of InvestorAgent
   * @param pNumber
   */

  public void setInvestor(int pNumber)
  {
     this.mStatisticPanel.setInvestor(pNumber);
  }

  /**
   * set the change of number of InvestorAgent
   * +: Increase
   * -: Decrease
   * @param pNumber
   */

  public void setInvestorChanged(int pNumber)
  {
     this.mStatisticPanel.setInvestorChanged( pNumber);
  }

  /**
   * set the total number of NoiseTraderAgent
   * @param pNumber
   */

  public void setNoiseTrader(int pNumber)
  {
     this.mStatisticPanel.setNoiseTrader( pNumber);
  }

  /**
   * set the change of number of NoiseTraderAgent
   * +: Increase
   * -: Decrease
   * @param pNumber
   */

  public void setNoiseTraderChanged(int pNumber)
  {
     this.mStatisticPanel.setNoiseTraderChanged( pNumber);
  }

  /**
   * set the total number of BlankoAgent
   * @param pNumber
   */
  public void setBlanko(int pNumber)
  {
     this.mStatisticPanel.setBlanko(pNumber);
  }

  /**
   * set the Number of BlankoAgent which becomes active
   * @param pNumber
   */

  public void setBlankoJustActivated(int pNumber)
  {
     this.mStatisticPanel.setBlankoJustActivated(pNumber);
  }

  /**
   * set the Agent Name list which become active
   * The namelist is saved into jTF_BlankoActive as a ToolTipText
   * when the mouse moves on it.
   * @param pNameList
   */

  public void setBlankoActivatedAgent(String pNameList)
  {
     this.mStatisticPanel.setBlankoActivatedAgent(pNameList);
  }

  /**
   * set the Number of BlankoAgent which becomes Inactive
   * @param pNumber
   */

  public void setBlankoDeactivated(int pNumber)
  {
     this.mStatisticPanel.setBlankoDeactivated( pNumber);
  }

  /**
   * set the Agent Name list which become Inactive
   * The namelist is saved into jTF_BlankoInactive as a ToolTipText
   * when the mouse moves on it.
   * @param pNameList
   */

  public void setBlankoInactivatedAgent(String pNameList)
  {
     this.mStatisticPanel.setBlankoDeactivatedAgent(pNameList);
  }




}


