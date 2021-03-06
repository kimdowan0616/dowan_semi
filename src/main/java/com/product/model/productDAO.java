package com.product.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.product.db.ConnectionPoolMgr;

public class productDAO {
	private ConnectionPoolMgr pool;
	
	public productDAO() {
		pool = new ConnectionPoolMgr();
	}
	
	/**
	 * @param keyword	검색어
	 * @param category	카테고리코드
	 * @return	List<productVO>
	 * @throws SQLException
	 */
	public List<productVO> selectAll(String keyword, String category) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<productVO> list = new ArrayList<productVO>();
		try {
			// 1, 2
			con = pool.getConnection();

			// 3 ps
			String sql = "select * from product";
			
			// 카테고리로 진입, 검색x => 매개변수 category_code로 각 카테고리에 해당하는 상품 출력
			if(category!=null && !category.isEmpty()) {
				sql += " where category_code = ?";
				sql += " order by pdcode";
				ps = con.prepareStatement(sql);
				
				ps.setString(1, category);
			}	// 검색으로 진입 => 매개변수 keyword로 pdname에 해당하는 상품 출력
			else if(keyword!=null && !keyword.isEmpty()) {
				sql += " where pdname like '%' || ? || '%'";
				sql += " order by pdcode";
				ps = con.prepareStatement(sql);
				
				ps.setString(1, keyword);
			}
			
			
			// 4 exec
			rs = ps.executeQuery();
			while(rs.next()) {
				int pdcode = rs.getInt("pdcode");
				String pdname = rs.getString("pdname");
				int comno = rs.getInt("comno");
				int price = rs.getInt("price");
				Timestamp regdate = rs.getTimestamp("regdate");
				String image = rs.getString("image");
				int category_code = rs.getInt("category_code");
						
				productVO vo = new productVO(pdcode, pdname, comno, price, regdate, image, category_code);
				
				list.add(vo);
			}
			System.out.println("제품목록 결과 list.size = " + list.size() +", keyword = " + keyword + ", category = " + category);
			
			return list;			
		}finally {
			pool.dbClose(rs, ps, con);
		}
	}
	
	public List<productVO> selectByCategory(String category) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<productVO> list = new ArrayList<productVO>();
		try {
			// 1, 2
			con = pool.getConnection();

			// 3 ps
			String sql = "select * from product"
					+ " where category_code=(select category_code from category where category_name like '%' || ? || '%')"
					+ " order by pdcode";
			
			// 검색으로 진입 => 매개변수 keyword로 pdname에 해당하는 상품 출력
			/*if(category!=null && !category.isEmpty()) {
				sql += " where pdname like '%' || ? || '%'";
				sql += " order by pdcode";
				ps = con.prepareStatement(sql);
				
				ps.setString(1, category);
			}*/
			ps = con.prepareStatement(sql);
			
			ps.setString(1, category);
			
			// 4 exec
			rs = ps.executeQuery();
			while(rs.next()) {
				int pdcode = rs.getInt("pdcode");
				String pdname = rs.getString("pdname");
				int comno = rs.getInt("comno");
				int price = rs.getInt("price");
				Timestamp regdate = rs.getTimestamp("regdate");
				String image = rs.getString("image");
				int category_code = rs.getInt("category_code");
						
				productVO vo = new productVO(pdcode, pdname, comno, price, regdate, image, category_code);
				
				list.add(vo);
			}
			System.out.println("제품목록 결과 list.size = " + list.size() +", category = " + category);
			
			return list;			
		}finally {
			pool.dbClose(rs, ps, con);
		}
	}
	
	
	public List<productVO> selectByKeyword(String keyword) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<productVO> list = new ArrayList<productVO>();
		try {
			// 1, 2
			con = pool.getConnection();

			// 3 ps
			String sql = "select * from product";
			
			// 검색으로 진입 => 매개변수 keyword로 pdname에 해당하는 상품 출력
			if(keyword!=null && !keyword.isEmpty()) {
				sql += " where pdname like '%' || ? || '%'";
				sql += " order by pdcode";
				ps = con.prepareStatement(sql);
				
				ps.setString(1, keyword);
			}
			
			// 4 exec
			rs = ps.executeQuery();
			while(rs.next()) {
				int pdcode = rs.getInt("pdcode");
				String pdname = rs.getString("pdname");
				int comno = rs.getInt("comno");
				int price = rs.getInt("price");
				Timestamp regdate = rs.getTimestamp("regdate");
				String image = rs.getString("image");
				int category_code = rs.getInt("category_code");
						
				productVO vo = new productVO(pdcode, pdname, comno, price, regdate, image, category_code);
				
				list.add(vo);
			}
			System.out.println("제품목록 결과 list.size = " + list.size() +", keyword = " + keyword);
			
			return list;			
		}finally {
			pool.dbClose(rs, ps, con);
		}
	}
	
	public productVO selectBypdCode(String p_pdcode) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		productVO vo = null;
		
		try {
			con=pool.getConnection();
			
			String sql = "select * from product where pdcode=?";
			ps=con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(p_pdcode));
			
			rs=ps.executeQuery();
			if(rs.next()) {
				int pdcode = rs.getInt("pdcode");
				String pdname = rs.getString("pdname");
				int comno = rs.getInt("comno");
				int price = rs.getInt("price");
				Timestamp regdate = rs.getTimestamp("regdate");
				String image = rs.getString("image");
				int category_code = rs.getInt("category_code");
				
				vo = new productVO(pdcode, pdname, comno, price, regdate, image, category_code);
			}
			
			
			System.out.println("상품 조회 결과 vo="+ vo
				+", 매개변수 pdcode=" + p_pdcode);
			
			return vo;
		}finally {
			pool.dbClose(rs, ps, con);
		}
	}
	
}
