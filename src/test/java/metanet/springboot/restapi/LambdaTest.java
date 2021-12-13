package metanet.springboot.restapi;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;

public class LambdaTest {

    @Test
    public void iterable() {
        List<String> stringList = List.of("aa", "bb", "cc");
        //forEach의 아규먼트는 Consumer
        stringList.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
        //Lambda express
        stringList.forEach(val -> System.out.println("val " + val));

        //Method Reference
        stringList.forEach(System.out::println);

    }

    @Test @Disabled
    public void thread() {
        //Anonymous Inner Class
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous Inner Class");
            }
        });
        t1.start();

        //Lambda express
        Thread t2 = new Thread(() -> System.out.println("Lambda express"));
        t2.start();

    }



}
