//package br.biblioteca.livros.usercontrol.validator;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.ValidationUtils;
//import org.springframework.validation.Validator;
//
//import br.biblioteca.livros.beans.Usuario;
//import br.biblioteca.livros.repository.UserRepository;
//
//
//@Component
//public class LoginValidator implements Validator {
//	
//	@Autowired
//	private UserRepository usuarioRepository;
//
//	@Override
//	public boolean supports(Class<?> aClass) {
//		return Usuario.class.equals(aClass);
//	}
//
//	@Override
//	public void validate(Object o, Errors errors) {
//		Usuario usuario = (Usuario) o;
//
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
//		
//		if (usuarioRepository.findOne(usuario.getId()) == null) {
//			errors.rejectValue("username", "NotExist.userForm.username");
//		}
//		
//	}
//}