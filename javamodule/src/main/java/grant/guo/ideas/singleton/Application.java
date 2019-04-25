package grant.guo.ideas.singleton;

public class Application {

    public static void main(String[] args) {

        MySingleton singleton1 = MySingleton.getInstance();
        int ret1 = singleton1.function();

        MySingleton singleton2 = MySingleton.getInstance();
        int ret2 = singleton2.function();

        assert (ret1 == ret2);

        MySingletonWithLazyInitialization singleton3 = MySingletonWithLazyInitialization.getInstance();
        int ret3 = singleton2.function();
        assert (ret3 == 200);
    }
}
