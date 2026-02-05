package AccountBook;

import java.util.List;
import java.util.Scanner;

public class AccountBookManager {
	
	int i;
	
	AccountBookDAO dao;
	Scanner scan = new Scanner(System.in);
	
	public AccountBookManager() {
		dao = new OracleAccountBookDAO();
	}
	
	public AccountBookManager(AccountBookDAO dao) {
		this.dao = dao;
	}
	
	public void insert(String type, int amount, String category, String date) {
		dao.insert(new AccountBook(0, type, amount, category, date));
	}
	
	public void findAll() {
		
		List<AccountBook> list = dao.findAll();
	    System.out.println("\n+------+------+-------+----------+------------+------------+------------+");
	    System.out.println("| 순번  | ID   | 타입   | 금액      | 카테고리     | 날짜\t   | 잔액\t|");
	    System.out.println("+------+------+-------+----------+------------+------------+------------+");
	    
	    int no = 1; // 사용자가 보기 편한 단순 순번
	    for(AccountBook a : list) {
	        // %-4d 등 형식을 지정하면 줄 맞춤이 훨씬 깔끔해집니다.
	    	System.out.printf("| %-4d | %-4d | %-4s | %-8d | %-9s | %-10s | %-11d|\n", 
                    no++, a.getId(), a.getType(), a.getAmount(), 
                    a.getCategory(), a.getDate(), a.getBalance());
	    }
	    System.out.println("+------+------+-------+----------+------------+------------+------------+");
	    System.out.println("※ 수정/삭제 시에는 오른쪽의 'ID' 번호를 입력하세요.");
	}

	public void view(String select, String type) {
		String comment;
		
		if(select.equals("날짜")) {
			System.out.println("날짜를 입력하세요 : (yyyy-mm-dd)");
			comment = scan.next();
			dao.view_date(comment, type);
		} else if(select.equals("카테고리")) {
			System.out.println("카테고리를 입력하세요(식사 여가 교통 쇼핑 의료 기타): ");
			comment = scan.next();
			dao.view_category(comment, type);
		}
	}
	
	public boolean isExist(int id) {
		if(dao.findById(id)==null) {
			return false;
		}
		return true;
	}

	public void update(int id, AccountBook a) {
		dao.update(id, a);
	}

	public void delete(int id) {
		dao.delete(id);
	}
}
