package Project_ver1;

/**
 * [단어 객체 클래스]
 * 영어 단어, 한글 뜻, 그리고 중요 표시(북마크) 정보를 담는 클래스입니다.
 * 단어장 프로그램에서 데이터 하나하나를 담당합니다.
 */
public class Word {
    String eng;        // 영어 단어
    String kor;        // 한글 뜻
    boolean bookmarked; // 북마크 여부 (true=중요, false=일반)

    /**
     * [기본 생성자]
     * 사용자가 직접 새 단어를 추가할 때 사용합니다.
     * 북마크는 기본적으로 '안 함(false)' 상태로 시작합니다.
     * * @param eng 영어 단어
     * @param kor 한글 뜻
     */
    public Word(String eng, String kor) {
        super();
        this.eng = eng;
        this.kor = kor;
        this.bookmarked = false;
    }

    /**
     * [파일 로딩용 생성자 (오버로딩)]
     * 파일에 저장된 단어를 읽어올 때 사용합니다.
     * 저장되어 있던 북마크 상태(*)까지 한 번에 설정할 수 있습니다.
     * * @param eng 영어 단어
     * @param kor 한글 뜻
     * @param bookmarked 북마크 상태 (true/false)
     */
    public Word(String eng, String kor, boolean bookmarked) {
        this.eng = eng;
        this.kor = kor;
        this.bookmarked = bookmarked;
    }

    public void setKor(String kor) {
        this.kor = kor;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    /**
     * [출력용 문자열 반환]
     * 콘솔 화면에 단어를 보여줄 때 사용됩니다.
     * 북마크된 단어는 앞에 "* " 표시가 붙습니다.
     */
    @Override
    public String toString() {
        String bookmarkIndicator = bookmarked ? "* " : "";
        return bookmarkIndicator + eng + " : " + kor;
    }

    /**
     * [파일 저장용 문자열 반환]
     * 단어장을 텍스트 파일로 저장할 때 사용하는 형식을 만듭니다.
     * 탭(\t)으로 단어와 뜻을 구분하고, 북마크된 경우 끝에 *을 붙입니다.
     * * @return 파일에 기록될 문자열
     */
    public String toFileString() {
        if (bookmarked) {
            return eng + "\t" + kor + "\t*";
        } else {
            return eng + "\t" + kor;
        }
    }
}
