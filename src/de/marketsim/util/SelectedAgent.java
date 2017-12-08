package de.marketsim.util;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Organisation: </p>
 * @author unbekannt
 * @version 1.0
 */

import java.util.Vector;

public class SelectedAgent
{
    public int    AgentIndex = -1;
    public String AgentName;
    public String GroupType;
    // Every Agent will have at least 1 Partner.
    public Vector myAllPartner = new Vector();
}