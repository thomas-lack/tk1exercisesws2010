package client;

import java.awt.Color;
import java.awt.Point;
import java.rmi.RemoteException;
import java.util.UUID;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * TK1 Exercise 5 - Implementation of whiteboard client
 * controller in MVC concept
 * 
 * @author Thomas Lack, Florian Mueller, Andre Ester
 */
public class WhiteboardClient implements IWhiteboardClient, MessageListener
{
	private static final long serialVersionUID = -227030971339158140L;
	private String clientID = UUID.randomUUID().toString();
	private WhiteboardGUI gui = null;
	private enum State { CONNECTED, DISCONNECTED }
	private State state = State.DISCONNECTED;
	private Connection connection;
	private MessageProducer producer = null;
	private Session session = null;
	private LineDataListModel lineData = null;
	
	/**
	 * Constructor
	 * @throws RemoteException
	 */
	public WhiteboardClient()
	{
	   super();
	   lineData = new LineDataListModel();
	   gui = new WhiteboardGUI(this);
	}
   
	@Override
   public void onMessage(Message message)
   {
	   try 
	   {
	      if (message instanceof ObjectMessage)
         {
            // add received line object to the line data model instance
	         LineData line = (LineData)((ObjectMessage) message).getObject();
            lineData.addElement(line);
         }
         
         else if(message instanceof TextMessage) 
         {
            if (((TextMessage) message).getText().equals("reset"))
            {
               lineData.removeAll();
            }
         } 
      } catch (JMSException e) 
      {
         gui.showErrorDialog("Error", e.getMessage());
      }
   }
	
	/**
	 * sends information about a user drawn line to the server
	 * 
	 * @param start
	 * @param end
	 * @throws JMSException 
	 */
	public void sendLine(Point start, Point end, Color color) throws JMSException
	{
      if (isConnected())
      {
         LineData line = new LineData(start, end, color);
         Message message = session.createObjectMessage(line);
         producer.send(message);
      }
	}
	
	/**
	 * reset the whiteboard on all clients
	 */
	public void sendReset() throws JMSException
	{
	   if (isConnected())
	   {
	      Message message = session.createTextMessage("reset");
	      producer.send(message);
	   }
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
    * @return current clients UUID
    */
   public String getClientID()
   {
      return clientID;
   }
   
   /**
    * getter for the line data model
    * @return current clients line data model 
    */
   public LineDataListModel getLineData()
   {
      return lineData;
   }
   
   /**
    * Connect current whiteboard client to jms server
    * @param url
    * @param topic
    * @return
    * @throws JMSException 
    */
   public void connect(String url, String topic) throws JMSException
   {
      // set standard url if none is set:
      // failover://tcp://localhost:61616
      if (url == null || url.equals(""))
      {
         url = ActiveMQConnection.DEFAULT_BROKER_URL;
      }
      
      // set standard topic if none is set:
      if (topic == null || topic.equals(""))
      {
         topic = "Whiteboard";
      }
      
      ActiveMQConnectionFactory conFactory = new ActiveMQConnectionFactory(clientID, ActiveMQConnection.DEFAULT_PASSWORD, url);
      
      // try connecting to the jms server
      connection = conFactory.createConnection();
      connection.start();
      state = State.CONNECTED;

      session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
      Destination destination = session.createTopic(topic);
      MessageConsumer consumer = session.createConsumer(destination);
      consumer.setMessageListener(this);
      producer = session.createProducer(destination);
      
      System.out.println("Client " + getClientID() + ": connect");
      
      // invoke button appearance change at the gui
      gui.connectionStatusChanged();
   }
   
   /**
    * return the current connection status
    */
   public boolean isConnected()
   {
	   return State.CONNECTED == state;
   }
   
   /**
    * disconnect current whiteboard client from jms server
    */
   public void disconnect() throws JMSException
   {
      session.close();
      connection.close();
      state = State.DISCONNECTED;
      System.out.println("Client " + getClientID() + ": disconnect");
   }
   
	/**
    * @param args the command line arguments, 
    *    args[0] = jms server, 
    *    args[1] = topic/channel to join
    */
   public static void main(String[] args) 
   {	  
		WhiteboardClient client = new WhiteboardClient();
		
      if (args.length == 2)
      {
         try
         {
            client.connect(args[0], args[1]);
         } catch (JMSException e)
         {
            System.out.println(e.getMessage());
         }
      }
      else
      {
         System.err.println("Client: missing arguments (server adress and/or channel). Please connect manually via the gui!");
      }
      client.renderGUI();
   }
}
