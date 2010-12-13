package master;
import java.awt.*;
import java.awt.event.*;
/**
import java.awt.image.*;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Iterator;
import javax.swing.border.*;
**/
import javax.swing.*;
import com.ibm.tspaces.TupleSpace;
import com.ibm.tspaces.TupleSpaceException;

public class Mandel
{

  public Mandel()
  {  
    JFrame frame=new JFrame();
    frame.addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent e)
      {
	System.exit(0);
      }
    });
    JPanel panel=new JPanel();
    panel.setLayout(new BorderLayout());
    panel.add("Center", canvas=new MandelCanvas());

    JMenuBar menubar=new JMenuBar();
    JMenu menu=new JMenu("Mandel");
    JMenuItem btn;
    menu.add(btn=new JMenuItem("Refresh"));
    btn.addActionListener(new CalcAction());
    menu.add(new JSeparator());
    menu.add(btn=new JMenuItem("Exit"));
    btn.addActionListener(new ExitAction());
    menubar.add(menu);

    frame.setJMenuBar(menubar);
    frame.setTitle("Mandel");
    frame.setSize(500, 500);
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add("Center", panel);
    frame.show();
  }
  
  class CalcAction implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      new CalcThread(canvas, -2.1, -1.25, 1.1, 1.25, 1000).start();
    }
  }
  class ExitAction implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
    	
      System.exit(0);
    }
  }
  

  void initiate(String host, int port)
  {
      System.out.println("Master/Client tries to connect to TSpace server at: " + host + ":" + port);
      try
      {
         MandelMaster master = new MandelMaster();
         master.connect(host, port);
      } catch (TupleSpaceException e)
      {
         System.err.println("Error while instantiating TupleSpace object:");
         e.printStackTrace();
      }
  }
  
  public static void main(String[] args)
  {  
      String host;
      int port;
      
      if (args.length == 1)
      {
         host = args[0];
         port = TupleSpace.DEFAULTPORT;
      }
      else if (args.length == 2)
      {
         host = args[0];
         port = Integer.parseInt(args[1]);
      }
      else
      {
         host = TupleSpace.DEFAULTHOST;
         port = TupleSpace.DEFAULTPORT;
      }
      
      System.out.println("Master/Client tries to connect to TSpace server at: " + host + ":" + port);
      try
      {
         MandelMaster master = new MandelMaster();
         master.connect(host, port);
      } catch (TupleSpaceException e)
      {
         System.err.println("Error while instantiating TupleSpace object:");
         e.printStackTrace();
      }  
    new Mandel();
  }
  private MandelCanvas canvas;
}
