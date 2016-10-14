package br.com.alphatechnology.crmpap.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.alphatechnology.crmpap.bean.LoginBean;
import br.com.alphatechnology.crmpap.model.Chamado;
import br.com.alphatechnology.crmpap.model.Status;
import br.com.alphatechnology.crmpap.model.TipoChamado;

public class ChamadoDao implements Serializable {

	@Inject
	private LoginBean loginBean;

	@PersistenceContext
	private EntityManager manager;

	public void salva(Chamado chamado) {
		manager.persist(chamado);
	}

	public List<Chamado> listar() {
		List<Chamado> chamados;
		if (loginBean.isAdmin()) {
			chamados = manager.createQuery("select c from Chamado c").getResultList();
		} else {
			chamados = manager.createQuery("select c from Chamado c where c.usuario = :usuario")
					.setParameter("usuario", loginBean.getUsuario()).getResultList();

		}

		return chamados;
	}

	
	public List<Chamado> listaOrdenadoPeloProdutoComTipo(TipoChamado tipoChamado){
		
		return manager.createQuery("select c from Chamado c where c.tipo = :tipo order by c.produto ")
				.setParameter("tipo", tipoChamado)
				.getResultList();
	}
	
	public long devolveResultadoPeloStatus(Status status){
		
		return (long) manager.createQuery("select count(c) from Chamado c where c.status = :status ")
				.setParameter("status", status)
				.getSingleResult();
	}
	
	public void deleta(Chamado chamado) {
		manager.remove(chamado);
		manager.flush();
	}

	public Chamado busca(Long id) {
		return manager.find(Chamado.class, id);
	}

	public void atualiza(Chamado chamado) {
		manager.merge(chamado);
	}
}
