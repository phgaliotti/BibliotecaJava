package br.biblioteca.livros.controladores;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.biblioteca.livros.beans.Autor;
import br.biblioteca.livros.beans.Livro;
import br.biblioteca.livros.repository.AutorRepository;
import br.biblioteca.livros.repository.LivroRepository;
import br.biblioteca.livros.util.FileSaver;

@Controller
@RequestMapping("/livros")
public class LivroController {
	private Iterable<Livro> livros;
	
	@Autowired
	private LivroRepository livroRepository;
	
	@Autowired
	private AutorRepository autorRepository;
	
	
	@GetMapping("/list")
	public ModelAndView livros() {
		this.livros = livroRepository.findAll();
		logLivros();
		return new ModelAndView("livros/list", "livros", this.livros);
	}
	
	@GetMapping("/novo")
	public ModelAndView createForm(@ModelAttribute Livro livro){
		Iterable<Autor> autores = this.autorRepository.findAll();
		
		ModelAndView modelAndView = new ModelAndView("livros/form");
		modelAndView.addObject("autores", autores);
		return modelAndView;
	}
	
	@PostMapping(params = "form")
	public ModelAndView create(@RequestParam("capaUrl") 
			 					MultipartFile capaUrl,
								@ModelAttribute("livro") 
								@Valid Livro livro, 
								BindingResult bindingResult,
								Model model) {
		
		if (livro.getId() != null) {
			if (capaUrl.getOriginalFilename().length() > 0) {
				incluiCapa(capaUrl, livro, model);
			}
		} else {
			if ("".equals(capaUrl.getOriginalFilename())) {
				model.addAttribute("capa", "A capa não pode ser vazia");
			} else {
				incluiCapa(capaUrl, livro, model);
			}
		}
		
		if (bindingResult.hasErrors()){
			Iterable<Autor> autores = autorRepository.findAll();
			return new ModelAndView("livro/form", "autores", autores);
		}
		
		livro = livroRepository.save(livro);
		return new ModelAndView("redirect:/livros/list");
	}
	
	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable("id") Long id){
		Livro livro = this.livroRepository.findOne(id);
		Iterable<Autor> autores = autorRepository.findAll();
		
		ModelAndView modelAndView = new ModelAndView("livros/form");
		modelAndView.addObject("livro", livro);
		modelAndView.addObject("autores", autores);
		return modelAndView;
	}
	
	@GetMapping("/excluir/{id}")
	public ModelAndView excluir(@PathVariable("id") Long id){
		Livro livro = this.livroRepository.findOne(id);
		this.livroRepository.delete(livro);
		return new ModelAndView("redirect:/livros/list");
	}

	public void logLivros(){
		System.out.println("Livros ====>");
		
		for (Livro livro : this.livros) {
			System.out.println(livro.getNome());
		}
	}
	
	private void incluiCapa(MultipartFile capaUrl, Livro livro, Model model) {
		if (capaUrl.getContentType().equals("image/png")) {
			String webPath = new FileSaver().write("uploaded-images",capaUrl);
			livro.setCapa(webPath);
		} else {
			model.addAttribute("capa", "Arquivo em formato errado. Permitido apenas png");
		}
	}

}
