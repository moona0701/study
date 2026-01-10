# Week 9

**[W9-1] 조합 계산하기**
* **조건:** C(n, r) 공식을 함수로 구현. 입력받는 두 정수를 스페이스로 구분.
* **구조:** 입력을 받는 `getInteger`, 팩토리얼 구하는 `getFactorial`, 조합 구하는 `combination` 함수로 쪼개서 작성.

```c
#include <stdio.h>

int getInteger(void) {
    int num;
    scanf("%d", &num);
    return num;
}

int getFactorial(int n) {
    int result = 1;
    for (int i = 2; i <= n; i++) {
        result *= i;
    }
    return result;
}

int combination(int n, int r) {
    // 팩토리얼 함수 재사용 (모듈화)
    return getFactorial(n) / (getFactorial(r) * getFactorial(n - r));
}

int main() {
    int n = getInteger();
    int r = getInteger();

    printf("C(%d, %d): %d\n", n, r, combination(n, r));

    return 0;
}
```

**[W9-2] 자연상수 e 계산하기**
* **조건:** 50이하의 정수 입력받기. 소수점 아래 열자리까지 출력.

```c
#include <stdio.h>

// 나눗셈을 위해 반환형을 double로 설정
double getFactorial(int n) {
    double result = 1.0;
    for (int i = 2; i <= n; i++) {
        result *= i;
    }
    return result;
}

double series(int n) {
    double sum = 1.0; 
    for (int i = 1; i <= n; i++) {
        sum += 1.0 / getFactorial(i); // 팩토리얼 함수 재사용
    }
    return sum;
}

int main() {
    int n;
    
    scanf("%d", &n);

    printf("Result: %.10f\n", series(n));

    return 0;
}
```

**[W9-3] 주사위 게임**
* **조건:** 컴퓨터 vs 사용자 주사위 3번 굴려서 합계 큰 쪽이 승리.
* **사용:**
    * `srand(2025)`: 시드값 고정.
    * `sleep(1)`: 1초씩 딜레이 줌.
    * `fflush(stdout)`: 강제 플러시.

```c
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h> // sleep 함수용

int rollDice(void) {
    return rand() % 6 + 1; // 1~6 사이 난수
}

int main() {
    int userSum = 0, computerSum = 0;

    srand(2025); // 시드 고정

    // User Turn
    printf("User     Win > ");
    fflush(stdout); // 버퍼 비우기 (필수)
    for (int i = 0; i < 3; i++) {
        int roll = rollDice();
        userSum += roll;
        printf("%d ", roll);
        fflush(stdout);
        sleep(1); // 1초 대기
    }
    printf("> %d\n", userSum);

    // Computer Turn
    printf("Computer Dice > ");
    fflush(stdout);
    for (int i = 0; i < 3; i++) {
        int roll = rollDice();
        computerSum += roll;
        printf("%d ", roll);
        fflush(stdout);
        sleep(1); 
    }
    printf("> %d\n", computerSum);

    // 승패 판정
    if (userSum > computerSum) {
        printf("User Win\n");
    } else if (userSum < computerSum) {
        printf("Computer Win\n");
    } else {
        printf("Draw\n");
    }

    return 0;
}
```
