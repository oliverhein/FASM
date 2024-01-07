package de.marketsim.gui;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung:
 * Dieser Frame ist verwendet, um die Gewinnprozent von NoiseTrader anzuzeigen.
 * </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

  import java.awt.*;
  import java.util.*;
  import javax.swing.*;
  import javax.swing.border.*;
  import javax.swing.event.*;
  import java.awt.event.*;
  import com.borland.jbcl.layout.*;

  import de.marketsim.gui.BasePanel;
  import de.marketsim.config.Configurator;
  import de.marketsim.util.SingleBarData;
  import de.marketsim.util.SortTool;

  public class GewinnProcentFrame extends JFrame
  {
    private JPanel mCommandPanel  = new JPanel();
    // Chart:
    //private TwoBarPanel mGewinnPanel  = new TwoBarPanel();

    // SingleBar (nur Relative Gewinn Procent )
    private SingleBarPanel mGewinnPanel  = new SingleBarPanel();

    private String mSubTitle = "";
    private String mMaintitel= "Profit Procent";

    TitledBorder titledBorder1;
    Object  synobj = new Object();
    Vector  GewinnProcent  = new Vector();

    private int databeginindex = -1;
    private PaneLayout paneLayout1 = new PaneLayout();
    private BoxLayout2 boxLayout21 = new BoxLayout2();

    public GewinnProcentFrame( String pMainTitel )
    {
      try
      {
        jbInit();
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
      mMaintitel = pMainTitel;
      this.setTitle( pMainTitel );
    }

    private void jbInit() throws Exception
    {
      boxLayout21.setAxis(1);
      this.getContentPane().setLayout(boxLayout21 );
      titledBorder1 = new TitledBorder("");
      this.getContentPane().add( this.mGewinnPanel, null);
      this.setIconImage(  Toolkit.getDefaultToolkit().getImage("fasm.gif") );

    }

    public void setData( Vector pData, int pDay, double pMax, double pMin)
    {
       Vector SortedData = SortTool.sortSingleBardata( pData );
       this.mGewinnPanel.setData( SortedData, false );
       this.mGewinnPanel.setMin(pMin);
       this.mGewinnPanel.setMax(pMax);
       this.mGewinnPanel.showChart();
       this.setTitle( mMaintitel + " "  +  "Day "+ pDay );
    }

    public void showChart()
    {
       this.mGewinnPanel.showChart();
    }

    public static void main(String args[])
    {
        // Standalone Testdata
        GewinnProcentFrame ff = new GewinnProcentFrame("Relative Profit Procent");
        ff.setSize(500,600);
        ff.setVisible(true);

        Vector dd = new Vector();

        dd.add( new SingleBarData(100,"V1")  );
        dd.add( new SingleBarData(300,"V2")  );
        dd.add( new SingleBarData(-200,"V3")  );
        ff.setData( dd, 1, 300, 0 );
        ff.showChart();
    }

  }

