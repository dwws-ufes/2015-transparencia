package br.ufes.inf.nemo.transparencia.application;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.transparencia.domain.Orgao;
import br.ufes.inf.nemo.transparencia.persistence.OrgaoDAO;
import br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;

@Stateless
public class ManageOrgaosServiceBean extends CrudServiceBean<Orgao>
		implements ManageOrgaosService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB private OrgaoDAO orgaoDAO;

	@Override
	public BaseDAO<Orgao> getDAO() {
		// TODO Auto-generated method stub
		return orgaoDAO;
	}

	@Override
	protected Orgao createNewEntity() {
		// TODO Auto-generated method stub
		return new Orgao();
	}

}
