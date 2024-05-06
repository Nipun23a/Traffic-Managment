package main.server;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import jakarta.jms.Queue;
import main.iot.IoTDevice;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;


public class AnalyticalServer {

    private static final  List<IoTDevice> processedDevices = new ArrayList<>();
    private static final List<Double> trafficFlow = new ArrayList<>();

    public static void processMessage(String message) {
        try {
            // Split the message to extract individual data
            String[] parts = message.split("; ");

            if (parts.length == 2) {
                // Extract data
                double vehicleSpeed = Double.parseDouble(parts[0].split(": ")[1]);
                boolean lightStatus = Boolean.parseBoolean(parts[1].split(": ")[1]);

                // Add the data to the respective lists
                IoTDevice device = new IoTDevice(vehicleSpeed,lightStatus);
                processedDevices.add(device);
                trafficFlow.add(calculateTrafficFlow(vehicleSpeed, lightStatus));

                // Analyze the data
                analyzeData(device);
            } else {
                System.out.println("Invalid message format: " + message);
            }
        } catch (Exception e) {
            System.out.println("Error processing message: " + message);
            e.printStackTrace();
        }
    }
    private static double calculateAverageSpeed() {
        double sum =0 ;
        for (IoTDevice device : processedDevices){
            sum += device.getVehicleSpeed();
        }
        return sum/processedDevices.size();
    }

    private static void analyzeData(IoTDevice device){
            double averageSpeed=calculateAverageSpeed();
            double averageTrafficFlow = calculateAverageTrafficFlow(device);
            double efficiency = calculateUrbanMobilityEfficiency(averageSpeed,averageTrafficFlow);
            sendAnalyzedData(averageSpeed,averageTrafficFlow,efficiency);
    }
    private static double calculateAverageTrafficFlow(IoTDevice device) {

        int count = 0;
        double sum = 0;

        // Iterate over processed devices to find traffic flows for the given GPRS coordinate
        for (IoTDevice processedDevice : processedDevices) {
                sum += calculateTrafficFlow(processedDevice.getVehicleSpeed(), processedDevice.getTrafficLightStatus());
                count++;
        }
        if (count > 0) {
            return sum / count;
        } else {
            return 0;
        }
    }


    private static double calculateTrafficFlow(double vehicleSpeed, boolean trafficLightStatus) {
        double flow;
        if (trafficLightStatus) {
            flow = vehicleSpeed * 0.8;
        } else {
            flow = vehicleSpeed * 0.5;
        }
        return flow;
    }


    private static double calculateUrbanMobilityEfficiency(double averageSpeed, double averageTrafficFlow) {
        double speedWeight = 0.6;
        double flowWeight = 0.4;
        double efficiency = (speedWeight * averageSpeed) + (flowWeight * averageTrafficFlow);
        return efficiency;
    }



    public static void sendAnalyzedData(double averageSpeed, double averageTrafficFlow, double urbanMobilityEfficiency) {
        JMSContext jmsContext = null;
        try {
            InitialContext initialContext = new InitialContext();
            ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("jms/myConnectionFactory");
            Queue queue = (Queue) initialContext.lookup("jms/myOutputQueue");

            jmsContext = connectionFactory.createContext();
            JMSProducer jmsProducer = jmsContext.createProducer();

            String message = "Average Vehicle Speed: " + averageSpeed +
                    "; Average Traffic Flow: " + averageTrafficFlow+
                    "; Average Urban Mobility Efficiency: " + urbanMobilityEfficiency;

            System.out.println("Sending analyzed data:");
            System.out.println(message);
            jmsProducer.send(queue, message);
            System.out.println("Analyzed data sent successfully.");
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (jmsContext != null) {
                jmsContext.close();
            }
        }
    }

}