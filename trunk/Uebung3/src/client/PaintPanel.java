package client;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;

import javax.swing.*;

public class PaintPanel extends JPanel implements MouseListener, MouseMotionListener
{
   private static final long serialVersionUID = 4217217917346748845L;
   private BufferedImage _bufImage = null;
   private WhiteboardClient client = null;
   private Point _start = null;
   private Point _end = null;
   private enum State { IDLE, DRAGGING }
   private State _state = State.IDLE;

   
   public PaintPanel(WhiteboardClient client)
   {
      this.client = client;
      this.addMouseListener(this);
      this.addMouseMotionListener(this);
   }
   
   @Override 
   public void paintComponent(Graphics g) 
   {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;
      
      //... One time initialization of in-memory, saved image.
      if (_bufImage == null) {
          // initialize buffer image with greater dimensions than the 
          // JFrame, so rescaling is not an issue anymore
          int w = 2000; 
          int h = 2000; 
          _bufImage = (BufferedImage)this.createImage(w, h);
          Graphics2D gc = _bufImage.createGraphics();
          gc.setColor(Color.WHITE);
          gc.fillRect(0, 0, w, h); // fill in background
      }
      
      //... Display the saved image.
      g2.drawImage(_bufImage, null, 0, 0);
      
      //... Overwrite the screen display with currently dragged image.
      if (_state == State.DRAGGING) {
          //... Write shape that is being dragged over the screen image,
          //    but not into the saved buffered image.  It will be written
          //    on the saved image when the mouse is released.
          drawNewLine(g2);
      }
   }
   
   private void drawNewLine(Graphics2D g2) {
      //... Draws current shape on a graphics context, either
      //    on the context passed to paintComponent, or the
      //    context for the BufferedImage.
      
      g2.setColor(client.getCurrentColor());    // Set the color.
      g2.drawLine(_start.x, _start.y, _end.x  , _end.y);
      //System.out.println("Drawing line (" + client.getCurrentColor() + ") from " + _start.x + "," + _start.y + " to " + _end.x + "," + _end.y);
   }
   
   public void receiveLine(Point start, Point end, Color color)
   {
      Graphics2D g2 = _bufImage.createGraphics();
      g2.setColor(color);
      g2.drawLine(start.x, start.y, end.x, end.y);
      this.repaint();
   }
   
   
   @Override
   public void mousePressed(MouseEvent e)
   {
      _state = State.DRAGGING;   // Assume we're starting a drag.
      
      _start = e.getPoint();     // Save start point, and also initially
      _end   = _start;           // as end point, which drag will change.
      
      System.out.println("Mouse pressed: " + _state + "/" + _start);
   }

   @Override
   public void mouseReleased(MouseEvent e) {
      //... If released at end of drag, write shape into the BufferedImage,
      //    which saves it in the drawing.
      _end = e.getPoint();      // Set end point of drag.
      if (_state == State.DRAGGING) 
      {
         _state = State.IDLE;
          
         //... Draw current shape in saved buffered image.
         //drawNewLine(_bufImage.createGraphics());
          
         //this.repaint();
         try
         {
            client.sendLine(_start, _end, client.getCurrentColor());
         } catch (RemoteException e1)
         {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
      }
   }

   @Override
   public void mouseDragged(MouseEvent e)
   {
      _state = State.DRAGGING;   // We're dragging to create a shape.
      
      _end = e.getPoint();       // Set end point of drag.  May change.
      this.repaint();            // After change, show new shape
   }
   
   // unused methods
   @Override public void mouseMoved(MouseEvent e) {}
   @Override public void mouseClicked(MouseEvent arg0) {}
   @Override public void mouseEntered(MouseEvent arg0) {}
   @Override public void mouseExited(MouseEvent arg0) {}
}
