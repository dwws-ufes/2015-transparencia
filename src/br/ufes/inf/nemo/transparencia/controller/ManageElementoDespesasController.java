package br.ufes.inf.nemo.transparencia.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.transparencia.application.ManageElementoDespesasService;
import br.ufes.inf.nemo.transparencia.domain.ElementoDespesa;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;
import br.ufes.inf.nemo.util.ejb3.application.filters.SimpleFilter;
import br.ufes.inf.nemo.util.ejb3.controller.CrudController;

@Named
@SessionScoped
public class ManageElementoDespesasController extends CrudController<ElementoDespesa> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB 
	private ManageElementoDespesasService manageElementoDespesasService;
	
	@Override
	protected CrudService<ElementoDespesa> getCrudService() {
		// TODO Auto-generated method stub
		return manageElementoDespesasService;
	}

	@Override
	protected ElementoDespesa createNewEntity() {
		// TODO Auto-generated method stub
		return new ElementoDespesa();
	}

	@Override
	protected void initFilters() {
		// TODO Auto-generated method stub
		addFilter(new SimpleFilter("manageElementoDespesas.filter.byName", "strNomeElementoDespesa", getI18nMessage("msgs", "manageElementoDespesas.text.filter.byName")));

	}
	
	public ManageElementoDespesasController() {
	    viewPath = "/CadastroElementoDespesa/";
	    bundleName = "msgs";
	}

}
