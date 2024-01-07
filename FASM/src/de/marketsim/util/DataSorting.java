package de.marketsim.util;

/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author
 * @version 1.0
 */

import java.util.Vector;
import de.marketsim.util.*;

public class DataSorting
{

  public DataSorting()
  {
  }

  public static Vector DecendSorting( Vector pData)
  {
      Vector Original = pData;
      Vector Result   = new Vector();
      while ( Original.size() > 0 )
      {
          // find the position of max price
          int pos = getMaxPosition( Original );
          // copy value to Result
          Result.add( Original.elementAt(pos) );
          // remove this max element
          Original.removeElementAt(pos);
      }
      return Result;
  }

  public static Vector AcendSorting( Vector pData)
  {
      Vector Original = pData;
      Vector Result   = new Vector();
      while ( Original.size() > 0 )
      {
          // find the position of min price
          int pos = getMinPosition( Original );
          // copy value to Result
          Result.add( Original.elementAt(pos) );
          // remove this min element
          Original.removeElementAt(pos);
      }
      return Result;
  }

  public static int getMaxPosition(Vector pData)
  {
     double maxprice = 0.0;
     int pos      = -1;
     for (int i=0; i< pData.size(); i++)
     {
         de.marketsim.util.PriceCalcBase onedata = ( PriceCalcBase) pData.elementAt(i);
         if ( onedata.mLimit > maxprice )
         {
           pos = i;
           maxprice = onedata.mLimit;
         }
     }
     return pos;
  }

  public static int getMinPosition(Vector pData)
  {
     double minprice = Integer.MAX_VALUE;
     int pos      = -1;
     for (int i=0; i< pData.size(); i++)
     {
         de.marketsim.util.PriceCalcBase onedata = ( PriceCalcBase) pData.elementAt(i);
         if ( onedata.mLimit < minprice )
         {
           pos = i;
           minprice = onedata.mLimit;
         }
     }
     return pos;
  }


}