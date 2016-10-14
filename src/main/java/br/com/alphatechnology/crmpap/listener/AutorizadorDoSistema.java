package br.com.alphatechnology.crmpap.listener;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;

import br.com.alphatechnology.crmpap.bean.LoginBean;

public class AutorizadorDoSistema implements PhaseListener {

	@Inject
	private LoginBean loginBean;

	@Override
	public void afterPhase(PhaseEvent event) {

		FacesContext context = event.getFacesContext();

		if ("/usuario/login.xhtml".equals(context.getViewRoot().getViewId())
				|| "/usuario/novo.xhtml".equals(context.getViewRoot().getViewId())) {
			return;
		}

		if (!loginBean.isLogado()) {
			NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();

			navigationHandler.handleNavigation(context, null, "/usuario/login?faces-redirect=true");

			context.renderResponse();
		}
	}

	@Override
	public void beforePhase(PhaseEvent event) {

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
