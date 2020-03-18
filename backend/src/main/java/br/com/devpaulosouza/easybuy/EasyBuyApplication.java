package br.com.devpaulosouza.easybuy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

@SpringBootApplication
public class EasyBuyApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyBuyApplication.class, args);
	}

	@Bean
	public ProblemModule problemModule() {
		return new ProblemModule();
	}
	@Bean
	public ConstraintViolationProblemModule constraintViolationProblemModule() {
		return new ConstraintViolationProblemModule();
	}


}
