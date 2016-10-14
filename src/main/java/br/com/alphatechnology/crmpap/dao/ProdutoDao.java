package br.com.alphatechnology.crmpap.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.alphatechnology.crmpap.model.Produto;

public class ProdutoDao implements Serializable {

	@PersistenceContext
	private EntityManager manager;

	public List<Produto> listar() {

		return manager.createQuery("Select p from Produto p").getResultList();
	}

	public void salva(Produto produto) {
		manager.persist(produto);
	}

	public Produto buscaPelo(Long id) {
		// TODO Auto-generated method stub
		return manager.find(Produto.class, id);

	}

	
}
