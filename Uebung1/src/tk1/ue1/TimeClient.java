package tk1.ue1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * 
 * @author Thomas Lack
 */
public class TimeClient 
{
   private long t0,t1,t2,t3,delay,offset;
   private long bestDelay = Long.MAX_VALUE;
   private long bestOffset = Long.MAX_VALUE;
   
   /**
    * Initialize TimeClient
    */
	public TimeClient(int port)
	{
	   try
      {
         // establish new connection to the server
	      Socket client = new Socket("localhost", port);
	      client.setSoTimeout(10000);
         DataInputStream in = new DataInputStream(client.getInputStream());
         DataOutputStream out = new DataOutputStream(client.getOutputStream());
         
         for (int i=0; i < 9; i++)
         {
            //System.out.println("Thread id:" + Thread.currentThread().getId() + " Time measuring process #" + i);
            
            // memorize starting time (t0)
            out.writeLong(System.currentTimeMillis());
            
            // save client_send_time_1 (t0), server_receive_time_1 (t1) and server_send_time_2 (t2) 
            t0 = in.readLong();
            t1 = in.readLong();
            t2 = in.readLong();
                        
            // model random delay between server and client and save client_receive_time_2 (t3)
            Thread.sleep(getRandomDelay());
            t3 = System.currentTimeMillis();
            
            // calculate d_i and o_i
            calculateDelay();
            calculateOffset();
            determineEstimationQuality();
            
            // print <o_i, d_i> result pair
            System.out.println("Thread id:" + Thread.currentThread().getId() + " <offset_" + i + ", delay_" + i + "> = <" + offset + ", " + delay + ">");
            
            // wait 600ms after every measurement
            Thread.sleep(600);
         }
                  
         // print best estimation and close connection to the server
         System.out.println("Thread id:" + Thread.currentThread().getId() + " Estimation for offset o: " + bestOffset + " (ms)");
         client.close();
         
      } catch (UnknownHostException e)
      {
         e.printStackTrace();
      } catch (IOException e)
      {
         e.printStackTrace();
      } catch (InterruptedException e)
      {
         e.printStackTrace();
      }
	}
   
	/**
	 * calculate d_i
	 */
	private void calculateDelay()
	{
	   delay = t1 - t0 + t3 - t2;
	   //System.out.println("delay_i = " + t1 + " - " + t0 + " + " + t3 + " - " + t2 + " = " + delay + " (ms)");
	}
	
	/**
	 * calculate o_i
	 */
	private void calculateOffset()
	{
	   offset = (t1 - t0 + t2 - t3) / 2;
	   //System.out.println("offset_i = (" + t1 + " - " + t0 + " + " + t2 + " - " + t3 + ") / 2 = " + offset + " (ms)");
	}
	
	/**
	 * Compare current time measurement result with previous results. 
	 * Remember current result as best quality result, if appropriate.
	 */
	private void determineEstimationQuality()
	{
	   // the value of o_i that corresponds to the minimum d_i is chosen as an estimate for o
	   if (delay < bestDelay)
	   {
	      bestOffset = offset;
	      bestDelay = delay;
	      //System.out.println("Thread id:" + Thread.currentThread().getId() + " new best <offset,delay> : <" + bestOffset + ", " + bestDelay + ">" );
	   }
	}
	
	/**
    * Returns a random number between 70 and 140
    * 
    * @return a random number
    */
   private long getRandomDelay()
   {
      return 70L + (Math.abs(new Random().nextLong()) % 71L);
   }
   
   /**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		int port = -1;

		if(1 != args.length){
			System.out.println("usage: server <port>");
			System.exit(1);
		}
		
		try{
			port = Integer.parseInt(args[0]);
		}
		catch (NumberFormatException e){
			System.out.println(args[0] + " is no valid port");
			System.exit(1);
		}
		
	   new TimeClient(port);
	}
}
