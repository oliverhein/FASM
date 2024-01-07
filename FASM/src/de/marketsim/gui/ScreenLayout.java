package de.marketsim.gui;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.awt.Toolkit;
import de.marketsim.util.HelpTool;

public class ScreenLayout
{

 public static int MainFrame_Location_X  = 0;
 public static int MainFrame_Location_Y  = 0;
 public static int MainFrame_Width       = 580;
 public static int MainFrame_Height      = 300;

 public static int TradestatusFrame_Location_X       = 0;
 public static int TradestatusFrame_Location_Y       = 0;
 public static int TradestatusFrame_Width            = 0;
 public static int TradestatusFrame_Height           = 0;

 public static int InvestorGewinnFrame_Location_X    = 0;
 public static int InvestorGewinnFrame_Location_Y    = 0;
 public static int InvestorGewinnFrame_Width         = 0;
 public static int InvestorGewinnFrame_Height        = 0;

 public static int NoiseTraderGewinnFrame_Location_X  = 0;
 public static int NoiseTraderGewinnFrame_Location_Y  = 0;
 public static int NoiseTraderGewinnFrame_Width       = 0;
 public static int NoiseTraderGewinnFrame_Height      = 0;

 public static int HistroyFrame_Location_X     = 0;
 public static int HistroyFrame_Location_Y     = 0;
 public static int HistroyFrame_Width          = 0;
 public static int HistroyFrame_Height         = 0;

 public static void checkScreenResolution()
 {
    int screen_width =  (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    int screen_height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 5;

         TradestatusFrame_Location_X       = MainFrame_Location_X;
         TradestatusFrame_Location_Y       = MainFrame_Height;
         TradestatusFrame_Width            = MainFrame_Width;
         TradestatusFrame_Height           = ( int ) ( 0.6 * ( screen_height - MainFrame_Height )  );

         InvestorGewinnFrame_Location_X    = MainFrame_Location_X;
         InvestorGewinnFrame_Location_Y    = MainFrame_Height + TradestatusFrame_Height;
         InvestorGewinnFrame_Width         = screen_width/2;
         InvestorGewinnFrame_Height        = screen_height - MainFrame_Height - TradestatusFrame_Height;

         HistroyFrame_Location_X           = MainFrame_Width;
         HistroyFrame_Location_Y           = MainFrame_Location_Y;
         HistroyFrame_Width                = screen_width - MainFrame_Width;
         HistroyFrame_Height               = MainFrame_Height + TradestatusFrame_Height;

         NoiseTraderGewinnFrame_Location_X  = screen_width/2;
         NoiseTraderGewinnFrame_Location_Y  = InvestorGewinnFrame_Location_Y;
         NoiseTraderGewinnFrame_Width       = screen_width/2;
         NoiseTraderGewinnFrame_Height      = InvestorGewinnFrame_Height;

 }

}