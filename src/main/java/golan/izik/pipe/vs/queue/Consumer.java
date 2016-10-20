package golan.izik.pipe.vs.queue;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * <pre>
 * <B>Copyright:</B>   HP Software IL
 * <B>Owner:</B>       <a href="mailto:izik.golan@hp.com">Izik Golan</a>
 * <B>Creation:</B>    12/07/2015 22:03
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class Consumer implements Runnable {


  private final int              index;
  private final PipedInputStream inputStream;
  private       boolean          finished;

  public Consumer(int index, PipedInputStream inputStream) {
    this.index = index;
    this.inputStream = inputStream;
  }

  @Override
  public void run() {
    finished = false;
    try {
      OutputStream writer = Files.newOutputStream(Paths.get("C:\\out.log"));

      byte[] bytes = new byte[500];
      boolean hasMore = true;
      while (hasMore) {


        //CONTINUE HERE
        //inputStream.

        //int read = inputStream.read(bytes);
        //writer.write(bytes);
        //hasMore = (read!=-1);

        throw new RuntimeException("UNIMPLEMENTED");


      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    finally {
      finished = true;
    }
  }

  public boolean isFinished() {
    return finished;
  }
}
