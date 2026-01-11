# Week 11

**[Lab 1] ContentPane 배경색 변경**
* **내용:** 프레임의 ContentPane을 가져와 배경색을 변경하고 버튼을 추가.

```java
package week11.example01;
import javax.swing.*;
import java.awt.*;

public class ContentPaneEx extends JFrame {
    public ContentPaneEx(){
        setTitle("ContentPane과 JFrame 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container cp = getContentPane();
        cp.setBackground(Color.ORANGE);
        cp.setLayout(new FlowLayout());

        cp.add(new JButton("OK"));
        cp.add(new JButton("Cancel"));
        
        setSize(300,150);
        setVisible(true);
    }
    public static void main(String[] args) { new ContentPaneEx(); }
}

```

**[Lab 2] 레이아웃 매니저**
* **내용:** BorderLayout, FlowLayout, GridLayout, Null Layout 실습.

```java
// FlowLayout 예제
public class FlowEx extends JFrame {
    FlowEx() {
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 40));
        cp.add(new JButton("add"));
        cp.add(new JButton("sub"));
        // ...
        setSize(300, 200);
        setVisible(true);
    }
}

// GridLayout 예제
public class GridEx extends JFrame {
    GridEx(){
        Container cp = getContentPane();
        cp.setLayout(new GridLayout(1, 10)); // 1행 10열
        for (int i = 0; i< 10; i++){
            cp.add(new JButton(Integer.toString(i)));
        }
        // ...
    }
}

```
