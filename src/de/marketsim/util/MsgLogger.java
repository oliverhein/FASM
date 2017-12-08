package de.marketsim.util;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.*;

import java.net.URL;
import java.io.InputStream;

public class MsgLogger extends Logger
{

    public static boolean AppenderHasIntialized = false;

    /** default Logger */
    public static Logger LoggerInstance = null;

    /** constructor */
    public MsgLogger(String pName)
    {
        super( pName );
    }

    public static void loadMsgLoggerConfig()
    {
        URL  uu = ClassLoader.getSystemResource("loggerproperties.xml");
        try
        {
          InputStream ins = uu.openStream();
          byte[] buffer = new byte[ ins.available() ];
          ins.read( buffer );
          String ss = new String( buffer );
          DOMConfigurator.configure( uu );
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /** get a new Logger with pname */
    public static Logger getMsgLogger(String pName)
    {
         return Logger.getLogger( pName );
    }

    /** self test program **/

    public static void ggg()
    {
        String ss = "AAAAAAAAAA";
        ss.charAt(-1);
    }

    public static void main(String args[])
    {
       loadMsgLoggerConfig();

       Logger log = getMsgLogger("V24.COMMUNICATION.ABC");
       System.out.println("V24 debug=" + log.isDebugEnabled() );

       log.debug("Hallo yyyyy");
       log.error("ERrror xxxx");

       try
       {
          String ss = "ABC";
          String ss1 = ss.substring(3,100);
       }
       catch (Exception ex)
       {
            log.error("Eoorr ", ex);
            log.debug( "Eoorr ", ex);
            log.fatal("Eoorr ", ex);
            log.info("Eoorr ", ex);

       }

       log = getMsgLogger("V25");
       System.out.println("V25 debug=" + log.isDebugEnabled() );
       log.debug("Market simulation");

       log = getMsgLogger("STATISTIC");
       log.info("Hallo");
       log.debug("AAAAAAAA");

       HelpTool.pause("any key");

       log.debug("AAAAAAAA");
       log.debug("AAAAAAAA");
       log.debug("AAAAAAAA");
       log.debug("AAAAAAAA");
       log.debug("AAAAAAAA");

       try
       {

          ggg();

       }
       catch (Exception ex)
       {
          log.debug("Error in test", ex);
          log.error("Error in test", ex);
       }

       log = getMsgLogger("TOBINTAX");
       System.out.println("TOBINTAX Log" + log.isDebugEnabled() );
       log.info("Hallo");
       log.debug("AAAAAAAA");



    }

    static class XYZ
    {
        int j = 100;
        String name = "Wang";
        XYZ()
        {

        }

        public String getString()
        {
           return "Name="+name + "j="+j;
        }
    }


}
