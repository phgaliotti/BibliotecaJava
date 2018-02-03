package br.biblioteca.livros.beans;

public enum Roles {

	ROLE_BASIC("ROLE_BASIC"), 
	ROLE_ADMIN("ROLE_ADMIN");

	private String role;

	Roles(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}
