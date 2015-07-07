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
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.ufes.inf.nemo.transparencia.application.ManageViewFornecedoresService;
import br.ufes.inf.nemo.transparencia.view.ViewFornecedores;
import br.ufes.inf.nemo.util.ejb3.controller.JSFController;

@Named
@SessionScoped
public class ManageViewFornecedoresController  extends JSFController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB 
	private ManageViewFornecedoresService ManageViewFornecedoresService;
	
	private HorizontalBarChartModel horizontalBarModel;
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<ViewFornecedores> getDespesas() {			
	
		// Monta a view com a soma das despesas por órgão
		String jpql = "select new br.ufes.inf.nemo.transparencia.view.ViewFornecedores(p.strNomeFavorecido as  strNomeFavorecido , " +
				"sum(p.valValorRepasse) as total) " +
				"from Pagamentos p group by p.strNomeFavorecido order by (sum(p.valValorRepasse)) desc";		
				
		TypedQuery<ViewFornecedores> query = entityManager.createQuery(jpql, ViewFornecedores.class);
		
		// Seleciona as 10 maiores despesas
		List<ViewFornecedores> result = query.setMaxResults(30).getResultList();
		return result;
	}

    @PostConstruct
    public void init() {
        createBarModels();
    }
 
    public HorizontalBarChartModel getHorizontalBarModel() {
        return horizontalBarModel;
    }
 
    // Carrega os dados para o gráfico
    private HorizontalBarChartModel initBarModel() {
    	HorizontalBarChartModel model = new HorizontalBarChartModel();
 
        ChartSeries despesasBar = new ChartSeries();
        despesasBar.setLabel("Despesas");
        List<ViewFornecedores> pagamentos = getDespesas();
        
        // Preenche o gráficos os dados
        for(ViewFornecedores pagamento : pagamentos)
        {
        	despesasBar.set(pagamento.getStrNomeFavorecido(), pagamento.getTotal()/1000000 );
        }

        model.addSeries(despesasBar);
         
        return model;
    }
     
    private void createBarModels() {
        createHorizontalBarModel();
    }
    
    // Cria e configura o gráfico
    private void createHorizontalBarModel() {
    	horizontalBarModel = initBarModel();
         
    	horizontalBarModel.setTitle("30 maiores fornecedores");
    	horizontalBarModel.setLegendPosition("ne");
    	horizontalBarModel.setZoom(true);
        
    	horizontalBarModel.setAnimate(true);
         
        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Despesas (em milh�es)");
        xAxis.setTickAngle(-90);
        xAxis.setMax(3800); 
         
        Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Fornecedores");
        yAxis.setMin(0);
        
    }  
}