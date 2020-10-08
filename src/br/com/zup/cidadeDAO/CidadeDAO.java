package br.com.zup.cidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.zup.factory.ConnectionFactory;
import br.com.zup.pojo.Cidade;

public class CidadeDAO {
	
	private Connection conexao;
	
	public CidadeDAO() {
		this.conexao = new ConnectionFactory().getConnection();
	}
	
public boolean insereCidade(Cidade cidade) throws SQLException {
		
		String sql = "insert into piloto"
		+ "(nome, cep, numero_habitantes, capital, estado, renda_percapita, data_fundacao"
		+ "values (?, ?, ?, ?, ?, ?)";
		
		PreparedStatement statment = conexao.prepareStatement(sql);
		
		statment.setString(1, cidade.getNome());
		statment.setString(2, cidade.getCep());
		statment.setInt(3, cidade.getQtdHabitantes());
		statment.setBoolean(4, cidade.isCapital());
		statment.setString(5, cidade.getEstado());
		statment.setDouble(6, cidade.getRendaPerCapita());
		statment.setString(7, cidade.getDataFundacao());
		return true;
	}

	public Connection getConexao() {
		return conexao;
	}

	public void setConexao(Connection conexao) {
		this.conexao = conexao;
	}

}

