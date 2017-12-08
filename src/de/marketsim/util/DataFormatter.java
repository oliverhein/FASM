package de.marketsim.util;

/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author
 * @version 1.0
 */

import java.util.*;
import java.text.*;

import de.marketsim.SystemConstant;


public class DataFormatter
{

  private NumberFormat decformat = null;
  private NumberFormat intformat = null;

  private int mMaxFractionDigits = 4; // Default
  private int mMinFractionDigits = 2; // Default

  public DataFormatter( String pLand )
  {

    if ( pLand.equalsIgnoreCase( SystemConstant.DataFormatLanguage_English ))
    {
       decformat = java.text.DecimalFormat.getInstance( java.util.Locale.US );
       intformat = java.text.NumberFormat.getInstance(java.util.Locale.US);
    }
    else
    if ( pLand.equalsIgnoreCase( SystemConstant.DataFormatLanguage_German ) )
    {
      decformat = java.text.DecimalFormat.getInstance( java.util.Locale.GERMANY );
      intformat = java.text.NumberFormat.getInstance(java.util.Locale.GERMANY);
    }
    else
    if ( pLand.equalsIgnoreCase( SystemConstant.DataFormatLanguage_Chinese ) )
    {
      decformat = java.text.DecimalFormat.getInstance( java.util.Locale.CHINA );
      intformat = java.text.NumberFormat.getInstance(java.util.Locale.CHINA);

    }
    else
    {
      //  Default German
      decformat = java.text.DecimalFormat.getInstance( java.util.Locale.GERMANY );
      intformat = java.text.NumberFormat.getInstance(java.util.Locale.GERMANY);
    }
    
    decformat.setMaximumFractionDigits(mMaxFractionDigits);
    decformat.setMinimumFractionDigits(mMinFractionDigits);
    decformat.setGroupingUsed( false );
    intformat.setGroupingUsed( false );

  }

  public void setMaxFractionDigits(int pDigits)
  {
    this.mMaxFractionDigits = pDigits;
    decformat.setMaximumFractionDigits(mMaxFractionDigits);
  }

  public void setMinFractionDigits(int pDigits)
  {
    this.mMinFractionDigits = pDigits;
    decformat.setMinimumFractionDigits(mMinFractionDigits);
  }

  public String format2str(double dd)
  {
    return decformat.format(dd);
  }

  public String format2str(int dd)
    {
      return intformat.format(dd);
    }

  public double format2double(double dd)
  {
    String ss = decformat.format(dd);
    return Double.parseDouble(ss);
  }

  public void setMaximumFractionDigits(int pMaximumFractionDigits)
  {
     this.decformat.setMaximumFractionDigits(pMaximumFractionDigits);

  }

  public void setMinimumFractionDigits(int pMinimumFractionDigits)
  {
    this.decformat.setMinimumFractionDigits(pMinimumFractionDigits);

  }

}