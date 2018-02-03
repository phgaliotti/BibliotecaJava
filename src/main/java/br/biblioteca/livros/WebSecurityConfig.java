package br.biblioteca.livros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET, "/user/registration").permitAll()
				.antMatchers(HttpMethod.POST, "/user/registration").permitAll()
//				.antMatchers(HttpMethod.GET, "/livros/novo").hasRole("ADMIN")
//				.antMatchers(HttpMethod.POST, "/livros/novo").hasRole("ADMIN")
//				.antMatchers(HttpMethod.GET, "/autor/novo").hasRole("ADMIN")
//				.antMatchers(HttpMethod.POST, "/autor/novo").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/usuarios/list").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/usuarios/list").hasRole("ADMIN")
				// .antMatchers(HttpMethod.GET, "/user/list").hasRole("BASIC")
				// .antMatchers(HttpMethod.GET, "/user/listadmin").hasRole("ADMIN")
				.and().formLogin().loginPage("/user/login").permitAll().and().logout().permitAll();

	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder
			.userDetailsService(userDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}
	

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
//		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
//	}

}
