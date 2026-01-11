package project2_ver1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;

/**
 * [퀴즈 진행 화면]
 * 퀴즈 설정 -> 객관식 문제 -> 주관식 문제 순으로 화면이 전환됩니다.
 * CardLayout을 사용하여 패널 전환을 구현했고, javax.swing.Timer로 제한시간 기능을 추가했습니다.
 */
public class QuizPanel extends JPanel {
    VocManager manager;

    // 패널 전환을 위한 CardLayout 컨테이너
    JPanel centerPanel = new JPanel(new CardLayout());

    JTextField tfObjCount = new JTextField("5", 5);
    JTextField tfSubjCount = new JTextField("5", 5);
    JTextField tfTimeLimit = new JTextField("10", 5);

    JLabel lblTimer = new JLabel("남은 시간: ");
    JLabel lblObjWord = new JLabel();
    JLabel lblSubjWord = new JLabel();

    java.util.List<Word> quizList = new ArrayList<>();
    int currentIndex = 0;
    int correctCount = 0;
    int objCount = 0;
    int subjCount = 0;

    // 퀴즈 제한시간 타이머
    Timer timer;
    int settingTime = 10;
    int timeLeft = 10;

    JRadioButton[] options = new JRadioButton[4];
    ButtonGroup bg = new ButtonGroup();

    JTextField tfAnswer = new JTextField(15);

