package com.example.vehicle.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.vehicle.entity.TelematicsData;
import com.example.vehicle.service.VehicleService;
import com.example.vehicle.util.HttpUtil;
import com.example.vehicle.util.PageInfoUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/vehicle")
public class VehicleController {
    String URL_PREFIX = "https://test.mobilityblockchainplatform.io/fleet-backend-1/api/v1/vehicles/";
    String URL_SUFFIX = "/telematics-data";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    VehicleService vehicleService;

    @RequestMapping(value = "/telematicsData")
    public Map<String, Object> telematicsData(@RequestParam int page, @RequestParam int rows, @RequestParam String vin, @RequestParam String startTime, @RequestParam String endTime) {
        Map<String, Object> map = new HashMap<>();
        logger.info("page:[{}],rows:[{}],vin:[{}],startTime:[{]],endTime:[{}]",page,rows,vin,startTime,endTime);
        try {
            //获取响应信息
            long from, to;

            //参数判断
            //判断vin号
            if (vin == null || vin.equals("")) {
                return null;
            }
            //判断时间起点
            if (startTime == null || startTime.equals("")) {
                from = 0;
            } else {
                from = simpleDateFormat.parse(startTime).getTime()/1000;
            }
            //判断时间终点
            if (endTime == null || endTime.equals("")) {
                to = System.currentTimeMillis()/1000;
            } else {
                to = (simpleDateFormat).parse(endTime).getTime()/1000;
            }

            //访问戴姆勒后台获取数据
            String url = URL_PREFIX + vin + URL_SUFFIX+"?from="+from+"&to="+to;
            logger.info("url:[{}]",url);
            String result = HttpUtil.sendGet(url);
            if (result == null || result.equals("")) {
                return null;
            }

            //返回前端显示
            List<TelematicsData> list = JSONObject.parseArray(result, TelematicsData.class);
            PageInfo<TelematicsData> queryResult = PageInfoUtils.list2PageInfo(list, page, rows);
            //PageInfo<TelematicsData> queryResult = vehicleService.telematicsData(list, page, rows);
            map.put("rows", queryResult.getList());
            map.put("total", queryResult.getTotal());
            map.put("pageNum", queryResult.getPageNum());
            map.put("pageSize", queryResult.getPageSize());
        } catch (Exception e) {
            logger.error("error:[{}]",e.getMessage(),e);
            return null;
        }
        System.out.println("map:"+map);
        return map;
    }
}
