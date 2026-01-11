# Week 09

**[Lab 1] 성적 처리 시스템**

* **내용:** `scores.txt` 파일에서 학생 이름과 성적을 읽어와 총점, 평균, 등수를 계산.

```java
private static void lab01() {
    File file = new File("scores.txt");
    try {
        Scanner scanner = new Scanner(file);
        final int ROW = scanner.nextInt(); // 학생 수
        
        String[] names = new String[ROW];
        int[][] scores = new int[ROW][]; // 가변 배열
        float[] average = new float[ROW];
        int[] rank = new int[ROW];

        int row = 0;
        while(scanner.hasNext()) {
            final int COL = scanner.nextInt(); // 과목 수
            names[row] = scanner.next();
            scores[row] = new int[COL];
            
            int sum = 0;
            for(int i=0; i<COL; i++){
                scores[row][i] = scanner.nextInt();
                sum += scores[row][i];
            }
            average[row] = (float)sum / COL;
            row++;
        }
        
        // 등수 계산
        for(int i=0; i<average.length; i++){
            int r = 1;
            for(int j=0; j<average.length; j++){
                if(average[i] < average[j]) r++;
            }
            rank[i] = r;
        }
        // 출력 로직 생략
    } catch (FileNotFoundException e) {
        System.out.println("파일을 찾을 수 없음");
    }
}

```

**[Lab 2] 파일 합치기**
* **내용:** 두 개의 텍스트 파일을 읽어 하나의 파일로 합침.

```java
package week9_1.lab01;
import java.io.*;
import java.util.Scanner;

public class TestMain {
    public static void fileMerge(String filename1, String filename2, String filename3) {
        try(Scanner file1 = new Scanner(new File(filename1));
            Scanner file2 = new Scanner(new File(filename2));
            PrintWriter outfile = new PrintWriter(filename3)) {
            
            while (file1.hasNextLine()) outfile.println(file1.nextLine());
            while (file2.hasNextLine()) outfile.println(file2.nextLine());
            
            System.out.println("파일 합치기가 완료되었습니다.");
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}

```

**[Lab 3] 사각형 비교**
* **내용:** `Rect` 객체의 면적이 같으면 동일한 객체로 판단하도록 `equals`를 재정의.

```java
package week9_1.lab03;

public class Rect {
    int width, height;

    public Rect(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Rect){
            Rect o1 = (Rect) obj;
            if (this.height * this.width == o1.height * o1.width)
                return true;
        }
        return false;
    }
}

```

**[Lab 4] 끝말잇기 게임**
* **내용:** 문자열의 첫 글자와 마지막 글자를 비교하여 게임 규칙을 검사.

```java
// WordGame.java
package week9_2.lab01;

public class WordGame {
    // ... (플레이어 생성 및 게임 루프)
    
    void run(){
        // ...
        while (true) {
            // ...
            Player currentPlayer = players[index];
            currentPlayer.getWordFromUser();

            if (!currentPlayer.checkSuccess(lastWord)){
                System.out.println(currentPlayer.name + "이 졌습니다.");
                break;
            }
            lastWord = currentPlayer.word; // 다음 단어로 갱신
            // ...
        }
    }
}

// Player.java
public boolean checkSuccess(String lastWord){
    char lastChar = lastWord.charAt(lastWord.length() - 1);
    char firstChar = word.charAt(0);
    return lastChar == firstChar;
}

```
