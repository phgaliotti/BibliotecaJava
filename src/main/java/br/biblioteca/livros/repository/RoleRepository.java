package br.biblioteca.livros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.biblioteca.livros.beans.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
