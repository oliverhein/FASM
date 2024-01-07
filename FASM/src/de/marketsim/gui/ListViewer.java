package de.marketsim.gui;

import java.awt.*;
import javax.swing.JFrame;
import java.awt.event.*;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class ListViewer extends JFrame {
  private TextArea textArea1 = new TextArea();
  private Button Close = new Button();

  public ListViewer()
  {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void putMessage(String msg)
  {
    if ( msg != null )
    {
      this.textArea1.append( msg + "\r\n" );
    }
  }

  public void clearBoard()
  {
     this.textArea1.setText("") ;
  }

  public static void main(String[] args)
  {
    ListViewer pp = new ListViewer();
  }

  private void jbInit() throws Exception
  {
    this.setSize(450,340);
    textArea1.setBounds(new Rectangle(9, 12, 383, 259));
    this.getContentPane().setLayout(null);
    this.setTitle("Agent Distribution List");
    Close.setLabel("Close");
    Close.setBounds(new Rectangle(158, 276, 99, 19));
    Close.addActionListener(new ListViewer_Close_actionAdapter(this));
    this.getContentPane().add(textArea1, null);
    this.getContentPane().add(Close, null);
  }

  void Close_actionPerformed(ActionEvent e)
  {
     this.setVisible(false);
  }
}

class ListViewer_Close_actionAdapter implements java.awt.event.ActionListener {
  private ListViewer adaptee;

  ListViewer_Close_actionAdapter(ListViewer adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.Close_actionPerformed(e);
  }
}