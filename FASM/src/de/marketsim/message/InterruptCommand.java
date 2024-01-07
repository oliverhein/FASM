package de.marketsim.message;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class InterruptCommand implements java.io.Serializable
{
    public String mReason = "";
    public InterruptCommand(String pReason )
    {
       this.mReason = pReason;
    }
}