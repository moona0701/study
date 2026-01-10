# Week 6

**[W6-1] 색상 문자를 입력받아 화학물질 판별**
* **조건:**  대소문자 모두 처리해야 함.

```c
#include <stdio.h>

int main()
{
    char color;
    
    scanf("%c", &color);
    
    // 대소문자 모두 처리 (OR 연산자)
    if (color == 'R'|| color == 'r')
        printf("ammonia\n");
    else if (color == 'G' || color == 'g')
        printf("hydrogen\n");
    else if (color == 'W' || color == 'w')
        printf("oxygen\n");
    else if (color == 'B' || color == 'b')
        printf("carbon monoxide\n");
    else
        printf("unknown\n");

    return 0;
}
```

**[W6-2] 문자 입력받고 문자 종류 판별 (대/소문자, 숫자)**
* **조건:** `getchar()`로 문자 하나 입력받기.

```c
#include <stdio.h>

int main() {
   
    char ch;
    ch = getchar(); // 문자 하나 입력

    if (ch >= 'A' && ch <= 'Z') {
        printf("%c is uppercase\n", ch);
    } else if (ch >= 'a' && ch <= 'z') {
        printf("%c is lowercase\n", ch);
    } else if (ch >= '0' && ch <= '9') {
        printf("%c is numeric\n", ch);
    } else {
        printf("%c is unknown\n", ch);
    }

    return 0;
}
```

**[W6-3] 년,월,일을 입력받고 그 해의 몇 번째 날인지 계산**
* **조건:** 정수형으로 입력받기. 배열과과 외부 라이브러리 사용 금지. 년도에 -1 입력 시 종료.

```c
#include <stdio.h>

int main()
{
    int year, month, day;
    
    scanf("%d", &year);
    
    if (year == -1) {
        printf("Program terminated\n");
        return -1;
    }
    
    scanf("%d %d", &month, &day);

    // 윤년 체크 (4의 배수이고 100의 배수가 아니거나, 400의 배수)
    int isLeap = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);

    int totalDays = 0;

    // break 없이 작성하여 이전 달들의 날짜를 모두 더함
    switch(month) {
        case 12: totalDays += 30; // 11월 일수
        case 11: totalDays += 31; // 10월 일수
        case 10: totalDays += 30; 
        case 9:  totalDays += 31; 
        case 8:  totalDays += 31; 
        case 7:  totalDays += 30; 
        case 6:  totalDays += 31; 
        case 5:  totalDays += 30; 
        case 4:  totalDays += 31; 
        case 3:  totalDays += isLeap ? 29 : 28; // 2월 (윤년 반영)
        case 2:  totalDays += 31; // 1월
        case 1:  totalDays += 0;  // 1월 이전은 없음
    }

    totalDays += day; // 현재 달의 날짜 추가

    printf("The day number for %d/%02d/%02d is: %d\n", year, month, day, totalDays);

    return 0;
}
```

**[W6-4] 2025년 이후의 년/월을 입력받아 달력 출력**
* **조건:** 배열과 외부 라이브러리 사용 금지. 2025년 1월 1일은 수요일.
* **알고리즘:**
    1. 2025년부터 입력받은 년도 직전까지의 총 일수 계산.
    2. 입력받은 해의 1월부터 직전 월까지의 총 일수 계산.
    3. `(기준요일 3 + 총 일수) % 7`로 시작 요일 구함.
    4. 줄바꿈(`\n`) 타이밍 맞춰서 날짜 출력.

```c
#include <stdio.h>

int main() {
    int year, month;
    scanf("%d", &year);

    if (year == -1) {
        printf("Program terminated\n");
        return 0;
    }

    scanf("%d", &month);

    if (year < 2025 || month < 1 || month > 12) {
        printf("Invalid input\n");
        return 0;
    }

    int leap, totalDays = 0;

    // 1. 2025년부터 작년까지 날짜 더하기
    for (int y = 2025; y < year; y++) {
        leap = (y % 4 == 0 && y % 100 != 0) || (y % 400 == 0);
        totalDays += leap ? 366 : 365;
    }

    // 2. 올해 1월부터 지난달까지 날짜 더하기
    for (int m = 1; m < month; m++) {
        if (m == 2) {
            leap = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
            totalDays += leap ? 29 : 28;
        } else if (m == 4 || m == 6 || m == 9 || m == 11) {
            totalDays += 30;
        } else {
            totalDays += 31;
        }
    }

    // 3. 시작 요일 계산 (2025.1.1 수요일 = 3)
    int startWeekday = (3 + totalDays) % 7;

    // 4. 이번 달이 며칠까지 있는지 확인
    int daysInMonth;
    if (month == 2) {
        leap = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
        daysInMonth = leap ? 29 : 28;
    } else if (month == 4 || month == 6 || month == 9 || month == 11) {
        daysInMonth = 30;
    } else {
        daysInMonth = 31;
    }

    // 5. 달력 출력
    printf("\n              %d  %d\n", year, month);
    printf("------------------------------------\n");
    printf("Sun  Mon  Tue  Wed  Thu  Fri  Sat\n");

    // 첫 주 공백 출력
    for (int i = 0; i < startWeekday; i++) {
        printf("     ");
    }

    // 날짜 출력
    for (int d = 1; d <= daysInMonth; d++) {
        printf("%3d  ", d);
        // 토요일(6)이면 줄바꿈, 단 마지막 날은 줄바꿈 안 함
        if ((startWeekday + d) % 7 == 0 && d != daysInMonth) {
            printf("\n");
        }
    }
    printf("\n"); // 끝

    return 0;
}
```
