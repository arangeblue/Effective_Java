public class MyResource implements AutoCloseable {

    public void doSomething() {
        System.out.println("Do Something");
        throw new FirstError();
    }

    @Override
    public void close() throws Exception {
        System.out.println("Close my resource");
        throw new SecondError();
    
    }
    

}
