package de.marketsim.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.*;

public class DialogAdvanced1Frame extends JFrame {
  private JButton jBAdd = new JButton();
  private JButton jBWeiter = new JButton();
  private JButton jBAbbrechen = new JButton();

  Vector   mFileList = new Vector();
  Vector   mFileItemGUIList = new Vector();


  public DialogAdvanced1Frame() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public static void main(String[] args) {
    DialogAdvanced1Frame dialogAdvanced1Frame = new DialogAdvanced1Frame();
  }
  private void jbInit() throws Exception {
    jBAdd.setBounds(new Rectangle(293, 21, 98, 39));
    jBAdd.setText("Add");
    jBAdd.addActionListener(new DialogAdvanced1Frame_jBAdd_actionAdapter(this));
    this.getContentPane().setLayout(null);
    jBWeiter.setBounds(new Rectangle(296, 73, 95, 39));
    jBWeiter.setText("Weiter");
    jBAbbrechen.setBounds(new Rectangle(297, 125, 95, 36));
    jBAbbrechen.setText("Abbrechen");
    this.getContentPane().add(jBAdd, null);
    this.getContentPane().add(jBWeiter, null);
    this.getContentPane().add(jBAbbrechen, null);
  }

  void jBAdd_actionPerformed(ActionEvent e)
  {
        // remove old GUI (Lable)
        for ( int i=0; i<mFileItemGUIList.size(); i++)
        {
           java.awt.Component  cmp = ( java.awt.Component ) mFileItemGUIList.elementAt(i);
           this.getContentPane().remove(cmp);
        }
        mFileItemGUIList.removeAllElements();

        int x = 10;
        int y = 10;
        for ( int i=0; i<mFileList.size(); i++)
        {
           java.awt.Label lb =  new Label( (String) mFileList.elementAt(i) );
           lb.setLocation(x, y);
           lb.setSize( 50, 20 );
           y = y + 25;
           this.getContentPane().add(lb);
           mFileItemGUIList.add(lb);
        }

  }
}

class DialogAdvanced1Frame_jBAdd_actionAdapter implements java.awt.event.ActionListener {
  private DialogAdvanced1Frame adaptee;

  DialogAdvanced1Frame_jBAdd_actionAdapter(DialogAdvanced1Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBAdd_actionPerformed(e);
  }
}