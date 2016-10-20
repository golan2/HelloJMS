package golan.izik.pipe.vs.queue;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * <pre>
 * <B>Copyright:</B>   HP Software IL
 * <B>Owner:</B>       <a href="mailto:izik.golan@hp.com">Izik Golan</a>
 * <B>Creation:</B>    12/07/2015 18:56
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class Producer implements Runnable {


  public  static final byte[] NEWLINE     = new byte[]{13, 10};
  private static final int    ITEMS_COUNT = 100;

  private final PipedInputStream inputStream;
  private final int              index;
  private       boolean          finished;

  public Producer(int index, PipedInputStream inputStream) {
    this.inputStream = inputStream;
    this.index = index;
  }

  @Override
  public void run() {
    finished = false;
    OutputStream outputStream = null;
    try {
      outputStream = new PipedOutputStream(inputStream);

      for (int i = 0; i < ITEMS_COUNT; i++) {
        outputStream.write(index);
        outputStream.write(new DataObject(i).getStream());
        outputStream.write(NEWLINE);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    finally {
      finished = true;
      try {
        if (outputStream!=null) outputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public boolean isFinished() {
    return finished;
  }
}
