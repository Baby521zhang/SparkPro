package com.nyist.hdl.model;

import java.io.Serializable;

/**
 * 
 * @author zhangchenguang
 *
 */
public class Log implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ip;
	private String dtime;
	private String url;

	public Log() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Log(String ip, String dtime, String url) {
		super();
		this.ip = ip;
		this.dtime = dtime;
		this.url = url;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDtime() {
		return dtime;
	}

	public void setDtime(String dtime) {
		this.dtime = dtime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Log [ip=" + ip + ", dtime=" + dtime + ", url=" + url + "]";
	}

}