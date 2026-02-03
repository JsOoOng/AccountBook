package AccountBook;

import java.util.Date;
// 5명이 모여서 설계 (기능)
// 기능을 가지고 메뉴를 작성 ( 문서로 작성하면서 각각의 기능의 입력값과 출력값을 상세히 명세 )
// 인터페이스를 이용하여 각자 맡은 코드를 나눠서 작성, 나눠서 작성한 것을 다시 결합 ( 인터페이스 상속 )
// 팀장의 역할은 모든 프로그램을 합쳐서 조율
// Oracle, h2에 대해 DAO 처리


public class AccountBook {
	private int id; // 아이디
	private String type; // 수입 또는 지출
	private int amount; // 사용한 금액 
	private String category; // 분류
	private String date;
	//private java.util.Date date; // 사용 날짜 or java.sql.Date
	public AccountBook() {
		// TODO Auto-generated constructor stub
	}
	public AccountBook(int id, String type, int amount, String category, String date) {
		super();
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.category = category;
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "AccountBook [id=" + id + ", type=" + type + ", amount=" + amount + ", category=" + category + ", date="
				+ date + "]";
	}
	
	
	
}


