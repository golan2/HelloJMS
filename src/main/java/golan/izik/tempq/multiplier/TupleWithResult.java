package golan.izik.tempq.multiplier;

import java.io.Serializable;

/**
 * <pre>
 * <B>Copyright:</B>   HP Software IL
 * <B>Owner:</B>       <a href="mailto:izik.golan@hp.com">Izik Golan</a>
 * <B>Creation:</B>    10/07/2015 17:21
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class TupleWithResult extends Tuple implements Serializable {
  private final int result;

  public TupleWithResult(Tuple t) {
    super(t.a, t.b);
    this.result = t.a * t.b;
  }

  public int getResult() {
    return result;
  }

  @Override
  public String toString() {
    return "TWR{" +  super.toString() +
        " result=" + result +
        '}';
  }
}
