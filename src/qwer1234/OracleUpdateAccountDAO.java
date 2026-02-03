package qwer1234;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;




public class OracleUpdateAccountDAO implements AccountDAO {
	
	Connection conn;
	
	public OracleUpdateAccountDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.15.54:1521:xe", "system", "1234");
			if(conn==null) {
				System.out.println("DB연결을 다시 확인하세요.");
				System.out.println("프로그램을 종료합니다.");
			}
			System.out.println("DB연결 성공!!");
			}catch(Exception e) {
				e.printStackTrace();
			}
	}

	@Override
	public int update(AccountBook ab) {
		try {
			//기존에 값에 새로운 값을 교체하는 작업
			AccountBook new_ab = findById(ab.getId());
			if(ab.getType()!=null) {
				new_ab.setType(ab.getType());
			}
			if(ab.getAmount()!=0) {
				new_ab.setAmount(ab.getAmount());
			}
			if(ab.getCategory()!=null) {
				new_ab.setCategory(ab.getCategory());
			}
			
			if(ab.getDate()!=null) {
				new_ab.setDate(ab.getDate());
			}
				
			String sql = "update accountbook set type=?, amount=?, category=?, indate=? where id=?";
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, new_ab.getType());
			
			ps.setInt(2, new_ab.getAmount());
			
			ps.setString(3, new_ab.getCategory());
			
			ps.setString(4, new_ab.getDate());
			
			ps.setInt(5, new_ab.getId());
			
			int result = ps.executeUpdate(); //Statement 객체와 차이점은 sql을 여기서 실행하는것이 아님
			
			ps.close();
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	@Override
	public AccountBook findById(int id) {
		try {
			String sql = "select * from accountbook where id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				String type = rs.getString("type");
				int amount = rs.getInt("amount");
				String category = rs.getString("category");
				String date = rs.getString("indate");
				AccountBook ab = new AccountBook(id, type, amount, category, date);
				rs.close();
				ps.close();
				return ab;
			}
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
}
