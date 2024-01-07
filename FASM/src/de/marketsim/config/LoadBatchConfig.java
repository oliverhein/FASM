package de.marketsim.config;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;

import de.marketsim.config.*;
import de.marketsim.util.*;
import de.marketsim.message.*;
import de.marketsim.SystemConstant;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2005
 * Company:
 * @author
 * @version 1.0
 */

public class LoadBatchConfig extends JFrame {

  JLabel jLabel1 = new JLabel();
  JTextField jTFConfigPath = new JTextField();
  JButton jBCheck = new JButton();
  JButton jBStart = new JButton();
  JButton jBQuit = new JButton();
  JTextArea BlackBoard = new JTextArea();
  private JScrollPane jScrollPane1 = new JScrollPane();

  Agent mMyAgent = null;

  public LoadBatchConfig( Agent pMyAgent )
  {
    this.mMyAgent = pMyAgent;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    String ss = System.getProperty("AGENTCONFIGPATH") ;
    if ( ss != null )
    {
        this.setConfigPath( System.getProperty("AGENTCONFIGPATH")  );
    }

  }

  private void jbInit() throws Exception {
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1.setText("Path of Config Files");
    jLabel1.setBounds(new Rectangle(19, 16, 119, 42));
    this.getContentPane().setLayout(null);

    jTFConfigPath.setText("C:/FASM/run/config");  // Default directory

    jTFConfigPath.setBounds(new Rectangle(134, 25, 338, 26));
    jBCheck.setText("select config files");
    jBCheck.setBounds(new Rectangle(149, 73, 213, 29));
    jBCheck.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jBCheck_actionPerformed(e);
      }
    });
    jBStart.setBounds(new Rectangle(150, 119, 212, 29));
    jBStart.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jBStart_actionPerformed(e);
      }
    });
    jBStart.setText("Start");
    jBQuit.setBounds(new Rectangle(152, 164, 211, 29));
    jBQuit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jBQuit_actionPerformed(e);
      }
    });
    jBQuit.setText("Quit");
    BlackBoard.setBorder(BorderFactory.createLineBorder(Color.black));
    this.setTitle("Batch Configuration Load");
    jScrollPane1.setBounds(new Rectangle(6, 196, 465, 99));
    this.getContentPane().add(jBCheck, null);
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(jTFConfigPath, null);
    this.getContentPane().add(jBStart, null);
    this.getContentPane().add(jBQuit, null);
    this.getContentPane().add(jScrollPane1, null);
    jScrollPane1.getViewport().add(BlackBoard, null);

  }

  void jBCheck_actionPerformed(ActionEvent e)
  {
       // String config Path
       String  configpath = this.jTFConfigPath.getText();
       configpath = configpath.trim();
       if ( configpath == null || configpath.length() == 0 )
       {
            // Error Report
            JOptionPane.showMessageDialog(this,"Config File Path is not defined.",
                                             "Error",
                                             JOptionPane.ERROR_MESSAGE,
                                             null);
            return;
       }

       ConfigFileCollector filecollect;
       try
       {
           filecollect = new ConfigFileCollector( configpath, ".xml" );
       }
       catch (Exception ex)
       {
            // Error Report
          JOptionPane.showMessageDialog(this, ex.getMessage(),
                                             "Error",
                                             JOptionPane.ERROR_MESSAGE,
                                             null);
            return;
       }

       boolean haserror = false;
       BlackBoard.setText("");

       long diskrequired = 0; // in bytes

       ConfigFileRepeatorDefine  confrepeator = new ConfigFileRepeatorDefine(this,"Config fIle repeator definition", false);
       confrepeator.setSize( 480, 400  );

       for ( int i=0; i<filecollect.getXmlFileNumber(); i++ )
       {
          ConfigFileValidation onexmlfile = filecollect.getXmlFile(i);
          confrepeator.addoneConfigFile(  onexmlfile );
       };


       /** jdk 1.5  */
      confrepeator.setModal( true  );
      confrepeator.setVisible( true  );

       // read parameter from Dialog
       filecollect.setConfigFiles4Simulation( confrepeator.getFileList4Simulation(), confrepeator.getFileSimulationZahlList() );

       // reset the index for File
       filecollect.resetFileIndex();

       if ( ! haserror )
       {
           this.jBStart.setEnabled( true );
       }
       else
       {
           this.jBStart.setEnabled( false );
       }
  }

  void jBQuit_actionPerformed(ActionEvent e)
  {
    Vector agentsimulatorAIDlist = AgentSimulatorManagerContainer.getAgentSimulatorManagerAIDs();
    if (  agentsimulatorAIDlist.size() > 0 )
    {
          //send quit command to all CommunicationManager
          ACLMessage aclmsg = new ACLMessage( ACLMessage.INFORM );
          for (int i=0; i<agentsimulatorAIDlist.size(); i++)
          {
             aclmsg.addReceiver( (AID) agentsimulatorAIDlist.elementAt(i) );
          }

          try
          {
            aclmsg.setContentObject( MessageFactory.createQuitCommand() );
          }
          catch (Exception ex)
          {
             ex.printStackTrace();
          }

          this.mMyAgent.send( aclmsg );
          System.out.println("Quit Command is sent");
          //wait for 200 ms
          try
          {
             Thread.sleep(2000);
          }
          catch (Exception ex)
          {

          }
    }
    System.exit(0);
  }

  void jBStart_actionPerformed(ActionEvent e)
  {
       if (  ConfigFileCollector.mConfigFileList4Simulation == null )
       {
         JOptionPane.showMessageDialog(this,"Config File has not been checked from directory.","Error", JOptionPane.ERROR_MESSAGE);
         return;
       }

       if (  ConfigFileCollector.mConfigFileList4Simulation.length == 0 )
       {
         JOptionPane.showMessageDialog(this,"No Config File is selected for simulation.","Error", JOptionPane.ERROR_MESSAGE);
         return;
       }

       Vector agentsimulatorAIDlist  = AgentSimulatorManagerContainer.getAgentSimulatorManagerAIDs();

       if ( agentsimulatorAIDlist.size() == 0 )
       {
         JOptionPane.showMessageDialog(this,"No AgentSimulator is found. Please start AgentSimulator.","Error", JOptionPane.ERROR_MESSAGE);
         return;
       }

       this.BlackBoard.append("give start signal to TaskController"+ "\r\n");

       // Give the start signal to TaskController,
       StartBatchSignal.mStartSignal = 0;
       this.hide();
  }

  public void makeVisible()
  {
    this.setSize(510,340);
    this.setResizable( false );
    this.setVisible(true);
  }

  public void setConfigPath(String pPATH)
  {
     this.jTFConfigPath.setText( pPATH );
  }


  public static void main(String[] args)
  {
    // Start Selb Test
    LoadBatchConfig pp = new LoadBatchConfig( null );
    pp.makeVisible();

  }


}