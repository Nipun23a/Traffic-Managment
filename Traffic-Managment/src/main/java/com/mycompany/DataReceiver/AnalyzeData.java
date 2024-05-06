package main.java.com.mycompany.DataReceiver;

public class AnalyzeData {
    private String gprsCoordinate;
    private double averageSpeed;
    private double averageTrafficFlow;
    private double urbanMobilityEfficiency;
    public AnalyzeData(){}

    public AnalyzeData(double averageSpeed, double averageTrafficFlow, double urbanMobilityEfficiency) {
        this.averageSpeed = averageSpeed;
        this.averageTrafficFlow = averageTrafficFlow;
        this.urbanMobilityEfficiency = urbanMobilityEfficiency;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public double getAverageTrafficFlow() {
        return averageTrafficFlow;
    }

    public double getUrbanMobilityEfficiency() {
        return urbanMobilityEfficiency;
    }



    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public void setAverageTrafficFlow(double averageTrafficFlow) {
        this.averageTrafficFlow = averageTrafficFlow;
    }

    public void setUrbanMobilityEfficiency(double urbanMobilityEfficiency) {
        this.urbanMobilityEfficiency = urbanMobilityEfficiency;
    }
}
