# [Java_Lab]

## Week 01

**[Lab 1] 커피 가격 계산**

```java
package week02;

public class TestMain {
    public static void lab01() {
        final int COFFEE_PRICE = 100;
        final int MILK_PRICE = 50;
        final int WATER_PRICE = 10;
        
        int coffee = 5;
        int milk = 2;
        int water = 1;

        int total = (coffee * COFFEE_PRICE) + (milk * MILK_PRICE) + (water * WATER_PRICE);
        
        System.out.println("***** 주문내역 *****");
        System.out.println("커피 : " + coffee + "잔 " + (coffee*COFFEE_PRICE) + "원");
        System.out.println("우유 : " + milk + "잔 " + (milk*MILK_PRICE) + "원");
        System.out.println("물 : " + water + "잔 " + (water*WATER_PRICE) + "원");
        System.out.println("총 지불금액 : " + total + "원");
    }
}

```

**[Lab 2] 강제 형변환과 데이터 손실**

```java
public static void lab02() {
    byte b;
    int i = 414;
    float f = 123.456f;

    b = (byte) i;
    System.out.println("int 414를 byte로 변환 : " + b);
    i = (int) f;
    System.out.println("float 123.456을 int로 변환 : " + i);
    b = (byte) f;
    System.out.println("float 123.456을 byte로 변환 : " + b);
}

```

**[Lab 3] Scanner 입력과 버퍼 처리**

```java
public static void lab03() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("학번 : ");
    String sid = scanner.next();
    scanner.nextLine(); // 버퍼 비우기
    
    System.out.print("이름 : ");
    String sname = scanner.nextLine();
    
    System.out.print("나이 : ");
    int sage = scanner.nextInt();
    scanner.nextLine(); // 버퍼 비우기
    
    System.out.print("주소 : ");
    String sadd = scanner.nextLine();

    System.out.println("학번: " + sid);
    System.out.println("이름: " + sname);
    System.out.println("나이: " + sage);
    System.out.println("주소: " + sadd);
}

```
