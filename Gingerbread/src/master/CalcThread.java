package master;

import java.awt.image.IndexColorModel;
import java.awt.image.MemoryImageSource;

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
  
  
  void create_jobs(int x, int y, int i)
  {
	  
	 double x_tmp =  xstart+(xend-xstart)*x/(width-1);
	 double y_tmp = ystart+(yend-ystart)*y/(height-1);
	 

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
