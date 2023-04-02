package delivery.fee.calculation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot REST API Documentation",
                description = "REST API documentation for food delivery application, which\n" +
                        "calculates the delivery fee for food couriers based on regional base fee, vehicle type, and weather\n" +
                        "conditions.",
                version = "v1.0"
        )
)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
