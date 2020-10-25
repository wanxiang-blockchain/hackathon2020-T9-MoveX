package com.example.vehicle.service.impl;

import com.example.vehicle.entity.TelematicsData;
import com.example.vehicle.service.VehicleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Override
    public PageInfo<TelematicsData> telematicsData(List<TelematicsData> list, int pageNum, int pageSize) throws IOException {
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<TelematicsData> pageInfo=new PageInfo<TelematicsData>(list);
        return pageInfo;
    }
}
