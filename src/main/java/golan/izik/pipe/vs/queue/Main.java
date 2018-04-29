package golan.izik.pipe.vs.queue;

import java.io.PipedInputStream;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    12/07/2015 22:19
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class Main {

  static final int PIPE_SIZE = 4096;

  public static void main(String[] args) throws InterruptedException {

    PipedInputStream inputStream = new PipedInputStream(PIPE_SIZE);
    Producer producer = new Producer(1, inputStream);
    Consumer consumer = new Consumer(1, inputStream);
    Thread tc = new Thread(consumer);
    Thread tp = new Thread(producer);
    tc.setDaemon(true);
    tp.setDaemon(true);

    tc.start();
    tp.start();

    while (!producer.isFinished() || !consumer.isFinished()) {
      Thread.sleep(1000);
      System.out.println("Waiting...");
    }

  }
}
