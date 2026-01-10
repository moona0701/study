# Java 프로그래밍

## [W1] 자바 시작 & 자료형

**[배운 내용]**

* **자바의 특징:** JVM(자바 가상 머신) 덕분에 OS(윈도우, 맥, 리눅스) 상관없이 다 돌아감.
* **프로그램 구조:** 모든 코드는 `class` 안에 있어야 함. `main` 메소드에서 시작함.
* **식별자 규칙:** 클래스 이름은 **대문자**로, 변수/메소드는 **소문자**로 시작.
* **자료형(Data Type):**
* 정수: `byte`, `short`, `int`, `long`
* 실수: `float`, `double`
* 문자: `char` (2바이트, 유니코드)
* 논리: `boolean` (T/F)

**[코드/개념 메모]**

* **기본 구조 (암기):**
```java
public class Hello { // 파일명과 클래스명 일치해야 함!!
    public static void main(String[] args) {
        System.out.println("Hello Java");
    }
}

```

* **형 변환 (Casting):**
* **자동 변환:** 작은 -> 큰 (`int` -> `double`). 데이터 손실 없음.
* **강제 변환:** 큰 -> 작은 (`double` -> `int`). `(int)3.14` 하면 `.14` 날아감. 값 깨질 수 있어서 조심해야 함.


* **문자형(char):** 작은따옴표 `' '` 사용. 한글 한 글자도 저장 가능 (`char c = '한';`).

**[주의/느낀점]**

* `main` 메소드(`public static void...`) 자동완성(`main` + Ctrl+Space) 있음.
* `boolean`에는 무조건 `true` 아니면 `false`만 들어감. 숫자 0이나 1 넣으면 에러.

---

## [W2] 입출력 & 제어문

**[배운 내용]**

* **출력:** `System.out.println()` (줄바꿈 O), `print()` (줄바꿈 X), `printf()` (서식 지정).
* **입력:** `Scanner` 클래스 사용. 외부에서 뭐 가져오려면 `import` 필요.
* **제어문:**
* 조건문: `if`, `else if`, `switch`
* 반복문: `for` (횟수 정해질 때), `while` (조건 중요할 때), `do-while` (무조건 1번 실행)
* **제어 키워드:** `break` (탈출), `continue` (건너뛰기).

**[코드/개념 메모]**

* **입력 받기 (Scanner):**
```java
import java.util.Scanner; // 1. 임포트 필수

public class InputTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // 2. 객체 생성

        int age = sc.nextInt();      // 정수 입력
        String name = sc.next();     // 단어 입력 (공백 전까지)
        String line = sc.nextLine(); // 한 줄 통째로 입력

        sc.close(); // 다 쓴 후 닫아주기
    }
}

```

* **버퍼 비우기 (중요):**
* 숫자 입력(`nextInt`) 받고 나서 바로 문자열(`nextLine`) 받으면, 엔터키가 문자열로 인식돼서 그냥 넘어가버림.
* 중간에 `sc.nextLine();` 한번 더 써서 엔터 처리해줘야 함.

* **Label을 이용한 break:**
* 중첩 반복문(`for` 안에 `for`)을 한 번에 빠져나가고 싶을 때 사용.
* `EXIT_LABEL: for(...)` 처럼 이름 붙이고 `break EXIT_LABEL;` 하면 됨.

**[주의/느낀점]**

* `Scanner` 쓸 때 `System.in` 안 넣으면 에러. 키보드 입력이라는 뜻이라 함.
* `switch`문 쓸 때 `break` 빼먹지 말기.

---

## [W3] 참조 타입과 배열 

**[배운 내용]**

* **메모리 구조:**
* **스택(Stack):** 기본 타입(`int`, `double` 등) 값, 변수 이름 저장.
* **힙(Heap):** 객체(`new`로 만든 거), 배열 저장. (여기가 중요)


* **참조 타입:** 변수에 실제 값이 아니라 **'주소(번지)'**가 들어감.
* **배열:** 같은 타입 데이터를 연속된 공간에 저장. `int[] arr = new int[5];`
* **null:** "참조하는 객체가 없다"는 뜻.

**[코드/개념 메모]**

* **배열 선언과 생성:**
```java
int[] arr1 = {1, 2, 3}; // 선언과 동시에 초기화
int[] arr2 = new int[5]; // 0으로 꽉 찬 방 5개 만듦
// arr2.length; -> 5 (괄호 없음 주의! 문자열은 length()임)

```

