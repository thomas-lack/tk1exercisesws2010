package server;

import java.util.Random;

import org.mundo.service.Node;
import org.mundo.util.DefaultApplication;

import api.ILoadBalancer;

public class LoadBalancer extends DefaultApplication implements ILoadBalancer {
	@Override
	public String getBestServer() {
		Node[] neighbors = Node.getNeighbors();
		Random rand = new Random(System.nanoTime());
		int index = rand.nextInt(neighbors.length);
		return neighbors[index].getName();
	}
	
	public static void main(String[] args) {
		start(new DefaultApplication());
	}
}
