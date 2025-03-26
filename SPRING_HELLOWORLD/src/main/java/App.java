import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AppConfig.class);
        
        HelloWorld bean1 =
                (HelloWorld) applicationContext.getBean("helloworld");
        HelloWorld bean2 =
                (HelloWorld) applicationContext.getBean("helloworld");

        Cat beanCat1 =
                (Cat) applicationContext.getBean("cat");
        Cat beanCat2 =
                (Cat) applicationContext.getBean("cat");

        System.out.println(bean1==bean2);

        System.out.println(beanCat1==beanCat2);
    }
}
