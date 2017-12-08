package de.marketsim.util;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.io.*;

public class HTMLCreator
{

  public static void putHtmlHead(PrintStream pw )
  {
     if ( pw == null )
     {
       return;
     }

     java.sql.Timestamp  ts = new java.sql.Timestamp( System.currentTimeMillis() );
     try
     {
        pw.println("<html><Head><Title>Test Report on " + ts.toString()+ "</title></head>" );
     }
     catch (Exception ex)
     {
     }
  }

  public static void putHtmlHeadwithTitel(PrintStream pw, String pTitel )
  {
    if ( pw == null )
    {
      return;
    }

     try
     {
        pw.println("<html><Head><Title>" + pTitel + "</title></head>" );
     }
     catch (Exception ex)
     {
     }
  }

  public static void putHtmlBodyBegin(PrintStream pw)
  {
    if ( pw == null )
    {
      return;
    }

     try
     {
        pw.println("<Body> <h4>");
     }
     catch (Exception ex)
     {
     }
  }

  public static void putHtmlBodyEnd(PrintStream pw)
  {
    if ( pw == null )
    {
      return;
    }

     try
     {
        pw.println("</Body>");
     }
     catch (Exception ex)
     {
     }
  }

  public static void putHtmlEnd(PrintStream pw)
  {
    if ( pw == null )
    {
      return;
    }

     try
     {
        pw.println("</HTML>");
     }
     catch (Exception ex)
     {
     }
  }

  public static void putFileLink(PrintStream pw, String LinkName, String pFilename )
  {
    if ( pw == null )
    {
      return;
    }

     try
     {
        pw.println("<br>");
        pw.println("<a href='" +pFilename + "'><H2>" + LinkName + "</a>");
        pw.println("<br>");
     }
     catch (Exception ex)
     {
     }
  }

/**
 * 
 * @param pw
 * @param LinkName,  will be displayed in the browser
 * @param pURLName,  will be used to point to the HTML file
 * @param pWindowName,
 */  
  
public static void putFileLinkwithNewWindow(PrintStream pw, String LinkName,  String pURLName, String pWindowName )
{
  if ( pw == null )
  {
    return;
  }

     try
     {
        pw.println("<br>");
        pw.println("<a href='" + pURLName + "' Target='"+ pWindowName+ "' ><H2>" + LinkName + "</a>");
        pw.println("<br>");
     }
     catch (Exception ex)
     {
     }
  }

  public static void putHTMLLine(PrintStream pw, String content)
  {
     if ( pw == null )
     {
       return;
     }
     try
     {
        pw.println(content + "<br>");
     }
     catch (Exception ex)
     {
     }
  }


  public static void putHTMLTableBegin(PrintStream pw, String TName)
  {
    if ( pw == null )
    {
      return;
    }

    try
       {
          pw.println("<table name =" + TName+ " border ='1' >");
       }
       catch (Exception ex)
       {
          ex.printStackTrace();
     }

  }

  public static void putHTMLTableEnd(PrintStream pw)
  {
    if ( pw == null )
    {
      return;
    }

    try
    {
      pw.println("</table>");
    }
    catch (Exception ex)
    {
       ex.printStackTrace();
     }

  }

  public static void putHTMLTableItem(PrintStream pw,String contentName,String content)
  {
    if ( pw == null )
    {
      return;
    }

    try
     {
          pw.println("<tr><td>");
          if (contentName.length()>0 & contentName.length()<=20)
          {
            pw.println(contentName);
          }else
          {
           String ss = contentName;
            for (int n = 0; n<contentName.length()/20; n++)
           {
            ss = ss.substring(1,n*20);
             pw.println(ss);
             pw.println("<br>") ;
             ss = ss.substring(n*20+1);
             if (ss.length()<20)
             {
             pw.println(ss);
             }

           }
          }
          pw.println("</td>");
          pw.println("<td>");
          pw.println(content);
          pw.println("</td>");

       pw.println("</tr>");
     }
     catch (Exception ex)
     {
     }
  }

  public static void putHTMLContent(PrintStream pw, String content)
  {
     if ( pw == null )
     {
       return;
     }

     try
     {
        pw.println(content);
     }
     catch (Exception ex)
     {
        ex.printStackTrace();
     }
  }
}