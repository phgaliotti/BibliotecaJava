package br.biblioteca.livros.usercontrol.services;

import br.biblioteca.livros.beans.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}