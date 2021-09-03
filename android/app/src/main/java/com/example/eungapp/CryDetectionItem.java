package com.example.eungapp;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CryDetectionItem {
    private String timestamp;
    private double temperature;
    private double humidity;
    private boolean smell;

    @SerializedName("sensor")
    private SensorItem sensorItem;

    private boolean checked;

    public CryDetectionItem() {}
    public CryDetectionItem(String timestamp, double temperature, double humidity, boolean smell, SensorItem sensorItem) {
        this.timestamp = timestamp; this.temperature = temperature; this.humidity = humidity;
        this.smell = smell; this.sensorItem = sensorItem;
    }

    public String getTimestamp() { return timestamp; }
    public double getTemperature() { return temperature; }
    public double getHumidity() { return humidity; }
    public boolean getSmell() { return smell; }
    public SensorItem getSensorItem() { return sensorItem; }
    public Boolean getChecked() { return checked; }
}
