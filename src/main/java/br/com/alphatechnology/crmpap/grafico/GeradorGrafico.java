package br.com.alphatechnology.crmpap.grafico;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.PieChartModel;

import br.com.alphatechnology.crmpap.dao.ChamadoDao;
import br.com.alphatechnology.crmpap.model.Chamado;
import br.com.alphatechnology.crmpap.model.Produto;
import br.com.alphatechnology.crmpap.model.Status;
import br.com.alphatechnology.crmpap.model.TipoChamado;

public class GeradorGrafico implements Serializable {

	@Inject
	private ChamadoDao chamadoDao;

	private PieChartModel pieChart;
	private PieChartModel pieChartStatus;
	private BarChartModel barChartModel;

	@PostConstruct
	public void inicia() {
		pieChart = new PieChartModel();
		pieChartStatus = new PieChartModel();
		barChartModel = new BarChartModel();
	}

	public PieChartModel getPieModel() {

		List<Chamado> chamados = chamadoDao.listar();
		int elogios = 0, reclamacoes = 0, sugestoes = 0;
		for (Chamado chamado : chamados) {
			if (chamado.getTipo() == TipoChamado.ELOGIO) {
				elogios++;
			} else if (chamado.getTipo() == TipoChamado.RECLAMACAO) {
				reclamacoes++;
			} else {
				sugestoes++;
			}

		}

		pieChart.set("Reclamações", reclamacoes);
		pieChart.set("Elogios", elogios);
		pieChart.set("Sugestões", sugestoes);

		pieChart.setTitle("Analise - Tipo dos Chamados");

		pieChart.setLegendPosition("e");
		pieChart.setFill(false);
		pieChart.setShowDataLabels(true);
		// pieChart.setDiameter(150);

		return pieChart;
	}

	public BarChartModel getBarModel() {

		BarChartSeries reclamacaoSeries = new BarChartSeries();
		reclamacaoSeries.setLabel("Reclamação");

		List<Chamado> listaReclamacao = chamadoDao.listaOrdenadoPeloProdutoComTipo(TipoChamado.RECLAMACAO);
		populaSerie(reclamacaoSeries, listaReclamacao);

		BarChartSeries elogiosSeries = new BarChartSeries();
		elogiosSeries.setLabel("Elogios");

		List<Chamado> listaElogios = chamadoDao.listaOrdenadoPeloProdutoComTipo(TipoChamado.ELOGIO);
		populaSerie(elogiosSeries, listaElogios);

		BarChartSeries sugestoesSeries = new BarChartSeries();
		sugestoesSeries.setLabel("Sugestoes");

		List<Chamado> listaSugestoes = chamadoDao.listaOrdenadoPeloProdutoComTipo(TipoChamado.SUGESTACAO);
		populaSerie(sugestoesSeries, listaSugestoes);

		barChartModel.addSeries(sugestoesSeries);
		barChartModel.addSeries(elogiosSeries);
		barChartModel.addSeries(reclamacaoSeries);
		barChartModel.setLegendPosition("ne");

		barChartModel.setTitle("Analise - Produtos No Mercado");

		return barChartModel;
	}

	public void populaSerie(BarChartSeries series, List<Chamado> lista) {
		Produto produto = lista.get(0).getProduto();
		int contador = 0;

		for (Chamado chamado : lista) {
			if (chamado.getProduto().equals(produto)) {
				contador++;
			} else {

				series.set(produto.getNome(), contador);
				contador = 1;
				produto = chamado.getProduto();

			}
		}
		series.set(produto.getNome(), contador);

	}

	public PieChartModel getPieModelStatus() {

		long novos = chamadoDao.devolveResultadoPeloStatus(Status.NOVO);
		long pendentes = chamadoDao.devolveResultadoPeloStatus(Status.PENDENTE);
		long processados = chamadoDao.devolveResultadoPeloStatus(Status.PROCESSANDO);
		long finalizados = chamadoDao.devolveResultadoPeloStatus(Status.FINALIZADO);
		
		System.out.println("Novos :" + novos);
	
		pieChartStatus.set("Novos", novos);
		pieChartStatus.set("Pendentes", pendentes);
		pieChartStatus.set("Processando", processados);
		pieChartStatus.set("Finalizados", finalizados);

		pieChartStatus.setTitle("Analise - Status dos Chamados");

		pieChartStatus.setLegendPosition("w");
		pieChartStatus.setShowDataLabels(true);
		
		return pieChartStatus;
	}

}
