package golan.izik.vmqueue;

import org.apache.activemq.ActiveMQConnection;

/**
 * Hello world!
 */
public class App {

  static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
  static final String QUEUE_NAME = "TEST.FOO";
  static final String VM_LOCALHOST = "vm://localhost";
  public static final String TCP_LOCALHOST = "tcp://127.0.0.1:61616";

  public static void main(String[] args) throws Exception {
    thread(new HelloWorldProducer(), false);
    thread(new HelloWorldProducer(), false);
    thread(new HelloWorldConsumer(), false);
    Thread.sleep(1000);
    thread(new HelloWorldConsumer(), false);
    thread(new HelloWorldProducer(), false);
    thread(new HelloWorldConsumer(), false);
    thread(new HelloWorldProducer(), false);
    Thread.sleep(1000);
    thread(new HelloWorldConsumer(), false);
    thread(new HelloWorldProducer(), false);
    thread(new HelloWorldConsumer(), false);
    thread(new HelloWorldConsumer(), false);
    thread(new HelloWorldProducer(), false);
    thread(new HelloWorldProducer(), false);
    Thread.sleep(1000);
    thread(new HelloWorldProducer(), false);
    thread(new HelloWorldConsumer(), false);
    thread(new HelloWorldConsumer(), false);
    thread(new HelloWorldProducer(), false);
    thread(new HelloWorldConsumer(), false);
    thread(new HelloWorldProducer(), false);
    thread(new HelloWorldConsumer(), false);
    thread(new HelloWorldProducer(), false);
    thread(new HelloWorldConsumer(), false);
    thread(new HelloWorldConsumer(), false);
    thread(new HelloWorldProducer(), false);
  }

  public static void thread(Runnable runnable, boolean daemon) {
    Thread brokerThread = new Thread(runnable);
    brokerThread.setDaemon(daemon);
    brokerThread.start();
  }

}