# Week 10

**[Lab 1] 제네릭 배열 클래스**
* **내용:** 타입 파라미터 `<E>`를 사용하는 제네릭 클래스를 구현.

```java
package week10.lab01;

public class MyArray <E> {
    private final int CAPACITY;
    private int count = 0;
    E[] arr;

    public MyArray(int CAPACITY) {
        this.CAPACITY = CAPACITY;
        arr = (E[])new Object[this.CAPACITY]; // 강제 형변환
    }

    public void add(E e) {
        if (this.count < this.CAPACITY)
            arr[count++] = e;
        else
            System.out.println("공간부족");
    }

    public E getElement(int index) {
        if (index >= 0 && index < count)
            return arr[index];
        else
            return null;
    }
}

```

**[Lab 2] 단어장**
* **내용:** `Vector` 컬렉션을 사용하여 단어를 추가하고 검색.

```java
package week10.lab02;
import java.io.File;
import java.util.Scanner;
import java.util.Vector;

public class VocManager {
    Vector<Word> voc = new Vector<>();
    
    // 파일 읽어서 단어장 생성
    void makeVoc(String fileName){
        try(Scanner file = new Scanner(new File(fileName))){
            while(file.hasNextLine()){
                String str = file.nextLine();
                String[] temp = str.split("\t");
                this.voc.add(new Word(temp[0].trim(), temp[1].trim()));
            }
        } catch (Exception e){
            System.out.println("오류 발생");
        }
    }

    // 단어 검색
    public Word findWord(String sWord){
        for (Word word: voc){
            if (word.eng.equalsIgnoreCase(sWord)){
                return word;
            }
        }
        return null;
    }
}

```
