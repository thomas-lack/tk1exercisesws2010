package tk.ue13;

import java.util.UUID;

import org.mundo.agent.Agent;
import org.mundo.agent.DoIMobility;
import org.mundo.rt.Mundo;
import org.mundo.rt.Service;

//import tk.ue13.agent.IMandelAgent;
import tk.ue13.agent.DoIMandelAgent;

public class MandelClient extends Service implements IMandelClient
{
   final private String CLIENT_ID = "clienthorst";
   private MandelGUI gui;   
   
   public MandelClient()
   {
      gui = new MandelGUI(this);
      gui.setVisible(true);
      
      // initialize mundo and register client as new service
      Mundo.init();
      Mundo.registerService(this);
   }
   
   public void calculateMandelImage(MandelConfig cfg)
   {
      try
      {
         DoIMobility mobility = Agent.newInstance(this.getSession(), "tk.ue13.agent.MandelAgent");
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
