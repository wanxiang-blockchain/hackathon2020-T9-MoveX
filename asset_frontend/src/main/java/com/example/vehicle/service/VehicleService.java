package com.example.vehicle.service;

import com.example.vehicle.entity.TelematicsData;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface VehicleService {
    PageInfo<TelematicsData> telematicsData(List<TelematicsData>  list, int pageNum, int pageSize) throws IOException;
}
