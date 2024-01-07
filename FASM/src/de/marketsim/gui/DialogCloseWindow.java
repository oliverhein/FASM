/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author
 * @version 1.0
 */

package de.marketsim.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class DialogCloseWindow extends JDialog
{
  JPanel panel1 = new JPanel();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();

  public DialogCloseWindow(Frame frame, String title, boolean modal)
  {
    super(frame, title, modal);
    try
    {
      jbInit();
      pack();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }

  public DialogCloseWindow()
  {
    this(null, "", false);
  }
  void jbInit() throws Exception
  {
    panel1.setLayout(null);
    panel1.setBorder(BorderFactory.createLineBorder(Color.black));
    panel1.setBounds(new Rectangle(3, 12, 376, 149));
    this.getContentPane().setLayout(null);
    jButton1.setText("JA");
    jButton1.setBounds(new Rectangle(72, 57, 109, 46));
    jButton1.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        jButton1_actionPerformed(e);
      }
    });
    jButton2.setText("Nein");
    jButton2.setBounds(new Rectangle(209, 50, 103, 46));
    jButton2.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        jButton2_actionPerformed(e);
      }
    });
    this.getContentPane().add(panel1, null);
    panel1.add(jButton2, null);
    panel1.add(jButton1, null);
  }

  void jButton1_actionPerformed(ActionEvent e)
  {

  }

  void jButton2_actionPerformed(ActionEvent e)
  {

  }
}