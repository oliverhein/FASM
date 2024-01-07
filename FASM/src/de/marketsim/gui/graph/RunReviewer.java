package de.marketsim.gui.graph;

import java.awt.*;
import java.util.Vector;

import javax.swing.*;
import java.awt.event.*;
import de.marketsim.util.GraphStateFileReader;
import de.marketsim.util.GraphDailyState;
import de.marketsim.util.DailyStatisticOfNetwork;

import de.marketsim.config.NetworkFileLoader;
import de.marketsim.gui.graph.AgentGraphStatusFrame;
import de.marketsim.gui.ScreenImage;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class RunReviewer extends JFrame {

  private GraphStateFileReader mGraphStateFileReader=null;
  private AgentGraphStatusFrame mAgentGraphStatus = null;

  private JLabel jLabel1 = new JLabel();
  private JTextField jTFStateFile = new JTextField();
  private JButton jBSelectDataFile = new JButton();
  private JButton jBBack = new JButton();
  private JButton jBForward = new JButton();
  private JButton jBPlay = new JButton();
  private JLabel jLabel2 = new JLabel();
  private JTextField jTFDelay = new JTextField();
  private JButton jBSaveGraph = new JButton();
  private JButton jBExit = new JButton();
  private JLabel jLabel3 = new JLabel();
  private JTextField jTFCurremtDay = new JTextField();
  private JButton jBStop = new JButton();
  private JLabel jLabel4 = new JLabel();
  private JTextField jTTotalDays = new JTextField();

  private Thread mAutoPlay = null;
  private JButton jBLoad = new JButton();


  public RunReviewer() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    jLabel1.setText("Run state file of the simulation");
    jLabel1.setBounds(new Rectangle(7, 6, 184, 24));
    this.getContentPane().setLayout(null);
    jTFStateFile.setText("<Input or select the state file of Graph>");
    jTFStateFile.setBounds(new Rectangle(6, 27, 224, 23));
    jBSelectDataFile.setBounds(new Rectangle(240, 4, 53, 22));
    jBSelectDataFile.setMargin(new Insets(2, 2, 2, 2));
    jBSelectDataFile.setText("Select");
    jBSelectDataFile.addActionListener(new RunReviewer_jBSelectDataFile_actionAdapter(this));
    jBBack.setBounds(new Rectangle(5, 95, 49, 22));
    jBBack.setMargin(new Insets(2, 2, 2, 2));
    jBBack.setText("Back");
    jBBack.addActionListener(new RunReviewer_jBBack_actionAdapter(this));
    jBForward.setText("Forward");

    jBForward.addActionListener(new RunReviewer_jBForward_actionAdapter(this));

    jBForward.setBounds(new Rectangle(56, 94, 61, 22));
    jBForward.setMargin(new Insets(2, 2, 2, 2));
    jBPlay.setText("Play");
    jBPlay.addActionListener(new RunReviewer_jBPlay_actionAdapter(this));
    jBPlay.setBounds(new Rectangle(119, 94, 45, 22));
    jBPlay.setMargin(new Insets(2, 2, 2, 2));
    jLabel2.setBounds(new Rectangle(6, 53, 89, 24));
    jLabel2.setText("Delay ( in ms )");
    jTFDelay.setText("1000");
    jTFDelay.setBounds(new Rectangle(99, 55, 53, 19));
    jBSaveGraph.setMargin(new Insets(2, 2, 2, 2));
    jBSaveGraph.setBounds(new Rectangle(213, 95, 79, 22));
    jBSaveGraph.setText("Save Graph");
    jBSaveGraph.addActionListener(new RunReviewer_jBSaveGraph_actionAdapter(this));
    jBExit.setMargin(new Insets(2, 2, 2, 2));
    jBExit.setBounds(new Rectangle(240, 51, 53, 22));
    jBExit.setText("Exit");
    jBExit.addActionListener(new RunReviewer_jBExit_actionAdapter(this));
    this.setTitle("Review the Graph of Simulation states ");
    jLabel3.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel3.setText("current day");
    jLabel3.setBounds(new Rectangle(7, 74, 73, 22));
    jTFCurremtDay.setText("1");
    jTFCurremtDay.setBounds(new Rectangle(82, 79, 25, 17));
    jBStop.setMargin(new Insets(2, 2, 2, 2));
    jBStop.setBounds(new Rectangle(165, 94, 45, 22));
    jBStop.setText("Stop");
    jBStop.addActionListener(new RunReviewer_jBStop_actionAdapter(this));
    jLabel4.setBounds(new Rectangle(112, 75, 62, 22));
    jLabel4.setText("Total days");
    jLabel4.setFont(new java.awt.Font("Dialog", 1, 12));
    jTTotalDays.setEditable(false);
    jTTotalDays.setText("0");
    jTTotalDays.setBounds(new Rectangle(179, 77, 34, 19));
    jBLoad.setBounds(new Rectangle(240, 29, 53, 21));
    jBLoad.setMargin(new Insets(2, 2, 2, 2));
    jBLoad.setText("load");
    jBLoad.addActionListener(new RunReviewer_jBLoad_actionAdapter(this));
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(jTFStateFile, null);
    this.getContentPane().add(jBSelectDataFile, null);
    this.getContentPane().add(jBLoad, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jTFDelay, null);
    this.getContentPane().add(jLabel3, null);
    this.getContentPane().add(jTFCurremtDay, null);
    this.getContentPane().add(jLabel4, null);
    this.getContentPane().add(jTTotalDays, null);
    this.getContentPane().add(jBBack, null);
    this.getContentPane().add(jBExit, null);
    this.getContentPane().add(jBForward, null);
    this.getContentPane().add(jBPlay, null);
    this.getContentPane().add(jBStop, null);
    this.getContentPane().add(jBSaveGraph, null);
  }

  public static void main(String[] args)
  {
     RunReviewer pp = new RunReviewer();
     pp.setLocation( (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()-350, (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 200 ) );
     pp.setSize(310,  155);
     pp.setVisible(true);
     pp.setResizable(false);
  }

  void jBExit_actionPerformed(ActionEvent e)
  {
      System.exit(0);
  }

  protected void setPlay(boolean pEnabled)
  {
    this.jBPlay.setEnabled( pEnabled );
  }

  void jBSelectDataFile_actionPerformed(ActionEvent e)
  {
    java.awt.FileDialog  fd = new FileDialog( this );
    fd.setSize(200,200);
    fd.setVisible(true);
    String filename = fd.getFile();
    String dirname = fd.getDirectory();
    if ( filename == null )
    {
       // abbrechen, nichts zu tun
    }
    else
    {
        // read file
        GraphStateFileReader filereader;
        try
        {
          filereader = new GraphStateFileReader( dirname + filename );
        }
        catch (Exception ex)
        {
          JOptionPane.showMessageDialog(this,"Error while reading " +dirname + filename+ ": " + ex.getMessage() ,"Error", JOptionPane.ERROR_MESSAGE);
          return;
        }

        this.mGraphStateFileReader = filereader;
        String networkfile = this.mGraphStateFileReader.getNetworkFile();
        this.jTFCurremtDay.setText(""+1);
        this.jTTotalDays.setText("" +filereader.getDays() );
        this.jTFStateFile.setText( dirname +"/" + filename );
        NetworkFileLoader networkloader = new NetworkFileLoader(networkfile);
        if ( ! networkloader.checknetworkfile() )
        {
          JOptionPane.showMessageDialog(this,"Network file " +networkfile + " seems changed. Now it is not valid." ,"Error", JOptionPane.ERROR_MESSAGE);
          return;
        }
        // init Graph
        if ( this.mAgentGraphStatus != null )
        {
          this.mAgentGraphStatus.setVisible(false);
          this.mAgentGraphStatus = null;
        }

        this.mAgentGraphStatus = new AgentGraphStatusFrame( networkfile );
        this.mAgentGraphStatus.setSize(500,500);
    }
  }

  private void DisplayIndexError( int pLimit)
  {
    JOptionPane.showMessageDialog(this,"Day index can be set between 1 and " + pLimit,"Error", JOptionPane.ERROR_MESSAGE);
    return;
  }

  void jBBack_actionPerformed(ActionEvent e) {
        int index = Integer.parseInt( this.jTFCurremtDay.getText() );
        if ( index > 1 )
        {
          index = index - 1;
          this.jTFCurremtDay.setText(""+index);
          display( index );
        }
  }

  void jBForward_actionPerformed(ActionEvent e)
  {
     this.forward();
  }

  public boolean forward()
  {
    int index = Integer.parseInt( this.jTFCurremtDay.getText() );
    if ( index < this.mGraphStateFileReader.getDays() )
    {
      index = index + 1;
      this.jTFCurremtDay.setText(""+index);
      display( index );
      return true;
    }
    else
    {
      return false;
    }
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

  public void display(int pIndex)
  {
     GraphDailyState dd = this.mGraphStateFileReader.getDailyGraphState( pIndex );
     if ( dd != null )
     {

       this.mAgentGraphStatus.DisplayDailyStatus( dd );

       this.mAgentGraphStatus.setVisible(true);
     }
  }

  void jBPlay_actionPerformed(ActionEvent e)
  {
    int index = Integer.parseInt( this.jTFCurremtDay.getText() );
    if ( (index >0 ) && ( index <= this.mGraphStateFileReader.getDays()  ) )
    {
      this.mAutoPlay = new AutoPlay(index, Integer.parseInt( this.jTFDelay.getText( ) ), this );
      this.mAutoPlay.start();
      this.jBPlay.setEnabled(false);
    }
    else
    {
      DisplayIndexError( this.mGraphStateFileReader.getDays() );
    }
  }

  void jBStop_actionPerformed(ActionEvent e)
  {
    if ( this.mAutoPlay != null )
    {
      this.mAutoPlay.interrupt();
    }
  }

  void jBSaveGraph_actionPerformed( ActionEvent e )
  {
    java.awt.FileDialog  fd = new FileDialog( this );
    fd.setMode( FileDialog.SAVE  );
    fd.setSize(200,200);
    fd.setVisible(true);
    String filename = fd.getFile();
    String dirname = fd.getDirectory();

    if ( filename == null )
    {
       // abbrechen, nichts zu tun
    }
    else
    {
      if ( this.mAgentGraphStatus != null )
      {
        String jpgfile= dirname + "/" + filename;
        int j= jpgfile.indexOf(".");
        if ( j<0)
        {
            jpgfile =  jpgfile+".jpg";
        }

        try
        {
          ScreenImage.createImage( this.mAgentGraphStatus.getGraphPanel().getPureGraphPanel(), jpgfile );
        }
        catch (Exception ex)
        {
          JOptionPane.showMessageDialog(this,"Graph can not be saved to " + jpgfile ,"Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    }
  }

  void jBLoad_actionPerformed(ActionEvent e)
  {

    String filename = this.jTFStateFile.getText();
    GraphStateFileReader filereader;
    try
    {
      filereader = new GraphStateFileReader( filename );
    }
    catch (Exception ex)
    {
      JOptionPane.showMessageDialog(this,"Error while reading " + filename+ ": " + ex.getMessage() ,"Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    this.mGraphStateFileReader = filereader;
    String networkfile = this.mGraphStateFileReader.getNetworkFile();
    this.jTFCurremtDay.setText(""+1);
    this.jTTotalDays.setText("" +filereader.getDays() );
    this.jTFStateFile.setText( filename );
    NetworkFileLoader networkloader = new NetworkFileLoader(networkfile);
    if ( ! networkloader.checknetworkfile() )
    {
      JOptionPane.showMessageDialog(this,"Network file " +networkfile + " seems changed. Now it is not valid." ,"Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    if ( this.mAgentGraphStatus != null )
    {
      this.mAgentGraphStatus.setVisible(false);
      this.mAgentGraphStatus = null;
    }
    this.mAgentGraphStatus = new AgentGraphStatusFrame( networkfile );
    this.mAgentGraphStatus.setSize(500,500);
  }

}

class AutoPlay extends Thread
{
  private RunReviewer adaptee;
  private int mDelay ;
  private int mStartDay ;

  public AutoPlay (int pStartDay, int pDelay, RunReviewer pAdaptee)
  {
    this.mDelay    = pDelay;
    this.adaptee   = pAdaptee;
    this.mStartDay = pStartDay;
  }

  public void run()
  {
     this.adaptee.display( this.mStartDay );
     boolean goon = true;
     while ( (! this.isInterrupted() ) && goon )
     {
        try
        {
           Thread.sleep( this.mDelay );
        }
        catch (Exception ex)
        {
            this.interrupt();
        }
        goon = this.adaptee.forward();
     }
     this.adaptee.setPlay( true );
  }
}

class RunReviewer_jBExit_actionAdapter implements java.awt.event.ActionListener {
  private RunReviewer adaptee;

  RunReviewer_jBExit_actionAdapter(RunReviewer adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBExit_actionPerformed(e);
  }
}

class RunReviewer_jBSelectDataFile_actionAdapter implements java.awt.event.ActionListener {
  private RunReviewer adaptee;

  RunReviewer_jBSelectDataFile_actionAdapter(RunReviewer adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBSelectDataFile_actionPerformed(e);
  }
}

class RunReviewer_jBBack_actionAdapter implements java.awt.event.ActionListener {
  private RunReviewer adaptee;

  RunReviewer_jBBack_actionAdapter(RunReviewer adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBBack_actionPerformed(e);
  }
}

class RunReviewer_jBForward_actionAdapter implements java.awt.event.ActionListener {
  private RunReviewer adaptee;

  RunReviewer_jBForward_actionAdapter(RunReviewer adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBForward_actionPerformed(e);
  }
}

class RunReviewer_jBPlay_actionAdapter implements java.awt.event.ActionListener {
  private RunReviewer adaptee;

  RunReviewer_jBPlay_actionAdapter(RunReviewer adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBPlay_actionPerformed(e);
  }
}

class RunReviewer_jBStop_actionAdapter implements java.awt.event.ActionListener {
  private RunReviewer adaptee;

  RunReviewer_jBStop_actionAdapter(RunReviewer adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBStop_actionPerformed(e);
  }
}

class RunReviewer_jBSaveGraph_actionAdapter implements java.awt.event.ActionListener {
  private RunReviewer adaptee;

  RunReviewer_jBSaveGraph_actionAdapter(RunReviewer adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBSaveGraph_actionPerformed(e);
  }
}

class RunReviewer_jBLoad_actionAdapter implements java.awt.event.ActionListener {
  private RunReviewer adaptee;

  RunReviewer_jBLoad_actionAdapter(RunReviewer adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBLoad_actionPerformed(e);
  }
}