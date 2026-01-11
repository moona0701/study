# Week 11 

**[Lab 1] ContentPane 배경색 변경**
* **내용:** 프레임의 ContentPane을 가져와 배경색을 변경하고 버튼을 추가.

```java
package week11.example01;
import javax.swing.*;
import java.awt.*;

public class ContentPaneEx extends JFrame {
    public ContentPaneEx(){ 
        this("ContentPane과 JFrame 예제");
    }

    public ContentPaneEx(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container cp = getContentPane();
        cp.setBackground(Color.ORANGE);
        cp.setLayout(new FlowLayout());

        cp.add(new JButton("OK"));
        cp.add(new JButton("Cancel"));
        cp.add(new JButton("Ignore"));

        setSize(300,150);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ContentPaneEx();
    }
}

```

**[Lab 2] FlowLayout**
* **내용:** 컴포넌트를 왼쪽에서 오른쪽으로 물 흐르듯 배치. (정렬: LEFT, 간격: 30, 40)

```java
package week11.frame;
import javax.swing.*;
import java.awt.*;

public class FlowEx extends JFrame {
    FlowEx() {
        setTitle("FlowLayout 예제");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container cp = getContentPane();

        // 왼쪽 정렬, 수평 간격 30, 수직 간격 40
        cp.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 40));
        
        cp.add(new JButton("add"));
        cp.add(new JButton("sub"));
        cp.add(new JButton("mul"));
        cp.add(new JButton("div"));
        cp.add(new JButton("Calculate"));

        setSize(300, 200);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FlowEx();
    }
}

```

**[Lab 3] BorderLayout**
* **내용:** 컨테이너를 5개 영역(North, South, East, West, Center)으로 나누어 배치.

```java
package week11.frame;
import javax.swing.*;
import java.awt.*;

public class BorderEx extends JFrame {
    BorderEx(){
        setTitle("BorderLayout 예제");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container cp = getContentPane();

        // 수평 간격 30, 수직 간격 20
        cp.setLayout(new BorderLayout(30,20));
        
        cp.add(new JButton("add"), BorderLayout.NORTH);
        cp.add(new JButton("div"), BorderLayout.WEST);
        cp.add(new JButton("mul"), BorderLayout.EAST);
        cp.add(new JButton("sub"), BorderLayout.SOUTH);
        cp.add(new JButton("Calculate"), BorderLayout.CENTER);

        setSize(300,200);
        setVisible(true);
    }

    public static void main(String[] args) {
        new BorderEx();
    }
}

```

**[Lab 4] GridLayout**
* **내용:** 컨테이너를 동일한 크기의 격자(1행 10열)로 나누어 배치.

```java
package week11.frame;
import javax.swing.*;
import java.awt.*;

public class GridEx extends JFrame {
    GridEx(){
        setTitle("GridLayout 예제");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container cp = getContentPane();

        // 1행 10열로 배치
        cp.setLayout(new GridLayout(1,10));
        
        for (int i = 0; i< 10; i++){
            String text = Integer.toString(i);
            cp.add(new JButton(text));
        }
        setSize(500,200);
        setVisible(true);
    }

    public static void main(String[] args) {
        new GridEx();
    }
}

```

**[Lab 5] Null Layout**
* **내용:** 배치관리자를 제거하고 좌표와 크기를 직접 지정.

```java
package week11.frame;
import javax.swing.*;
import java.awt.*;

public class FreeEx extends JFrame {
    FreeEx(){
        setTitle("배치관리자가 없는 경우");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container cp = getContentPane();
        cp.setLayout(null); // 배치관리자 제거

        JLabel la = new JLabel("Hello, Press Buttons!");
        la.setLocation(130,50);
        la.setSize(200,30);
        cp.add(la);

        // 버튼들을 겹치게 배치
        for (int i = 0; i < 9; i++){
            JButton b = new JButton(Integer.toString(i));
            b.setLocation(i*15, i*15);
            b.setSize(50, 20);
            cp.add(b);
        }
        setSize(300,200);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FreeEx();
    }
}

```
