package client;
import java.awt.Color;
import java.awt.Point;
//import java.io.Serializable;
import java.util.UUID;
import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class WhiteboardClient implements IWhiteboardClient, MessageListener
{
	private String clientID = UUID.randomUUID().toString();
	private WhiteboardGUI gui = null;
	private enum State { CONNECTED, DISCONNECTED }
	private State state = State.DISCONNECTED;

	/**
	 * 
	 *  DATA for JMS 
	 *  TODO : visibility  
	 * 	@author Andre Ester
	 */
	
	String user = ActiveMQConnection.DEFAULT_USER;
    String password = ActiveMQConnection.DEFAULT_PASSWORD;    
    String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    String topic = "myTopic";
    Destination destination;
	Connection myConnection;
	Session session;
	MessageConsumer consumer;
	MessageProducer producer;
	Color color;
	
   	public WhiteboardClient() {
		super();
		gui = new WhiteboardGUI(this);
	}
   @Override
   	public void receiveLine(Point start, Point end, Color color)
	{
		gui.drawLine(start,end,color);
	}
	
	/**
	 * sends information about a user drawn line to the broker
	 * 
	 * @param start
	 * @param end
	 * @throws JMSException
	 */
   	public void sendLine(Point start, Point end) throws JMSException
	{
	   if (isConnected())
	   {
		   LineData line = new LineData(start, end, color);
		   Message message = session.createObjectMessage(line);
		   producer.send(message);
	   }

	}
	
   	void sendMessage(String text) throws JMSException {
		TextMessage message = session.createTextMessage(text);
		
		System.out.println("DEBUG : Client : Attempting to send message...");
		producer.send(message);
		System.out.println("DEBUG : Client : Message sent.");

	}
   	
	/**
	 * render according client gui
	 */
	public void renderGUI()
	{
      gui.setVisible(true);
	}
	
   /**
    * getter for the client ID
    * 
    * @return this client objects UUID
    */
   public String getClientID()
   {
      return clientID;
   }
   
   
   public boolean connect() throws JMSException {
	
	ActiveMQConnectionFactory myConnectionFactory = new ActiveMQConnectionFactory(user, password, url);
			
		myConnection = myConnectionFactory.createConnection();
		myConnection.start();
		System.out.println("DEBUG : Client : Connected.");
		state = State.CONNECTED;

		session = myConnection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		destination = session.createTopic(topic);
			
		consumer = session.createConsumer(destination);
		consumer.setMessageListener(this);
		producer = session.createProducer(destination);
		
		return true;

   }
   
   public boolean isConnected(){
	   if(State.CONNECTED == state){
		   return true;
	   }
	   else
	   {
		   return false;
	   }
   }
   
   public void disconnect() throws JMSException 
   {
		myConnection.close();
		state = State.DISCONNECTED;
		System.out.println("DEBUG : Client : Disconnected.");
   }
   
	/**
    * @param args the command line arguments
    */
   public static void main(String args[]) 
   {	  
		try {
			WhiteboardClient client = new WhiteboardClient();
			client.renderGUI();
		} catch (Exception e) {
			e.printStackTrace();
		}
   }

	@Override
	public void onMessage(Message message) 
	{
		try {
			if (message instanceof ObjectMessage)
			{
				LineData Line = (LineData)((ObjectMessage) message).getObject();
				gui.drawLine(Line.start, Line.end, Line.color);
				//System.out.println(" DEBUG : Client : Received line with start = "+Line.start+ " end = " +Line.end +" color = "+Line.color );	
			}
			
			else if(message instanceof TextMessage) {
				System.out.println("Received text message \"" + ((TextMessage)message).getText() + "\"");
			} else {
				System.out.println("Received non-text message \"" + message.toString() + "\"");
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
