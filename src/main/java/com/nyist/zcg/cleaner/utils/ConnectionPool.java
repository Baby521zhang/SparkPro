package com.nyist.zcg.cleaner.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;

/**
 * 数据库连接池
 * @author zhangchenguang
 *
 */
public class ConnectionPool {
	private static LinkedList<Connection> connectionQueue;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public synchronized static Connection getConnection() {
		try {
			if (connectionQueue == null) {
				connectionQueue = new LinkedList<Connection>();
				for (int i = 0; i < 5; i++) {
					Connection conn = DriverManager
							.getConnection(
									"jdbc:mysql://localhost:3306/t_bigdata", 
									"root",
									"123456");
					connectionQueue.push(conn);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connectionQueue.poll();

	}

	public static void returnConnection(Connection conn) {
		connectionQueue.push(conn);
	}
	
	public static void main(String[] args) {
		ConnectionPool.getConnection();
		System.out.println("获取成功！！");
	}
}