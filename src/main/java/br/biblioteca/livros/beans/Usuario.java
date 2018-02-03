package br.biblioteca.livros.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	public Usuario() { }

	public Usuario(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String username;

	@NotNull
	private String password;
	
	@NotNull
	private Roles role;

	@OneToMany(mappedBy = "usuario")
	private List<Review> reviews = new ArrayList<>();

	public List<Review> getReviews() {
		return reviews;
	}

	public Roles getRoles() {
		return role;
	}

	public void setRoles(Roles role) {
		this.role = role;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Usuario [username=" + username + ", password=" + this.password + ", roles=" + this.role.toString() + "]";
	}

}
