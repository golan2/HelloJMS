package golan.izik.tempq.multiplier;

import golan.izik.vmqueue.App;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.support.JmsUtils;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import java.io.Serializable;
import java.util.Random;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    09/07/2015 23:29
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * http://archive.oreilly.com/pub/a/onjava/2007/04/10/designing-messaging-applications-with-temporary-queues.html
 *
 * </pre>
 */
public class Client implements Runnable, Serializable{

  private static final Random RANDOM = new Random(System.currentTimeMillis());
  private final Tuple tuple;
  private final int index;
  private int result;

  public Client(int index) {
    this.index = index;
    this.tuple = new Tuple( RANDOM.nextInt(100) , RANDOM.nextInt(100) );
    System.out.println("[C"+index+"] Client created "+this.tuple);
  }

  @Override
  public void run() {
    Connection connection = null;
    MessageProducer reqProducer = null;
    try {
      connection = getConnection();
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      reqProducer = session.createProducer( session.createQueue(Main.Q_MULTIPLY_REQ) );
      reqProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
      ObjectMessage message = session.createObjectMessage(this.tuple);
      TemporaryQueue tempQ = session.createTemporaryQueue();
      message.setJMSReplyTo(tempQ);
      reqProducer.send(message);
      System.out.println("[C"+index+"] Message sent ("+this.tuple+")(TempQueue:"+tempQ.getQueueName()+")");

      System.out.println("[C"+index+"] Waiting for reply on tempQ ["+tempQ.getQueueName()+"]...");
      MessageConsumer consumer = session.createConsumer(tempQ);
      Message message1 = consumer.receive(5000);
      if (message1 instanceof ObjectMessage) {
        TupleWithResult reply = (TupleWithResult) (((ObjectMessage) message1).getObject());
        System.out.println("[C"+index+"] Reply message received. Reply=["+reply.getResult()+"] Request=["+tuple+"]");
      }
      else {
        System.out.println("[C"+index+"] Unknown reply message type " + message);
      }

    } catch (JMSException e) {
      e.printStackTrace();
    }
    finally {
      JmsUtils.closeConnection(connection);
      JmsUtils.closeMessageProducer(reqProducer);
    }


  }

  private Connection getConnection() throws JMSException {
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(App.TCP_LOCALHOST);
    Connection connection = connectionFactory.createConnection();
    connection.start();
    return connection;
  }
}
