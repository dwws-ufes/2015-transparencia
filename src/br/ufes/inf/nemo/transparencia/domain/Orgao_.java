package br.ufes.inf.nemo.transparencia.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;

/**
*
* @author Caio Barbosa (caio.barbosa@outlook.com)
* @version 1.0
*/
@StaticMetamodel(Orgao.class)
public class Orgao_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<Orgao, String> strNomeOrgao;
	public static volatile SingularAttribute<Orgao, Integer> codTipoPoder;
	public static volatile SingularAttribute<Orgao, String> strSigla;
}
