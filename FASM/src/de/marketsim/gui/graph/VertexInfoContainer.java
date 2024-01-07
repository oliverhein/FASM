package de.marketsim.gui.graph;

import edu.uci.ics.jung.utils.UserDataContainer;
import edu.uci.ics.jung.utils.UserDataContainer.CopyAction;
import java.util.Iterator;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.Hashtable;

public class VertexInfoContainer implements UserDataContainer, Cloneable
{

  String mVertextName =null;
  Hashtable VertexInfoList = new Hashtable();

  public VertexInfoContainer( String pVertextName)
  {
     this.mVertextName = pVertextName;
     this.VertexInfoList.put("NAME", this.mVertextName);

  }

  public Object clone() throws CloneNotSupportedException
  {
      return null;
  }

  public void addUserDatum(Object parm1, Object parm2, UserDataContainer.CopyAction parm3)
  {

  }
  public void importUserData(UserDataContainer parm1)
  {

  }

  public Iterator getUserDatumKeyIterator()
  {
      return  this.VertexInfoList.keySet().iterator();
  }

  public UserDataContainer.CopyAction getUserDatumCopyAction(Object pKey)
  {
    return new myCopyActionImpl();
    //return null;
    /**@todo Diese edu.uci.ics.jung.utils.UserDataContainer-Methode implementieren*/
    //throw new java.lang.UnsupportedOperationException("Methode getUserDatumCopyAction() noch nicht implementiert.");

  }

  public Object getUserDatum(Object pKey)
  {
      return  this.VertexInfoList.get(pKey);
  }

  public void setUserDatum(Object parm1, Object parm2, UserDataContainer.CopyAction parm3)
  {
    /**@todo Diese edu.uci.ics.jung.utils.UserDataContainer-Methode implementieren*/
    throw new java.lang.UnsupportedOperationException("Methode setUserDatum() noch nicht implementiert.");
  }

  public Object removeUserDatum(Object pKey)
  {
     return this.VertexInfoList.remove(pKey);
  }

  public boolean containsUserDatumKey(Object pKey)
  {
     return  this.VertexInfoList.containsKey(pKey);
  }
}

class myCopyActionImpl implements CopyAction
{
    // Methoden
    public Object onCopy(Object object, UserDataContainer userDataContainer, UserDataContainer userDataContainer2)
    {
      return object;
    }
  }
