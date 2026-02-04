package AccountBook;

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
		int id = dao.count()+1;
		dao.insert(new AccountBook(id, type, amount, category, date));
	}
	
	public void findAll() {
		for(AccountBook a : dao.findAll()) System.out.println(a);
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
	
	public void Idcleanner() {
		dao.Idcleanner();
	}
}
