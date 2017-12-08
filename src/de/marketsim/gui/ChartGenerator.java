package de.marketsim.gui;

import java.awt.*;
import java.awt.Toolkit;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.marketsim.util.FileChecker;
import de.marketsim.util.FileTool;
import de.marketsim.util.HelpTool;
//import de.marketsim.config.ConfData;
import de.marketsim.config.Configurator;
/**
 * <p>Überschrift: FASM Frankfurt Articial Simulation Market</p>
 * <p>Beschreibung: Mircomarket Simulator </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class ChartGenerator extends JFrame
{

  DirectoryDialog mDirSelectDialog;
  Display         mDirSelectDisplay;

  private JButton jBCreate = new JButton();
  private JButton jBEXIT = new JButton();

  private JTextArea jTAShow = new JTextArea();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTextField jTFWorkDir = new JTextField();
  private JLabel jLabel2 = new JLabel();
  private JButton jBSetWorkDir = new JButton();
  private JCheckBox jCBPauseRequired = new JCheckBox();
  private JTextField jTFChartWidth = new JTextField();
  private JTextField jTFChartHeight = new JTextField();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel3 = new JLabel();
  private JLabel jLabel4 = new JLabel();
  private JLabel jLabel5 = new JLabel();

  int mscreen_width ;
  int mscreen_height;

  public ChartGenerator()
  {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void logInfo(String pInfo)
  {
     this.jTAShow.append( pInfo + "\r\n" );
  }

  public boolean isPauseRequiredBeforeSavingImage()
  {
      return this.jCBPauseRequired.isSelected();
  }

  public int getChartWidth()
  {
      String ss = this.jTFChartWidth.getText();
      int width = 500;
      try
      {
      width = Integer.parseInt( ss);
      }
      catch (Exception ex)
      {

      }
      return width;
  }

  public int getChartHeight()
  {
      String ss = this.jTFChartHeight.getText();
      int hh = 600;
      try
      {
        hh = Integer.parseInt( ss);
      }
      catch (Exception ex)
      {

      }
      return hh;
  }

  private void jbInit() throws Exception {

    mscreen_width  =  (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    mscreen_height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 5;

    jBCreate.setBounds(new Rectangle(10, 134, 144, 28));
    jBCreate.setText("Select one report");
    jBCreate.addActionListener(new ChartGenerator_jBCreate_actionAdapter(this));
    this.getContentPane().setLayout(null);
    jBEXIT.setBounds(new Rectangle(157, 133, 116, 28));
    jBEXIT.setText("EXIT");
    jBEXIT.addActionListener(new ChartGenerator_jBEXIT_actionAdapter(this));
    jTAShow.setBorder(BorderFactory.createLineBorder(Color.black));
    jScrollPane1.setBounds(new Rectangle(11, 165, 340, 139));
    jTFWorkDir.setText(".");
    jTFWorkDir.setBounds(new Rectangle(128, 16, 193, 27));
    jLabel2.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel2.setText("report directory");
    jLabel2.setBounds(new Rectangle(9, 13, 118, 29));
    jBSetWorkDir.setBounds(new Rectangle(325, 15, 34, 27));
    jBSetWorkDir.setText("jButton1");
    jBSetWorkDir.addActionListener(new ChartGenerator_jBSetWorkDir_actionAdapter(this));
    jCBPauseRequired.setText("Pause before saving Image");
    jCBPauseRequired.setBounds(new Rectangle(7, 51, 186, 28));
    this.setTitle("Chart Generator");

    jTFChartWidth.setText("" + mscreen_width /2 );

    jTFChartWidth.setBounds(new Rectangle(52, 83, 50, 21));

    jTFChartHeight.setText("" + Math.round( mscreen_height*0.85 ) );

    jTFChartHeight.setBounds(new Rectangle(188, 83, 42, 22));
    jLabel1.setText("Width");
    jLabel1.setBounds(new Rectangle(9, 82, 37, 23));
    jLabel3.setBounds(new Rectangle(142, 82, 42, 23));
    jLabel3.setText("Height");
    jLabel4.setBounds(new Rectangle(101, 82, 35, 23));
    jLabel4.setText("Pixel");
    jLabel5.setBounds(new Rectangle(235, 82, 31, 23));
    jLabel5.setText("Pixel");
    this.getContentPane().add(jScrollPane1, null);
    jScrollPane1.getViewport().add(jTAShow, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jTFWorkDir, null);
    this.getContentPane().add(jCBPauseRequired, null);
    this.getContentPane().add(jTFChartWidth, null);
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(jBSetWorkDir, null);
    this.getContentPane().add(jTFChartHeight, null);
    this.getContentPane().add(jLabel3, null);
    this.getContentPane().add(jLabel5, null);
    this.getContentPane().add(jLabel4, null);
    this.getContentPane().add(jBCreate, null);
    this.getContentPane().add(jBEXIT, null);

  }

  void jBEXIT_actionPerformed(ActionEvent e)
  {
        System.exit(0);
  }

  void jBCreate_actionPerformed(ActionEvent e)
  {
        if ( mDirSelectDialog == null )
        {
        // Select the Top-Directory
        mDirSelectDisplay = new Display();
        final Shell shell = new Shell(mDirSelectDisplay);
        mDirSelectDialog = new DirectoryDialog(shell);
        String reportdir =this.jTFWorkDir.getText();

        reportdir = reportdir.substring(0, reportdir.length()-2 );
        mDirSelectDialog.setFilterPath(  reportdir );
        }

        String selectedDirectory = mDirSelectDialog.open();
        //mDirSelectDisplay.dispose();

        if (  selectedDirectory == null )
        {

          JOptionPane.showMessageDialog(this, "No Report Directory is selected.","Error", JOptionPane.ERROR_MESSAGE);
          return;
        }

        System.out.println("Directory=" + selectedDirectory );
        System.out.println( "Searching Data ....... " );
        ChartCreateThread  pp = new ChartCreateThread(selectedDirectory, this);

        int w= Integer.parseInt(  this.jTFChartWidth.getText() );
        int h= Integer.parseInt(  this.jTFChartHeight.getText() );
        pp.setPreferedChartSize( w, h );
        pp.setOpenChartOverview( true );
        pp.start();
  }

  public void setWorkDir(String pDir)
  {
      this.jTFWorkDir.setText( pDir );
  }

  void jBSetWorkDir_actionPerformed(ActionEvent e)
  {
    // Select the Work-Directory
    Display display = new Display();
    final Shell shell = new Shell(display);
    DirectoryDialog dlg = new DirectoryDialog(shell);
    String selectedDirectory = dlg.open();
    display.dispose();
    this.jTFWorkDir.setText( selectedDirectory );

  }

  public static void main(String[] args)
  {
        ChartGenerator pp = new ChartGenerator();
        int ww = 360;
        int hh = 320;
        pp.setSize(ww,hh);
        pp.setLocation( (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()-ww, (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - hh ) );
        String ss = System.getProperty("FASMWORKDIR");
        if ( ss != null )
        {
          try
          {
               File ff = new File(ss);
               pp.setWorkDir(ff.getAbsolutePath());
          }
          catch (Exception ex)
          {

          }
        }
        pp.setVisible(true);
 }

}

class ChartGenerator_jBEXIT_actionAdapter implements java.awt.event.ActionListener {
  private ChartGenerator adaptee;

  ChartGenerator_jBEXIT_actionAdapter(ChartGenerator adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBEXIT_actionPerformed(e);
  }
}


class ChartGenerator_jBCreate_actionAdapter implements java.awt.event.ActionListener {
  private ChartGenerator adaptee;

  ChartGenerator_jBCreate_actionAdapter(ChartGenerator adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBCreate_actionPerformed(e);
  }
}

class ChartGenerator_jBSetWorkDir_actionAdapter implements java.awt.event.ActionListener {
  private ChartGenerator adaptee;

  ChartGenerator_jBSetWorkDir_actionAdapter(ChartGenerator adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBSetWorkDir_actionPerformed(e);
  }
}


