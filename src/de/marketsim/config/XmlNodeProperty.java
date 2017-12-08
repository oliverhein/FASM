/**
 *
 * This is a generall Xml Config loader for the
 * Simulation configuration
 * Autor: Xining Wang
 * (c) 2006
 */

package de.marketsim.config;

import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class XmlNodeProperty {

/**
 * an XML Element like
 *
 * <Network No=1 >
 *
 *    <NetworkFile>TOMAP1</NetworkFile>
 *    <Investor>10</Investor>
 *    <NoiseTrader>15</NoiseTrader>
 * </Network>
 *
 */

private Node mNode;

public XmlNodeProperty( Node pNode )
{
    this.mNode = pNode;
}

public void AddOneNode ( Node pNode)
{
    this.mNode.appendChild(mNode);
}

public void removeAllStructure(String pTagName)
{
      Node ll[] = this.getStructureList(pTagName);
      for ( int i=0; i<ll.length; i++ )
      {
          this.mNode.removeChild(ll[i]);
      }
}

public void removeOneStructure(String pTagName, String pAttributeName, String pAttributeValue )
{
    Node nd = this.getStructure(pTagName, pAttributeName, pAttributeValue);
    if ( nd != null )
    {
        this.mNode.removeChild(nd);
    }

}

public String getProperty( String pPropertyName)
{
    NodeList list = this.mNode.getChildNodes();
    for ( int i=0; i<list.getLength(); i++)
    {
      Node nd = list.item(i);
      if ( nd.getNodeType() == Node.ELEMENT_NODE)
      {
           if ( nd.getNodeName().equals( pPropertyName ) )
           {
              String ss1 = this.mygetNodeValue( nd );
              String ss2 = nd.getNodeValue();
              //return this.mygetNodeValue( nd ;
              return ss1;
           }
      }
    }
    return null;
}

public String getProperty( String pPropertyName, String pDefaultValue)
{
    String ss = this.getProperty(pPropertyName);
    if ( ss == null )
    {
      return pDefaultValue;
    }
    else
    {
      return ss;
    }
}

public static String mygetNodeValue(Node pNode)
{
    String res = null;
    if (pNode.getNodeType() == Node.ELEMENT_NODE)
    {
            Node tempnode = pNode.getFirstChild();

            if ( tempnode== null)
            {
                return null;
            }
            else
            res = tempnode.getNodeValue();
    }
    else if (pNode.getNodeType() == Node.TEXT_NODE) {
        res = pNode.getNodeValue();
    }
    return res;
}

private String[] getPropertyList()
{
    NodeList list = this.mNode.getChildNodes();
    Vector dd = new Vector();
    for ( int i=0; i<list.getLength(); i++)
    {
      Node nd = list.item(i);
      if ( nd.getNodeType() == Node.ELEMENT_NODE )
      {
          dd.add( nd.getNodeName() );
      }
    }
    String tt[] = new String[ dd.size() ];
    for ( int i=0; i<dd.size();i++)
    {
       tt[i] = (String) dd.elementAt(i);
    }
    return tt;
}

public Node getStructure(String pTagName)
{
    NodeList list = this.mNode.getChildNodes();
    for ( int i=0; i<list.getLength(); i++)
    {
      Node nd = list.item(i);
      if ( nd.getNodeType() == Node.ELEMENT_NODE )
      {
          if ( nd.getNodeName().equals( pTagName ) )
          {
              return nd;
          }
      }
    }
    return null;
}

public Node getStructure(String pTagName, String pAttributeName,  String pAttributeValue)
{
    Node nodelist[] = this.getStructureList(pTagName);

    for ( int i=0; i<nodelist.length; i++)
    {
      Node nd = nodelist[i];
      NamedNodeMap map = nd.getAttributes();

      System.out.println( i +"." + nd.getNodeName() + " has "  + map.getLength() + " Attributes");

      Node tempnd = map.getNamedItem( pAttributeName );
      if ( tempnd.getNodeValue().equals( pAttributeValue ) )
      {
        return nd;
      }
    }
    return null;
}


public Node[] getStructureList(String pTagName)
{
    NodeList list = this.mNode.getChildNodes();
    Vector dd = new Vector();
    for ( int i=0; i<list.getLength(); i++)
    {
      Node nd = list.item(i);
      if ( nd.getNodeType() == Node.ELEMENT_NODE )
      {
          if ( nd.getNodeName().equals( pTagName ) )
          {
              dd.add ( nd );
          }
      }
    }

    Node ll[] = new Node[dd.size()];
    for ( int i=0; i<dd.size(); i++ )
    {
        ll[i] = ( Node )dd.elementAt(i);
    }
    return ll;
}

/**
 * For self test
 */
public static void dotest(String pXmlFileName, String pInstanceId)
{
  DocumentBuilderFactory docBFac;
  DocumentBuilder docBuild;
  Document doc = null;

  try
  {
     docBFac = DocumentBuilderFactory.newInstance();
     docBuild = docBFac.newDocumentBuilder();
     doc = docBuild.parse( pXmlFileName );

     Element rootelement = doc.getDocumentElement();
/*
     XmlNodeProperty rootnode = new XmlNodeProperty(rootelement);

     Node ll[] = rootnode.getStructureList("Order");

     System.out.println( ll.length + " Networks are checked" );


Element element; // Wurde vorher initialisiert
NodeList list = element.getElementsByTagName("Network");
// Alternative: getElementsByTagNameNS
for (int i = 0, size = list.getLength(); i < size; i++) {
Node node = list.item(i);
if (node instanceof Element) {
Element subelement = (Element)node;
[...]
}
}

*/
     NodeList list = rootelement.getChildNodes();
     for (int i = 0, size = list.getLength(); i < size; i++) {
       Node node = list.item(i);
       if (node instanceof Element) {
         Element subelement = (Element)node;


       } else if

         (node instanceof Text)
         {
           Text textnode = (Text)node;
           System.out.println(textnode.getData());

         }
     }


     /*

 XmlNodeProperty rootnode = new XmlNodeProperty(rootelement);
     Node ll[] = rootnode.getStructureList("Order");

     Node nn = rootnode.getStructure("Network", "No" , pInstanceId);
     rootnode.removeOneStructure("Network", "No", "1");
     ll = rootnode.getStructureList("Network");

     if ( nn == null )
     {
         System.out.println("Network with No " + pInstanceId + " does not exist. Please check " + pXmlFileName);
         return;
     }

     XmlNodeProperty tt1 = new XmlNodeProperty( nn );

     String pp[] = tt1.getPropertyList();

     System.out.println( "PropertyList =" + pp.length );

     for ( int i=0; i<pp.length; i++)
     {
         System.out.println( i+". PropertyName =" + pp[i] );
         System.out.println( i+". PropertyValue =" + tt1.getProperty( pp[i] ) );
     }

     System.out.println( "NetworkFile=" + tt1.getProperty("NetworkFile") );

     System.out.println( "now remove one Netwrok with 1") ;
*/

  //   System.out.println( "After removing "+ll.length + " Networks is checked" );


     System.out.println( "now remove all Networks ") ;

    // rootnode.removeAllStructure("Network");
     //ll = rootnode.getStructureList("Network");
     //System.out.println( "After removing "+ll.length + " Networks are checked" );


    //ll = rootnode.getStructureList("Network");
    //XmlNodeProperty ddd = new XmlNodeProperty( ll[1] );

     /*
     Node nnd = ll[0].getFirstChild();
     System.out.println( "NodeName =" +   nnd.getNodeName() );
     System.out.println( "NodeValue =" +  nnd.getNodeValue() );

     String ss = getNodeValue( nnd  );

     System.out.println( "my getProperty( Name ) =" +  ddd.getProperty( "Price" )  );

     System.out.println( "my getProperty( Name ) =" +  ddd.getProperty( "HHHH" )  );

     System.out.println( "my getProperty( Name ) =" +  XmlNodeProperty.mygetNodeValue( ll[2]  )  );
*/


  }
  catch (Exception ex)
  {
      ex.printStackTrace();
  }

}

/**
 * The self test program
 * @param args
 */
public static void main(String[] args)
{
    /*
    if ( args.length < 2 )
    {
        System.out.println( "Usage:" );
        System.out.println( "java de.marketsim.config.XmlNodeConfig <XmlFileName> <No>" );
        System.out.println( "Example" );
        System.out.println( "java de.marketsim.config.XmlNodeConfig run/simulation1.xml 1");
        return;
    }

    String xmlfilename = args[0];
    String no          = args[1];

    */

    String xmlfilename = "C:/order.xml";
    String no          = "1";

    dotest(xmlfilename, no);

}

}



