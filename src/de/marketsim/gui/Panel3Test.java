package de.marketsim.gui;

import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author unbekannt
 * @version 1.0
 */


public class Panel3Test extends JFrame
{
  private BasePanel3Chart jPanel1 = new BasePanel3Chart();

  public Panel3Test()
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
       Panel3Test panel3Test = new Panel3Test();
       panel3Test.setSize(400,400);
       panel3Test.setVisible(true);
  }


  private void jbInit() throws Exception
  {
      this.getContentPane().add(jPanel1, BorderLayout.CENTER);

      this.jPanel1.setKurv1_Titel("Fund Agent");
      this.jPanel1.setKurv2_Titel("Trend Agent");
      this.jPanel1.setKurv3_Titel("Blanko Agent");

      this.jPanel1.setKurv1_DrawingColor( Color.BLACK );
      this.jPanel1.setKurv2_DrawingColor( Color.RED );
      this.jPanel1.setKurv3_DrawingColor( Color.GREEN );

      Vector vc1 = new Vector();
      Vector vc2 = new Vector();
      Vector vc3 = new Vector();

      vc1.add(new Double(21) );
      vc1.add(new Double(5) );
      vc1.add(new Double(7) );
      vc1.add(new Double(15) );
      vc1.add(new Double(3) );
      vc1.add(new Double(13) );

      vc2.add(new Double(8) );
      vc2.add(new Double(8) );
      vc2.add(new Double(8) );
      vc2.add(new Double(8) );
      vc2.add(new Double(8) );
      vc2.add(new Double(8) );

      vc3.add(new Double(16) );
      vc3.add(new Double(6) );
      vc3.add(new Double(9) );
      vc3.add(new Double(10) );
      vc3.add(new Double(12) );
      vc3.add(new Double(2) );
      this.jPanel1.setData(vc1,vc2,vc3);
      this.jPanel1.setFractionDigits(0);

      this.jPanel1.showChart();

  }
}