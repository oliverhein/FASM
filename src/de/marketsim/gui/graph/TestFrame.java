package de.marketsim.gui.graph;

import java.awt.*;
import javax.swing.*;
import java.beans.*;
import com.borland.jbcl.layout.*;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class TestFrame extends JFrame
{

  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel2 = new JPanel();

  public TestFrame()
  {
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception
  {
    this.getContentPane().setLayout(borderLayout1);

    jPanel1.setBorder(BorderFactory.createLineBorder(Color.black));
    jPanel1.setToolTipText("bbb");

    jPanel2.setBorder(BorderFactory.createLineBorder(Color.black));
    jPanel2.setPreferredSize(new Dimension(10, 80));
    jPanel2.setToolTipText("aaaa");
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    this.getContentPane().add(jPanel2, BorderLayout.NORTH);
  }

  void jPanel1_propertyChange(PropertyChangeEvent e)
  {
      System.out.println("Property is changed.");
  }

  public static void main(String args[] )
  {

      TestFrame tt = new TestFrame();
      tt.setSize(200,300);
      tt.setVisible(true);

  }


}