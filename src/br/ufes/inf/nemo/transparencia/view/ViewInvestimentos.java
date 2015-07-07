package br.ufes.inf.nemo.transparencia.view;

public class ViewInvestimentos {
	private String municipio;
	private double totalDeInvestimentos;
	private int totalPopulacao;
	private String url;
	
	public ViewInvestimentos(double totalDeInvestimentos) {
		this.totalDeInvestimentos = totalDeInvestimentos;
	}
	
	public ViewInvestimentos() {
	}

	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public double getTotalDeInvestimentos() {
		return totalDeInvestimentos;
	}
	public void setTotalDeInvestimentos(double totalDeInvestimentos) {
		this.totalDeInvestimentos = totalDeInvestimentos;
	}
	public int getTotalPopulacao() {
		return totalPopulacao;
	}
	public void setTotalPopulacao(int totalPopulacao) {
		this.totalPopulacao = totalPopulacao;
	}
	public double getTotalDeInvestimentosPerCapita() {
		return this.totalDeInvestimentos / this.totalPopulacao;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
