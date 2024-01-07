package de.marketsim.config;

import java.io.FilenameFilter;
import java.io.File;


/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

/**
 * filter out all but *.xml files
 */
public class CFGFilter implements FilenameFilter
{

  private String mAllowedExtension;

  /**
   * Filename Extention: xml
   * @param pExtension
   */

  public CFGFilter(String pExtension)
  {
    mAllowedExtension = pExtension.toUpperCase();
  }

  /**
   * Select only *.xml files.
   * @param dir the directory in which the file was found.
   * @param name the name of the file
   * @return true if and only if the name should be
   * included in the file list; false otherwise.
   */
   public boolean accept ( File dir, String name )
   {
      if ( new File( dir, name ).isDirectory() )
      {
            return false;
      }
      return name.toUpperCase().endsWith( mAllowedExtension );
   }
 }

