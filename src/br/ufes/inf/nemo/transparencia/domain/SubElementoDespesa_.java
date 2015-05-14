package br.ufes.inf.nemo.transparencia.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;

/**
*
* @author Caio Barbosa (caio.barbosa@outlook.com)
* @version 1.0
*/
@StaticMetamodel(SubElementoDespesa.class)
public class SubElementoDespesa_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<SubElementoDespesa, String> strSubelementoDespesa;
}
