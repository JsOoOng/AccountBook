package AccountBook;

import java.sql.*;

public class H2AccountBookDAO implements AccountBookDAO {

    private Connection conn;

    public H2AccountBookDAO() {
		try {
		Class.forName("org.h2.Driver");
		conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test","sa","");
		if(conn == null) {
			System.out.println("DB 연결을 다시 확인하세요!");
			System.out.println("프로그램을 종료합니다");
		}
		System.out.println("DB 연결 성공!");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
  

    @Override
    public AccountBook findById(int id) {
        
        try {
        	String sql = "SELECT * FROM AccountBook WHERE id = ?";
        	
        	PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            
            if (rs.next()) {
                AccountBook ab = new AccountBook();
                ab.setId(rs.getInt("id"));
                ab.setType(rs.getString("type"));
                ab.setAmount(rs.getInt("amount"));
                ab.setCategory(rs.getString("category"));
                ab.setDate(rs.getString("date"));
                return ab;
            }
            return null;
        } catch (Exception e) {
        	  e.printStackTrace();
              return null;
		} 

    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM AccountBook WHERE id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
           int result = ps.executeUpdate();
           ps.close();
           return result;
        } catch (Exception e) {
        	e.printStackTrace();
		}   
            return 0;
    }
}
