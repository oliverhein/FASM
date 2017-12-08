package de.marketsim.gui.graph;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class GraphTestor extends JFrame
{
  private JLabel jLabel1 = new JLabel();
  private JTextField jTextField1 = new JTextField();
  private JButton jBDisplay = new JButton();
  private JButton jBExit   = new JButton();
  private JButton jBSetAction = new JButton();
  private JButton jBChangeType = new JButton();

  private AgentGraphStatusFrame  mGraphFrame = null;
  JButton jBChangeEdge = new JButton();
  JLabel jLabelTest = new JLabel();


  public GraphTestor()
  {
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception
  {
    jLabel1.setText("Network");
    jLabel1.setBounds(new Rectangle(4, 8, 63, 29));
    this.getContentPane().setLayout(null);
    jTextField1.setText("C:/FASM/run/network25.txt");  // Default
    jTextField1.setBounds(new Rectangle(80, 8, 305, 27));
    jBDisplay.setBounds(new Rectangle(11, 44, 113, 27));
    jBDisplay.setText("Display");
    jBDisplay.addActionListener(new GraphTestor_jBDisplay_actionAdapter(this));
    jBExit.setBounds(new Rectangle(129, 45, 89, 29));
    jBExit.setText("Exit");
    jBExit.addActionListener(new GraphTestor_jBExit_actionAdapter(this));
    jBSetAction.setBounds(new Rectangle(11, 80, 204, 23));
    jBSetAction.setText("Change Action");
    jBSetAction.addActionListener(new GraphTestor_jBSetAction_actionAdapter(this));
    jBChangeType.setBounds(new Rectangle(10, 111, 206, 26));
    jBChangeType.setText("Change type");
    jBChangeType.addActionListener(new GraphTestor_jBChangeType_actionAdapter(this));
    jBChangeEdge.setBounds(new Rectangle(11, 149, 203, 24));
    jBChangeEdge.setText("change edge");
    jBChangeEdge.addActionListener(new GraphTestor_jBChangeEdge_actionAdapter(this));
    jLabelTest.setForeground(Color.red);
    jLabelTest.setText("jLabel2");
    jLabelTest.setBounds(new Rectangle(249, 47, 119, 25));
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(jTextField1, null);
    this.getContentPane().add(jBSetAction, null);
    this.getContentPane().add(jBDisplay, null);
    this.getContentPane().add(jBExit, null);
    this.getContentPane().add(jBChangeType, null);
    this.getContentPane().add(jBChangeEdge, null);
    this.getContentPane().add(jLabelTest, null);
  }

  void jBDisplay_actionPerformed(ActionEvent e)
  {
    mGraphFrame = new AgentGraphStatusFrame( this.jTextField1.getText() );
    mGraphFrame.setSize(400,400);
    mGraphFrame.show();
  }

  void jBExit_actionPerformed(ActionEvent e)
  {
     System.exit(0);
  }

  public static void main(String[] args)
  {
     GraphTestor  gg = new GraphTestor();
     gg.setSize( 500,600 );
     gg.setVisible(true);
  }

  void jBSetAction_actionPerformed(ActionEvent e)
  {

    if ( this.mGraphFrame != null )
    {
       Hashtable hh = this.mGraphFrame.CreateDemoActionList(25);
       this.mGraphFrame.setVisible(false);
       this.mGraphFrame.setActionList( hh );
       this.mGraphFrame.setVisible(true);
    }

  }

  void jBChangeType_actionPerformed(ActionEvent e)
  {
    if ( mGraphFrame != null )
    {
       Hashtable hh = this.mGraphFrame.CreateDemoTypeDistribution(25);
       this.mGraphFrame.setVisible(false);
       this.mGraphFrame.setAgentTypeList( hh );
       this.mGraphFrame.setVisible(true);
    }
  }

  void jBChangeEdge_actionPerformed(ActionEvent e)
  {
      Vector pCommList = new Vector();
      pCommList.add("V1;V2");
      pCommList.add("V1;V4");
      pCommList.add("V4;V6");
      pCommList.add("V2;V17");
      this.mGraphFrame.setCommunicationPairList(pCommList);

  }
}

class GraphTestor_jBDisplay_actionAdapter implements java.awt.event.ActionListener
{
  GraphTestor adaptee;

  GraphTestor_jBDisplay_actionAdapter(GraphTestor adaptee)
  {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e)
  {
    adaptee.jBDisplay_actionPerformed(e);
  }
}

class GraphTestor_jBExit_actionAdapter implements java.awt.event.ActionListener
{
  GraphTestor adaptee;

  GraphTestor_jBExit_actionAdapter(GraphTestor adaptee)
  {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e)
  {
    adaptee.jBExit_actionPerformed(e);
  }
}

class GraphTestor_jBSetAction_actionAdapter implements java.awt.event.ActionListener {
  private GraphTestor adaptee;

  GraphTestor_jBSetAction_actionAdapter(GraphTestor adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBSetAction_actionPerformed(e);
  }
}

class GraphTestor_jBChangeType_actionAdapter implements java.awt.event.ActionListener {
  private GraphTestor adaptee;

  GraphTestor_jBChangeType_actionAdapter(GraphTestor adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBChangeType_actionPerformed(e);
  }
}

class GraphTestor_jBChangeEdge_actionAdapter implements java.awt.event.ActionListener
{
  GraphTestor adaptee;

  GraphTestor_jBChangeEdge_actionAdapter(GraphTestor adaptee)
  {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e)
  {
    adaptee.jBChangeEdge_actionPerformed(e);
  }
}