    public QuizPanel(VocManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lblTimer.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        lblTimer.setForeground(Color.RED);
        lblTimer.setVisible(false); // 퀴즈 시작 전엔 타이머 숨김
        top.add(lblTimer);

        add(top, BorderLayout.NORTH);

        // 카드 레이아웃에 화면 추가
        centerPanel.add(createSetupPanel(), "SETUP");
        centerPanel.add(createObjectivePanel(), "OBJ");
        centerPanel.add(createSubjectivePanel(), "SUBJ");
        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * [1. 설정 화면 생성]
     * 문제 개수와 제한 시간을 입력받는 패널입니다.
     */
    private JPanel createSetupPanel() {
        JPanel p = new JPanel(new GridLayout(3, 1));
        p.add(new JLabel("")); // 레이아웃 간격용

        JPanel contentPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("퀴즈 설정", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        title.setForeground(Color.BLUE);
        contentPanel.add(title, BorderLayout.NORTH);

        JPanel inputWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel inputPanel = new JPanel(new GridLayout(4, 1, 0, 5));

        JPanel p1 = new JPanel();
        p1.add(new JLabel("객관식 문제 수:")); p1.add(tfObjCount);

        JPanel p2 = new JPanel();
        p2.add(new JLabel("주관식 문제 수:")); p2.add(tfSubjCount);

        JPanel p3 = new JPanel();
        p3.add(new JLabel("문제당 제한시간(초):")); p3.add(tfTimeLimit);

        JButton btnStart = new JButton("퀴즈 시작");
        btnStart.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        btnStart.setBackground(Color.CYAN);
        btnStart.addActionListener(e -> startQuiz());

        inputPanel.add(p1); inputPanel.add(p2); inputPanel.add(p3);
        inputPanel.add(btnStart);

        inputWrapper.add(inputPanel);
        contentPanel.add(inputWrapper, BorderLayout.CENTER);
        p.add(contentPanel);
        p.add(new JLabel(""));

        return p;
    }

    /**
     * [2. 객관식 화면 생성]
     * 문제와 4지 선다 보기를 표시하는 패널입니다.
     */
    private JPanel createObjectivePanel() {
        JPanel p = new JPanel(new BorderLayout());

        JPanel qPanel = new JPanel(new GridLayout(2, 1));
        lblObjWord.setHorizontalAlignment(SwingConstants.CENTER);
        lblObjWord.setFont(new Font("Arial", Font.BOLD, 35));
        lblObjWord.setForeground(Color.BLUE);

        JLabel guide = new JLabel("알맞은 뜻을 고르세요", SwingConstants.CENTER);
        guide.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

        qPanel.add(lblObjWord);
        qPanel.add(guide);
        p.add(qPanel, BorderLayout.NORTH);

        JPanel centerWrapper = new JPanel(new BorderLayout());
        JPanel optionsP = new JPanel(new GridLayout(4, 1, 5, 15));
        Font optFont = new Font("맑은 고딕", Font.PLAIN, 16);

        // 라디오 버튼 생성 및 그룹화
        for(int i=0; i<4; i++) {
            options[i] = new JRadioButton();
            options[i].setFont(optFont);
            options[i].setBackground(Color.WHITE);
            bg.add(options[i]);
            optionsP.add(options[i]);
        }

        JPanel buttonContainer = new JPanel(new BorderLayout());
        buttonContainer.add(new JLabel(" "), BorderLayout.NORTH);
        buttonContainer.add(optionsP, BorderLayout.CENTER);
        buttonContainer.add(new JLabel(" "), BorderLayout.SOUTH);

        centerWrapper.add(buttonContainer, BorderLayout.NORTH);
        centerWrapper.add(new JLabel("          "), BorderLayout.WEST);
        centerWrapper.add(new JLabel("          "), BorderLayout.EAST);

        p.add(centerWrapper, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnNext = new JButton("정답 확인");
        JButton btnQuit = new JButton("중단");

        btnNext.addActionListener(e -> checkAnswerObj());
        btnQuit.addActionListener(e -> quitQuiz());
        btnQuit.setBackground(Color.PINK);

        btnPanel.add(btnNext);
        btnPanel.add(btnQuit);

        p.add(btnPanel, BorderLayout.SOUTH);

        return p;
    }

    /**
     * [3. 주관식 화면 생성]
     * 문제와 정답 입력 필드를 표시하는 패널입니다.
     */
    private JPanel createSubjectivePanel() {
        JPanel p = new JPanel(new BorderLayout());

        JPanel qPanel = new JPanel(new GridLayout(2, 1));
        lblSubjWord.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubjWord.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        lblSubjWord.setForeground(Color.MAGENTA);

        JLabel guide = new JLabel("뜻에 맞는 영단어를 입력하세요", SwingConstants.CENTER);
        guide.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

        qPanel.add(lblSubjWord);
        qPanel.add(guide);
        p.add(qPanel, BorderLayout.NORTH);

        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        tfAnswer.setFont(new Font("Arial", Font.PLAIN, 20));
        tfAnswer.setHorizontalAlignment(JTextField.CENTER);
        centerWrapper.add(tfAnswer);
        p.add(centerWrapper, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnNext = new JButton("제출");
        JButton btnQuit = new JButton("중단");

        btnNext.addActionListener(e -> checkAnswerSubj());
        btnQuit.addActionListener(e -> quitQuiz());
        btnQuit.setBackground(Color.PINK);

        btnPanel.add(btnNext);
        btnPanel.add(btnQuit);

        p.add(btnPanel, BorderLayout.SOUTH);

        tfAnswer.addActionListener(e -> checkAnswerSubj());
        return p;
    }

    /**
     * [퀴즈 중단]
     * 진행 중인 타이머를 멈추고 설정 화면으로 복귀합니다.
     */
    private void quitQuiz() {
        if(timer != null) timer.stop();
        JOptionPane.showMessageDialog(this, "퀴즈를 중단하고 설정 화면으로 돌아갑니다.");
        lblTimer.setVisible(false);
        CardLayout cl = (CardLayout)centerPanel.getLayout();
        cl.show(centerPanel, "SETUP");
    }

    /**
     * [퀴즈 시작 로직]
     * 설정값을 확인하고 문제를 랜덤으로 추출하여 퀴즈를 시작합니다.
     */
    private void startQuiz() {
        try {
            objCount = Integer.parseInt(tfObjCount.getText().trim());
            subjCount = Integer.parseInt(tfSubjCount.getText().trim());
            settingTime = Integer.parseInt(tfTimeLimit.getText().trim());
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "숫자만 입력해주세요.");
            return;
        }

        int totalNeed = objCount + subjCount;
        if(totalNeed == 0 || totalNeed > manager.voc.size()) {
            JOptionPane.showMessageDialog(this, "문제 수가 0이거나 전체 단어 수보다 많습니다.");
            return;
        }

        quizList = manager.getRandomWords(totalNeed);
        currentIndex = 0;
        correctCount = 0;

        lblTimer.setVisible(true);
        nextQuestion();
    }

    /**
     * [다음 문제 로드]
     * 인덱스에 따라 객관식/주관식 화면을 전환하고 타이머를 재시작합니다.
     */
    private void nextQuestion() {
        if(currentIndex >= quizList.size()) {
            finishQuiz();
            return;
        }

        Word w = quizList.get(currentIndex);

        // 기존 타이머 종료 후 새로 시작
        if(timer != null) timer.stop();
        timeLeft = settingTime;
        lblTimer.setText("남은 시간: " + timeLeft);

        timer = new Timer(1000, e -> {
            timeLeft--;
            lblTimer.setText("남은 시간: " + timeLeft);
            if(timeLeft <= 0) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "시간 초과! 오답 처리됩니다.");
                manager.addWrongAnswer(w); // 시간 초과도 오답 처리
                currentIndex++;
                nextQuestion();
            }
        });
        timer.start();

        // 현재 문제 순서에 따라 화면 전환
        CardLayout cl = (CardLayout)centerPanel.getLayout();
        if(currentIndex < objCount) {
            lblObjWord.setText(w.getEng());
            setOptions(w);
            cl.show(centerPanel, "OBJ");
        } else {
            lblSubjWord.setText(w.getKor());
            tfAnswer.setText("");
            tfAnswer.requestFocus();
            cl.show(centerPanel, "SUBJ");
        }
    }

    /**
     * [객관식 보기 생성]
     * 정답 1개와 오답 3개를 섞어서 라디오 버튼에 배치합니다.
     */
    private void setOptions(Word answer) {
        bg.clearSelection();
        java.util.List<Word> opts = new ArrayList<>();
        opts.add(answer);

        // 오답 3개 랜덤 추출
        while(opts.size() < 4) {
            Word randW = manager.voc.get((int)(Math.random()*manager.voc.size()));
            if(!opts.contains(randW)) opts.add(randW);
        }
        Collections.shuffle(opts); // 보기 순서 섞기

        for(int i=0; i<4; i++) {
            options[i].setText(opts.get(i).getKor());
            options[i].setActionCommand(opts.get(i).getEng()); // 숨겨진 값으로 영어 저장
        }
    }

    // 객관식 정답 확인
    private void checkAnswerObj() {
        if(timer != null) timer.stop();
        Word current = quizList.get(currentIndex);
        String selected = "";

        for(JRadioButton rb : options) {
            if(rb.isSelected()) {
                selected = rb.getActionCommand();
                break;
            }
        }

        if(selected.equals(current.getEng())) {
            JOptionPane.showMessageDialog(this, "정답!");
            correctCount++;
        } else {
            String msg = "오답입니다!\n\n[정답 정보]\n단어 : " + current.getEng() + "\n뜻   : " + current.getKor();
            JOptionPane.showMessageDialog(this, msg, "결과", JOptionPane.ERROR_MESSAGE);
            manager.addWrongAnswer(current);
        }
        currentIndex++;
        nextQuestion();
    }

    // 주관식 정답 확인
    private void checkAnswerSubj() {
        if(timer != null) timer.stop();
        Word current = quizList.get(currentIndex);
        String input = tfAnswer.getText().trim();

        if(input.equalsIgnoreCase(current.getEng())) {
            JOptionPane.showMessageDialog(this, "정답!");
            correctCount++;
        } else {
            String msg = "오답입니다!\n\n[정답 정보]\n단어 : " + current.getEng() + "\n뜻   : " + current.getKor();
            JOptionPane.showMessageDialog(this, msg, "결과", JOptionPane.ERROR_MESSAGE);
            manager.addWrongAnswer(current);
        }
        currentIndex++;
        nextQuestion();
    }

    private void finishQuiz() {
        if(timer != null) timer.stop();
        JOptionPane.showMessageDialog(this, "퀴즈 끝! 맞은 개수: " + correctCount);
        lblTimer.setVisible(false);
        CardLayout cl = (CardLayout)centerPanel.getLayout();
        cl.show(centerPanel, "SETUP");
    }
}