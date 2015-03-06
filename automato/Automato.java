package automato;

import gui.OperationFrames;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Automato implements Serializable, Cloneable {
	private static final long serialVersionUID = 1000;

	protected List<Estado> estados;
	protected List<String> alfabeto;
	// IMPORTANTE estado inicial é SEMPRE o primeiro da lista
	protected Estado inicial;
	protected boolean temEpislonTransicao = false;
	public String name = "";

	public Automato(List<Estado> estados, List<String> alfabeto) {
		this.estados = estados;
		this.alfabeto = alfabeto;
		if (!estados.isEmpty()) {
			this.inicial = estados.get(0);

		}

		existeEpislonTransicao();
	}

	public Automato() {
		estados = new ArrayList<Estado>();
		this.alfabeto = new ArrayList<String>();
		inicial = null;
		existeEpislonTransicao();
	}

	public void existeEpislonTransicao() {

		for (Estado e : estados) {
			for (Transicao t : e.transicoes) {
				if (t.simbolo.compareTo("&") == 0) {
					temEpislonTransicao = true;
				}
			}
		}
	}
	
	public boolean existeEtransicao(){
		for (Estado e : estados) {
			for (Transicao t : e.transicoes) {
				if (t.simbolo.compareTo("&") == 0) {
					return true;
				}
			}
		}
		return false;
	}

	public void adicionarEstado(Estado estado) {
		if (!estados.contains(estado)) {
			this.estados.add(estado);
		}
		if (inicial == null) {
			this.inicial = estado;
		}
	}

	public void removerEstado(Estado estado) {
		if (estados.contains(estado)) {
			estados.remove(estado);
			for (Estado e : estados) {
				e.removerTransicoes(estado);
			}
			estados.remove(estado);
		}
	}

	public boolean ehDeterministico() {
		for (Estado e : estados) {
			if (!e.ehDeterministico()) {
				return false;
			}
		}
		return true;
	}

	public Estado estadoPorNome(String nome) {
		for (Estado e : estados) {
			if (e.getNome().compareTo(nome) == 0)
				return e;
		}
		return null;
	}

	public void determinizar() {
		if (inicial == null) {
			System.out.println("Criar automato antes de determinizar!");
			return;
		}
		removerEtransicao();

		List<Estado> deterministicos = new ArrayList<Estado>();
		deterministicos.add(inicial);
		Estado estadoAlvo;
		int i = 0;

		while (i < deterministicos.size()) {
			estadoAlvo = deterministicos.get(i);
			for (String s : alfabeto) {
				if (estadoAlvo.reconhecer(s) != null) {
					Estado novo = estadoAlvo.unirTransicoes(s);
					if (!deterministicos.contains(novo)) {
						for (Estado e : novo.getComposicao()) {
							for (Transicao t : e.getTransicoes()) {
								if (!novo.getTransicoes().contains(t)) {
									novo.adicionarTransicao(t);
								}
							}
						}
						novo.getComposicao().clear();
						novo.getComposicao().add(novo);
						deterministicos.add(novo);
					} else {
						Estado jaCriado = null;
						for (Estado e : deterministicos) {
							if (e.getNome().compareTo(novo.getNome()) == 0) {
								jaCriado = e;
								break;
							}
						}
						for (Transicao t : estadoAlvo.getTransicoes()) {
							if (t.getDestino().getNome()
									.compareTo(jaCriado.getNome()) == 0) {
								t.setDestino(jaCriado);
							}
						}

					}
				}
			}
			i++;
		}

		setEstados(deterministicos);

	}

	public List<Estado> alcansaveis() {
		List<Estado> alcansaveis = new ArrayList<Estado>();
		Estado temp;

		// Inicial sempre alcansavel
		alcansaveis.add(inicial);

		int i = 0;
		while (alcansaveis.size() > i) {
			temp = alcansaveis.get(i);

			if (temp.transicoes != null) {
				for (int x = 0; x < temp.transicoes.size(); x++) {
					Estado proximo = temp.transicoes.get(x).getDestino();
					if (proximo != null && !alcansaveis.contains(proximo)) {
						alcansaveis.add(proximo);
					}
				}

				i++;
			}
		}

		return alcansaveis;
	}

	public void removerInalcansaveis() {
		List<Estado> inalcansaveis = new ArrayList<Estado>();
		inalcansaveis.addAll(estados);
		inalcansaveis.removeAll(alcansaveis());

		for (Estado e : inalcansaveis) {
			removerEstado(e);
		}
	}

	public List<Estado> vivos() {
		List<Estado> vivos = new ArrayList<Estado>();

		if (possuiInalcasaveis()) {
			removerInalcansaveis();
		}

		vivos.addAll(estadosFinais());

		int i = 0;
		Estado vivo;

		while (vivos.size() > i) {
			vivo = vivos.get(i);

			for (int x = 0; x < estados.size(); x++) {
				Estado temp = estados.get(x);

				if (!temp.transicaoPara(vivo).isEmpty()) {
					if (!vivos.contains(temp)) {
						vivos.add(temp);
					}
				}
			}

			i++;
		}

		return vivos;

	}

	public boolean possuiInalcasaveis() {
		boolean temp = false;
		if (estados.size() > alcansaveis().size()) {
			temp = true;
		} else {
			temp = false;
		}
		return temp;
	}

	public void removerMortos() {
		List<Estado> mortos = new ArrayList<Estado>();
		mortos.addAll(estados);
		mortos.removeAll(vivos());
		for (Estado e : mortos) {
			removerEstado(e);
		}
	}

	public List<Estado> estadosFinais() {
		List<Estado> finais = new ArrayList<Estado>();
		for (Estado e : estados) {
			if (e.isEstadoFinal()) {
				finais.add(e);
			}
		}
		return finais;
	}

	public boolean estaContidoEm(Automato outro) {

		Automato clone1 = this.copy();
		Automato clone2 = outro.copy();
		clone1.minimizar();
		clone2.minimizar();
		OperationFrames.print(clone1.name);
		OperationFrames.print(clone1.toString());
		clone1.complemento();
		OperationFrames.print("¬" + clone1.name);
		OperationFrames.print(clone1.toString());
		OperationFrames.print(clone2.name);
		OperationFrames.print(clone2.toString());
		Automato temp = clone1.uniao(clone2);
		OperationFrames.print("¬"+clone1.name + " U " + clone2.name);
		OperationFrames.print(temp.toString());
		temp.complemento();
		OperationFrames.print("¬(¬"+clone1.name + " U " + clone2.name+")");
		OperationFrames.print(temp.toString());
		return temp.linguagemEhVazia();
	}

	public boolean ehEquivalente(Automato outro) {
		boolean retorno = false;

		this.name = "A";
		outro.name = "B";
		
		OperationFrames.print("Verificanto equivalência entre AFs");
		OperationFrames.print("A < B ?");
		if (this.estaContidoEm(outro)) {
			OperationFrames.print("A < B : true");
			OperationFrames.print("B < A ?");
			if (outro.estaContidoEm(this)) {
				retorno = true;
				OperationFrames.print("B < A : true");
			}
		}
		OperationFrames.print("A == B : " + retorno);
		return retorno;
	}

	public Automato uniao(Automato outro) {
		Automato uniao = new Automato();
		int aux = 1;

		for (Estado e : this.estados) {
			e.setNome("S" + aux++);
		}

		for (Estado e : outro.getEstados()) {
			e.setNome("S" + aux++);
		}
		
		Estado inicial1 = this.inicial;
		Estado inicial2 = outro.getInicial();

		uniao.estados.addAll(outro.getEstados());
		uniao.estados.addAll(this.getEstados());

		List<String> alfa = new ArrayList<String>();
		alfa.addAll(this.alfabeto);
		for (String l : outro.alfabeto) {
			if (!alfa.contains(l)) {
				alfa.add(l);
			}
		}

		uniao.setAlfabeto(alfa);

		boolean testeFinal = false;
		if (inicial1.isEstadoFinal() || inicial2.isEstadoFinal()) {
			testeFinal = true;
		}

		Estado novoInicial = new Estado(testeFinal, "S0");
		for (Transicao t : inicial1.getTransicoes()) {
			novoInicial.adicionarTransicao(new Transicao(t.getSimbolo(), t
					.getDestino()));
		}
		for (Transicao t : inicial2.getTransicoes()) {
			novoInicial.adicionarTransicao(new Transicao(t.getSimbolo(), t
					.getDestino()));
		}

		uniao.estados.add(0, novoInicial);
		uniao.setInicial(novoInicial);

		return uniao;
	}

	public void complemento() {
		determinizar();
		completar();

		for (Estado e : estados) {
			e.setEstadoFinal(!e.isEstadoFinal());
		}
	}

	public boolean linguagemEhVazia() {
		boolean retorno = false;

		List<String> aux = gerarSentencas(estados.size() - 1);
		if (aux.isEmpty()) {
			retorno = true;
		} else {
			System.out.println(aux);
		}

		return retorno;
	}

	public boolean reconhecer(String sentenca) {
		boolean retorno = false;
		if (sentenca.contains("$")) {
			retorno = false;
		}
		
		determinizar();
		
		sentenca = sentenca + "$";
		Estado atual = inicial;

		for (int i = 0; i < sentenca.length(); i++) {
			if (sentenca.charAt(i) == '$') {
				if (atual.isEstadoFinal()) {
					retorno = true;
				} else {
					retorno = false;
				}
			} else {
				atual = atual
						.reconhecer(Character.toString(sentenca.charAt(i)));
				if (atual == null) {
					retorno = false;
					break;
				}
			}
		}
		return retorno;
	}

	private List<String> gerarFormasSentenciais(int n, Estado estado, String si) {

		List<String> sentencas = new ArrayList<String>();

		if (si.length() >= n) {
			return sentencas;
		}
		String sentenca = si;

		Estado proximo = null;
		String parte = null;

		for (int j = 0; j < estado.transicoes.size(); j++) {

			parte = sentenca + estado.simbolosPossiveis().get(j);
			sentencas.add(parte);

			proximo = estado.transicoes.get(j).destino;

			List<String> textos = gerarFormasSentenciais(n, proximo, parte);
			sentencas.addAll(textos);

		}

		return sentencas;
	}

	public List<String> gerarSentencas(int n) {
		if (!ehDeterministico()) {
			determinizar();
		}
		List<String> sentencas = new ArrayList<String>();
		if (this.inicial.isEstadoFinal()) {
			sentencas.add("&");
		}

		List<String> temp = gerarFormasSentenciais(n, inicial, "");

		for (int i = 0; i < temp.size(); i++) {
			if (reconhecer(temp.get(i))) {
				sentencas.add(temp.get(i));
			}
		}

		return sentencas;

	}

	public void minimizar() {
		removerEtransicao();
		determinizar();
		removerMortos();
		completar();

		List<List<Estado>> classesEquivalencia = classesDeEquivalencia();
		
		if (classesEquivalencia.size() < estados.size()) {
			List<Estado> novosEstados = new ArrayList<Estado>();

			for (int i = 0; i < classesEquivalencia.size(); i++) {
				if (classesEquivalencia.get(i).size() > 0) {
					novosEstados.add(new Estado(classesEquivalencia.get(i)
							.get(0).isEstadoFinal(), "q" + i));
				}
			}
			Estado estadoAlvo;
			Estado estadoDestino;
			for (int i = 0; i < classesEquivalencia.size(); i++) {
				estadoAlvo = classesEquivalencia.get(i).get(0);

				for (String s : alfabeto) {
					estadoDestino = estadoAlvo.reconhecer(s);
					for (int j = 0; j < classesEquivalencia.size(); j++) {
						if (classesEquivalencia.get(j).contains(estadoDestino)) {
							novosEstados.get(i).adicionarTransicao(
									new Transicao(s, novosEstados.get(j)));
						}
					}
				}
			}
			setEstados(novosEstados);
			for (int i = 0; i < classesEquivalencia.size(); i++) {
				if (classesEquivalencia.get(i).contains(inicial)) {
					setInicial(novosEstados.get(i));
					break;
				}
			}
		}
	}

	public boolean ehCompleto() {
		boolean retorno = true;
		for (Estado e : estados) {
			if (!e.ehCompleto(alfabeto)) {
				retorno = false;
			}
		}
		return retorno;
	}

	public void completar() {
		if (!ehCompleto()) {
			Estado erro = new Estado(false, "ERRO");
			adicionarEstado(erro);
			for (Estado e : estados) {
				e.completar(alfabeto, erro);
			}
		}
	}

	public List<List<Estado>> classesDeEquivalencia() {

		List<List<Estado>> classes = new Vector<List<Estado>>();

		List<Estado> terminais = new Vector<Estado>();
		List<Estado> nTerminais = new Vector<Estado>();

		terminais.addAll(estadosFinais());

		nTerminais.addAll(estados);
		nTerminais.removeAll(estadosFinais());
		classes.add(terminais);
		classes.add(nTerminais);
		boolean algoMudou;
		do {
			algoMudou = false;
			for (int z = 0; z < classes.size(); z++) {

				if (classes.get(z).isEmpty()) {
					classes.remove(z);
					break;
				}
				List<Estado> novaClasse = null;
				for (int i = 0; i < classes.get(z).size(); i++) {

					for (int j = i + 1; j < classes.get(z).size(); j++) {
						List<Estado> temp = null;
						List<Estado> temp2 = null;
						boolean equivalente = true;
						for (String s : alfabeto) {

							Estado e1 = classes.get(z).get(i).reconhecer(s);
							Estado e2 = classes.get(z).get(j).reconhecer(s);

							for (List<Estado> list : classes) {

								if (list.contains(e1)) {
									temp = list;
								}
								if (list.contains(e2)) {
									temp2 = list;
								}
							}
							int x = i;
							if (!temp.equals(temp2)) {
								equivalente = false;
								Estado t = classes.get(z).get(0).reconhecer(s);
								if (temp2.contains(t)) {
									x = i;
								} else if (temp.contains(t)) {
									x = j;
								} else {
									System.out
											.println("N�o garanto o funcionamento possivel ERRO");
								}

							} else {
								equivalente = true;
							}

							if (!equivalente) {
								algoMudou = true;
								if (novaClasse == null) {
									novaClasse = new Vector<Estado>();
									novaClasse.add(classes.get(z).get(x));
								} else {
									if (!novaClasse.contains(classes.get(z)
											.get(x))) {
										novaClasse.add(classes.get(z).get(x));
									}
								}
							}

						}

					}
					if (novaClasse != null) {
						classes.get(z).removeAll(novaClasse);
						if (!classes.contains(novaClasse)
								&& !novaClasse.isEmpty()) {
							classes.add(novaClasse);
						}
					}
				}

			}
		} while (algoMudou);

		return classes;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public Estado getInicial() {
		return inicial;
	}

	public List<String> getAlfabeto() {
		return alfabeto;
	}

	public void setAlfabeto(List<String> alfabeto) {
		this.alfabeto = alfabeto;
	}

	public void setInicial(Estado inicial) {
		System.out.println(estados);
		this.inicial = inicial;
		this.estados.remove(inicial);
		this.estados.add(0, inicial);
		System.out.println(estados);
	}

	public void print() {
		for (Estado e : estados) {
			System.out.println(e + " : " + e.getComposicao());
		}

		System.out.println("Inicial -> " + this.inicial);

		for (Estado e : estados) {
			System.out.println("=+=+=+=+=+=");
			if (e.isEstadoFinal()) {
				System.out.println("> Estado: " + "*" + e);
			} else {
				System.out.println("> Estado: " + e);
			}
			for (Transicao t : e.getTransicoes()) {
				System.out.println("> " + t);
			}
		}
		System.out.println("#############################");
	}

	@Override
	public String toString() {
		String retorno = "";
		retorno += "Inicial -> " + this.inicial + "\n";
		for (Estado e : estados) {
			retorno += "=+=+=+=+=+=\n";
			if (e.isEstadoFinal()) {
				retorno += "> Estado: " + "*" + e + "\n";
			} else {
				retorno += "> Estado: " + e + "\n";
			}
			for (Transicao t : e.getTransicoes()) {
				retorno += "> " + t + "\n";
			}
		}
		retorno += "#############################\n";

		return retorno;
	}

	public void removerEpislonTransicao() {
		List<Transicao> temp;
		for (Estado e : estados) {
			temp = e.epislonTrasicoes();
			if (temp.size() > 0) {
				for (Transicao t : temp) {
					Estado proximo = t.destino;
					e.adicionarTransicoes(proximo.transicoes);

					if (proximo.isEstadoFinal()) {
						e.setEstadoFinal(true);
					}
					if (proximo.epislonTrasicoes().size() > 0) {
						temEpislonTransicao = true;
					}
				}
				e.transicoes.removeAll(temp);
			}
		}

		if (temEpislonTransicao) {
			temEpislonTransicao = false;
			removerEpislonTransicao();
		} 
		else {
			for (int i = 0; i < alfabeto.size(); i++) {
				if (alfabeto.get(i).compareTo("&") == 0) {
					alfabeto.remove(i);
				}
			}
		}
	}
	
	public void removerEtransicao(){
		List<Transicao> temp;
		
		while(existeEtransicao()){
			for (Estado e : estados) {
				temp = e.epislonTrasicoes();
				if (temp.size() > 0) {
					for (Transicao t : temp) {
						Estado proximo = t.destino;
						e.adicionarTransicoes(proximo.transicoes);

						if (proximo.isEstadoFinal()) {
							e.setEstadoFinal(true);
						}
					}
					e.transicoes.removeAll(temp);
					print();
				}
			}
		}
		alfabeto.remove("&");
		System.out.println(alfabeto);
	}

	public Automato clonar() throws CloneNotSupportedException {
		Automato clone = new Automato();
		clone.temEpislonTransicao = this.temEpislonTransicao;

		clone.alfabeto.addAll(alfabeto);

		clone.setInicial(inicial);
		for (int i = 0; i < estados.size(); i++) {
			Estado temp = estados.get(i);
			clone.estados.add(temp.copy());
		}

		for (int i = 0; i < clone.estados.size(); i++) {
			Estado temp = clone.estados.get(i);
			List<Transicao> transTemp = estados.get(i).transicoes;
			for (int j = 0; j < transTemp.size(); j++) {
				temp.adicionarTransicao(transTemp.get(j).copy(clone.estados));
			}

		}

		return clone;
	}

	public Automato copy() {

		List<Estado> estadosCopiados = new ArrayList<Estado>();

		for (Estado e : this.estados) {
			estadosCopiados.add(new Estado(e.isEstadoFinal(), e.getNome()));
		}

		for (Estado e : this.estados) {
			Estado alvo = estadosCopiados.get(estadosCopiados.indexOf(e));
			for (Transicao t : e.getTransicoes()) {
				alvo.adicionarTransicao(new Transicao(t.getSimbolo(),
						estadosCopiados.get(estadosCopiados.indexOf(t
								.getDestino()))));
			}
		}

		List<String> novoAlfabeto = new ArrayList<>();
		novoAlfabeto.addAll(alfabeto);
		
		Automato copy = new Automato(estadosCopiados, novoAlfabeto);
		copy.name = this.name;
		return copy;
	}
}
