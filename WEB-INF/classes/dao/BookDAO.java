package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.BookDTO;
import utils.DBHelper;

public class BookDAO implements IBookDAO {

	private Connection conn;
	
	public BookDAO() {
		DBHelper dbMan = new DBHelper();
		try {
			dbMan.dbOpen();
			conn = dbMan.DB_con;
			System.out.println(">>> DB 연결 완료 <<<");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// 전체 조회
	@Override
	public ArrayList<BookDTO> select() {
		ArrayList<BookDTO> resultList = new ArrayList<>();
		String sql = " SELECT * FROM book b INNER JOIN category c ON b.category_id = c.id ORDER BY b.id";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BookDTO dto = new BookDTO();
				dto.setId(rs.getInt("ID"));
				dto.setName(rs.getString("NAME"));
				dto.setWriter(rs.getString("WRITER"));
				dto.setPublisher(rs.getString("PUBLISHER"));
				dto.setImage(rs.getString("IMAGE"));
				dto.setDescription(rs.getString("DESCRIPTION"));
				dto.setCheckOutCount(rs.getInt("CHECK_OUT_COUNT"));
				dto.setAvailable(rs.getBoolean("IS_AVAILABLE"));
				dto.setCategoryId(rs.getInt("CATEGORY_ID"));
				dto.setCategoryName(rs.getString("NAME"));
				resultList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultList;
	}

	// 하나의 조건에 대해 조회
	@Override
	public ArrayList<BookDTO> select(String column, int value) {
		ArrayList<BookDTO> resultList = new ArrayList<>();
		String sql = " SELECT * FROM (SELECT b.*, c.name AS category_name, ROWNUM AS rnum FROM book b INNER JOIN category c ON b.category_id = c.id ORDER BY b.check_out_count DESC) WHERE rnum <= ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, value);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				 System.out.println("check_out_count: " + rs.getInt("check_out_count"));
				BookDTO dto = new BookDTO();
				dto.setId(rs.getInt("ID"));
				dto.setName(rs.getString("NAME"));
				dto.setWriter(rs.getString("WRITER"));
				dto.setPublisher(rs.getString("PUBLISHER"));
				dto.setImage(rs.getString("IMAGE"));
				dto.setDescription(rs.getString("DESCRIPTION"));
				dto.setCheckOutCount(rs.getInt("CHECKOUT_COUNT"));
				dto.setAvailable(rs.getBoolean("IS_AVAILABLE"));
				dto.setCategoryId(rs.getInt("CATEGORY_ID"));
				dto.setCategoryName(rs.getString("NAME"));
				resultList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultList;
	}

	// 검색어에 대한 조회
	@Override
	public ArrayList<BookDTO> select(String search) {
		ArrayList<BookDTO> resultList = new ArrayList<>();
		String sql = "SELECT * FROM (SELECT b.*, c.name AS category_name, ROWNUM AS rnum FROM book b INNER JOIN category c ON b.category_id = c.id WHERE b.name LIKE ? ORDER BY b.name) WHERE rnum <= 10";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + search + "%");
			rs = pstmt.executeQuery();
			System.out.println(search);
			
			while (rs.next()) {
				BookDTO dto = new BookDTO();
				dto.setId(rs.getInt("ID"));
				dto.setName(rs.getString("NAME"));
				dto.setWriter(rs.getString("WRITER"));
				dto.setPublisher(rs.getString("PUBLISHER"));
				dto.setImage(rs.getString("IMAGE"));
				dto.setDescription(rs.getString("DESCRIPTION"));
				dto.setCheckOutCount(rs.getInt("CHECK_OUT_COUNT"));
				dto.setAvailable(rs.getBoolean("IS_AVAILABLE"));
				dto.setCategoryId(rs.getInt("CATEGORY_ID"));
				dto.setCategoryName(rs.getString("NAME"));
				resultList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
		        if (rs != null) {
		            rs.close();
		        }
		        if (pstmt != null) {
		            pstmt.close();
		        }
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultList;
	}

	// 전체 조회 - 정렬
	@Override
	public ArrayList<BookDTO> selectOrder(String orderColumn) {
		 ArrayList<BookDTO> resultList = new ArrayList<>();
		    String sql = "SELECT * FROM (SELECT b.*, c.name AS category_name, ROWNUM AS rnum FROM book b INNER JOIN category c ON b.category_id = c.id ORDER BY " + orderColumn + ") WHERE rnum <= ?";
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;
		    try {
		        pstmt = conn.prepareStatement(sql);
		        pstmt.setInt(1, 20);  // MAX_ROW_NUM은 최대 허용하는 행의 수를 나타내는 상수로 바꿔주어야 해

		        rs = pstmt.executeQuery();

		        while (rs.next()) {
		            BookDTO dto = new BookDTO();
		            dto.setId(rs.getInt("ID"));
		            dto.setName(rs.getString("NAME"));
		            dto.setWriter(rs.getString("WRITER"));
		            dto.setPublisher(rs.getString("PUBLISHER"));
		            dto.setImage(rs.getString("IMAGE"));
		            dto.setDescription(rs.getString("DESCRIPTION"));
		            dto.setCheckOutCount(rs.getInt("CHECK_OUT_COUNT"));
		            dto.setAvailable(rs.getBoolean("IS_AVAILABLE"));
		            dto.setCategoryId(rs.getInt("CATEGORY_ID"));
		            dto.setCategoryName(rs.getString("name"));
		            resultList.add(dto);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            rs.close();
		            pstmt.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    return resultList;
	}

	// 하나의 조건에 대한 조회 - 정렬
	@Override
	public ArrayList<BookDTO> selectOrder(String column, int value, String orderColumn) {
		ArrayList<BookDTO> resultList = new ArrayList<>();
		String sql = "SELECT * FROM book b INNER JOIN category c ON b.category_id = c.id WHERE " + column + " = ? ORDER BY " + orderColumn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, value);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BookDTO dto = new BookDTO();
				dto.setId(rs.getInt("ID"));
				dto.setName(rs.getString("NAME"));
				dto.setWriter(rs.getString("WRITER"));
				dto.setPublisher(rs.getString("PUBLISHER"));
				dto.setImage(rs.getString("IMAGE"));
				dto.setDescription(rs.getString("DESCRIPTION"));
				dto.setCheckOutCount(rs.getInt("CHECK_OUT_COUNT"));
				dto.setAvailable(rs.getBoolean("IS_AVAILABLE"));
				dto.setCategoryId(rs.getInt("CATEGORY_ID"));
				dto.setCategoryName(rs.getString("NAME"));
				resultList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultList;
	}

	// 대여량 갱신
	@Override
	public int update(int checkOutCount, int bookId) {
		int resultCount = 0;
		String query = " UPDATE book SET check_out_count = ? WHERE id = ? ";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, checkOutCount);
			pstmt.setInt(2, bookId);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return resultCount;
	}
	
	// 대여 가능 여부 갱신
	@Override
	public int update(boolean isAvailable, int bookId) {
		int resultCount = 0;
		String query = " UPDATE book SET is_available = ? WHERE id = ? ";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, isAvailable);
			pstmt.setInt(2, bookId);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return resultCount;
	}

	// 대여량 순 & LIMIT n개
	@Override
	public ArrayList<BookDTO> select(int count) {
		ArrayList<BookDTO> resultList = new ArrayList<>();
	    String sql = "SELECT * FROM (SELECT b.*, c.name AS category_name, ROWNUM AS rnum FROM book b INNER JOIN category c ON b.category_id = c.id ORDER BY b.check_out_count DESC) WHERE rnum <= ?";
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, count);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            BookDTO dto = new BookDTO();
	            dto.setId(rs.getInt("ID"));
				dto.setName(rs.getString("NAME"));
				dto.setWriter(rs.getString("WRITER"));
				dto.setPublisher(rs.getString("PUBLISHER"));
				dto.setImage(rs.getString("IMAGE"));
				dto.setDescription(rs.getString("DESCRIPTION"));
				dto.setCheckOutCount(rs.getInt("CHECK_OUT_COUNT"));
				dto.setAvailable(rs.getBoolean("IS_AVAILABLE"));
	            dto.setCategoryId(rs.getInt("CATEGORY_ID"));
	            dto.setCategoryName(rs.getString("NAME"));
	            resultList.add(dto);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (pstmt != null) {
	                pstmt.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return resultList;
	}


}
