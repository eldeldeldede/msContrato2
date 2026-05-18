package cl.duoc.msContrato2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsContrato2Application {

	public static void main(String[] args) {
		SpringApplication.run(MsContrato2Application.class, args);
	}

}
