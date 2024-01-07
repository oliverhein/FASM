package de.marketsim.gui;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */


import java.util.Vector;
import com.borland.jbcl.layout.*;
import javax.swing.JFrame;

public class SimpleKurvFrame extends JFrame
{

  private BasePanel mKurvPanel  = new BasePanel();
  private BoxLayout2 boxLayout21 = new BoxLayout2();

  public SimpleKurvFrame(String pTitle, String pKurvName)
  {
    this.setTitle(pTitle);
    // Default: Integer
    try
    {
       this.mKurvPanel.setDataType( "Integer" );
    }
    catch (Exception ex)
    {

    }
    // setDrawForm( 0 ):  draw line, Default Form
    // setDrawForm( 1 ):  draw Bar

    this.mKurvPanel.setDrawForm( 0 );
    this.mKurvPanel.setYTitel(pKurvName);
    this.getContentPane().setLayout( boxLayout21 );
    this.getContentPane().add(this.mKurvPanel, null);
    this.setSize(500,300);
  }

  public void setTrennWert(int pTrennWert)
  {
     this.mKurvPanel.setDrawTrennLine( true );
     this.mKurvPanel.setYTrennwert( pTrennWert );

  }


  /**
   *
   * @param pDataType  Integer or Double
   */
  public void setDataType(String pDataType) throws Exception
  {
    this.mKurvPanel.setDataType( pDataType );
  }

  public void showChart()
  {
    this.mKurvPanel.showChart();
  }

  public void setData(Vector pData)
  {
    this.mKurvPanel.setXScaleNumber( pData.size() );
    this.mKurvPanel.setData( pData );
    this.setVisible(true);
    this.mKurvPanel.showChart();
  }
}