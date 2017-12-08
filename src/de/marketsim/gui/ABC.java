package de.marketsim.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ABC extends JFrame
{
  private JTextArea jTA1 = new JTextArea();
  private JTextField jTF1 = new JTextField();
  private JButton jBEXIT = new JButton();

  public ABC()
  {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void setSSS(String ss)
  {
      this.jTF1.setText(  "Length=" + ss.length() );
      this.jTA1.setText( ss );

  }

  public static void main(String[] args)
  {
    String ss1 = args[0];

    ABC pp = new ABC();
    pp.setSize(400,300);
    pp.setVisible(true);
    pp.setSSS( ss1 );

  }
  private void jbInit() throws Exception {
    jTA1.setText("jTextArea1");
    jTA1.setBounds(new Rectangle(14, 86, 403, 149));
    this.getContentPane().setLayout(null);
    jTF1.setText("jTextField1");
    jTF1.setBounds(new Rectangle(22, 31, 141, 28));
    jBEXIT.setBounds(new Rectangle(18, 251, 94, 25));
    jBEXIT.setText("EXIT");
    jBEXIT.addActionListener(new ABC_jBEXIT_actionAdapter(this));
    this.getContentPane().add(jTA1, null);
    this.getContentPane().add(jTF1, null);
    this.getContentPane().add(jBEXIT, null);
  }

  void jBEXIT_actionPerformed(ActionEvent e)
  {
      System.exit(0);
  }
}

class ABC_jBEXIT_actionAdapter implements java.awt.event.ActionListener {
  private ABC adaptee;

  ABC_jBEXIT_actionAdapter(ABC adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBEXIT_actionPerformed(e);
  }
}