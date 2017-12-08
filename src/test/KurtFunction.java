






































package test;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.*;
import java.text.*;


public class KurtFunction
{

  public KurtFunction()
  {
  }

  public double getSchiefe()
  {
    //3,4,5,2,3,4,5,6,4,7) equals 0.359543
    int pData[] ={3,4,5,2,3,4,5,6,4,7};
    int n = pData.length;
    double average = this.getAverage( pData );
    double stdev   = this.getstdev( pData);
    double sum0 = 0.0;
    for ( int i=0; i<n; i++)
    {
         double dd = (  ( pData[i] - average ) / stdev );
         sum0 = sum0 + dd*dd*dd;
    }

    double  f1 =  n*1.0 / ( (n-1)*(n-2) );

    double schiefe = f1 * sum0 ;
    System.out.println( "schiefe="  + schiefe );

    return schiefe;
  }

  public double getAverage( int pData[])
  {
    int n = pData.length;
    int sum1 = 0;
    for (int i=0; i<n; i++)
    {
       sum1 = sum1+ pData[i];
    }
    return sum1*1.0 / n;
  }

  public double getstdev( int pData[])
  {

    int n = pData.length;

    int sum1 = 0;
    for (int i=0; i<n; i++)
    {
       sum1 = sum1+ pData[i];
    }

    int sum2 = 0;
    for (int i=0; i<n; i++)
    {
       sum2 = sum2+ pData[i]*pData[i];
    }

    int fenzi = n * sum2 - sum1*sum1;
    int fenmu = n * (n-1);

    double kanfanqian = fenzi*1.0 / fenmu;

    double stdev = java.lang.Math.sqrt( kanfanqian );

    System.out.println( "stdev="  + stdev );

    return stdev;

  }


  public double getkurt()
  {

    int pData[] ={3,4,5,2,3,4,5,6,4,7};

    int n = pData.length;

    double average = this.getAverage( pData );
    double stdev   = this.getstdev( pData);

    double sum0 = 0.0;
    for ( int i=0; i<n; i++)
    {
         double dd = (  ( pData[i] - average ) / stdev );
         sum0 = sum0 + dd*dd*dd*dd;
    }

    double  f1 =  n*(n+1)*1.0 / ( (n-1)*(n-2)*(n-3) );

    double  f2 =  3*(n-1)*(n-1)*1.0 / ( (n-2)*(n-3) );

    double kurt = f1 * sum0 - f2;
    System.out.println( "kurt="  + kurt );

    return kurt;

    //KURT(3,4,5,2,3,4,5,6,4,7) returns -0.1518

  }

  public static void main(String[] args)
  {
    KurtFunction pp= new KurtFunction();

    int data[] = {1345, 1301, 1368, 1322, 1310, 1370, 1318, 1350, 1303, 1299};

    //pp.getstdev( data );
    pp.getkurt();

    pp.getSchiefe();

    java.text.NumberFormat nff = java.text.NumberFormat.getNumberInstance( java.util.Locale.GERMANY );
    nff.setMaximumFractionDigits(8);
    nff.setGroupingUsed(false);

    System.out.println( nff.format( 0.0026363883  ) );
    System.out.println( nff.format( -12.10263837363883  ) );
    System.out.println( nff.format( -3737237312.10263837363883  ) );
    System.out.println( nff.format( -37312.2636  ) );

    double dd = Math.log( 0.101161 )  -  Math.log( 0.092155);
    dd = dd / 2;
    dd = 1/ dd;
    System.out.println( dd  );




  }
}