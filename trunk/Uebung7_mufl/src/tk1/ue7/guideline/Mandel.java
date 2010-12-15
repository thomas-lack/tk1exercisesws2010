package tk1.ue7.guideline;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

// ------------------------------------------------------------------

class MandelCanvas extends JComponent{
	public static final long serialVersionUID = -6167901729574706590L;
	
	private Image image;

	public synchronized void paintComponent(Graphics g){
	    Rectangle r=getBounds();
	    g.setColor(Color.white);
	    g.fillRect(r.x, r.y, r.width, r.height);
	    if (image!=null)
	      g.drawImage(image, 0, 0, null);
	}
	public synchronized void setImage(Image i)
	{
	    if(image!=null)
	    	image.flush();
	    image=i;
	    repaint();
	}
}

// ------------------------------------------------------------------

class CalcThread extends Thread
{
  public CalcThread(MandelCanvas c, double x1, double y1, double x2, double y2, int mi)
  {
    xstart=x1; ystart=y1; xend=x2; yend=y2; maxiter=mi;
    canvas=c;
    width=canvas.getWidth();
    height=canvas.getHeight();
    data=new int[width*height];
    mis=new MemoryImageSource(width, height, generateColorModel(),
        data, 0, width);
  }
  private int iterate (double x, double y)
  {
    int iter=0;
    double aold, bold, a2old=Double.MAX_VALUE,
           b2old=Double.MAX_VALUE, zsquared,a=0,b=0,asquared=0,bsquared=0;
    aold=0; bold=0;
    asquared=a*a;
    a=x;
    bsquared=b*b;
    b=y;
    zsquared=asquared+bsquared;
    for (iter=0; iter<maxiter; iter++)
    {
      a=asquared-bsquared+x;
      asquared=a*a;
      b=2*aold*b+y;
      if (bold==b && aold==a)
      {
        iter=maxiter-1;
      }
      bsquared=b*b;
      zsquared=asquared+bsquared;
      if (zsquared>4)
        break;
      bold=b; aold=a;
    }
    return iter;
  }
  public void run()
  {
    int x, y, i=0;
    for (y=0; y<height; y++)
    {
      for (x=0; x<width; x++)
      {
        data[i++]=iterate(xstart+(xend-xstart)*x/(width-1),
                          ystart+(yend-ystart)*y/(height-1))%256;
      }
    }
    canvas.setImage(canvas.getToolkit().createImage(mis));
  }
  private IndexColorModel generateColorModel()
  {
    byte[] r = new byte[255];
    byte[] g = new byte[255];
    byte[] b = new byte[255];
    int iter;
    for (iter=0;iter<255; iter++)
    {
      r[iter]=(byte)((iter*26)%250);
      g[iter]=(byte)((iter*2)%250);
      b[iter]=(byte)((iter*35)%250);
    }
    return new IndexColorModel(8,255,r,g,b);
  }
  private int width, height; 
  private int data[];
  private MemoryImageSource mis;
  private MandelCanvas canvas;
  private double xstart, xend, ystart, yend;
  private int maxiter;
}

// ------------------------------------------------------------------

public class Mandel
{
  public Mandel()
  {
    JFrame frame=new JFrame();
    frame.addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent e)
      {
	System.exit(0);
      }
    });
    JPanel panel=new JPanel();
    panel.setLayout(new BorderLayout());
    panel.add("Center", canvas=new MandelCanvas());

    JMenuBar menubar=new JMenuBar();
    JMenu menu=new JMenu("Mandel");
    JMenuItem btn;
    menu.add(btn=new JMenuItem("Refresh"));
    btn.addActionListener(new CalcAction());
    menu.add(new JSeparator());
    menu.add(btn=new JMenuItem("Exit"));
    btn.addActionListener(new ExitAction());
    menubar.add(menu);

    frame.setJMenuBar(menubar);
    frame.setTitle("Mandel");
    frame.setSize(500, 500);
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add("Center", panel);
    frame.show();
  }
  class CalcAction implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      //new CalcThread(canvas, -2.1, -1.25, 1.1, 1.25, 1000).start();
       new CalcThread(canvas, -2.1, -1.25, -0.6, 0.0, 1000).start();
    }
  }
  class ExitAction implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      System.exit(0);
    }
  }
  public static void main(String[] args)
  {
    new Mandel();
  }
  private MandelCanvas canvas;
}

// ------------------------------------------------------------------
