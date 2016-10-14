package br.com.alphatechnology.crmpap.bean;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.alphatechnology.crmpap.model.Chamado;

@Named
@ConversationScoped
public class ChamadoBean implements Serializable {

	@Inject
	private Conversation conversation;

	private Chamado chamado;

	public Chamado getChamado() {
		return chamado;
	}

	public void termina() {
		if (!conversation.isTransient())
			conversation.end();
	}

	public void setChamado(Chamado chamado) {
		conversation.begin();
		this.chamado = chamado;
	}

}
