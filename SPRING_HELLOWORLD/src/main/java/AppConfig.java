import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {
 
    @Bean(name="helloworld")
    public HelloWorld getHelloWorld() {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.setMessage("Hello World!");
        return helloWorld;
    }
 
    @Bean(name="cat")
    @Scope("prototype") // для создания нового бина каждый раз, когда он запрашивается  
    public Cat getCat() {
        return new Cat("Стич", "Пришелец", (byte) 1);
    }
}
