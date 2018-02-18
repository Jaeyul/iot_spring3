package com.iot.spring.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.iot.spring.common.dbcon.DBCon;
import com.iot.spring.dao.SqlDAO;

@Repository
public class SqlDAOImpl implements SqlDAO {
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs= null;	

	@Override
	public List<Map<String, Object>> select(String sql) {

		List<Map<String, Object>> List = new ArrayList<Map<String, Object>>();
		try {
			con = DBCon.getCon();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd  = rs.getMetaData();
			int colCnt = rsmd.getColumnCount();
			String colStr = "";
			String typeStr = "";
			for(int i=1; i<=colCnt; i++) {
				colStr +=  rsmd.getColumnName(i) + ",";	
				typeStr += "ro,";
			}
			colStr = colStr.substring(0, colStr.length()-1);
			typeStr = typeStr.substring(0, typeStr.length()-1);
			
			Map<String, Object> metaDataMap = new HashMap<String,Object>();			
			metaDataMap.put("colStr", colStr);
			metaDataMap.put("typeStr", typeStr);			
			List.add(metaDataMap);
			
			while(rs.next()) {
				Map<String, Object> rowDataMap = new HashMap<String,Object>();
				for(int i=1; i<=colCnt; i++) {
					rowDataMap.put(rsmd.getColumnName(i), rs.getObject(rsmd.getColumnName(i)));	
				}
				List.add(rowDataMap);
			}
			return List;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}		
		return null;
	}

	@Override
	public int insert(String sql) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String sql) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(String sql) {
		// TODO Auto-generated method stub
		return 0;
	}


}
