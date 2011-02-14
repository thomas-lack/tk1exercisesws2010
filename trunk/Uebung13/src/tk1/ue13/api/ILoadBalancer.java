package tk1.ue13.api;

import org.mundo.annotation.mcRemote;

@mcRemote
public interface ILoadBalancer {
	public String getBestServer();
}
