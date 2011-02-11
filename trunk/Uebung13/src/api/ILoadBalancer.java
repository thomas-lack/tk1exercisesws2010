package api;

import org.mundo.annotation.mcRemote;

@mcRemote
public interface ILoadBalancer {
	public String getBestServer();
}
