package client;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

public class WhiteboardGUI extends javax.swing.JFrame implements ItemListener
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
   private javax.swing.JRadioButtonMenuItem menuItemBlack;
   private javax.swing.JRadioButtonMenuItem menuItemYellow;
   private javax.swing.JRadioButtonMenuItem menuItemBlue;
   private javax.swing.JRadioButtonMenuItem menuItemRed;
   private javax.swing.JRadioButtonMenuItem menuItemGreen;
   private PaintPanel paintPanel;
   
   
   /** Creates new form WhiteboardGUI */
   public WhiteboardGUI(WhiteboardClient client) {
      this.client = client;  
      initColorMap();
      initComponents();
   }

   private void initColorMap()
   {
      colorMap.put("Black", Color.BLACK);
      colorMap.put("Yellow", Color.YELLOW);
      colorMap.put("Blue", Color.BLUE);
      colorMap.put("Green", Color.GREEN);
      colorMap.put("Red", Color.RED);
   }
   
   private void initComponents() {
       paintPanel = new PaintPanel(client); //new javax.swing.JPanel();
       menuBar = new javax.swing.JMenuBar();
       clientMenu = new javax.swing.JMenu();
       menuItemConnect = new javax.swing.JMenuItem();
       menuItemDisconnect = new javax.swing.JMenuItem();
       menuItemExit = new javax.swing.JMenuItem();
       colorMenu = new javax.swing.JMenu();
       menuItemBlack = new javax.swing.JRadioButtonMenuItem();
       menuItemYellow = new javax.swing.JRadioButtonMenuItem();
       menuItemBlue = new javax.swing.JRadioButtonMenuItem();
       menuItemRed = new javax.swing.JRadioButtonMenuItem();
       menuItemGreen = new javax.swing.JRadioButtonMenuItem();
   
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
       clientMenu.add(menuItemConnect);
     
       menuItemDisconnect.setText("Disconnect");
       clientMenu.add(menuItemDisconnect);
      
       menuItemExit.setText("Exit");
       clientMenu.add(menuItemExit);
      
       menuBar.add(clientMenu);
        
       // color menu
       colorMenu.setText("Color");
      
       menuItemBlack.setText("Black");
       menuItemBlack.setSelected(true);
       menuItemBlack.addItemListener(this);
       colorMenu.add(menuItemBlack);
      
       menuItemYellow.setText("Yellow");
       menuItemYellow.addItemListener(this);
       colorMenu.add(menuItemYellow);
      
       menuItemBlue.setText("Blue");
       menuItemBlue.addItemListener(this);
       colorMenu.add(menuItemBlue);
     
       menuItemRed.setText("Red");
       menuItemRed.addItemListener(this);
       colorMenu.add(menuItemRed);
      
       menuItemGreen.setText("Green");
       menuItemGreen.addItemListener(this);
       colorMenu.add(menuItemGreen);
           
       ButtonGroup g = new ButtonGroup();
       g.add(menuItemBlack);
       g.add(menuItemYellow);
       g.add(menuItemBlue);
       g.add(menuItemRed);
       g.add(menuItemGreen);
        
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
   }
    
   public JPanel getPaintPanel()
   {
      return paintPanel;
   }
   
   @Override
   public void itemStateChanged(ItemEvent e)
   {
      System.out.println(e.getSource());
      Object comp = e.getSource();
      if (comp instanceof JRadioButtonMenuItem)
      {
         String color = ((JRadioButtonMenuItem) comp).getText();
         client.setCurrentColor(colorMap.get(color));
      }
   }
}