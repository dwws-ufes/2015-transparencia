package br.ufes.inf.nemo.transparencia.view;


public class ViewFornecedores{

	private Long codOrgao;
	private String nomeOrgao;
	private String siglaOrgao;
	private String strNomeFavorecido;
	private double total;
	
	public ViewFornecedores(String strNomeFavorecido, double total) {
		this.total = total;
		this.strNomeFavorecido = strNomeFavorecido;
	}
	/*
	public ViewFornecedores(String nomeOrgao, String siglaOrgao,double total) {
		this.nomeOrgao = nomeOrgao;
		this.siglaOrgao = siglaOrgao;
		this.total = total;
	}
*/
	public Long getCodOrgao() {
		return codOrgao;
	}

	public void setCodOrgao(Long codOrgao) {
		this.codOrgao = codOrgao;
	}

	public String getNomeOrgao() {
		return nomeOrgao;
	}

	public void setNomeOrgao(String nomeOrgao) {
		this.nomeOrgao = nomeOrgao;
	}

	public String getSiglaOrgao() {
		return siglaOrgao;
	}

	public void setSiglaOrgao(String siglaOrgao) {
		this.siglaOrgao = siglaOrgao;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}	


	public String getStrNomeFavorecido() {
		int maxStr;
		if (strNomeFavorecido.length() < 40)
			maxStr = strNomeFavorecido.length();
		else
			maxStr = 40;
		return strNomeFavorecido.substring(0,maxStr);
	}
	
	public void setStrNomeFavorecido(String strNomeFavorecido) {
		this.strNomeFavorecido = strNomeFavorecido;
	}
}