package de.marketsim.test;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ScrollPanel extends JFrame {
  private JButton jBAdd = new JButton();
  private JScrollPane NetwrokEditor = new JScrollPane();

  private int yy = 0;
  private int xx = 10;
  private JPanel jPanel1 = new JPanel();
  private JButton jBCheck = new JButton();
  private JTextArea jTextArea1 = new JTextArea();

  public ScrollPanel() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public static void main(String[] args) {
    ScrollPanel pp = new ScrollPanel();
    pp.setSize(300,500);
    pp.setVisible(true);

  }
  private void jbInit() throws Exception {
    jBAdd.setBounds(new Rectangle(12, 11, 90, 27));
    jBAdd.setText("Add");
    jBAdd.addActionListener(new ScrollPanel_jBAdd_actionAdapter(this));
    this.getContentPane().setLayout(null);
    NetwrokEditor.setBounds(new Rectangle(17, 178, 392, 169));
    jPanel1.setLayout(null);
    jPanel1.setBorder(BorderFactory.createLineBorder(Color.black));
    jPanel1.setBounds(new Rectangle(144, 27, 238, 115));
    jBCheck.setBounds(new Rectangle(14, 56, 87, 27));
    jBCheck.setText("check");
    jBCheck.addActionListener(new ScrollPanel_jBCheck_actionAdapter(this));
    jTextArea1.setText("jTextArea1");
    this.getContentPane().add(jBAdd, null);
    this.getContentPane().add(NetwrokEditor, null);
    NetwrokEditor.getViewport().add(jTextArea1, null);
    this.getContentPane().add(jBCheck, null);
    this.getContentPane().add(jPanel1, null);
  }

  void jBAdd_actionPerformed(ActionEvent e)
  {
       /*
       JButton jb = new JButton("Test");
       this.jPanel1. add( jb );
       jb.setSize(100,20);
       jb.setLocation( xx, yy);
       yy = yy + 25;
       if ( yy >300 )
       {
           this.jPanel1.setSize( this.jPanel1.getWidth(), yy  );
       }
    */

    yy = this.jTextArea1.getHeight();
    this.jTextArea1.setSize( this.jTextArea1.getWidth(), yy + 100 );
    this.NetwrokEditor.repaint();

  }

  void jBCheck_actionPerformed(ActionEvent e) {

    System.out.println( "NetworkPanel.height= " + this.NetwrokEditor.getHeight() );
    //System.out.println( "TextEditor.height= " + this.jTextArea1.getHeight() );

    System.out.println( "jPanel.height= " + this.jPanel1.getHeight() );

  }
}

class ScrollPanel_jBAdd_actionAdapter implements java.awt.event.ActionListener {
  private ScrollPanel adaptee;

  ScrollPanel_jBAdd_actionAdapter(ScrollPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBAdd_actionPerformed(e);
  }
}

class ScrollPanel_jBCheck_actionAdapter implements java.awt.event.ActionListener {
  private ScrollPanel adaptee;

  ScrollPanel_jBCheck_actionAdapter(ScrollPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBCheck_actionPerformed(e);
  }
}