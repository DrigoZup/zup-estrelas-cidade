package br.com.zup.pojo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import br.com.zup.cidadeDAO.CidadeDAO;
import br.com.zup.factory.ConnectionFactory;

public class Principal {
	
	public static void verificaCapital(Scanner teclado, boolean capital) {
		
		System.out.println("A cidade é uma capital? Digite S para sim e N para não");
		String confereCapital = teclado.next();
		
		switch (confereCapital) {
		case "S": {
			capital = true;
			break;
		}
		
		case "N": {
			capital = false;
			break;
		}
		default:
			System.out.println("Por favor, digite S para sim e N para não");
		}

		
	}

	public static void main(String[] args) throws SQLException {
		
		Scanner teclado = new Scanner(System.in);
		Connection conexao = new ConnectionFactory().getConnection();
		
		
		System.out.println("-----------------------------------");
		System.out.println("        Cadastro de Cidades        ");
		System.out.println("-----------------------------------\n"
				+ "Preencha os dados a seguir:\n");
		
		System.out.println("Digite o nome: ");
		String nome = teclado.next();
		System.out.println("Digite o Cep: ");
		String cep = teclado.next();
		System.out.println("Quantos habitantes tem na cidade? ");
		int qtdHabitantes = teclado.nextInt();
		boolean capital = false;
		verificaCapital(teclado, capital);
		System.out.println("Qual a sigla do estado da cidade?");
		String estado = teclado.next();
		System.out.println("Informe a renda percapita da cidade: ");
		double rendaPerCapita = teclado.nextDouble();
		System.out.println("Qual a data de fundação da cidade?");
		String dataFundacao = teclado.next();
		
		Cidade cidade = new Cidade(nome, cep, qtdHabitantes, capital, estado, rendaPerCapita, dataFundacao);
		CidadeDAO cidadeAdd = new CidadeDAO();
		
		cidadeAdd.insereCidade(cidade);
		
		
		
		
		conexao.close();
		teclado.close();
	}

}
