package tk.ue13.server;

import org.mundo.util.DefaultApplication;

import tk.ue13.MandelConfig;

public class MandelCalcServer extends DefaultApplication implements IMandelCalcServer
{
   @Override
   public MandelConfig calculateMandelImage(MandelConfig config)
   {
      if (config != null)
      {
         int[] data = calculateImageData(config);
         config.setImageData(data);
      }
      
      return config;
   }
   
   private int iterate (double x, double y, int maxiter)
   {
     int iter=0;
     double aold, bold, zsquared,a=0,b=0,asquared=0,bsquared=0;
     
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
   
   private int[] calculateImageData(MandelConfig c)
   {
      int[] data = new int[c.getWidth()*c.getHeight()];
      double xstart=c.getX1(), ystart=c.getY1(); 
      double xend=c.getX2(), yend=c.getY2(); 
      int maxiter=c.getMi();
      int width=c.getWidth(); //canvas.getWidth();
      int height=c.getHeight(); //canvas.getHeight();
      
      int x, y, i=0;
      for (y=0; y<height; y++)
      {
         for (x=0; x<width; x++)
         {
            data[i++]=iterate(xstart+(xend-xstart)*x/(width-1),
                           ystart+(yend-ystart)*y/(height-1),maxiter)%256;
         }
      }
      
      return data;
   }
   
   public static void main(String args[]) 
   {
      start(new MandelCalcServer());
    }

}
