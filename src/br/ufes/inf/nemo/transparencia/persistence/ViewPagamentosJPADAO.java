package br.ufes.inf.nemo.transparencia.persistence;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.ufes.inf.nemo.transparencia.view.ViewPagamentos;

@Stateless
public class ViewPagamentosJPADAO implements ViewPagamentosDAO {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** TODO: document this field. */
	@PersistenceContext
	private EntityManager entityManager;

	public List<ViewPagamentos> getViewPagamentos() {	
		String jpql = "select new br.ufes.inf.nemo.transparencia.view.ViewPagamentos(p.codOrgao, (sum(p.valValorRepasse)/1000000) as total) " +
				"from Pagamentos p group by p.codOrgao order by (sum(p.valValorRepasse)/1000000)";
		TypedQuery<ViewPagamentos> query = entityManager.createQuery(jpql, ViewPagamentos.class);
		List<ViewPagamentos> result = query.getResultList();
		return result;
	}
}