package br.com.alphatechnology.crmpap.bean;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.alphatechnology.crmpap.dao.ChamadoDao;
import br.com.alphatechnology.crmpap.model.Chamado;

@Named
@ViewScoped
public class ListaChamadoBean implements Serializable {

	@Inject
	private ChamadoDao dao;

	private ArrayList<Chamado> lista;

	@Transactional
	public ArrayList<Chamado> getLista() {

		if (lista == null) {

			lista = (ArrayList<Chamado>) dao.listar();
		}

		return lista;
	}

	@Transactional
	public void deleta(Long id) {
		Chamado chamado = dao.busca(id);
		dao.deleta(chamado);

		this.lista = (ArrayList<Chamado>) dao.listar();
	}

	public String vaiProForm() {

		return "form?faces-redirect=true";
	}

}
