# Week 07

**[Lab 1] 커피숍 결제**

* **내용:** `Payment` 인터페이스와 정적 내부 클래스, 익명 클래스를 활용해 다양한 결제 방식을 구현.

```java
// CoffeeShop.java
package week7.lab02_1;

public class CoffeeShop {
    interface Payment {
        void processPayment(int amount);
    }

    static class CardPayment implements Payment {
        @Override
        public void processPayment(int amount) {
            System.out.println(amount + "원 카드 결제");
        }
    }
    
    public void orderCoffee(String coffeeName, int amount, Payment payment){
        payment.processPayment(amount);
        System.out.println(coffeeName + " 주문 완료");
    }
}

// TestMain.java (익명 클래스 사용)
public class TestMain {
    public static void main(String[] args){
        CoffeeShop shop = new CoffeeShop();
        
        // 익명 클래스로 즉석에서 결제 로직 구현
        shop.orderCoffee("카페모카", 3000, new CoffeeShop.Payment(){
            @Override
            public void processPayment(int amount) {
                System.out.println(amount + "원 계좌이체 결제");
            }
        });
    }
}

```

