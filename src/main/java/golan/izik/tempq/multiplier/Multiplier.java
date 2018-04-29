package golan.izik.tempq.multiplier;

import golan.izik.vmqueue.App;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.support.JmsUtils;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    09/07/2015 23:42
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class Multiplier implements Runnable {

  private boolean stopProcessingJobs = false;

  @Override
  public void run() {
    Connection connection = null;
    MessageConsumer consumer = null;
    try {
      connection = getConnection();
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      consumer = session.createConsumer(session.createQueue(Main.Q_MULTIPLY_REQ));

      int i=0;
      while (!stopProcessingJobs) {
        System.out.println("[M] Iteration ["+ ++i +"] - Waiting for a message...");
        Message message = consumer.receive(5000);

        if (message instanceof ObjectMessage) {
          //Process message
          Tuple tuple = (Tuple) (((ObjectMessage) message).getObject());
          System.out.println("[M] Message received: " + tuple);

          //Create reply & send it
          TupleWithResult reply = new TupleWithResult(tuple);
          MessageProducer reqProducer = session.createProducer(message.getJMSReplyTo());
          reqProducer.send(session.createObjectMessage(reply));
          System.out.println("[M] Reply [" + reply + "] was sent to [" + message.getJMSReplyTo() + "]");

        } else {
          System.out.println("[M] Unknown message type " + message);
        }
      }
      System.out.println("[M] Stopping" );

    } catch (JMSException e) {
      e.printStackTrace();
    }
    finally {
      JmsUtils.closeConnection(connection);
      JmsUtils.closeMessageConsumer(consumer);
    }

  }

  private Connection getConnection() throws JMSException {
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(App.TCP_LOCALHOST);
    Connection connection = connectionFactory.createConnection();
    connection.start();
    return connection;
  }

  public void stopProcessingJobs() {
    System.out.println("[M] stopProcessingJobs was called" );
    this.stopProcessingJobs = true;
  }
}
