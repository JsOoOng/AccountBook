package AccountBook;

import java.io.IOException;
import java.util.Scanner;

public class AccountBookProgram {
	
	AccountBookManager am;
	Scanner scan = new Scanner(System.in);
	int id; //아이디 : 중복 X
	int amount; //금액 : 사용액 
	int balance; //잔여액 : 남은 돈
	String type; //수입 또는 지출 
	String category; //분류 : 식사 여가 교통 쇼핑 의료 기타
	String date;
	
	public AccountBookProgram(AccountBookDAO dao) throws IOException {
		am = new AccountBookManager(dao);
		
		while(true) {
			switch(menu()) {
				case 1:	insert(); break;
				case 2: views(); break;
				case 3: view();	break;
				case 4: update(); break;
				case 5: delete(); break;
				case 0: 
					System.out.println("프로그램을 종료합니다.");
					System.exit(0);
				default : System.out.println("제대로 입력해주세요.");
			}
		}//while end
	}//main end
	
	//메뉴
	public int menu() {
		System.out.println("+===================+");
		System.out.println("|     가계부 관리	    |");
		System.out.println("+===================+");
		System.out.println("|  1. 가계부 입력	    |");
		System.out.println("|  2. 가계부 전체출력  |");
		System.out.println("|  3. 가계부 찾기	    |");
		System.out.println("|  4. 가계부 수정	    |");
		System.out.println("|  5. 가계부 삭제	    |");
		System.out.println("|  0. 프로그램 종료    |");
		System.out.println("+===================+");
		return scan.nextInt();
	} //menu end
	
	//입력
	public void insert() {
	
		System.out.println("가계부에 입력할 타입(수입 / 지출) : ");
		type = scan.next();
		System.out.println("가계부에 입력할 금액 : ");
		amount = scan.nextInt();
		System.out.println("카테고리를 입력하세요(식사 여가 교통 쇼핑 의료 기타) : ");
		category = scan.next();
		System.out.println("날짜를 입력하세요(yyyy-mm-dd) : ");
		date = scan.next();
		am.insert(type, amount, category, date);
	} //insert end
	
	//전체출력
	public void views() throws IOException {
		
		am.findAll();
		System.in.read();
	} //views end
	
	//선택출력
	private void view() {
		System.out.println("찾을 방법을 선택하세요 (날짜 / 카테고리): ");
		String select = scan.next();
		System.out.println("찾을 타입을 선택하세요 (수입 / 지출): ");
		String type = scan.next();
		am.view(select, type);
		
	} //view end

	//수정
	private void update() {
		if(scan.hasNextLine()) {
			 scan.nextLine();
		}
		System.out.println("수정할 가계부의 아이디를 입력하세요 : ");
		id=scan.nextInt(); 
		System.out.println("test");
		if(scan.hasNextLine()) {
			scan.nextLine();
		}
		
		if(am.isExist(id)) {
			System.out.println("변경할 타입(수입 / 지출)을 입력하세요[변경사항이 없으면 enter]");
			type=scan.nextLine();				 
			System.out.println("변경할 금액을 입력하세요[변경사항이 없으면 0]");
			amount=scan.nextInt();		
			if(scan.hasNextLine()) {
				scan.nextLine();
			}
			System.out.println("변경할 카테고리(식사 여가 교통 쇼핑 의료 기타)를 입력하세요[변경사항이 없으면 enter]");
			category=scan.nextLine();
			System.out.println("변경할 날짜(yyyy-mm-dd)를 입력하세요[변경사항이 없으면 enter]");
			date=scan.nextLine();
			
			am.update(id, new AccountBook(type, amount, category, date));
		} else {
			System.out.println("수정할 가계부가 존재하지않습니다.");
		}
	}//update end

	//삭제
	private void delete() {
		System.out.println("삭제할 아이디를 입력하세요.");
		id=scan.nextInt();
		if(am.isExist(id)) {
			am.delete(id);
		}else {
			System.out.println("삭제할 정보가 없습니다.");
		}
	} //delete end
	

	
}
