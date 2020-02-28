package com.nyist.zcg.cleaner.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import com.nyist.zcg.cleaner.entity.PV;
import com.nyist.zcg.cleaner.utils.ConnectionPool;
import com.nyist.zcg.cleaner.utils.DateUtil;

/**
 * flink sink输出操作
 * @author zhangchenguang
 *
 */
public class MysqlSink extends RichSinkFunction<String>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PV pv = null;

	@Override
	public void invoke(String value) throws Exception {
		
		Connection con = ConnectionPool.getConnection();
		String sql="SELECT * FROM t_pvbyday WHRER tj_time = ?";
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setString(1, DateUtil.formatDate(new Date()));
		
		ResultSet rs = psmt.executeQuery();
		while(rs.next()){
			int t_id = rs.getInt("id");
			int pv_counts = rs.getInt("pv");
			String tj_time = rs.getString("tj_time");
			pv = new PV();
			pv.setId(t_id);
			pv.setPv(pv_counts);
			pv.setTj_time(tj_time);
		}
		if(pv != null){
			pv.setPv(pv.getPv()+Integer.parseInt(value));
			updatePV(pv);
		}else{
			pv = new PV();
			pv.setPv(Integer.parseInt(value));
			pv.setTj_time(DateUtil.formatDate(new Date()));
			insertPV(pv);
		}
		
	}

	/**
	 * update 操作
	 * @param pv2
	 */
	private void updatePV(PV pv2) {
		
		try {
			String sql = "UPDATE t_pvbyday SET pv = ? WHERE id = ?";
			PreparedStatement psmt = ConnectionPool.getConnection().prepareStatement(sql);
			psmt.setInt(1, pv2.getPv());
			psmt.setInt(2, pv2.getId());
			psmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * insert 操作
	 * @param pv2
	 */
	private void insertPV(PV pv2) {
		String sql = "INSERT INTO t_pvbyday VALUES(null,?,?)";
		PreparedStatement psmt;
		try {
			psmt = ConnectionPool.getConnection().prepareStatement(sql);
			psmt.setInt(1, pv2.getPv());
			psmt.setString(2, pv2.getTj_time());
			psmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
