package de.marketsim.message;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class InitKurs implements java.io.Serializable
{

  public int    AktienKurs[] ;
  public double MoneyKurs[] ;

  public void setAktienKurs( int pKurs[]  )
  {
     this.AktienKurs = pKurs;
  }

  public void setMoneyKurs( double pKurs[]  )
  {
     this.MoneyKurs = pKurs;
  }


}