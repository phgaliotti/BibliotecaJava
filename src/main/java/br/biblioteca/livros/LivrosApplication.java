package br.biblioteca.livros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("br.biblioteca.livros.repository")
@ComponentScan({"br.biblioteca.livros.usercontrol.services"})
@EntityScan("br.biblioteca.livros.beans")
public class LivrosApplication extends SpringBootServletInitializer {
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LivrosApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(LivrosApplication.class, args);
	}
}
