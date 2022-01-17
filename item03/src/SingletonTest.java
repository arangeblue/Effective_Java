import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class SingletonTest {
    public static void main(String[] args) throws  SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        

        // new Elvis1();
        Elvis1 instance1 = Elvis1.instance;
       
        System.out.println(instance1);

        try{

            Constructor<Elvis1> constructor = Elvis1.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            Elvis1 newInstance = constructor.newInstance();
            
            System.out.println(newInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Supplier<Elvis2> supplier = Elvis2::getInstance;

        Elvis2 elvis22 = supplier.get();

        System.out.println(elvis22);
    }

}
