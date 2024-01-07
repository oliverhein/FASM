package de.marketsim.gui;

import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class Test extends JDialog {
  private JButton jButton1 = new JButton();

  public static void main(String args[])
  {
    Test tt = new Test(null,"ttt",true);
    tt.setSize(400,200);
    tt.setVisible(true);
  }

  public Test(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public Test() {
    this(null, "", false);
  }
  private void jbInit() throws Exception {
    jButton1.setBounds(new Rectangle(50, 34, 214, 48));
    jButton1.setText("jButton1");
    jButton1.addActionListener(new Test_jButton1_actionAdapter(this));
    this.getContentPane().setLayout(null);
    this.getContentPane().add(jButton1, null);


  }

  void jButton1_actionPerformed(ActionEvent e)
  {
    this.getGraphics().drawString("Hallo", 10,200 );

  }
}

class Test_jButton1_actionAdapter implements java.awt.event.ActionListener {
  private Test adaptee;

  Test_jButton1_actionAdapter(Test adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton1_actionPerformed(e);
  }
}