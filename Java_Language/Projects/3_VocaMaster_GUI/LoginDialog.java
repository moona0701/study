package project2_ver1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

/**
 * [로그인 및 회원가입 창]
 * 프로그램 시작 시 사용자 인증을 담당하는 별도의 윈도우입니다.
 * 사용자 정보는 텍스트 파일(users.txt)로 관리됩니다.
 */
public class LoginDialog extends JDialog {
    private JTextField tfUser = new JTextField(15);
    private JPasswordField pfPass = new JPasswordField(15);
    private JButton btnLogin = new JButton("로그인");
    private JButton btnRegister = new JButton("회원가입");
    private JButton btnCancel = new JButton("취소");

    private String loggedInUser = null; // 로그인 성공한 ID 저장
    private final File userFile = new File("res/users.txt");

    public LoginDialog(Frame owner) {
        super(owner, "로그인", true);
        setLayout(new BorderLayout(10, 10));

        // 입력창 패널 구성
        JPanel pLabel = new JPanel(new GridLayout(2, 1, 0, 10));
        pLabel.add(new JLabel("사용자명 : ", SwingConstants.RIGHT));
        pLabel.add(new JLabel("비밀번호 : ", SwingConstants.RIGHT));

        JPanel pInput = new JPanel(new GridLayout(2, 1, 0, 10));
        pInput.add(tfUser);
        pInput.add(pfPass);

        // 중앙 배치 (라벨과 입력창 결합)
        JPanel pCenter = new JPanel(new BorderLayout(10, 0));
        pCenter.add(pLabel, BorderLayout.WEST);
        pCenter.add(pInput, BorderLayout.CENTER);

        // 여백 처리를 위해 BorderLayout 활용
        JPanel pWrapper = new JPanel(new BorderLayout());
        pWrapper.add(pCenter, BorderLayout.CENTER);
        pWrapper.add(new JLabel("   "), BorderLayout.WEST); // 왼쪽 여백
        pWrapper.add(new JLabel("   "), BorderLayout.EAST); // 오른쪽 여백
        pWrapper.add(new JLabel(" "), BorderLayout.NORTH);  // 위쪽 여백

        add(pWrapper, BorderLayout.CENTER);

        // 하단 버튼 패널
        JPanel south = new JPanel(new FlowLayout());
        south.add(btnLogin);
        south.add(btnRegister);
        south.add(btnCancel);

        add(south, BorderLayout.SOUTH);

        setSize(350, 180);
        setLocationRelativeTo(owner); // 화면 중앙 배치

        // 이벤트 리스너 등록 (람다식 사용)
        btnLogin.addActionListener(e -> doLogin());
        btnRegister.addActionListener(e -> doRegister());
        btnCancel.addActionListener(e -> { loggedInUser = null; dispose(); });

        // 엔터키 입력 시 로그인 처리
        tfUser.addActionListener(e -> doLogin());
        pfPass.addActionListener(e -> doLogin());

        // 사용자 정보 파일/폴더가 없으면 자동 생성
        if (!userFile.getParentFile().exists()) userFile.getParentFile().mkdirs();
        try {
            if (!userFile.exists()) userFile.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * [로그인 창 표시]
     * 창을 화면에 띄우고, 작업이 끝나면 로그인 성공 ID를 반환합니다.
     */
    public String showDialog() {
        setVisible(true);
        return loggedInUser;
    }

    /**
     * [로그인 로직]
     * 파일에서 ID:PW 정보를 읽어 입력값과 대조합니다.
     */
    private void doLogin() {
        String user = tfUser.getText().trim();
        String pass = new String(pfPass.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 입력하세요.");
            return;
        }

        try (Scanner scan = new Scanner(userFile)) {
            boolean found = false;
            // 파일 내용을 한 줄씩 읽어서 비교
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] parts = line.split(":"); // ID:PW 분리
                if (parts.length == 2 && parts[0].equals(user) && parts[1].equals(pass)) {
                    found = true;
                    break;
                }
            }

            if (found) {
                loggedInUser = user;
                dispose(); // 창 닫기
            } else {
                int r = JOptionPane.showConfirmDialog(this, "계정이 없거나 비밀번호가 틀립니다.\n회원가입 하시겠습니까?", "로그인 실패", JOptionPane.YES_NO_OPTION);
                if (r == JOptionPane.YES_OPTION) doRegister();
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * [회원가입 로직]
     * ID 중복 확인 후 파일에 새로운 계정 정보를 추가합니다.
     */
    private void doRegister() {
        String user = tfUser.getText().trim();
        String pass = new String(pfPass.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID/PW를 입력하세요.");
            return;
        }

        try {
            // ID 중복 검사
            try (Scanner scan = new Scanner(userFile)) {
                while (scan.hasNextLine()) {
                    String line = scan.nextLine();
                    String[] parts = line.split(":");
                    if (parts.length >= 1 && parts[0].equals(user)) {
                        JOptionPane.showMessageDialog(this, "이미 존재하는 사용자명입니다.");
                        return;
                    }
                }
            }
            // 파일 끝에 추가
            try (PrintWriter out = new PrintWriter(new FileWriter(userFile, true))) {
                out.println(user + ":" + pass);
            }
            JOptionPane.showMessageDialog(this, "회원가입 완료. 로그인해주세요.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}