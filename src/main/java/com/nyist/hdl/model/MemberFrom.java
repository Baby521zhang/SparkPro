package com.nyist.hdl.model;

import java.io.Serializable;

public class MemberFrom implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String fromName;
	private int count;
	
	public MemberFrom() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MemberFrom(String fromName, int count) {
		super();
		this.fromName = fromName;
		this.count = count;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "MemberFrom [fromName=" + fromName + ", count=" + count + "]";
	}
	
}
