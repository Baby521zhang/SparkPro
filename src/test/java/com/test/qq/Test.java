package com.test.qq;
import java.time.LocalDate;
import java.util.Date;


public class Test {

	public static void main(String[] args) {
//		Date today = new Date();
//		System.out.println(today.toString());
//		
//		
//		LocalDate localDate = LocalDate.now();
//		System.out.println(localDate.getYear()+"=="+localDate.getMonthValue());
		Circle c = new Circle();
		c.setR(2.0);
		System.out.println("周长是："+c.peri()+"\n面积是"+c.area());
	}
}


class Circle{
	
	private double r;

	public Circle() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Circle(double r) {
		super();
		this.r = r;
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}

	@Override
	public String toString() {
		return "Circle [r=" + r + "]";
	}
	
	// 计算周长
	public double peri(){
		return 2*Math.PI*r;
	}
	
	// 计算面积
	public double area(){
		return Math.PI*r*r;
	}
	
}