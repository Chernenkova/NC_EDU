package Elena.Chernenkova;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by 123 on 20.09.2017.
 */

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /*@Bean
    CommandLineRunner init(StudentRepository studentRepository) {
        return (evt) -> Arrays.asList(
                "jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
                .forEach(
                        a -> {
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        });
    }*/

}
