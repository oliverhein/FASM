package de.marketsim.install;

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

public class AcceptDialog extends JDialog {
  private JPanel panel1 = new JPanel();
  private JTextArea jTextArea1 = new JTextArea();
  private JButton jBNext = new JButton();
  private JButton JBBack = new JButton();

  public int  mNextChoosed = 1;
  public int  mBackChoosed = 2;

  private int mUserChoice = mNextChoosed;

  public AcceptDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public AcceptDialog() {
    this(null, "", false);
  }
  private void jbInit() throws Exception {
    panel1.setLayout(null);
    jTextArea1.setBorder(BorderFactory.createLineBorder(Color.black));
    jTextArea1.setText("Software description:");
    jTextArea1.setBounds(new Rectangle(7, 8, 387, 203));
    jBNext.setBounds(new Rectangle(124, 241, 125, 34));
    jBNext.setText("Next");
    jBNext.addActionListener(new AcceptDialog_jBNext_actionAdapter(this));
    JBBack.setBounds(new Rectangle(256, 241, 115, 33));
    JBBack.setText("Back");
    JBBack.addActionListener(new AcceptDialog_JBBack_actionAdapter(this));
    this.setResizable(false);
    getContentPane().add(panel1);
    panel1.add(jTextArea1, null);
    panel1.add(JBBack, null);
    panel1.add(jBNext, null);

  }

  void jBNext_actionPerformed(ActionEvent e)
  {
     this.mUserChoice = this.mNextChoosed;
     this.hide();
  }

  void JBBack_actionPerformed(ActionEvent e)
  {
    this.mUserChoice = this.mBackChoosed;
    this.hide();
  }

  public int getUserChoice()
  {
    return this.mUserChoice;
  }

}

class AcceptDialog_jBNext_actionAdapter implements java.awt.event.ActionListener {
  private AcceptDialog adaptee;

  AcceptDialog_jBNext_actionAdapter(AcceptDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBNext_actionPerformed(e);
  }
}

class AcceptDialog_JBBack_actionAdapter implements java.awt.event.ActionListener {
  private AcceptDialog adaptee;

  AcceptDialog_JBBack_actionAdapter(AcceptDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.JBBack_actionPerformed(e);
  }
}