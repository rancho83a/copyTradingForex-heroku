package forex.copytradingforex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CopyTradingForexApplication {

    public static void main(String[] args) {
        SpringApplication.run(CopyTradingForexApplication.class, args);
    }

}
