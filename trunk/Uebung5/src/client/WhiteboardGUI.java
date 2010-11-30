package client;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import javax.jms.JMSException;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

/**
 * TK1 Exercise 3 - graphical user interface for the client
 * (view in MVC concept)
 * 
 * @author Thomas Lack, Florian Mueller
 */
public class WhiteboardGUI extends JFrame implements ActionListener
{
   private static final long serialVersionUID = 1L;
   private WhiteboardClient client;
   private JMenu clientMenu;
   private JMenu colorMenu;
   private JMenu optionsMenu;
   private JMenuBar menuBar;
   private JMenuItem menuItemConnect;
   private JMenuItem menuItemDisconnect;
   private JMenuItem menuItemExit;
   private PaintPanel paintPanel;
   private HashMap<String, Color> colorMap;
   private Color currentColor = Color.BLACK;
   
   /** Creates new form WhiteboardGUI */
   public WhiteboardGUI(WhiteboardClient client) 
   {
      this.client = client;  
      initColorMap();
      initComponents();
   }
   
   private void initColorMap()
   {
      colorMap = new HashMap<String, Color>();
      colorMap.put("Black", Color.BLACK);
      colorMap.put("Red", Color.RED);
      colorMap.put("Blue", Color.BLUE);
      colorMap.put("Green", Color.GREEN);
      colorMap.put("Orange", Color.ORANGE);
      colorMap.put("Magenta", Color.MAGENTA);
      colorMap.put("Gray", Color.GRAY);
      colorMap.put("Yellow", Color.YELLOW);
   }
   
   /**
    * initialize all gui components
    */
   private void initComponents() 
   {
       paintPanel = new PaintPanel(this, client);
       menuBar = new JMenuBar();
       clientMenu = new JMenu();
       colorMenu = new JMenu();
       optionsMenu = new JMenu();
       menuItemConnect = new JMenuItem();
       menuItemDisconnect = new JMenuItem();
       menuItemExit = new JMenuItem();
   
       this.setTitle("Client: " + client.getClientID());
       setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
      
       paintPanel.setBackground(Color.WHITE);
      
       // client menu
       clientMenu.setText("Client");
       
       menuItemConnect.setText("Connect");
       menuItemConnect.setEnabled(!client.isConnected());
       menuItemConnect.addActionListener(this);
       clientMenu.add(menuItemConnect);
     
       menuItemDisconnect.setText("Disconnect");
       menuItemDisconnect.setEnabled(client.isConnected());
       menuItemDisconnect.addActionListener(this);
       clientMenu.add(menuItemDisconnect);
      
       menuItemExit.setText("Exit");
       menuItemExit.addActionListener(this);
       clientMenu.add(menuItemExit);
       
       // color menu
       colorMenu.setText("Color");
       
       ButtonGroup colorGroup = new ButtonGroup();
       
       for (String color : colorMap.keySet())
       {
          JRadioButtonMenuItem colorItem = new JRadioButtonMenuItem(color);
          if (color.equals("Black"))
          {
             colorItem.setSelected(true);
          }
          colorItem.addActionListener(this);
          colorGroup.add(colorItem);
          colorMenu.add(colorItem);
       }
       
       // options menu
       optionsMenu.setText("Options");
       JMenuItem item = new JMenuItem("Clear local Whiteboard");
       item.addActionListener(this);
       optionsMenu.add(item);
       
       item = new JMenuItem("Clear all Whiteboards");
       item.addActionListener(this);
       optionsMenu.add(item);
       
       // assign menu bar
       menuBar.add(clientMenu);
       menuBar.add(colorMenu);
       menuBar.add(optionsMenu);
       setJMenuBar(menuBar);
       
       // pack layout
       GroupLayout paintPanelLayout = new GroupLayout(paintPanel);
       paintPanel.setLayout(paintPanelLayout);
       paintPanelLayout.setHorizontalGroup(
           paintPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
           .addGap(0, 515, Short.MAX_VALUE)
       );
       paintPanelLayout.setVerticalGroup(
           paintPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
           .addGap(0, 380, Short.MAX_VALUE)
       );
       
       GroupLayout layout = new GroupLayout(getContentPane());
       
       getContentPane().setLayout(layout);
       layout.setHorizontalGroup(
             layout.createParallelGroup(
            		 GroupLayout.Alignment.LEADING)
             .addComponent(
            		 paintPanel, 
            		 GroupLayout.DEFAULT_SIZE, 
            		 GroupLayout.DEFAULT_SIZE, 
            		 Short.MAX_VALUE)
       );
       layout.setVerticalGroup(
             layout.createParallelGroup(
            		 GroupLayout.Alignment.LEADING)
             .addComponent(
            		 paintPanel, 
            		 GroupLayout.DEFAULT_SIZE, 
            		 GroupLayout.DEFAULT_SIZE, 
            		 Short.MAX_VALUE)
       );
      
       pack();
       
       // call disconnect if window is closed
       addWindowListener(new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) 
          {   
             if(client.isConnected())
             {
                try
                {
                   client.disconnect();
                } catch (JMSException ex)
                {
                  ex.printStackTrace();
                }
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
      
      // exit
      if (menuItem.equals("Exit")){
         if(client.isConnected())
         {
            try
            {
               client.disconnect();
            } catch (JMSException ex)
            {
               showErrorDialog("Error", ex.getMessage());
            }
         }
    		System.exit(0);
      }
      // connect
      else if (menuItem.equals("Connect")){
       	String address = JOptionPane.showInputDialog("Enter server address (leave empty if unsure)");
       	String topic = JOptionPane.showInputDialog("Enter Channel Name (leave empty if unsure)");
       	  
       	try
         {
            client.connect(address, topic);
            paintPanel.clearCanvas();
            connectionStatusChanged();
         } catch (JMSException ex)
         {
            showErrorDialog("Error", ex.getMessage());
         } 
      }
      // disconnect
      else if (menuItem.equals("Disconnect")){
         try
         {
            client.disconnect();
            connectionStatusChanged();
         } catch (JMSException ex)
         {
            showErrorDialog("Error", ex.getMessage());
         }
      }
      // color has changed
      else if (colorMap.containsKey(menuItem))
      {
         currentColor = colorMap.get(menuItem);
      }
      // clear local whiteboard
      else if (menuItem.equals("Clear local Whiteboard"))
      {
         client.getLineData().removeAll();
      }
      // clear all whiteboards
      else if (menuItem.equals("Clear all Whiteboards"))
      {
         try
         {
            client.sendReset();
         } catch (JMSException ex)
         {
            showErrorDialog("Error", ex.getMessage());
         }
      }
   }
   
   /**
    * Create a new message dialog error window with given title and message 
    * @param title
    * @param msg
    */
   public void showErrorDialog(String title, String msg)
   {
      JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
   }
   
   /**
    * Getter for the current drawing color
    * @return Color
    */
   public Color getCurrentColor()
   {
      return currentColor;
   }
   
   /**
    * Helper method that can be called, after a connection status change has happened
    */
   public void connectionStatusChanged()
   {
      menuItemConnect.setEnabled(!client.isConnected());
      menuItemDisconnect.setEnabled(client.isConnected());
   }
}