* **향상된 for문 (For-each):**
* 인덱스(`i`) 필요 없을 때 좋음.

```java
for (int num : arr1) {
    System.out.println(num); // 1, 2, 3 출력
}

```

* **값 복사 vs 주소 복사:**
* `int[] a = {1, 2}; int[] b = a;` -> `b`가 `a`랑 똑같은 주소를 바라봄.
* `b[0]` 바꾸면 `a[0]`도 바뀜. (얕은 복사(?))

**[주의/느낀점]**

* C언어에선 배열 크기 변수로 못 넣었는데, 자바는 `new int[size]` 됨. 
* `String` 비교할 때 `==` 쓰면 안 됨. 주소 비교라 무조건 `.equals()` .
* 배열 인덱스 `0`부터 시작이라 `length`까지 돌리면 에러.

---

## [W4] 클래스와 객체

**[배운 내용]**

* **클래스 vs 객체:**
* **클래스(붕어빵 틀):** 설계도. (`public class Car`)
* **객체(붕어빵):** 실제 메모리에 생성된 것. 인스턴스라고도 함. (`new Car()`)

* **구성 멤버:** 필드(변수), 생성자(초기화), 메소드(함수).
* **this:** 객체 자신을 가리키는 키워드. (주로 이름 겹칠 때 씀 `this.name = name`)
* **생성자(Constructor):** `new` 할 때 딱 한 번 호출됨. 리턴 타입 없음.

**[코드/개념 메모]**

* **기본 사용법:**
```java
public class Student {
    String name; // 필드

    // 생성자 (클래스 이름이랑 똑같아야 함)
    public Student(String name) {
        this.name = name; // this.필드 = 매개변수
    }
}
// 메인에서 쓸 때
Student s1 = new Student("Kim"); // new 키워드로 힙 영역에 생성

```

* **생성자 오버로딩:**
* 이름은 같은데 매개변수만 다르게 여러 개 만들 수 있음. (매개변수 없는 '기본 생성자'는 딴 거 만들면 자동으로 안 생김. 주의.)

**[주의/느낀점]**

* C언어 구조체랑 비슷한데 안에 함수(메소드)까지 들어가니까 신기.
* 객체는 무조건 new 해줘야 함.

---

## [W5] 메소드와 객체 배열

**[배운 내용]**

* **메소드 오버로딩:** 이름 같은 함수 여러 개 만들기 (매개변수 타입이나 개수가 달라야 함!).
* **매개변수 전달:**
* **기본 타입:** 값이 복사됨 (Call by Value). 메소드 안에서 바꿔도 원본 안 바뀜.
* **참조 타입(객체, 배열):** 주소가 복사됨. 메소드 안에서 바꾸면 **원본도 바뀜.**


* **객체 배열:** 객체 담는 배열. `Book[] books = new Book[3];`

**[코드/개념 메모]**

* **객체 배열 초기화 (매우 중요):**
```java
Book[] books = new Book[3]; // 1. 책꽂이(배열)만 산 거. 책은 없음.
// books[0].title = "Java"; // 여기서 에러 남 (Null Pointer)

// 2. 책(객체)을 사서 꽂아야 함
for(int i=0; i<books.length; i++) {
    books[i] = new Book(); // 이 과정 필수!
}

```

* **메소드 선언:**
* `void`: 리턴값 없음.
* `static`: 객체 생성 없이 바로 쓸 수 있음 (`main` 함수가 `static`인 이유).

**[주의/느낀점]**

* 객체 배열 만들면 끝인 줄 알았는데, 배열 안의 각 칸마다 또 `new` 해줘야 하는 거 몰라서 헤맴. **"배열 생성과 객체 생성 다름!!"**
* 메소드 인자로 배열 넘겼을 때, 안에서 값 바꾸면 밖에서도 바뀌는 거 확인. C언어 포인터 넘긴 거랑 똑같음.

---

## [W6] 상속과 추상 클래스

**[배운 내용]**

* **상속(`extends`):** 부모 클래스(Super)의 필드랑 메소드를 자식(Sub)이 물려받는 거. 코드 복붙 안 해도 돼서 좋음.
* **메소드 오버라이딩(@Override):** 부모가 준 기능을 고쳐 쓰는 거. (이름, 매개변수 똑같아야 함)
* **다형성:** 부모 타입 변수에 자식 객체 넣기 가능. (`Parent p = new Child();`)
* **추상 클래스(`abstract`):** "대충 이런 기능 있어"라고 껍데기만 만들어 둔 거. `new` 못 함. 상속받으면 무조건 구현해야 함.

