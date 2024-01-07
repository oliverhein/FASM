package de.marketsim.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import de.marketsim.config.*;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class AgentParameterPanel extends JPanel
{

  private JLabel jLabel1 = new JLabel();
  private JTextField jTFPeriod = new JTextField();
  private JLabel jLabel2 = new JLabel();
  private JLabel jLabel3 = new JLabel();
  private JLabel jLabel4 = new JLabel();
  private JLabel jLabel5 = new JLabel();
  private JLabel jLabel6 = new JLabel();
  private JTextField jTFMAXInnererwert = new JTextField();
  private JTextField jTFMINInnererwert = new JTextField();
  private JTextField jTFBeginInnererwert = new JTextField();
  private JTextField jTFInvestor_R2_Abschlag = new JTextField();
  private JTextField jTFInvestor_R3_Abschlag = new JTextField();
  private JLabel jLabel7 = new JLabel();
  private JLabel jLabel8 = new JLabel();
  private JButton jBOK = new JButton();
  private JLabel jLabel9 = new JLabel();
  private JRadioButton jRadioButtonAufsteigen = new JRadioButton();
  private JRadioButton jRadioButtonAbsteigen = new JRadioButton();
  private ButtonGroup buttonGroup1 = new ButtonGroup();
  private JLabel jLabel10 = new JLabel();
  private JTextField jTFInnererWertMaximalTagAbweichnung = new JTextField();
  private JLabel jLabel11 = new JLabel();
  private JLabel jLabel12 = new JLabel();
  private JLabel jLabel13 = new JLabel();
  private JTextField jTFRandomTraderMinMenge = new JTextField();
  private JTextField jTFRandomTraderMaxMenge = new JTextField();
  private JLabel jLabel26 = new JLabel();
  private ButtonGroup buttonGroup2 = new ButtonGroup();
  private JRadioButton jRNachverlust = new JRadioButton();
  private JRadioButton jRBestgewinner = new JRadioButton();
  private JLabel jLabel14 = new JLabel();
  private JTextField jTFGewinnRefernztag = new JTextField();
  private JLabel jLabel15 = new JLabel();
  private JLabel jLabel27 = new JLabel();
  private JTextField jTFHillEstimatorProzent = new JTextField();
  private JLabel jLabel16 = new JLabel();
  private JLabel jLabel17 = new JLabel();
  private JLabel jLabel18 = new JLabel();
  private JLabel jLabel19 = new JLabel();
  private JLabel jLabel110 = new JLabel();
  private JLabel jLabel111 = new JLabel();
  private JLabel jLabel112 = new JLabel();
  private JLabel jLabel113 = new JLabel();
  private JLabel jLabel114 = new JLabel();
  private JTextField jTFInvestorR1MinMenge = new JTextField();
  private JTextField jTFInvestorR2MinMenge = new JTextField();
  private JTextField jTFInvestorR3MinMenge = new JTextField();
  private JTextField jTFNoiseTraderR1MinMenge = new JTextField();
  private JTextField jTFNoiseTraderR2MinMenge = new JTextField();
  private JTextField jTFNoiseTraderR3MinMenge = new JTextField();
  private JTextField jTFInvestorR1MaxMenge = new JTextField();
  private JTextField jTFInvestorR2MaxMenge = new JTextField();
  private JTextField jTFInvestorR3MaxMenge = new JTextField();
  private JTextField jTFNoiseTraderR1MaxMenge = new JTextField();
  private JTextField jTFNoiseTraderR2MaxMenge = new JTextField();
  private JTextField jTFNoiseTraderR3MaxMenge = new JTextField();

  public AgentParameterPanel(String title, boolean modal)
  {
    //super(title, modal);
    try {
      jbInit();
      //pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public AgentParameterPanel()
  {
    this("", false);
  }

  private void jbInit() throws Exception
  {
    jLabel2.setBounds(new Rectangle(29, 62, 230, 31));
    jLabel2.setText("Maximum von InnererWert");
    jLabel2.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel3.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel3.setText("Minimum von InnererWert");
    jLabel3.setBounds(new Rectangle(29, 90, 230, 31));
    jLabel4.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel4.setText("Begin von InnererWert");
    jLabel4.setBounds(new Rectangle(29, 116, 230, 31));
    jLabel5.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel5.setText("Abschlag-Prozent: Investor-Rule 2 ");
    jLabel5.setBounds(new Rectangle(28, 219, 230, 31));
    jLabel6.setBounds(new Rectangle(29, 247, 230, 31));
    jLabel6.setText("Abschlag-Prozent: Investor-Rule 3");
    jLabel6.setFont(new java.awt.Font("Dialog", 1, 12));
    jTFMAXInnererwert.setBounds(new Rectangle(269, 66, 53, 22));
    jTFMAXInnererwert.setText("2000");
    jTFMINInnererwert.setText("500");
    jTFMINInnererwert.setBounds(new Rectangle(270, 91, 53, 22));
    jTFBeginInnererwert.setBounds(new Rectangle(269, 118, 53, 22));
    jTFBeginInnererwert.setText("1000");
    jTFInvestor_R2_Abschlag.setBounds(new Rectangle(267, 221, 53, 22));
    jTFInvestor_R2_Abschlag.setText("10");
    jTFInvestor_R3_Abschlag.setText("20");
    jTFInvestor_R3_Abschlag.setBounds(new Rectangle(266, 250, 53, 22));
    jLabel7.setText("%");
    jLabel7.setBounds(new Rectangle(322, 253, 20, 17));
    jLabel8.setBounds(new Rectangle(323, 224, 19, 17));
    jLabel8.setText("%");

    this.setLayout(null);

    jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1.setText("Tage von Period ueber InnererWert");
    jLabel1.setBounds(new Rectangle(30, 35, 230, 31));
    jTFPeriod.setText("1500");
    jTFPeriod.setBounds(new Rectangle(270, 39, 53, 22));
    jBOK.setBounds(new Rectangle(145, 609, 96, 31));

    jLabel9.setBounds(new Rectangle(30, 156, 192, 31));
    jLabel9.setText("InnererWertEntwicklungsTrend ");
    jLabel9.setFont(new java.awt.Font("Dialog", 1, 12));

    jRadioButtonAufsteigen.setText("Aufsteigen");
    jRadioButtonAufsteigen.setBounds(new Rectangle(262, 146, 92, 19));
    jRadioButtonAufsteigen.setActionCommand("AUFSTEIGEN" );

    jRadioButtonAbsteigen.setText("Absteigen");
    jRadioButtonAbsteigen.setBounds(new Rectangle(262, 170, 90, 20));
    jRadioButtonAbsteigen.setActionCommand("ABSTEIGEN" );

    jLabel10.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel10.setText("MaximalAbweichung gegen Vortag");
    jLabel10.setBounds(new Rectangle(29, 188, 222, 31));
    jTFInnererWertMaximalTagAbweichnung.setText("12");
    jTFInnererWertMaximalTagAbweichnung.setBounds(new Rectangle(267, 195, 53, 22));
    jLabel11.setText("%");
    jLabel11.setBounds(new Rectangle(324, 199, 19, 17));
    jLabel12.setBounds(new Rectangle(57, 415, 137, 25));
    jLabel12.setText("RandomRandomTrder");
    jLabel12.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel13.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel13.setText("Ordermenge-Definitionen ------------");
    jLabel13.setBounds(new Rectangle(24, 388, 226, 31));
    jTFRandomTraderMinMenge.setBounds(new Rectangle(226, 415, 33, 22));
    jTFRandomTraderMinMenge.setText("3");
    jTFRandomTraderMaxMenge.setText("20");
    jTFRandomTraderMaxMenge.setBounds(new Rectangle(276, 415, 40, 22));
    jLabel26.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel26.setText("Typwechselstrategie");
    jLabel26.setBounds(new Rectangle(26, 298, 158, 31));
    jRNachverlust.setActionCommand("VERLUST");
    jRNachverlust.setText("nur nach N-mal Verlust");
    jRNachverlust.setBounds(new Rectangle(219, 303, 167, 25));
    jRBestgewinner.setActionCommand("BESTENGEWINNER");
    jRBestgewinner.setText("zum besten Gewinner");
    jRBestgewinner.setBounds(new Rectangle(218, 328, 174, 22));
    jLabel14.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel14.setText("Gewinn-berechnen gegenueber  dem vorletzten ");
    jLabel14.setBounds(new Rectangle(28, 275, 278, 31));
    jTFGewinnRefernztag.setBounds(new Rectangle(315, 280, 30, 22));
    jTFGewinnRefernztag.setText("50");
    jLabel15.setBounds(new Rectangle(359, 275, 32, 28));
    jLabel15.setText("Day");
    jLabel15.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel27.setBounds(new Rectangle(24, 348, 192, 31));
    jLabel27.setText("Hill-Estimator Prozent von Price");
    jLabel27.setFont(new java.awt.Font("Dialog", 1, 12));
    jTFHillEstimatorProzent.setText("5");
    jTFHillEstimatorProzent.setBounds(new Rectangle(219, 354, 29, 22));
    jLabel16.setBounds(new Rectangle(250, 356, 20, 17));
    jLabel16.setText("%");
    jLabel17.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel17.setText("Minimal ");
    jLabel17.setBounds(new Rectangle(224, 387, 54, 28));
    jLabel18.setBounds(new Rectangle(276, 388, 54, 25));
    jLabel18.setText("Maximal");
    jLabel18.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel19.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel19.setText("Fundenmental Investor R1 ");
    jLabel19.setBounds(new Rectangle(57, 440, 156, 25));
    jLabel110.setBounds(new Rectangle(57, 465, 156, 25));
    jLabel110.setText("Fundenmental Investor R2");
    jLabel110.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel111.setBounds(new Rectangle(56, 490, 156, 25));
    jLabel111.setText("Fundenmental Investor R3");
    jLabel111.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel112.setBounds(new Rectangle(55, 516, 151, 25));
    jLabel112.setText("Noise Trader R1 --------------");
    jLabel112.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel113.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel113.setText("Noise Trader R2 --------------");
    jLabel113.setBounds(new Rectangle(56, 539, 151, 25));
    jLabel114.setBounds(new Rectangle(58, 561, 151, 25));
    jLabel114.setText("Noise Trader R3 --------------");
    jLabel114.setFont(new java.awt.Font("Dialog", 1, 12));
    jTFInvestorR1MinMenge.setText("3");
    jTFInvestorR1MinMenge.setBounds(new Rectangle(227, 442, 33, 22));
    jTFInvestorR2MinMenge.setText("3");
    jTFInvestorR2MinMenge.setBounds(new Rectangle(226, 467, 33, 22));
    jTFInvestorR3MinMenge.setText("3");
    jTFInvestorR3MinMenge.setBounds(new Rectangle(226, 492, 33, 22));
    jTFNoiseTraderR1MinMenge.setText("3");
    jTFNoiseTraderR1MinMenge.setBounds(new Rectangle(226, 518, 33, 22));
    jTFNoiseTraderR2MinMenge.setText("3");
    jTFNoiseTraderR2MinMenge.setBounds(new Rectangle(226, 542, 33, 22));
    jTFNoiseTraderR3MinMenge.setText("3");
    jTFNoiseTraderR3MinMenge.setBounds(new Rectangle(226, 566, 33, 22));
    jTFInvestorR1MaxMenge.setText("3");
    jTFInvestorR1MaxMenge.setBounds(new Rectangle(276, 443, 33, 22));
    jTFInvestorR2MaxMenge.setText("3");
    jTFInvestorR2MaxMenge.setBounds(new Rectangle(276, 469, 33, 22));
    jTFInvestorR3MaxMenge.setText("3");
    jTFInvestorR3MaxMenge.setBounds(new Rectangle(276, 494, 33, 22));
    jTFNoiseTraderR1MaxMenge.setText("3");
    jTFNoiseTraderR1MaxMenge.setBounds(new Rectangle(276, 519, 33, 22));
    jTFNoiseTraderR2MaxMenge.setText("3");
    jTFNoiseTraderR2MaxMenge.setBounds(new Rectangle(276, 543, 33, 22));
    jTFNoiseTraderR3MaxMenge.setText("3");
    jTFNoiseTraderR3MaxMenge.setBounds(new Rectangle(276, 567, 33, 22));

    this.add(jLabel1, null);
    this.add(jTFPeriod, null);
    this.add(jTFMAXInnererwert, null);
    this.add(jLabel2, null);
    this.add(jLabel3, null);
    this.add(jLabel4, null);
    this.add(jLabel9, null);
    this.add(jLabel10, null);
    this.add(jLabel5, null);
    this.add(jLabel6, null);
    this.add(jTFInvestor_R3_Abschlag, null);
    this.add(jTFInvestor_R2_Abschlag, null);
    this.add(jTFInnererWertMaximalTagAbweichnung, null);
    this.add(jRadioButtonAbsteigen, null);
    this.add(jRadioButtonAufsteigen, null);
    this.add(jTFMINInnererwert, null);
    this.add(jTFBeginInnererwert, null);
    this.add(jLabel11, null);
    this.add(jLabel8, null);
    this.add(jLabel7, null);
    buttonGroup1.add(jRadioButtonAufsteigen);
    buttonGroup1.add(jRadioButtonAbsteigen);
    buttonGroup2.add(jRNachverlust);
    buttonGroup2.add(jRBestgewinner);
    this.add(jLabel14, null);
    this.add(jTFGewinnRefernztag, null);
    this.add(jLabel15, null);
    this.add(jLabel26, null);
    this.add(jRNachverlust, null);
    this.add(jRBestgewinner, null);
    this.add(jLabel27, null);
    this.add(jTFHillEstimatorProzent, null);
    this.add(jLabel16, null);
    this.add(jBOK, null);
    this.add(jLabel13, null);
    this.add(jLabel17, null);
    this.add(jLabel18, null);
    this.add(jTFRandomTraderMaxMenge, null);
    this.add(jTFRandomTraderMinMenge, null);
    this.add(jLabel12, null);
    this.add(jLabel19, null);
    this.add(jLabel110, null);
    this.add(jLabel111, null);
    this.add(jLabel112, null);
    this.add(jTFInvestorR1MinMenge, null);
    this.add(jTFInvestorR2MinMenge, null);
    this.add(jTFInvestorR3MinMenge, null);
    this.add(jTFNoiseTraderR1MinMenge, null);
    this.add(jTFNoiseTraderR2MinMenge, null);
    this.add(jTFNoiseTraderR3MinMenge, null);
    this.add(jTFInvestorR1MaxMenge, null);
    this.add(jTFInvestorR2MaxMenge, null);
    this.add(jTFInvestorR3MaxMenge, null);
    this.add(jTFNoiseTraderR1MaxMenge, null);
    this.add(jTFNoiseTraderR2MaxMenge, null);
    this.add(jTFNoiseTraderR3MaxMenge, null);
    this.add(jLabel113, null);
    this.add(jLabel114, null);

  }

  /**
   * pTrend = 1; Aufsteigen
   *        = 0; Absteigen
   * @param pTrend
   */
  public void setAuf_Ab_Steigen( int pTrend  )
  {
    //Aufsteigen
    if ( pTrend == 1 )
    {
      buttonGroup1.setSelected( this.jRadioButtonAufsteigen.getModel(), true );
    }
    else
    {
      buttonGroup1.setSelected( this.jRadioButtonAbsteigen.getModel(), true );
    }
  }

  /**
   * pStrategie = 1; NACHVERLUST
   *            = 2; zu Besten Gewinner
   * @param pStrategie
   */
  public void setTypwechselStrategie( int pStrategie )
  {
    //Aufsteigen
    if ( pStrategie == 1 )
    {
      buttonGroup2.setSelected( this.jRNachverlust.getModel(), true );
    }
    else
    {
      buttonGroup2.setSelected( this.jRBestgewinner.getModel(), true );
    }
  }

  void jBOK_actionPerformed(ActionEvent e)
  {
       int p1 = Integer.parseInt( this.jTFBeginInnererwert.getText() );
       int p2 = Integer.parseInt( this.jTFMAXInnererwert.getText() );
       int p3 = Integer.parseInt( this.jTFMINInnererwert.getText() );
       if ( ( p1 >p2 ) || ( p1<p3 ))
       {
         JOptionPane.showMessageDialog(this,"BeginWert von InnererWert ist nicht gueltig.","Error", JOptionPane.ERROR_MESSAGE);
         return;
       }
       else
       {
         this.setVisible(false);
       }
  }

  /////////////////////////////////////////////////////////////////////
  /// setter Methode

  public void setDaysOfOnePeriod(int pDays)
  {
     this.jTFPeriod.setText( "" +pDays );
  }

  public void setMaxInnererWert(int pMaxInnererWert)
  {
     this.jTFMAXInnererwert.setText( "" + pMaxInnererWert );
  }

  public void setMinInnererWert(int pMinInnererWert)
  {
     this.jTFMINInnererwert.setText( "" +pMinInnererWert );
  }

  public void setBeginInnererWert(int pBeginInnererWert)
  {
     this.jTFBeginInnererwert.setText( "" +pBeginInnererWert );
  }

  public void setInvestor_Rule2_Abschlag(int pAbschlag)
  {
     this.jTFInvestor_R2_Abschlag.setText( "" +pAbschlag );
  }

  public void setInvestor_Rule3_Abschlag(int pAbschlag)
  {
     this.jTFInvestor_R3_Abschlag.setText( "" +pAbschlag );
  }

  public void setInnererWertMaximalTagAbweichnung(int pMaximalAbweichungProzent)
  {
     this.jTFInnererWertMaximalTagAbweichnung.setText( "" +pMaximalAbweichungProzent );
  }

  public void setMinimalOrderMengeofRandomTrader(int pMinimalMenge)
  {
     this.jTFRandomTraderMinMenge.setText( "" +pMinimalMenge );
  }

  public void setMaximalOrderMengeofRandomTrader(int pMaximalMenge)
  {
     this.jTFRandomTraderMaxMenge.setText( "" +pMaximalMenge );
  }

  public void setGewinnReferenztag(int pReferenztag)
  {
     this.jTFGewinnRefernztag.setText( "" +pReferenztag );
  }

  public void setHillEstimatorProzent(double pProzent)
  {
     this.jTFHillEstimatorProzent.setText( "" +pProzent );
  }

  /////////////////////////////////////////////////////////////////////
  /// Getter Methode

  public int getGewinnReferenztag()
  {
     return Integer.parseInt( this.jTFGewinnRefernztag.getText() );
  }

  public int getTageVonPeriod()
  {
     return Integer.parseInt( this.jTFPeriod.getText() );
  }

  public int getMaxInnererWert()
  {
     return Integer.parseInt( this.jTFMAXInnererwert.getText() );
  }

  public int getMinInnererWert()
  {
     return Integer.parseInt( this.jTFMINInnererwert.getText() );
  }

  public int getBeginInnererWert()
  {
     return Integer.parseInt( this.jTFBeginInnererwert.getText() );
  }

  public int getInvestor_Rule2_Abschlag()
  {
     return Integer.parseInt( this.jTFInvestor_R2_Abschlag.getText() );
  }

  public int getInvestor_Rule3_Abschlag()
  {
     return Integer.parseInt( this.jTFInvestor_R3_Abschlag.getText() );
  }

  public int getInnererWertMaximalTagAbweichnung()
  {
     return Integer.parseInt( this.jTFInnererWertMaximalTagAbweichnung.getText() );
  }
  /***************************/
  public int getMinimalOrderMengeofRandomTrader()
  {
     return Integer.parseInt(this.jTFRandomTraderMinMenge.getText());
  }

  public int getMaximalOrderMengeofRandomTrader()
  {
     return Integer.parseInt(this.jTFRandomTraderMaxMenge.getText());
  }
  /***************************/
  public int getMinimalOrderMengeofInvestorR1()
  {
     return Integer.parseInt(this.jTFInvestorR1MinMenge.getText());
  }

  public int getMaximalOrderMengeofInvestorR1()
  {
     return Integer.parseInt(this.jTFInvestorR1MaxMenge.getText());
  }
  /***************************/
  public int getMinimalOrderMengeofInvestorR2()
  {
     return Integer.parseInt(this.jTFInvestorR2MinMenge.getText());
  }

  public int getMaximalOrderMengeofInvestorR2()
  {
     return Integer.parseInt(this.jTFInvestorR2MaxMenge.getText());
  }
  /***************************/
  public int getMinimalOrderMengeofInvestorR3()
  {
     return Integer.parseInt(this.jTFInvestorR3MinMenge.getText());
  }

  public int getMaximalOrderMengeofInvestorR3()
  {
     return Integer.parseInt(this.jTFInvestorR3MaxMenge.getText());
  }
  /***************************/
  public int getMinimalOrderMengeofNoiseTraderR1()
  {
     return Integer.parseInt(this.jTFNoiseTraderR1MinMenge.getText());
  }

  public int getMaximalOrderMengeofNoiseTraderR1()
  {
     return Integer.parseInt(this.jTFNoiseTraderR1MaxMenge.getText());
  }
  /***************************/
  public int getMinimalOrderMengeofNoiseTraderR2()
  {
     return Integer.parseInt(this.jTFNoiseTraderR2MinMenge.getText());
  }

  public int getMaximalOrderMengeofNoiseTraderR2()
  {
     return Integer.parseInt(this.jTFNoiseTraderR2MaxMenge.getText());
  }
  /***************************/
  public int getMinimalOrderMengeofNoiseTraderR3()
  {
     return Integer.parseInt(this.jTFNoiseTraderR3MinMenge.getText());
  }

  public int getMaximalOrderMengeofNoiseTraderR3()
  {
     return Integer.parseInt(this.jTFNoiseTraderR3MaxMenge.getText());
  }
  /***************************/

  // in Porzent: 5.0 means 5.0%, while using, has to be devided by 100
  public double getHillEstimatorProzent()
  {
     return Double.parseDouble( this.jTFHillEstimatorProzent.getText() );
  }

  public int getInnererwertEntwicklungsTrend()
  {
      ButtonModel bm = this.buttonGroup1.getSelection();
      // When nichts gewälht:
      if ( bm == null )
      {
        // Aufsteigen
        return 1;
      }

      String ss = bm.getActionCommand();
      if ( ss.equals("ABSTEIGEN") )
      {
         return 0;
      }
      else
      {
         return 1;
      }
  }

  /*
  public int getTypWechselStrategie()
  {
  }
  */

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

}

