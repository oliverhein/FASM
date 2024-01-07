package de.marketsim.install;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import de.marketsim.util.FileTool;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class InstallMain extends JFrame {
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JTextField jTFTargetDirectory = new JTextField();
  private JButton jButton1 = new JButton();
  private JTextArea jTAInstallState = new JTextArea();
  private JLabel jLabel3 = new JLabel();
  private JButton jBInstall = new JButton();
  private JButton jBExit = new JButton();

  public InstallMain() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public static void main(String[] args)
  {

    AcceptDialog acdialog = new AcceptDialog();
    acdialog.setSize(405,325);
    acdialog.setModal(true);
    acdialog.setVisible(true);

    int userchoice = acdialog.getUserChoice();
    if ( userchoice != acdialog.mNextChoosed )
    {
        System.exit(0);
        return;
    }

    System.out.println("continue ....  " );

    InstallMain pp = new InstallMain();

    pp.setSize( 540,460);
    pp.setVisible(true);


  }
  private void jbInit() throws Exception {
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setText("Stock Market simulator   Install Program Version 1.0");
    jLabel1.setBounds(new Rectangle(2, 5, 553, 28));
    this.getContentPane().setLayout(null);
    jLabel2.setText("Target Directory");
    jLabel2.setBounds(new Rectangle(6, 127, 91, 26));
    jTFTargetDirectory.setText("C:\\stockmarketsim");
    jTFTargetDirectory.setBounds(new Rectangle(103, 131, 335, 20));
    jButton1.setBounds(new Rectangle(442, 131, 63, 21));
    jButton1.setMargin(new Insets(2, 1, 2, 1));
    jButton1.setText("Select");
    jButton1.addActionListener(new InstallMain_jButton1_actionAdapter(this));
    jTAInstallState.setBounds(new Rectangle(7, 326, 522, 108));
    jLabel3.setText("Install state");
    jLabel3.setBounds(new Rectangle(7, 296, 521, 25));
    jBInstall.setBounds(new Rectangle(8, 208, 128, 27));
    jBInstall.setText("Start Installation");
    jBInstall.addActionListener(new InstallMain_jBInstall_actionAdapter(this));
    jBExit.setBounds(new Rectangle(141, 209, 102, 27));
    jBExit.setText("Exit");
    jBExit.addActionListener(new InstallMain_jBExit_actionAdapter(this));
    this.setResizable(false);
    this.getContentPane().add(jTAInstallState, null);
    this.getContentPane().add(jLabel3, null);
    this.getContentPane().add(jBInstall, null);
    this.getContentPane().add(jTFTargetDirectory, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jButton1, null);
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(jBExit, null);
  }

  void jBInstall_actionPerformed(ActionEvent e)
  {

    String ss = this.jTFTargetDirectory.getText();
    if ( ss == null )
    {
       JOptionPane.showMessageDialog(this,"Target directory is empty.","Error", JOptionPane.ERROR_MESSAGE);
       return;
    }
    else
    {
      if ( ss.length() == 0 )
      {
        JOptionPane.showMessageDialog(this,"Target directory is empty.","Error", JOptionPane.ERROR_MESSAGE);
        return;
      }
    }

    InstallConfig.mTargetDirectory = ss;

    // create Main Directory
    FileTool.createDirectory( InstallConfig.mTargetDirectory );
    log("creating main directory: " +  InstallConfig.mTargetDirectory );

    /**
     *  create lib,
     *         etc,
     *         config
     *         run
     *  directory
    */
    FileTool.createDirectory( InstallConfig.mTargetDirectory + "/" +InstallConfig.mEtcDir );
    log("creating main directory: " + InstallConfig.mTargetDirectory + "/" +InstallConfig.mEtcDir );
    FileTool.createDirectory( InstallConfig.mTargetDirectory + "/" +InstallConfig.mLibDir );
    log("creating main directory: " + InstallConfig.mTargetDirectory + "/" +InstallConfig.mLibDir );
    FileTool.createDirectory( InstallConfig.mTargetDirectory + "/" +InstallConfig.mConfigDir );
    log("creating main directory: " + InstallConfig.mTargetDirectory + "/" +InstallConfig.mConfigDir );
    FileTool.createDirectory( InstallConfig.mTargetDirectory + "/" +InstallConfig.mRunDir );
    log("creating main directory: " + InstallConfig.mTargetDirectory + "/" +InstallConfig.mRunDir );

  }

  private boolean InstallLibrary()
  {



     return true;
  }

  private boolean InstallDemoConfig()
  {



     return true;
  }

  private boolean InstallScripts()
  {



     return true;
  }


  void jBExit_actionPerformed(ActionEvent e)
  {

      System.exit(0);
  }

  void jButton1_actionPerformed(ActionEvent e) {

  }

  private void log(String pInfo)
  {
     this.jTAInstallState.append( pInfo +"\r\n" );
  }

}

class InstallMain_jBInstall_actionAdapter implements java.awt.event.ActionListener {
  private InstallMain adaptee;

  InstallMain_jBInstall_actionAdapter(InstallMain adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBInstall_actionPerformed(e);
  }
}

class InstallMain_jBExit_actionAdapter implements java.awt.event.ActionListener {
  private InstallMain adaptee;

  InstallMain_jBExit_actionAdapter(InstallMain adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBExit_actionPerformed(e);
  }
}

class InstallMain_jButton1_actionAdapter implements java.awt.event.ActionListener {
  private InstallMain adaptee;

  InstallMain_jButton1_actionAdapter(InstallMain adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton1_actionPerformed(e);
  }
}