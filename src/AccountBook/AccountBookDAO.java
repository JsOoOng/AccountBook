
package AccountBook;

import java.util.List;

public interface AccountBookDAO {
	public int insert(AccountBook ab);
	public void view_date(String date, String type);
	public void view_category(String category, String type);
	public AccountBook findById(int id);
    public int delete(int id);	
    public int update(AccountBook ab);
    public List<AccountBook> findAll();
	public int count();
}


