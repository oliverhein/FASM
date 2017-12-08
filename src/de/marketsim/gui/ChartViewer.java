package de.marketsim.gui;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;

public class ChartViewer extends JApplet
{
  boolean isStandalone = false;
  JButton jBTest = new JButton();
  JTextField jTFFilePath = new JTextField();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea jTextArea1 = new JTextArea();
  /**Get a parameter value*/
  public String getParameter(String key, String def)
  {
    return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
  }

  /**Construct the applet*/
  public ChartViewer()
  {
  }
  /**Initialize the applet*/
  public void init()
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
  /**Component initialization*/
  private void jbInit() throws Exception
  {
    jBTest.setText("Check");
    jBTest.setBounds(new Rectangle(18, 108, 111, 39));
    jBTest.addActionListener(new ChartViewer_jBTest_actionAdapter(this));
    this.setSize(new Dimension(548, 341));
    this.getContentPane().setLayout(null);
    jTFFilePath.setBounds(new Rectangle(21, 169, 107, 41));
    jScrollPane1.setBounds(new Rectangle(228, 41, 240, 273));
    jTextArea1.setText("jTextArea1");
    this.getContentPane().add(jBTest, null);
    this.getContentPane().add(jTFFilePath, null);
    this.getContentPane().add(jScrollPane1, null);
    jScrollPane1.getViewport().add(jTextArea1, null);
  }
  /**Get Applet information*/
  public String getAppletInfo()
  {
    return "Applet Information";
  }
  /**Get parameter info*/
  public String[][] getParameterInfo()
  {
    return null;
  }

  //static initializer for setting look & feel
  static
  {
    try
    {
      //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    catch(Exception e)
    {
    }
  }

  void jBTest_actionPerformed(ActionEvent e)
  {
      // do something
      String ss = this.getParameter("DataPath");
      this.jTFFilePath.setText(ss);

      java.io.InputStream ins  = null;
      try
      {
         ins = java.lang.ClassLoader.getSystemResourceAsStream(  ss  );
      }
      catch (Exception ex)
      {
          ex.printStackTrace();
          this.jTextArea1.append( "Exception ClassName " + ex.getClass().getName()+" \r\n" );
          this.jTextArea1.append( ex.getMessage() +"\r\n" );
      }

      try
      {

         this.jTextArea1.append( ins.available() +" bytes \r\n" );

         //   java.io.BufferedReader rd = new  java.io.BufferedReader( new java.io.FileReader ( ss ) );
         java.io.BufferedReader rd = new  java.io.BufferedReader( new java.io.InputStreamReader ( ins )   );
        String ss1 = rd.readLine();

        while ( ss1 != null )
        {
           this.jTextArea1.append( ss1 +"\r\n" );
           ss1 = rd.readLine();
        }

      }
      catch (Exception ex)
      {
          this.jTextArea1.append( "Exception " + ex.getClass().getName() +"\r\n" );
          this.jTextArea1.append( ex.getMessage() +"\r\n" );

      }



  }
}

class ChartViewer_jBTest_actionAdapter implements java.awt.event.ActionListener
{
  ChartViewer adaptee;

  ChartViewer_jBTest_actionAdapter(ChartViewer adaptee)
  {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e)
  {
    adaptee.jBTest_actionPerformed(e);
  }
}