package golan.izik.pipe.vs.queue;

import java.io.Serializable;
import java.util.Random;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    12/07/2015 18:51
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class DataObject implements Serializable {

  private static final Random RANDOM = new Random(System.currentTimeMillis());
  static final int BUFFER_SIZE = 1000;

  private final byte[] stream;

  public DataObject(int index) {
    this.stream = new byte[BUFFER_SIZE];
    byte[] indexBytes = String.format("%05d", index).getBytes();
    int i;
    for (i = 0; i < 5; i++) {
      this.stream[i] = indexBytes[i];
    }

    while (i < BUFFER_SIZE) {
      this.stream[i++] = (byte) (RANDOM.nextInt(124-34)+34);
    }
  }

  public byte[] getStream() {
    return stream;
  }
}
