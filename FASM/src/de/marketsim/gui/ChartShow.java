package de.marketsim.gui;

/**
 * <p>Überschrift: FASM Frankfurt Articial Simulation Market</p>
 * <p>Beschreibung: Mircomarket Simulator </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Toolkit;
import java.awt.*;
import java.util.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;

import org.jfree.chart.axis.*;

import org.jfree.chart.axis.ValueAxis;

import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;

import org.jfree.chart.plot.*;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.*;

import org.jfree.chart.renderer.xy.*;
import org.jfree.chart.renderer.*;

import org.jfree.data.category.CategoryDataset;

import org.jfree.data.*;
import org.jfree.data.xy.*;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A simple demonstration application showing how to create a bar chart.
 */

public class ChartShow extends ApplicationFrame {

        private String mTitle;
        private int cc = 0;
        private JFreeChart            mChart;
        private CombinedDomainXYPlot  mPlot_Main;
        private XYPlot                mPlot_PriceAndInnervalue;  // At Top
        private XYPlot                mPlot_PriceDeviation;
        private XYPlot                mPlot_Volume;
        private XYPlot                mPlot_AgentAnzahl;  // At Bottom
        private XYPlot                mPlot_DailyLogYield;  // At Bottom

        private ChartPanel mChartPanel;

        int mscreen_width;
        int mscreen_height;

