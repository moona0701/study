package subject01.may;

/**
 * 차량의 정보를 나타내는 클래스입니다.
 * 차량 번호, 차종, 소유주 전화번호 정보를 가집니다.
 *
 * @author 문아영
 * @since 2025-10-10
 */
public class Car {
    private final String carNumber; //차량번호
    private final String carType; // 차량종류
    private final String phoneNumber; // 전화번호

    /**
     * Car 객체를 생성합니다.
     *
     * @param carNumber  차량번호
     * @param carType    차량종류
     * @param phoneNumber 전화번호
     */
    public Car(String carNumber, String carType, String phoneNumber) {
        this.carNumber = carNumber;
        this.carType = carType;
        this.phoneNumber = phoneNumber;
    }

    /**
     * 차량번호를 반환합니다.
     * @return 차량번호 문자열
     */
    public String getCarNumber() {
        return carNumber;
    }

    /**
     * 차량종류를 반환합니다.
     * @return 차량종류 문자열
     */
    public String getCarType() {
        return carType;
    }

    /**
     * 소유주 전화번호를 반환합니다.
     * @return 소유주 전화번호 문자열
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 차량 정보를 형식에 맞춰 문자열로 반환합니다.
     * @return "차량번호: [번호], 차종: [종류], 연락처: [연락처]" 형식의 문자열
     */
    @Override
    public String toString() {
        return "차량번호: "+carNumber+", 차종: "+carType+", 연락처: "+phoneNumber;
    }
}
