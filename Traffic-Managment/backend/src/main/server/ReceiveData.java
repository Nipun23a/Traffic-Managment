package main.server;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/Queue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class ReceiveData implements MessageListener {



    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String iotDeviceData = textMessage.getText();
                System.out.println("Received IoT device data: " + iotDeviceData);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
        }
}