**[코드/개념 메모]**

* **상속 기본 문법:**
```java
class Parent {
    int money = 10000;
}
class Child extends Parent { // extends 키워드 사용
    // money 변수 안 만들어도 10000원 있음
}

```

* **super 키워드:**
* `super.name`: 부모의 변수.
* `super()`: 부모의 생성자 호출. (자식 생성자 맨 윗줄에 무조건 있어야 함. 안 쓰면 컴파일러가 알아서 `super()` 넣어줌).

* **오버로딩 vs 오버라이딩:**
* **Overloading:** 이름만 같고 매개변수가 다름 (새로운 거 만듦).
* **Overriding:** 싹 다 똑같은데 내용만 바꿈 (덮어쓰기).

**[주의/느낀점]**

* `private` 붙은 건 상속 안 됨.
* 추상 클래스는 미완성이라 `new Animal();` 하면 에러 남. 꼭 상속받아서 완성시켜야 됨.
* 자바는 **다중 상속(부모 2명)** 안 됨. (`extends A, B` 불가). 

---

## [W7] 인터페이스 (Interface)

**[배운 내용]**

* **인터페이스:** "이 기능은 무조건 있어야 해"라고 강제하는 규격서. `class` 대신 `interface` 씀.
* **구현(`implements`):** 인터페이스를 가져다가 클래스 만드는 거. (`extends` 아님!)
* **다중 상속 가능:** 클래스 상속은 1개만 되는데, 인터페이스는 여러 개 구현 가능. (`implements A, B`)
* **익명 객체:** 이름 없는 1회용 객체. 이벤트 처리할 때 많이 씀.

**[코드/개념 메모]**

* **인터페이스 선언과 구현:**
```java
interface RemoteControl {
    void turnOn(); // 껍데기만 있음 (추상 메소드)
}

class TV implements RemoteControl { // implements 사용
    public void turnOn() { // 무조건 내용 채워야 함 (강제성)
        System.out.println("TV 켬");
    }
}

```

* **익명 구현 객체 :**
* 클래스 따로 만들기 귀찮을 때 즉석에서 만들어서 씀. 

```java
RemoteControl rc = new RemoteControl() {
    public void turnOn() {
        System.out.println("일회용 TV 켬");
    }
}; // 세미콜론 필수

```

**[주의/느낀점]**

* `extends`랑 `implements` 헷갈림.
* 클래스 -> 클래스: `extends`
* 인터페이스 -> 클래스: `implements`
* 인터페이스 -> 인터페이스: `extends` 
* 익명 객체 괄호 닫는 거 `});` 모양 주의.

---

## [W9] 예외처리

**[배운 내용]**

* **에러 vs 예외:**
* **Error:** 하드웨어 고장 같은 거. 수습 불가 (전원 꺼짐 등).
* **Exception:** 코드 잘못 짜서 나는 거. 수습 가능 (0으로 나누기, null 참조).


* **try-catch-finally:** 예외 잡는 문법.
* `try`: 에러 날 것 같은 코드 넣음.
* `catch`: 에러 나면 실행됨.
* `finally`: 에러 나든 말든 무조건 실행.
* 

* **throws:** 에러 떠넘기기.
* **사용자 정의 예외:** 내가 직접 에러 이름 만들어서 발생시키기 (`throw new MyException()`).

**[코드/개념 메모]**

* **기본 구조:**
```java
try {
    int result = 10 / 0; // 0으로 나누면 ArithmeticException 발생
} catch (ArithmeticException e) {
    System.out.println("0으로 못 나눔: " + e.getMessage());
} finally {
    System.out.println("계산 끝 (무조건 실행)");
}

```

* **throws 주의:**
* 메소드 선언부에 `void method() throws Exception` 이렇게 씀.
* 웬만하면 `try-catch`로 막는 게 좋음.


**[주의/느낀점]**

* `catch` 여러 개 쓸 때 부모 예외(Exception)를 맨 위에 쓰면 밑에 자식 예외들이 실행 안 되므로 순서 중요.
* `NullPointerException` 진짜 많이 나옴. 객체 `null`인지 체크하자.

---

## [W9] 자바 기본 API (Object, String, Wrapper)

**[배운 내용]**

