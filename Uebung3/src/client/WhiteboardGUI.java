package client;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

/**
 * TK1 Exercise 3 - graphical user interface for the client
 * (view in MVC concept)
 * 
 * @author Thomas Lack, Florian Mueller
 */
public class WhiteboardGUI extends javax.swing.JFrame implements ItemListener, ActionListener
{
   private static final long serialVersionUID = 1L;
   private WhiteboardClient client;
   private HashMap<String, Color> colorMap = new HashMap<String, Color>();
   private javax.swing.JMenu clientMenu;
   private javax.swing.JMenu colorMenu;
   private javax.swing.JMenuBar menuBar;
   private javax.swing.JMenuItem menuItemConnect;
   private javax.swing.JMenuItem menuItemDisconnect;
   private javax.swing.JMenuItem menuItemExit;
   private javax.swing.JRadioButtonMenuItem currentColorMenuItem;
   private PaintPanel paintPanel;
   
   
   /** Creates new form WhiteboardGUI */
   public WhiteboardGUI(WhiteboardClient client) {
      this.client = client;  
      initColorMap();
      initComponents();
   }

   /**
    * sets up a mapping between colors the user can choose and 
    * the internal representation of these colors
    */
   private void initColorMap()
   {
      colorMap.put("Black", Color.BLACK);
      colorMap.put("Yellow", Color.YELLOW);
      colorMap.put("Blue", Color.BLUE);
      colorMap.put("Green", Color.GREEN);
      colorMap.put("Red", Color.RED);
      colorMap.put("Orange", Color.ORANGE);
      colorMap.put("Magenta", Color.MAGENTA);
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
       colorMenu = new javax.swing.JMenu();
   
       this.setTitle("Client: " + client.getClientID());
       setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
      
       paintPanel.setBackground(Color.WHITE);
       //paintPanel.addMouseListener(this);
      
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
        
       // color menu
       colorMenu.setText("Color");
       ButtonGroup g = new ButtonGroup();
       
       for (String color : colorMap.keySet()) {
    	   JRadioButtonMenuItem colorMenuItem = new JRadioButtonMenuItem();
    	   colorMenuItem.setText(color);
    	   colorMenuItem.addItemListener(this);
    	   colorMenu.add(colorMenuItem);
    	   g.add(colorMenuItem);
       }
        
       menuBar.add(colorMenu);
   
       setJMenuBar(menuBar);
      
       javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
       getContentPane().setLayout(layout);
       layout.setHorizontalGroup(
             layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
             .addComponent(paintPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
       );
       layout.setVerticalGroup(
             layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
             .addComponent(paintPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
       );
      
       pack();
       
       addWindowListener(new WindowAdapter() {
    	   @Override
    	public void windowClosing(WindowEvent e) {   
    		if(client.isConnected())
    			client.disconnect();
    		
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
   public void itemStateChanged(ItemEvent e)
   {
      // register new color if chosen
      Object comp = e.getSource();
      String color = ((JRadioButtonMenuItem) comp).getText();
      client.setCurrentColor(colorMap.get(color));
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
      String menuItem = ((JMenuItem) e.getSource()).getText();
      
      if (menuItem.equals("Exit")){
    	 if(client.isConnected())
    		 client.disconnect();
    	 
         System.exit(0);
      }
      else if (menuItem.equals("Connect")){
    	  String address = JOptionPane.showInputDialog("Enter server address");
    	  String[] addressSegments = address.split(":");
    	  
    	  if(2 == addressSegments.length){
    		  if(client.connect(
    				  addressSegments[0], 
    				  Integer.parseInt(addressSegments[1]))){
        		  menuItemConnect.setEnabled(false);
        		  menuItemDisconnect.setEnabled(true);
        	  } else{
        		  JOptionPane.showMessageDialog(
        				  null, 
        				  "Invalid address!",
        				  "Error",
        				  JOptionPane.ERROR_MESSAGE);
        	  }
    	  }
    	  else{
    		  if(client.connect(address)){
        		  menuItemConnect.setEnabled(false);
        		  menuItemDisconnect.setEnabled(true);
        	  } else{
        		  JOptionPane.showMessageDialog(
        				  null, 
        				  "Invalid address!",
        				  "Error",
        				  JOptionPane.ERROR_MESSAGE);
        	  }
    	  }
      }
      else if (menuItem.equals("Disconnect")){
         client.disconnect();
         menuItemConnect.setEnabled(true);
		 menuItemDisconnect.setEnabled(false);
      }
   }
}