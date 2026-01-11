package project2_ver1;

import javax.swing.*;
import java.awt.*;

/**
 * [메인 프레임 클래스]
 * 프로그램의 메인 윈도우입니다.
 * 탭 구조를 사용하여 단어 관리, 퀴즈, 오답노트 패널을 전환합니다.
 */
public class MainFrame extends JFrame {
    VocManager manager = new VocManager();
    JTabbedPane tabs = new JTabbedPane(); // 탭 컨테이너

    public MainFrame(String userName) {
        super(userName + "님의 나만의 영어 단어장");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(600, 700);
        setLocationRelativeTo(null); // 화면 중앙 실행

        manager.setUserName(userName); // 매니저에 로그인 정보 전달

        createMenuBar();

        // 각 기능별 패널을 탭에 추가
        tabs.addTab("단어 관리", new WordPanel(manager));
        tabs.addTab("퀴즈 풀기", new QuizPanel(manager));
        tabs.addTab("오답 노트", new WrongPanel(manager));

        add(tabs);

        // 창이 닫힐 때 데이터 자동 저장
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                manager.saveVoc();
                manager.saveWrongVoc();
            }
        });

        setVisible(true);
    }

    /**
     * [메뉴바 생성]
     * 파일 저장 및 종료 기능을 제공하는 상단 메뉴를 구성합니다.
     */
    private void createMenuBar() {
        JMenuBar mb = new JMenuBar();
        JMenu fileMenu = new JMenu("파일");
        JMenuItem saveItem = new JMenuItem("수동 저장");
        JMenuItem exitItem = new JMenuItem("종료");

        // 메뉴 클릭 시 동작 설정
        saveItem.addActionListener(e -> {
            manager.saveVoc();
            manager.saveWrongVoc();
            JOptionPane.showMessageDialog(this, "저장되었습니다.");
        });
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(saveItem);
        fileMenu.addSeparator(); // 구분선
        fileMenu.add(exitItem);
        mb.add(fileMenu);
        setJMenuBar(mb);
    }

    public static void main(String[] args) {
        // 로그인 창을 먼저 실행
        LoginDialog login = new LoginDialog(null);
        String user = login.showDialog();

        // 로그인이 성공적으로 완료되면 메인 프레임 생성
        if(user != null) {
            new MainFrame(user);
        } else {
            System.exit(0);
        }
    }
}