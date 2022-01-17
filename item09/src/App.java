public class App {
    public static void main(String[] args) throws Exception {
        MyResource myResource = new MyResource();
    
        try{
            myResource.doSomething();
        } finally {
            myResource.close(); // 첫 번째 error를 체크할 수 없음
        }

    }
}
