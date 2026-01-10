# [Week 12] 문자열 (String)

**1. 사전 순서 비교**
* **목표:** `strcmp`로 두 단어 비교.

```c
#include <stdio.h>
#include <string.h>

int main() {
    char s1[] = "Apple";
    char s2[] = "Banana";
    
    int res = strcmp(s1, s2);
    
    if (res < 0) printf("%s comes first.\n", s1);
    else printf("%s comes first.\n", s2);
    
    return 0;
}
```

**2. 문자열 길이 구하기**
* **목표:** `NULL` 문자 만날 때까지 카운트.

```c
#include <stdio.h>

int main() {
    char str[100];
    scanf("%s", str);
    
    int len = 0;
    while(str[len] != '\0') { // 널 문자 나올 때까지
        len++;
    }
    printf("Length: %d\n", len);
    return 0;
}
```

**3. 문자열 뒤집기**
* **목표:** 문자열을 거꾸로 출력.

```c
#include <stdio.h>
#include <string.h>

int main() {
    char str[] = "Konkuk";
    int len = strlen(str);

    printf("Original: %s\n", str);
    printf("Reverse : ");
    
    for(int i = len - 1; i >= 0; i--) {
        printf("%c", str[i]);
    }
    printf("\n");
    return 0;
}
```
