package de.marketsim.util;

import javax.swing.UIManager;

public class OptionPaneBugFix
{

public static void changeYesButtonText()
{
   UIManager.put("OptionPane.yesButtonText","yes");
}

public static void changeNoButtonText()
{
   UIManager.put("OptionPane.noButtonText","no");
}

public static void YesNoButtonTextBugfix()
{
    changeYesButtonText();
    changeNoButtonText();
}




}