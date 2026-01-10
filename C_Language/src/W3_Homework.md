# Week 3

**[W3-1] 이니셜과 나이 입력받아 출력하기**
* **조건:** 이니셜 3개 또는 4개를 입력받고, 내년 나이를 계산해서 출력. `scanf` 반환값으로 입력 개수 판단.

```c
#include <stdio.h>

int main() {
    char in1, in2, in3, in4;
    int age;
    int result;

    printf("Input your three initials and your age: ");
    
    // scanf의 반환값(입력 성공한 변수 개수)을 저장
    result = scanf(" %c%c%c%c %d", &in1, &in2, &in3, &in4, &age);

    printf("Greetings %c.%c.%c", in1, in2, in3);
    
    // 이니셜이 4개 들어왔을 경우 마지막 하나 더 출력
    if (result == 5) {
        printf(".%c", in4);
    }

    printf(" Next year your age will be %d.", age + 1);

    return 0;
}
```

**[W3-2] 빛의 이동 거리 계산하기**
* **조건:** `long double` 자료형 사용. 입력 없이 고정된 값으로 계산.

```c
#include <stdio.h>

int main() {
    long double light_speed = 300000.0;
    long double time_arrival = 1000000000000000.0;
    long double distance = light_speed * time_arrival;

    // %.Lf 서식지정자 사용 (long double)
    printf("Speed of light   : %.Lfkm/s\n", light_speed);
    printf("Time to arrive   : %.Lfs\n", time_arrival);
    printf("Distance to Star : %.Lfkm\n", distance);

    return 0;
}
```

**[W3-3] 실수 3개의 합, 평균, 곱 계산하기**
* **조건:** `double` 입력. 합/평균은 소수점 4자리(`%.4lf`), 곱은 지수 표기법(`%.2e`)으로 출력.

```c
#include <stdio.h>

int main() {
    double a, b, c;
    double sum, average, product;
    
    scanf("%lf %lf %lf", &a, &b, &c);

    sum = a + b + c;
    average = sum / 3.0;
    product = a * b * c;

    printf("Numbers : %.4lf %.4lf %.4lf\n", a, b, c);
    printf("Sum     : %.4lf\n", sum);
    printf("Average : %.4lf\n", average);
    printf("Product : %.2e\n", product);

    return 0;
}
```

**[W3-4] 이니셜의 ASCII 코드 확인하기**
* **조건:** 이니셜 3개를 입력받아 해당 문자의 ASCII 값과, 그 다음 문자의 ASCII 값을 출력. 탭(`\t`)으로 간격 맞춤.

```c
#include <stdio.h>

int main() {
    char a, b, c;
    
    printf("Enter your 3 initials: ");
    scanf(" %c %c %c", &a, &b, &c); 

    printf("\nInitial\t\tASCII\t\tNext\t\tASCII\n");
    printf("%c\t\t%d\t\t%c\t\t%d\n", a, a, a + 1, a + 1);
    printf("%c\t\t%d\t\t%c\t\t%d\n", b, b, b + 1, b + 1);
    printf("%c\t\t%d\t\t%c\t\t%d\n", c, c, c + 1, c + 1);

    return 0;
}
```
