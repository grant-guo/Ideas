package grant.guo.ideas.singleton;

public class Application {

    public static void main(String[] args) {

        MySingleton singleton1 = MySingleton.getInstance();
        int ret1 = singleton1.function();

        MySingleton singleton2 = MySingleton.getInstance();
        int ret2 = singleton2.function();

        assert (ret1 == ret2);
        assert (singleton1.equals(singleton2));

        MySingletonWithLazyInitialization singleton3 = MySingletonWithLazyInitialization.getInstance();
        MySingletonWithLazyInitialization singleton4 = MySingletonWithLazyInitialization.getInstance();

        assert (singleton3.equals(singleton4));
        int ret3 = singleton3.function();
        assert (ret3 == 200);
        int ret4 = singleton4.function();
        assert (ret3 == ret4);
    }
}
