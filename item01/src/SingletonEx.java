public class SingletonEx {
    private static SingletonEx singletonInstance = null;

    private SingletonEx() {
    }

    static SingletonEx getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new SingletonEx();
        }

        return singletonInstance;
    }

    public static void main(String[] args) {
        SingletonEx instance1 = SingletonEx.getInstance();
        SingletonEx instance2 = SingletonEx.getInstance();

        System.out.println(instance1 == instance2);
    }
}