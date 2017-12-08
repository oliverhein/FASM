package de.marketsim.gui.graph;

import java.awt.*;
import com.borland.jbcl.layout.*;
import javax.swing.*;

/**
 * <p>Überschrift: FASM Frankfurt Articial Simulation Market</p>
 * <p>Beschreibung: Mircomarket Simulator </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class Frame1 extends JFrame {
  private JButton jButton1 = new JButton();
  private JButton jButton2 = new JButton();
  private JButton jButton3 = new JButton();
  private JButton jButton4 = new JButton();
  private GridLayout gridLayout1 = new GridLayout(2,2);

  public Frame1() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public static void main(String[] args) {
    Frame1 frame1 = new Frame1();
  }
  private void jbInit() throws Exception {
    jButton1.setText("jButton1");
    this.getContentPane().setLayout(gridLayout1);
    jButton2.setText("jButton2");
    jButton3.setText("jButton3");
    jButton4.setText("jButton4");
    this.getContentPane().add(jButton1, null);
    this.getContentPane().add(jButton2, null);
    this.getContentPane().add(jButton3, null);
    this.getContentPane().add(jButton4, null);
  }
}