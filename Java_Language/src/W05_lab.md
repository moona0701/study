# Week 05

**[Lab 1] 은행 계좌**

* **내용:** `this()`를 사용하여 생성자를 오버로딩하고 입출금 기능을 구현

```java
package week05;

public class BankAccount {
    public int accountNumber;
    public String customerName;
    public double accountBalance;

    public BankAccount() {}

    public BankAccount(int accountNumber, String customerName) {
        this(accountNumber, customerName, 0.0);
    }

    public BankAccount(int accountNumber, String customerName, double accountBalance) {
        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.accountBalance = accountBalance;
    }
    
    public void deposit(double amount) { 
        this.accountBalance += amount; 
    }
    
    public void withdraw(double amount) {
        if (this.accountBalance >= amount) {
            this.accountBalance -= amount;
        } else {
            System.out.println("잔액 부족. 출금 불가!");
        }
    }
    
    public void transfer(BankAccount account, double amount) {
        if (this.accountBalance >= amount) {
            this.withdraw(amount);
            account.deposit(amount);
        } else {
            System.out.println("잔액 부족. 계좌이체 불가!");
        }
    }

    public void showAccount() {
        System.out.println("고객이름 : " + this.customerName);
        System.out.println("계좌번호 : " + this.accountNumber);
        System.out.println("잔액 : " + this.accountBalance);
    }
}

```

**[Lab 2] 자동차**

* **내용:** 다른 Vehicle 객체를 매개변수로 받아 동일한 상태를 가진 객체를 생성.

```java
package week05;

public class Vehicle {
    public String color;
    public int speed;
    public int mileage;
    public char gearStatus;

    // 복사 생성자
    public Vehicle(Vehicle car) {
        this(car.color, car.speed, car.mileage, car.gearStatus);
    }

    public Vehicle(String color, int speed, int mileage, char gearStatus) {
        this.color = color;
        this.speed = speed;
        this.mileage = mileage;
        this.gearStatus = gearStatus;
    }
    
    public void showStatus() {
        System.out.println("차량색상: " + this.color);
        System.out.println("차량속도: " + this.speed);
        System.out.println("주행거리: " + this.mileage);
        System.out.println("기어상태: " + this.gearStatus);
    }
}

```
