package grant.guo.ideas.singleton;

public class MySingleton {
    private static MySingleton ourInstance = new MySingleton();

    public static MySingleton getInstance() {
        return ourInstance;
    }

    private int value = 0;
    private MySingleton() {
        value = 100;
    }

    public int function() {
        return value;
    }
}
