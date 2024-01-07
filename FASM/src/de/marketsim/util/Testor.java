package de.marketsim.util;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author unbekannt
 * @version 1.0
 */

public class Testor
{

  public static void main(String[] args)
  {
     double dd1[] = {44.4060,19.8076,16.4395,40.0067,14.3988};

     double dd2[] = {8.1071, 6.2839, 14.2314, 23.1751, 17.1951 };

     double pp = SimulationReportProcessor.CaltVariance( dd1 );
     System.out.println( "variance =" + pp);

     pp = SimulationReportProcessor.CaltVariance( dd2 );
     System.out.println( "variance =" + pp);

  }
}