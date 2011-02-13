package tk.ue13.server;

import org.mundo.annotation.mcRemote;

import tk.ue13.MandelConfig;

@mcRemote
public interface IMandelCalcServer
{
   public MandelConfig calculateMandelImage(MandelConfig config);
}
