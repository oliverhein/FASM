package de.marketsim.util;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.*;

class SingleOrderSorting
{

  public static Vector DecendSorting( Vector pData, String  pSortierungNach)
  {
      Vector Original = pData;

      Vector tempvector = new Vector();
      for ( int i=0; i<pData.size(); i++)
      {
           tempvector.add( pData.elementAt(i) );
      }

      Vector Result   = new Vector();
      while ( tempvector.size() > 0 )
      {
          // find the position of max price
          int pos = getMaxPosition( tempvector,  pSortierungNach );
          // copy value to Result
          Result.add( tempvector.elementAt(pos) );
          // remove this max element
          tempvector.removeElementAt(pos);
      }
      return Result;
  }

  public static Vector AcendSorting( Vector pData, String  pSortierungNach)
  {
      Vector Original = pData;
      Vector Result   = new Vector();
      Vector tempvector = new Vector();
      for ( int i=0; i<pData.size(); i++)
      {
           tempvector.add( pData.elementAt(i) );
      }

      while ( tempvector.size() > 0 )
      {
          // find the position of min price
          int pos = getMinPosition( tempvector,  pSortierungNach );
          // copy value to Result
          Result.add( tempvector.elementAt(pos) );
          // remove this min element
          tempvector.removeElementAt(pos);
      }
      return Result;
  }

  public static int getMaxPosition(Vector pData, String  pSortierungNach)
  {
     double maxprice = 0.0;
     int pos      = -1;
     for (int i=0; i< pData.size(); i++)
     {
         SingleOrder  so = ( SingleOrder) pData.elementAt(i);
         if ( so.mLimit > maxprice )
         {
           pos = i;
           maxprice = so.mLimit;
         }
     }
     return pos;
  }

  public static int getMinPosition(Vector pData, String  pSortierungNach)
  {
     double minprice = Integer.MAX_VALUE;
     int pos      = -1;
     for (int i=0; i< pData.size(); i++)
     {
         SingleOrder so = ( SingleOrder ) pData.elementAt(i);
         if ( so.mLimit < minprice )
         {
           pos = i;
           minprice = so.mLimit;
         }
     }
     return pos;
  }

  public static void main(String args[])
  {
      Vector vv = new Vector();
      SingleOrder so = new SingleOrder(null,'B',1,1,1000,"",1);
      vv.add(so);
      so = new SingleOrder(null,'B',1,1,2000,"",1);
      vv.add(so);
      so = new SingleOrder(null,'B',1,1,-1,"",1);
      vv.add(so);
      so = new SingleOrder(null,'B',1,1,1000,"",1);
      vv.add(so);
      so = new SingleOrder(null,'B',1,1,-1,"",1);
      vv.add(so);

      Vector vv2 = AcendSorting(vv,"");

      for (int i=0; i< vv2.size(); i++)
      {
         so = (SingleOrder) vv2.elementAt(i);
         System.out.println("Limit:" + so.mLimit);
      }
 }

}