package golan.izik.vmqueue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
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
public class HelloWorldConsumer implements Runnable, ExceptionListener {

  public static void main(String[] args) {
    new HelloWorldConsumer().run();
  }

  public void run() {
    try {

      // Create a ConnectionFactory
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(App.TCP_LOCALHOST);

      // Create a Connection
      Connection connection = connectionFactory.createConnection();
      connection.start();

      connection.setExceptionListener(this);

      // Create a Session
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

      // Create the destination (Topic or Queue)
      Queue queue = session.createQueue(App.QUEUE_NAME);

      System.out.println("Queue was created: " + queue);

      // Create a MessageConsumer from the Session to the Topic or Queue
      MessageConsumer consumer = session.createConsumer(queue);

      int counter = 0;
      while (++counter<25) {
        System.out.println("Wait for a message...");
        Message message = consumer.receive(100);
        if (message instanceof TextMessage) {
          TextMessage textMessage = (TextMessage) message;
          String text = textMessage.getText();
          System.out.println("Received: " + text);
        } else {
          System.out.println("Received: " + message);
        }
      }

      System.out.println("Closing and shutting down...");


      consumer.close();
      session.close();
      connection.close();
    } catch (Exception e) {
      System.out.println("Caught: " + e);
      e.printStackTrace();
    }
  }

  public synchronized void onException(JMSException ex) {
    System.out.println("JMS Exception occured. Shutting down client.");
  }
}
