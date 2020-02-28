package com.nyist.zcg.cleaner.entity;

public class PV {
	private int id;
	private int pv;
	private String tj_time;
	
	public PV() {
		// TODO Auto-generated constructor stub
	}

	public PV(int id, int pv, String tj_time) {
		super();
		this.id = id;
		this.pv = pv;
		this.tj_time = tj_time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPv() {
		return pv;
	}

	public void setPv(int pv) {
		this.pv = pv;
	}

	public String getTj_time() {
		return tj_time;
	}

	public void setTj_time(String tj_time) {
		this.tj_time = tj_time;
	}

	@Override
	public String toString() {
		return "PV [id=" + id + ", pv=" + pv + ", tj_time=" + tj_time + "]";
	}

}
