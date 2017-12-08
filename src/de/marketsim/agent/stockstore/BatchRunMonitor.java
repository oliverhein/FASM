package de.marketsim.agent.stockstore;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import de.marketsim.config.ConfigFileCollector;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class BatchRunMonitor extends JFrame {
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel3 = new JLabel();
  private JLabel jLabel5 = new JLabel();

  ConfigExecutionState  mExecutionStateList[];

  public BatchRunMonitor() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void processEvent( AWTEvent e )
  {
    // 201 is the code of Windows Closing
    if ( e.getID()== 201 )
    {
       e = null;
       // So this Event is killed hier
       // The event will not be forwarded further.
       // The window can not be closed by click on the Close Icon.
    }
    else
    {
       super.processEvent(e);
    }
  }

  public static void main(String[] args)
  {

    BatchRunMonitor pp = new BatchRunMonitor();
    pp.setSize(410, 460);
    pp.setVisible( true );

    String ss[] = new String [3];
    ss[0] = "aaa";
    ss[1] = "aaa";
    ss[2] = "aaa";

    pp.setConfigFileList( ss  );
    pp.setState( 0, "finished" );
    pp.setRunnigNo( 2, 3 );

  }
  private void jbInit() throws Exception {
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1.setText("Config File");
    jLabel1.setBounds(new Rectangle(12, 21, 106, 25));
    this.setTitle("Batch Monitor");
    this.getContentPane().setLayout(null);
    jLabel3.setBounds(new Rectangle(217, 20, 87, 25));
    jLabel3.setText("Repeat times");
    jLabel3.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel5.setBounds(new Rectangle(320, 21, 39, 25));
    jLabel5.setText("State");
    jLabel5.setFont(new java.awt.Font("Dialog", 1, 12));


    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(jLabel5, null);
    this.getContentPane().add(jLabel3, null);
  }

  void jBHide_actionPerformed(ActionEvent e)
  {
    // this.setDefaultCloseOperation( 1 );
  }

  public void setConfigFileList(String pConfigList[] )
  {
      this.setSize( 430 , 80 +  pConfigList.length * 25 + 30  );
      this.setResizable( false );
      mExecutionStateList  = new ConfigExecutionState[pConfigList.length];

      int x_filename = 14;
      int x_repeat   = 220;
      int x_state    = 320;

      int y= 45;
      int hh = 20;

      for (int i=0; i<pConfigList.length; i++)
      {
          mExecutionStateList[i] = new ConfigExecutionState();
          mExecutionStateList[i].mFilename.setText( pConfigList[i] );

          mExecutionStateList[i].mFilename.setLocation( x_filename, y );
          mExecutionStateList[i].mFilename.setSize( 200, hh );

          mExecutionStateList[i].mRepeatTimes.setLocation( x_repeat, y );
          mExecutionStateList[i].mRepeatTimes.setSize( 30, hh );


          mExecutionStateList[i].mRepeatTimes.setText( "" + ConfigFileCollector.getSimulationTimesOneConfigFile( pConfigList[i]   ) );

          mExecutionStateList[i].mState.setLocation( x_state, y);
          mExecutionStateList[i].mState.setSize( 90 , hh );


          this.getContentPane().add( mExecutionStateList[i].mFilename, null  );
          this.getContentPane().add( mExecutionStateList[i].mRepeatTimes, null  );
          this.getContentPane().add( mExecutionStateList[i].mState, null  );

          y = y + hh + 5;
      }


  }

  public void setState (int pNo, String pState )
  {
       mExecutionStateList[pNo].mState.setText(pState);
  }

  public void setRunnigNo (int pNo, int pRunNo )
  {
       mExecutionStateList[pNo].mCurrentRunNo.setText(pRunNo+"");
  }

}


class ConfigExecutionState
{
    public Label  mFilename     = new Label();
    public Label  mRepeatTimes  = new Label("1");
    public Label  mState        = new Label("wait to perform");
    public Label  mCurrentRunNo =  new Label("");
}


