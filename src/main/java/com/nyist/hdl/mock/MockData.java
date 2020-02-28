package com.nyist.hdl.mock;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

/**
 * mock data  json 数据
 * 自己动手，丰衣足食，幸福是靠自己的双手创造的。
 */
public class MockData {

	public static void main(String[] args) throws IOException {
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/Users/zhangchenguang/eclispe_workspace/spark_pro/src/main/java/com/nyist/hdl/datas/data2.txt")));
		String djson = null;
		Data data = null;
		
		int count = 10000;
		while(count>0){
			int tmp = 0;
			djson = new String();
			data = new Data();
			
			Random rd = new Random();
			
			// 112.97.63.126
			data.setIp(rd.nextInt(254)+"."+rd.nextInt(99)+"."+rd.nextInt(99)+"."+rd.nextInt(254));
			
			//1001
			data.setUser_id(rd.nextInt(10000)+"");
			
			//2
			tmp = rd.nextInt(5);
			switch (tmp) {
			case 1:
				data.setActions("登录");
				break;
			case 2:
				data.setActions("浏览商品");
				break;
			case 3:
				data.setActions("浏览评论");
				break;
			case 4:
				data.setActions("搜索");
				break;
			default:
				data.setActions("下单");
				break;
			}
			
			
			//2018-10-24
			data.setActionTimes("2018-"+rd.nextInt(12)+"-"+rd.nextInt(31));
			
			tmp = rd.nextInt(4);
			
			/**
			 * Mozilla/5.0 (Windows NT 6.1; Win64; x64) 
			 * IE/537.36 (KHTML, like Gecko) 
			 * Chrome/73.0.3683.75 
			 * Safari/537.36
			 */
			switch (tmp) {
			case 1:
				data.setFromType("Mozilla/5.0 (Windows NT 6.1; Win64; x64)");
				break;
			case 2:
				data.setFromType("IE/537.36 (KHTML, like Gecko) ");
				break;
			case 3:
				data.setFromType("Safari/537.36");
				break;
			default:
				data.setFromType("Chrome/73.0.3683.75");
				break;
			}
			
			//v1.0
			data.setBb("v1.0");
			
			tmp = rd.nextInt(10);
			if(tmp%2==0){
				//GET
				data.setRequestMethod("GET");
			}else{
				//GET
				data.setRequestMethod("POST");
			}
			
			
			//
			tmp = rd.nextInt(5);
			switch (tmp) {
			case 1:
				// 百度推广
				data.setFromURL("https://www.baidu.com/s?wd=ip%E5%9C%B0%E5%9D%80&rsv_spt=1");
				break;
			case 2:
				// 视频广告
				data.setFromURL("https://tv.qq.com/channel/child?listpage=1&channel=children&itype=3");
				break;
			case 3:
				// 直接访问
				data.setFromURL("https://www.nyist.com/s?wd=ip%E5%9C%B0%E5%9D%80&rsv_spt=1");
				break;
			case 4:
				// 邮件方式
				data.setFromURL("https://www.mail.com/int?wd=ip%E5%9C%B0%E5%9D%80&rsv_spt=1");
				break;
			default:
				// 客服电话
				data.setFromURL("https://www.phone.com/int?wd=ip%E5%9C%B0%E5%9D%80&rsv_spt=1");
				break;
			}
			
			//476e2875e9fb4802a7f6
			data.setSessionId(UUID.randomUUID().toString());
			
			//关键洞察力 2018-10-17
			data.setTitle("关键"+rd.nextInt(1000)+"洞察力");
			
			djson = JSON.toJSONString(data);
			bw.write(djson);
			if(count-1>0){
				bw.write("\n");
			}
			
			count--;
		}
		bw.close();
	}
}

class Data{
	private String ip;
	private String user_id;
	private String actions;
	private String actionTimes;
	private String fromType;
	private String bb;
	private String requestMethod;
	private String fromURL;
	private String sessionId;
	private String title;
	public Data() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Data(String ip, String user_id, String actions, String actionTimes, String fromType,
			String bb, String requestMethod, String fromURL, String sessionId, String title) {
		super();
		this.ip = ip;
		this.user_id = user_id;
		this.actions = actions;
		this.actionTimes = actionTimes;
		this.fromType = fromType;
		this.bb = bb;
		this.requestMethod = requestMethod;
		this.fromURL = fromURL;
		this.sessionId = sessionId;
		this.title = title;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getActions() {
		return actions;
	}
	public void setActions(String actions) {
		this.actions = actions;
	}
	public String getActionTimes() {
		return actionTimes;
	}
	public void setActionTimes(String actionTimes) {
		this.actionTimes = actionTimes;
	}
	public String getFromType() {
		return fromType;
	}
	public void setFromType(String fromType) {
		this.fromType = fromType;
	}
	public String getBb() {
		return bb;
	}
	public void setBb(String bb) {
		this.bb = bb;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	public String getFromURL() {
		return fromURL;
	}
	public void setFromURL(String fromURL) {
		this.fromURL = fromURL;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "Data [ip=" + ip + ", user_id=" + user_id + ", actions=" + actions + ", actionTimes=" + actionTimes
				+ ", fromType=" + fromType + ", bb=" + bb + ", requestMethod=" + requestMethod + ", fromURL=" + fromURL
				+ ", sessionId=" + sessionId + ", title=" + title + "]";
	}
	
}