        /**
     * Creates a new demo instance.
     *
     * @param title  the frame title.
     */
    public ChartShow(String title, String subtitle ) {
        super(title +" " +subtitle);
        mTitle = title+" " +subtitle;
        this.initChart();
        mChartPanel = new ChartPanel(this.mChart, false);

        int ww  =  (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int hh  = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 5;

        mChartPanel.setPreferredSize(new Dimension(ww*2/3, hh-100 ));

        setContentPane(mChartPanel);
        mscreen_width  =  (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        mscreen_height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 5;
        this.setLocation(10,50);
    }

    public void setChartSize(int pWidth, int pHeight )
    {
      mChartPanel.setPreferredSize(new Dimension(pWidth, pHeight ));
    }

    private void  setDemoDataSet()
    {

      XYDataset dataset_PriceInnerValue       = this.createXYDataset_PriceAndInnerValue(20);
      XYDataset dataset_Deviation             = this.createXYDataset_Deviation(20);
      XYDataset dataset_Volume                = this.createXYDataset_Volume(20);
      XYDataset dataset_AgentAnzahl           = this.createXYDataset_AgentAnzahl(20);
      XYDataset dataset_DailyLogYield         = this.createXYDataset_DialyLogYield(20);

      setFasmDataSet(dataset_PriceInnerValue,
                     dataset_Deviation,
                     dataset_Volume,
                     dataset_AgentAnzahl,
                     dataset_DailyLogYield );

    }

    private void setFasmDataSet( XYDataset dataset_priceandinnervalue, XYDataset dataset_priceminusinnervalue, XYDataset dataset_volume, XYDataset dataset_agentanzahl, XYDataset dataset_dailylogyield )
    {
      this.mPlot_PriceAndInnervalue.setDataset  (dataset_priceandinnervalue);
      this.mPlot_PriceDeviation.setDataset(dataset_priceminusinnervalue);
      this.mPlot_Volume.setDataset              (dataset_volume);
      this.mPlot_AgentAnzahl.setDataset         (dataset_agentanzahl);
      this.mPlot_DailyLogYield.setDataset      ( dataset_dailylogyield );
    }

    /**
     * @param priceandinnervalue     N  Lines, 2 Colume
     * @param priceminusinnervalue   N  Lines, 1 Colume
     * @param volume                 N  Lines, 1 Colume
     * @param agentanzahl            N  Lines, 3 Colume
     * @param dailylogyield          N  Lines, 1 Colume
     */

    public void setFasmData( double priceandinnervalue[][], double pricedeviation[], double volume[], double agentanzahl[][], double dailylogyield[] )
    {
      DefaultXYDataset dataset_PriceInnerValue = new DefaultXYDataset();
      // priceandinnervalue.length  is the number of Lines of data table

      double mydata1[][] = new double[2][ priceandinnervalue.length ];
      double mydata2[][] = new double[2][ priceandinnervalue.length ];

      for ( int i=0; i<priceandinnervalue.length; i++ )
      {
              //System.out.println( i + ". priceandinnervalue");
              mydata1[0][i]=i;
              mydata1[1][i]=priceandinnervalue[i][0];  // Price

              mydata2[0][i]=i;
              mydata2[1][i]=priceandinnervalue[i][1]; // InnerValue
      }
      dataset_PriceInnerValue.addSeries( "Price",        mydata1 );
      dataset_PriceInnerValue.addSeries( "Inner value",  mydata2 );

      DefaultIntervalXYDataset dataset_Deviation  = new DefaultIntervalXYDataset();
      double mydata3[][] = new double[6][ pricedeviation.length ];
      for ( int i=0; i<pricedeviation.length; i++)
      {
          //System.out.println( i + ". priceaMinusrvalue");
          mydata3[0][i] = i;
          mydata3[1][i] = i-0.3;
          mydata3[2][i] = i+0.3;

          mydata3[3][i] = pricedeviation[i];

          if ( mydata3[3][i] >=0 )
          {
            mydata3[4][i] = 0;
            mydata3[5][i] = pricedeviation[i];
            }
            else
            {
              mydata3[4][i] = pricedeviation[i];
              mydata3[5][i] = 0;
            }
      }
      dataset_Deviation.addSeries( "Deviation",  mydata3 );

      DefaultIntervalXYDataset dataset_Volume = new DefaultIntervalXYDataset();
      double mydata4[][] = new double[6][ volume.length ];  // for XYBar
      for ( int i=0; i< volume.length; i++)
      {
          mydata4[0][i] = i;
          mydata4[1][i] = i-0.3;
          mydata4[2][i] = i+0.3;

          mydata4[3][i] = volume[i];
          mydata4[4][i] = 0;
          mydata4[5][i] = mydata4[3][i];
      }
      dataset_Volume.addSeries( "Volume",  mydata4 );

      DefaultXYDataset dataset_AgentAnzahl  = new DefaultXYDataset();
      double mydata5[][] = new double[2][ volume.length ];
      double mydata6[][] = new double[2][ volume.length ];
      double mydata7[][] = new double[2][ volume.length ];
      for ( int i=0; i< volume.length; i++)
      {
          //System.out.println( i + ". AgentAnzahl");

          mydata5[0][i] = i;
          mydata5[1][i] = agentanzahl[i][0];  // Investor

          mydata6[0][i] = i;
          mydata6[1][i] = agentanzahl[i][1]; // NoiseTrader

          mydata7[0][i] = i;
          mydata7[1][i] = agentanzahl[i][2]; // RetailAgent
      }
      dataset_AgentAnzahl.addSeries( "Fundamental",  mydata5 );
      dataset_AgentAnzahl.addSeries( "Trend",  mydata6 );
      dataset_AgentAnzahl.addSeries( "Retail",  mydata7 );

      DefaultXYDataset dataset_DailyLogYield = new DefaultXYDataset();;

      double mydata8[][] = new double[2][ dailylogyield.length ];
      for ( int i=0; i< dailylogyield.length; i++)
      {
          mydata8[0][i] = i;
          mydata8[1][i] = dailylogyield[i];
          //System.out.println( i + ". DailyLogYield=" + mydata8[1][i] );
      }
      dataset_DailyLogYield.addSeries( "Daily Log Yield",  mydata8 );

      this.setFasmDataSet( dataset_PriceInnerValue,
                           dataset_Deviation,
                           dataset_Volume,
                           dataset_AgentAnzahl,
                           dataset_DailyLogYield  );

      // Update Screen
      ShowFasmChart();

    }

    // only for test
    private static XYDataset createXYDataset_PriceAndInnerValue(int Data_CC) {

        // row keys...
        String series1 = "Price";
        String series2 = "Inner Value";

        // create the dataset...
        DefaultXYDataset dataset = new DefaultXYDataset();

        Random rr = new Random();
        int  data1_min =-2000;
        int  data1_max = 2000;

        int  data2_min =-1000;
        int  data2_max = 1000;

        double mydata1[][] = new double[2][ Data_CC ];
        double mydata2[][] = new double[2][ Data_CC ];

        for ( int i=0; i<Data_CC; i++ )
        {
                mydata1[0][i]=i;
                mydata1[1][i]=data1_min + rr.nextInt(  data1_max - data1_min );
                mydata2[0][i]=i;
                mydata2[1][i]=data2_min + rr.nextInt(  data2_max - data2_min );
        }
        dataset.addSeries( series1,  mydata1 );
        dataset.addSeries( series2,  mydata2 );
        return dataset;

    }


    // only for test
    // Deviation = (Price - Innervalue) / Innervalue
    private static XYDataset createXYDataset_Deviation(int Data_CC) {

        // row keys...
        String series = "Deviation";

        // create the dataset...
        DefaultIntervalXYDataset dataset = new DefaultIntervalXYDataset();

        Random rr = new Random();
        int  data_min = -20;
        int  data_max =  20;

        double mydata[][] = new double[6][ Data_CC ];

        for ( int i=0; i<Data_CC; i++ )
        {
                mydata[0][i]=i;
                mydata[1][i]=i-0.3;
                mydata[2][i]=i+0.3;

                mydata[3][i]=data_min + rr.nextInt(  (data_max - data_min) * 100 ) / 100.0;
                if ( mydata[3][i] >=0 )
                {
                  mydata[4][i] = 0;
                  mydata[5][i] = mydata[3][i];
                }
                else
                {
                  mydata[4][i] = mydata[3][i];
                  mydata[5][i] = 0;
                }
        }
        dataset.addSeries( series,  mydata );
        return dataset;

    }

    // only for test
    private static XYDataset createXYDataset_Volume(int Data_CC) {

        // row keys...
        String series = "Volume";

        // create the dataset...
        DefaultXYDataset dataset = new DefaultXYDataset();

        Random rr = new Random();
        int  data1_min = 0;
        int  data1_max = 20000;

        double mydata[][] = new double[6][ Data_CC ];

        for ( int i=0; i<Data_CC; i++ )
        {
              mydata[0][i]=i;
              mydata[1][i]=i-0.3;
              mydata[2][i]=i+0.3;

              mydata[3][i]=data1_min + rr.nextInt(  data1_max - data1_min );;
              mydata[4][i]=0;
              mydata[5][i]=data1_min + rr.nextInt(  data1_max - data1_min );
        }
        dataset.addSeries( series, mydata );
        return dataset;

    }

    // only for test
    private static XYDataset createXYDataset_AgentAnzahl(int Data_CC) {

        // row keys...
        String series1 = "Fundamental";
        String series2 = "Trend";
        String series3 = "Retail";

        // create the dataset...
        DefaultXYDataset dataset = new DefaultXYDataset();

        Random rr = new Random();
        int  data_min = 0;
        int  data_max = 100;

        double mydata1[][] = new double[2][ Data_CC ];
        double mydata2[][] = new double[2][ Data_CC ];
        double mydata3[][] = new double[2][ Data_CC ];

        for ( int i=0; i<Data_CC; i++ )
        {
                mydata1[0][i]=i;
                mydata1[1][i]=data_min + rr.nextInt(  data_max - data_min );

                mydata2[0][i]=i;
                mydata2[1][i]=data_min + rr.nextInt(  data_max - data_min );

                mydata3[0][i]=i;
                mydata3[1][i]=data_min + rr.nextInt(  data_max - data_min );


        }
        dataset.addSeries( series1,  mydata1 );
        dataset.addSeries( series2,  mydata2 );
        dataset.addSeries( series3,  mydata3 );
        return dataset;

    }


    private static XYDataset createXYDataset_DialyLogYield(int Data_CC) {

        // row keys...
        String series = "Daily Log Yield";

        // create the dataset...
        DefaultXYDataset dataset = new DefaultXYDataset();

        Random rr = new Random();
        int  data1_min = -5;
        int  data1_max =  10;


        double mydata[][] = new double[2][ Data_CC ];

        for ( int i=0; i<Data_CC; i++ )
        {
                mydata[0][i]=i;
                mydata[1][i]=data1_min + rr.nextInt(  data1_max - data1_min );
        }
        dataset.addSeries( series, mydata );
        return dataset;
    }



    /**
     * Initialize / Creates a sample Line chart.
     *
     * @param dataset  the dataset.
     *
     * @return The chart.
     */
    private void initChart()
    {
            this.mPlot_Main = new CombinedDomainXYPlot();

            this.mPlot_PriceAndInnervalue = new XYPlot();
            this.mPlot_PriceAndInnervalue.setBackgroundPaint(Color.gray );

            this.mPlot_PriceDeviation = new XYPlot();
            this.mPlot_PriceDeviation.setBackgroundPaint(Color.gray );

            this.mPlot_Volume = new XYPlot();
            this.mPlot_Volume.setBackgroundPaint(Color.gray);

            this.mPlot_AgentAnzahl = new XYPlot();
            this.mPlot_AgentAnzahl.setBackgroundPaint(Color.gray);

            this.mPlot_DailyLogYield = new XYPlot();
            this.mPlot_DailyLogYield.setBackgroundPaint(Color.gray);

            this.mPlot_Main.setBackgroundPaint(Color.lightGray);
            this.mPlot_Main.setDomainGridlinePaint(Color.white);
            this.mPlot_Main.setDomainGridlinesVisible(true);
            this.mPlot_Main.setRangeGridlinePaint(Color.white);
            this.mPlot_Main.setDomainGridlinesVisible( true );
            this.mPlot_Main.setRangeGridlinesVisible( true );

            NumberAxis yAxis_PiceAndInnervalue = new NumberAxis();
            //yAxis_PiceAndInnervalue.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            yAxis_PiceAndInnervalue.setLabelAngle( 0.0 );
            yAxis_PiceAndInnervalue.setLabel( "Price & Inner Value" );
            yAxis_PiceAndInnervalue.setAutoRangeIncludesZero( false );

            NumberAxis yAxis_PriceMinusInnervalue = new NumberAxis();
            //yAxis_PriceMinusInnervalue.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            yAxis_PriceMinusInnervalue.setLabelAngle( 0.0 );
            yAxis_PriceMinusInnervalue.setLabel( "Deviation in %" );
            // Deviation is Price Deviation = ( Price - Innervalue) / Innervalue

            NumberAxis yAxis_Volume = new NumberAxis();
            yAxis_Volume.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            yAxis_Volume.setLabelAngle( 0.0 );
            yAxis_Volume.setLabel( "Volume" );
            yAxis_Volume.setAutoRangeIncludesZero(false);

            NumberAxis yAxis_AgentAnzahl = new NumberAxis();
            yAxis_AgentAnzahl.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            yAxis_AgentAnzahl.setLabelAngle( 0.0 );
            yAxis_AgentAnzahl.setLabel( "Number of Agents" );

            NumberAxis yAxis_DailyLogYield = new NumberAxis();
            //yAxis_DailyLogYield.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            yAxis_DailyLogYield.setLabelAngle( 0.0 );
            yAxis_DailyLogYield.setLabel( "Daily Log Yield" );

            this.mPlot_PriceAndInnervalue.setRangeAxis( yAxis_PiceAndInnervalue);
            this.mPlot_PriceDeviation.setRangeAxis( yAxis_PriceMinusInnervalue);
            this.mPlot_Volume.setRangeAxis( yAxis_Volume);
            this.mPlot_AgentAnzahl.setRangeAxis( yAxis_AgentAnzahl);
            this.mPlot_DailyLogYield.setRangeAxis( yAxis_DailyLogYield);

            this.mPlot_Main.add(this.mPlot_PriceAndInnervalue);
            this.mPlot_Main.add(this.mPlot_PriceDeviation);
            this.mPlot_Main.add(this.mPlot_Volume);
            this.mPlot_Main.add(this.mPlot_AgentAnzahl);
            this.mPlot_Main.add(this.mPlot_DailyLogYield);

            // create the chart object...
            this.mChart = new JFreeChart( this.mTitle, this.mPlot_Main );

            // set the background color for the chart...
            this.mChart.setBackgroundPaint(Color.white);

            /*  Wichtig: X Axia can display  value like  1.0, 2.3   etc  */
            //  Domain Axis is  X Axis

            NumberAxis xAxis = new NumberAxis();
            xAxis.setLabelAngle( 0 );
            xAxis.setLabel("Days");
            xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

           this.mPlot_Main.setDomainAxis( xAxis);

        XYLineAndShapeRenderer renderer_PriceAndInnervalue = new XYLineAndShapeRenderer();

        renderer_PriceAndInnervalue.setDrawSeriesLineAsPath( true );
        renderer_PriceAndInnervalue.setBaseSeriesVisible( true );
        renderer_PriceAndInnervalue.setBaseItemLabelsVisible( true );
        renderer_PriceAndInnervalue.setBaseSeriesVisibleInLegend(true);
        renderer_PriceAndInnervalue.setSeriesLinesVisible(0, true);
        renderer_PriceAndInnervalue.setSeriesLinesVisible(1, true);

        renderer_PriceAndInnervalue.setSeriesShapesVisible(0,  false );
        renderer_PriceAndInnervalue.setSeriesShapesVisible(1,  false );

        //renderer.setDrawOutlines( false);
        // set up gradient paints for series...
        renderer_PriceAndInnervalue.setSeriesPaint(0, Color.BLUE ); // Pries
        renderer_PriceAndInnervalue.setSeriesPaint(1, Color.GREEN ); // Innervalue

        XYBarRenderer renderer_PriceDeviation = new XYBarRenderer();
        renderer_PriceDeviation.setBaseSeriesVisible( true );
        renderer_PriceDeviation.setBaseItemLabelsVisible( true );
        renderer_PriceDeviation.setBaseSeriesVisibleInLegend(true);

        GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue,   0.0f, 0.0f, new Color(0, 0, 64));
        renderer_PriceDeviation.setSeriesPaint(0, gp0 );

        XYBarRenderer renderer_volume = new XYBarRenderer();
        renderer_volume.setBaseSeriesVisible( true );
        renderer_volume.setBaseItemLabelsVisible( true );
        renderer_volume.setBaseSeriesVisibleInLegend(true);
        GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.YELLOW,  0.0f, 0.0f, new Color(255, 255, 10));
        renderer_volume.setSeriesPaint(0, gp1 );

        XYLineAndShapeRenderer renderer_AgentAnzahl = new XYLineAndShapeRenderer();

        renderer_AgentAnzahl.setDrawSeriesLineAsPath( true );
        renderer_AgentAnzahl.setBaseSeriesVisible( true );
        renderer_AgentAnzahl.setBaseItemLabelsVisible( true );
        renderer_AgentAnzahl.setBaseSeriesVisibleInLegend(true);
        renderer_AgentAnzahl.setSeriesLinesVisible(0, true);
        renderer_AgentAnzahl.setSeriesLinesVisible(1, true);
        renderer_AgentAnzahl.setSeriesLinesVisible(2, true);
        renderer_AgentAnzahl.setSeriesShapesVisible(0,  false );
        renderer_AgentAnzahl.setSeriesShapesVisible(1,  false);
        renderer_AgentAnzahl.setSeriesShapesVisible(2,  false );
        renderer_AgentAnzahl.setSeriesPaint(0, Color.RED );
        renderer_AgentAnzahl.setSeriesPaint(1, Color.BLUE );
        renderer_AgentAnzahl.setSeriesPaint(2, Color.CYAN );

        XYLineAndShapeRenderer renderer_DailyLogYield = new XYLineAndShapeRenderer();

        renderer_DailyLogYield.setDrawSeriesLineAsPath( true );
        renderer_DailyLogYield.setBaseSeriesVisible( true );
        renderer_DailyLogYield.setBaseItemLabelsVisible( true );
        renderer_DailyLogYield.setBaseSeriesVisibleInLegend(true);

        renderer_DailyLogYield.setSeriesLinesVisible(0, true);
        renderer_DailyLogYield.setSeriesShapesVisible(0,  false );
        renderer_DailyLogYield.setSeriesPaint(0, Color.GREEN );

        this.mPlot_PriceAndInnervalue.setRenderer  ( renderer_PriceAndInnervalue );
        this.mPlot_PriceDeviation.setRenderer      ( renderer_PriceDeviation );
        this.mPlot_Volume.setRenderer              ( renderer_volume );
        this.mPlot_AgentAnzahl.setRenderer         ( renderer_AgentAnzahl );
        this.mPlot_DailyLogYield.setRenderer       ( renderer_DailyLogYield );

    }

