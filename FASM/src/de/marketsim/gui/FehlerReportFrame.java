package de.marketsim.gui;

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

public class FehlerReportFrame extends JFrame {
  private JLabel jLFehlerText = new JLabel();
  private JButton jBClose = new JButton();

  public FehlerReportFrame()
  {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    this.setSize(400, 220);
    this.setVisible(true);
  }

  public void setFehlertext(String pFehler)
  {
    this.jLFehlerText.setText(pFehler);
  }

  public static void main(String[] args) {
    FehlerReportFrame fehlerReportFrame = new FehlerReportFrame();
  }
  private void jbInit() throws Exception {
    jLFehlerText.setHorizontalAlignment(SwingConstants.CENTER);
    jLFehlerText.setText("jLabel1");
    jLFehlerText.setBounds(new Rectangle(18, 28, 367, 72));
    this.getContentPane().setLayout(null);
    jBClose.setBounds(new Rectangle(121, 119, 151, 46));
    jBClose.setText("Close");
    jBClose.addActionListener(new FehlerReportFrame_jBClose_actionAdapter(this));
    this.getContentPane().add(jLFehlerText, null);
    this.getContentPane().add(jBClose, null);
  }

  void jBClose_actionPerformed(ActionEvent e)
  {
      this.setVisible(false);
  }
}

class FehlerReportFrame_jBClose_actionAdapter implements java.awt.event.ActionListener {
  private FehlerReportFrame adaptee;

  FehlerReportFrame_jBClose_actionAdapter(FehlerReportFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBClose_actionPerformed(e);
  }
}