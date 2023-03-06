package com.bus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BusExperimentApplication {
	/*echo "# TP1-DEVOPS" >> README.md
	git init
	git add README.md
	git commit -m "first commit"
	git branch -M main
	git remote add origin https://github.com/hananesi2/TP1-DEVOPS.git
	git push -u origin main*/

	public static void main(String[] args) {
		SpringApplication.run(BusExperimentApplication.class, args);
	}

}
