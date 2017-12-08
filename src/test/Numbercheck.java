package test;
import de.marketsim.util.*;
import de.marketsim.SystemConstant;
/**
 * <p>Überschrift: FASM Frankfurt Articial Simulation Market</p>
 * <p>Beschreibung: Mircomarket Simulator </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.text.*;

public class Numbercheck
{

  public Numbercheck()
  {
  }
  
  public static void main(String[] args)
  {
	  
      NumberFormat decformat = java.text.DecimalFormat.getInstance( java.util.Locale.US );
      
      decformat.setGroupingUsed( false );
      decformat.setMaximumFractionDigits(2);
      
      System.out.println( decformat.format(1000000) );
      System.out.println( decformat.format(1272772.82828) );
      
      System.out.println( decformat.format(123.8893) );
      decformat.setGroupingUsed( true );
      System.out.println( "---------------------" );
      System.out.println( decformat.format(1000000) );
      System.out.println( decformat.format(1272772.82828) );
      
      System.out.println( decformat.format(123.8893) );
      
      
      
      
	  
    Numbercheck numbercheck1 = new Numbercheck();
    DataFormatter nff =new de.marketsim.util.DataFormatter( SystemConstant.DataFormatLanguage_German );
    System.out.println(nff.format2str(12));
  }
}