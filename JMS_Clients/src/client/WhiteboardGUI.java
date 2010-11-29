package client;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import javax.jms.JMSException;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * TK1 Exercise 3 - graphical user interface for the client
 * (view in MVC concept)
 * 
 * @author Thomas Lack, Florian Mueller
 */
public class WhiteboardGUI extends javax.swing.JFrame implements ActionListener
{
   private static final long serialVersionUID = 1L;
   private WhiteboardClient client;
   private javax.swing.JMenu clientMenu;
   private javax.swing.JMenuBar menuBar;
   private javax.swing.JMenuItem menuItemConnect;
   private javax.swing.JMenuItem menuItemDisconnect;
   private javax.swing.JMenuItem menuItemExit;
   private PaintPanel paintPanel;
   
   
   /** Creates new form WhiteboardGUI */
   public WhiteboardGUI(WhiteboardClient client) {
      this.client = client;  
      initComponents();
   }
   
   /**
    * initialize all gui components
    */
   private void initComponents() {
       paintPanel = new PaintPanel(client); //new javax.swing.JPanel();
       menuBar = new javax.swing.JMenuBar();
       clientMenu = new javax.swing.JMenu();
       menuItemConnect = new javax.swing.JMenuItem();
       menuItemDisconnect = new javax.swing.JMenuItem();
       menuItemExit = new javax.swing.JMenuItem();
   
       this.setTitle("Client: " + client.getClientID());
       setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
      
       paintPanel.setBackground(Color.WHITE);
      
       javax.swing.GroupLayout paintPanelLayout = new javax.swing.GroupLayout(paintPanel);
       paintPanel.setLayout(paintPanelLayout);
       paintPanelLayout.setHorizontalGroup(
           paintPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addGap(0, 515, Short.MAX_VALUE)
       );
       paintPanelLayout.setVerticalGroup(
           paintPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addGap(0, 380, Short.MAX_VALUE)
       );
   
       // client menu
       clientMenu.setText("Client");
     
       menuItemConnect.setText("Connect");
       menuItemConnect.addActionListener(this);
       clientMenu.add(menuItemConnect);
     
       menuItemDisconnect.setText("Disconnect");
       menuItemDisconnect.setEnabled(false);
       menuItemDisconnect.addActionListener(this);
       clientMenu.add(menuItemDisconnect);
      
       menuItemExit.setText("Exit");
       menuItemExit.addActionListener(this);
       clientMenu.add(menuItemExit);
      
       menuBar.add(clientMenu);
       setJMenuBar(menuBar);
      
       javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
    		   getContentPane());
       
       getContentPane().setLayout(layout);
       layout.setHorizontalGroup(
             layout.createParallelGroup(
            		 javax.swing.GroupLayout.Alignment.LEADING)
             .addComponent(
            		 paintPanel, 
            		 javax.swing.GroupLayout.DEFAULT_SIZE, 
            		 javax.swing.GroupLayout.DEFAULT_SIZE, 
            		 Short.MAX_VALUE)
       );
       layout.setVerticalGroup(
             layout.createParallelGroup(
            		 javax.swing.GroupLayout.Alignment.LEADING)
             .addComponent(
            		 paintPanel, 
            		 javax.swing.GroupLayout.DEFAULT_SIZE, 
            		 javax.swing.GroupLayout.DEFAULT_SIZE, 
            		 Short.MAX_VALUE)
       );
      
       pack();
       
       addWindowListener(new WindowAdapter() {
    	   @Override
    	public void windowClosing(WindowEvent e) {   
    		if(client.isConnected())
    			try{
    			client.disconnect();
    			}
    		catch(JMSException j)
    		{
    			
    		}
    		super.windowClosing(e);
    	}
       });
   }
   
   /**
    * draws a new line on the painting panel. see also class PaintPanel. 
    * 
    * @param start
    * @param end
    * @param color
    */
   public void drawLine(Point start, Point end, Color color)
   {
      paintPanel.drawLine(start, end, color);
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
      String menuItem = ((JMenuItem) e.getSource()).getText();
      
      if (menuItem.equals("Exit")){
    	 if(client.isConnected())
			try {
				client.disconnect();
			} catch (JMSException e1) {
				
				e1.printStackTrace();
			}
    	 
         System.exit(0);
      }
      else if (menuItem.equals("Connect"))
      {
    	  try {
			client.connect();
		} catch (JMSException e1) {
		
			e1.printStackTrace();
		}
    	     	  
    	  menuItemConnect.setEnabled(false);
    	  menuItemDisconnect.setEnabled(true);
    	  paintPanel.clearCanvas();
    	     	  
    	  showColorDialog();
      }
      else if (menuItem.equals("Disconnect"))
      {
         try {
			client.disconnect();
		} catch (JMSException e1) {
			
			e1.printStackTrace();
		}
         menuItemConnect.setEnabled(true);
		 menuItemDisconnect.setEnabled(false);
      }
   }

   /**
    * Shows a dialog where the user can choose a color 
    */
   public void showColorDialog(){
	   String color = "";
	  // boolean colorAvailable = false;
	
	   HashMap<String, Color> colorMap;
	   colorMap = new HashMap<String, Color>();
	      colorMap.put("Black", Color.BLACK);
	      colorMap.put("Red", Color.RED);
	      colorMap.put("Blue", Color.BLUE);
	      colorMap.put("Green", Color.GREEN);
	      colorMap.put("Orange", Color.ORANGE);
	      colorMap.put("Magenta", Color.MAGENTA);
	      colorMap.put("Gray", Color.GRAY);
	      colorMap.put("Yellow", Color.YELLOW);
	      
	      
	   try {
		 
			   List<String> availableColors = new LinkedList<String>(colorMap.keySet());
			   
			   color = (String) JOptionPane.showInputDialog(
					   	null,
					   	"",
						"Choose a Color",
						JOptionPane.QUESTION_MESSAGE, 
						null, 
						availableColors.toArray(), 
						availableColors.get(0));
			   
			   // TODO  write selected color in client.color
			   
			   client.color = Color.BLACK;
			   
			   if(null == color)
				   throw new Exception("Colordialog canceled");
			   
			  
	   } catch (Exception e) {
		   JOptionPane.showMessageDialog(
 				  null, 
 				  "Connection failed, please try again " + 
 				  "\n\n Cause: " + e.getMessage(), 
 				  "Error", 
 				  JOptionPane.ERROR_MESSAGE);
		   
		   try {
			client.disconnect();
		} catch (JMSException e1) {
			
			e1.printStackTrace();
		}
		   menuItemConnect.setEnabled(true);
		   menuItemDisconnect.setEnabled(false);
	   }
   }
}