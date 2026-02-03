package AccountBook;

import java.util.List;

public interface AccountDAO {
	
	/*
	 업데이트 
	1) (id, category, amount, type) null값일시 변경X 
	 */
	
	public AccountBook findById(int id);
	public int update(AccountBook ab);
	
	/*
	 전체합계
	 (전체)수입,지출 / 현재 자산 을 계산하고 출력
	 */
	public List<AccountBook> findAll();
}