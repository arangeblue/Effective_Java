public class item06 {
    
    public static void main(String[] args) {
        String name = new String("wi");    
        String name2 = new String("wi");
        
        System.out.println(name == name2); // 같은 객체가 아니다.
        System.out.print(name.equals(name2)); // String의 값은 동일하다.
    }
}
