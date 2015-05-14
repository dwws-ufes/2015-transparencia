package br.ufes.inf.nemo.transparencia.view;


public class ViewPagamentos{

	private Long codOrgao;
	private String nomeOrgao;
	private String siglaOrgao;
	private double total;
	
	public ViewPagamentos(Long codOrgao, String nomeOrgao, String siglaOrgao,
			double total) {
		this.codOrgao = codOrgao;
		this.nomeOrgao = nomeOrgao;
		this.siglaOrgao = siglaOrgao;
		this.total = total;
	}
	
	public ViewPagamentos(String nomeOrgao, String siglaOrgao,double total) {
		this.nomeOrgao = nomeOrgao;
		this.siglaOrgao = siglaOrgao;
		this.total = total;
	}

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
}
