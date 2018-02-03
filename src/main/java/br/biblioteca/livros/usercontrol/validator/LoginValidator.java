package br.biblioteca.livros.usercontrol.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.biblioteca.livros.beans.User;
import br.biblioteca.livros.repository.UserRepository;


@Component
public class LoginValidator implements Validator {
	
	@Autowired
	private UserRepository UserRepository;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		User User = (User) o;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		
		if (UserRepository.findOne(User.getId()) == null) {
			errors.rejectValue("username", "NotExist.userForm.username");
		}
		
	}
}