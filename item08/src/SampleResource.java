public class SampleResource implements AutoCloseable {

    

    @Override
    public void close() throws Exception {
        System.out.println("close");
    }
    
    public void hello() {
        System.out.println("hello");
    }

    @Override
    protected void finalize() throws Exception { // 안전망을 삼아 한 번 finalize안에 close를 
        close();
    }
}
