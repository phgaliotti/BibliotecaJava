package br.biblioteca.livros.usercontrol.services;

import org.springframework.stereotype.Repository;

import br.biblioteca.livros.beans.User;

@Repository
public interface UserService {
    void save(User user);

    User findByUsername(String username);

	Iterable<User> findAll();
}