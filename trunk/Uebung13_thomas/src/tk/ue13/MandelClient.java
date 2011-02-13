package tk.ue13;

import java.util.UUID;

import org.mundo.agent.Agent;
import org.mundo.agent.DoIMobility;
import org.mundo.rt.Mundo;
import org.mundo.rt.Service;
import org.mundo.service.Node;

//import tk.ue13.agent.IMandelAgent;
import tk.ue13.agent.DoIMandelAgent;

public class MandelClient implements IMandelClient
{
   final private String CLIENT_ID = "client";
   private Service client;
   private MandelGUI gui;   
   
   public MandelClient()
   {
      gui = new MandelGUI(this);
      gui.setVisible(true);
      
      // initialize mundo and register client as new service
      Mundo.init();
      client = new Service();
      Mundo.registerService(client);
   }
   
   public void calculateMandelImage(MandelConfig cfg)
   {
      //MemoryImageSource mis = new MandelbrotCalc(height, width, x1, y1, x2, y2, mi).getImage();
      //gui.updateImage(mis);
      
      Node[] nodes = Node.getNeighbors();
      for (Node n : nodes)
      {
         System.out.println(n.getName());
      }
      
      try
      {
         DoIMobility mobility = Agent.newInstance(client.getSession(), "tk.ue13.agent.MandelAgent");
         DoIMandelAgent agent = new DoIMandelAgent(mobility);
         agent.run(UUID.randomUUID().toString(), CLIENT_ID, cfg);
      }
      catch(Exception e)
      {
         System.err.println("Failure during agent activity:" + e.getMessage());
      }
   }
   
   @Override
   public void calculationFinished(MandelConfig cfg)
   {
      gui.updateImage(cfg);
   }
   
   public void exit()
   {
      try
      {
         Mundo.shutdown();
      }
      finally
      {
         System.exit(0);
      }
   }
   
   /**
    * @param args
    */
   public static void main(String[] args)
   {
      new MandelClient();
   }
}
