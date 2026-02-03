package AccountBook;

import java.util.Iterator;
import java.util.List;

public class AccountBookDAOImpl implements AccountBookDAO {

    private List<AccountBook> list;

    public AccountBookDAOImpl(List<AccountBook> list) {
        this.list = list;
    }

    @Override
    public AccountBook findById(int id) {
        for (AccountBook ab : list) {
            if (ab.getId() == id) {
                return ab;
            }
        }
        return null; 
    }

    @Override
    public int delete(int id) {
        Iterator<AccountBook> iterator = list.iterator();

        while (iterator.hasNext()) {
            AccountBook ab = iterator.next();
            if (ab.getId() == id) {
                iterator.remove();
                return 1;
            }
        }

        return 0; 
    }
}
