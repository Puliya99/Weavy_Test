package lk.ijse.gdse.weavy_test;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WeavyTestApplication {
    @Bean
    ModelMapper mapper(){
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(WeavyTestApplication.class, args);
    }

}
