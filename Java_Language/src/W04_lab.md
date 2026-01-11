# Week 04

**[Lab 1] TV 클래스 구현**
* **내용:** 채널, 볼륨, 전원을 제어하는 TV 클래스를 정의

```java
package week04;

public class TV {
    public boolean power;
    public int channel;
    public int volume;
    
    public final int MAX_CHANNEL = 5;
    public final int MIN_CHANNEL = 0;
    public final int MAX_VOLUME = 10;
    public final int MIN_VOLUME = 0;

    public void powerOnOff() {
        power = !power;
        showTV();
    }

    public void channelUp() {
        if (power) {
            channel++;
            if (channel > MAX_CHANNEL) channel = MIN_CHANNEL;
        }
        showTV();
    }

    public void channelDown() {
        if (power) {
            channel--;
            if (channel < MIN_CHANNEL) channel = MAX_CHANNEL;
        }
        showTV();
    }

    public void volumeUp() {
        if (power) {
            volume++;
            if (volume > MAX_VOLUME) volume = MAX_VOLUME;
        }
        showTV();
    }

    public void volumeDown() {
        if (power) {
            volume--;
            if (volume < MIN_VOLUME) volume = MIN_VOLUME;
        }
        showTV();
    }

    public void showTV() {
        if (power) {
            System.out.println(channel + "번 시청중입니다.");
            System.out.println("TV의 volume은" + volume + "입니다.");
        } else {
            System.out.println("TV 전원이 꺼져있습니다.");
        }
    }
}

```

**[Lab 2] TV 객체 테스트**
* **내용:** TV 객체를 생성하고 메소드를 호출하여 기능을 테스트

```java
package week04;

public class TestMain {
    public static void main(String[] args){
        ex1();
    }

    private static void ex1() {
        TV tv1 = new TV();
        tv1.powerOnOff();
        tv1.channelUp();
        tv1.channelUp();
        tv1.volumeUp();
        tv1.powerOnOff();
    }
}

```
