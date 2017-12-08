package de.marketsim.config;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.util.Vector;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class ConfigFileRepeatorDefine extends JDialog {
  private JPanel panel1 = new JPanel();
  private JButton jBOK = new JButton();

  private Vector mConfigFileTextFieldList = new Vector();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JButton jBReset = new JButton();

  private String[]  mFileList4Simulation;
  private int[]  mFilesSimulationzahlList;

  public ConfigFileRepeatorDefine(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

 public static void main(String args[])
 {
    ConfigFileRepeatorDefine tt = new    ConfigFileRepeatorDefine();

    tt.setSize(400,350);
    tt.addoneConfigFile( new ConfigFileValidation( "Hallo1.xml", true ) );
    tt.addoneConfigFile( new ConfigFileValidation("Hallo2.xml", true ));
    tt.addoneConfigFile( new ConfigFileValidation("Hallo3.xml", false ));

    tt.setVisible(true);
 }

  public ConfigFileRepeatorDefine() {
    this(null, "", false);
  }

  private void jbInit() throws Exception {
    panel1.setLayout(null);
    jBOK.setBounds(new Rectangle(38, 10, 100, 34));
    jBOK.setActionCommand("jBOK");
    jBOK.setText("OK");
    jBOK.addActionListener(new ConfigFileRepeatorDefine_jBOK_actionAdapter(this));
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1.setText("Config File name");
    jLabel1.setBounds(new Rectangle(39, 43, 105, 35));
    jLabel2.setBounds(new Rectangle(257, 43, 184, 35));
    jLabel2.setText("Repeat  Times of same Network");
    jLabel2.setFont(new java.awt.Font("Dialog", 1, 12));
    jBReset.setBounds(new Rectangle(141, 11, 95, 33));
    jBReset.setText("Reset");
    jBReset.addActionListener(new ConfigFileRepeatorDefine_jBReset_actionAdapter(this));
    getContentPane().add(panel1);
    panel1.add(jLabel1, null);
    panel1.add(jLabel2, null);
    panel1.add(jBOK, null);
    panel1.add(jBReset, null);
  }

  void jBOK_actionPerformed(ActionEvent e)
  {
    // speichert nur den Index
    Vector temp = new Vector();

    for (int i=0; i< this.mConfigFileTextFieldList.size(); i++)
    {
        TextField tt = (TextField) mConfigFileTextFieldList.elementAt(i);
        if ( Integer.parseInt( tt.getText() ) > 0 )
        {
           temp.add( new Integer( i ) );
        }
    }

    mFileList4Simulation     = new String[ temp.size() ];
    mFilesSimulationzahlList = new int[ temp.size() ];
    for (int i=0; i<temp.size(); i++)
    {
        // get the Index (Integer Object)
        Integer JJ = (Integer) temp.elementAt(i);
        // get the real File from the ConfigFileTextFieldList using the Index
        TextField tt = (TextField) mConfigFileTextFieldList.elementAt( JJ.intValue() );
        mFileList4Simulation[ i ] = tt.getName();
        mFilesSimulationzahlList[ i ] = Integer.parseInt( tt.getText() );
    }
    this.hide();
  }

  public String[] getFileList4Simulation()
  {
     return mFileList4Simulation;
  }

  public int[] getFileSimulationZahlList()
  {
     return mFilesSimulationzahlList;
  }

  public void addoneConfigFile( ConfigFileValidation pFile)
  {
    int Highdelta = 30;
    int FieldHigheit= 20;
    int FileNameStartX = 40;
    int FileNameStartY = 80;
    int RepeatTimeStartX = 260;
    int ErrorInfoStartX  = 340;
    int RepeatTimeStartY = 80;
    // calcalute
    int haseditem = mConfigFileTextFieldList.size();

    // links:
    // Output String
    TextField lb = new TextField();
    lb.setText(pFile.mFileName);
    lb.setLocation( FileNameStartX, haseditem*Highdelta + FileNameStartY );
    lb.setSize( 200, FieldHigheit  );
    lb.setEditable( false );
    panel1.add(lb, null);

    TextField tf = new TextField();
    // default times: 0 not selected
    tf.setText("0");
    tf.setName(pFile.mFileName );
    tf.setLocation( RepeatTimeStartX,  haseditem*Highdelta + RepeatTimeStartY );
    tf.setSize(50, FieldHigheit );

    if ( ! pFile.mIsValid )
    {
      tf.setEditable( false );
      Label err = new Label();
      err.setText("Invalid Config File!");
      err.setLocation( ErrorInfoStartX, haseditem*Highdelta + RepeatTimeStartY );
      err.setSize(120, FieldHigheit );
      panel1.add( err, null);
    }

    panel1.add(tf, null);
    mConfigFileTextFieldList.add( tf );

  }

  void jBReset_actionPerformed(ActionEvent e)
  {

    for (int i=0; i<mConfigFileTextFieldList.size(); i++)
    {
        TextField tt = (TextField) mConfigFileTextFieldList.elementAt(i);
        tt.setText("0");
    }

  }


}

class ConfigFileRepeatorDefine_jBOK_actionAdapter implements java.awt.event.ActionListener {
  private ConfigFileRepeatorDefine adaptee;

  ConfigFileRepeatorDefine_jBOK_actionAdapter(ConfigFileRepeatorDefine adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBOK_actionPerformed(e);
  }
}

class ConfigFileRepeatorDefine_jBReset_actionAdapter implements java.awt.event.ActionListener {
  private ConfigFileRepeatorDefine adaptee;

  ConfigFileRepeatorDefine_jBReset_actionAdapter(ConfigFileRepeatorDefine adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBReset_actionPerformed(e);
  }
}