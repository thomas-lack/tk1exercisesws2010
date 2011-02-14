package tk1.ue13.server;

import java.util.Random;

import org.mundo.service.Node;
import org.mundo.util.DefaultApplication;

import tk1.ue13.api.ILoadBalancer;


public class LoadBalancer extends DefaultApplication implements ILoadBalancer {
	@Override
	public String getBestServer() {
		Node[] neighbors = Node.getNeighbors();
		Random rand = new Random(System.nanoTime());
		int index = 0;
		do {
			index = rand.nextInt(neighbors.length);
		} while (neighbors[index].getName().equals("master"));
		
		return neighbors[index].getName();
	}
	
	public static void main(String[] args) {
		start(new LoadBalancer());
	}
}
