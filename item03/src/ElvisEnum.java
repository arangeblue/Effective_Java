public enum ElvisEnum {
    INSTANCE;

    public void leaveTheBuilding() {
        System.out.println("호출이 정상적으로 작동했습니다.");
    }
}

public class Main {
    public static void main(String[] args) {
        Elvis instance = Elvis.INSTANCE;
        instance.leaveTheBuilding();
    }
}