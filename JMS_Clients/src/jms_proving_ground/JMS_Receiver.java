package jms_proving_ground;
import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JMS_Receiver implements MessageListener{
	String user = ActiveMQConnection.DEFAULT_USER;
    String password = ActiveMQConnection.DEFAULT_PASSWORD;    
    String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    String topic = "myTopic";
    Destination destination;
	Connection myConnection;
	Session session;
	MessageConsumer consumer;
	MessageProducer producer;
	
	
	Object signal = null;
	int counter = 0;
	
	public JMS_Receiver()
	{
		signal = new Object();
	}
	
    public static void main(String[] args)
    {
		System.out.println("DEBUG : Sender : Starting");
		JMS_Receiver my_JMS = new JMS_Receiver();
		
		try 
		{
			my_JMS.connect();
			my_JMS.listen();
			my_JMS.disconnect();
		} 
		catch (JMSException e) 
		{
			System.out.println("DEBUG : Sender : ERROR in main()");
			//e.printStackTrace();
		}
	}
	
	void connect() throws JMSException
	{
		ActiveMQConnectionFactory myConnectionFactory = new ActiveMQConnectionFactory(user, password, url);
		
		myConnection = myConnectionFactory.createConnection();
		myConnection.start();
		System.out.println("DEBUG : Sender : Connected.");

		session = myConnection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		destination = session.createTopic(topic);
		
		consumer = session.createConsumer(destination);
		consumer.setMessageListener(this);
		
		producer = session.createProducer(destination);
	}
	
	void disconnect() throws JMSException
	{
		myConnection.close();
		System.out.println("DEBUG : Sender: Disconnected.");
		
	}
	
	void sendMessage(String text) throws JMSException {
		TextMessage message = session.createTextMessage(text);
		
		System.out.println("DEBUG : Sender : Attempting to send message...");
		producer.send(message);
		System.out.println("DEBUG : Sender : Message sent.");

	}
	
	void listen()
	{
		synchronized(signal) {
			if (this.counter < 4) {
				try {
					this.signal.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				System.out.println("Received text message \"" + ((TextMessage)message).getText() + "\"");
			} else {
				System.out.println("Received non-text message \"" + message.toString() + "\"");
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
		// Count the message
		counter++;

		// Notify the application it has received 100 messages...
		if (counter == 4) {
			synchronized(this.signal) {
				this.signal.notify();
			}
		}
		
	}

}
