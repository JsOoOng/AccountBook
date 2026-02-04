package AccountBook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class OracleAccountBookDAO implements AccountBookDAO {

    Connection conn;
    public OracleAccountBookDAO() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@172.16.15.55:1521:xe";
            conn = DriverManager.getConnection(url, "system", "1234");

            if (conn == null) {
                System.out.println("DB 연결 실패");
            } else {
                System.out.println("DB 연결 성공");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void view_date(String date, String type) {
        List<AccountBook> list = new ArrayList<>();
        String sql = "select * from accountbook where indate = ? and type = ?";
        PreparedStatement ps = null;
        ResultSet result = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, date);
            ps.setString(2, type);
            result = ps.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                int amount = result.getInt("amount");
                String category = result.getString("category");
                list.add(new AccountBook(id, type, amount, category, date));
                System.out.println("id : " + id + "\t|type : " + type + "\t|amount : " + amount + "\t|category : " + category + "\t|date : " + date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (result != null) result.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
        }
    }

    @Override
    public void view_category(String category, String type) {
        List<AccountBook> list = new ArrayList<>();
        String sql = "select * from accountbook where category = ? and type = ?";
        PreparedStatement ps = null;
        ResultSet result = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, category);
            ps.setString(2, type);
            result = ps.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                int amount = result.getInt("amount");
                String date = result.getString("indate");
                list.add(new AccountBook(id, type, amount, category, date));
                System.out.println("id : " + id + "\t|타입 : " + type + "\t|금액 : " + amount + "\t|카테고리 : " + category + "\t|날짜 : " + date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (result != null) result.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
        }
    }

    @Override
    public int insert(AccountBook ab) {
        try {
        	
            String sql = "insert into accountbook values(seq_account_id.NEXTVAL,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ab.getType());
            ps.setInt(2, ab.getAmount());
            ps.setString(3, ab.getCategory());
            ps.setString(4, ab.getDate());
            int result = ps.executeUpdate();
            ps.close();
          
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    } // <- 여기서 insert 메서드가 끝나야 합니다.

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
                ab.setDate(rs.getString("indate")); // 컬럼명 확인 필요 (indate?)
                rs.close(); ps.close();
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
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    @Override
	public int update(int id, AccountBook ab) {
		try {
			//기존에 값에 새로운 값을 교체하는 작업
			AccountBook new_ab = findById(id);
			if(!ab.getType().equals(""))new_ab.setType(ab.getType());
			if(ab.getAmount()!=0) {new_ab.setAmount(ab.getAmount());}
			if(!ab.getCategory().equals(""))new_ab.setCategory(ab.getCategory());
			if(!ab.getDate().equals(""))new_ab.setDate(ab.getDate());
			System.out.println();
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
	public List<AccountBook> findAll() {
		try {

	    	String sql = "select * from accountbook";
	    	PreparedStatement ps = conn.prepareStatement(sql);

	    	ResultSet rs = ps.executeQuery();
	    	
	    	List<AccountBook> list = new ArrayList<AccountBook>(); //빈리스트 만들기
	    	while(rs.next()) {
	    		
	    		
	    		
	    		int id = rs.getInt("id");
	    		String type = rs.getString("type");
	    		int amount = rs.getInt("amount");
	    		String category = rs.getString("category");
	    		String date = rs.getString("indate");
	    		
	    		AccountBook ab= new AccountBook(id, type, amount, category, date);
	    		list.add(ab);
	    	}
	    	rs.close();
	    	ps.close();
	    	return list;
	    	
	    	}catch (Exception e) {
	    		e.printStackTrace();
	    		return null;
	    	}
	}
	
	@Override
	public int count() {
		try {
			String sql = "select count(*) as cnt from accountbook";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet result = ps.executeQuery();
			
			if(result.next()) {
				int cnt = result.getInt("cnt");
				result.close(); ps.close();
				return cnt;
			}
			result.close();
			ps.close();
			return -1;
			
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}