* **Object 클래스:** 모든 자바 클래스의 조상. (`toString()`, `equals()`, `hashCode()` 들어있음).
* **String 클래스:** 문자열 다루기. 불변이므로 수정하면 새로운 객체가 생김.
* **StringBuffer/StringBuilder:** 문자열 수정 많이 할 때 씀 (메모리 아낌).
* **Wrapper 클래스:** 기본 타입(`int`, `double`)을 객체로 감싼 거 (`Integer`, `Double`). `ArrayList` 같은 데 넣을 때 필요.

**[코드/개념 메모]**

* **equals() vs ==:**
* `==`: 주소 비교.
* `equals()`: 내용 비교. `String` 비교할 땐 무조건 이거.
* 내가 만든 클래스에서 내용 비교 하려면 `equals` 오버라이딩해야 함.


* **Wrapper 박싱/언박싱:**
* `Integer num = 10;` (Auto Boxing: 정수 -> 객체)
* `int n = num;` (Auto Unboxing: 객체 -> 정수)
* `Integer.parseInt("100")` -> 문자열을 숫자로 바꿀 때. 필수 암기.


* **Calendar 주의:**
* `Calendar.MONTH`: **0부터 시작함.** (1월이 0임). +1 해줘야 우리가 아는 월 나옴.


**[주의/느낀점]**

* `String`에 `+` 연산자 막 쓰면 메모리 낭비 심하다고 함. 
* `Object`가 조상이라 아무거나 다 들어감(`Object obj = new Person()`).

---

## [W10] 컬렉션 프레임워크 

**[배운 내용]**

* **컬렉션:** 배열 업그레이드 버전. 크기가 가변변. 
* **제네릭(Generic, `< >`):** "이 상자엔 사과만 담을 거야"라고 딱 정해주는 거. 형 변환 안 해도 돼서 편함.
* **List:** 순서 있음, 중복 허용. (`ArrayList`, `Vector`, `LinkedList`)
* **Set:** 순서 없음, 중복 불가. (`HashSet`) -> 집합 생각하면 됨.
* **Map:** 키(Key)와 값(Value) 쌍으로 저장. 키는 중복 안 됨. (`HashMap`)

**[코드/개념 메모]**

* **ArrayList:**
```java
ArrayList<String> list = new ArrayList<>(); // String만 넣겠다 선언
list.add("Java");
list.add("Python");
String s = list.get(0); // 배열처럼 [0] 아니고 .get(0) 씀
list.remove(0); // 삭제하면 뒤에 있는 애들이 앞으로 땡겨짐

```

* **HashMap:**
```java
HashMap<String, Integer> map = new HashMap<>();
map.put("Apple", 1000); // 데이터 넣기 (.put)
int price = map.get("Apple"); // 데이터 꺼내기 (.get)
// map.get("Banana"); -> 없는 키 찾으면 null 리턴함 (에러 주의)

```

* **Iterator (반복자):**
* 컬렉션 요소를 하나씩 꺼낼 때 씀. `while(it.hasNext()) { ... }` 패턴. 향상된 for문 쓰는 게 더 편하긴 함.


**[주의/느낀점]**

* 제네릭 `<int>`아니고 무조건 객체 타입(`<Integer>`) 써야 함. (Wrapper 클래스 필수)
* `Map` 쓸 때 키 값 헷갈려서 에러 자주 냄. `.containsKey()`로 확인하고 꺼내는 습관 들여야지.

---

## [W11] GUI 기초

**[배운 내용]**

* **AWT vs Swing:**
* **AWT:** 운영체제(OS)꺼 빌려 씀. 투박함.
* **Swing:** 자바가 직접 그림. 클래스 앞에 `J` 붙음 (`JButton`, `JFrame`).


* **컨테이너 & 컴포넌트:**
* **컨테이너:** 창문(`JFrame`), 판넬(`JPanel`). 부품 담는 그릇.
* **컴포넌트:** 버튼(`JButton`), 라벨(`JLabel`). 실제 부품들.

* **배치관리자 (Layout Manager):** 부품들을 어떻게 줄 세울지 결정하는 놈.

**[코드/개념 메모]**

* **기본 구조 (암기):**
```java
import javax.swing.*; // 스윙 임포트

public class MyFrame extends JFrame {
    public MyFrame() {
        setTitle("내 첫 GUI");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 없으면 닫기 눌러도 프로그램 안 꺼짐

        Container c = getContentPane(); // 작업 영역 가져오기
        c.setLayout(new FlowLayout()); // 레이아웃 설정

        c.add(new JButton("Click Me")); // 버튼 추가

        setVisible(true); // 창 보여주기 (맨 마지막에)
    }

    public static void main(String[] args) {
        new MyFrame();
    }
}

```

