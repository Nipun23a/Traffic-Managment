package main.server;

import jakarta.jms.*;
import main.server.AnalyticalServer;

import javax.naming.*;

public class MessageReceiver {
    private static final String CONNECTION_FACTORY_NAME = "jms/myConnectionFactory";
    private static final String QUEUE_NAME = "jms/myQueue";
    public static void main(String[] args) {
        try {
            InitialContext initialContext = new InitialContext();
            ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup(CONNECTION_FACTORY_NAME);
            Queue queue = (Queue) initialContext.lookup(QUEUE_NAME);

            // Create a JMS context and consumer
            try (JMSContext jmsContext = connectionFactory.createContext()) {
                JMSConsumer jmsConsumer = jmsContext.createConsumer(queue);

                System.out.println("Waiting for messages...");

                // Continuously receive messages
                while (true) {
                    // Receive the message
                    String message = jmsConsumer.receiveBody(String.class);

                    // Pass the received message to the main.server.AnalyticalServer
                    AnalyticalServer.processMessage(message);
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }


}
