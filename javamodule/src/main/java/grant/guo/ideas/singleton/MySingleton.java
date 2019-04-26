package grant.guo.ideas.singleton;

public class MySingleton {

    private int value = 0;

    private MySingleton() {
        value = 100;
    }

    private static MySingleton ourInstance = new MySingleton();

    public static MySingleton getInstance() {
        return ourInstance;
    }

    public int function() {
        return value;
    }
}
