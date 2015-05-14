package br.ufes.inf.nemo.transparencia.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;

/**
*
* @author Caio Barbosa (caio.barbosa@outlook.com)
* @version 1.0
*/
@StaticMetamodel(ElementoDespesa.class)
public class ElementoDespesa_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<ElementoDespesa, Integer> codElementoDespesa;
	public static volatile SingularAttribute<ElementoDespesa, String> strElementoDespesa;
	public static volatile SingularAttribute<ElementoDespesa, String> strDescricaoElementoDespesa;	
}
