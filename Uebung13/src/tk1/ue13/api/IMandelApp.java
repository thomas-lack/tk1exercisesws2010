package tk1.ue13.api;

import org.mundo.annotation.mcRemote;

import tk1.ue13.agent.MandelConfig;


@mcRemote
public interface IMandelApp {
	void setMandelImage(MandelConfig config, int[] data);
}
