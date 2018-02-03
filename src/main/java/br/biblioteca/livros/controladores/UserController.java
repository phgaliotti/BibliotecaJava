package br.biblioteca.livros.controladores;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.biblioteca.livros.beans.Roles;
import br.biblioteca.livros.beans.Usuario;
import br.biblioteca.livros.repository.UsuarioRepository;
import br.biblioteca.livros.usercontrol.services.SecurityService;
import br.biblioteca.livros.usercontrol.validator.LoginValidator;
import br.biblioteca.livros.usercontrol.validator.UserValidator;

@Controller
@RequestMapping("/user")
public class UserController {
	private Iterable<Usuario> usuarios;

	@Autowired
	private UsuarioRepository usuarioRepository;

//	@Autowired
//	private SecurityService securityService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private LoginValidator loginValidator;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("usuarios/form", "userForm", new Usuario());
	}

	@PostMapping("/autentication")
	public ModelAndView autentication(@ModelAttribute("userForm") Usuario userForm, BindingResult bindingResult,
			Model model) {

		Usuario userAutentica = usuarioRepository.findByUsername(userForm.getUsername());
		loginValidator.validate(userAutentica, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("usuarios/form");
		}

		securityService.login(userAutentica.getUsername(), userAutentica.getPassword());

		return new ModelAndView("redirect:/");
	}

	@GetMapping(value = "/registration")
	public ModelAndView registration() {
		return new ModelAndView("usuarios/registration", "userForm", new Usuario());
	}

	@PostMapping(value = "/registration")
	public ModelAndView registrationform(@ModelAttribute("userForm") Usuario userForm, BindingResult bindingResult,
			Model model) {

		

		if (bindingResult.hasErrors()) {
			return new ModelAndView("/registration");
		}

		String password = bCryptPasswordEncoder.encode(userForm.getPassword());
		userForm.setPassword(password);
		userForm.setRoles(Roles.ROLE_ADMIN);
		//userForm.setRoles(Roles.ROLE_BASIC);

		usuarioRepository.save(userForm);
		userValidator.validate(userForm, bindingResult);

		try {
			securityService.login(userForm.getUsername(), password);

			return new ModelAndView("redirect:/");
		} catch (Exception e) {
			return new ModelAndView("redirect:/user/registration");
		}
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		SecurityContextHolder.clearContext();
		if (session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}

	@GetMapping("/list")
	public ModelAndView usuarios() {
		this.usuarios = usuarioRepository.findAll();
		return new ModelAndView("usuarios/list", "usuarios", this.usuarios);
	}

}
