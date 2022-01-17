import java.time.LocalDateTime;

public class SampleRunner {
    

    public static void main(String[] args) throws Exception {

        // SampleRunner sampleRunner = new SampleRunner();
        // sampleRunner.run(); // 호출되지 않을 수도 있음(예전에 os 스케쥴러에 의해 쓰레드의 우선순위가 보장되지 않는다는 것과 비슷)
        // System.gc(); // gc 사용한다고 finalize가 보장되지 않는다.
        // Thread.sleep(1000);

        // long start = System.currentTimeMillis();
        // SampleResource sr = new SampleResource();
        // sr.hello();
        // sr.close();
        // System.out.println("걸린 시간 : " + (System.currentTimeMillis() - start));

        long start = System.currentTimeMillis();
        try (SampleResource sampleResource = new SampleResource()) {
            sampleResource.hello();
        }
        System.out.println("걸린 시간 : " + (System.currentTimeMillis() - start));

    }
    
    private void run() {
        FinalizerExample finalizerExample = new FinalizerExample();
        finalizerExample.hello();
    }
}
