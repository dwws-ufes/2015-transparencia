package br.ufes.inf.nemo.transparencia.application;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.transparencia.domain.ElementoDespesa;
import br.ufes.inf.nemo.transparencia.persistence.ElementoDespesaDAO;
import br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;

@Stateless
public class ManageElementoDespesasServiceBean extends CrudServiceBean<ElementoDespesa>
		implements ManageElementoDespesasService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB private ElementoDespesaDAO ElementoDespesaDAO;

	@Override
	public BaseDAO<ElementoDespesa> getDAO() {
		// TODO Auto-generated method stub
		return ElementoDespesaDAO;
	}

	@Override
	protected ElementoDespesa createNewEntity() {
		// TODO Auto-generated method stub
		return new ElementoDespesa();
	}

}
