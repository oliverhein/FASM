package de.marketsim.gui.graph;

import javax.swing.*;
import java.awt.*;

/**
 * <p>Überschrift: FASM Frankfurt Articial Simulation Market</p>
 * <p>Beschreibung: Mircomarket Simulator </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class PanelGraphStatistic extends JPanel
{
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JLabel jLabel3 = new JLabel();
  private JTextField jTF_Investor = new JTextField();
  private JTextField jTF_NoiseTrader = new JTextField();
  private JTextField jTF_BlankoActivated = new JTextField();
  private JLabel jLabel4 = new JLabel();
  private JLabel jLabel5 = new JLabel();
  private JTextField jTF_BlankoDeactivated = new JTextField();
  private JTextField jTF_BlankoTotal = new JTextField();
  private JTextField jTF_InvestorChanged = new JTextField();
  private JTextField jTF_NoiseTraderChanged = new JTextField();
  private JLabel jLabel7 = new JLabel();
  private JLabel jLabel8 = new JLabel();

  public PanelGraphStatistic()
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
    Frame ff = new Frame();
    PanelGraphStatistic pp = new PanelGraphStatistic();
    ff.add( pp );
    ff.setSize(300,300);
    ff.setVisible(true);
  }

  private void jbInit() throws Exception {
    jTF_BlankoDeactivated.setBounds(new Rectangle(303, 41, 28, 19));
    jTF_BlankoDeactivated.setText("0");
    jLabel5.setBounds(new Rectangle(220, 42, 75, 18));
    jLabel5.setText("deactivated");
    jLabel5.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel4.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel4.setText("activated");
    jLabel4.setBounds(new Rectangle(120, 40, 61, 18));
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1.setText("Fundamental");
    jLabel1.setBounds(new Rectangle(5, 15, 84, 18));
    this.setLayout(null);
    jLabel2.setBounds(new Rectangle(172, 14, 41, 18));
    jLabel2.setText("Trend");
    jLabel2.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel3.setBounds(new Rectangle(5, 37, 51, 18));
    jLabel3.setText("Retail ");
    jLabel3.setFont(new java.awt.Font("Monospaced", 1, 12));
    jTF_Investor.setText("0");
    jTF_Investor.setBounds(new Rectangle(88, 14, 32, 19));
    jTF_NoiseTrader.setBounds(new Rectangle(214, 14, 28, 19));
    jTF_NoiseTrader.setText("0");
    jTF_BlankoActivated.setBounds(new Rectangle(178, 41, 36, 19));
    jTF_BlankoActivated.setText("0");
    jTF_BlankoTotal.setText("0");
    jTF_BlankoTotal.setBounds(new Rectangle(77, 39, 36, 19));
    jTF_InvestorChanged.setBounds(new Rectangle(137, 15, 33, 19));
    jTF_InvestorChanged.setText("0");
    jTF_NoiseTraderChanged.setText("0");
    jTF_NoiseTraderChanged.setBounds(new Rectangle(259, 14, 29, 19));
    jLabel7.setBounds(new Rectangle(123, 15, 11, 16));
    jLabel7.setText("/");
    this.setToolTipText("");
    jLabel8.setText("/");
    jLabel8.setBounds(new Rectangle(246, 16, 11, 16));
    this.add(jLabel1, null);
    this.add(jLabel3, null);
    this.add(jTF_BlankoTotal, null);
    this.add(jLabel4, null);
    this.add(jTF_BlankoActivated, null);
    this.add(jLabel5, null);
    this.add(jTF_BlankoDeactivated, null);
    this.add(jTF_NoiseTraderChanged, null);
    this.add(jLabel2, null);
    this.add(jLabel8, null);
    this.add(jTF_NoiseTrader, null);
    this.add(jTF_InvestorChanged, null);
    this.add(jLabel7, null);
    this.add(jTF_Investor, null);
  }

  /**
   * set the total number of InvestorAgent
   * @param pNumber
   */

  public void setInvestor(int pNumber)
  {
     this.jTF_Investor.setText("" + pNumber);
  }

  /**
   * set the change of number of InvestorAgent
   * +: Increase
   * -: Decrease
   * @param pNumber
   */

  public void setInvestorChanged(int pNumber)
  {
     this.jTF_InvestorChanged.setText("" + pNumber);
  }

  /**
   * set the total number of NoiseTraderAgent
   * @param pNumber
   */

  public void setNoiseTrader(int pNumber)
  {
     this.jTF_NoiseTrader.setText("" + pNumber);
  }

  /**
   * set the change of number of NoiseTraderAgent
   * +: Increase
   * -: Decrease
   * @param pNumber
   */

  public void setNoiseTraderChanged(int pNumber)
  {
     this.jTF_NoiseTraderChanged.setText("" + pNumber);
  }

  /**
   * set the total number of BlankoAgent
   * @param pNumber
   */
  public void setBlanko(int pNumber)
  {
     this.jTF_BlankoTotal.setText("" + pNumber);
  }

  /**
   * set the Number of BlankoAgent which becomes active
   * @param pNumber
   */

  public void setBlankoJustActivated(int pNumber)
  {
     this.jTF_BlankoActivated.setText("" + pNumber);
  }

  /**
   * set the Agent Name list which become active
   * The namelist is saved into jTF_BlankoActive as a ToolTipText
   * when the mouse moves on it.
   * @param pNameList
   */

  public void setBlankoActivatedAgent(String pNameList)
  {
     this.jTF_BlankoActivated.setToolTipText(pNameList);
  }

  /**
   * set the Number of BlankoAgent which becomes Inactive
   * @param pNumber
   */

  public void setBlankoDeactivated(int pNumber)
  {
     this.jTF_BlankoDeactivated.setText("" + pNumber);
  }

  /**
   * set the Agent Name list which become Inactive
   * The namelist is saved into jTF_BlankoInactive as a ToolTipText
   * when the mouse moves on it.
   * @param pNameList
   */

  public void setBlankoDeactivatedAgent(String pNameList)
  {
     this.jTF_BlankoDeactivated.setToolTipText(pNameList);
  }


}