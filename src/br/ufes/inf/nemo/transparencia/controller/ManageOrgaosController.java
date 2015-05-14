package br.ufes.inf.nemo.transparencia.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.transparencia.application.ManageOrgaosService;
import br.ufes.inf.nemo.transparencia.domain.Orgao;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;
import br.ufes.inf.nemo.util.ejb3.application.filters.SimpleFilter;
import br.ufes.inf.nemo.util.ejb3.controller.CrudController;

@Named
@SessionScoped
public class ManageOrgaosController extends CrudController<Orgao> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB 
	private ManageOrgaosService manageOrgaosService;
	
	@Override
	protected CrudService<Orgao> getCrudService() {
		// TODO Auto-generated method stub
		return manageOrgaosService;
	}

	@Override
	protected Orgao createNewEntity() {
		// TODO Auto-generated method stub
		return new Orgao();
	}

	@Override
	protected void initFilters() {
		// TODO Auto-generated method stub
		addFilter(new SimpleFilter("manageOrgaos.filter.byName", "strNomeOrgao", getI18nMessage("msgs", "manageOrgaos.text.filter.byName")));

	}
	
	public ManageOrgaosController() {
	    viewPath = "/CadastroOrgao/";
	    bundleName = "msgs";
	}

}
