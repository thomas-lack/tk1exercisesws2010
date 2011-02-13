package tk.ue13.agent;

import org.mundo.annotation.mcRemote;

import tk.ue13.MandelConfig;

@mcRemote
public interface IMandelAgent
{
   public void run(String id, String client, MandelConfig cfg);
   public void atLoadBalancer();
   public void atCalcServer();
   public void atClient();
}
