package br.biblioteca.livros.usercontrol.services;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import br.biblioteca.livros.beans.User;
import br.biblioteca.livros.repository.RoleRepository;
import br.biblioteca.livros.repository.UserRepository;

@Repository
public class UserServiceImpl implements UserService {
	
	@Autowired
    private UserRepository userRepository;
	
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


	@Override
	public void save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(new HashSet<>(roleRepository.findAll()));
		userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

}
