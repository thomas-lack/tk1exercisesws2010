package master;

import java.util.UUID;

import com.ibm.tspaces.Field;
import com.ibm.tspaces.Tuple;
import com.ibm.tspaces.TupleSpace;
import com.ibm.tspaces.TupleSpaceException;

public class MandelMaster
{
   private String masterID = UUID.randomUUID().toString();
   private TupleSpace tupleSpace;
      
   public MandelMaster() 
   {
      // nothing to do till now...
   }
   
   @SuppressWarnings("static-access")
   public void connect(String host, int port) throws TupleSpaceException
   {
      // check status of server before connecting
      Tuple active = TupleSpace.status(host,port);
      System.out.println("Master: Server-Status ? "+active);
      if ( active == null || active.getField(0).getValue().equals("NotRunning")) 
      { 
        System.err.println("TSpaces server not running on " + host + ":" + port );
        System.exit(1);
      }
      
      // connect to channel "Mandel"
      tupleSpace = new TupleSpace("Mandel", host, port);
      
      // follow TSpace server status at http://server_host_ip:8201
      // tupleSpace.setDebug(true); 
      
      // setup a template to describe what tuples the master needs
      Tuple template = new Tuple(masterID, new Field(String.class));
      
      
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
      
      try
      {
         System.out.println("Master/Client tries to connect to TSpace server at: " + host + ":" + port);
         MandelMaster master = new MandelMaster();
         master.connect(host, port);
      } catch (TupleSpaceException e)
      {
         System.err.println("Error while instantiating TupleSpace object:");
         e.printStackTrace();
      }
   }
}
