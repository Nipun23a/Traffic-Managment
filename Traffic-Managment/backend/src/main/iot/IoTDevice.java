package main.iot;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class IoTDevice {


    //Attributes



    private double vehicleSpeed;
    private boolean trafficLightStatus;



    // Constructors
    public IoTDevice(){}

    public IoTDevice(double vehicleSpeed,boolean trafficLightStatus){
        this.vehicleSpeed = vehicleSpeed;
        this.trafficLightStatus = trafficLightStatus;
    }

    // Getter and Setter Method

    public double getVehicleSpeed() {
        return vehicleSpeed;
    }

    public void setVehicleSpeed(double vehicleSpeed) {
        this.vehicleSpeed = vehicleSpeed;
    }

    public boolean getTrafficLightStatus() {
        return trafficLightStatus;
    }

    public void setTrafficLightStatus(boolean trafficLightStatus) {
        this.trafficLightStatus = trafficLightStatus;
    }

    // Other Methods

    public void generateRandomValues() {
        Random rand = new Random();
        this.vehicleSpeed = rand.nextDouble() * 100; // Assuming speed is in km/h
        this.trafficLightStatus = rand.nextBoolean();
    }


}

