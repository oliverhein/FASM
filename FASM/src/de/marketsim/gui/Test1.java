/**
 * Created on 02.05.2005
 * @author Xining Wang
 * This is a test program.
 *
 */

package de.marketsim.gui;

import javax.swing.JFrame;

import javax.swing.JButton;

import java.awt.*;
import java.awt.image.*;

import javax.imageio.*;
import java.io.*;
import java.util.*;

import com.borland.jbcl.layout.*;

public class Test1 extends JFrame {

	private javax.swing.JPanel jContentPane = null;

	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
  private PaneLayout paneLayout1 = new PaneLayout();

  private FlowLayout flowLayout1 = new FlowLayout();
	/**
	 * This method initializes jButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("Check");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					System.out.println("actionPerformed()"); 
					saveFrame();
				}
			});
		}
		return jButton;
	}
	/**
	 * This method initializes jButton1
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("Close");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); 

					System.exit(0);

				}
			});
		}
		return jButton1;
	}

	public void doresize()
	{
		   int w = this.getWidth();
		   int h = this.getHeight();
		   System.out.println("w=" + w + " h="+ h);
		   this.setSize( w+100, h+100  );
		   this.repaint();
	}

	public void saveFrame()
	{
		JFrame f2 = new JFrame("test");
		f2.setSize(400,400);
		f2.setVisible(true);
		try
		{
		   System.out.println("go to sleep ------------");
           Thread.sleep(10000);
		   System.out.println("wake again, set focus to this window ------------");
		   //this.jButton1.requestFocus();
		   System.out.println("press enter to continue ------------");
		   pause();
		   this.requestFocus();
		   this.repaint();

		   Thread.sleep(1000);
		   ScreenImage.createImage( this, "frame.jpg"  );
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void saveimage1()
	{
		Image i = new javax.swing.ImageIcon("myimage.gif").getImage();
		BufferedImage bimage1 = new BufferedImage ( this.getWidth(),this.getHeight(), BufferedImage.TYPE_INT_RGB);
		BufferedImage bimage2 = new BufferedImage ( this.getWidth(),this.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);

		Graphics2D  g2d_rgb = bimage1.createGraphics();
		Graphics2D  g2d_rgb_4bytes = bimage2.createGraphics();

		g2d_rgb.setClip(0,0, this.getWidth(),this.getHeight() );
		g2d_rgb_4bytes.setClip(0,0, this.getWidth(),this.getHeight() );


		this.paint( g2d_rgb );
		this.paint( g2d_rgb_4bytes );

		g2d_rgb.dispose();
		g2d_rgb_4bytes.dispose();

		try
		{
			FileOutputStream fos1 = new FileOutputStream ("testframe.jpg" );
			FileOutputStream fos2 = new FileOutputStream ("testframe_4bytes.jpg" );
			ImageIO.write( bimage1, "jpg",  fos1  );
			ImageIO.write( bimage2, "jpg",  fos2  );
			fos1.close();
			fos2.close();

		}
		catch (Exception ex)
		{
		    ex.printStackTrace();
		}
	}

	/**
	 * This method initializes jButton2
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("resize");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					System.out.println("actionPerformed()"); 
					doresize();
				}
			});
		}
		return jButton2;
	}
   	public static void main(String[] args)
  	{
  		Test1  tt = new Test1();
  		tt.setSize(400,400);
  		tt.setVisible(true);
	}
	/**
	 * This is the default constructor
	 */
	public Test1() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize()
	{

          String ss="d:/logo.gif";
          Image mm = Toolkit.getDefaultToolkit().getImage( ss );
          this.setIconImage( mm );


		this.setSize(369, 203);
		//this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
    this.getContentPane().setLayout(flowLayout1);
                //this.getContentPane().add(getJContentPane(), null);

	}
	/**
	 * This method initializes jContentPane
	 *
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(paneLayout1);
			jContentPane.add(getJButton(),  new PaneConstraints("jButton", "jButton", PaneConstraints.ROOT, 1.0f));
      jContentPane.add(getJButton2(),  new PaneConstraints("jButton2", "jButton", PaneConstraints.BOTTOM, 0.66502464f));
			jContentPane.add(getJButton1(),  new PaneConstraints("jButton1", "jButton2", PaneConstraints.RIGHT, 0.6666667f));
		}
		return jContentPane;
	}

	public static void pause()
	{
		BufferedReader rd = new BufferedReader ( new InputStreamReader   ( System.in ) );
		try
		{
		  rd.readLine();
		}
		catch (Exception ex)
		{

		}
	}

}  //  @jve:decl-index=0:visual-constraint="119,26"
