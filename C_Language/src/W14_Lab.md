# [Week 14] 구조체 (Structure)

**1. 퀴즈 프로그램**
* **목표:** 구조체 배열로 문제 은행 만들기.

```c
#include <stdio.h>

typedef struct {
    char quest[50];
    int answer;
} QUIZ;

int main() {
    QUIZ q[2] = {{"1+1=?", 2}, {"2*3=?", 6}};
    int ans, score = 0;

    for(int i=0; i<2; i++) {
        printf("%s ", q[i].quest);
        scanf("%d", &ans);
        if(ans == q[i].answer) score++;
    }
    printf("Score: %d/2\n", score);
    return 0;
}
```

**2. 두 점 사이의 거리 (2D Point)**
* **목표:** 구조체와 `sqrt` 함수 사용.

```c
#include <stdio.h>
#include <math.h>

typedef struct {
    int x, y;
} POINT;

int main() {
    POINT p1 = {0, 0};
    POINT p2 = {3, 4};

    double dist = sqrt(pow(p2.x - p1.x, 2) + pow(p2.y - p1.y, 2));
    
    printf("Distance: %.2f\n", dist);
    return 0;
}
```

**3. 도서 관리 시스템**
* **목표:** 책 제목, 저자, 가격 저장 및 출력.

```c
#include <stdio.h>

struct Book {
    char title[30];
    char author[30];
    int price;
};

int main() {
    struct Book b1 = {"C Programming", "Prof. Oh", 25000};
    
    printf("Title : %s\n", b1.title);
    printf("Author: %s\n", b1.author);
    printf("Price : %d won\n", b1.price);
    
    return 0;
}
```
