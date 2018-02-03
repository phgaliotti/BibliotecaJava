package br.biblioteca.livros.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.biblioteca.livros.beans.User;
import br.biblioteca.livros.usercontrol.services.SecurityService;
import br.biblioteca.livros.usercontrol.services.UserService;
import br.biblioteca.livros.usercontrol.validator.UserValidator;

@Controller
@RequestMapping("/user")
public class UserController {
	//private Iterable<User> usuarios;

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserValidator userValidator;

	// Registro de usuarios no sistema
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		return new ModelAndView("usuarios/registration", "userForm", new User());
	}

	@PostMapping(value = "/registration")
	public ModelAndView registrationform(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,
			Model model) {
		
		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("/registration");
		}

		userService.save(userForm);

		try {
			securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
			return new ModelAndView("redirect:/");
		} catch (Exception e) {
			return new ModelAndView("redirect:/user/registration");
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "login";
	}

	// @GetMapping("/login")
	// public ModelAndView login() {
	// return new ModelAndView("usuarios/form", "userForm", new Usuario());
	// }

	// @PostMapping("/autentication")
	// public ModelAndView autentication(@ModelAttribute("userForm") Usuario
	// userForm, BindingResult bindingResult,
	// Model model) {
	//
	// Usuario userAutentica =
	// usuarioRepository.findByUsername(userForm.getUsername());
	// loginValidator.validate(userAutentica, bindingResult);
	//
	// if (bindingResult.hasErrors()) {
	// return new ModelAndView("usuarios/form");
	// }
	//
	// securityService.login(userAutentica.getUsername(),
	// userAutentica.getPassword());
	//
	// return new ModelAndView("redirect:/");
	// }

	// @GetMapping("/logout")
	// public String logout(HttpServletRequest request) {
	// HttpSession session = request.getSession(false);
	// SecurityContextHolder.clearContext();
	// if (session != null) {
	// session.invalidate();
	// }
	// return "redirect:/";
	// }

//	@GetMapping("/list")
//	public ModelAndView usuarios() {
//		this.usuarios = usuarioRepository.findAll();
//		return new ModelAndView("usuarios/list", "usuarios", this.usuarios);
//	}

}
