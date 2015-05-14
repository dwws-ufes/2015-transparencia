package br.ufes.inf.nemo.transparencia.persistence;

import javax.ejb.Local;

import br.ufes.inf.nemo.transparencia.domain.Pagamentos;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;

@Local
public interface PagamentosDAO extends BaseDAO<Pagamentos> {

}
