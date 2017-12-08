package de.marketsim.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

import java.util.Vector;

import de.marketsim.agent.stockstore.stockdata.InnererwertRandomWalkGenerator;
import de.marketsim.agent.stockstore.stockdata.InnererwertContainer;




/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import de.marketsim.config.Configurator;
import de.marketsim.agent.stockstore.stockdata.InnererwertRandomWalkGenerator;

public class InnererWertDefFrame extends JFrame
{
  private JLabel jLabel1 = new JLabel();
  private JTextField jTFInitWert = new JTextField();
  private JLabel jLabel2 = new JLabel();
  private JTextField jTFAbweichungMax = new JTextField();
  private JButton jBErzeugen = new JButton();
  private JLabel jLabel5 = new JLabel();
  private JTextField jTFDay = new JTextField();
  private JButton jBAbbrechen = new JButton();
  private JTextField jTFMinus = new JTextField();
  private JTextField jTFPlus = new JTextField();
  private JLabel jLabel6 = new JLabel();
  private JLabel jLabel7 = new JLabel();

  private int mPlusWert;
  private int mMinusWert;
  private JTextField jTFMin = new JTextField();
  private JTextField jTFMax = new JTextField();
  private JLabel jLabel8 = new JLabel();
  private JLabel jLabel9 = new JLabel();
  private JLabel jLabel3 = new JLabel();
  private JButton jBWeiter = new JButton();
  private JLabel jLabel4 = new JLabel();

  private SimpleKurvFrame mKurvFrame = new SimpleKurvFrame("Inner Value", "Inner Value");
  private JLabel jLabel10 = new JLabel();

  private InnererwertRandomWalkGenerator mRandomWalk ;

  public InnererWertDefFrame()
  {
    this.setTitle("Inner Value Definition");
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args)
  {
    InnererWertDefFrame r = new InnererWertDefFrame();
    r.setSize(370,250);
    r.setVisible(true);
  }

  private void jbInit() throws Exception {
    jLabel1.setMaximumSize(new Dimension(39, 22));
    jLabel1.setMinimumSize(new Dimension(39, 22));
    jLabel1.setPreferredSize(new Dimension(39, 22));
    jLabel1.setText("Initwert ---------");
    jLabel1.setBounds(new Rectangle(8, 34, 81, 21));
    this.getContentPane().setLayout(null);
    jTFInitWert.setText("1500");
    jTFInitWert.setBounds(new Rectangle(96, 34, 79, 21));
    jLabel2.setMaximumSize(new Dimension(39, 22));
    jLabel2.setMinimumSize(new Dimension(39, 22));
    jLabel2.setPreferredSize(new Dimension(39, 22));
    jLabel2.setText("MaxAbweichung");
    jLabel2.setBounds(new Rectangle(8, 125, 98, 21));
    jTFAbweichungMax.setText("3.0");
    jTFAbweichungMax.setBounds(new Rectangle(99, 125, 79, 21));
    jBErzeugen.setBounds(new Rectangle(11, 161, 103, 32));
    jBErzeugen.setText("Erzeugen");
    jBErzeugen.addActionListener(new InnererWertDefFrame_jBErzeugen_actionAdapter(this));
    jLabel5.setMaximumSize(new Dimension(39, 22));
    jLabel5.setMinimumSize(new Dimension(39, 22));
    jLabel5.setPreferredSize(new Dimension(39, 22));
    jLabel5.setText("Tage ----------");
    jLabel5.setBounds(new Rectangle(8, 102, 81, 21));
    jTFDay.setText("100");
    jTFDay.setBounds(new Rectangle(99, 102, 79, 21));
    jBAbbrechen.setBounds(new Rectangle(221, 161, 103, 32));
    jBAbbrechen.setText("Abbrechen");
    jBAbbrechen.addActionListener(new InnererWertDefFrame_jBAbbrechen_actionAdapter(this));


    jTFMinus.setBounds(new Rectangle(306, 61, 42, 21));
    jTFPlus.setBounds(new Rectangle(305, 32, 42, 22));
    jLabel6.setText("Über InitWert");
    jLabel6.setBounds(new Rectangle(222, 29, 77, 24));
    jLabel7.setText("Unter InitWert");
    jLabel7.setBounds(new Rectangle(222, 60, 78, 25));
    jTFMin.setText("1000");
    jTFMin.setBounds(new Rectangle(97, 56, 79, 21));
    jTFMax.setText("2000");
    jTFMax.setBounds(new Rectangle(99, 80, 79, 21));
    jLabel8.setBounds(new Rectangle(8, 57, 81, 21));
    jLabel8.setMaximumSize(new Dimension(39, 22));
    jLabel8.setMinimumSize(new Dimension(39, 22));
    jLabel8.setPreferredSize(new Dimension(39, 22));
    jLabel8.setText("Min -------------");
    jLabel9.setMaximumSize(new Dimension(39, 22));
    jLabel9.setMinimumSize(new Dimension(39, 22));
    jLabel9.setPreferredSize(new Dimension(39, 22));
    jLabel9.setText("Max -------------");
    jLabel9.setBounds(new Rectangle(8, 80, 81, 21));
    jLabel3.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel3.setText("Statistik");
    jLabel3.setBounds(new Rectangle(221, 12, 144, 19));
    jBWeiter.setBounds(new Rectangle(122, 161, 93, 32));
    jBWeiter.setText("Weiter");
    jBWeiter.addActionListener(new InnererWertDefFrame_jBWeiter_actionAdapter(this));
    jLabel4.setBounds(new Rectangle(7, 8, 180, 22));
    jLabel4.setText("Parameter für Generierung");
    jLabel4.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel10.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel10.setText("%");
    jLabel10.setBounds(new Rectangle(181, 126, 22, 20));
    this.getContentPane().add(jLabel8, null);
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(jTFMax, null);
    this.getContentPane().add(jLabel9, null);
    this.getContentPane().add(jTFDay, null);
    this.getContentPane().add(jLabel5, null);
    this.getContentPane().add(jTFAbweichungMax, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jBErzeugen, null);
    this.getContentPane().add(jBWeiter, null);
    this.getContentPane().add(jBAbbrechen, null);
    this.getContentPane().add(jLabel4, null);
    this.getContentPane().add(jLabel3, null);
    this.getContentPane().add(jLabel6, null);
    this.getContentPane().add(jLabel7, null);
    this.getContentPane().add(jLabel10, null);
    this.getContentPane().add(jTFInitWert, null);
    this.getContentPane().add(jTFMin, null);
    this.getContentPane().add(jTFPlus, null);
    this.getContentPane().add(jTFMinus, null);
  }

