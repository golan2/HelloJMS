package golan.izik.vmqueue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
* <pre>
* <B>Copyright:</B>   HP Software IL
* <B>Owner:</B>       <a href="mailto:izik.golan@hp.com">Izik Golan</a>
* <B>Creation:</B>    09/07/2015 00:03
* <B>Since:</B>       BSM 9.21
* <B>Description:</B>
*
* </pre>
*/
public class HelloWorldProducer implements Runnable {

  public static void main(String[] args) {
    new HelloWorldProducer().run();

  }

  public void run() {
    try {
      // Create a ConnectionFactory
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(App.TCP_LOCALHOST);

      // Create a Connection
      Connection connection = connectionFactory.createConnection();
      connection.start();

      // Create a Session
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

      // Create the destination (Topic or Queue)
      Destination destination = session.createQueue(App.QUEUE_NAME);

      // Create a MessageProducer from the Session to the Topic or Queue
      MessageProducer producer = session.createProducer(destination);
      producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

      int counter = 0;
      while (++counter<50000) {
        // Create a messages
        String text = "MESSAGE ["+counter+"]";
        TextMessage message = session.createTextMessage(text);

        // Tell the producer to send the message
        System.out.println("Sent message: " + message);
        producer.send(message);

        Thread.sleep(500);
      }


      // Clean up
      session.close();
      connection.close();
    }
    catch (Exception e) {
      System.out.println("Caught: " + e);
      e.printStackTrace();
    }
  }
}
