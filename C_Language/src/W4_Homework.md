# Week 4

**[W4-1] 화씨(F)를 섭씨(C)로 변환**
* **조건:** 주어진 공식 사용. 소수점 2자리 출력.

```c
#include <stdio.h>

int main() {
    float fahrenheit, celsius;

    printf("Enter Fahrenheit Temperature : ");
    scanf("%f", &fahrenheit);

    // 정수 나눗셈 방지 위해 5.0, 9.0 사용
    celsius = (5.0 / 9.0) * (fahrenheit - 32);

    printf("Celsius Temperature          : %.2f\n", celsius);

    return 0;
}
```

**[W4-2] 온도에 따른 물의 상태 판별**
* **조건:** `if`문 쓰지 말고 조건 연산자(삼항 연산자)만 쓸 것.

```c
#include <stdio.h>

int main()
{
    float celsius;
    char *state; // 문자열 저장할 포인터
    
    printf("Enter Celsius Temperature : ");
    scanf("%f", &celsius);
    
    // 중첩 삼항 연산자 사용 (if문 대체)
    state = (celsius < 0) ? "Ice" : ((celsius < 100) ? "Liquid" : "Gas");
    
    printf("Celsius Temperature       : %.2f\n", celsius);
    printf("State                     : %s\n ", state);
    
    return 0;
}
```

**[W4-3] 좌표 입력받아 사분면 판별**
* **조건:** `if`나 `switch` 절대 금지. 오직 조건 연산자(`? :`)만 사용.

```c
#include <stdio.h>

int main()
{
    int x, y;
    
    printf("Enter Coordinates (x, y) : ");
    scanf("%d %d", &x, &y);
    
    // 조건 연산자 중첩으로 사분면 판별
    printf("Location                 : %s\n",
        (x > 0 && y > 0) ? "Q1" :
        (x < 0 && y > 0) ? "Q2" :
        (x < 0 && y < 0) ? "Q3" :
        (x > 0 && y < 0) ? "Q4" :
        (x == 0 && y == 0) ? "Origin" :
        (x == 0) ? "Y Axis" : "X Axis" );

    return 0;
}
```

**[W4-4] 비트 연산으로 문자 병합**
* **조건:** 문자 4개(`char`)를 입력받아 `unsigned int` 변수 하나에 합치기. 16진수(`%#x`)로 출력.

```c
#include <stdio.h>

int main(void) {
    
    char ch1, ch2, ch3, ch4;
    unsigned int result;

    printf("Enter Characters : ");
    scanf(" %c %c %c %c", &ch1, &ch2, &ch3, &ch4);

    // ch4를 맨 앞(24비트 이동), ch1을 맨 뒤로 보냄
    result = (ch4 << 24) |
             (ch3 << 16) |
             (ch2 << 8)  |
             ch1;

    printf("Result           : %#x\n", result);
    
    return 0;
}
```
