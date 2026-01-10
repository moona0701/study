# [Week 10] 함수와 재귀 (Recursion)

**1. 하노이의 탑**
* **목표:** 재귀의 정석. 원반 n개를 옮기는 과정 출력.

```c
#include <stdio.h>

void hanoi(int n, char from, char to, char aux) {
    if (n == 1) {
        printf("Move 1 from %c to %c\n", from, to);
        return;
    }
    hanoi(n-1, from, aux, to);
    printf("Move %d from %c to %c\n", n, from, to);
    hanoi(n-1, aux, to, from);
}

int main() {
    int n = 3; // 원반 3개
    hanoi(n, 'A', 'C', 'B');
    return 0;
}
```

**2. 피보나치 수열**
* **목표:** F(n) = F(n-1) + F(n-2) 구현.

```c
#include <stdio.h>

int fib(int n) {
    if (n == 0) return 0;
    if (n == 1) return 1;
    return fib(n-1) + fib(n-2);
}

int main() {
    int n;
    scanf("%d", &n);
    printf("Fibonacci(%d) = %d\n", n, fib(n));
    return 0;
}
```

**3. 전역변수 vs 지역변수**
* **목표:** `static` 변수가 함수 종료 후에도 값 유지하는지 확인.

```c
#include <stdio.h>

void counter() {
    static int cnt = 0; // 정적 변수 (메모리에 계속 상주)
    int local = 0;      // 지역 변수 (매번 초기화)
    
    cnt++;
    local++;
    printf("Static: %d, Local: %d\n", cnt, local);
}

int main() {
    for(int i=0; i<3; i++) counter();
    return 0;
}
```
