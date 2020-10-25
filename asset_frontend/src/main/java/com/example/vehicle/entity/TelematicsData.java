package com.example.vehicle.entity;

import lombok.Data;

@Data
public class TelematicsData {
    String vin;
    String deviceId;
    TelematicsRecordData data;
    String deviceSignature;
}
