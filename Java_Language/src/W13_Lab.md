# Week 13

**[Lab 1] 스트림 API 활용**
* **내용:** 리스트에 저장된 학생 데이터를 스트림을 이용해 정렬 및 필터링.

```java
package week13.lab01;
import java.util.*;

public class TestMain {
    public static void main(String[] args){
        List<Student> list = new ArrayList<>();
        list.add(new Student("홍길동", 80));
        list.add(new Student("김길동", 70));
        // ...

        // 점수 기준 내림차순 정렬하여 1명만 출력
        list.stream()
            .sorted((o1,o2) -> o2.score - o1.score)
            .limit(1)
            .forEach(System.out::println);

        // 점수가 80점 이상인 학생 필터링
        list.stream()
            .filter(std -> std.score >= 80)
            .sorted((o1,o2) -> o1.score - o2.score)
            .forEach(System.out::println);
    }
}

```

**[Lab 2] 단어장 GUI와 람다식**
* **내용:** 단어장 로직을 GUI에 연결하고 이벤트 처리를 람다식으로 구현.

```java
package week13.lab02;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    // ... 기존 코드 생략 ...

    private void initLayout() {
        this.centerPanel = new JPanel();
        this.northPanel = new JPanel();
        JButton btn = new JButton("클릭");
        
        // 람다식을 이용한 이벤트 처리
        btn.addActionListener(e -> centerPanel.setBackground(Color.BLUE));
        
        this.northPanel.add(btn);
        frame.add(this.northPanel, "North");
        frame.add(this.centerPanel, "Center");
    }
}

```
