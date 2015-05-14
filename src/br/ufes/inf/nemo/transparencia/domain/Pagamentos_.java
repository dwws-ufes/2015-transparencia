package br.ufes.inf.nemo.transparencia.domain;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;

/**
*
* @author Caio Barbosa (caio.barbosa@outlook.com)
* @version 1.0
*/
@StaticMetamodel(Pagamentos.class)
public class Pagamentos_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<Pagamentos, Orgao> orgao;
	public static volatile SingularAttribute<Pagamentos, Integer> codOrgao;
	public static volatile SingularAttribute<Pagamentos, Integer> codGestaoEmitente;
	public static volatile SingularAttribute<Pagamentos, String> strOrdemBancaria;
	public static volatile SingularAttribute<Pagamentos, String> strCpfCnpjNis;
	public static volatile SingularAttribute<Pagamentos, String> strNomeFavorecido;
	public static volatile SingularAttribute<Pagamentos, String> strNumProcesso;
	public static volatile SingularAttribute<Pagamentos, Float> valValorRepasse;
	public static volatile SingularAttribute<Pagamentos, Date> datEmissaoOrdemBancaria;
	public static volatile SingularAttribute<Pagamentos, String> strHistoricoDocumento;
	public static volatile SingularAttribute<Pagamentos, String> numDocumentoEmpenhado;
}
