package de.marketsim.test;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ScrollFrame extends JFrame {
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JButton jBCheck = new JButton();
  private JButton jBDelete = new JButton();
  private JList jList1 = new JList();
  private JButton jBNew = new JButton();

  public ScrollFrame() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public static void main(String[] args) {
    ScrollFrame scrollFrame = new ScrollFrame();
  }
  private void jbInit() throws Exception {
    this.getContentPane().setLayout(null);
    jScrollPane1.setBounds(new Rectangle(8, 97, 357, 253));
    jBCheck.setBounds(new Rectangle(376, 101, 75, 30));
    jBCheck.setText("Check");
    jBDelete.setBounds(new Rectangle(376, 138, 77, 30));
    jBDelete.setText("Delete");
    jBNew.setBounds(new Rectangle(379, 178, 70, 27));
    jBNew.setText("New");
    jBNew.addActionListener(new ScrollFrame_jBNew_actionAdapter(this));
    this.getContentPane().add(jScrollPane1, null);
    this.getContentPane().add(jBCheck, null);
    jScrollPane1.getViewport().add(jList1, null);
    this.getContentPane().add(jBDelete, null);
    this.getContentPane().add(jBNew, null);
  }

  void jBNew_actionPerformed(ActionEvent e)
  {



  }

}

class ScrollFrame_jBNew_actionAdapter implements java.awt.event.ActionListener {
  private ScrollFrame adaptee;

  ScrollFrame_jBNew_actionAdapter(ScrollFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBNew_actionPerformed(e);
  }
}