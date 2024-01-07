package de.marketsim.test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class JButtonTableExample extends JFrame
{
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JButton jBNewFile = new JButton();
  private JButton jBRemove = new JButton();

  private DefaultTableModel tm = new DefaultTableModel();

  private JTable jTable1 = new JTable();

  private int fileno=1;
  private JButton jBCheck = new JButton();

  public JButtonTableExample()
  {
        super( "JButtonTable Test" );


        /*

        dm.setDataVector
        ( new Object[][]
        {  {"button 1","foo"},
           {"button 2","bar"}
        },
        new Object[] { "Button", "String" }
        );

        JTable table = new JTable(dm);

        table.getColumn("Button").setCellRenderer( new ButtonRenderer() );
        table.getColumn("Button").setCellEditor  ( new ButtonEditor (new JCheckBox() ) );

        JScrollPane scroll = new JScrollPane(table);
        getContentPane().add( scroll );
        setSize( 400, 100 );
        setVisible(true);
        */

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    tm.setColumnCount(2);
    tm.setColumnIdentifiers( new String[] {"Networkfile", "Nodes"} );

    jTable1.setModel( tm );

    //jTable1.setTableHeader( javax.jTableHeader )  ;

    //jTable1.getColumn("Networkfile").setCellRenderer( new ButtonRenderer() );
    //jTable1.getColumn("Networkfile").setCellEditor  ( new ButtonEditor (new JCheckBox() ) );

    //jTable1.getColumn("Nodes").setCellRenderer( new ButtonRenderer() );
    //jTable1.getColumn("Nodes").setCellEditor  ( new ButtonEditor (new JCheckBox() ) );



    jTable1.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
    jTable1.setSelectionBackground( Color.red );
    jTable1.setGridColor( Color.BLUE );
    // jTable1.setColumnSelectionAllowed( true );

    //jTable1.setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );


    }


    public static void main(String[] args)
    {
       JButtonTableExample frame = new JButtonTableExample();
       frame.setSize(400,400);
       frame.setVisible(true);

       frame.addWindowListener (new WindowAdapter()
       {      public void windowClosing(WindowEvent e)
              {   System.exit(0);
              }
        });
     }


  private void jbInit() throws Exception {
    this.getContentPane().setLayout(null);
    jScrollPane1.setBorder(BorderFactory.createLineBorder(Color.black));
    jScrollPane1.setBounds(new Rectangle(9, 48, 270, 286));
    jBNewFile.setBounds(new Rectangle(292, 49, 100, 32));
    jBNewFile.setText("New File");
    jBNewFile.addActionListener(new JButtonTableExample_jBNewFile_actionAdapter(this));
    jBRemove.setBounds(new Rectangle(292, 95, 99, 31));
    jBRemove.setText("Remove");
    jBRemove.addActionListener(new JButtonTableExample_jBRemove_actionAdapter(this));
    jBCheck.setBounds(new Rectangle(294, 138, 100, 27));
    jBCheck.setText("Check");
    jBCheck.addActionListener(new JButtonTableExample_jBCheck_actionAdapter(this));
    this.getContentPane().add(jScrollPane1, null);

    jScrollPane1.getViewport().add(jTable1, null);

    this.getContentPane().add(jBNewFile, null);
    this.getContentPane().add(jBRemove, null);
    this.getContentPane().add(jBCheck, null);
  }

  void jBNewFile_actionPerformed(ActionEvent e)
  {
      tm.addRow( new String[] {"aaa"+fileno, "bbbb"} );

      fileno=fileno+1;
  }

  void jBRemove_actionPerformed(ActionEvent e)
  {
     int j= this.jTable1.getSelectedRow();

     System.out.println( "j=" + j );

     //this.jTable1.remove(j);
     //tm.moveRow( j, j+1, 1 );
     tm.removeRow(j);
  }

  void jBCheck_actionPerformed(ActionEvent e) {

    int j= this.jTable1.getSelectedRow();
    String filename = (String) this.jTable1.getValueAt(j,0);

    System.out.println( "filename=" + filename );

  }
}

class JButtonTableExample_jBNewFile_actionAdapter implements java.awt.event.ActionListener {
  private JButtonTableExample adaptee;

  JButtonTableExample_jBNewFile_actionAdapter(JButtonTableExample adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBNewFile_actionPerformed(e);
  }
}

class JButtonTableExample_jBRemove_actionAdapter implements java.awt.event.ActionListener {
  private JButtonTableExample adaptee;

  JButtonTableExample_jBRemove_actionAdapter(JButtonTableExample adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBRemove_actionPerformed(e);
  }
}

class JButtonTableExample_jBCheck_actionAdapter implements java.awt.event.ActionListener {
  private JButtonTableExample adaptee;

  JButtonTableExample_jBCheck_actionAdapter(JButtonTableExample adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBCheck_actionPerformed(e);
  }
}
