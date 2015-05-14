package br.ufes.inf.nemo.transparencia.application;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.transparencia.persistence.ViewPagamentosDAO;

@Stateless
public class ManageViewPagamentosServiceBean implements ManageViewPagamentosService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB 
	private ViewPagamentosDAO ViewPagamentosDAO;

}
