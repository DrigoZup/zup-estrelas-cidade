package br.com.zup.pojo;




public class Cidade {
	private String nome;
	private String cep;
	private int qtdHabitantes;
	private boolean capital;
	private String estado;
	private double rendaPerCapita;
	private String dataFundacao;
	
	
	public Cidade(String nome, String cep, int qtdHabitantes, boolean capital, String estado, double rendaPerCapita, String dataFundacao) {
		this.nome = nome;
		this.cep = cep;
		this.qtdHabitantes = qtdHabitantes;
		this.capital = capital;
		this.estado = estado;
		this.rendaPerCapita = rendaPerCapita;
		this.dataFundacao = dataFundacao;
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public int getQtdHabitantes() {
		return qtdHabitantes;
	}
	public void setQtdHabitantes(int qtdHabitantes) {
		this.qtdHabitantes = qtdHabitantes;
	}
	public boolean isCapital() {
		return capital;
	}
	public void setCapital(boolean capital) {
		this.capital = capital;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public double getRendaPerCapita() {
		return rendaPerCapita;
	}
	public void setRendaPerCapita(double rendaPerCapita) {
		this.rendaPerCapita = rendaPerCapita;
	}
	public String getDataFundacao() {
		return dataFundacao;
	}
	public void setDataFundacao(String dataFundacao) {
		this.dataFundacao = dataFundacao;
	}

}
