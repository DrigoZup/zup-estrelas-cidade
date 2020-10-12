package br.com.zup.pojo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import br.com.zup.cidadeDAO.CidadeDAO;
import br.com.zup.factory.ConnectionFactory;

public class Principal {

	public static void menu() {

		System.out.println("==== Fake IBGE ====\n" 
				+ "Escolha uma opção!\n\n" 
				+ "1 - Cadastrar uma nova cidade\n"
				+ "2 - Listar cidades\n" 
				+ "3 - Deletar cidades\n"
				+ "4 - Buscar cidade pelo Cep\n"
				+ "5 - Buscar cidade por texto\n"
				+ "6 - Buscar lista de cidades pela sigla do estado\n"
				+ "7 - Contar cidades em um estado\n"
				+ "8 - Exibir capitais ou não capitais\n" 
				+ "0 - Encerrar o programa");
	}

	public static void insereCidade(Scanner teclado) throws SQLException {

		System.out.println("-----------------------------------");
		System.out.println("        Cadastro de Cidades        ");
		System.out.println("-----------------------------------\n" + "Preencha os dados a seguir:\n");

		System.out.println("Digite o nome: ");
		String nome = teclado.next();
		System.out.println("Digite o Cep: ");
		String cep = teclado.next();
		System.out.println("Quantos habitantes tem na cidade? ");
		int qtdHabitantes = teclado.nextInt();
		System.out.println("A cidade é uma capital?");
		boolean capital = verificaCapital(teclado);

		System.out.println("Qual a sigla do estado da cidade?");
		String estado = teclado.next();
		System.out.println("Informe a renda percapita da cidade: ");
		double rendaPerCapita = teclado.nextDouble();
		System.out.println("Qual a data de fundação da cidade?");
		String dataFundacao = teclado.next();

		Cidade cidade = new Cidade();
		cidade.setNome(nome);
		cidade.setCep(cep);
		cidade.setQtdHabitantes(qtdHabitantes);
		cidade.setCapital(capital);
		cidade.setEstado(estado);
		cidade.setRendaPerCapita(rendaPerCapita);
		cidade.setDataFundacao(dataFundacao);
		CidadeDAO cidadeAdd = new CidadeDAO();
		cidadeAdd.insereCidade(cidade);

		System.out.println("A cidade " + nome + " foi inserida no banco de dados!\n");

	}

	public static boolean verificaCapital(Scanner teclado) {

		String confereCapital;
		boolean capital = false;
		
		do {
			System.out.println("Digite S para sim e N para não");
			confereCapital = teclado.next();

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
				System.out.println("Essa opção não é válida!!!");
			}
		} while ((!confereCapital.equals("S") && (!confereCapital.equals("N"))));

		return capital;
	}

	public static void listaCidades() throws SQLException {

		System.out.println("----------------------------------------\n" + "            LISTA DE CIDADES            \n"
				+ "----------------------------------------\n");

		CidadeDAO listagem = new CidadeDAO();
		List<Cidade> cidadesDB = listagem.listaCidade();

		for (Cidade cidade : cidadesDB) {
			System.out.println(cidade.mostraDados());
		}
	}

	public static void deletaCidade(Scanner teclado) throws SQLException {

		System.out.println("Digite o Cep da cidade que quer encontrar");
		String cep = teclado.next();

		CidadeDAO cidadeDeleta = new CidadeDAO();

		cidadeDeleta.removeCidade(cep);
		System.out.println("A cidade de cep " + cep + " foi removida do banco de dados!");
	}

	public static void buscaCidadeCep(Scanner teclado) throws SQLException {
		
		System.out.println("Digite o cep da cidade que deseja encontrar");
		String cep = teclado.next();
		
		CidadeDAO buscaCidade = new CidadeDAO();
		Cidade cidadeEncontrada = buscaCidade.buscaCidadeCep(cep);
		
		System.out.println("\n"+cidadeEncontrada.mostraDados());
	}
	
	public static void buscaCidadeNome(Scanner teclado) throws SQLException {
		
		System.out.println("Digite o trecho do nome da cidade que você quer procurar");
		String trechoNomeCidade = teclado.next();
		
		CidadeDAO cidadesBD = new CidadeDAO();
		List<Cidade> listagem = cidadesBD.buscaNomeCidade(trechoNomeCidade);
		
		for (Cidade imprimeCidades : listagem) {
			System.out.println(imprimeCidades.mostraDados());
		}
	}
	
	public static void buscaCidadeBySigla(Scanner teclado) throws SQLException {
		System.out.println("Digite a sigla do estado para que eu liste as cidades dele:");
		String sigla = teclado.next();
		
		CidadeDAO listagem = new CidadeDAO();
		List<Cidade> cidadesDB = listagem.buscaCidadeBySigla(sigla);
		
		for (Cidade cidade : cidadesDB) {
			System.out.println(cidade.mostraDados());
		}
	}
	
	public static void contaCidadeBySigla(Scanner teclado) throws SQLException {
		System.out.println("Digite a sigla do estado desejado!");
		String sigla = teclado.next();
		
		CidadeDAO contagem = new CidadeDAO();
		
		contagem.contaCidadeBySigla(sigla);
	}
	
	public static void exibirCapitais(Scanner teclado) throws SQLException {
		
		System.out.println("Você deseja listar as capitais?");
		boolean escolheCapital = verificaCapital(teclado);
		CidadeDAO capitais = new CidadeDAO();
		List<Cidade> cidades = capitais.selecionaCapitais(escolheCapital);
		
		for (Cidade cidade : cidades) {
			System.out.println(cidade.mostraDados());
		}
	}
	
	public static void main(String[] args) throws SQLException {

		Scanner teclado = new Scanner(System.in);
		Connection conexao = new ConnectionFactory().getConnection();
		String escolhaIBGE = null;

		do {
			menu();
			escolhaIBGE = teclado.next();

			switch (escolhaIBGE) {
			case "1": {

				insereCidade(teclado);
				break;
			}

			case "2": {

				listaCidades();
				break;
			}

			case "3": {

				deletaCidade(teclado);
				break;
			}
			
			case "4": {
				
				buscaCidadeCep(teclado);
				break;
			}
			
			case "5": {
				
				buscaCidadeNome(teclado);
				break;
			}
			
			case "6": {
				
				buscaCidadeBySigla(teclado);
				break;
			}

			case "7": {
				
				contaCidadeBySigla(teclado);
				break;
			}
			
			case "8": {
				
				exibirCapitais(teclado);
				break;
			}
			
			case "0": {
				System.out.println("Programa encerrado");
				break;
			}

			default:
				System.out.println("Opção Inválida");
			}
		} while (!escolhaIBGE.equals("0"));

		conexao.close();
		teclado.close();
	}
}
