package br.biblioteca.livros.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class index {
	
	@GetMapping("/")
	public ModelAndView home() {
		return new ModelAndView("index");
	}
	
}
