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

public class Frame1 extends JFrame
{
  private JCheckBox jCheckBox1 = new JCheckBox();

  public Frame1()
  {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public static void main(String[] args)
  {
    Frame1 frame1 = new Frame1();
  }
  private void jbInit() throws Exception {
    jCheckBox1.setText("jCheckBox1");
    jCheckBox1.setBounds(new Rectangle(103, 85, 71, 42));
    this.getContentPane().setLayout(null);
    this.getContentPane().add(jCheckBox1, null);
  }
}