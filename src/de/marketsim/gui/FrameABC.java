package de.marketsim.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class FrameABC extends JFrame {
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JButton jButton1 = new JButton();
  private JTextArea jTextArea1 = new JTextArea();
  private JTextArea jTextArea2 = new JTextArea();

  public FrameABC() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    this.getContentPane().setLayout(null);
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    jScrollPane1.setBounds(new Rectangle(2, 73, 392, 271));
    jButton1.setBounds(new Rectangle(9, 24, 149, 29));
    jButton1.setText("jButton1");
    jButton1.addActionListener(new FrameABC_jButton1_actionAdapter(this));
    jTextArea1.setText("jTextArea1");
    jTextArea1.setBounds(new Rectangle(189, 17, 132, 50));
    jTextArea2.setText("jTextArea2");
    this.getContentPane().add(jScrollPane1, null);
    jScrollPane1.getViewport().add(jTextArea2, null);
    this.getContentPane().add(jButton1, null);
    this.getContentPane().add(jTextArea1, null);
    this.setIconImage( Toolkit.getDefaultToolkit().getImage("C:/fasm/fasm.gif"));
  }

  public static void main(String args[] )
  {
      FrameABC  abc = new FrameABC();
      abc.setSize(300,400);
      abc.setVisible(true);
  }

  void jButton1_actionPerformed(ActionEvent e)
  {

       /*
       this.jPanel1.setSize(300,900);
       this.jPanel1.setLayout( null );

    */

       for (int i=0; i<30; i++)
       {
       /*TextField tf = new TextField();
       tf.setSize(100,30);
       tf.setBounds(new Rectangle(9, 20 + i*20, 100, 18));
       this.jPanel1.add( tf ); */

         this.jTextArea2.append("AAAAAAAAAAAAAA\r\n");

       }

  }


}

class FrameABC_jButton1_actionAdapter implements java.awt.event.ActionListener {
  private FrameABC adaptee;

  FrameABC_jButton1_actionAdapter(FrameABC adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton1_actionPerformed(e);
  }
}