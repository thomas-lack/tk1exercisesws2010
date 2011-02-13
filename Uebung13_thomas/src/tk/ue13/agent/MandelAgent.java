package tk.ue13.agent;

import org.mundo.agent.Agent;
import org.mundo.annotation.mcSerialize;
import org.mundo.rt.Mundo;
import org.mundo.service.Node;

import tk.ue13.IMandelClient;
import tk.ue13.MandelConfig;
import tk.ue13.server.ILoadBalancer;
import tk.ue13.server.IMandelCalcServer;

@mcSerialize
public class MandelAgent extends Agent implements IMandelAgent
{
   public String id, clientId;
   public MandelConfig config;
   
   @Override
   public void run(String id, String client, MandelConfig cfg)
   {
      this.id = id;
      clientId = client;
      config = cfg;
      
      System.out.println("*** "+id+" starting at "+Node.thisNode().getName());
      System.out.println("*** "+config+" config at "+Node.thisNode().getName());
      Node[] nodes = Node.getNeighbors();
      for (Node n : nodes)
      {
         System.out.println(n.getName());
      }
      
      // at first, move to the load balancer
      moveTo("LoadBalancer", "atLoadBalancer");     
   }

   @Override
   public void atCalcServer()
   {
      System.out.println("*** "+id+" now at "+Node.thisNode().getName());
      System.out.println("*** "+config+" config at "+Node.thisNode().getName());
      Node[] nodes = Node.getNeighbors();
      for (Node n : nodes)
      {
         System.out.println(n.getName());
      }
      
      //calculate Mandelbrot picture with given parameters
      IMandelCalcServer srv = (IMandelCalcServer) Mundo.getServiceByType(IMandelCalcServer.class);
      
      if (srv == null)
      {
         throw new IllegalStateException("MandelCalcServer service not found!");
      }
      
      config = srv.calculateMandelImage(config);
      System.out.println("*** leaving "+Node.thisNode().getName());
      moveTo(clientId, "atClient");
   }

   @Override
   public void atLoadBalancer()
   {
      System.out.println("*** "+id+" now at "+Node.thisNode().getName());
      System.out.println("*** "+config+" config at "+Node.thisNode().getName());
      Node[] nodes = Node.getNeighbors();
      for (Node n : nodes)
      {
         System.out.println(n.getName());
      }
      
      ILoadBalancer loadBalancer = (ILoadBalancer) Mundo.getServiceByType(ILoadBalancer.class);
      
      if (loadBalancer == null)
      {
         throw new IllegalStateException("LoadBalancer service not found!");
      }
      
      String calculatingServer = loadBalancer.getServer();
      System.out.println("*** leaving "+Node.thisNode().getName());
      moveTo(calculatingServer, "atCalcServer");
   }

   @Override
   public void atClient()
   {
      System.out.println("*** "+id+" now at "+Node.thisNode().getName());
      System.out.println("*** "+config+" config at "+Node.thisNode().getName());
      Node[] nodes = Node.getNeighbors();
      for (Node n : nodes)
      {
         System.out.println(n.getName());
      }
      
      // post calculated mandelbrot image to the client
      IMandelClient client = (IMandelClient) Mundo.getServiceByType(IMandelClient.class);
      
      if (client == null)
      {
         throw new IllegalStateException("Client service not found!");
      }
      
      client.calculationFinished(config);
   }
}