* **주요 레이아웃 4개:**
1. **FlowLayout:** 글자 쓰듯이 왼쪽 -> 오른쪽으로 차례대로 배치. (`JPanel` 기본값)
2. **BorderLayout:** 동, 서, 남, 북, 중앙 5구역으로 나눔. (`JFrame` 기본값)
3. **GridLayout:** 엑셀처럼 격자(행, 열) 맞춰서 배치.
4. **CardLayout:** 여러 컴포넌트를 카드처럼 겹쳐 놓고 한 번에 하나의 화면만 보여줌.


**[주의/느낀점]**

* `setDefaultCloseOperation` 안 쓰면 창 닫아도 계속 실행.
* 레이아웃 설정 안 하면(`BorderLayout` 기본) 버튼 추가할 때마다 겹쳐서 마지막 하나만 보여서 버튼의 위치 알려줘야 함.

---

## [W12] 이벤트 처리 

**[배운 내용]**

* **이벤트 기반 프로그래밍:** 클릭, 키보드 입력 같은 '사건(Event)'이 터지면 코드 실행되는 방식.
* **리스너(Listener):** 이벤트를 기다림. `ActionListener`(버튼), `MouseListener`(마우스), `KeyListener`(키보드).
* **어댑터(Adapter):** 리스너 인터페이스에 메소드 너무 많을 때, 필요한 것만 골라 쓰려고 만든 껍데기 클래스.
* **메뉴/툴바:** `JMenuBar`(상단 메뉴), `JToolBar`(아이콘 버튼 모음).

**[코드/개념 메모]**

* **버튼 클릭 이벤트 (익명 클래스 vs 람다):**
* 옛날 방식 (익명 클래스): 코드가 김.
```java
btn.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click!");
    }
});

```

* 요즘 방식 (람다): 짧음.
```java
btn.addActionListener(e -> System.out.println("Click!"));

```

* **MouseListener:**
* `mouseClicked`: 클릭했을 때.
* `mousePressed` / `mouseReleased`: 눌렀을 때 / 뗐을 때.
* `mouseEntered` / `mouseExited`: 마우스가 컴포넌트 안으로 들어옴 / 나감 (롤오버 효과 낼 때 씀).


**[주의/느낀점]**

* `KeyListener` 쓸 때 포커스(`setFocusable(true)`) 줘야 됨.
* 내부 클래스 쓰면 `main`의 변수 못 가져다 씀. (상수 `final`만 가능).

---

## [W13] 다이얼로그 & 스레드 & 스트림

**[배운 내용]**

* **JOptionPane:** 알림창 띄우기. `showMessageDialog`(메시지), `showConfirmDialog`(예/아니오), `showInputDialog`(입력).
* **스레드(Thread):** 작업 하나 더 만들기(멀티태스킹). `Thread` 상속받거나 `Runnable` 구현.
* **동기화:** 여러 스레드가 변수 하나 같이 건드릴 때 충돌 안 나게 줄 세우는 거.
* **람다 & 스트림:** 함수형 프로그래밍. 코드 한 줄로 배열 정렬, 필터링 다 함.

**[코드/개념 메모]**

* **스레드 만들기:**
```java
class MyThread extends Thread {
    public void run() { // run 메소드 오버라이딩
        // 여기서 작업함
    }
}
// 실행할 땐 run()이 아니라 start() 호출해야 함!
new MyThread().start();

```

* **스트림 (Stream):**
* 배열에서 짝수만 골라서 출력하기가 짧아짐.

```java
int[] arr = {1, 2, 3, 4, 5};
Arrays.stream(arr)
      .filter(n -> n % 2 == 0) // 짝수만 필터링
      .forEach(n -> System.out.println(n)); // 출력

```

* **모달(Modal) vs 모달리스:**
* 모달: 창 닫기 전까지 뒤에 거 클릭 못 함 (오류 메시지 같은 거).
* 모달리스: 창 떠 있어도 뒤에 거 작업 가능 (찾기 창 같은 거).


**[주의/느낀점]**

* 스레드 `run()` 직접 호출했다가 멀티스레드 안 되고 그냥 순서대로 실행됨. 무조건 `start()` 써야 함.
* 람다식(`->`) 재밌고 편한 것 같음. `for`, `if` 줄어들어서 좋음.
* `JOptionPane` 입력창에서 취소 누르면 `null` 리턴됨. 이거 처리 안 하면 에러.
