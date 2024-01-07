package de.marketsim.gui.graph;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.Vector;
import java.util.Hashtable;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseVertex;
import edu.uci.ics.jung.graph.impl.UndirectedSparseVertex;
import edu.uci.ics.jung.graph.impl.*;

import de.marketsim.gui.graph.VertexInfoContainer;
import de.marketsim.gui.graph.OneCommunication;

public class GraphData
{
  private int       mTotalVertexNumber       = 0 ;
  private int       mNormalVertexNumber      = 0 ;
  private int       mIndependVertexNumber    = 0 ;

  public  Vector    mCommunicationList  = new Vector();
  public  Vertex[]  mVertexList         = new Vertex[0];

  public  GraphData()
  {
      // empty graph, no nodes, no edges
  }

  public  GraphData(int pNormalVertexNumber, int pIndependVertexNumber )
  {
    // empty graph, only nodes
    this.mTotalVertexNumber = pNormalVertexNumber + pIndependVertexNumber ;
    this.mVertexList   = new Vertex[this.mTotalVertexNumber];

    // !!!! Important !!!
    // Vertex Name is so defined:  V1,V2,V3,V4

    for ( int i=0; i< pNormalVertexNumber; i++)
    {
       //mVertexList[i] = new DirectedSparseVertex();
       mVertexList[i] = new UndirectedSparseVertex();
       //mVertexList[i] = new edu.uci.ics.jung.graph.impl.SimpleSparseVertex();
       String vname = "V"+(i+1);
       VertexInfoContainer myInfoContainer = new VertexInfoContainer( vname );
       mVertexList[i].importUserData( myInfoContainer );
    }

    // RandomTrader
    for ( int i=0; i< pIndependVertexNumber; i++)
    {
      mVertexList[i + pNormalVertexNumber ]   = new UndirectedSparseVertex();
      //mVertexList[i] = new edu.uci.ics.jung.graph.impl.SimpleSparseVertex();
      String vname = "RandomTrader"+(i + 1);
      VertexInfoContainer myInfoContainer = new VertexInfoContainer( vname );
      mVertexList[ i + pNormalVertexNumber ].importUserData( myInfoContainer );
    }

  }

  public  void addOneCommunication( OneCommunication pComm)
  {

    mCommunicationList.add( pComm );
  }

  public void reset()
  {
      this.mCommunicationList.clear();
      this.mTotalVertexNumber=0;
      this.mVertexList = new Vertex[0];
  }

  public void setVertexNumber(int pNormalVertexNumber, int pIndependVertexNumber)
  {
    this.mTotalVertexNumber = pNormalVertexNumber  + pIndependVertexNumber;
    mVertexList        = new Vertex[this.mTotalVertexNumber];
    for ( int i=0; i<pNormalVertexNumber; i++)
    {
       //mVertexList[i] = new DirectedSparseVertex();
       mVertexList[i] = new UndirectedSparseVertex();
       String vname = "V"+(i+1);
       VertexInfoContainer myInfoContainer = new VertexInfoContainer( vname );
       mVertexList[i].importUserData( myInfoContainer );
    }

    for ( int i=0; i< pIndependVertexNumber; i++)
    {
      mVertexList[i + pNormalVertexNumber ]   = new UndirectedSparseVertex();
      //mVertexList[i] = new edu.uci.ics.jung.graph.impl.SimpleSparseVertex();
      String vname = "RANDOM"+(i + 1);
      VertexInfoContainer myInfoContainer = new VertexInfoContainer( vname );
      mVertexList[ i + pNormalVertexNumber ].importUserData( myInfoContainer );
    }

  }

  public int getNormalVertexNumber()
  {
    return this.mNormalVertexNumber;
  }

  public int getIndependVertexNumber()
  {
    return this.mIndependVertexNumber;
  }

  public int getTotalVertexNumber()
  {
    return this.mTotalVertexNumber;
  }

}

