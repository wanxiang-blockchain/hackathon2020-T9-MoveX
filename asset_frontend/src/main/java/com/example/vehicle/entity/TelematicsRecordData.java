package com.example.vehicle.entity;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class TelematicsRecordData {
    long timestamp;
    double locLat;
    double locLong;
    double mileage;
    double fuelLevel;
    boolean isDoorOpened;
    /*
    String datetime;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public String getDatetime(){
        try{
            return sdf.format(new Date(timestamp * 1000));
        }catch (Exception e){
            return "0";
        }
    }
     */


}
