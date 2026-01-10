# [Week 13] 포인터 (Pointer)

**1. Swap 함수 (Call by Reference)**
* **목표:** 주소값을 넘겨서 변수 값 교환.

```c
#include <stdio.h>

void swap(int *a, int *b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

int main() {
    int x = 10, y = 20;
    swap(&x, &y);
    printf("x=%d, y=%d\n", x, y);
    return 0;
}
```

**2. 포인터로 배열 합계 구하기**
* **목표:** arr[i] 대신 (arr + i) 사용.

```c
#include <stdio.h>

int main() {
    int arr[5] = {1, 2, 3, 4, 5};
    int sum = 0;
    int *ptr = arr; // 배열의 이름은 주소

    for(int i=0; i<5; i++) {
        sum += *(ptr + i); // 포인터 연산
    }
    printf("Sum: %d\n", sum);
    return 0;
}
```

**3. 포인터로 최댓값 찾기**
* **목표:** 포인터를 이동시키며 최댓값 탐색.

```c
#include <stdio.h>

int main() {
    int arr[5] = {10, 50, 20, 90, 30};
    int *max = arr; // 일단 첫 번째를 최댓값으로 가정

    for(int i=1; i<5; i++) {
        if (*(arr + i) > *max) {
            max = arr + i; // 더 큰 값의 주소를 저장
        }
    }
    printf("Max value: %d\n", *max);
    return 0;
}
```
