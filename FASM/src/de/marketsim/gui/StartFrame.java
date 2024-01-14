package de.marketsim.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import de.marketsim.config.Configurator;
import de.marketsim.config.JadeConfig;
import de.marketsim.util.JadeMainContainerChecker;
import de.marketsim.util.MsgLogger;

import de.marketsim.gui.InnererWertDefFrame;


public class StartFrame extends JFrame
{
  private ButtonGroup DialogBatchModeSelectGroup = new ButtonGroup();
  private JRadioButton jRBDialog = new JRadioButton();
  private JRadioButton jRBBatch = new JRadioButton();
  private JButton jBWeiter = new JButton();
  private JLabel jLabel1 = new JLabel();
  private JButton jButton2 = new JButton();
  private JPanel jPanel1 = new JPanel();

  private ButtonGroup AktienMoneyMarketSelectGroup = new ButtonGroup();

  private ButtonGroup TobintaxAktiveSelectGroup = new ButtonGroup();

  public StartFrame()
  {

    // String jadebasicconfig = System.getProperty("JADEBASICCONFIG");
    String jadebasicconfig = "C:/Users/olive/eclipse - fasm/FASM/run/etc/jadebasic.cfg";
    System.out.println("JADEBASICCONFIG=" + jadebasicconfig );

    JadeConfig.Init ( jadebasicconfig );

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception
  {
    jRBDialog.setText("Dialog  Process");
    jRBDialog.setBounds(new Rectangle(42, 27, 127, 25));
    jRBDialog.addActionListener(new StartFrame_jRBDialog_actionAdapter(this));
    jRBDialog.setFont(new java.awt.Font("Dialog", 1, 12));
    jRBDialog.setActionCommand("Dialog");
    jRBDialog.setSelected(true);
    this.setIconImage(  Toolkit.getDefaultToolkit().getImage("fasm.gif") );

    this.getContentPane().setLayout(null);
    jRBBatch.setFont(new java.awt.Font("Dialog", 1, 12));
    jRBBatch.setActionCommand("Batch");
    jRBBatch.setText("Batch  Process ");
    jRBBatch.setBounds(new Rectangle(208, 27, 132, 25));
    jRBBatch.addActionListener(new StartFrame_jRBBatch_actionAdapter(this));
    jBWeiter.setBounds(new Rectangle(73, 82, 107, 28));
    jBWeiter.setText("Next");
    jBWeiter.addActionListener(new StartFrame_jBWeiter_actionAdapter(this));
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setText("Simulation Mode Configuration");
    jLabel1.setBounds(new Rectangle(102, 12, 250, 39));
    jButton2.setBounds(new Rectangle(187, 83, 108, 27));
    jButton2.setText("Exit");
    jButton2.addActionListener(new StartFrame_jButton2_actionAdapter(this));
    this.setTitle("FASM Version 2.0");
    jPanel1.setBorder(BorderFactory.createLineBorder(Color.black));
    jPanel1.setBounds(new Rectangle(21, 60, 390, 127));
    jPanel1.setLayout(null);
    jPanel1.add(jRBDialog, null);
    jPanel1.add(jRBBatch, null);
    jPanel1.add(jBWeiter, null);
    jPanel1.add(jButton2, null);
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(jPanel1, null);
    DialogBatchModeSelectGroup.add(jRBDialog);
    DialogBatchModeSelectGroup.add(jRBBatch);
  }

  void jBWeiter_actionPerformed(ActionEvent e)
  {

    try
    {
      System.out.println("checking port: " + JadeConfig.getPort() );
      boolean used = de.marketsim.util.JadeMainContainerChecker.checkServerPortIsUsed( Integer.parseInt( JadeConfig.getPort() ) );
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(this,"It seems that you have alreday started FASM. \r\nIf you started it from DOS, please use Ctrol+C to break it. \r\nIf you started it from Desktop, please use WindowsTaskManager to find the process with javaw.exe, then kill it.","Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    // check Dialog Mode or Batch Mode is selected.
    ButtonModel bm = this.DialogBatchModeSelectGroup.getSelection();
    // When nichts gewälht:
    if ( bm == null )
    {
      JOptionPane.showMessageDialog(this,"No Mode is selected.","Error", JOptionPane.ERROR_MESSAGE);
      return;
    }
    else
    {
        String ss = bm.getActionCommand();
        // load Log4j config file from etc/loggerproperties.xml
        MsgLogger.loadMsgLoggerConfig();

    }

    // check AktienMarket or MoneyMarket is selected.
    /**
    bm = this.AktienMoneyMarketSelectGroup.getSelection();
    {
        String ss = bm.getActionCommand();
        if ( ss.equalsIgnoreCase("AktienMarket") )
        {
          Configurator.mConfData.mMarketMode = Configurator.mConfData.mAktienMarket;
          System.out.println("set to AktienMarket Mode");

        }
        else
        {
          Configurator.mConfData.mMarketMode = Configurator.mConfData.mMoneyMarket;
          System.out.println("set to MoneyMarket Mode");

          // MoneyMarket is selected
          // check if TobinTax is aktive
          bm = this.TobintaxAktiveSelectGroup.getSelection();
          ss = bm.getActionCommand();
          if ( ss.equalsIgnoreCase("Aktive") )
          {
            Configurator.mConfData.mTobintaxAgentAktive = true;
          }
          else
          {
            Configurator.mConfData.mTobintaxAgentAktive = false;
          }
        }
    }

    */

    this.setVisible(false);
    String args[] = new String[0];
    de.marketsim.agent.stockstore.BootDialog.main( args );

  }

  void jButton2_actionPerformed(ActionEvent e)
  {
     // von dort wird system beendet
     JadeMainContainerChecker.KillPossibleClient( JadeConfig.getHostname(), JadeConfig.getPort() );
  }

  public void processEvent( AWTEvent e )
  {
    // 201 is the code of Windows Closing
    if ( e.getID()== 201 )
    {
       // Nothing to do
    }
    else
    {
       super.processEvent(e);
    }
  }

  public static void main(String[] args)
  {

    //String jadebasicconfig = System.getProperty("JADEBASICCONFIG");
    String jadebasicconfig = "C:/Users/olive/git/FASM/run/etc/jadebasic.cfg";
    System.out.println("JADEBASICCONFIG=" + jadebasicconfig );
    JadeConfig.Init ( jadebasicconfig );
    System.out.println("checking port: " + JadeConfig.getPort() );

    try
    {
      boolean used = de.marketsim.util.JadeMainContainerChecker.checkServerPortIsUsed( Integer.parseInt( JadeConfig.getPort() ) );
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      String ss = System.getProperty("DIALOG", "true");
      if ( ss.equalsIgnoreCase("true") )
      {
         JOptionPane.showMessageDialog( new Frame(),"It seems that you have alreday started FASM. \r\nIf you started it from DOS, please close it or change to that DOS window and then press Ctrol+C to force breaking it. \r\nIf you started it from Desktop, please use WindowsTaskManager to find the process with javaw.exe, then kill it.","Error", JOptionPane.ERROR_MESSAGE);
      }
      else
      {
         System.out.println("*******************************************************");
         System.out.println("* ERROR                                               *");
         System.out.println("* It seems that you have alreday started FASM.        *");
         System.out.println("* Please kill it using kill -9 <PID>                  *");
         System.out.println("*******************************************************");
      }
      System.exit( -1 );
    }

     // load Log4j config file from etc/loggerproperties.xml
     MsgLogger.loadMsgLoggerConfig();
     de.marketsim.agent.stockstore.BootDialog.main( args );

  }

  void jRB_MoneyMarket_actionPerformed(ActionEvent e)
  {
  }

  void jRB_AktienMarket_actionPerformed(ActionEvent e)
  {
  }

  void jRBBatch_actionPerformed(ActionEvent e) {


  }

  void jRBDialog_actionPerformed(ActionEvent e)
  {
    ButtonModel  bm = this.AktienMoneyMarketSelectGroup.getSelection();
    String ss = bm.getActionCommand();
   }

}

class StartFrame_jButton2_actionAdapter implements java.awt.event.ActionListener {
  private StartFrame adaptee;

  StartFrame_jButton2_actionAdapter(StartFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton2_actionPerformed(e);
  }
}

class StartFrame_jBWeiter_actionAdapter implements java.awt.event.ActionListener {
  private StartFrame adaptee;

  StartFrame_jBWeiter_actionAdapter(StartFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBWeiter_actionPerformed(e);
  }
}

class StartFrame_jRBBatch_actionAdapter implements java.awt.event.ActionListener {
  private StartFrame adaptee;

  StartFrame_jRBBatch_actionAdapter(StartFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jRBBatch_actionPerformed(e);
  }
}

class StartFrame_jRBDialog_actionAdapter implements java.awt.event.ActionListener
{
  private StartFrame adaptee;

  StartFrame_jRBDialog_actionAdapter(StartFrame adaptee)
  {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e)
  {
    adaptee.jRBDialog_actionPerformed(e);
  }



}
