public class Elvis1 {

    public static final Elvis1 instance = new Elvis1();


    private Elvis1() {
    }

    public void leaveTheBuilding(){
        System.out.println("호출이 정상적으로 작동합니다.");
    }

    public static void main(String[] args) {
        Elvis instance = Elvis.INSTANCE;
        instance.leaveTheBuilding();
    }
}