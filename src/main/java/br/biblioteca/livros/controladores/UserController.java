package br.biblioteca.livros.controladores;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.biblioteca.livros.beans.User;
import br.biblioteca.livros.usercontrol.services.SecurityService;
import br.biblioteca.livros.usercontrol.services.UserService;
import br.biblioteca.livros.usercontrol.validator.LoginValidator;
import br.biblioteca.livros.usercontrol.validator.UserValidator;

@Controller
@RequestMapping("/user")
public class UserController {
	private Iterable<User> usuarios;

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private LoginValidator loginValidator;

	// Registro de usuarios no sistema
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		return new ModelAndView("usuarios/registration", "userForm", new User());
	}

	@PostMapping(value = "/registration")
	public ModelAndView registrationform(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,
			Model model) {
		
		userValidator.validate(userForm, bindingResult);
		
		userService.save(userForm);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("/usuarios/registration");
		}

		try {
			securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
			return new ModelAndView("redirect:/");
		} catch (Exception e) {
			return new ModelAndView("redirect:/usuarios/registration");
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(Model model, String error, String logout) {
		return new ModelAndView("usuarios/form", "userForm", new User());
	}


	 @PostMapping("/login")
	 public ModelAndView autentication(@ModelAttribute("userForm") User
			 							userForm, BindingResult bindingResult,
			 							Model model) {
	
		User userAutentica = userService.findByUsername(userForm.getUsername());
		loginValidator.validate(userAutentica, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return new ModelAndView("usuarios/form");
		}
		
		try {
			securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
			return new ModelAndView("redirect:/");
		} catch (Exception e) {
			return new ModelAndView("redirect:/usuarios/registration");
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
		this.usuarios = userService.findAll();
		return new ModelAndView("usuarios/list", "usuarios", this.usuarios);
	}

}
