package golan.izik.tempq.multiplier;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    10/07/2015 00:01
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * The {@link golan.izik.tempq.multiplier.Client} puts {@link golan.izik.tempq.multiplier.Tuple} on the queue ({@link #Q_MULTIPLY_REQ})
 * Part of the message ({@link javax.jms.Message#setJMSReplyTo(javax.jms.Destination)}) is also a name of a {@link javax.jms.TemporaryQueue} where it waits for the result
 * The {@link golan.izik.tempq.multiplier.Multiplier} takes the message and puts a corresponding {@link golan.izik.tempq.multiplier.TupleWithResult} on the TemporaryQueue.
 * </pre>
 */
public class Main {

  public static final String Q_MULTIPLY_REQ = "MultiplyQueue_request";

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    Multiplier multiplier = new Multiplier();
    Thread m = new Thread(multiplier);
    m.setDaemon(true);
    m.start();

    for (int i = 0; i < 10; i++) {
      executorService.submit(new Client(i));
    }

    executorService.awaitTermination(15, TimeUnit.SECONDS);
    multiplier.stopProcessingJobs();
    //Thread m = new Thread(new Multiplier());
    //m.start();
    //Thread c = new Thread(new Client());
    //c.start();
    //while (m.getState()!= Thread.State.TERMINATED || c.getState()!=Thread.State.TERMINATED) {
    //  System.out.println("m="+m.getState()+" c="+c.getState());
    //  Thread.sleep(2500);
    //}

    //System.out.println("FINISHED (m="+m.getState()+" c="+c.getState()+")");
    Thread.sleep(1000);
    System.out.println("DONE!");
  }
}
