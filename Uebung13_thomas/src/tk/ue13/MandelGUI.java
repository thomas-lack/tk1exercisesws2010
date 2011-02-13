package tk.ue13;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.image.MemoryImageSource;

public class MandelGUI extends javax.swing.JFrame
{
   private static final long serialVersionUID = -5805053342220396991L;
   
   private javax.swing.JMenu jMenu1;
   private javax.swing.JMenuBar jMenuBar1;
   private javax.swing.JMenuItem jMenuItem1;
   private javax.swing.JMenuItem jMenuItem3;
   private javax.swing.JPanel jPanel1;
   
   private MandelClient controller;
   private MandelbrotCanvas canvas;
   
   public MandelGUI(MandelClient controller) 
   {
      this.controller = controller; 
      
      this.addWindowListener(new WindowAdapter() 
      {
         public void windowClosing(java.awt.event.WindowEvent e)
         {
            exit();
         }
      });
      
      initComponents();
   }
   
   private void initComponents() 
   {
      jPanel1 = new javax.swing.JPanel();
      jMenuBar1 = new javax.swing.JMenuBar();
      jMenu1 = new javax.swing.JMenu();
      jMenuItem1 = new javax.swing.JMenuItem();
      jMenuItem3 = new javax.swing.JMenuItem();
      canvas = new MandelbrotCanvas();
      
      jPanel1.setLayout(new BorderLayout()); //jPanel1Layout);
      jPanel1.add("Center", canvas);//add("Center", canvas=new MandelbrotCanvas());
      
      jMenu1.setText("Mandelbrot");

      jMenuItem1.setText("Perform Calculation");
      jMenuItem1.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent arg0)
         {
            //new MandelbrotCalc(canvas, -2.1, -1.25, 1.1, 1.25, 1000).start();
            MandelConfig cfg = new MandelConfig(0, 0, 500, 500, -2.1, -1.25, 1.1, 1.25, 1000);
            controller.calculateMandelImage(cfg);
         }
      });
      jMenu1.add(jMenuItem1);

      jMenuItem3.setText("Exit");
      jMenuItem3.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            controller.exit();
         }
      });
      jMenu1.add(jMenuItem3);
      jMenuBar1.add(jMenu1);
      setJMenuBar(jMenuBar1);
      
      this.setResizable(false);
      this.setSize(500, 500);
      this.setTitle("tk.ex13 Mandelbrot calculation via Mundocore Agents");
      
      this.getContentPane().setLayout(new BorderLayout());
      this.getContentPane().add("Center", jPanel1);
      
      //pack();
   }
   
   public void updateImage(MandelConfig cfg)
   {
      //canvas.setImage(canvas.getToolkit().createImage(cfg.getImage()));
   }
   
   private void exit()
   {
      controller.exit();
   }
}
