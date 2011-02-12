package app;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MandelGUI extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1829063381947399089L;
	
	private MandelCanvas canvas;
	private JMenuBar menuBar;
	private JMenu clientMenu;
	private JMenuItem menuItemRefresh;
	private JMenuItem menuItemExit;
	private MandelApp Client;
   
   public MandelGUI(MandelApp Client)
   {
	   this.Client = Client;
	   init();
	   setVisible(true);
   }
   
   /**
    * GET AND SET FUNCTIONS FOR THE MANDEL APP THE GUI BELONGS TO
    * @return
    */
   public MandelApp getClient()
   {
	   return Client;
   }
   
   public void setClient(MandelApp Client)
   {
	   this.Client = Client;
   }
   
   /**
    * INITIALIZATION OF THE GUI ELEMENTS
    */
   public void init()
   {
		setTitle("Mandelbrot Calculator");
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	    setResizable(false); // <- no resizing, so no need for a dynamic refresh 
	    
	    canvas = new MandelCanvas();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		
	    menuBar = new JMenuBar();
		clientMenu = new JMenu();
	    menuItemRefresh = new JMenuItem();
	    menuItemExit = new JMenuItem();
	    
	    clientMenu.setText("Menu");
	    
	    menuItemRefresh.setText("Refresh");
	    menuItemRefresh.addActionListener(this);
	    clientMenu.add(menuItemRefresh);
	    menuItemExit.setText("Exit");
	    menuItemExit.addActionListener(this);
	    clientMenu.add(menuItemExit);
	    
	    
	    menuBar.add(clientMenu);
	    setJMenuBar(menuBar);
	 	
		setSize(500, 500); // <- Predefined size ( can not be changed )

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add("Center", canvas);
		
   }

   /**
    * MENU BAR ACTIONS
    */
   @Override
   public void actionPerformed(ActionEvent e) 
   {
	   String menuItem = ((JMenuItem) e.getSource()).getText();
	   
	   // Refresh
	   if (menuItem.equals("Refresh"))
	   {   
		   /**
		    * RESTART MANDEL CALCULATION
		    * AND LINK THE AGENTS 
		    */
		   
		   //Client.start();
		   //showErrorDialog("System","trying to start");
		   showErrorDialog("Error","Calculation not yet implemented!");
	   }
	   // Exit
	   else if(menuItem.equals("Exit"))
	   {
   			System.exit(0);
	   }
   }
   
   /**
    * GET FUNCTION FOR THE CANVAS LINKED TO THE GUI
    * @return
    */
   public MandelCanvas getCanvas()
   {
	   return canvas;
   }
   
   /**
    * FUNCTION TO DISPLAY A ERROR MESSAGE
    * @param title
    * @param msg
    */
   
   public void showErrorDialog(String title, String msg)
   {
      JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
   }

}
