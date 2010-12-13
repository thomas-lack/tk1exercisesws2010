package worker;

import java.util.UUID;

import com.ibm.tspaces.Field;
import com.ibm.tspaces.Tuple;
import com.ibm.tspaces.TupleSpace;
import com.ibm.tspaces.TupleSpaceException;

public class MandelWorker
{
   private String workerID = UUID.randomUUID().toString();
   private enum State {RUNNING, IDLE};
   private State state = State.IDLE;
   private TupleSpace tupleSpace;
   private Tuple template; // search pattern for polling new work tuples

   
   public void connect(String host, int port) throws TupleSpaceException
   {
      // check status of server before connecting
      Tuple active = TupleSpace.status(host,port);
      System.out.println("Worker: Server-Status ? " + active);
      if ( active == null || active.getField(0).getValue().equals("NotRunning")) 
      { 
        System.err.println("TSpaces server not running on " + host + ":" + port );
        System.exit(1);
      }
      
      // connect to channel "Mandel"
      tupleSpace = new TupleSpace("Mandel", host, port);
      
      // setup a template to describe what tuples the worker needs
      template = new Tuple(new Field(String.class), new Field(String.class));
   }
   
   public void test()
   {
      try
      {
         tupleSpace.write("TEST", "data1");
         //Thread.sleep(5000);
         tupleSpace.write("TEST", "data2");
         //Thread.sleep(5000);
         tupleSpace.write("TEST", "data3");
         //Thread.sleep(5000);
      } catch (TupleSpaceException e)
      {
         e.printStackTrace();
      } /*catch (InterruptedException e)
      {
         e.printStackTrace();
      }*/
   }
   
   private int iterate (double x, double y)
   {
	 int maxiter = 1000;
     int iter=0;
     double aold, bold, a2old=Double.MAX_VALUE,
            b2old=Double.MAX_VALUE, zsquared,a=0,b=0,asquared=0,bsquared=0;
     aold=0; bold=0;
     asquared=a*a;
     a=x;
     bsquared=b*b;
     b=y;
     zsquared=asquared+bsquared;
     for (iter=0; iter<maxiter; iter++)
     {
       a=asquared-bsquared+x;
       asquared=a*a;
       b=2*aold*b+y;
       if (bold==b && aold==a)
       {
         iter=maxiter-1;
       }
       bsquared=b*b;
       zsquared=asquared+bsquared;
       if (zsquared>4)
         break;
       bold=b; aold=a;
     }
     return iter;
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
         System.out.println("Worker tries to connect to TSpace server at: " + host + ":" + port);
         MandelWorker worker = new MandelWorker();
         worker.connect(host, port);
         worker.test();
      } catch (TupleSpaceException e)
      {
         System.err.println("Error while instantiating TupleSpace object:");
         e.printStackTrace();
      }
   }

}
