package AccountBook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OracleAccountBookDAO implements AccountBookDAO {

    Connection conn;
    private int i = 1;

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
    public int insert(AccountBook ab) {
        try {
            String sql = "insert into accountbook values(?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, i);
            ps.setString(2, ab.getType());
            ps.setInt(3, ab.getAmount());
            ps.setString(4, ab.getCategory());
            ps.setString(5, ab.getDate());
            int result = ps.executeUpdate();
            ps.close();
            i++;
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
}