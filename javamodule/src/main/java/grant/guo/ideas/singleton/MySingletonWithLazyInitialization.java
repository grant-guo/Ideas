package grant.guo.ideas.singleton;

public class MySingletonWithLazyInitialization {

    private int value = 0;

    private MySingletonWithLazyInitialization() {
        value = 200;
    }

    private static class MySingletonInstanceHolder {
        public static final MySingletonWithLazyInitialization instance = createInstance();

        private static MySingletonWithLazyInitialization createInstance() {
            return new MySingletonWithLazyInitialization();
        }
    }

    public static MySingletonWithLazyInitialization getInstance() {
        return MySingletonInstanceHolder.instance;
    }

    public int function() {
        return value;
    }
}
