package com.example.eungapp;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class SensorItem {

    static HashMap<String, String> locationHashEngToKor = new HashMap<String, String>();

    private String serial_number;
    private String location;

    @SerializedName("user")
    private UserItem userItem;

    private Boolean status;
    private String model;

    public SensorItem(String serial_number, String location, UserItem userItem, Boolean status, String model) {
        this.serial_number = serial_number; this.location = location; this.userItem = userItem;
        this.status = status; this.model = model;
    }

    static { initLocationHashEngToKor(); }
    public static void initLocationHashEngToKor() {
        locationHashEngToKor.put("LIVING_ROOM", "거실");
        locationHashEngToKor.put("DINING_ROOM", "부엌");
        locationHashEngToKor.put("BED_ROOM1", "침실1");
        locationHashEngToKor.put("BED_ROOM2", "침실2");
        locationHashEngToKor.put("BED_ROOM3", "침실3");
        locationHashEngToKor.put("ETC", "기타");
    }


    public String getSerialNumber() { return serial_number; }
    public String getLocation() { return locationHashEngToKor.get(location); }
    public UserItem getUserItem() { return userItem; }
    public Boolean getStatus() { return status; }
    public String getModel() {
        if (model.equals("THERM")) return "온도 센서";
        if (model.equals("HUMID")) return "습도 센서";
        if (model.equals("GAS")) return "가스 센서";
        if (model.equals("AUX")) return "음성 센서";
        return "기타";
    }
}