    public void ShowFasmChart()
    {
            this.pack();
            RefineryUtilities.centerFrameOnScreen( this );
            this.setVisible(true);
    }


    public void ShowDemoChart()
    {
            this.setDemoDataSet();
            this.pack();
            RefineryUtilities.centerFrameOnScreen( this );
            this.setVisible(true);
    }

    public void saveFrame2File(String pTargetFile)
    {
      try
      {
        ScreenImage.createImage( this, pTargetFile );
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }

    }

    /**
     * overwrite processEvent( AWTEvent e ) of father class
     * @param e
     */
    public void processEvent( AWTEvent e )
    {
      // 201 is the code of Windows Closing
      if ( e.getID()== 201 )
      {
         // Frame will disapear
         this.hide();
         // Free the used resource ( Objects )
         this.dispose();
      }
      else
      {
         super.processEvent(e);
      }
    }




    /**
     * Starting point for the demonstration application.
     *
     *  Self test
     *
     * @param args  ignored.
     */
    public static void main(String[] args) {

        ChartShow demo = new ChartShow("FASM Chart", "");
        //demo.ShowDemoChart();

       int datacc=500;

       double  priceinnervalue[][] = new double[datacc][2];
       double  priceminusinnervalue[] = new double[datacc];
       double  volume[] = new double[datacc];
       double  agentanzahl[][] = new double[datacc][3];
       double  dailylogyield[] = new double[datacc];

       Random rd = new Random();

       for ( int i=0; i< datacc;  i++)
       {

         priceinnervalue[i][0] = rd.nextInt(1000) + 10;
         priceinnervalue[i][1] = rd.nextInt(1100) + 10;
         priceminusinnervalue[i] =priceinnervalue[i][0] - priceinnervalue[i][1];

         System.out.println( i + ". Price-Innervalue=" + priceminusinnervalue[i] );

         volume[i] = rd.nextInt(20000) ;
         agentanzahl[i][0] = rd.nextInt(50);
         agentanzahl[i][1] = rd.nextInt(50);
         agentanzahl[i][2] = 100 -  agentanzahl[i][0] - agentanzahl[i][1];

         if ( i==0 )
         {
           dailylogyield[i] = 0;
         }
         else
         {
           dailylogyield[i] = Math.log( priceinnervalue[i][0]*1.0 / priceinnervalue[i-1][0]);

           //System.out.println( i + ". " + dailylogyield[i] );

         }

       }

       demo.setFasmData( priceinnervalue, priceminusinnervalue, volume, agentanzahl, dailylogyield );

    }

}
