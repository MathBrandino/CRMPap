package br.com.alphatechnology.crmpap.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.alphatechnology.crmpap.dao.ChamadoDao;
import br.com.alphatechnology.crmpap.dao.ProdutoDao;
import br.com.alphatechnology.crmpap.model.Chamado;
import br.com.alphatechnology.crmpap.model.Produto;
import br.com.alphatechnology.crmpap.model.Status;
import br.com.alphatechnology.crmpap.model.TipoChamado;

@Model
public class FormularioChamadoBean implements Serializable {

	private Chamado chamado = new Chamado();

	private List<Produto> produtos;

	private Long idProduto;

	@Inject
	private LoginBean loginBean;

	@Inject
	private ProdutoDao produtoDao;

	@Inject
	private ChamadoBean chamadoBean;

	@Inject
	private ChamadoDao dao;

	public Chamado getChamado() {
		if (chamadoBean != null && chamadoBean.getChamado() != null) {
			chamado = chamadoBean.getChamado();
		}
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}

	public TipoChamado[] getTipos() {
		return TipoChamado.values();
	}

	@Transactional
	public void salvar() {

		if (validaCampos()) {
			if (chamado.getId() == null) {
				populaChamado();
				dao.salva(chamado);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Sucesso", "Adicionado com sucesso"));

			} else {
				dao.atualiza(chamado);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Sucesso", "Atualizado com sucesso"));

			}

			chamado = new Chamado();

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Problema", "Algum campo está inválido"));

		}

	}

	public Status[] getListaStatus() {
		return Status.values();
	}

	private void populaChamado() {
		chamado.setData(Calendar.getInstance());
		chamado.setProduto(produtoDao.buscaPelo(idProduto));
		chamado.setUsuario(loginBean.getUsuario());
		chamado.setStatus(Status.NOVO);
	}

	private boolean validaCampos() {
		if (temPalavrao(chamado.getDescricao()) || temPalavrao(chamado.getTitulo()))
			return false;
		return true;
	}

	public String vaiParaLista() {
		chamadoBean.termina();
		return "list?faces-redirect=true";
	}

	private boolean temPalavrao(String texto) {
		if (texto.contains("filho da puta") || texto.contains("merda") || texto.contains("porra")
				|| texto.contains("caralho")) {
			return true;
		}

		return false;
	}

	@Transactional
	public List<Produto> getProdutos() {
		if (produtos == null) {
			produtos = produtoDao.listar();
		}
		return produtos;
	}

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

}
