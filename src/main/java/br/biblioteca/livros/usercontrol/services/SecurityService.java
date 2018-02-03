package br.biblioteca.livros.usercontrol.services;

public interface SecurityService {

	//procura no banco  algum usuario com o login informado
	String findLoggedInUsername();

    void autologin(String username, String password);

//	//devolve usuario spring com base no login digitado pelo usuario
//	UserDetails findLoggedInUser();
//
//	//autentica usuatio
//	void login(String username, String password);
}
