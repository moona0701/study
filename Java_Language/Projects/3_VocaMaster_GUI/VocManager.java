package project2_ver1;

import java.io.*;
import java.util.*;

/**
 * [데이터 관리자 클래스]
 * 단어장 목록과 오답노트 데이터를 관리하는 클래스입니다.
 * 파일 읽기/쓰기, 단어 정렬, 검색 등의 핵심 기능을 담당합니다.
 */
public class VocManager {
    // 단어 목록을 저장하는 동적 배열 (Vector 사용)
    public Vector<Word> voc = new Vector<>();
    public Vector<Word> wrongAnswers = new Vector<>();

    public String userName;

    // 사용자별로 생성될 파일 경로
    private String userWordPath;
    private String userWrongPath;

    // 최초 실행 시 사용할 기본 단어장 경로
    private final String MASTER_WORD_PATH = "res/words.txt";

    public VocManager() {
    }

    /**
     * [사용자 설정 및 초기화]
     * 로그인한 사용자 이름으로 파일 경로를 설정하고,
     * 해당 사용자의 단어장과 오답노트 파일을 로드합니다.
     */
    public void setUserName(String name) {
        this.userName = name;
        // 사용자 이름에 따라 파일명을 다르게 설정 (ex: words_홍길동.txt)
        this.userWordPath = "res/words_" + name + ".txt";
        this.userWrongPath = "res/wrong_" + name + ".txt";

        initUserFiles();
    }

    /**
     * [파일 유무 확인 및 생성]
     * 사용자 전용 파일이 없으면 기본 파일을 복사하여 생성합니다.
     * 파일이 존재하면 내용을 메모리로 읽어옵니다.
     */
    private void initUserFiles() {
        voc.clear();
        wrongAnswers.clear();

        File userFile = new File(userWordPath);

        // 사용자 파일이 없으면 기본 단어장을 읽어서 새로 생성
        if (!userFile.exists()) {
            makeVoc(MASTER_WORD_PATH);
            saveVoc(); // 복사한 내용을 사용자 파일로 저장
        } else {
            makeVoc(userWordPath); // 기존 파일 읽기
        }

        loadWrongVoc(); // 오답노트도 로드
    }

    /**
     * [단어장 파일 읽기]
     * 텍스트 파일을 한 줄씩 읽어 단어 객체로 변환하여 리스트에 추가합니다.
     * 탭(\t) 문자를 기준으로 영단어, 뜻, 북마크 정보를 분리합니다.
     */
    public void makeVoc(String fileName) {
        File fileObj = new File(fileName);
        if (!fileObj.exists()) return;

        try (Scanner file = new Scanner(fileObj)) {
            while (file.hasNextLine()) {
                String str = file.nextLine().trim();
                if (str.isEmpty()) continue; // 빈 줄은 건너뜀

                // 탭으로 문자열 분리
                String[] temp = str.split("\t");
                if (temp.length < 2) continue; // 데이터 형식이 안 맞으면 패스

                String eng = temp[0].trim();
                String kor = temp[1].trim();

                // 3번째 항목이 '*'이면 북마크 설정된 것으로 판단
                boolean bk = (temp.length >= 3 && temp[2].trim().equals("*"));

                voc.add(new Word(eng, kor, bk));
            }
            sortVoc(); // 불러온 뒤 알파벳순 정렬
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * [단어장 파일 저장]
     * 현재 리스트에 있는 모든 단어 정보를 파일에 덮어씁니다.
     */
    public void saveVoc() {
        if (userWordPath == null) return;
        try (PrintWriter pw = new PrintWriter(new FileWriter(userWordPath))) {
            for (Word w : voc) {
                pw.println(w.toFileString()); // 한 줄씩 파일에 기록
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * [단어 추가]
     * 중복 검사를 수행한 뒤 새로운 단어를 추가합니다.
     * 추가 후에는 자동으로 정렬하고 파일에 저장합니다.
     */
    public boolean addWord(String eng, String kor) {
        // 이미 존재하는 단어인지 확인
        if (findWord(eng) != null) return false;

        voc.add(new Word(eng, kor));
        sortVoc(); // 순서 유지를 위해 정렬
        saveVoc(); // 데이터 보호를 위해 즉시 저장
        return true;
    }

    /**
     * [알파벳순 정렬]
     * Comparator를 사용하여 영단어 기준 오름차순으로 정렬합니다.
     */
    public void sortVoc() {
        Collections.sort(voc, new Comparator<Word>() {
            @Override
            public int compare(Word o1, Word o2) {
                // String의 compareToIgnoreCase 활용
                return o1.getEng().compareToIgnoreCase(o2.getEng());
            }
        });
    }

    /**
     * [단어 검색]
     * 영단어가 일치하는 객체를 찾아 반환합니다. (대소문자 무시)
     */
    public Word findWord(String eng) {
        for (Word w : voc) {
            if (w.getEng().equalsIgnoreCase(eng)) return w;
        }
        return null; // 없으면 null 반환
    }

    /**
     * [랜덤 단어 추출]
     * 퀴즈 출제를 위해 전체 단어 중 지정된 개수만큼을 무작위로 뽑습니다.
     */
    public List<Word> getRandomWords(int count) {
        if (voc.isEmpty()) return new ArrayList<>();

        // 원본 데이터를 건드리지 않기 위해 복사본 생성
        Vector<Word> temp = new Vector<>(voc);
        Collections.shuffle(temp); // 순서 섞기

        // 요청한 개수보다 데이터가 적으면 전체 반환
        if (count > temp.size()) count = temp.size();
        return temp.subList(0, count);
    }

    // --- 오답노트 관련 기능 ---

    /**
     * [오답 추가]
     * 틀린 단어를 오답노트 리스트에 추가하고 파일에 저장합니다.
     * 이미 오답노트에 있는 단어는 중복해서 추가하지 않습니다.
     */
    public void addWrongAnswer(Word word) {
        for (Word w : wrongAnswers) {
            if (w.getEng().equalsIgnoreCase(word.getEng())) return;
        }
        wrongAnswers.add(word);
        saveWrongVoc();
    }

    /**
     * [오답 삭제]
     * 오답 퀴즈를 맞혔을 때 목록에서 제거합니다.
     */
    public void removeWrongAnswer(Word word) {
        wrongAnswers.remove(word);
        saveWrongVoc();
    }

    public Vector<Word> getWrongList() { return wrongAnswers; }

    /**
     * [오답노트 저장]
     */
    public void saveWrongVoc() {
        if (userWrongPath == null) return;
        try (PrintWriter pw = new PrintWriter(new FileWriter(userWrongPath))) {
            for (Word w : wrongAnswers) {
                pw.println(w.toFileString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * [오답노트 로드]
     */
    public void loadWrongVoc() {
        if (userWrongPath == null) return;
        File fileObj = new File(userWrongPath);
        if (!fileObj.exists()) return;

        try (Scanner file = new Scanner(fileObj)) {
            while (file.hasNextLine()) {
                String str = file.nextLine().trim();
                if(str.isEmpty()) continue;
                String[] temp = str.split("\t");
                if (temp.length >= 2) {
                    wrongAnswers.add(new Word(temp[0].trim(), temp[1].trim()));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}