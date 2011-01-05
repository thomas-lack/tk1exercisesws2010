package tk1.ue9.server;

import java.io.IOException;

import javax.xml.ws.Endpoint;

import tk1.ue9.server.webservice.ConvertTemperatureWebservice;

public class ConvertTemperatureServer {
	public static void main(String[] args) throws IOException {
		Endpoint endpoint = Endpoint.publish(
				"http://localhost:8081/convert", 
				new ConvertTemperatureWebservice());
		
		System.out.println("Press any key to stop the server!");
		System.in.read();
		endpoint.stop();
	}
}
