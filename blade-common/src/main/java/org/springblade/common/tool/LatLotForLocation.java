package org.springblade.common.tool;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hyp
 * @description: TODO
 * @projectName SafetyStandards
 * @date 2019/11/1215:02
 */
public class LatLotForLocation {



	/**
	 * 获取百度地理位置
	* */
	public static String getProvince(String lat, String log,String ak) {
		String add = "";
		String[] akList = ak.split(",");
		//去除素组中重复的数组
		List<String> ak_listid = new ArrayList<String>();
		for (int i=0; i<akList.length; i++) {
			if(!ak_listid.contains(akList[i])) {
				ak_listid.add(akList[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] ak_idss= ak_listid.toArray(new String[1]);
		System.out.println(ak_idss.length);
		System.out.println(ak_idss[0]);
		String address = "";
		JSONObject result = null;
		if(ak_idss.length > 0 && ak_idss != null && !"".equals(ak_idss[0])) {
			for (int i = 0; i < ak_idss.length; i++) {
				add = getAdd(lat, log,ak_idss[i]);
				JSONObject jsonObject = JSONUtil.parseObj(add);
				result = (JSONObject)jsonObject.get("result");
				if(result != null){
					break;
				}else{
					continue;
				}
			}
		}
		if(result != null){
			address = result.get("formatted_address").toString();
			if(!StringUtils.isBlank(result.get("sematic_description").toString())){
				address += result.get("sematic_description").toString();
			}
		}
		return address;
	}

	public static String getAdd(String lat, String log,String ak) {
		/*
		 log lat
		 参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项)
		 coordtype 坐标的类型，目前支持的坐标类型包括：bd09ll（百度经纬度坐标）、bd09mc（百度米制坐标）、gcj02ll（国测局经纬度坐标，仅限中国）、wgs84ll（ GPS经纬度）
		 output 输出格式为json或者xml
		 extensions_poi extensions_poi=0，不召回pois数据。 extensions_poi=1，返回pois数据，默认显示周边1000米内的poi。
		8uUAMcdHlYWBzSkv3Q9amUGjUSxruG3F xinjiang
		5yGQ6pxQ5jl3EdF2SnVCdc4RD75c47ME jinwenpeng
		euwNU9FGbNw738IMRGStLVPoxlu1YePG anhui
		euwNU9FGbNw738IMRGStLVPoxlu1YePG ts
		*/
		String urlString = "http://api.map.baidu.com/reverse_geocoding/v3/?ak="+ak+"&output=json&coordtype=wgs84ll&extensions_poi=1&location="+lat+","+log;
		String res = "";
		try {
			URL url = new URL(urlString);
			java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				res += line + "\n";
			}
			in.close();
		} catch (Exception e) {
			System.out.println("error in wapaction,and e is " + e.getMessage());
		}
		return res;
	}

	/**
	 *  获取高德地理位置
	 * */
	public static String getProvinceGd(String lat, String log) {
		String add = getAddGD(lat, log);
		System.out.println(" ****** "+add);
		JSONObject jsonObject = JSONUtil.parseObj(add);

		JSONObject result = (JSONObject)jsonObject.get("regeocode");
		String address = result.get("formatted_address").toString();
		System.out.println(" ****** "+address);
		return address;
	}

	public static String getAddGD(String lat, String log) {
		/*
		 log lat
		 参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项)
		d3e99164851fccb3052b501e1d53af67 xinjiang
		a90acf8f0852d96d343c82867a5b6b2b tangseng
		*/
		String urlString = "https://restapi.amap.com/v3/geocode/regeo?output=json&location="+log+","+lat+ "&key=d3e99164851fccb3052b501e1d53af67&radius=1000&extensions=all";
		String res = "";
		try {
			URL url = new URL(urlString);
			java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				res += line + "\n";
			}
			in.close();
		} catch (Exception e) {
			System.out.println("error in wapaction,and e is " + e.getMessage());
		}
		return res;
	}

}
