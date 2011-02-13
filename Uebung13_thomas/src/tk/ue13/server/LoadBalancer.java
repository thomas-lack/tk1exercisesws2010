package tk.ue13.server;

import org.mundo.util.DefaultApplication;

public class LoadBalancer extends DefaultApplication implements ILoadBalancer
{
   
   @Override
   public String getServer()
   {
      return "server1";
   }
   
   public static void main(String args[]) 
   {
      start(new LoadBalancer());
    }

}
