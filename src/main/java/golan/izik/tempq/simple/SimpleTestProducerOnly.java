package golan.izik.tempq.simple;

import org.apache.wink.client.ClientConfig;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import golan.izik.vmqueue.App;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.ws.rs.core.MediaType;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    07/07/2015 13:18
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class SimpleTestProducerOnly {

  public static void main(String[] args) throws JMSException, InterruptedException {

    //Don't forget to launch ActiveMQ first:
    // C:\ActiveMQ\apache-activemq-5.11.1\bin>activemq start

    //firstTry();



    boolean cont = true;

    while (cont) {

      try {
        //QueueSenderImpl queueSender = new QueueSenderImpl();
        //ActiveMQTextMessage message = new ActiveMQTextMessage();
        //message.setText("Yuri & Izik");
        //queueSender.send(message);

        ClientConfig clientConfig = new ClientConfig();
        RestClient restClient = new RestClient(clientConfig);
        //Resource resource = restClient.resource("http://localhost:8080/rest");
        Resource resource = restClient.resource("http://myd-vm03329.hpswlabs.adapps.hp.com:8081/examples/servlets/");
        String response = resource.accept(MediaType.APPLICATION_JSON).get(String.class);
        System.out.println(response);
      } catch (Exception e) {
        System.out.println("ERR " + e.getMessage());
        e.printStackTrace();
      }

      Thread.sleep(10*1000);
    }

    System.out.println("DONE!");
  }


  private static void firstTry() throws JMSException, InterruptedException {// Create a ConnectionFactory
    Connection connection = getConnection();
    connection.start();
    connection.setExceptionListener(new MyExceptionListener());

    // Create a Session
    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    for (int i = 0; i < 5; i++) {
      // Create the destination (Topic or Queue)
      Destination destination = session.createTemporaryQueue(); //Destination destination = session.createQueue("TEST.FOO");

      MessageProducer producer = session.createProducer(destination);
      TextMessage message = session.createTextMessage("Avishag & Izik " + i);
      producer.send(message);
      Thread.sleep(10);
    }




    session.close();
    connection.close();
  }

  private static Connection getConnection()  {

    try {
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(App.TCP_LOCALHOST);

      // Create a Connection
      Connection result = connectionFactory.createConnection();
      result.start();
      return result;
    } catch (JMSException e) {
      e.printStackTrace();
      return null;
    }
  }

  private static class MyExceptionListener implements ExceptionListener {
    @Override
    public void onException(JMSException e) {
      System.out.println("ERRRRRRRRRRR -------" + e.getMessage());
    }
  }
}
