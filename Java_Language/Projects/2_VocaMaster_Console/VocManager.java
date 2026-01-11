package Project_ver1;

import java.io.*;
import java.util.*;

public class VocManager {
    String userName;
    Vector<Word> voc = new Vector<>();
    Vector<Word> wrongAnswers = new Vector<>();
    static Scanner scan = new Scanner(System.in);
    private String currentFileName;
    private Random rand = new Random();


    VocManager(String userName) {
        this.userName = userName;
    }

    void addWord(String eng, String kor){
        this.voc.add(new Word(eng, kor));
        sortVoc();
    }

    /**
     * [단어장 파일 읽기 및 초기화]
     * 지정된 파일에서 단어를 읽어와 단어장을 생성합니다.
     * 파일 내의 북마크 표시(*) 유무도 확인하여 처리합니다.
     *
     * @param fileName 읽어올 파일 경로
     */
    void makeVoc(String fileName) {
        this.currentFileName = fileName;

        Scanner file = null;
        try {
            file = new Scanner(new File(fileName));
            // 1. 파일 내용을 한 줄씩 읽어 단어와 뜻으로 분리
            while (file.hasNextLine()) {
                String str = file.nextLine().trim();
                if(str.isEmpty()) continue;

                String[] temp = str.split("\t");
                if(temp.length < 2)
                    continue; // 최소 2개 필드(eng, kor)가 있는지 확인
                String eng = temp[0].trim();
                String kor = temp[1].trim();

                boolean bookmarked = false; // 북마크 상태 초기화

                // 2. 북마크 정보 확인 (3번째 필드에 '*'가 있는지)
                if (temp.length >= 3) {
                    String marker = temp[2].trim();
                    if(marker.equals("*")) {
                        bookmarked = true;
                    }
                }
                // 3개 인자를 받는 Word 생성자를 통해 객체 생성
                this.voc.add(new Word(eng, kor, bookmarked));
            }
            System.out.println(this.userName+"님의 단어장 생성이 완료되었습니다.");

            sortVoc(); // 초기 정렬

        } catch (FileNotFoundException e) {
            System.out.println("파일을 찾을 수 없습니다. 프로그램을 종료합니다.");
            return;
        } finally {
            if (file != null) {
                file.close();
            }
        }
        this.menu(); // 초기화 완료 후 메인 메뉴 실행
    }

    void menu() {
        int choice = 0;
        while (choice != 11) {
            System.out.println("\n------" + userName + "의 단어장 -------");
            System.out.print("(1)단어 검색 ");
            System.out.print("(2)단어 추가 ");
            System.out.print("(3)단어 수정 ");
            System.out.println("(4)단어 삭제 ");
            System.out.print("(5)단어장 저장 ");
            System.out.print("(6)북마크 모드 ");
            System.out.print("(7)북마크 보기 ");
            System.out.println("(8)퀴즈 모드 ");
            System.out.print("(9)오답노트 저장/보기 ");
            System.out.print("(10)오답노트 퀴즈 ");
            System.out.println("(11)종료 ");
            System.out.print("메뉴를 선택하세요 : ");

            try {
                choice = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("오류: 1~11 사이의 숫자를 입력해주세요.");
                continue;
            }
            System.out.println();

            switch (choice) {
                case 1-> searchVoc(); //단어 검색
                case 2-> addWordMenu(); //단어 추가
                case 3-> modifyWordMenu(); //단어 수정
                case 4-> deleteWordMenu(); //단어 삭제
                case 5-> saveVoc(); //단어장 저장
                case 6-> bookmarkMode(); //북마크 모드
                case 7-> showBookmarked(); //북마크 보기
                case 8 -> quizMode(); //퀴즈 모드
                case 9-> wrong(); //오답노트 저장/보기
                case 10-> wrongQuiz(); // 오답노트 퀴즈
                case 11-> {
                    System.out.println("단어장을 자동 저장합니다.");
                    saveVoc();
                    System.out.println(userName + "의 단어장 프로그램을 종료합니다.");
                }
                default -> System.out.println("오류: 메뉴에 없는 번호입니다.");
            }

        }
    }

