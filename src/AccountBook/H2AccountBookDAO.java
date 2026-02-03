package AccountBook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class H2AccountBookDAO implements AccountBookDAO{
	
	Connection conn;
	
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
}
