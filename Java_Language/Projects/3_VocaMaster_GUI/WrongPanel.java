package project2_ver1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import javax.swing.Timer;

/**
 * [오답 노트 화면]
 * 틀린 단어 목록을 JTable로 확인하고, 해당 단어들로만 구성된 퀴즈를 다시 풀 수 있는 패널입니다.
 * 목록 화면과 퀴즈 화면 두 개의 패널을 CardLayout으로 겹쳐놓고 전환하며 사용합니다.
 */
public class WrongPanel extends JPanel {
    private VocManager vocManager;

    // 화면 전환을 담당하는 카드 레이아웃 설정
    private CardLayout cardLayout = new CardLayout();
    private JPanel contentPanel = new JPanel(cardLayout);

    // 오답 목록을 보여줄 테이블 관련 변수
    private JTable wrongTable;
    private DefaultTableModel tableModel;

    // 퀴즈 문제 수 입력 필드 (기본값 5)
    private JTextField tfQuizCount = new JTextField("5", 3);

    // 퀴즈 진행에 필요한 변수들
    private java.util.List<Word> quizList; // 실제 퀴즈에 사용할 단어 리스트
    private int currentIndex = 0; // 현재 풀고 있는 문제 인덱스
    private int correctCount = 0; // 맞은 개수

    // 제한시간 타이머
    private Timer timer;
    private int timeLeft = 10;

    // 퀴즈 UI 컴포넌트
    private JLabel lblTimer = new JLabel();
    private JLabel lblQuestion = new JLabel();
    private JTextField answerField = new JTextField(15);

    public WrongPanel(VocManager vocManager) {
        this.vocManager = vocManager;
        setLayout(new BorderLayout(10, 10)); // 전체 레이아웃 설정 (여백 10)

        // 카드 레이아웃 패널에 두 개의 화면(목록, 퀴즈)을 추가
        // "LIST"라는 이름으로 목록 패널을, "QUIZ"라는 이름으로 퀴즈 패널을 등록함
        contentPanel.add(createListPanel(), "LIST");
        contentPanel.add(createQuizPanel(), "QUIZ");

        add(contentPanel, BorderLayout.CENTER);

        refreshList(); // 패널이 처음 생성될 때 오답 데이터를 불러옴
    }

    /**
     * [오답 목록 패널 생성]
     * 상단: 제목, 중앙: 테이블, 하단: 설정 및 버튼으로 구성됩니다.
     */
    private JPanel createListPanel() {
        JPanel p = new JPanel(new BorderLayout(0, 10));

        // 상단 제목
        JLabel title = new JLabel("내 오답 노트", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 20));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(title);
        p.add(titlePanel, BorderLayout.NORTH);

        // 중앙 테이블 설정
        String[] cols = {"영어", "뜻"};
        // 익명 클래스로 모델을 재정의하여 셀 수정이 불가능하도록 설정
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        wrongTable = new JTable(tableModel);

        p.add(new JScrollPane(wrongTable), BorderLayout.CENTER);

        // 하단 버튼 및 입력창 구성
        JPanel btnP = new JPanel();
        JButton btnRef = new JButton("목록 새로고침");
        JButton btnQuiz = new JButton("오답 퀴즈 시작");

        btnQuiz.setBackground(Color.CYAN); // 강조 색상
        btnQuiz.setFont(new Font("맑은 고딕", Font.BOLD, 14));

        btnP.add(btnRef);
        btnP.add(new JLabel("   문제 수:"));
        tfQuizCount.setHorizontalAlignment(JTextField.CENTER);
        btnP.add(tfQuizCount);
        btnP.add(btnQuiz);

        p.add(btnP, BorderLayout.SOUTH);

        // 버튼 이벤트 연결
        btnRef.addActionListener(e -> refreshList()); // 파일에서 다시 읽어오기
        btnQuiz.addActionListener(e -> startWrongQuiz()); // 퀴즈 모드로 전환

