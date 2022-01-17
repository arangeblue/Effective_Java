public class App2 {
    public static void main(String[] args) throws Exception {

        try(MyResource myResource = new MyResource(); MyResource myResource2 = new MyResource();){
            myResource.doSomething();
            myResource2.doSomething(); // 두 번째 발생한 seconderror와 처음 발생한 error까지 잡아준다.
        }
    }
}
