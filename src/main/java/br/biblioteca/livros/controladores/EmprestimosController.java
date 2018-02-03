package br.biblioteca.livros.controladores;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.biblioteca.livros.beans.Emprestimo;
import br.biblioteca.livros.beans.Livro;
import br.biblioteca.livros.repository.EmprestimoRespository;
import br.biblioteca.livros.repository.LivroRepository;
import br.biblioteca.livros.repository.UserRepository;

@Controller
@RequestMapping("/emprestimo")
public class EmprestimosController {
	
	@Autowired
	private EmprestimoRespository emprestimoRepository;
	
	@Autowired
	private LivroRepository livroRepository;
	
	@Autowired
	private UserRepository usuarioRepository;
	
	private Iterable<Emprestimo> meusEmprestimos;
	private Iterable<Livro> livros;
	
	@GetMapping("/meusemprestimos")
	public ModelAndView emprestimo() {
		this.meusEmprestimos = emprestimoRepository.findAll();
		return new ModelAndView("emprestimo/list", "meusEmprestimos", this.meusEmprestimos);
	}
	
	@GetMapping("/novo")
	public ModelAndView createForm(@ModelAttribute Emprestimo emprestimo){
		this.livros = this.livroRepository.findAll();
		
		String sDataEmprestimo = formatDate("dataEmprestimo");
		String sDataDevolucao = formatDate("dataDevolucao");
		
		ModelAndView modelAndView = new ModelAndView("emprestimo/form");
		modelAndView.addObject("emprestimo", emprestimo);
		modelAndView.addObject("livros", livros);
		modelAndView.addObject("dataDevolucao", sDataDevolucao);
		modelAndView.addObject("dataEmprestimo", sDataEmprestimo);
		return modelAndView;
	}
	
	@PostMapping("/novoemprestimo")
	public ModelAndView create(@ModelAttribute("emprestimo") @Valid Emprestimo emprestimo, BindingResult bindingResult) {
		emprestimo.setUsuario(usuarioRepository.findOne(Long.parseLong("2")));
	
		insereDataDevolucao(emprestimo);
		insereDataEmprestimo(emprestimo);

		emprestimoRepository.save(emprestimo);
		
		this.meusEmprestimos = emprestimoRepository.findAll();
		return new ModelAndView("/meusemprestimos", "meusEmprestimos", this.meusEmprestimos);
	}
	
	public String formatDate(String tipoData) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Calendar dataAtual = Calendar.getInstance();
		
		String sDataFormatada = "";
		
		switch (tipoData) {
		case "dataDevolucao":
			dataAtual.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH + 15);
			Date dDevolucao = new Date();
			dDevolucao.setTime(dataAtual.getTimeInMillis());
			sDataFormatada = format.format(dDevolucao);
			
			break;
		case "dataEmprestimo":
			
			Date dEmprestimo = new Date();
			dEmprestimo.setTime(dataAtual.getTimeInMillis());
			sDataFormatada = format.format(dEmprestimo);
			break;
		}
		
		return sDataFormatada;
	}
	
	public void insereDataDevolucao(Emprestimo emprestimo) {
		Calendar dataAtual = Calendar.getInstance();
		dataAtual.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH + 15);
		Date dDevolucao = new Date();
		dDevolucao.setTime(dataAtual.getTimeInMillis());
		
		emprestimo.setDataDevolucao(dDevolucao);
	}
	
	public void insereDataEmprestimo(Emprestimo emprestimo) {
		Calendar dataAtual = Calendar.getInstance();
		Date dEmprestimo = new Date();
		dEmprestimo.setTime(dataAtual.getTimeInMillis());
		
		emprestimo.setDataDevolucao(dEmprestimo);
	}
	
}
