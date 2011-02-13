package tk.ue13;

import org.mundo.annotation.mcRemote;

@mcRemote
public interface IMandelClient
{
   public void calculationFinished(MandelConfig cfg);
}