  void jBErzeugen_actionPerformed(ActionEvent e)
  {
        Configurator.mConfData.mMaxInnererWert = Integer.parseInt( this.jTFMax.getText()) ;
        Configurator.mConfData.mMinInnererWert = Integer.parseInt( this.jTFMin.getText()) ;
        Configurator.mConfData.mInnererwertMaximalTagAbweichnung =Double.parseDouble(this.jTFAbweichungMax.getText());

        int day = Integer.parseInt(jTFDay.getText());
        double InitWert = Double.parseDouble(jTFInitWert.getText());

        mRandomWalk= new InnererwertRandomWalkGenerator
        (day,
         InitWert,
         Configurator.mConfData.mMinInnererWert,
         Configurator.mConfData.mMaxInnererWert,
         Configurator.mConfData.mInnererwertMaximalTagAbweichnung,
         2.5 );
        mRandomWalk.prepareInnererWert();

        Vector dd = new Vector();
        for ( int i=0; i<day; i++)
        {
          dd.add( new Integer( (int)  mRandomWalk.getInnererWertAtDay(i) ) );
        }

        this.jTFPlus.setText( mRandomWalk.getCC_OverInitWert() +"");
        this.jTFMinus.setText( mRandomWalk.getCC_UnderInitWert()+"");
        this.mKurvFrame.setTrennWert( (int)InitWert );
        this.mKurvFrame.setData( dd  );
  }

  void jBAbbrechen_actionPerformed(ActionEvent e)
  {
      System.exit(0);
  }

  void jBWeiter_actionPerformed(ActionEvent e)
  {
      this.mKurvFrame.setVisible(false);
      this.setVisible(false);

      // save into a static class
      InnererwertContainer.mInitWert =  (int) Double.parseDouble( this.jTFInitWert.getText());
      InnererwertContainer.mMin      = Integer.parseInt( this.jTFMin.getText()) ;
      InnererwertContainer.mMax      = Integer.parseInt( this.jTFMax.getText()) ;
      InnererwertContainer.mAbweichung =Double.parseDouble(this.jTFAbweichungMax.getText());
      InnererwertContainer.mDay = Integer.parseInt(jTFDay.getText());
      InnererwertContainer.mInnererwertGenerator = this.mRandomWalk;

      de.marketsim.gui.DialogAdvanced1Frame ff = new de.marketsim.gui.DialogAdvanced1Frame();
      ff.setSize(400,400);
      ff.setVisible(true);




  }

}

class InnererWertDefFrame_jBErzeugen_actionAdapter implements java.awt.event.ActionListener {
  private InnererWertDefFrame adaptee;

  InnererWertDefFrame_jBErzeugen_actionAdapter(InnererWertDefFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBErzeugen_actionPerformed(e);
  }
}

class InnererWertDefFrame_jBWeiter_actionAdapter implements java.awt.event.ActionListener {
  private InnererWertDefFrame adaptee;

  InnererWertDefFrame_jBWeiter_actionAdapter(InnererWertDefFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBWeiter_actionPerformed(e);
  }
}

class InnererWertDefFrame_jBAbbrechen_actionAdapter implements java.awt.event.ActionListener {
  private InnererWertDefFrame adaptee;

  InnererWertDefFrame_jBAbbrechen_actionAdapter(InnererWertDefFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBAbbrechen_actionPerformed(e);
  }
}


