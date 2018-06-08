package cn.edu360.day02.json;

import cn.edu360.day02.RatingBean;

import com.alibaba.fastjson.JSON;

public class FastJsonTest {
	public static void main(String[] args) {
		//json转对象
		//第一个参数是存入需要转换的json ，第二个参数
		String json = "{\"movie\":\"1193\",\"rate\":\"5\",\"timeStamp\":\"978300760\",\"uid\":\"1\"}";
		RatingBean object = JSON.parseObject(json, RatingBean.class);
		System.out.println(object);
		
		//对象转json
		String jsonString = JSON.toJSONString(object);
		System.out.println(jsonString);
	}

}

/**
{
	"queryLocation": [41.083778, 113.983896],
	"addrList": [{
		"type": "street",
		"status": 1,
		"name": "秀水街",
		"admCode": "130725",
		"admName": "河北省,张家口市,尚义县",
		"addr": "",
		"nearestPoint": [113.98374, 41.08395],
		"distance": 22.161
	}, {
		"type": "poi",
		"status": 1,
		"name": "华艺幼儿园",
		"id": "ANB01390PN2H",
		"admCode": "130725",
		"admName": "河北省,张家口市,尚义县,",
		"addr": "",
		"nearestPoint": [113.98263, 41.08359],
		"distance": 127.279
	}, {
		"type": "doorPlate",
		"status": 0,
		"name": "",
		"admCode": "",
		"admName": "",
		"nearestPoint": [],
		"distance": -1
	}]
} 
 */
