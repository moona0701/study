package subject01.may;

/**
 * 주차 관리 프로그램을 실행하는 메인 클래스입니다.
 *
 * 요구사항 1 : Yes
 * 요구사항 2 : Yes
 * 요구사항 3 : Yes
 * 요구사항 4 : Yes
 * 요구사항 5 : Yes
 * 요구사항 6 : Yes
 * 요구사항 7 : Yes
 * 요구사항 8 : Yes
 * 요구사항 9 : Yes
 *
 *
 * @author 문아영
 * @since 2025-10-11
 */
public class TestMain {

    /**
     * 프로그램의 시작점입니다.
     */
    public static void main(String[] args){
        System.out.println("202511957 문아영");

        ParkingLot konkuk = new ParkingLot("res/konkuk.txt");
        konkuk.menu();
    }

}
