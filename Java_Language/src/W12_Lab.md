# Week 12

**[Lab 1] 익명 클래스로 리스너 구현**
* **내용:** `ActionListener`를 익명 클래스로 구현하여 코드를 간결하게 작성.

```java
package week12.example01;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Anony extends JFrame {
    Anony(){
        setTitle("익명클래스 액션 이벤트리스너 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());

        JButton btn = new JButton("Action");
        cp.add(btn);
        
        // 익명 클래스 사용
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton b = (JButton) e.getSource();
                if (b.getText().equals("Action"))
                    b.setText("액션");
                else
                    b.setText("Action");
                setTitle(b.getText());
            }
        });
        setSize(200,100);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Anony();
    }
}

```

**[Lab 2] 독립 클래스로 리스너 구현**
* **내용:** `ActionListener`를 구현한 별도의 클래스를 만들어 사용.

```java
package week12.example01;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Indep extends JFrame {
    Indep(){
        setTitle("독립클래스 액션 이벤트 리스너 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());

        JButton btn = new JButton("Action");
        btn.addActionListener(new MyAcrionListner()); // 독립 클래스 객체 등록
        cp.add(btn);
        setSize(250,120);
        setVisible(true);
    }
    public static void main(String[] args) { new Indep(); }
}

// 외부(독립) 클래스
class MyAcrionListner implements ActionListener {
    public void actionPerformed(ActionEvent e){
        JButton b= (JButton)e.getSource();
        if (b.getText().equals("Action"))
            b.setText("액션");
        else
            b.setText("Action");
    }
}

```

**[Lab 3] 내부 클래스로 리스너 구현** 
* **내용:** `ActionListener`를 `JFrame` 상속 클래스 내부의 멤버 클래스로 정의.

```java
package week12.example01;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Inner extends JFrame {
    Inner(){
        setTitle("내부클래스 액션 이벤트 리스너 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container cp= getContentPane();
        cp.setLayout(new FlowLayout());

        JButton btn = new JButton("Action");
        btn.addActionListener(new MyActionListner()); // 내부 클래스 객체 등록
        cp.add(btn);
        setSize(300,200);
        setVisible(true);
    }

    // private 내부 클래스
    private class MyActionListner implements ActionListener {
        public void actionPerformed(ActionEvent e){
            JButton b = (JButton)e.getSource();
            if (b.getText().equals("Action"))
                b.setText("액션");
            else
                b.setText("Action");
            setTitle(b.getText());
        }
    }
    public static void main(String[] args) { new Inner(); }
}

```

**[Lab 4] 마우스 이벤트 리스너**
* **내용:** 마우스를 클릭한 위치로 라벨("Hello")을 이동.

```java
package week12.example02;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseLisnerEx extends JFrame {
    MouseLisnerEx(){
        setTitle("마우스 이벤트 리스너 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container cp = getContentPane();
        cp.setLayout(null); // 절대 배치

        JLabel la = new JLabel("Hello");
        la.setSize(50,20);
        la.setLocation(30,30);
        cp.add(la);

        cp.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y= e.getY();
                la.setLocation(x,y); // 클릭 좌표로 이동
            }
        });

        setSize(300,300);
        setVisible(true);
    }
    public static void main(String[] args) { new MouseLisnerEx(); }
}

```

**[Lab 5] 키 리스너**
* **내용:** 입력된 키의 정보를 출력하고, F1키/특수문자 입력 시 배경색을 바꿈.

```java
package week12.example03;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyCodeEx extends JFrame {
    JPanel cp = new JPanel();
    JLabel keyMessage = new JLabel("키를 입력하시오.");

    KeyCodeEx(){
        setTitle("바탕색 변경 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(cp);
        cp.add(keyMessage);
        
        cp.addKeyListener(new myKeyListner());
        
        setSize(300,100);
        setVisible(true);
        cp.requestFocusInWindow(); // 포커스 설정 필수
    }

    private class myKeyListner extends KeyAdapter {
        public void keyPressed(KeyEvent e){
            keyMessage.setText(KeyEvent.getKeyText(e.getKeyCode())+"이 입력됨.");
            
            if (e.getKeyCode() == KeyEvent.VK_F1)
                cp.setBackground(Color.GREEN);
            else if (e.getKeyChar() == '%') {
                cp.setBackground(Color.YELLOW);
            }
        }
    }
    public static void main(String[] args) { new KeyCodeEx(); }
}

```

**[Lab 6] 키 리스너**
* **내용:** 눌린 키의 Code, Char, Text 정보를 라벨 배열에 출력.

```java
package week12.example03;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListenerEx extends JFrame {
    JPanel cp = new JPanel();
    JLabel[] keyMessage;

    KeyListenerEx(){
        setTitle("KeyListner 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(cp);
        cp.addKeyListener(new myKeyListener());

        keyMessage = new JLabel[3];
        keyMessage[0] = new JLabel(" getKeyCode() ");
        keyMessage[1] = new JLabel(" getKeyChar() ");
        keyMessage[2] = new JLabel(" getKeyText() ");

        for (int i=0; i<keyMessage.length;i++){
            cp.add(keyMessage[i]);
            keyMessage[i].setOpaque(true);
            keyMessage[i].setBackground(Color.CYAN);
        }

        setSize(300,200);
        setVisible(true);
        cp.requestFocusInWindow();
    }

    class myKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e){
            int keyCode = e.getKeyCode();
            char keyChar = e.getKeyChar();
            keyMessage[0].setText(Integer.toString(keyCode));
            keyMessage[1].setText(Character.toString(keyChar));
            keyMessage[2].setText(KeyEvent.getKeyText(keyCode));
        }
    }
    public static void main(String[] args) { new KeyListenerEx(); }
}

```

**[Lab 7] 이미지 버튼**
* **내용:** 마우스 상태(기본, 롤오버, 클릭)에 따라 이미지가 변하는 버튼을 만듦.

```java
package week12.example04;
import javax.swing.*;
import java.awt.*;

public class ButtonImageEx extends JFrame {
    ButtonImageEx(){
        setTitle("이미지 버튼 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());

        ImageIcon normalIcon = new ImageIcon("img/Left.png");
        ImageIcon rolloverIcon = new ImageIcon("img/Right.png");
        ImageIcon pressedIcon = new ImageIcon("img/Up.png");

        JButton btn = new JButton("call~~", normalIcon);
        btn.setPressedIcon(pressedIcon);
        btn.setRolloverIcon(rolloverIcon);

        cp.add(btn);
        setSize(250,150);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ButtonImageEx();
    }
}

```
