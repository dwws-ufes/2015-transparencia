package br.ufes.inf.nemo.transparencia.application;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.transparencia.domain.SubElementoDespesa;
import br.ufes.inf.nemo.transparencia.persistence.SubElementoDespesaDAO;
import br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;

@Stateless
public class ManageSubElementoDespesasServiceBean extends CrudServiceBean<SubElementoDespesa>
		implements ManageSubElementoDespesasService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB private SubElementoDespesaDAO SubElementoDespesaDAO;

	@Override
	public BaseDAO<SubElementoDespesa> getDAO() {
		// TODO Auto-generated method stub
		return SubElementoDespesaDAO;
	}

	@Override
	protected SubElementoDespesa createNewEntity() {
		// TODO Auto-generated method stub
		return new SubElementoDespesa();
	}

}
