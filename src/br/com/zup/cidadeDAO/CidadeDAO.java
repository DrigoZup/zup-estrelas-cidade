package br.com.zup.cidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.zup.factory.ConnectionFactory;
import br.com.zup.pojo.Cidade;

public class CidadeDAO {

	private Connection conexao;

	public CidadeDAO() {
		this.conexao = new ConnectionFactory().getConnection();
	}

	public void consultaBanco(ResultSet rs, List<Cidade> listaCidades) throws SQLException{
		while (rs.next()) {
			Cidade cidade = new Cidade();

			cidade.setNome(rs.getString("nome"));
			cidade.setCep(rs.getString("cep"));
			cidade.setQtdHabitantes(rs.getInt("numero_habitantes"));
			cidade.setCapital(rs.getBoolean("capital"));
			cidade.setEstado(rs.getString("estado"));
			cidade.setRendaPerCapita(rs.getDouble("renda_percapita"));
			cidade.setDataFundacao(rs.getString("data_fundacao"));
			listaCidades.add(cidade);
		}

	}
	
	public boolean insereCidade(Cidade cidade) throws SQLException {

		String insercaoSQL = "insert into cidade"
				+ "(nome, cep, numero_habitantes, capital, estado, renda_percapita, data_fundacao)"
				+ "values (?, ?, ?, ?, ?, ?, ?)";
		try {

			PreparedStatement comando = this.conexao.prepareStatement(insercaoSQL);

			comando.setString(1, cidade.getNome());
			comando.setString(2, cidade.getCep());
			comando.setInt(3, cidade.getQtdHabitantes());
			comando.setBoolean(4, cidade.isCapital());
			comando.setString(5, cidade.getEstado());
			comando.setDouble(6, cidade.getRendaPerCapita());
			comando.setString(7, cidade.getDataFundacao());

			comando.execute();
			comando.close();
		} catch (SQLException exception) {
			System.err.println("Problemas ao cadastrar a cidade...");
			System.err.println(exception.getMessage());
		}
		return true;
	}

	public List<Cidade> listaCidade() throws SQLException {

		List<Cidade> cidades = new ArrayList<>();

		String listagemSQL = "select * from cidade";

		try {
			PreparedStatement comando = this.conexao.prepareStatement(listagemSQL);

			ResultSet resultadoConsulta = comando.executeQuery();

			consultaBanco(resultadoConsulta, cidades);
			
			comando.close();
		} catch (SQLException exception) {
			System.err.println("Erro na listagem...");
			System.err.println(exception.getMessage());

		}

		return cidades;
	}

	public boolean removeCidade(String cep) throws SQLException {

		String deletaSQL = "delete from cidade where cep = ?";

		try {
			PreparedStatement comando = this.conexao.prepareStatement(deletaSQL);
			comando.setString(1, cep);

			comando.execute();
			comando.close();
		} catch (SQLException exception) {
			System.err.println("Cidade não encontrada");
			System.err.println(exception.getMessage());
			return false;
		}
		return true;
	}

	public Cidade buscaCidadeCep(String cep) throws SQLException {
		
		Cidade cidadeEncontrada = new Cidade();
		String buscaSQL = "select * from cidade where cep = ?";
		
		try {
			
			PreparedStatement comando = this.conexao.prepareStatement(buscaSQL);
			comando.setString(1, cep);
			
			ResultSet resultadoConsulta = comando.executeQuery();
			
			while (resultadoConsulta.next()) {
				cidadeEncontrada.setNome(resultadoConsulta.getString("nome"));
				cidadeEncontrada.setCep(resultadoConsulta.getString("cep"));
				cidadeEncontrada.setQtdHabitantes(resultadoConsulta.getInt("numero_habitantes"));
				cidadeEncontrada.setCapital(resultadoConsulta.getBoolean("capital"));
				cidadeEncontrada.setEstado(resultadoConsulta.getString("estado"));
				cidadeEncontrada.setRendaPerCapita(resultadoConsulta.getDouble("renda_percapita"));
				cidadeEncontrada.setDataFundacao(resultadoConsulta.getString("data_fundacao"));
			}
			comando.close();
			
		} catch (SQLException exception) {
			System.err.println("Cidade não encontrada");
			System.err.println(exception.getMessage());
		}
		return cidadeEncontrada;
	}

	public List<Cidade> buscaNomeCidade(String textoDigitado) throws SQLException {
		
		List<Cidade> cidadesEncontradas = new ArrayList<Cidade>();
		String buscaSQL = "select *\r\n" + 
				"from cidade\r\n" + 
				"where nome like ?";
		
		try {
			PreparedStatement comando = this.conexao.prepareStatement(buscaSQL);
			comando.setString(1, "%"+textoDigitado+"%");
			ResultSet resultadoConsulta = comando.executeQuery();
			
			consultaBanco(resultadoConsulta, cidadesEncontradas);
			comando.close();
		} catch (SQLException exception) {
			System.err.println("Nenhuma cidade encontrada...");
			System.err.println(exception.getMessage());
		}
		return cidadesEncontradas;
	}

	public List<Cidade> buscaCidadeBySigla(String sigla) throws SQLException {
		
		List<Cidade> listaCidades = new ArrayList<Cidade>();
		
		String buscaSQL = "select * from cidade, estado where cidade.estado = estado.sigla and estado.sigla = ?";
		
		try {
		PreparedStatement comando = this.conexao.prepareStatement(buscaSQL);
		comando.setString(1, sigla);
		
		ResultSet resultadoConsulta = comando.executeQuery();
		
		consultaBanco(resultadoConsulta, listaCidades);
		comando.close();
		} catch(SQLException exception) {
			System.err.println("Nenhuma cidade encontrada...");
			System.err.println(exception.getMessage());
		}
		return listaCidades;
	}

	public int contaCidadeBySigla(String sigla) throws SQLException {
		
		
		String contagemSQL = "select count(*) from cidade, estado where cidade.estado = estado.sigla and estado.sigla = ?";
		int cidadesContabilizadas = 0;
		
		try {
			PreparedStatement comando = this.conexao.prepareStatement(contagemSQL);
			comando.setString(1, sigla);
			
			ResultSet resultadoConsulta = comando.executeQuery();
			while (resultadoConsulta.next()) {
			
				cidadesContabilizadas = resultadoConsulta.getInt(1);
			}
		comando.close();	
		} catch (SQLException exception) {
			System.err.println("Não foi possível realizar a contagem");
			System.err.println(exception.getMessage());
		}
		
		System.out.println("Existem "+cidadesContabilizadas+" pertencentes ao estado "+sigla+" cadastradas neste banco de dados!");
		
		return cidadesContabilizadas;
	}
	
	public List<Cidade> selecionaCapitais(boolean capital) throws SQLException {
		List<Cidade> capitais = new ArrayList<Cidade>();
		
		String selecaoSQL = "select * from cidade where cidade.capital = ?";
		
		try {
			
			PreparedStatement comando = this.conexao.prepareStatement(selecaoSQL);
			comando.setBoolean(1, capital);
			
			ResultSet resultadoConsulta = comando.executeQuery();
			consultaBanco(resultadoConsulta, capitais);
			comando.close();
			
		} catch (SQLException exception) {
			System.err.println("Não foi possível obter essa seleção.");
			System.err.println(exception.getMessage());
		}
		return capitais;
	}
	
	
}
