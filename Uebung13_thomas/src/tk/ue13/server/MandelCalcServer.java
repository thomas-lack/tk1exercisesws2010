package tk.ue13.server;

import java.awt.image.IndexColorModel;
import java.awt.image.MemoryImageSource;

import org.mundo.util.DefaultApplication;

import tk.ue13.MandelConfig;

public class MandelCalcServer extends DefaultApplication implements IMandelCalcServer
{
   private int width, height; 
   private int data[];
   private MemoryImageSource mis;
   private double xstart, xend, ystart, yend;
   private int maxiter;
   
   @Override
   public MandelConfig calculateMandelImage(MandelConfig config)
   {
      xstart=config.getX1(); ystart=config.getY1(); 
      xend=config.getX2(); yend=config.getY2(); 
      maxiter=config.getMi();
      //canvas=c;
      width=config.getWidth(); //canvas.getWidth();
      height=config.getHeight(); //canvas.getHeight();
      data=new int[width*height];
      mis=new MemoryImageSource(width, height, generateColorModel(),
          data, 0, width);
      
      config.setImage(getImage());
      return config;
   }
   
   private int iterate (double x, double y)
   {
     int iter=0;
     double aold, bold, zsquared,a=0,b=0,asquared=0,bsquared=0;
     //a2old=Double.MAX_VALUE, b2old=Double.MAX_VALUE,
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
   
   private MemoryImageSource getImage()
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
     //canvas.setImage(canvas.getToolkit().createImage(mis));
     return mis;
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
   
   public static void main(String args[]) 
   {
      start(new MandelCalcServer());
    }

}
