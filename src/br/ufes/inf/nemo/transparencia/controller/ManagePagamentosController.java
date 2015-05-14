package br.ufes.inf.nemo.transparencia.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.transparencia.application.ManagePagamentosService;
import br.ufes.inf.nemo.transparencia.domain.Pagamentos;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;
import br.ufes.inf.nemo.util.ejb3.application.filters.SimpleFilter;
import br.ufes.inf.nemo.util.ejb3.controller.CrudController;

@Named
@SessionScoped
public class ManagePagamentosController extends CrudController<Pagamentos> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB 
	private ManagePagamentosService managePagamentosService;
	
	@Override
	protected CrudService<Pagamentos> getCrudService() {
		// TODO Auto-generated method stub
		return managePagamentosService;
	}

	@Override
	protected Pagamentos createNewEntity() {
		// TODO Auto-generated method stub
		return new Pagamentos();
	}	

	@Override
	protected void initFilters() {
		// TODO Auto-generated method stub
		addFilter(new SimpleFilter("managePagamentos.filter.byName", "codId", getI18nMessage("msgs", "managePagamentos.text.filter.byName")));

	}
	
	public ManagePagamentosController() {
	    viewPath = "/DespesasPagamentos/";
	    bundleName = "msgs";
	}

}
