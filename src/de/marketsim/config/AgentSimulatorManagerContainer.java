package de.marketsim.config;

/**
 * Überschrift:   Market Simulator
 * Beschreibung:
 * Copyright:     Copyright (c) 2005
 * Organisation:
 * @author Xining Wang
 * @version 1.0
 */

import java.util.*;
import jade.core.AID;

public class AgentSimulatorManagerContainer
{
    private static Hashtable commlist = new Hashtable();
    private static Vector    NameGiven  = new Vector();
    private static int serialno = 1;

    public static int getNextSerialno()
    {
       int j = serialno;
       serialno++;
       return j;
    }


    public static void registerNewAgentSimulatorManager(String pName, Object pAID)
    {
          commlist.put(pName, pAID);
    }

    public static void unregisterAgentSimulatorManager(String pName)
    {
          commlist.remove(pName);
    }

    public static boolean AgentSimulatorManagerIsRegistered(String pName)
    {
         return  commlist.containsKey(pName);
    }

    public static boolean ListIsEmpty()
    {
         return  commlist.isEmpty();
    }


    public static Vector getAgentSimulatorManagerAIDs()
    {
         Vector list = new Vector();
         Enumeration pp = commlist.elements();
         while ( pp.hasMoreElements() )
         {
           list.add( pp.nextElement() );
         }
         return list;
    }

    // Return:  {SM1,SM2,SM3}
    public static String[] getAgentSimulatorManagerNames()
    {
         Vector list = new Vector();
         Enumeration pp = commlist.elements();
         String tt[] = new String[commlist.size()];
         int i=0;
         while ( pp.hasMoreElements() )
         {
           AID aid = (AID) pp.nextElement() ;
           tt[i] = aid.getLocalName();
           i++;
         }
         return tt;
    }

    // Return:  SM1,SM2,SM3,
    public static String getAgentSimulatorManagerNamesString()
    {
         Enumeration pp = commlist.elements();
         String tt = "";
         while ( pp.hasMoreElements() )
         {
           AID aid = (AID) pp.nextElement() ;
           tt = tt + aid.getLocalName() + ",";
         }
         return tt;
    }

    public synchronized static String getNewName()
    {
           return "SM" + getNextSerialno();
    }

}