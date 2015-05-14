package br.ufes.inf.nemo.transparencia.application;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.transparencia.domain.Pagamentos;
import br.ufes.inf.nemo.transparencia.persistence.PagamentosDAO;
import br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;

@Stateless
public class ManagePagamentosServiceBean extends CrudServiceBean<Pagamentos>
		implements ManagePagamentosService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB private PagamentosDAO PagamentosDAO;

	@Override
	public BaseDAO<Pagamentos> getDAO() {
		// TODO Auto-generated method stub
		return PagamentosDAO;
	}

	@Override
	protected Pagamentos createNewEntity() {
		// TODO Auto-generated method stub
		return new Pagamentos();
	}

}
