public class Elvis2 {
    
    public static final Elvis2 instance = new Elvis2(); // 역직렬화할 때 객체를 어떤 메서드를 호출하는데 그 때 다른 객체가 만들어짐, 즉 역직렬화의 해당 메서드의 return값을 overriding하여 사용하면 된다.

    private Elvis2() {

    }
    
    public static Elvis2 getInstance() {
        return instance;
    }
}
