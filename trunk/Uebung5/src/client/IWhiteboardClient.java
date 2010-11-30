package client;

import java.awt.Color;
import java.awt.Point;

import javax.jms.JMSException;

/**
 * TK1 Exercise 3 - client interface
 * 
 * @author Florian Müller, Thomas Lack
 */
public interface IWhiteboardClient{
   public void sendLine(Point start, Point end, Color color) throws JMSException;
	public void sendReset() throws JMSException;
   public void connect(String url, String topic) throws JMSException;
	public void disconnect() throws JMSException;
	public boolean isConnected();
}
