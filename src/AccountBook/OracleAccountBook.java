package AccountBook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class OracleAccountBook implements AccountBookDAO{
	Connection conn;
	private int i = 1;
	public OracleAccountBook() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager
			.getConnection("jdbc:oracle:thin:@172.16.15.53:1521:xe", "system", "1234");
			if(conn==null) {
				System.out.println("DB연결을 다시 확인하시오.");
				System.out.println("프로그램 종료 합니다.");
			}
			System.out.println("DB연결 성공!");
		} catch (Exception e) {
			e.printStackTrace();
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
			int rs = ps.executeUpdate();
			ps.close();
			i++;
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
