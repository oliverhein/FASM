/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author
 * @version 1.0
 */

package de.marketsim.gui;

// physical panel parameter of drawing area
public class Coordinator
{

    // define the computer coordinator value for human coordinator
    // The variable name is from human coordinator
    // Their values are the computer coordinator values.

     // const definition
     //public static int bottommargin = 30;
     //public static int rightmargin = 30;
     //public static int arrowlength  = 5;

      public  int bottommargin = 30;
      public  int rightmargin = 30;
      public  int arrowlength  = 5;

     // changeable definition
     public  int leftmargin = 50;
     public  int x0 = leftmargin;
     public  int ymax = 30;
     public  int y0;
     public  int xmax;

     public void setLeftmargin(int pLeftMargin)
     {
       leftmargin = pLeftMargin;
       x0 = leftmargin;
     }

}