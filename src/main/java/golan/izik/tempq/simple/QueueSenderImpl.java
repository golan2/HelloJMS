package golan.izik.tempq.simple;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueSender;

/**
 * <pre>
 * <B>Copyright:</B>   HP Software IL
 * <B>Owner:</B>       <a href="mailto:izik.golan@hp.com">Izik Golan</a>
 * <B>Creation:</B>    14/07/2015 14:18
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class QueueSenderImpl implements QueueSender {
  @Override
  public Queue getQueue() throws JMSException {
    return null;
  }

  @Override
  public void setDisableMessageID(boolean value) throws JMSException {

  }

  @Override
  public boolean getDisableMessageID() throws JMSException {
    return false;
  }

  @Override
  public void setDisableMessageTimestamp(boolean value) throws JMSException {

  }

  @Override
  public boolean getDisableMessageTimestamp() throws JMSException {
    return false;
  }

  @Override
  public void setDeliveryMode(int deliveryMode) throws JMSException {

  }

  @Override
  public int getDeliveryMode() throws JMSException {
    return 0;
  }

  @Override
  public void setPriority(int defaultPriority) throws JMSException {

  }

  @Override
  public int getPriority() throws JMSException {
    return 0;
  }

  @Override
  public void setTimeToLive(long timeToLive) throws JMSException {

  }

  @Override
  public long getTimeToLive() throws JMSException {
    return 0;
  }

  @Override
  public Destination getDestination() throws JMSException {
    return null;
  }

  @Override
  public void close() throws JMSException {

  }

  @Override
  public void send(Message message) throws JMSException {
    System.out.println("SENDING... " + message.toString());
  }

  @Override
  public void send(Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {

  }

  @Override
  public void send(Destination destination, Message message) throws JMSException {

  }

  @Override
  public void send(Destination destination, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {

  }

  @Override
  public void send(Queue queue, Message message) throws JMSException {

  }

  @Override
  public void send(Queue queue, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {

  }
}
