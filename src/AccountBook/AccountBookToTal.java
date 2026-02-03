package AccountBook;

import java.util.List;
import java.text.DecimalFormat;
	
public class AccountBookToTal {
	// 이 코드의 작동원리는 가계부 목록(List<AccountBook>)을 받아서
	//(전체)수입,지출 / 현재 자산 / 합계 / 평균 을 계산하고 출력하는 역할만 함.

	private List<AccountBook> list;
	
	public AccountBookToTal(List<AccountBook> list) {
	    this.list = list;
	}

	// 숫자 가독성 개선
	private String formatMoney(int amount) {
	    DecimalFormat df = new DecimalFormat("#,###");
	    return df.format(amount)+ "원";
	}
	
	//전체 수입 / 지출 합계
	// type 이 "income"인 데이터만 더함
	public int getTotalIncome() {
	    int sum = 0;
	    for (AccountBook ab : list) {
	        if ("income".equals(ab.getType())) {
	            sum += ab.getAmount();
	        }
	    }
	    return sum;
	}
	// type 이 "expense"인 데이터만 더함
	public int getTotalExpense() {
	    int sum = 0;
	    for (AccountBook ab : list) {
	        if ("expense".equals(ab.getType())) {
	            sum += ab.getAmount();
	        }
	    }
	    return sum;
	}
	
	// 현재 자산 (수입 - 지출)
	public int getBalance() {
	    return getTotalIncome() - getTotalExpense();
	}
	
	//전체 출력 메서드
	public void printSummary() {
		if (list == null || list.isEmpty()) {
		    System.out.println("내역이 없습니다.");
		    return;
		}
	    int income = getTotalIncome();
	    int expense = getTotalExpense();
	}
}