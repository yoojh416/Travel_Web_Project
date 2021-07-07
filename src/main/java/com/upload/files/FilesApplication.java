package com.upload.files;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FilesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilesApplication.class, args);
	}

	@Bean(name = "uploadPath")
	public String uploadPath() {
		return "d:/image/review";
	}

	/*캘린더 실행*/
	public void calendar(String[] args) {
		SpringApplication.run(ApplicationArguments.class, args);
	}

}
