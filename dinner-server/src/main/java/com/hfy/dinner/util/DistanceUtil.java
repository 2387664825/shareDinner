package com.hfy.dinner.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author hfy
 * @date 2021/5/11 18:54
 */
public class DistanceUtil {
    /**
     * 地球半径
     */
    private static double EarthRadius = 6378.137;

    /**
     * 经纬度转化成弧度
     * Add by 成长的小猪（Jason.Song） on 2017/11/01
     * http://blog.csdn.net/jasonsong2008
     *
     * @param d 经度/纬度
     * @return
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 获取两点间的距离
     *
     * @param firstLatitude
     * @param firstLongitude
     * @param secondLatitude
     * @param secondLongitude
     * @return
     */
    public static double getDistance(double firstLatitude, double firstLongitude,
                                     double secondLatitude, double secondLongitude) {
        double firstRadLat = rad(firstLatitude);
        double firstRadLng = rad(firstLongitude);
        double secondRadLat = rad(secondLatitude);
        double secondRadLng = rad(secondLongitude);

        double a = firstRadLat - secondRadLat;
        double b = firstRadLng - secondRadLng;
        double cal = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(firstRadLat)
                * Math.cos(secondRadLat) * Math.pow(Math.sin(b / 2), 2))) * EarthRadius;
        double result = Math.round(cal * 10000d) / 10000d;
        return result;

    }

    /**
     * 获取经纬度所在城市
     *
     * @param lat 纬度
     * @param lng 经度
     * @return 所属城市
     */
    public static String getDistrict(double lat, double lng) throws Exception {
        String json = getLocationInfo(lat, lng, false);
        System.out.println(json); // 打印结果json
        if (json != null) {
            /*
             * 返回数据
            {"status":0,"result":{"location":{"lng":103.93225527425858,"lat":30.577972524354914},
            "formatted_address":"四川省成都市双流区藏卫路171","business":"东升","addressComponent":{
             "country":"中国","country_code":0,"country_code_iso":"CHN","country_code_iso2":"CN","province":"四川省","city":"成都市",
            "city_level":2,"district":"双流区","town":"","town_code":"","adcode":"510116","street":"藏卫路",
            "street_number":"171","direction":"附近","distance":"9"},"pois":[],"roads":[],"poiRegions":[],"sematic_description":"","cityCode":75}}
             */
            // 解析得到的json格式数据
            JSONObject dataJson = JSONObject.parseObject(json);
            JSONObject result = dataJson.getJSONObject("result");
            JSONObject addressComponent = result.getJSONObject("addressComponent");
            String district = addressComponent.getString("district");
            return district;
        } else {
            throw new Exception("异常出现：调用 getLocationInfo() 返回结果异常");
        }
    }

    /**
     * 获取地址Json字符串
     *
     * @param lat    纬度
     * @param lng    经度
     * @param around 是否显示周边的poi（为true默认显示周边1000米内的poi）
     * @return
     */
    private static String getLocationInfo(double lat, double lng, boolean around) {
        String data = null;
        /** 我选择在 config.properties 配置 baidu.ak，这里你可以用自己的百度AK */
        String ak = "z8rDnOVU1ODhZVbQG2kRLCO50cQhhptk";
        if (StringUtils.isBlank(ak)) {
            System.out.println("baidu ak is null");
            return null;
        }
//        String url = "http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&location=" + lat + "," + lng
//                + "&output=json&pois=" + around + "&ak=" + ak;
        String url = "http://api.map.baidu.com/reverse_geocoding/v3/?ak=" + ak + "&output=json&coordtype=wgs84ll&location=" + lat + "," + lng;
        URL myURL = null;
        URLConnection httpsConn = null;
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        InputStreamReader insr = null;
        BufferedReader br = null;
        try {
            httpsConn = (URLConnection) myURL.openConnection();// 不使用代理
            if (httpsConn != null) {
                insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
                br = new BufferedReader(insr);
                data = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (insr != null) {
                    insr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return data;
    }
}
