# C언어 실습 과제 모음

수업 시간에 진행한 실습 및 과제 코드 정리.

---

## Week 2

**[W2-1] 이름 10번 반복 출력하기**
* **조건:** 1~9번은 숫자 앞에 공백을 둬서 10번이랑 줄 맞춤.

```c
#include <stdio.h>

int main() {
    char name[] = "Ayeong Moon"; 
    
    // 1~9번은 정렬을 위해 공백 추가
    for (int i = 1; i <= 9; i++) {
        printf(" %d:%s \n", i, name);
    }
    // 10번은 그냥 출력
    printf("10:%s \n", name);
    return 0;
}

```

**[W2-2] 자기소개 출력하기**

* **조건:** printf 1번만 사용.

```c
#include <stdio.h>

int main() {
    printf("Name       : Ayeong Moon\nUniversity : Konkuk University\nDivision   : Computer Engineering\n");
    return 0;
}

```

**[W2-3] 자기소개 출력하기**

* **조건:** printf 3번 사용.

```c
#include <stdio.h>

int main() {
    printf("Name       : Ayeong Moon\n");
    printf("University : Konkuk University\n");
    printf("Division   : Computer Engineering\n");
    return 0;
}

```

**[W2-4] 직사각형 넓이와 둘레 계산하기**

* **조건:** 가로(5.0), 세로(10.0) 고정값 사용. double 타입 네개의 변수 사용.

```c
#include <stdio.h>

int main() {
    double w = 5.0, h = 10.0;
    double area = w * h;            // 넓이 = 가로 * 세로
    double perimeter = 2 * (w + h); // 둘레 = 2 * (가로 + 세로)

    // 소수점 6자리까지 출력 (%.6f)
    printf("Area of the rectangle      : %.6f\n", area);
    printf("Perimeter of the rectangle : %.6f\n", perimeter);

    return 0;
}

```
