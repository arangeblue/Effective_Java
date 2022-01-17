public class UtilClass {
    
    public static String getName() {
        return "wi";
    }

    private UtilClass(){}

    static class AnnotherClass extends UtilClass{}
    
    public static void main(String[] args) {
        AnnotherClass annotherClass = new AnnotherClass();

        annotherClass.getName();

        UtilClass.getName();
    }
}
