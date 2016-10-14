package br.com.alphatechnology.crmpap.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.alphatechnology.crmpap.dao.UsuarioDao;
import br.com.alphatechnology.crmpap.model.Nivel;
import br.com.alphatechnology.crmpap.model.Usuario;

@Named
@ViewScoped
public class CadastroLoginBean implements Serializable {

	@Inject
	private Usuario usuario;

	@Inject
	private UsuarioDao dao;

	public String vaiParaLogin() {
		return "login?faces-redirect=true";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Nivel[] getNiveis() {
		return Nivel.values();
	}

	@Transactional
	public void salvar() {
		Usuario buscaUsuario = dao.buscaUsuario(usuario.getLogin());
		if (buscaUsuario == null) {
			dao.salva(usuario);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuário inserido"));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Desculpe", "Esse usuario já existe"));
		}

		usuario = new Usuario();
	}
}
