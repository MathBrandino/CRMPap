package br.com.alphatechnology.crmpap.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.alphatechnology.crmpap.dao.UsuarioDao;
import br.com.alphatechnology.crmpap.model.Nivel;
import br.com.alphatechnology.crmpap.model.Usuario;

@Named
@SessionScoped
public class LoginBean implements Serializable {

	@Inject
	private UsuarioDao dao;

	private String login = "";

	private String senha = "";

	private Usuario usuario;

	@Transactional
	public String logar() {
		if (camposPreenchidos()) {
			usuario = dao.buscaUsuario(login, senha);
			if (usuario == null) {
				FacesContext.getCurrentInstance().addMessage("growl",
						new FacesMessage("Não autorizado", "Nenhum registro foi encontrado com essas informações"));
				return null;
			}
		} else {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro", "Algum campo está nulo"));
			return null;
		}

		return vaiParaDash();
	}

	public String vaiParaDash() {
		return "/chamados/dashboard?faces-redirect=true";
	}

	public String novo() {
		return "novo?faces-redirect=true";
	}

	private boolean camposPreenchidos() {
		if (login.trim().isEmpty() || senha.trim().isEmpty())
			return false;
		return true;
	}

	public String deslogar() {
		usuario = null;
		return "/usuario/login?faces-redirect=true";
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean isAdmin() {
		return usuario.getNivel() == Nivel.ADMIN;
	}

	public boolean isLogado() {
		// TODO Auto-generated method stub
		return usuario != null;
	}

	public Usuario getUsuario() {
		// TODO Auto-generated method stub
		return usuario;
	}

}
