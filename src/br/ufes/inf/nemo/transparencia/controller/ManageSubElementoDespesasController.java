package br.ufes.inf.nemo.transparencia.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.transparencia.application.ManageSubElementoDespesasService;
import br.ufes.inf.nemo.transparencia.domain.SubElementoDespesa;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;
import br.ufes.inf.nemo.util.ejb3.application.filters.SimpleFilter;
import br.ufes.inf.nemo.util.ejb3.controller.CrudController;

@Named
@SessionScoped
public class ManageSubElementoDespesasController extends CrudController<SubElementoDespesa> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB 
	private ManageSubElementoDespesasService manageSubElementoDespesasService;
	
	@Override
	protected CrudService<SubElementoDespesa> getCrudService() {
		// TODO Auto-generated method stub
		return manageSubElementoDespesasService;
	}

	@Override
	protected SubElementoDespesa createNewEntity() {
		// TODO Auto-generated method stub
		return new SubElementoDespesa();
	}

	@Override
	protected void initFilters() {
		// TODO Auto-generated method stub
		addFilter(new SimpleFilter("manageSubElementoDespesas.filter.byName", "strNomeSubElementoDespesa", getI18nMessage("msgs", "manageSubElementoDespesas.text.filter.byName")));

	}
	
	public ManageSubElementoDespesasController() {
	    viewPath = "/CadastroSubElementoDespesa/";
	    bundleName = "msgs";
	}

}
