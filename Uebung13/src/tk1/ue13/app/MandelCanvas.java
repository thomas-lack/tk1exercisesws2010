package tk1.ue13.app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.MemoryImageSource;

import javax.swing.JComponent;


public class MandelCanvas extends JComponent
{
   private static final long serialVersionUID = -7589358158082712323L;
   private Image image = null;
   private ColorModel colorModel;
   
   public MandelCanvas() 
   {
      addComponentListener(new ComponentAdapter() {
         @Override
         public void componentResized(ComponentEvent e) {
            MandelCanvas.this.resize(getWidth(), getHeight());
            super.componentResized(e);
         }
      });
      
      colorModel = generateColorModel();
   }
   
   public void paintComponent(Graphics g)
   {
      Rectangle rect = g.getClipBounds();
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, rect.width, rect.height);
      
      if(null != image)
         g.drawImage(image, 0, 0, null);
      
      super.paintComponent(g);
   }
   
   public void addSubimage(int[] data, int x, int y, int width, int height)
   {
      Graphics2D g2 = (Graphics2D) image.getGraphics();
      
      MemoryImageSource memImage = new MemoryImageSource(
            width, height, colorModel, data, 0, width);
            
      g2.drawImage(
            getToolkit().createImage(memImage), 
            x, 
            y, 
            null);
      this.repaint();
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
   
   
   public void resize(int width, int height)
   {
      image = createImage(width, height);
      Graphics2D g2 = (Graphics2D) image.getGraphics();
      g2.setColor(Color.WHITE);
      g2.fillRect(0, 0, width, height);
   }
}
