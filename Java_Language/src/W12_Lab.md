# Week 12

**[Lab 1] 이벤트 리스너 구현 (익명/독립/내부 클래스)**

* **내용:** 버튼 클릭 이벤트를 다양한 형태의 클래스로 구현합니다.
* **코드 파일:** `week12/example01/Anony.java`

```java
package week12.example01;
import javax.swing.*;
import java.awt.event.*;

public class Anony extends JFrame {
    Anony(){
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());
        JButton btn = new JButton("Action");
        cp.add(btn);
        
        // 익명 클래스로 ActionListener 구현
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton b = (JButton) e.getSource();
                if (b.getText().equals("Action")) b.setText("액션");
                else b.setText("Action");
                setTitle(b.getText());
            }
        });
        // ...
    }
}

```

**[Lab 2] 마우스 리스너**

* **내용:** 마우스를 클릭한 위치로 라벨을 이동시킵니다.
* **코드 파일:** `week12/example02/MouseLisnerEx.java`

```java
package week12.example02;
import java.awt.event.*;
// ...
cp.addMouseListener(new MouseAdapter() {
    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        la.setLocation(x, y);
    }
});

```

**[Lab 3] 키 리스너**

* **내용:** 키보드 입력을 감지하고 특정 키(F1) 입력 시 배경색을 변경합니다.
* **코드 파일:** `week12/example03/KeyCodeEx.java`

```java
private class myKeyListner extends KeyAdapter {
    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_F1)
            cp.setBackground(Color.GREEN);
        else if (e.getKeyChar() == '%') {
            cp.setBackground(Color.YELLOW);
        }
    }
}

```

---

## Week 13

**[Lab 1] 스트림 API 활용**

* **내용:** 리스트에 저장된 학생 데이터를 스트림을 이용해 정렬 및 필터링합니다.
* **코드 파일:** `week13/lab01/TestMain.java`

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

* **내용:** 단어장 로직을 GUI에 연결하고 이벤트 처리를 람다식으로 구현합니다.
* **코드 파일:** `week13/lab02/MainFrame.java`, `VocManager.java`

```java
package week13.lab02;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    // ...
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

```
