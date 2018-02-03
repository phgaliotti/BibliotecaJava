package br.biblioteca.livros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
//@EnableJpaRepositories("br.biblioteca.livros.repository")
//@ComponentScan({"br.biblioteca.livros.usercontrol.services"})
////@ComponentScan({"br.biblioteca.livros.controladores"})
////@EntityScan("br.biblioteca.livros.beans")
public class LivrosApplication extends SpringBootServletInitializer {
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LivrosApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(LivrosApplication.class, args);
	}
}
