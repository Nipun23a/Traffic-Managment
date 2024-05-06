package main.java.com.mycompany.UI;

import javax.swing.*;
import java.awt.*;
import main.java.com.mycompany.DataReceiver.AnalyzedDataReceiver;

public class AnalyzeApplication {
    private JFrame mainFrame;

    private JLabel averageSpeedLabel;
    private JLabel trafficFlowLabel;
    private JLabel urbanMobilityEfficiencyLabel;

    public AnalyzeApplication() {
        mainFrame = new JFrame("Traffic Analyze App");

        // Panels for north and center
        JPanel northPanel = new JPanel();
        // North Panel Description
        JLabel titleLabel = new JLabel("Traffic Analyze App");
        northPanel.add(titleLabel);
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        northPanel.setPreferredSize(new Dimension(400, 50));

        // Central Panel Description
        JPanel centralPanel = new JPanel();

        // Panels in central panel
        JPanel centralUpLeftPanel = new JPanel();
        JPanel centralUpRightPanel = new JPanel();
        JPanel centralMiddleLeftPanel = new JPanel();
        JPanel centralMiddleRightPanel = new JPanel();
        JPanel centralDownLeftPanel = new JPanel();
        JPanel centralDownRightPanel = new JPanel();

        JLabel averageSpeedTitleLabel = new JLabel("Average Speed:");
        averageSpeedLabel = new JLabel();
        JLabel trafficFlowTitleLabel = new JLabel("Traffic Flow:");
        trafficFlowLabel = new JLabel();
        JLabel overallUrbanTitleLabel = new JLabel("Overall Urban Mobility Efficiency:");
        urbanMobilityEfficiencyLabel = new JLabel();


        centralUpLeftPanel.add(averageSpeedTitleLabel);
        centralUpLeftPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        centralUpRightPanel.add(averageSpeedLabel);
        centralUpRightPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        centralMiddleLeftPanel.add(trafficFlowTitleLabel);
        centralMiddleLeftPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        centralMiddleRightPanel.add(trafficFlowLabel);
        centralMiddleRightPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        centralDownLeftPanel.add(overallUrbanTitleLabel);
        centralDownLeftPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        centralDownRightPanel.add(urbanMobilityEfficiencyLabel);
        centralDownRightPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        centralPanel.add(centralUpLeftPanel);
        centralPanel.add(centralUpRightPanel);
        centralPanel.add(centralMiddleLeftPanel);
        centralPanel.add(centralMiddleRightPanel);
        centralPanel.add(centralDownLeftPanel);
        centralPanel.add(centralDownRightPanel);

        centralPanel.setLayout(new GridLayout(4, 4));

        mainFrame.add(northPanel, BorderLayout.NORTH);
        mainFrame.add(centralPanel, BorderLayout.CENTER);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 500);
        mainFrame.setLocationRelativeTo(null);

        // Start the AnalyzedDataReceiver in a new thread
        AnalyzedDataReceiver receiver = new AnalyzedDataReceiver(averageSpeedLabel, trafficFlowLabel, urbanMobilityEfficiencyLabel);
        new Thread(receiver).start();

        mainFrame.setVisible(true);
    }


}