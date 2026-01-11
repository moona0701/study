# Week 02

**[Lab 4] 커피 주문하기**
* **설명:** 향상된 Switch문 사용

```java
public static void lab04() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("무슨 커피 드릴까요? ");
    String coffeMenu = scanner.next();

    switch (coffeMenu) {
        case "Americano", "Espresso" -> System.out.println(coffeMenu + "는 2500원입니다.");
        case "Cappuccino", "CafeLatte" -> System.out.println(coffeMenu + "는 3500원입니다.");
        default -> System.out.println("판매하는 제품이 아닙니다.");
    }
}

```

**[Lab 5] 가위바위보 게임**

```java
public static void lab05() {
    System.out.println("가위(0), 바위(1), 보(2)중에 하나를 입력하세요.");
    System.out.println("3판 2선승제로 진행됩니다. 먼저 2승을 하는 사람이 승리합니다!");

    int i = 0, j = 0; // i: 사용자 승리 횟수, j: 컴퓨터 승리 횟수

    while (i < 2 && j < 2) {
        System.out.println("--- 현재 스코어 ---");
        System.out.println("사용자: " + i + "승, 컴퓨터: " + j + "승");

        Scanner scanner = new Scanner(System.in);
        System.out.print("입력 : ");
        int userInt = scanner.nextInt();

        Random r = new Random();
        int computerInt = r.nextInt(3);

        // 컴퓨터가 무엇을 냈는지 확인용 출력 (님 코드엔 없었지만 추가하면 좋음)
        // System.out.println("컴퓨터: " + computerInt);

        if (userInt == computerInt) {
            System.out.println("비겼습니다.");
        } else if ((userInt == 0 && computerInt == 2) || 
                   (userInt == 1 && computerInt == 0) || 
                   (userInt == 2 && computerInt == 1)) {
            System.out.println("사용자가 이겼습니다.");
            i++;
        } else {
            System.out.println("컴퓨터가 이겼습니다.");
            j++;
        }
    }

    System.out.println("--- 최종 결과 ---");
    if (i < j) {
        System.out.println("최종 승리자는 컴퓨터입니다.");
    } else {
        System.out.println("최종 승리자는 사용자입니다.");
    }
}
```

