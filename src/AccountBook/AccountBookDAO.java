package AccountBook;

public interface AccountBookDAO {
    AccountBook findById(int id);
    public int delete(int id);	
}

