package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.BookDTO;
import dto.CheckoutDTO;
import utils.DBHelper;

public class CheckoutDAO implements ICheckoutDAO {

	private Connection conn;
	
	public CheckoutDAO() {
		DBHelper dbMan = new DBHelper();
		try {
			dbMan.dbOpen();
			conn = dbMan.DB_con;
			System.out.println(">>> DB 연결 완료 <<<");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int insert(CheckoutDTO checkoutDTO) {
        int resultCount = 0;
        String query = "INSERT INTO checkout (id, user_id, book_id, checkout_date, is_return) VALUES (checkout_seq.nextval, ?, ?, CURRENT_DATE, 0)";
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, checkoutDTO.getUserId());
            pstmt.setInt(2, checkoutDTO.getBookId());
            resultCount = pstmt.executeUpdate();

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

    @Override
    public ArrayList<CheckoutDTO> select(String userId) {
        ArrayList<CheckoutDTO> resultList = new ArrayList<>();
        String sql = "SELECT c.*, b.name, b.writer, b.publisher, b.image, b.category_id FROM checkout c INNER JOIN book b ON c.book_id = b.id WHERE c.user_id = ? ORDER BY c.id DESC";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CheckoutDTO dto = new CheckoutDTO();
                dto.setId(rs.getInt("id"));
                dto.setUserId(rs.getString("user_id"));
                dto.setBookId(rs.getInt("book_id"));
                dto.setCheckoutDate(rs.getString("checkout_date"));
                dto.setIsReturn(rs.getBoolean("is_return"));
                dto.setName(rs.getString("name"));
                dto.setWriter(rs.getString("writer"));
                dto.setPublisher(rs.getString("publisher"));
                dto.setImage(rs.getString("image"));
                dto.setCategoryId(rs.getInt("category_id"));
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

    @Override
    public ArrayList<CheckoutDTO> select(String userId, int bookId) {
        ArrayList<CheckoutDTO> resultList = new ArrayList<>();
        String sql = "SELECT c.*, b.name, b.writer, b.publisher, b.image, b.category_id FROM checkout c INNER JOIN book b ON c.book_id = b.id WHERE c.user_id = ? AND c.book_id = ? ORDER BY c.id DESC";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setInt(2, bookId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CheckoutDTO dto = new CheckoutDTO();
                dto.setId(rs.getInt("id"));
                dto.setUserId(rs.getString("user_id"));
                dto.setBookId(rs.getInt("book_id"));
                dto.setCheckoutDate(rs.getString("checkout_date"));
                dto.setIsReturn(rs.getBoolean("is_return"));
                dto.setName(rs.getString("name"));
                dto.setWriter(rs.getString("writer"));
                dto.setPublisher(rs.getString("publisher"));
                dto.setImage(rs.getString("image"));
                dto.setCategoryId(rs.getInt("category_id"));
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

    @Override
    public int update(int id) {
        int resultCount = 0;
        String query = "UPDATE checkout SET is_return = 1 WHERE id = ?";
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            resultCount = pstmt.executeUpdate();

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

    @Override
    public CheckoutDTO select(int id) {
        CheckoutDTO resultDto = null;
        String sql = "SELECT * FROM checkout WHERE id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                resultDto = new CheckoutDTO();
                resultDto.setId(rs.getInt("id"));
                resultDto.setUserId(rs.getString("user_id"));
                resultDto.setBookId(rs.getInt("book_id"));
                resultDto.setCheckoutDate(rs.getString("checkout_date"));
                resultDto.setIsReturn(rs.getBoolean("is_return"));
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
        return resultDto;
    }


}
