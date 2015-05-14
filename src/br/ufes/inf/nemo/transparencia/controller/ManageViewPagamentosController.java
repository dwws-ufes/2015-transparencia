package br.ufes.inf.nemo.transparencia.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BubbleChartModel;
import org.primefaces.model.chart.BubbleChartSeries;
import org.primefaces.model.chart.ChartSeries;

import br.ufes.inf.nemo.transparencia.application.ManageViewPagamentosService;
import br.ufes.inf.nemo.transparencia.view.ViewPagamentos;
import br.ufes.inf.nemo.util.ejb3.controller.JSFController;

@Named
@SessionScoped
public class ManageViewPagamentosController  extends JSFController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB 
	private ManageViewPagamentosService ManageViewPagamentosService;
	
	private BarChartModel barModel;
	
	@PersistenceContext
	private EntityManager entityManager;	
	
	private List<ViewPagamentos> despesas;

	public List<ViewPagamentos> getDespesas() {			
	
		// Monta a view com a soma das despesas por órgão
		String jpql = "select new br.ufes.inf.nemo.transparencia.view.ViewPagamentos(p.orgao.strNomeOrgao, " +
				"p.orgao.strSigla, sum(p.valValorRepasse) as total) " +
				"from Pagamentos p group by p.orgao.id order by (sum(p.valValorRepasse)) desc";		
				
		TypedQuery<ViewPagamentos> query = entityManager.createQuery(jpql, ViewPagamentos.class);
		
		// Seleciona as 10 maiores despesas
		List<ViewPagamentos> result = query.setMaxResults(10).getResultList();
		return result;
	}

	public void setDespesas(List<ViewPagamentos> despesas) {
		this.despesas = despesas;
	}	

    @PostConstruct
    public void init() {
        createBarModels();
    }
 
    public BarChartModel getBarModel() {
        return barModel;
    }
 
    // Carrega os dados para o gráfico
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries despesasBar = new ChartSeries();
        despesasBar.setLabel("Despesas");
        List<ViewPagamentos> pagamentos = getDespesas();
        
        // Preenche o gráficos os dados
        for(ViewPagamentos pagamento : pagamentos)
        {
        	despesasBar.set(pagamento.getSiglaOrgao(), pagamento.getTotal()/1000000 );
        }

        model.addSeries(despesasBar);
         
        return model;
    }
     
    private void createBarModels() {
        createBarModel();
    }
    
    // Cria e configura o gráfico
    private void createBarModel() {
        barModel = initBarModel();
         
        barModel.setTitle("10 maiores despesas");
        barModel.setLegendPosition("ne");
        
        barModel.setAnimate(true);
         
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Despesas de Órgãos");
         
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Despesas (em milhões)");
        yAxis.setMin(0);
        yAxis.setMax(3000); 
    }  
}