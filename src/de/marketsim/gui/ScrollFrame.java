package de.marketsim.gui;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import com.borland.jbcl.layout.*;

import de.marketsim.util.OperatorDesc;
import de.marketsim.config.Configurator;


/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class ScrollFrame extends JFrame
{

  JScrollPane jsb;

  Vector mData = null;
  Hashtable  mChoiceTable = new Hashtable();
  private BoxLayout2 boxLayout21 = new BoxLayout2();

  public ScrollFrame( Vector pData )
  {
     super("ScrollFrame Test");
     mData = pData;
     setSize(500,500);
     this.boxLayout21.setAxis(1);
     this.getContentPane().setLayout(boxLayout21 );
     addMenuBar();
     this.addPPP();
     setVisible(true);
  }

  private void addMenuBar()
  {
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu( "reselect" );
    menuBar.add( menu );
    menu.addMenuListener( new myMenuListener() );
    this.setJMenuBar( menuBar );
  }

  class myMenuListener implements MenuListener
  {

    public myMenuListener()
    {

    }

    public void menuSelected(MenuEvent e)
    {
       doreselect();
    }

    public void menuDeselected(MenuEvent e)
    {


    }

    public void doreselect()
    {

      Vector vv = new Vector();
       OperatorDesc ag = new OperatorDesc("V1", "Trader", "R1") ;
       vv.add( ag );
       ag = new OperatorDesc("V2", "Trader", "R1") ;
       vv.add( ag );
       ag = new OperatorDesc("V3", "Trader", "R1") ;
       vv.add( ag );
       ag = new OperatorDesc("V4", "Trader", "R1") ;
       vv.add( ag );
       ag = new OperatorDesc("V5", "Trader", "R1") ;
       vv.add( ag );
       ag = new OperatorDesc("V6", "Trader", "R1") ;
       vv.add( ag );
       ag = new OperatorDesc("V7", "Trader", "R1") ;
       vv.add( ag );
       ag = new OperatorDesc("V8", "Trader", "R1") ;
       vv.add( ag );
       ag = new OperatorDesc("V9", "Trader", "R1") ;
       vv.add( ag );
       ag = new OperatorDesc("V10", "Trader", "R1") ;
       vv.add( ag );
       ag = new OperatorDesc("V11", "Trader", "R1") ;
       vv.add( ag );
       ag = new OperatorDesc("V12", "Trader", "R1") ;
       vv.add( ag );
       ag = new OperatorDesc("V13", "Trader", "R1") ;
       vv.add( ag );
       ag = new OperatorDesc("V14", "Trader", "R1") ;
       vv.add( ag );
       ag = new OperatorDesc("V15", "Trader", "R1") ;
       vv.add( ag );
       ag = new OperatorDesc("V16", "Trader", "R1") ;
       vv.add( ag );
       ag = new OperatorDesc("V17", "Trader", "R1") ;
       vv.add( ag );
       ag = new OperatorDesc("V18", "Trader", "R1") ;
       vv.add( ag );

       ScrollFrame sf = new ScrollFrame( vv);


    }

    public void menuCanceled(MenuEvent e)
    {

    }
}

  private void addPPP2()
  {
     TextArea   pp= new TextArea();
     pp.setSize(600,400);
     jsb = new  JScrollPane( pp );
     this.getContentPane().add( jsb, BorderLayout.CENTER );
  }

  private void addPPP()
  {
      int rows = 2 + mData.size();
      int cols = 4;

      for ( int i=0; i<mData.size(); i++)
      {
         java.awt.Checkbox  cbx = new Checkbox();
         OperatorDesc agd = (OperatorDesc) mData.elementAt(i);
         mChoiceTable.put( agd.mName, cbx  );
      }

       JPanel pp = new JPanel();
       pp.setSize(600,400);
       pp.setLayout( new GridLayout( rows, cols,10,1) ); // OK

       JButton jb1 =  new JButton("Select All");
       jb1.addActionListener(new jBSelectAll_actionAdapter() );
       pp.add( jb1 );

       JButton jb2 = new JButton("Deselect All");
       jb2.addActionListener(new jBDeselectAll_actionAdapter() );
       pp.add( jb2 );

       JButton jb3 = new JButton("OK") ;
       jb3.addActionListener(new jBOK_actionAdapter() );
       pp.add( jb3 );

       JButton jb4 =new JButton("Cancel");
       jb4.addActionListener(new jBCancel_actionAdapter() );
       pp.add( jb4 );

       pp.add( new Label("Name") );
       pp.add( new Label("Type") );
       pp.add( new Label("Regel") );
       pp.add( new Label("Selected") );

       for ( int i=0; i< mData.size(); i++)
       {
          OperatorDesc agd = (OperatorDesc) mData.elementAt(i);
          pp.add( new Label( agd.mName ) );
          pp.add( new Label( agd.mType ) );
          pp.add( new Label( agd.mRegel) );
          pp.add( (Checkbox)  mChoiceTable.get( agd.mName)  );
       }
       jsb = new  JScrollPane( pp );
       jsb.setBounds(0,0,200,300);
       this.getContentPane().add( jsb, BorderLayout.CENTER );
  }

  class jBSelectAll_actionAdapter implements java.awt.event.ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
         doselectall();
    }
  }

  class jBDeselectAll_actionAdapter implements java.awt.event.ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      unselectall();
    }
  }

  class jBOK_actionAdapter implements java.awt.event.ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      makeok();
    }
  }

  class jBCancel_actionAdapter implements java.awt.event.ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
        makecancel();
    }
  }

  public void doselectall()
  {
     for ( int i=0; i<mData.size(); i++ )
     {
       OperatorDesc agd = (OperatorDesc) mData.elementAt(i);
       Checkbox cb = (Checkbox)  mChoiceTable.get( agd.mName) ;
       cb.setState(true);
     }
  }

  public void unselectall()
  {
     for ( int i=0; i<mData.size(); i++ )
     {
       OperatorDesc agd = (OperatorDesc) mData.elementAt(i);
       Checkbox cb = (Checkbox)  mChoiceTable.get( agd.mName) ;
       cb.setState( false );
     }
  }

  public void makeok()
  {
     Vector NameList= new Vector();
     for ( int i=0; i<mData.size(); i++ )
     {
       OperatorDesc agd = (OperatorDesc) mData.elementAt(i);
       Checkbox cb = (Checkbox)  mChoiceTable.get( agd.mName) ;
       if ( cb.getState() )
       {
         NameList.add( agd.mName );
       }
     }
     // save into Configurator

     this.setVisible(false);

  }

  public void makecancel()
  {
     this.setVisible(false);
  }

  public static void main(String[] args)
  {

    Vector vv = new Vector();
    OperatorDesc ag = new OperatorDesc("V1", "Trader", "R1") ;
    vv.add( ag );
    ag = new OperatorDesc("V2", "Trader", "R1") ;
    vv.add( ag );
    ag = new OperatorDesc("V3", "Trader", "R1") ;
    vv.add( ag );
    ag = new OperatorDesc("V4", "Trader", "R1") ;
    vv.add( ag );
    ag = new OperatorDesc("V5", "Trader", "R1") ;
    vv.add( ag );
    ag = new OperatorDesc("V6", "Trader", "R1") ;
    vv.add( ag );
    ag = new OperatorDesc("V7", "Trader", "R1") ;
    vv.add( ag );
    ag = new OperatorDesc("V8", "Trader", "R1") ;
    vv.add( ag );
    ag = new OperatorDesc("V9", "Trader", "R1") ;
    vv.add( ag );
    ag = new OperatorDesc("V10", "Trader", "R1") ;
    vv.add( ag );
    ag = new OperatorDesc("V11", "Trader", "R1") ;
    vv.add( ag );
    ag = new OperatorDesc("V12", "Trader", "R1") ;
    vv.add( ag );
    ag = new OperatorDesc("V13", "Trader", "R1") ;
    vv.add( ag );
    ag = new OperatorDesc("V14", "Trader", "R1") ;
    vv.add( ag );
    ag = new OperatorDesc("V15", "Trader", "R1") ;
    vv.add( ag );
    ag = new OperatorDesc("V16", "Trader", "R1") ;
    vv.add( ag );
    ag = new OperatorDesc("V17", "Trader", "R1") ;
    vv.add( ag );
    ag = new OperatorDesc("V18", "Trader", "R1") ;
    vv.add( ag );

    ScrollFrame scrollFrame = new ScrollFrame( vv );
  }


}