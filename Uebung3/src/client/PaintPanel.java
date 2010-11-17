package client;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;

import javax.swing.*;

/**
 * TK1 Exercise 3 - extended JPanel with mouse listening and drawing capabilities
 * (part of the view in MVC concept)
 * 
 * @author Thomas Lack, Florian Mueller
 */
public class PaintPanel extends JPanel implements MouseListener, MouseMotionListener
{
   private static final long serialVersionUID = 4217217917346748845L;
   private BufferedImage buffer = null;
   private WhiteboardClient client = null;
   private Point startPoint = null;
   private Point endPoint = null;
   private enum State { IDLE, DRAGGING }
   private State _state = State.IDLE;
   private BasicStroke strokeAttribute = null;
   private BasicStroke clearStrokeAttribute = null;

   /**
    * constructor
    * 
    * @param client
    */
   public PaintPanel(WhiteboardClient client)
   {
      this.client = client;
      this.addMouseListener(this);
      this.addMouseMotionListener(this);
      
      strokeAttribute = new BasicStroke(
    		  3.0f, 
    		  BasicStroke.CAP_ROUND, 
    		  BasicStroke.JOIN_ROUND);
      
      clearStrokeAttribute = new BasicStroke(
    		  8.0f, 
    		  BasicStroke.CAP_ROUND, 
    		  BasicStroke.JOIN_ROUND);
   }
   
   @Override 
   public void paintComponent(Graphics g) 
   {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;
      
      
      // create buffer image we are going to paint on later
      if (buffer == null) {
          // initialize buffer image with greater dimensions than the 
          // JFrame, so rescaling is not an issue anymore
          int w = 2000; 
          int h = 2000; 
          buffer = (BufferedImage)this.createImage(w, h);
          clearCanvas();
      }
      
      //display current buffered image
      g2.drawImage(buffer, null, 0, 0);
   }
   
   /**
    * draw a new line between given coordinates onto the buffer image
    * 
    * @param start
    * @param end
    * @param color
    */
   public void drawLine(Point start, Point end, Color color)
   {
      // get current buffer image as graphic object...
      Graphics2D g2 = buffer.createGraphics();
      
      if(color.equals(Color.WHITE))
    	  g2.setStroke(clearStrokeAttribute);
      else
    	  g2.setStroke(strokeAttribute);
      
      // ... so the new line can be added
      g2.setColor(color);
      g2.drawLine(start.x, start.y, end.x, end.y);
      
      // create visual feedback for the user
      this.repaint();
   }
   
   public void clearCanvas(){
	   Graphics2D g2 = buffer.createGraphics();
	   g2.setColor(Color.WHITE);
	   g2.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
	   invalidate();
   }
   
   
   @Override
   public void mousePressed(MouseEvent e)
   {
	  if(MouseEvent.BUTTON1 == e.getButton()){
	   
	      // set the current state to mouse dragging / painting
	      _state = State.DRAGGING;
	      
	      
	      startPoint = e.getPoint();
	  }
   }
   
   @Override
   public void mouseDragged(MouseEvent e)
   {
	   if(State.DRAGGING == _state){
		   try{
		         client.sendLine(startPoint, e.getPoint());
		   } catch (RemoteException e1){
		         e1.printStackTrace();
		   }
		   
		   startPoint = e.getPoint();
	   }
   }
   
   @Override
   public void mouseReleased(MouseEvent e) {
      // change the current state back to idle / not painting if the mouse button 
      // is released
      if (_state == State.DRAGGING) {
    	  try{
		         client.sendLine(startPoint, e.getPoint());
		  } catch (RemoteException e1){
		         e1.printStackTrace();
		  }
         _state = State.IDLE;
      }
   }
   
   // unused methods
   @Override public void mouseMoved(MouseEvent e) {}
   @Override public void mouseClicked(MouseEvent arg0) {}
   @Override public void mouseEntered(MouseEvent arg0) {}
   @Override public void mouseExited(MouseEvent arg0) {}
}
