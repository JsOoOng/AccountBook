package AccountBook;

import java.io.IOException;

public class AccountBookMain {

	public static void main(String[] args) throws IOException {
		new AccountBookProgram(new OracleAccountBookDAO());
	}

}
