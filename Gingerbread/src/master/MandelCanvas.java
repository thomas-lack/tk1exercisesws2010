package master;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JComponent;

class MandelCanvas extends JComponent
{
  public synchronized void paintComponent(Graphics g)
  {
    Rectangle r=getBounds();
    g.setColor(Color.white);
    g.fillRect(r.x, r.y, r.width, r.height);
    if (image!=null)
      g.drawImage(image, 0, 0, null);
  }
  public synchronized void setImage(Image i)
  {
    if (image!=null)
      image.flush();
    image=i;
    repaint();
    
  }
  private Image image;
}