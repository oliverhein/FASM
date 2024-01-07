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

public class CommunicationModel
{
  private int     NumberOfPartner = 0;
  private Vector  One2ManyCommunicationList = new Vector();

  public CommunicationModel()
  {

  }

  public void addOne2ManyCommunication( Object  p)
  {
      this.One2ManyCommunicationList.add(p);
      this.NumberOfPartner =One2ManyCommunicationList.size();
  }

  public Vector getOne2ManyCommunicationList()
  {
      return this.One2ManyCommunicationList;
  }

}