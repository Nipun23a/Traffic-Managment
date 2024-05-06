package main.java.com.mycompany.DataReceiver;

import jakarta.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class AnalyzedDataReceiver implements Runnable {
    private JLabel gprsCoordinateLabel;
    private JLabel averageSpeedLabel;
    private JLabel trafficFlowLabel;
    private JLabel urbanMobilityEfficiencyLabel;
    private static List<AnalyzeData> analyzedDataList = new ArrayList<>();

    public static List<AnalyzeData> getAnalyzedDataList() {
        return analyzedDataList;
    }
    public AnalyzedDataReceiver(JLabel averageSpeedLabel, JLabel trafficFlowLabel, JLabel urbanMobilityEfficiencyLabel) {
        this.averageSpeedLabel = averageSpeedLabel;
        this.trafficFlowLabel = trafficFlowLabel;
        this.urbanMobilityEfficiencyLabel = urbanMobilityEfficiencyLabel;
    }

    @Override
    public void run() {
        try {
            InitialContext initialContext = new InitialContext();
            ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("jms/myConnectionFactory");
            Queue queue = (Queue) initialContext.lookup("jms/myOutputQueue");

            JMSContext jmsContext = connectionFactory.createContext();
            JMSConsumer jmsConsumer = jmsContext.createConsumer(queue);

            System.out.println("AnalyzedDataReceiver is running and ready to receive messages.");

            while (true) {
                String message = jmsConsumer.receiveBody(String.class);
                if (message != null) {
                   //System.out.println("Received message: " + message);
                    parseAndDisplayData(message);
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    private void parseAndDisplayData(String message) {
        String[] parts = message.split("; ");
        if (parts.length ==3) {
            double averageSpeed = Double.parseDouble(parts[0].split(": ")[1]);
            double averageTrafficFlow = Double.parseDouble(parts[1].split(": ")[1]);
            double urbanMobilityEfficiency = Double.parseDouble(parts[2].split(": ")[1]);

            AnalyzeData data = new AnalyzeData(averageSpeed, averageTrafficFlow, urbanMobilityEfficiency);
            analyzedDataList.add(data);

            System.out.println("New Analyzed Data:");

            System.out.println("Average Speed: " + data.getAverageSpeed());
            System.out.println("Traffic Flow: " + data.getAverageTrafficFlow());
            System.out.println("Urban Mobility Efficiency: " + data.getUrbanMobilityEfficiency());
            System.out.println("-------------------");

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    averageSpeedLabel.setText(String.valueOf(averageSpeed));
                    trafficFlowLabel.setText(String.valueOf(averageTrafficFlow));
                    urbanMobilityEfficiencyLabel.setText(String.valueOf(urbanMobilityEfficiency));
                }
            });
        }
    }

}