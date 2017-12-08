package de.marketsim.test;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class HTMLFrame extends JFrame
{
  JTextField jTextField1 = new JTextField();
  JButton jBExec = new JButton();
  JButton jButton1 = new JButton();

  public HTMLFrame()
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
  public static void main(String[] args)
  {
    HTMLFrame pp = new HTMLFrame();
    pp.setSize(300,200);
    pp.setVisible(true);

  }
  private void jbInit() throws Exception
  {
    jTextField1.setText("jTextField1");
    jTextField1.setBounds(new Rectangle(90, 43, 190, 32));
    this.getContentPane().setLayout(null);
    jBExec.setBounds(new Rectangle(91, 87, 133, 38));
    jBExec.setText("Exec");
    jBExec.addActionListener(new HTMLFrame_jBExec_actionAdapter(this));
    jButton1.setBounds(new Rectangle(93, 151, 128, 46));
    jButton1.setText("Quit");
    jButton1.addActionListener(new HTMLFrame_jButton1_actionAdapter(this));
    this.getContentPane().add(jTextField1, null);
    this.getContentPane().add(jBExec, null);
    this.getContentPane().add(jButton1, null);
  }

  void jBExec_actionPerformed(ActionEvent e)
  {
      String program = this.jTextField1.getText();
      String param[] = new String[1];
      param[0] = "d:/abc.html";
      try
      {
        Runtime.getRuntime().exec( program );
      }
      catch (Exception ex)
      {

      }
  }

  void jButton1_actionPerformed(ActionEvent e)
  {
     System.exit(0);
  }
}

class HTMLFrame_jBExec_actionAdapter implements java.awt.event.ActionListener
{
  HTMLFrame adaptee;

  HTMLFrame_jBExec_actionAdapter(HTMLFrame adaptee)
  {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e)
  {
    adaptee.jBExec_actionPerformed(e);
  }
}

class HTMLFrame_jButton1_actionAdapter implements java.awt.event.ActionListener
{
  HTMLFrame adaptee;

  HTMLFrame_jButton1_actionAdapter(HTMLFrame adaptee)
  {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e)
  {
    adaptee.jButton1_actionPerformed(e);
  }
}