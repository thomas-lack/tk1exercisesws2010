package tk1.ue7;

import java.sql.Time;

import com.ibm.tspaces.TupleSpaceException;
import com.ibm.tspaces.lock.LMAbortedException;
import com.ibm.tspaces.server.CheckpointException;
import com.ibm.tspaces.server.RollbackException;
import com.ibm.tspaces.server.TSServer;

public class TSpaceServer
{

   /**
    * @param args
    */
   public static void main(String[] args)
   {
      try
      {
         TSServer ts = new TSServer();
         ts.shutdown(1); // funktioniert natürlich nicht, warum sollte eine methode auch das machen wonach sie benannt ist?
         Thread serverThread = new Thread( ts, "TSServer" );
         serverThread.start();
         
      } catch (LMAbortedException e)
      {
         e.printStackTrace();
      } catch (RollbackException e)
      {
         e.printStackTrace();
      } catch (CheckpointException e)
      {
         e.printStackTrace();
      } catch (TupleSpaceException e)
      {
         e.printStackTrace();
      }
      
   }

}
