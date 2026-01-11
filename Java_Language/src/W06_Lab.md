# Week 06

**[Lab 1] 은행 계좌 관리자**

* **내용:** `BankAccount2` 클래스와 이를 배열로 관리하는 `BankManager` 클래스 구현.

```java
package week06;
import java.util.Scanner;

public class BankManager {
    public String branchName;
    public final int SIZE;
    public BankAccount2[] bankAccount;
    public int count = 0;
    public static Scanner scanner = new Scanner(System.in);

    public BankManager(String branchName, int SIZE) {
        this.branchName = branchName;
        this.SIZE = SIZE;
        if (this.SIZE > 0)
            this.bankAccount = new BankAccount2[this.SIZE];
    }

    public void createAccount() {
        System.out.println("--------계좌 개설---------");
        System.out.print("이름 : ");
        String name = scanner.next();
        System.out.print("금액 : ");
        int amount = scanner.nextInt();
        if (this.count < this.SIZE) {
            bankAccount[this.count++] = new BankAccount2(name, amount);
        } else {
            System.out.println("더이상 계좌 개설 불가");
        }
    }
    
    // ... (입금, 이체, 계좌 찾기 메소드는 파일 내용 참조)
}

```

**[Lab 2] 티켓 관리 시스템**

* **내용:** `Ticket`을 상속받은 `GeneralTicket`과 `AdvanceTicket`을 구현.

```java
package week06_1;

public class TicketManager {
    private Ticket[] tickets;
    private int count = 0;
    private final int NUMBER;

    public TicketManager(String name, int NUMBER) {
        this.NUMBER = NUMBER;
        if(this.NUMBER > 0)
            tickets = new Ticket[this.NUMBER];
    }

    public void register(Ticket ticket){
        if (this.count < this.NUMBER)
            this.tickets[this.count++] = ticket;
    }

    // 다형성 활용: 신용카드 결제 여부에 따라 GeneralTicket만 출력
    public void showGeneralTicket(boolean payByCredit){
        for(Ticket ticket : tickets){
            if(ticket != null && ticket instanceof GeneralTicket){
                GeneralTicket t = (GeneralTicket) ticket; // 다운캐스팅
                if (t.isPayByCredit() == payByCredit)
                    System.out.println(t);
            }
        }
    }
}

```

**[Lab 3] 스마트 홈 가전 제어**

* **내용:** `HomeAppliance`를 상속받은 냉장고, TV 등의 `menu()` 메소드를 오버라이딩하여 제어.

```java
package week06_2;
import java.util.Scanner;

public class Home {
    private HomeAppliance[] ha;
    private int count = 0;

    public Home(int capacity) {
        ha = new HomeAppliance[capacity];
    }

    public void butHA(HomeAppliance appliance){
        if (this.count < ha.length) {
            ha[this.count++] = appliance;
            System.out.println(appliance.getHaName() + " 배치 완료.");
        }
    }

    public void open(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("제어할 가전제품을 선택하세요!!");
            for (int i=0; i<count; i++){
                System.out.println((i+1) + ")" + ha[i].getHaName());
            }
            System.out.print("선택: ");
            int index = scanner.nextInt();
            
            if (index >= 1 && index <= count){
                ha[index-1].menu(); // 동적 바인딩으로 실제 객체의 menu() 실행
            } else {
                break;
            }
        }
    }
}

```
