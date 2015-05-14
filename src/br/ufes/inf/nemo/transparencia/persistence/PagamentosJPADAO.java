package br.ufes.inf.nemo.transparencia.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufes.inf.nemo.transparencia.domain.Pagamentos;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseJPADAO;

@Stateless
public class PagamentosJPADAO extends BaseJPADAO<Pagamentos> implements PagamentosDAO {
	/** TODO: document this field. */
	private static final long serialVersionUID = 1L;
	
	/** TODO: document this field. */
	@PersistenceContext 
	private EntityManager entityManager;
	
	/** @see br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO#getDomainClass() */
	@Override
	public Class<Pagamentos> getDomainClass() {
		return Pagamentos.class;
	}

	/** @see br.ufes.inf.nemo.util.ejb3.persistence.BaseJPADAO#getEntityManager() */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

}
