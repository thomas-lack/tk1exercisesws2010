package tk.ue13.server;

import java.util.HashMap;

import org.mundo.service.Node;
import org.mundo.util.DefaultApplication;

public class LoadBalancer extends DefaultApplication implements ILoadBalancer
{
   private final String SERVER_PREFIX = "server";
   
   private HashMap<Integer, String> server = new HashMap<Integer, String>();
   private int serverAssignmentCounter = 0;
   
   
   @Override
   public String getServer()
   {
      Node[] nodes = Node.getNeighbors();
      if (nodes.length < 1)
        throw new IllegalStateException("no neighbour nodes present");

      int serverCounter = 0;
      for (Node n : nodes)
      {
         if (n.getName().startsWith(SERVER_PREFIX))
         {
            server.put(serverCounter, n.getName());
            serverCounter++;
         }
      }
      
      if (serverCounter == 0)
         throw new IllegalStateException("no calculation servers present");
      
      serverAssignmentCounter++;
      return server.get(serverAssignmentCounter % serverCounter);
   }
   
   public static void main(String args[]) 
   {
      start(new LoadBalancer());
    }

}
