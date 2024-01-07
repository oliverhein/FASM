package de.marketsim.config;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.Vector;

public class XmlHelpTool
{

  public static String getXmlComment(String pComment)
  {
     return "<!-- " + pComment + " -->";
  }

  public static String getXmlTagFormat(String pParameterName, String pValue)
  {
     return "<"+pParameterName+">"+pValue+"</"+pParameterName+">";
  }

  public static String getXmlTagFormat(String pParameterName, int pValue)
  {
     return "<"+pParameterName+">"+pValue+"</"+pParameterName+">";
  }

  public static String getXmlTagFormat(String pParameterName, boolean pValue)
  {
     return "<"+pParameterName+">"+pValue+"</"+pParameterName+">";
  }

  public static String getXmlTagFormat(String pParameterName, double pValue)
  {
     return "<"+pParameterName+">"+pValue+"</"+pParameterName+">";
  }

  public static String[] Vector2StringArray(Vector pVector)
  {
       String[] ss = new String [ pVector.size() ];
       for ( int i=0; i < pVector.size(); i++ )
       {
           ss[i] = ( String ) pVector.elementAt(i);
       }
       return ss;
  }


}