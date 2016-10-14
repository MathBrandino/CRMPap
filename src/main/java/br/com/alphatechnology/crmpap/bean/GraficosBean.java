package br.com.alphatechnology.crmpap.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.PieChartModel;

import br.com.alphatechnology.crmpap.grafico.GeradorGrafico;

@Named
@ViewScoped
public class GraficosBean implements Serializable {

	@Inject
	private GeradorGrafico gerador;

	public PieChartModel getPieModel() {
		return gerador.getPieModel();
	}

	public BarChartModel getBarModel() {
		return gerador.getBarModel();
	}
	
	public PieChartModel getPieModelStatus(){
		return gerador.getPieModelStatus();
	}
}
