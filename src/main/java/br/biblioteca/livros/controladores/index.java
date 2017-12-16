package br.biblioteca.livros.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.biblioteca.livros.beans.Livro;
import br.biblioteca.livros.repository.LivroRepository;

@Controller
public class index {
	
	@Autowired
	private LivroRepository livroRepository;
	
	@GetMapping("/")
	public ModelAndView home() {
		return new ModelAndView("index");
	}
	
	@GetMapping("/livros")
	public ModelAndView livros() {
		Iterable<Livro> livros = livroRepository.findAll();
		
		for (Livro livro : livros) {
			System.out.println(livro.getNome());
		}
		
		return new ModelAndView("livros", "livros", livros);
		
	}
	
}