    /**
     * [단어 검색 유틸리티]
     * 단어장(voc)에서 특정 영어 단어를 검색하여 반환합니다.
     *
     * @param engWord 검색할 영어 단어
     * @return 찾은 Word 객체 (없으면 null 반환)
     */
    public Word findWord(String engWord) {
        for (Word word : voc) {
            if (word.eng.equalsIgnoreCase(engWord)) {
                return word;
            }
        }
        return null; // 못 찾으면 null 반환
    }

    /**
     * [단어장 정렬]
     * 버블 정렬 알고리즘을 사용하여 단어장을 알파벳 순으로 정렬합니다.
     */
    public void sortVoc() {
        int size = voc.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                Word w1 = voc.get(j);
                Word w2 = voc.get(j + 1);

                // w1의 영어단어가 w2보다 사전 순으로 뒤에 있다면 서로 자리를 바꿈
                if (w1.eng.compareToIgnoreCase(w2.eng) > 0) {
                    voc.set(j, w2);
                    voc.set(j + 1, w1);
                }
            }
        }
    }

    /**
     * [1. 단어 검색]
     * 영단어를 입력받아 정확히 일치하는 단어와
     * 해당 스펠링으로 시작하는 부분 일치 단어를 모두 출력합니다.
     */
    public void searchVoc() {
        System.out.println("------ 단어 검색 ------");
        System.out.print("검색할 단어를 입력하세요 (영단어) : ");
        String sWord = scan.nextLine().toLowerCase();

        Word exactMatch = null; // 정확히 일치하는 단어
        Vector<Word> partialMatches = new Vector<>(); // 부분 일치 단어

        for (Word word : voc) {
            String targetWordLower = word.eng.toLowerCase();

            // 1-1. 정확히 일치하는지 확인
            if (targetWordLower.equals(sWord)) {
                exactMatch = word;
                continue;
            }

            // 1-2. 부분 일치 확인 (길이가 검색어보다 짧으면 패스)
            if (sWord.length() > targetWordLower.length()) {
                continue;
            }

            boolean isMatch = true;
            for (int i = 0; i < sWord.length(); i++) {
                if (sWord.charAt(i) != targetWordLower.charAt(i)) {
                    isMatch = false;
                    break;
                }
            }

            // 모든 글자가 일치하면 리스트에 추가
            if (isMatch) {
                partialMatches.add(word);
            }
        }

        // 2. 검색 결과 출력

        // 2-1. 정확히 일치하는 단어 출력
        if (exactMatch != null) {
            System.out.println(exactMatch.eng + " 단어의 뜻 : " + exactMatch.kor);
        }

        // 2-2. 부분 일치하는 단어들 출력
        if (!partialMatches.isEmpty()) {
            System.out.println("'" + sWord + "-'(으)로 시작하는 단어:");
            for (Word w : partialMatches) {
                System.out.println(w);
            }
        }

        // 2-3. 아무 결과도 찾지 못했을 때
        if (exactMatch == null && partialMatches.isEmpty()) {
            System.out.println("'" + sWord + "'(으)로 시작하거나 일치하는 단어를 찾을 수 없습니다.");
        }
    }

    /**
     * [2. 단어 추가]
     * 사용자로부터 영어 단어와 뜻을 입력받아 단어장에 추가합니다.
     * 이미 존재하는 단어일 경우 추가하지 않습니다.
     */
    public void addWordMenu() {
        System.out.println("------ 단어 추가 ------");
        System.out.print("추가할 단어 (영단어) : ");
        String eng = scan.nextLine().trim();

        // 1. 중복 검사
        if (findWord(eng) != null) {
            System.out.println("'" + eng + "'는 이미 단어장에 있습니다.");
            return;
        }

        System.out.print("단어의 뜻 : ");
        String kor = scan.nextLine().trim();

        // 2. 단어 추가 실행
        addWord(eng, kor);
        System.out.println("단어를 추가했습니다.");
    }

    /**
     * [3. 단어 수정]
     * 기존 단어의 뜻을 수정합니다.
     */
    public void modifyWordMenu() {
        System.out.println("------ 단어 수정 ------");
        System.out.print("수정할 단어 (영단어) : ");
        String eng = scan.nextLine().trim();

        Word wordToModify = findWord(eng);

        if (wordToModify != null) {
            System.out.print("새로운 뜻 : ");
            String newKor = scan.nextLine().trim();
            wordToModify.setKor(newKor); // 수정 적용
            System.out.println("단어의 뜻을 수정했습니다.");
        } else {
            System.out.println("'" + eng + "'(을)를 단어장에서 찾을 수 없습니다.");
        }
    }

    /**
     * [4. 단어 삭제]
     * 단어장에서 특정 단어를 찾아 삭제합니다.
     */
    public void deleteWordMenu() {
        System.out.println("------ 단어 삭제 ------");
        System.out.print("삭제할 단어 (영단어) : ");
        String eng = scan.nextLine().trim();

        Word wordToDelete = findWord(eng);

        if (wordToDelete != null) {
            voc.remove(wordToDelete); // Vector에서 객체 제거
            System.out.println("단어 '" + eng + "'를 삭제했습니다.");
        } else {
            System.out.println("'" + eng + "'(을)를 단어장에서 찾을 수 없습니다.");
        }
    }

    /**
     * [5. 단어장 저장]
     * 현재 메모리에 있는 단어장 목록을 파일에 덮어씁니다.
     * 북마크 정보도 함께 저장됩니다.
     */
    public void saveVoc() {
        if (this.currentFileName == null) {
            System.out.println("오류: 파일 경로가 지정되지 않아 저장할 수 없습니다.");
            return;
        }

        System.out.println("'" + this.currentFileName + "' 파일에 저장 중...");

        // try-with-resources 구문으로 자동 close 처리
        try (PrintWriter pw = new PrintWriter(new FileWriter(this.currentFileName))) {
            for (Word word : voc) {
                pw.println(word.toFileString());
            }
            System.out.println("총 " + voc.size() + "개의 단어를 저장했습니다.");
        } catch (IOException e) {
            System.out.println("파일 저장 중 오류 발생: " + e.getMessage());
        }
    }

    /**
     * [6. 북마크 모드]
     * 특정 단어의 북마크 상태를 토글(설정/해제)합니다.
     */
    public void bookmarkMode() {
        System.out.println("------ 북마크 모드 ------");
        System.out.print("북마크 상태를 변경할 영단어를 입력하세요: ");
        String eng = scan.nextLine().trim();

        Word word = findWord(eng);// findWord 메서드 재활용

        if (word != null) {
            // 현재 북마크 상태의 반대로 설정
            word.setBookmarked(!word.isBookmarked());
            if (word.isBookmarked()) {
                System.out.println("'" + eng + "' 단어를 북마크했습니다.");
            } else {
                System.out.println("'" + eng + "' 단어의 북마크를 해제했습니다.");
            }
        } else {
            System.out.println("단어장에 없는 단어입니다.");
        }
    }

    /**
     * [7. 북마크 보기]
     * 현재 단어장에서 북마크(*)된 단어들만 모아서 출력합니다.
     */
    public void showBookmarked() {
        System.out.println("------ 북마크된 단어 목록 ------");

        if (voc.isEmpty()) {
            System.out.println("단어장이 비어 있습니다.");
            return;
        }

        boolean found = false;
        int count = 0;

        // 단어장을 순회하며 북마크된 단어 출력
        for (int i = 0; i < voc.size(); i++) {
            Word w = voc.get(i);
            if (w.isBookmarked()) {
                System.out.println((count + 1) + ") " + w);
                found = true;
                count++;
            }
        }

        if (!found) {
            System.out.println("북마크된 단어가 없습니다.");
        } else {
            System.out.println("총 " + count + "개의 단어가 북마크되어 있습니다.");
        }
    }

    /**
     * [8. 퀴즈 모드 메인 메서드]
     * 객관식 및 주관식 퀴즈의 개수를 설정하고 순차적으로 실행합니다.
     * 퀴즈가 종료되면 최종 정답 수와 결과를 출력합니다.
     */
    private void quizMode() {
        System.out.println("------ 퀴즈 모드 ------");

        // 1. 퀴즈 진행 가능 여부 확인 (단어장이 비어있으면 실행 불가)
        if (voc.size() == 0) {
            System.out.println("오류: 단어장에 단어가 1개 이상 있어야 퀴즈를 실행할 수 있습니다.");
            return;
        }

        int numObjective = 0; // 객관식  문제 수
        int numSubjective = 0; // 주관식 문제 수

        // 2. 객관식 퀴즈 설정
        System.out.println("[객관식 퀴즈 설정]");
        if (voc.size() < 4) {
            System.out.println("단어장에 단어가 4개 미만이라 객관식 퀴즈를 진행할 수 없습니다.");
        } else {
            // 사용자로부터 문제 개수 입력받기 (최대: 현재 단어장 크기)
            numObjective = getQuizNum("객관식", voc.size());
        }

        // 3. 주관식 퀴즈 설정
        System.out.println("\n[주관식 퀴즈 설정]");
        numSubjective = getQuizNum("주관식", voc.size());

        // 두 퀴즈 모두 0문제를 선택했다면 메뉴로 복귀
        if (numObjective == 0 && numSubjective == 0) {
            System.out.println("메뉴로 돌아갑니다.");
            return;
        }

        int totalCorrect = 0; // 전체 맞은 개수 합계
        int totalQuestions = numObjective + numSubjective; // 전체 문제 수 합계

        // 4. 객관식 퀴즈 실행
        if (numObjective > 0) {
            System.out.println("\n--- 객관식 퀴즈를 시작합니다. (총 " + numObjective + "문제) ---");
            totalCorrect += runObjectiveQuiz(numObjective);
        }

        // 5. 주관식 퀴즈 실행
        if (numSubjective > 0) {
            System.out.println("\n--- 주관식 퀴즈를 시작합니다. (총 " + numSubjective + "문제) ---");
            // 오답 발생 시 오답노트(this.wrongAnswers)에 추가하도록 전달
            totalCorrect += runSubjectiveQuiz(this.voc, numSubjective, this.wrongAnswers);
        }

        // 6. 최종 결과 출력
        System.out.println("\n========== 퀴즈 최종 결과 ==========");
        System.out.println("총 " + totalQuestions + "문제 중 " + totalCorrect + "문제를 맞혔습니다.");
        System.out.println("==================================");

    }

    /**
     * [객관식 퀴즈 실행 메서드]
     * 4지 선다형 퀴즈를 출제하고 맞은 개수를 반환합니다.
     * 틀린 문제는 자동으로 오답노트에 추가됩니다.
     *
     * @param numQuiz 출제할 문제 개수
     * @return 맞은 문제 개수
     */
    public int runObjectiveQuiz(int numQuiz) {
        int correctCount = 0;
        int vocSize = voc.size(); // 단어장 전체 크기

        if (numQuiz > vocSize) numQuiz = vocSize; // vocSize보다 크면, 단어장 크기만큼만 출제

        // 중복 방지를 위해 Set을 사용하여 랜덤 인덱스 추출
        Set<Integer> index = new HashSet<>();

        // 0부터 (단어장 크기-1) 사이의 랜덤 인덱스 뽑기(문제 수만큼 채워질 때까지 반복)
        while (index.size() < numQuiz) {
            index.add(rand.nextInt(vocSize));
            // Set 특성상 중복된 인덱스라면 .add()가 자동으로 무시됨
        }

        int qNum = 1; // 문제 번호 표시용

        // 뽑힌 문제들을 하나씩 출제
        for (int answerIndex : index) {

            Word correct = voc.get(answerIndex); // 정답 단어 객체

            // --- 4개의 보기 생성 로직 ---
            List<Word> options = new ArrayList<>();
            options.add(correct); // 1. 정답 단어를 보기에 먼저 추가

            // 2. 나머지 3개의 오답 보기 랜덤 추출
            while (options.size() < 4) {
                Word candidate = voc.get(rand.nextInt(vocSize));
                // 이미 보기에 포함된 단어(정답 포함)는 제외하고 추가
                if (!options.contains(candidate)) {
                    options.add(candidate);
                }
            }

            // 3. 보기 순서 섞기
            List<Word> randomized = new ArrayList<>();
            for (Word w : options) {
                // randomized 리스트의 0 ~ size 사이의 랜덤 위치를 뽑음
                int pos = rand.nextInt(randomized.size() + 1);
                randomized.add(pos, w); // 뽑힌 위치(pos에 단어(w) 삽입
            }

            // 문제 출력 및 정답 찾기
            System.out.println("\nQ " + (qNum++) + ". " + correct.eng + "의 뜻은?");
            int answerNum = 0; // 정답 번호 저장용

            // 보기 출력
            for (int i = 0; i < randomized.size(); i++) {
                System.out.println((i + 1) + ") " + randomized.get(i).kor);
                // 현재 출력하는 단어가 정답 단어라면 번호 저장
                if (randomized.get(i) == correct) {
                    answerNum = i + 1;
                }
            }

            // 사용자 정답 입력 받기 (유효성 검사 포함)
            int userAnswer = 0;
            while (true) {
                System.out.print("답 (1-4): ");
                try {
                    userAnswer = Integer.parseInt(scan.nextLine());
                    if (userAnswer >= 1 && userAnswer <= 4)
                        break; // 1~4 입력 시 반복 종료
                    System.out.println("오류: 1~4 사이 숫자 입력");
                } catch (NumberFormatException e) {
                    System.out.println("오류: 숫자를 입력하세요.");
                }
            }

            // 채점
            if (userAnswer == answerNum) {
                System.out.println("정답입니다!");
                correctCount++;
            } else {
                System.out.println("오답입니다. (정답: " + answerNum + "번 " + correct.kor + ")");
                this.wrongAnswers.add(correct); //오답노트에 추가
            }
        }
        return correctCount;
    }

    /**
     * [주관식 퀴즈 실행 메서드]
     * 한글 뜻을 보고 영어 단어를 입력하는 퀴즈입니다.
     *
     * @param voc 퀴즈를 출제할 대상 단어장
     * @param numQuiz 출제할 문제 개수
     * @param wrongListDestination 틀린 단어를 저장할 리스트 (null 가능)
     * @return 맞은 문제 개수
     */
    public int runSubjectiveQuiz(Vector<Word> voc, int numQuiz, Vector<Word> wrongListDestination) {
        int correctCount = 0;
        int vocSize = voc.size();

        if (numQuiz > vocSize) numQuiz = vocSize; // 문제 수 예외 처리

        // 중복 방지를 위해 Set을 사용하여 랜덤 인덱스 추출
        Set<Integer> index = new HashSet<>();

        while (index.size() < numQuiz) {
            index.add(rand.nextInt(vocSize));
        }

        int qNum = 1; // 문제 번호 표시용

        // 문제 출제 루프
        for (int i : index) {
            Word w = voc.get(i); // 해당 인덱스의 단어를 단어장에서 가져옴

            System.out.println("\nQ " + (qNum++) + ". " + w.kor + "의 뜻을 가진 영단어는?");
            System.out.print("답: ");
            String answer = scan.nextLine().trim(); // 공백 제거 후 입력 받기

            // 채점 (문자열 일치 여부 비교)
            if (answer.equals(w.eng)) {
                System.out.println("정답");
                correctCount++;
            } else {
                System.out.println("오답 (정답: " + w.eng + ")");
                // 오류 방지 (오답노트 리스트가 유효한지 확인)
                if (wrongListDestination != null) {
                    wrongListDestination.add(w); // 틀린 단어를 파라미터로 받은 리스트에 추가
                }
            }
        }
        return correctCount;
    }

    /**
     * [퀴즈 개수 입력 헬퍼 메서드]
     * 사용자로부터 유효한 퀴즈 개수 입력을 받습니다.
     * * @param quizType 퀴즈 종류 이름
     * @param num 최대 입력 가능 개수 (현재 단어장 크기)
     * @return 사용자가 입력한 유효한 문제 개수 (0 입력 시 취소)
     */
    public int getQuizNum(String quizType, int num) {
        int numQuiz = 0;
        while (true) {
            System.out.print( quizType + " 문제 개수 (1~" + num + ", 0=취소): ");
            try {
                numQuiz = Integer.parseInt(scan.nextLine());
                // 0 입력 시 취소 처리
                if (numQuiz == 0) {
                    System.out.println(quizType + " 퀴즈를 취소합니다.");
                    return 0;
                }
                // 유효 범위 확인
                if (numQuiz > 0 && numQuiz <= num) {
                    return numQuiz;
                } else {
                    System.out.println("오류: 1에서 " + num + " 사이의 숫자를 입력해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("오류: 숫자를 입력해주세요.");
            }
        }
    }

    /**
     * [9. 오답노트 보기]
     * 기존 오답 파일을 읽어와, 현재 퀴즈에서 발생한 오답을 기존 오답과 합칩니다.
     * 병합된 전체 오답 목록을 다시 파일에 덮어쓰기로 저장합니다.
     * 사용자에게 전체 오답 목록을 콘솔에 출력하여 보여줍니다.
     */
    public void wrong() {
        System.out.println("----- 오답노트 보기/저장 -----");
        String wrongFileName = "res/wrong_words.txt";

        // 기존 오답 파일을 읽어옴
        Vector<Word> totalWrongWords = new Vector<>();
        File f = new File(wrongFileName);

        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(f);
            while (fileScanner.hasNextLine()) {
                String str = fileScanner.nextLine().trim(); //한 줄씩 읽고 앞뒤 공백 제거
                if (str.isEmpty()) continue;
                String[] temp = str.split("\t"); //\t를 기준으로 영어단어와 한글뜻 분리
                if (temp.length < 2) continue;
                //파일에서 읽어온 단어 Word 객체로 만들어 리스트에 추가
                totalWrongWords.add(new Word(temp[0].trim(), temp[1].trim()));
            }
        } catch (FileNotFoundException e) {
            // 파일이 없는 것은 처음 실행 시 정상이므로 오류 메시지 삭제
        } finally {
            if (fileScanner != null) fileScanner.close();
        }

        // 현재 퀴즈의 오답(this.wrongAnswers)을 합치기 (누적)
        int newWordsAdded = 0; //새로 추가된 오답 개수
        for (Word newWord : this.wrongAnswers) {
            boolean exists = false;

            for (Word oldWord : totalWrongWords) {
                // 영어 단어 기준으로 중복 검사 (대소문자 무시)
                if (oldWord.eng.equalsIgnoreCase(newWord.eng)) {
                    exists = true;
                    break;
                }
            }
            // 기존 오답노트에 없는 새로운 단어만 추가
            if (!exists) {
                totalWrongWords.add(newWord);
                newWordsAdded++;
            }
        }

        this.wrongAnswers.clear();

        if (newWordsAdded > 0) {
            System.out.println("새로운 오답 " + newWordsAdded + "개가 오답노트에 추가되었습니다.");
        }

        // 병합된 전체 오답 리스트를 파일에 덮어쓰기 함
        // this.wrongAnswers 대신 누적된 totalWrongWords를 기준으로 비어있는지 확인
        if (totalWrongWords.isEmpty()) {
            System.out.println("오답노트가 비어있습니다.");
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(wrongFileName))) {
            for (Word word : totalWrongWords) {
                pw.println(word.eng + "\t" + word.kor);
            }
            System.out.println("오답노트가 저장되었습니다.");
        } catch (IOException e) {
            System.out.println("오답노트 저장 중 오류 발생" + e.getMessage());
        }

        // 콘솔에 전체 오답 목록을 출력
        System.out.println("\n----- 오답 목록 -----");
        System.out.println("총 " + totalWrongWords.size() + "개의 틀린 단어가 있습니다.");
        for (Word word : totalWrongWords) {
            System.out.println(word);
        }
    }

    /**
     * [10. 오답노트 퀴즈]
     * 오답노트 파일(wrong_words.txt)에 저장된 단어들 중 일부를 랜덤으로 뽑아
     * 주관식 퀴즈를 진행하는 기능입니다.
     *
     * 퀴즈를 본 단어는 맞으면 제거되고, 틀리면 다시 오답노트로 남습니다.
     * 퀴즈를 보지 않은 단어는 그대로 유지하여 오답노트의 '지능 보존'을 구현합니다.
     */
    public void wrongQuiz() {
        System.out.println("------ 오답노트 퀴즈 ------");
        String wrongFileName = "res/wrong_words.txt";

        // 1. 오답노트 파일 읽기
        Vector<Word> wrongList = new Vector<>();
        File f = new File(wrongFileName);

        // 파일 존재 여부 먼저 확인
        try (Scanner s = new Scanner(f)) {
            // 정상적으로 열리면 아무 것도 안 함
        } catch (FileNotFoundException e) {
            System.out.println("오답노트 파일이 없습니다. 먼저 일반 퀴즈를 풀어주세요.");
            return;
        }

        // 실제 단어 읽기
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(f);
            while (fileScanner.hasNextLine()) {
                String str = fileScanner.nextLine().trim();
                if (str.isEmpty()) continue;

                // \t 로 구분된 영어/한글 분리
                String[] temp = str.split("\t");
                if (temp.length < 2) continue;

                wrongList.add(new Word(temp[0], temp[1]));
            }
        } catch (Exception e) {
            System.out.println("오답 파일 읽기 오류: " + e.getMessage());
            return;
        } finally {
            if (fileScanner != null) fileScanner.close();
        }

        // 오답노트가 비어있으면 종료
        if (wrongList.isEmpty()) {
            System.out.println("오답노트가 비어있습니다!");
            return;
        }

        // 2. 퀴즈 개수 입력받기
        int numQuiz = getQuizNum("주관식", wrongList.size());
        if (numQuiz == 0) {
            System.out.println("오답노트 퀴즈를 취소합니다.");
            return;
        }

        /**
         * 3. 퀴즈 볼 단어와 퀴즈 안 볼 단어 분리
         *    - wordsToQuiz : 선택된 문제 단어
         *    - wordsToKeep : 퀴즈에서 제외할 단어 (오답노트 유지)
         */
        Vector<Word> wordsToQuiz = new Vector<>();
        Vector<Word> wordsToKeep = new Vector<>();
        Set<Integer> index = new HashSet<>();
        int listSize = wrongList.size();

        // 퀴즈 볼 단어 인덱스 랜덤 선택
        while (index.size() < numQuiz) {
            index.add(rand.nextInt(listSize));
        }

        // wrongList를 두 개의 리스트로 분리
        for (int i = 0; i < listSize; i++) {
            Word w = wrongList.get(i);
            if (index.contains(i)) {
                wordsToQuiz.add(w);
            } else {
                wordsToKeep.add(w);
            }
        }

        /**
         * 4. 실제 퀴즈 실행
         *    - runSubjectiveQuiz() 재사용
         *    - 틀린 단어는 stillWrongWord에 저장됨
         */
        Vector<Word> stillWrongWord = new Vector<>();
        int correctCount = runSubjectiveQuiz(wordsToQuiz, numQuiz, stillWrongWord);

        /**
         * 5. 오답노트 갱신
         *    오답노트는 아래 두 종류만 남긴다.
         *    - 이번에 퀴즈를 보지 않은 단어(wordsToKeep)
         *    - 이번 퀴즈에서 또 틀린 단어(stillWrongWord)
         */
        try (PrintWriter pw = new PrintWriter(new FileWriter(wrongFileName))) {
            // 퀴즈 안 본 단어 추가
            for (Word word : wordsToKeep) {
                pw.println(word.eng + "\t" + word.kor);
            }
            // 퀴즈에서 틀린 단어 추가
            for (Word word : stillWrongWord) {
                pw.println(word.eng + "\t" + word.kor);
            }
        } catch (IOException e) {
            System.out.println("오답노트 저장 중 오류 발생: " + e.getMessage());
        }

        // 6. 최종 결과 출력
        System.out.println("\n========== 오답 퀴즈 최종 결과 ==========");
        System.out.println("퀴즈 본 " + numQuiz + "개 단어 중 " + correctCount + "개를 맞혔습니다!");

        int remainingCount = wordsToKeep.size() + stillWrongWord.size();
        System.out.println(remainingCount + "개의 단어가 오답노트에 남았습니다.");
        System.out.println("========================================");
    }

}