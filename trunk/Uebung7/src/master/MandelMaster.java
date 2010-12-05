package master;

import java.util.UUID;
import java.util.Vector;

import com.ibm.tspaces.Callback;
import com.ibm.tspaces.Field;
import com.ibm.tspaces.SuperTuple;
import com.ibm.tspaces.Tuple;
import com.ibm.tspaces.TupleSpace;
import com.ibm.tspaces.TupleSpaceException;

public class MandelMaster implements Callback
{
   //private String masterID = UUID.randomUUID().toString();
   private String masterID = "TEST";
   private TupleSpace tupleSpace;
   private Vector<Tuple> queue = new Vector<Tuple>();
   private boolean quit = false;
      
   @SuppressWarnings("static-access")
   public void connect(String host, int port) throws TupleSpaceException
   {
      // check status of server before connecting
      Tuple active = TupleSpace.status(host,port);
      System.out.println("Master: Server-Status ? " + active);
      if ( active == null || active.getField(0).getValue().equals("NotRunning")) 
      { 
        System.err.println("TSpaces server not running on " + host + ":" + port );
        System.exit(1);
      }
      
      // connect to channel "Mandel"
      tupleSpace = new TupleSpace("Mandel", host, port);
      
      // follow TSpace server status at http://server_host_ip:8201
      tupleSpace.setDebug(true); 
      
      // setup a template to describe what tuples the master needs
      Tuple template = new Tuple(new Field(masterID), new Field(String.class));
      
      // register as processor, if a tuple that matches the template
      // was added to the TupelSpace via WRITE command
      tupleSpace.eventRegister(TupleSpace.WRITE, template, this, false);
      
      // start the data processing loop
      //loop();
   }
   
   @Override
   public boolean call(String eventName, String tsName, int seqNum, SuperTuple tuple, boolean isException)
   {
      System.out.println("TupelSpace WRITE registered! Message: " + tuple);
      
      // add the data tuple to the processing queue
      // because further operations on the TupleSpace are not allowed during this call method
      queue.add((Tuple) tuple);
      
      return false;
   }
   
   private void loop()
   {
      Tuple tuple = null;
      String data;
      
      while (!quit)
      {
         if (queue.size() > 0)
         {
            // get first tuple element from the queue and remove it
            tuple = queue.get(0);
            queue.remove(0);
            
            System.out.println("Master: processing ... " + tuple);
            data = null;
            
            try
            {
               data = (String) tuple.getField(1).getValue();
               System.out.println("Master: data: " + data);
               //tupleSpace.takeTupleById(tuple.getTupleID());
            } catch (TupleSpaceException e)
            {
               e.printStackTrace();
            }

         }
      }
   }
   
   /**
    * @param args
    */
   public static void main(String[] args)
   {
      String host;
      int port;
      
      if (args.length == 1)
      {
         host = args[0];
         port = TupleSpace.DEFAULTPORT;
      }
      else if (args.length == 2)
      {
         host = args[0];
         port = Integer.parseInt(args[1]);
      }
      else
      {
         host = TupleSpace.DEFAULTHOST;
         port = TupleSpace.DEFAULTPORT;
      }
      
      System.out.println("Master/Client tries to connect to TSpace server at: " + host + ":" + port);
      
      try
      {
         MandelMaster master = new MandelMaster();
         master.connect(host, port);
      } catch (TupleSpaceException e)
      {
         System.err.println("Error while instantiating TupleSpace object:");
         e.printStackTrace();
      }
   }
}
