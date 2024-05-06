

import java.io.File;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import main.iot.IoTDevice;


import  jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import jakarta.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import static main.server.SendDataServer.sendDataToServer;

public class Application {
    private static final int DELAY = 2000;
    private static ConnectionFactory connectionFactory;
    private static Queue queue;

    private static final IoTDevice device = new IoTDevice();

    public static void main(String[] args) {
        try {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new DataGeneratorTask(), DELAY, DELAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class DataGeneratorTask extends TimerTask {
        @Override
        public void run() {
            device.generateRandomValues();
            sendDataToServer(device);
        }
    }



}