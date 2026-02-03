
package AccountBook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class H2AccountBookDAO implements AccountBookDAO{
	
	Connection conn;
	private int i = 1;
	
	public H2AccountBookDAO() {
		try {
			Class.forName("org.h2.Driver");
			String url = "jdbc:h2:tcp://localhost/~/test";
			conn = DriverManager.getConnection(url,"su","");
				
			if(conn==null) {
				System.out.println("DB 연결 실패");
				System.out.println("시스템 종료");
			}
			System.out.println("DB 연결 성공");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void view_date(String date, String type) {
		List<AccountBook> list = new ArrayList<>(); // 여러 건을 담을 리스트
	    String sql = "select * from accountbook where indate = ? and type = ?";
	    PreparedStatement ps = null;
	    ResultSet result = null;

	    try {
	        ps = conn.prepareStatement(sql);
	        ps.setString(1, date);
	        ps.setString(2, type);
	        result = ps.executeQuery();
	        
	        while(result.next()) {
	            int id = result.getInt("id");
	            int amount = result.getInt("amount");
	            String category = result.getString("category");
	            
	            // 리스트에 추가
	            list.add(new AccountBook(id, type, amount, category, date));
	            
	            System.out.println("id : " + id + "\t|type : " + type + "\t|amount : " + amount + "\t|cotegoty : " + category + "\t|date : " + date);
	        }

	        if(list.isEmpty()) {
	            System.out.println(date + " " + type + " 정보가 없습니다.");
	        }
	        
	    } catch(Exception e) { 
	        e.printStackTrace(); 
	    } finally {
	        // 리소스 해제는 반드시 마지막에! (자바 7 이상이면 try-with-resources 권장)
	        try { if(result != null) result.close(); } catch(Exception e) {}
	        try { if(ps != null) ps.close(); } catch(Exception e) {}
	    }
	}


	@Override
	public void view_category(String category, String type ) {
		List<AccountBook> list = new ArrayList<>(); // 여러 데이터를 담을 바구니
	    String sql = "select * from accountbook where category = ? and type = ?";
	    PreparedStatement ps = null;
	    ResultSet result = null;

	    try {
	        ps = conn.prepareStatement(sql);
	        ps.setString(1, category);
	        ps.setString(2, type);
	        result = ps.executeQuery();
	        
	        while(result.next()) {
	            int id = result.getInt("id");
	            int amount = result.getInt("amount");
	            String date = result.getString("indate");
	            
	            // 조회된 데이터를 리스트에 계속 추가
	            list.add(new AccountBook(id, type, amount, category, date));
	            System.out.println("id : " + id + "\t|type : " + type + "\t|amount : " + amount + "\t|cotegoty : " + category + "\t|date : " + date);
	        }

	        if(list.isEmpty()) {
	            System.out.println(category + " 카테고리에 해당되는 정보가 없슴다.");
	        }
	        
	    } catch(Exception e) { 
	        e.printStackTrace(); 
	    } finally {
	        // 통로 닫기는 '반드시' 작업이 다 끝난 여기서!
	        try { if(result != null) result.close(); } catch(Exception e) {}
	        try { if(ps != null) ps.close(); } catch(Exception e) {}
	    }
	}

	@Override
	public int insert(AccountBook ab) {
		try {
			String sql = "insert into accountbook values(?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			System.out.println(ps);
			ps.setInt(1, i);
			ps.setString(2, ab.getType());
			ps.setInt(3, ab.getAmount());
			ps.setString(4, ab.getCategory());
			ps.setString(5, ab.getDate());
			ps.executeUpdate();
			ps.close();
			i++;
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
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
           int result = ps.executeUpdate();
           ps.close();
           return result;
        }catch (Exception e) {
        	e.printStackTrace();
		}   
           return 0;
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
			System.out.println(result);
			ps.close();
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
    }
}
