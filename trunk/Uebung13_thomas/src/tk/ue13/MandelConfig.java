package tk.ue13;

import org.mundo.annotation.mcSerialize;

@mcSerialize
public class MandelConfig
{
   public int startX, startY; // starting pixels
   public int height, width, mi;
   public double x1, y1, x2, y2;
   public int[] imageData;
   
   public MandelConfig()
   {
   }
   
   public MandelConfig(int startX, int startY, int height, int width, double x1, double y1, double x2, double y2, int mi)
   {
      this.startX = startX;
      this.startY = startY;
      this.height = height;
      this.width = width;
      this.x1 = x1;
      this.y1 = y1;
      this.x2 = x2;
      this.y2 = y2;
      this.mi = mi;
   }
   
   public int[] getImageData()
   {
      return imageData;
   }
   
   public void setImageData(int[] data)
   {
      imageData = data;
   }
   
   public int getStartX()
   {
      return startX;
   }
   
   public int getStartY()
   {
      return startY;
   }
   
   public int getHeight()
   {
      return height;
   }
   
   public int getWidth()
   {
      return width;
   }
   
   public int getMi()
   {
      return mi;
   }
   
   public double getX1()
   {
      return x1;
   }
   
   public double getX2()
   {
      return x2;
   }
   
   public double getY1()
   {
      return y1;
   }
   
   public double getY2()
   {
      return y2;
   }
}