        return p;
    }

    /**
     * [오답 퀴즈 패널 생성]
     * 주관식 퀴즈 형태의 UI를 구성합니다.
     * 상단: 타이머/문제, 중앙: 정답 입력, 하단: 제출/중단 버튼
     */
    private JPanel createQuizPanel() {
        JPanel p = new JPanel(new BorderLayout());

        // 상단 정보 표시 영역
        JPanel top = new JPanel(new GridLayout(2,1));

        lblTimer.setHorizontalAlignment(SwingConstants.CENTER);
        lblTimer.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        lblTimer.setForeground(Color.RED); // 타이머는 빨간색 강조

        lblQuestion.setHorizontalAlignment(SwingConstants.CENTER);
        lblQuestion.setFont(new Font("맑은 고딕", Font.BOLD, 20));

        top.add(lblTimer);
        top.add(lblQuestion);
        p.add(top, BorderLayout.NORTH);

        // 중앙 입력 영역
        JPanel cen = new JPanel();
        cen.add(new JLabel("정답:"));
        answerField.setHorizontalAlignment(JTextField.CENTER);
        cen.add(answerField);
        p.add(cen, BorderLayout.CENTER);

        // 하단 버튼 영역
        JButton btnSub = new JButton("제출");
        JButton btnStop = new JButton("중단");

        btnSub.addActionListener(e -> checkAnswer()); // 정답 확인
        btnStop.addActionListener(e -> stopQuiz());   // 퀴즈 포기
        btnStop.setBackground(Color.PINK);

        // 엔터키 쳐도 제출되도록 이벤트 추가
        answerField.addActionListener(e -> checkAnswer());

        JPanel botPanel = new JPanel();
        botPanel.add(btnSub);
        botPanel.add(btnStop);
        p.add(botPanel, BorderLayout.SOUTH);

        return p;
    }

    /**
     * [퀴즈 중단]
     * 퀴즈 도중 '중단' 버튼을 눌렀을 때의 처리입니다.
     * 타이머를 멈추고 목록 화면으로 돌아갑니다.
     */
    private void stopQuiz() {
        if(timer != null) timer.stop();
        JOptionPane.showMessageDialog(this, "퀴즈를 중단합니다.");
        cardLayout.show(contentPanel, "LIST");
        refreshList(); // 중단 후 목록 갱신
    }

    /**
     * [목록 갱신]
     * VocManager에게 오답 리스트를 요청해서 테이블 데이터를 다시 그립니다.
     */
    public void refreshList() {
        Vector<Word> list = vocManager.getWrongList();
        tableModel.setRowCount(0); // 기존 테이블 내용 초기화

        if(!list.isEmpty()) {
            for(Word w : list) {
                String engSpace = "    " + w.getEng(); // 여백 추가
                String korSpace = "    " + w.getKor();
                tableModel.addRow(new Object[]{engSpace, korSpace});
            }
        }
    }

    /**
     * [오답 퀴즈 시작 로직]
     * 1. 오답 데이터가 있는지 확인
     * 2. 사용자 입력값(문제 수) 검증
     * 3. 문제 랜덤 추출 및 화면 전환
     */
    private void startWrongQuiz() {
        Vector<Word> wrongList = vocManager.getWrongList();

        // 1. 데이터 검증
        if(wrongList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "오답 데이터가 없습니다.");
            return;
        }

        // 2. 입력값 파싱 및 예외 처리
        int count = 0;
        try {
            count = Integer.parseInt(tfQuizCount.getText().trim());
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "문제 수는 숫자여야 합니다.");
            return;
        }

        // 입력된 문제 수가 전체 오답 수보다 많으면 전체를 출제
        if (count > wrongList.size()) count = wrongList.size();
        if (count <= 0) count = wrongList.size();

        // 3. 퀴즈 리스트 준비
        quizList = new ArrayList<>(wrongList);
        Collections.shuffle(quizList); // 순서 섞기
        quizList = quizList.subList(0, count); // 개수만큼 자르기

        // 변수 초기화 후 퀴즈 화면으로 전환
        currentIndex = 0;
        correctCount = 0;
        cardLayout.show(contentPanel, "QUIZ");

        nextQuestion(); // 첫 문제 시작
    }

    /**
     * [다음 문제 출제]
     * 남은 문제가 있으면 문제를 화면에 표시하고 타이머를 시작합니다.
     * 문제가 없으면 종료 처리를 합니다.
     */
    private void nextQuestion() {
        // 모든 문제를 다 풀었는지 확인
        if(currentIndex >= quizList.size()) {
            JOptionPane.showMessageDialog(this, "퀴즈 끝! 점수: " + correctCount);
            cardLayout.show(contentPanel, "LIST"); // 목록 화면으로 복귀
            refreshList(); // 갱신된 오답 목록 불러오기
            return;
        }

        // 현재 문제 가져오기
        Word w = quizList.get(currentIndex);
        lblQuestion.setText("뜻: " + w.getKor());
        answerField.setText("");
        answerField.requestFocus(); // 바로 입력할 수 있게 포커스 이동

        startTimer(); // 제한시간 카운트 시작
    }

    /**
     * [타이머 시작]
     * 1초마다 감소하는 타이머를 실행합니다.
     * 시간이 0이 되면 자동으로 다음 문제로 넘어갑니다.
     */
    private void startTimer() {
        // 기존에 돌고 있는 타이머가 있다면 중지 (중복 실행 방지)
        if(timer!=null) timer.stop();

        timeLeft = 10; // 10초로 초기화
        lblTimer.setText("시간: " + timeLeft);

        // 1000ms(1초)마다 실행되는 이벤트 정의
        timer = new Timer(1000, e-> {
            timeLeft--;
            lblTimer.setText("시간: " + timeLeft);

            // 시간 초과 시 처리
            if(timeLeft<=0) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "시간 초과!");
                currentIndex++;
                nextQuestion();
            }
        });
        timer.start();
    }

    /**
     * [정답 체크]
     * 사용자가 입력한 답과 정답을 비교합니다.
     */
    private void checkAnswer() {
        if(timer!=null) timer.stop(); // 제출했으니 타이머 정지

        Word w = quizList.get(currentIndex);

        // 대소문자 무시하고 비교
        if(answerField.getText().trim().equalsIgnoreCase(w.getEng())) {
            JOptionPane.showMessageDialog(this, "정답!");
            correctCount++;

            vocManager.removeWrongAnswer(w); // 오답 퀴즈를 맞히면 오답노트 목록에서 영구 삭제함

        } else {
            String msg = "오답입니다!\n\n[정답 정보]\n단어 : " + w.getEng() + "\n뜻   : " + w.getKor();
            JOptionPane.showMessageDialog(this, msg, "결과", JOptionPane.ERROR_MESSAGE);
            // 틀리면 삭제하지 않고 유지함
        }

        currentIndex++;
        nextQuestion();
    }
}