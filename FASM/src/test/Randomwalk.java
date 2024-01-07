package test;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

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

public class Randomwalk extends JFrame
{
  private JLabel jLabel1 = new JLabel();
  private JTextField jTFInitWert = new JTextField();
  private JLabel jLabel2 = new JLabel();
  private JLabel jLabel4 = new JLabel();
  private JTextField jTFAbweichungMax = new JTextField();
  private JButton jButton1 = new JButton();
  private JLabel jLabel5 = new JLabel();
  private JTextField jTFDay = new JTextField();
  private JButton jButton2 = new JButton();
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

  public Randomwalk()
  {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

 private double[] GeneratenDaten1(int pDay)
{
  double[] dd = new double[pDay];
  Random rd1 = new Random();
  Random rd2 = new Random();
  Random rd3 = new Random();
  Random rd4 = new Random();

  double InitWert = Double.parseDouble(jTFInitWert.getText());
  double AbweichungMax = Double.parseDouble(this.jTFAbweichungMax.getText());
  int ABMin = (int)(AbweichungMax*(-10));
  int ABMax = (int)(AbweichungMax*10);
  int dif = ABMax-ABMin+1;
  dd[0] = InitWert;

  int cc=0;

  boolean forced = true;
  int factor = 0;

  //rd.setSeed( System.currentTimeMillis() );

  for (int nn = 1; nn<pDay; nn++)
  {

    if ( forced )
    {
       dd[nn] = dd[nn-1] * ( 1 + factor*((ABMin + rd2.nextInt(dif))*0.1)*0.01 );
    }
    else
    {
          double p1,p2;
          int m1 = rd1.nextInt(10);

          if ( m1 <5 )
          {
             p1 = dd[nn-1]/2.0 * ( 1 - ((ABMin + rd2.nextInt(dif))*0.1)*0.01 );
             cc++;
          }
          else
         {
             p1 = dd[nn-1]/2.0 * ( 1 + (ABMin + rd2.nextInt(dif))*0.1*0.01 );
         }

         int m2 = rd3.nextInt(10);

         if ( m2 <5 )
         {
            p2 = dd[nn-1]/2.0 * ( 1 - ((ABMin + rd4.nextInt(dif))*0.1)*0.01 );
         }
         else
         {
            p2 = dd[nn-1]/2.0 * ( 1 + (ABMin + rd4.nextInt(dif))*0.1*0.01 );
         }


         dd[nn] = p1 + p2;
    }

  if ( ! forced )
  {

   if ( dd[nn] / dd[0] > 3.0 )
   {
      forced = true;
      factor = -1;
   }
   else
     if ( dd[nn] / dd[0] < 0.3 )
     {
        forced = true;
        factor = +1;
     }
  }
  else
  {
    if (  Math.abs (dd[nn] - dd[0] ) / dd[0] < 0.03 )
    {
        forced = false;
     }
  }



   this.jTFMinus.setText( (  (int) ( (nn-cc)*100.0/nn) )+"");
   this.jTFPlus.setText( (int) ( cc*100.0/nn )+"");

  }
  return dd;
}


private double[] GeneratenDaten4(int pDay)
{
 double[] dd = new double[pDay];
 Random rd1 = new Random();
 Random rd2 = new Random();
 Random rd3 = new Random();
 Random rd4 = new Random();

 double InitWert = Double.parseDouble(jTFInitWert.getText());
 double AbweichungMax = Double.parseDouble(this.jTFAbweichungMax.getText());
 int ABMin = (int)(AbweichungMax*(-10));
 int ABMax = (int)(AbweichungMax*10);
 int dif = ABMax-ABMin+1;
 dd[0] = InitWert;

 int cc=0;

 boolean forced = true;
 int factor = 0;

 for (int nn = 1; nn<pDay; nn++)
 {

   if ( forced )
   {
      dd[nn] = dd[nn-1] * ( 1 + factor*((ABMin + rd2.nextInt(dif))*0.1)*0.01 );
   }
   else
   {
         if ( nn < 17 )
         {
         double p1,p2;
         int m1 = rd1.nextInt(10);

         if ( m1 <5 )
         {
            p1 = dd[nn-1]/2.0 * ( 1 - ((ABMin + rd2.nextInt(dif))*0.1)*0.01 );
            cc++;
         }
         else
        {
            p1 = dd[nn-1]/2.0 * ( 1 + (ABMin + rd2.nextInt(dif))*0.1*0.01 );
        }

        int m2 = rd3.nextInt(10);

        if ( m2 <5 )
        {
           p2 = dd[nn-1]/2.0 * ( 1 - ((ABMin + rd4.nextInt(dif))*0.1)*0.01 );
        }
        else
       {
           p2 = dd[nn-1]/2.0 * ( 1 + (ABMin + rd4.nextInt(dif))*0.1*0.01 );
       }

        dd[nn] = p1 + p2;

         }
         else
         {

           double p1,p2;
           int m1 = rd1.nextInt(10);

           if ( m1 <5 )
           {
              p1 = dd[nn-5]/2.0 * ( 1 - ((ABMin + rd2.nextInt(dif))*0.1)*0.01 );
              cc++;
           }
           else
          {
              p1 = dd[nn-5]/2.0 * ( 1 + (ABMin + rd2.nextInt(dif))*0.1*0.01 );
          }

          int m2 = rd3.nextInt(10);

          if ( m2 <5 )
          {
             p2 = dd[nn-17]/2.0 * ( 1 - ((ABMin + rd4.nextInt(dif))*0.1)*0.01 );
          }
          else
         {
             p2 = dd[nn-17]/2.0 * ( 1 + (ABMin + rd4.nextInt(dif))*0.1*0.01 );
         }
         dd[nn] = p1 + p2;
         }

   }

   if ( ! forced )
   {
        if ( dd[nn] / dd[0] > 3.0 )
        {
           forced = true;
           factor = -1;
        }
        else
        if ( dd[nn] / dd[0] < 0.3 )
        {
             forced = true;
             factor = +1;
        }
   }
   else
   {
         if (  Math.abs (dd[nn] - dd[0] ) / dd[0] < 0.03 )
         {
             forced = false;
         }
   }

   if ( dd[nn] >= dd[0] )
   {
     this.mPlusWert++;
   }
   else
   {
     this.mPlusWert++;
   }
 }


 return dd;
}

/** OK **/
private double[] GeneratenDaten(int pDay)
{
 double[] dd = new double[pDay];
 Random rd1 = new Random();
 Random rd2 = new Random();

 double InitWert = Double.parseDouble(jTFInitWert.getText());
 double AbweichungMax = Double.parseDouble(this.jTFAbweichungMax.getText());
 int ABMin = (int)(AbweichungMax*(-10));
 int ABMax = (int)(AbweichungMax*10);
 int dif = ABMax-ABMin+1;
 dd[0] = InitWert;

 int cc=0;

 boolean forced = true;
 int factor = 0;
 for (int nn = 1; nn<pDay; nn++)
 {
         double p1,p2,p3;
         //dd[nn] = dd[nn-1] * ( 1 + (ABMin + rd1.nextInt(dif))*0.1*0.01 );

         if ( nn < 5 )
         {
             double perc = (ABMin + rd1.nextInt(dif))*0.1*0.01;
             p1 = dd[nn-1] * ( 1 + perc );
             dd[nn] = p1;
         }
         else
         {
           double perc = (ABMin + rd1.nextInt(dif))*0.1*0.01;
           p1 = dd[nn-1]*0.7  * ( 1 + perc );
           p2 = dd[nn-2]*0.15 * ( 1 + perc );
           p3 = dd[nn-3]*0.15 * ( 1 + perc );
           dd[nn] = p1 + p2 + p3;
         }

         if ( dd[nn] >= dd[0] )
         {
            this.mPlusWert++;
         }
         else
         {
            this.mMinusWert++;
         }
  }
  return dd;
}

private double[] GeneratenDaten2(int pDay)
{
 double[] dd = new double[pDay];
 Random rd1 = new Random();
 Random rd2 = new Random();
 Random rd3 = new Random();
 Random rd4 = new Random();

 double InitWert = Double.parseDouble(jTFInitWert.getText());
 double AbweichungMax = Double.parseDouble(this.jTFAbweichungMax.getText());
 int ABMin = (int)(AbweichungMax*(-10));
 int ABMax = (int)(AbweichungMax*10);
 int dif = ABMax-ABMin+1;
 dd[0] = InitWert;

 int cc=0;

 boolean forced = true;
 int factor = 0;

 for (int nn = 1; nn<pDay; nn++)
 {

   if ( forced )
   {
      dd[nn] = dd[nn-1] * ( 1 + factor*((ABMin + rd2.nextInt(dif))*0.1)*0.01 );
   }
   else
   {
         if ( nn < 17 )
         {
         double p1,p2;
         int m1 = rd1.nextInt(10);

         if ( m1 <5 )
         {
            p1 = dd[nn-1]/2.0 * ( 1 - ((ABMin + rd2.nextInt(dif))*0.1)*0.01 );
            cc++;
         }
         else
        {
            p1 = dd[nn-1]/2.0 * ( 1 + (ABMin + rd2.nextInt(dif))*0.1*0.01 );
        }

        int m2 = rd3.nextInt(10);

        if ( m2 <5 )
        {
           p2 = dd[nn-1]/2.0 * ( 1 - ((ABMin + rd4.nextInt(dif))*0.1)*0.01 );
        }
        else
       {
           p2 = dd[nn-1]/2.0 * ( 1 + (ABMin + rd4.nextInt(dif))*0.1*0.01 );
       }

        dd[nn] = p1 + p2;

         }
         else
         {

           double p1,p2;
           int m1 = rd1.nextInt(10);

           if ( m1 <5 )
           {
              p1 = dd[nn-5]/2.0 * ( 1 - ((ABMin + rd2.nextInt(dif))*0.1)*0.01 );
              cc++;
           }
           else
          {
              p1 = dd[nn-5]/2.0 * ( 1 + (ABMin + rd2.nextInt(dif))*0.1*0.01 );
          }

          int m2 = rd3.nextInt(10);

          if ( m2 <5 )
          {
             p2 = dd[nn-17]/2.0 * ( 1 - ((ABMin + rd4.nextInt(dif))*0.1)*0.01 );
          }
          else
         {
             p2 = dd[nn-17]/2.0 * ( 1 + (ABMin + rd4.nextInt(dif))*0.1*0.01 );
         }
         dd[nn] = p1 + p2;
         }

   }

 if ( ! forced )
 {

  if ( dd[nn] / dd[0] > 3.0 )
  {
     forced = true;
     factor = -1;
  }
  else
    if ( dd[nn] / dd[0] < 0.3 )
    {
       forced = true;
       factor = +1;
    }
 }
 else
 {
   if (  Math.abs (dd[nn] - dd[0] ) / dd[0] < 0.03 )
   {
       forced = false;
    }
 }

  this.jTFMinus.setText( (  (int) ( (nn-cc)*100.0/nn) )+"");
  this.jTFPlus.setText( (int) ( cc*100.0/nn )+"");

 }
 return dd;
}



  public static void main(String[] args)
  {
    Randomwalk r = new Randomwalk();
    r.setSize(400,400);
    r.setVisible(true);
  }

  private void jbInit() throws Exception {
    jLabel1.setText("Initwert");
    jLabel1.setBounds(new Rectangle(8, 14, 52, 17));
    this.getContentPane().setLayout(null);
    jTFInitWert.setText("1000");
    jTFInitWert.setBounds(new Rectangle(67, 13, 66, 22));
    jLabel2.setText("Abweichung");
    jLabel2.setBounds(new Rectangle(13, 56, 101, 25));
    jLabel4.setText("MAX");
    jLabel4.setBounds(new Rectangle(24, 88, 45, 30));
    jTFAbweichungMax.setText("3.0");
    jTFAbweichungMax.setBounds(new Rectangle(74, 91, 79, 24));
    jButton1.setBounds(new Rectangle(64, 217, 103, 32));
    jButton1.setText("Generaten");
    jButton1.addActionListener(new Randomwalk_jButton1_actionAdapter(this));
    jLabel5.setText("Day");
    jLabel5.setBounds(new Rectangle(25, 123, 45, 20));
    jTFDay.setText("3000");
    jTFDay.setBounds(new Rectangle(73, 121, 72, 23));
    jButton2.setBounds(new Rectangle(195, 223, 124, 30));
    jButton2.setText("close");
    jButton2.addActionListener(new Randomwalk_jButton2_actionAdapter(this));
    jTFMinus.setBounds(new Rectangle(303, 110, 62, 26));
    jTFPlus.setBounds(new Rectangle(302, 144, 62, 30));
    jLabel6.setText("Over");
    jLabel6.setBounds(new Rectangle(250, 112, 35, 24));
    jLabel7.setText("Under");
    jLabel7.setBounds(new Rectangle(252, 146, 40, 25));
    jTFMin.setText("300");
    jTFMin.setBounds(new Rectangle(176, 13, 68, 25));
    jTFMax.setText("2000");
    jTFMax.setBounds(new Rectangle(309, 14, 56, 24));
    jLabel8.setBounds(new Rectangle(139, 13, 32, 24));
    jLabel8.setText("Min");
    jLabel9.setText("Max");
    jLabel9.setBounds(new Rectangle(259, 15, 32, 24));
    this.getContentPane().add(jTFInitWert, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jButton1, null);
    this.getContentPane().add(jButton2, null);
    this.getContentPane().add(jTFMin, null);
    this.getContentPane().add(jTFMax, null);
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(jLabel8, null);
    this.getContentPane().add(jLabel9, null);
    this.getContentPane().add(jTFPlus, null);
    this.getContentPane().add(jTFMinus, null);
    this.getContentPane().add(jLabel7, null);
    this.getContentPane().add(jLabel6, null);
    this.getContentPane().add(jLabel4, null);
    this.getContentPane().add(jTFAbweichungMax, null);
    this.getContentPane().add(jLabel5, null);
    this.getContentPane().add(jTFDay, null);
  }

  void jButton1_actionPerformed(ActionEvent e)
  {

        Configurator.mConfData.mMaxInnererWert = Integer.parseInt( this.jTFMax.getText()) ;
        Configurator.mConfData.mMinInnererWert = Integer.parseInt( this.jTFMin.getText()) ;
        Configurator.mConfData.mInnererwertMaximalTagAbweichnung =Double.parseDouble(this.jTFAbweichungMax.getText());

        int day = Integer.parseInt(jTFDay.getText());
        double InitWert = Double.parseDouble(jTFInitWert.getText());

        InnererwertRandomWalkGenerator pp =
            new InnererwertRandomWalkGenerator(day,
                                               InitWert,
                                               Configurator.mConfData.mMinInnererWert,
                                               Configurator.mConfData.mMaxInnererWert,
                                               Configurator.mConfData.mInnererwertMaximalTagAbweichnung,
                                               2.5 );
        pp.prepareInnererWert();

        double[] dd = new double[day];
        for ( int i=0; i<day; i++)
        {
          dd[i] = pp.getInnererWertAtDay(i);
        }

        this.jTFPlus.setText( pp.getCC_OverInitWert() +"");
        this.jTFMinus.setText( pp.getCC_UnderInitWert()+"");
        for (int n = 0; n<day; n++)
        {
          System.out.println((int)dd[n]);
        }
  }

  void jButton2_actionPerformed(ActionEvent e)
  {
      System.exit(0);
  }

}





class Randomwalk_jButton1_actionAdapter implements java.awt.event.ActionListener {
  private Randomwalk adaptee;

  Randomwalk_jButton1_actionAdapter(Randomwalk adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton1_actionPerformed(e);
  }
}

class Randomwalk_jButton2_actionAdapter implements java.awt.event.ActionListener {
  private Randomwalk adaptee;

  Randomwalk_jButton2_actionAdapter(Randomwalk adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton2_actionPerformed(e);
  }
}