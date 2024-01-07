package de.marketsim.message;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import de.marketsim.message.AktienTrade_Order;

public class MessageWrapper implements java.io.Serializable
{

  public int mMessageType = 0;
  public Object mMessageContent  ;

  public MessageWrapper(int pType, Object pContent)
  {
      this.mMessageContent = pContent;
      this.mMessageType    = pType;
  }

  public MessageWrapper()
  {

  }

  /**
   * This methode is only used by BlankoAgent
   */
  public void setStatusToActivated()
  {
      if ( this.mMessageContent != null )
      {
        AktienTrade_Order order = (AktienTrade_Order) this.mMessageContent;
        order.setBlankoActivated();
      }
  }

  /**
   * This methode is only used by BlankoAgent
   */

  public void setStatusToDeactivated()
  {
    if ( this.mMessageContent != null )
    {
      AktienTrade_Order order = (AktienTrade_Order) this.mMessageContent;
      order.setBlankoDeactivated();
    }

  }

}