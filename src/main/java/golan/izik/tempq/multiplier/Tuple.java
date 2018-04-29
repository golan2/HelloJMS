package golan.izik.tempq.multiplier;

import java.io.Serializable;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    10/07/2015 00:07
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class Tuple implements Serializable{
  final int a;
  final int b;

  public Tuple(int a, int b) {
    this.a = a;
    this.b = b;
  }

  @Override
  public String toString() {
    return "T{" +
        "a=" + a +
        ", b=" + b +
        '}';
  }
}
