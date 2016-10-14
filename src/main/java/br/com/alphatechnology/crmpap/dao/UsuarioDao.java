package br.com.alphatechnology.crmpap.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.alphatechnology.crmpap.model.Usuario;

public class UsuarioDao implements Serializable {

	@PersistenceContext
	private EntityManager manager;

	public Usuario buscaUsuario(String login, String senha) {

		try {
			Usuario doBanco = (Usuario) manager
					.createQuery("select u from Usuario u where login = :login and senha = :senha")
					.setParameter("login", login).setParameter("senha", senha).getSingleResult();

			return doBanco;
		} catch (Exception e) {
			return null;
		}
	}

	public void salva(Usuario usuario) {
		manager.persist(usuario);
	}

	public Usuario buscaUsuario(String login) {
		try {
			Usuario doBanco = (Usuario) manager.createQuery("select u from Usuario u where login = :login")
					.setParameter("login", login).getSingleResult();

			return doBanco;
		} catch (Exception e) {
			return null;
		}
	}

}
