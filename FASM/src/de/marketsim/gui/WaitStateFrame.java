package de.marketsim.gui;

import java.awt.*;
import javax.swing.*;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class WaitStateFrame extends JFrame {
  private JLabel jLabel1 = new JLabel();

  public WaitStateFrame() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 16));
    jLabel1.setText("Data is being processed, please wait");
    jLabel1.setBounds(new Rectangle(56, 36, 306, 68));
    this.getContentPane().setLayout(null);
    this.getContentPane().add(jLabel1, null);

    int screen_width =  (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    int screen_height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 5;
    int ww = 400;
    int hh = 150;
    this.setLocation( (screen_width-ww)/2, (screen_height-hh)/2);
    this.setSize(ww, hh);
    this.setVisible(true);
  }
}