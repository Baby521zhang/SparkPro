package com.test.qq;

/**
 * 继承关系
 * @author zhangchenguang
 *
 */
public class Ab extends A{

	private String name;
	private int age;
	
	public Ab() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Ab(String name, int age) {
		super(name,age);
//		this.name = name;
//		this.age = age;
	}
	@Override
	public String toString() {
		return "Ab [name=" + name + ", age=" + age + "]";
	}
	
	public static void main(String[] args) {
		Ab ab = new Ab("张三",10);
		System.out.println(ab);
	}
	
	
}


class A {
	private String name;
	private int age;
	public A() {
		super();
		// TODO Auto-generated constructor stub
	}
	public A(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "Ab [name=" + name + ", age=" + age + "]";
	}
}