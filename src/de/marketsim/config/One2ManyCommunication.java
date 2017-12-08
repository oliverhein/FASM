package de.marketsim.config;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: </p>
 * @author unbekannt
 * @version 1.0
 */

import java.util.Vector;

public class One2ManyCommunication
{

  String mMasterName = null;
  Vector mMyPartner  = new Vector();

  public One2ManyCommunication( String pMasterName)
  {
     mMasterName = pMasterName;
  }

  public void addOnePartner(String pPartnerName)
  {
    mMyPartner.add(pPartnerName);
  }

  public Vector getmyPartner()
  {
     return mMyPartner;
  }

  public String getMasterName()
  {
     return this.mMasterName;
  }

}