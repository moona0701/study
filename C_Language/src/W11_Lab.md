## [Week 11] 배열 (Array)

**1. Fractal 별 찍기**
* **목표:** 3의 거듭제곱 크기로 구멍 뚫린 패턴 재귀 출력.

```c
#include <stdio.h>

void star(int i, int j, int n) {
    if ((i / n) % 3 == 1 && (j / n) % 3 == 1)
        printf(" ");
    else {
        if (n / 3 == 0) printf("*");
        else star(i, j, n / 3);
    }
}

int main() {
    int n = 9; // 3, 9, 27...
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) star(i, j, n);
        printf("\n");
    }
    return 0;
}
```

**2. 행렬 덧셈 (Matrix Addition)**
* **목표:** 2차원 배열 2개를 더해서 결과 출력.

```c
#include <stdio.h>

int main() {
    int A[2][2] = {{1, 2}, {3, 4}};
    int B[2][2] = {{5, 6}, {7, 8}};
    int Sum[2][2];

    for(int i=0; i<2; i++) {
        for(int j=0; j<2; j++) {
            Sum[i][j] = A[i][j] + B[i][j];
            printf("%d ", Sum[i][j]);
        }
        printf("\n");
    }
    return 0;
}
```

**3. 버블 정렬**
* **목표:** 배열 안의 숫자를 오름차순으로 정렬.

```c
#include <stdio.h>

int main() {
    int arr[5] = {34, 12, 5, 99, 78};
    int temp;

    for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 4 - i; j++) {
            if (arr[j] > arr[j+1]) { // 앞이 더 크면 교체
                temp = arr[j];
                arr[j] = arr[j+1];
                arr[j+1] = temp;
            }
        }
    }

    for(int i=0; i<5; i++) printf("%d ", arr[i]);
    return 0;
}
```
