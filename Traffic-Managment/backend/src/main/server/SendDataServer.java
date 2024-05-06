package main.server;



import main.iot.IoTDevice;


import  jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import jakarta.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SendDataServer {
    private static final int DELAY = 2000;
    private static ConnectionFactory connectionFactory;
    private static Queue queue;
    private static final IoTDevice device = new IoTDevice();
    public static void sendDataToServer(IoTDevice device) {
        JMSContext jmsContext = null;
        try {
            InitialContext initialContext = new InitialContext();
            connectionFactory = (ConnectionFactory) initialContext.lookup("jms/myConnectionFactory");
            queue = (Queue) initialContext.lookup("jms/myQueue");

            jmsContext = connectionFactory.createContext();
            JMSProducer jmsProducer = jmsContext.createProducer();

            device.generateRandomValues();

            String message = "Vehicle Speed: " + device.getVehicleSpeed() +
                    "; Traffic Light Status: " + device.getTrafficLightStatus();
            System.out.println(message);
            System.out.println("Sending Message to JMS-");
            jmsProducer.send(queue, message);
            System.out.println("Message Sent Successfully");
        } catch (NamingException e) {
            System.out.println("Some Error Occurred");
        } finally {
            if (jmsContext != null) {
                jmsContext.close();
            }
        }
    }

}
