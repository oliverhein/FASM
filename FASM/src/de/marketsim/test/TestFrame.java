package de.marketsim.test;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.*;
import java.io.File;
import de.marketsim.util.FileTool;
import de.marketsim.util.HelpTool;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class TestFrame extends JFrame
{
  private JTextField jTFReciever = new JTextField();
  private JLabel jLabel1 = new JLabel();
  private JTextField jTFMessage = new JTextField();
  private JLabel jLabel2 = new JLabel();
  private JButton jBSenden = new JButton();
  private JLabel jLabel3 = new JLabel();
  private JTextField jTFName = new JTextField();

  SimpleBehavior mBH = null;
  private JButton jButton1 = new JButton();
  private JButton jButton2 = new JButton();
  private JButton jButton3 = new JButton();
  private JTable jTable1 = new JTable();


  public TestFrame()
  {
    try {
      jbInit();
      /*TableColumn TC = new TableColumn();
      TC.getCellEditor().s
      jTable1.addColumn();
      */
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }


  public void setMyname(String pName )
  {
     this.jTFName.setText( pName );
  }

  public void setBehavior(SimpleBehavior pBH )
  {
     this.mBH=pBH;
  }

  public static void main(String[] args) {
    TestFrame pp = new TestFrame();
    pp.setSize(300,300);
    pp.setVisible(true);



  }
  private void jbInit() throws Exception {
    jTFReciever.setText("AAAA");
    jTFReciever.setBounds(new Rectangle(106, 58, 107, 29));
    this.getContentPane().setLayout(null);
    jLabel1.setText("Reciever");
    jLabel1.setBounds(new Rectangle(36, 51, 83, 35));
    jTFMessage.setText("Hallo");
    jTFMessage.setBounds(new Rectangle(109, 96, 104, 26));
    jLabel2.setText("Message");
    jLabel2.setBounds(new Rectangle(32, 95, 58, 29));
    jBSenden.setBackground(Color.pink);
    jBSenden.setBounds(new Rectangle(39, 133, 169, 40));
    jBSenden.setText("Senden");
    jBSenden.addActionListener(new TestFrame_jBSenden_actionAdapter(this));
    jLabel3.setText("My Name");
    jLabel3.setBounds(new Rectangle(33, 22, 65, 22));
    jTFName.setEditable(false);
    jTFName.setText("XXXXX");
    jTFName.setBounds(new Rectangle(104, 23, 107, 29));
    jButton1.setBounds(new Rectangle(40, 197, 93, 35));
    jButton1.setText("jButton1");
    jButton1.addActionListener(new TestFrame_jButton1_actionAdapter(this));
    jButton2.setBounds(new Rectangle(145, 199, 81, 33));
    jButton2.setText("jButton2");
    jButton2.addActionListener(new TestFrame_jButton2_actionAdapter(this));
    jButton3.setBounds(new Rectangle(240, 137, 80, 30));
    jButton3.setText("jButton3");
    jButton3.addActionListener(new TestFrame_jButton3_actionAdapter(this));
    jTable1.setToolTipText("");
    jTable1.setCellSelectionEnabled(true);
    jTable1.setTableHeader(null);
    jTable1.setBounds(new Rectangle(248, 38, 91, 54));
    this.getContentPane().add(jTFMessage, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jTFReciever, null);
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(jLabel3, null);
    this.getContentPane().add(jTFName, null);
    this.getContentPane().add(jBSenden, null);
    this.getContentPane().add(jButton1, null);
    this.getContentPane().add(jButton2, null);
    this.getContentPane().add(jButton3, null);
    this.getContentPane().add(jTable1, null);
  }

  void jBSenden_actionPerformed(ActionEvent e)
  {
      String who = this.jTFReciever.getText();
      /*
      String msg = this.jTFMessage.getText();
      this.mBH.sendMessage(who, msg  );
      */
      //System.out.println( CreatChangeSumOfRun( who ) );

      String ss = ( 1==2 )? ("AAA") : ("BBB");

      String name1 = "V1";
      String name2 = "V20";

      int root1 = (int) name1.charAt(0) +  (int) name1.charAt(1);
      int root2 = (int) name2.charAt(0) +  (int )name2.charAt(1) + name2.charAt(2);
      System.out.println( root1+ ",  " + root2 );

      java.util.Random rd1 = new java.util.Random( root1 );
      java.util.Random rd2 = new java.util.Random( root2 );

      int dd1[] = new int[100];
      int dd2[] = new int[100];

      for ( int i=0; i<100; i++)
      {
          dd1[i] = rd1.nextInt(100);
          dd2[i] = rd2.nextInt(100);
          System.out.println( dd1[i]+ ",  " + dd2[i]);
      }

  }

  private int  CreatChangeSumOfRun(String pRunChangeStatistic)
  {
      if ( pRunChangeStatistic.length() == 0 )
      {
        return 0;
      }
      int sum = 0;
      String ss =pRunChangeStatistic;
      while ( ss.length() > 0 )
      {
          int j= ss.indexOf(";");
          String tt = ss.substring(0,j);
          sum = sum + Integer.parseInt(tt);
          ss = ss.substring(j+1);
      }
      return sum;
  }


  void jButton1_actionPerformed(ActionEvent e)
  {
    String ss2 = "true";
    System.out.println( "ss=" + Boolean.valueOf(ss2).booleanValue() );


    String ss1 = "config\\abc.txt";

    ss1 = ss1.replace('\\','/');

    System.out.println("ss="+ss1 );


    File dir1 = new File (".");
    File dir2 = new File ("..");
    String CurrentDir =  " ";
    try {

       System.out.println ("Current dir : " + dir1.getCanonicalPath());
       System.out.println ("Parent  dir : " + dir2.getCanonicalPath());

       CurrentDir = dir1.getCanonicalPath();
       }
     catch(Exception ex) {
       ex.printStackTrace();
       }

    String ss =CurrentDir+  "/run/reports/2006-09-10_12_35_01dialog/testserien.html";
    try
    {
       Runtime.getRuntime().exec("iexplore.exe " + ss);
    }
    catch (Exception ex)
    {
       ex.printStackTrace();
    }

  }

  void jButton2_actionPerformed(ActionEvent e)
  {
     String ss = this.jTFReciever.getText();
     System.out.println("Reci=" + ss);
     String ss1 = HelpTool.TrimString(ss, ' ');
     System.out.println("Reci=" + ss1);

  }

  void jButton3_actionPerformed(ActionEvent e)
  {
    // JOptionPane.showMessageDialog(this, "NoiseTrader Order-Menge enthalten falsche Werten. Bitte zu NoiseTrader Panel wechseln und korrigieren","Error", JOptionPane.OK_CANCEL_OPTION );
    // java.util.Locale  ll = new java.util.Locale("USA");
    // int p = JOptionPane.showConfirmDialog(this, "Do you want really to quit?","Choice", JOptionPane.YES_NO_OPTION);

    // Open a FileDialog for saving file
    JFrame pp = new JFrame();
    java.awt.FileDialog  fd = new java.awt.FileDialog( pp ,"select directory",  java.awt.FileDialog.SAVE );

    fd.setFile("abc");

    fd.setSize(200,200);
    fd.setVisible(true);

    String filename = fd.getFile();
    String dirname = fd.getDirectory();

    System.out.println("dirname="+dirname);

  }

}

class TestFrame_jBSenden_actionAdapter implements java.awt.event.ActionListener {
  private TestFrame adaptee;

  TestFrame_jBSenden_actionAdapter(TestFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBSenden_actionPerformed(e);
  }
}

class TestFrame_jButton1_actionAdapter implements java.awt.event.ActionListener {
  private TestFrame adaptee;

  TestFrame_jButton1_actionAdapter(TestFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton1_actionPerformed(e);
  }
}

class TestFrame_jButton2_actionAdapter implements java.awt.event.ActionListener {
  private TestFrame adaptee;

  TestFrame_jButton2_actionAdapter(TestFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton2_actionPerformed(e);
  }
}

class TestFrame_jButton3_actionAdapter implements java.awt.event.ActionListener {
  private TestFrame adaptee;

  TestFrame_jButton3_actionAdapter(TestFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton3_actionPerformed(e);
  }
}