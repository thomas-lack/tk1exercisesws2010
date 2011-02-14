package tk.ue13;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

//import java.awt.image.IndexColorModel;
//import java.awt.image.MemoryImageSource;

public class MandelGUI extends javax.swing.JFrame
{
   private static final long serialVersionUID = -5805053342220396991L;
   
   private javax.swing.JMenu jMenu1;
   private javax.swing.JMenuBar jMenuBar1;
   private javax.swing.JMenuItem jMenuItem1;
   private javax.swing.JMenuItem jMenuItem3;
   
   private MandelClient controller;
   private MandelCanvas canvas;
   
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
      jMenuBar1 = new javax.swing.JMenuBar();
      jMenu1 = new javax.swing.JMenu();
      jMenuItem1 = new javax.swing.JMenuItem();
      jMenuItem3 = new javax.swing.JMenuItem();
      canvas = new MandelCanvas();
      
      jMenu1.setText("Mandelbrot");

      jMenuItem1.setText("Perform Calculation");
      jMenuItem1.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent arg0)
         {
            // new MandelbrotCalc(canvas, -2.1, -1.25, 1.1, 1.25, 1000).start();
            // split the 500x500 pixel panel into 4 parts and send 4 according calculation agents
            // -- we split the image allways into 4 parts, since it was not part of the exercise to
            // write a more flexible algorithm!
            int x = canvas.getWidth() / 2;
            int y = canvas.getHeight() / 2;
            
            controller.calculateMandelImage(new MandelConfig(0, y, y, x, -2.1, 0, -0.6, 1.25, 1000));
            controller.calculateMandelImage(new MandelConfig(0, 0, y, x, -2.1, -1.25, -0.6, 0, 1000));
            controller.calculateMandelImage(new MandelConfig(x, y, y, x, -0.6, 0, 1.1, 1.25, 1000));
            controller.calculateMandelImage(new MandelConfig(x, 0, y, x, -0.6, -1.25, 1.1, 0, 1000));
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
      
      this.setSize(500, 500);
      this.setTitle("tk.ex13 Mandelbrot calculation via Mundocore Agents");
      getContentPane().setLayout(new BorderLayout());
      getContentPane().add("Center", canvas);
   }
   
   public void updateImage(MandelConfig cfg)
   {
      canvas.addSubimage(cfg.getImageData(), cfg.getStartX(), cfg.getStartY(), cfg.getWidth(), cfg.getHeight());
   }
   
   private void exit()
   {
      controller.exit();
   }
}
