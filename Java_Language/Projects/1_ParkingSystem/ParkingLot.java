package subject01.may;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 주차장 관리를 위한 클래스입니다.
 * 2차원 객체 배열을 사용하여 주차 공간을 관리하고,
 * 주차, 출차, 검색 등의 기능을 제공합니다.
 *
 * @author 문아영
 * @since 2025.10.10
 */
public class ParkingLot {

    private Car[][] spaces; //2차원 주차 공간 배열
    private int rows;
    private int cols;
    public static Scanner scanner = new Scanner(System.in);

    /**
     * ParkingLot 객체를 생성하고 파일을 읽어 주차장을 초기화합니다.
     * @param filepath 초기 주차 정보가 담긴 텍스트 파일의 경로
     */
    public ParkingLot(String filepath){
        File file = new File(filepath); //파일 객체 생성
        try {
            Scanner Scanner = new Scanner(file);

            this.rows = Scanner.nextInt();
            this.cols = Scanner.nextInt();
            //위에서 읽어온 크기만큼 2차원 배열 생성
            this.spaces = new Car[this.rows][this.cols];

            //이미 주차된 차 정보 읽기
            while (Scanner.hasNext()){
                int r = Scanner.nextInt(); //좌표
                int c = Scanner.nextInt(); //좌표
                String carNumber = Scanner.next(); //차량번호
                String carType = Scanner.next(); //차종
                String phoneNumber = Scanner.next(); //전번

                //읽어온 차를 주차 공간에 배치 (안전장치 있음)
                if (r < this.rows && c < this.cols){
                    //주차장 크기 안에 있을 때만 해당 위치에 새로운 Car객체 생성 및 주차
                    this.spaces[r][c] = new Car(carNumber, carType, phoneNumber);
                }
            }
            Scanner.close();
        } catch (FileNotFoundException e){
            System.out.println("파일을 찾을 수 없습니다: "+filepath);
        }
    }

    /**
     * 주차 관리 시스템의 메인 메뉴를 운영하는 메소드입니다.
     */
    public void menu(){
        int choice = 0;

        while (choice != 5){
            System.out.println("\n===== 주차 현황  ====");
            showStatus(null);
            System.out.println("==================");
            System.out.println("1. 차량 주차  2. 차량 출차  3. 차량 검색  4. 주차장 정보 출력  5. 종료 ");
            choice = scanner.nextInt();

            switch (choice){
                case 1 -> parkCar();
                case 2 -> unparkCar();
                case 3 -> searchCar();
                case 4 -> printAllCarInfo();
                case 5 -> System.out.println("프로그램을 종료합니다.");
                default -> System.out.println("잘못된 메뉴입니다. 다시 선택해주세요.");
            }
        }
    }

    /**
     * 현재 주차장의 상태를 화면에 출력합니다.
     * @param searchTarget 검색할 차량의 번호 또는 소유주 전화번호, 일반 출력시에는 null
     */
    private void showStatus(String searchTarget) {
        for (int i=0; i<this.rows; i++){
            for(int j=0; j<this.cols; j++){
                if (this.spaces[i][j] != null) { //차가 있으면
                    if (searchTarget != null &&
                            (this.spaces[i][j].getCarNumber().equals(searchTarget) ||
                                    this.spaces[i][j].getPhoneNumber().equals(searchTarget))) {
                        System.out.print("★ ");
                    } else {
                        System.out.print("■ ");
                    }
                } else {
                    System.out.print("□ ");
                }
            }
            System.out.println();
        }
    }

    /**
     * 새로운 차량을 주차하는 기능입니다.
     */
    private void parkCar() {
        int r,c;

        while (true) {
            System.out.print("주차할 위치(행 열)를 입력하세요: ");
            r = scanner.nextInt();
            c = scanner.nextInt();

            //주차장 범위 확인
            if (r >= this.rows || c >= this.cols || r < 0 || c < 0) {
                System.out.println("잘못된 위치입니다. 다시 입력하세요. ");
                continue;
            }

            //중복된 주차 위치 확인
            if (this.spaces[r][c] != null) {
                System.out.println("해당 위치에는 다른 차량이 주차되어 있습니다.");
                continue;
            }
            break;
        }

        System.out.print("차량 정보를 입력하세요.\n차량번호: ");
        String carNumber = scanner.next();
        System.out.print("차량종류: ");
        String carType = scanner.next();
        System.out.print("소유주 전화번호: ");
        String phoneNumber = scanner.next();

        for (int i=0; i<this.rows; i++){
            for (int j=0; j<this.cols; j++){
                if (this.spaces[i][j] != null && this.spaces[i][j].getCarNumber().equals(carNumber)) {
                    System.out.println("이미 등록된 차량입니다.");
                    return;
                }
            }
        }
        //주차할 준비가 다 되었다면, 새로운 Car객체를 만들어 주차공간에 등록
        this.spaces[r][c] = new Car(carNumber,carType,phoneNumber);
        System.out.println(r+"행 "+c+"열에 차량이 주차되었습니다.");
    }


    /**
     * 주차된 차량 번호를 입력받아 출차하는 기능입니다.
     */
    private void unparkCar() {
        System.out.print("출차할 차량번호를 입력하세요: ");
        String carNumber = scanner.next();
        boolean found = false; //차를 찾았는지 기억하는 코드

        for (int i=0; i<this.rows; i++){
            for (int j=0; j<this.cols; j++){
                //해당 칸에 차가 있고, 입력한 차량번호와 같다면 해당 칸에서 차를 제거
                if (this.spaces[i][j] != null && this.spaces[i][j].getCarNumber().equals(carNumber)){
                    this.spaces[i][j] = null;
                    System.out.println("차량이 출차되었습니다.");
                    found = true;
                    break;
                }
            }
            if (found){
                break; //이미 찾았으니 종료
            }
        }

        //for문이 끝나고도 found가 false라면
        if(!found){
            System.out.println("해당 차량은 주차되어 있지 않습니다.");
        }

    }

    /**
     * 차량을 검색하는 기능입니다.
     */
    private void searchCar() {
        System.out.print(" 검색할 차량 번호 또는 소유주 전화번호를 입력하세요: ");
        String searchTarget = scanner.next();
        boolean found = false;

        for (int i=0; i<this.rows; i++){
            for (int j=0; j<this.cols; j++){
                if (this.spaces[i][j] != null){
                    if (this.spaces[i][j].getCarNumber().equals(searchTarget)||
                            this.spaces[i][j].getPhoneNumber().equals(searchTarget)){
                        found = true;
                        break;
                    }
                }
            }
            if (found){
                break;
            }
        }
        if (found){
            System.out.println("====차량 검색 결과====");
            showStatus(searchTarget);
            System.out.println("===================");
        } else {
            System.out.println("해당 정보의 차량을 찾을 수 없습니다.");
        }

    }

    /**
     * 주차된 모든 차량의 정보를 출력하는 기능입니다.
     */
    private void printAllCarInfo() {
        System.out.println("\n==== 전체 주차 차량 정보 ====");
        boolean found = false;
        for (int i=0; i<this.rows; i++){
            for (int j=0; j<this.cols; j++){
                if (this.spaces[i][j] != null){
                    Car car = this.spaces[i][j];
                    System.out.println(car.getCarNumber() + " "
                            + car.getCarType() + " " + car.getPhoneNumber());
                    found = true;
                }
            }
        } if (!found){
            System.out.println("주차된 차량이 없습니다.");
        }
        System.out.println("==========================");
    }

}
