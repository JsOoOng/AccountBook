

package AccountBook;

//5명 기능 설계
//기능을 가지고 메뉴를 작성(문서로 작성하면서 각각의 기능의 입력값과 출력값을 상세히 명세)
//수입 금액, 지출 금액, 잔여액, 카테고리별 지출합계, 카테고리별 제한금액
//인터페이스를 활용해 각자 맡은 코드 나눠서 작성
//나눠서 작성한 것을 결합(인터페이스 상속)
//팀장의 역할은 모든 프로그램을 합쳐서 조율
//Oracle, H2에 대해 처리

public class AccountBook {
	private int id; //아이디 : 중복 X
	private int amount; //금액 : 사용액 
	private int balance; //잔여액 : 남은 돈
	
	private String type; //수입 또는 지출 
	private String category; //분류 : 식사 여가 교통 쇼핑 의료 기타
	private String date; //현재 날짜
	//private java.util.Date date; //사용날짜 or java.sql.Date
	
	public AccountBook() {}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public AccountBook(int id, String type, int amount, String category, String date) {
		super();
		this.id = id;
		this.amount = amount;
		this.type = type;
		this.category = category;
		this.date = date;
	}
	public AccountBook(String type, int amount, String category, String date) {
		this.amount = amount;
		this.type = type;
		this.category = category;
		this.date = date;
	}
	@Override
	public String toString() {
		return "AccountBook [id=" + id +  ", type=" + type + ", amount=" + amount +
				", category=" + category + ", date=" + date + "]";
	}
	
}

