# Week 03

**[Lab 1] 배열에서 최댓값 찾기**

```java
package week03;

import java.util.Scanner;

public class TestMain {
    public static void example01() {
        Scanner scanner = new Scanner(System.in);
        int[] intArray = new int[5];
        int max = 0;
        
        System.out.println("양수 5개 입력: ");
        for (int i=0; i<5; i++){
            intArray[i] = scanner.nextInt();
            if (intArray[i] > max) max = intArray[i];
        }
        System.out.println("가장 큰 수: " + max);
    }
}

```

**[Lab 2] 커피 메뉴 주문 (Switch)**

```java
public static void lab01() {
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

**[Lab 3] 가위바위보 게임 (3판 2선승)**

* **내용:** 사용자와 컴퓨터가 가위바위보를 진행하며, 먼저 2승을 하는 쪽이 승리합니다.
* **코드 파일:** `task02.java`

```java
public static void lab02() {
    System.out.println("가위(0), 바위(1), 보(2)중에 하나를 입력하세요.");
    System.out.println("3판 2선승제로 진행됩니다. 먼저 2승을 하는 사람이 승리합니다!");

    int i = 0, j = 0; // i: 사용자승, j: 컴퓨터승

    while (i < 2 && j < 2) {
        System.out.println("--- 현재 스코어 ---");
        System.out.println("사용자: " + i + "승, 컴퓨터: " + j + "승");

        Scanner scanner = new Scanner(System.in);
        System.out.print("입력 : ");
        int userInt = scanner.nextInt();

        Random r = new Random();
        int computerInt = r.nextInt(3);

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
    if (i < j) System.out.println("최종 승리자는 컴퓨터입니다.");
    else System.out.println("최종 승리자는 사용자입니다.");
}

```

**[Lab 4] 로또 번호 생성기 (중복 제거 및 정렬)**

```java
public static void lab03() {
    int[] lotto = makeLotto();
    System.out.print("로또 번호 : ");
    for (int num : lotto) {
        System.out.print(num + " ");
    }
    System.out.println();
}

private static int[] makeLotto() {
    int[] lotto = new int[6];
    Random random = new Random();
    
    // 1. 번호 생성 및 중복 제거
    for (int i = 0; i < lotto.length; i++) {
        lotto[i] = random.nextInt(46) + 1;
        for (int j = 0; j < i; j++) {
            if (lotto[i] == lotto[j]) {
                i--; // 중복 발생 시 인덱스를 되돌려 다시 뽑음
                break;
            }
        }
    }
    
    // 2. 정렬 (Selection Sort 방식)
    for (int i = 0; i < lotto.length - 1; i++) {
        int minIndex = i;
        for (int j = i + 1; j < lotto.length; j++) {
            if (lotto[j] < lotto[minIndex]) {
                minIndex = j;
            }
        }
        int temp = lotto[minIndex];
        lotto[minIndex] = lotto[i];
        lotto[i] = temp;
    }
    return lotto;
}

```

