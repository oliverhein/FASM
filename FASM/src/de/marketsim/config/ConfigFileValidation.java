package de.marketsim.config;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class ConfigFileValidation
{
   public String mFileName;
   public boolean mIsValid = false;

   public ConfigFileValidation ( String pFilename, boolean pValid)
   {
     this.mFileName = pFilename;
     this.mIsValid  = pValid;
   }
}