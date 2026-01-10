## [Week 7] 파일 입출력 (File I/O)

**1. 학생 성적 리포트 저장 (fprintf)**
* **목표:** 구조체 배열 데이터를 `report.txt` 파일로 출력.

```c
#include <stdio.h>
#define MAX 3

typedef struct {
    char name[20];
    int score;
} STUDENT;

int main() {
    STUDENT s[MAX] = {{"Kim", 90}, {"Lee", 85}, {"Park", 95}};
    FILE *fp = fopen("report.txt", "w"); 

    if (fp == NULL) return 1;

    fprintf(fp, "Name\tScore\n");
    for(int i=0; i<MAX; i++) {
        fprintf(fp, "%s\t%d\n", s[i].name, s[i].score);
    }
    
    fclose(fp);
    printf("Saved to report.txt\n");
    return 0;
}
```

**2. 파일에서 숫자 읽어 합계 구하기 (fscanf)**
* **목표:** `data.txt`에 적힌 숫자들을 읽어서 합계 계산. (파일 끝 `EOF` 처리 중요)

```c
#include <stdio.h>

int main() {
    FILE *fp = fopen("data.txt", "r"); // 미리 data.txt 만들어둬야 함
    int num, sum = 0;

    if (fp == NULL) {
        printf("File not found!\n");
        return 0;
    }

    // 파일 끝까지 읽기
    while (fscanf(fp, "%d", &num) != EOF) {
        sum += num;
    }

    printf("Sum from file: %d\n", sum);
    fclose(fp);
    return 0;
}
```

**3. 표준 입출력 스트림 복사 (Standard I/O)**
* **목표:** 키보드(`stdin`)로 입력받은 걸 그대로 모니터(`stdout`)로 출력. `Ctrl+Z`(Windows) 누르면 종료.

```c
#include <stdio.h>

int main() {
    char ch;
    printf("Type sentences (Ctrl+Z to quit):\n");
    
    // 한 글자씩 읽어서 바로 출력
    while ((ch = getchar()) != EOF) {
        putchar(ch);
    }
    return 0;
}
```
