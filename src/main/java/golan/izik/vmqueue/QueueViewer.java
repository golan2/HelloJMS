package golan.izik.vmqueue;

import golan.izik.tempq.multiplier.Main;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import java.util.Enumeration;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    09/07/2015 11:39
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class QueueViewer {

  static final String QUEUE_NAME = Main.Q_MULTIPLY_REQ;

  public static void main(String[] args) throws JMSException {
    System.out.println("QueueViewer - BEGIN");
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(App.TCP_LOCALHOST);
    Connection connection = connectionFactory.createConnection();
    connection.start();
    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      Queue queue = session.createQueue(QUEUE_NAME);
    QueueBrowser browser = session.createBrowser(queue);
    Enumeration enumeration = browser.getEnumeration();
    while (enumeration.hasMoreElements()) {
      Message message = (Message) enumeration.nextElement();
      System.out.println(message);
    }
    System.out.println("QueueViewer - END");
  }
}
