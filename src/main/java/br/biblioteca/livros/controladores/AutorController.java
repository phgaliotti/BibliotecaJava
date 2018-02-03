package br.biblioteca.livros.controladores;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.biblioteca.livros.beans.Autor;
import br.biblioteca.livros.repository.AutorRepository;

@Controller
@RequestMapping("/autor")
public class AutorController {
	private Iterable<Autor> autores;
	
	@Autowired
	private AutorRepository autorRepository;
	
	@GetMapping("/list")
	public ModelAndView autores(){
		this.autores = this.autorRepository.findAll();
		logAutores();
		return new ModelAndView("autor/list", "autores", this.autores);
	}
	
	@GetMapping("/novo")
	public String createForm(@ModelAttribute Autor autor){
		return "autor/form";
	}
	
	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable("id") Long id){
		Autor autor = this.autorRepository.findOne(id);
		return new ModelAndView("autor/form", "autor", autor);	
	}
	
	@GetMapping("/excluir/{id}")
	public ModelAndView excluir(@PathVariable("id") Long id){
		Autor autor = this.autorRepository.findOne(id);
		this.autorRepository.delete(autor);
		return new ModelAndView("redirect:/autor/list");
	}
	
	@PostMapping(params = "form")
	public ModelAndView create(@ModelAttribute("autor") @Valid Autor autor, BindingResult bindingResult) {
		if (bindingResult.hasErrors()){
			return new ModelAndView("autor/form");
		}
		
		autor = this.autorRepository.save(autor);
		return new ModelAndView("redirect:/autor/list");
	}
	
	public void logAutores(){
		System.out.println("Autores ====>");
		
		for (Autor autor : this.autores) {
			System.out.println(autor.getNome());
		}
	}
}
