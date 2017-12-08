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

import java.util.*;
import java.io.*;
import de.marketsim.util.*;

public class KurtosisCheckerFrame extends JFrame
{
  private JLabel jLabel1 = new JLabel();
  private JTextField jTFPriceFile = new JTextField();
  private JTextField jTFDays = new JTextField();
  private JLabel jLabel2 = new JLabel();
  private JButton jBKurt = new JButton();
  private JButton jButton1 = new JButton();
  private TextArea textArea1 = new TextArea();

  private boolean mFileLoaded = false;
  private Vector  mPriceData  = new Vector();

  public KurtosisCheckerFrame()
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
    KurtosisCheckerFrame pp = new KurtosisCheckerFrame();
    pp.setSize(400,400);
    pp.setVisible(true);

  }
  private void jbInit() throws Exception {
    jLabel1.setText("Price File");
    jLabel1.setBounds(new Rectangle(19, 29, 85, 26));
    this.getContentPane().setLayout(null);
    jTFPriceFile.setText("price.csv");
    jTFPriceFile.setBounds(new Rectangle(137, 26, 242, 29));
    jTFDays.setText("400");
    jTFDays.setBounds(new Rectangle(137, 72, 57, 27));
    jLabel2.setText("Days for calculation");
    jLabel2.setBounds(new Rectangle(19, 72, 119, 25));
    jBKurt.setBounds(new Rectangle(217, 73, 77, 27));
    jBKurt.setText("Kurt");
    jBKurt.addActionListener(new KurtosisCheckerFrame_jBKurt_actionAdapter(this));
    jButton1.setBounds(new Rectangle(304, 73, 71, 28));
    jButton1.setActionCommand("jBEXIT");
    jButton1.setText("Exit");
    jButton1.addActionListener(new KurtosisCheckerFrame_jButton1_actionAdapter(this));
    textArea1.setBounds(new Rectangle(5, 111, 392, 187));
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(jTFPriceFile, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jTFDays, null);
    this.getContentPane().add(jBKurt, null);
    this.getContentPane().add(jButton1, null);
    this.getContentPane().add(textArea1, null);
  }

  void jButton1_actionPerformed(ActionEvent e)
  {
     System.exit(0);
  }

  void jBKurt_actionPerformed(ActionEvent e)
  {
     if ( ! this.mFileLoaded )
     {
       boolean tt = this.loadprice( this.jTFPriceFile.getText() );
       if ( ! tt )
       {
         return ;
       }
     }

     PriceStatistic  pp = new PriceStatistic();

     int days = Integer.parseInt( this.jTFDays.getText()  );
     if ( days > this.mPriceData.size() )
     {
       JOptionPane.showMessageDialog(this, "days is too big (maximal:" + this.mPriceData.size() +")","Error", JOptionPane.ERROR_MESSAGE);
       return;
     }
     double[] usedprice = new double[ days ];
     for ( int i=0; i<days; i++)
     {
         Double dd = (Double ) this.mPriceData.elementAt(i);
         usedprice[i] = dd.doubleValue();
     }
     double[] logrenditen = pp.getLogRenditen( usedprice );
     double kurt = pp.getkurt( logrenditen );
     this.textArea1.append( "days=" + days + " ; Kurt= "  + kurt + "\r\n");

     int j = Integer.MAX_VALUE;
     System.out.println( j );
     j= j + 1;
     System.out.println( j );

     System.out.println( 1291*1.0* 1292*1293 );
     System.out.println( 1290* 1291*1292 );

  }

  boolean loadprice(String pFile)
  {
    java.io.BufferedReader rd = null;
    try
    {
       rd = new java.io.BufferedReader ( new java.io.FileReader(pFile));
    }
    catch ( java.io.FileNotFoundException ex)
    {
        JOptionPane.showMessageDialog(this, pFile+ " not exist !","Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    String ss = null;
    try
    {
        do
        {
            ss = rd.readLine();

            if ( ss != null )
            {
                int j1 = ss.indexOf(";");
                int j2 = ss.indexOf(";",j1+1);
                Double dd = new Double( ss.substring(j1+1,j2));
                mPriceData.add(dd);
            }
         }
        while ( ss != null );
        rd.close();
        System.out.println( mPriceData.size() + " Data are loaded" );
        this.mFileLoaded = true;
        return true;
    }
    catch ( IOException ex )
    {
      JOptionPane.showMessageDialog(this, ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
      return false;
    }

  }



}

class KurtosisCheckerFrame_jButton1_actionAdapter implements java.awt.event.ActionListener {
  private KurtosisCheckerFrame adaptee;

  KurtosisCheckerFrame_jButton1_actionAdapter(KurtosisCheckerFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton1_actionPerformed(e);
  }
}

class KurtosisCheckerFrame_jBKurt_actionAdapter implements java.awt.event.ActionListener {
  private KurtosisCheckerFrame adaptee;

  KurtosisCheckerFrame_jBKurt_actionAdapter(KurtosisCheckerFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBKurt_actionPerformed(e);
  }
}