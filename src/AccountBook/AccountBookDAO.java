
package AccountBook;

public interface AccountBookDAO {
	public int insert(AccountBook ab);
	public void view_date(String date, String type);
	public void view_category(String category, String type);
}

