package automato;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Estado implements Serializable, Cloneable {
	private static final long serialVersionUID = 2000;

	List<Transicao> transicoes;
	boolean estadoFinal;
	String nome;
	public int id;
	static int next = 0;
	
	//usado apenas para determinização
	List<Estado> composicao;

	public Estado(boolean estadoFinal_, String nome_) {
		id = next++;
		transicoes = new ArrayList<Transicao>();
		estadoFinal = estadoFinal_;
		nome = nome_;
		composicao = new ArrayList<Estado>();
		composicao.add(this);
	}

	public Estado(boolean estadoFinal_, String nome_,
			List<Transicao> transicoes_) {
		id = next++;
		transicoes = transicoes_;
		estadoFinal = estadoFinal_;
		nome = nome_;
		composicao = new ArrayList<Estado>();
		composicao.add(this);
	}

	public List<Transicao> getTransicoes() {
		return transicoes;
	}

	public void setTransicoes(List<Transicao> transicoes) {
		this.transicoes = transicoes;
	}

	public boolean isEstadoFinal() {
		return estadoFinal;
	}

	public void setEstadoFinal(boolean estadoFinal) {
		this.estadoFinal = estadoFinal;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void adicionarTransicao(Transicao transicao) {
		if (transicao != null) {
			transicoes.add(transicao);
		}
	}

	public boolean ehDeterministico() {
		for (int i = 0; i < transicoes.size(); i++) {
			Transicao a = transicoes.get(i);
			for (int j = 0; j < transicoes.size(); j++) {
				if (i != j) {
					Transicao b = transicoes.get(j);
					if (a.getSimbolo().compareTo(b.getSimbolo()) == 0) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public Estado reconhecer(String simbolo) {
		for (Transicao t : transicoes) {
			if (t.getSimbolo().compareTo(simbolo) == 0) {
				return t.getDestino();
			}
		}
		return null;
	}

	public List<Transicao> transicaoPara(Estado estado) {
		List<Transicao> temp = new ArrayList<Transicao>();

		for (Transicao t : transicoes) {

			if (t.destino.equals(estado)) {
				temp.add(t);
			}
		}

		return temp;
	}

	public List<String> simbolosPossiveis() {
		List<String> simbolos = new ArrayList<String>();

		for (int i = 0; i < transicoes.size(); i++) {
			simbolos.add(transicoes.get(i).simbolo);
		}

		return simbolos;

	}

	public Estado unirTransicoes(String s) {
		
		List<Estado> comp = new ArrayList<Estado>();
		List<Transicao> aux = new ArrayList<Transicao>();
		String novoNome = "";
		boolean novoEhFinal = false;

		// verifica os nomes e o isFinal das transicoes e coloca nas variaveis
		// criadas
		for (Transicao t : transicoes) {
			if (t.getSimbolo().compareTo(s) == 0) {
				comp.addAll(t.getDestino().getComposicao());
				if (t.getDestino().isEstadoFinal()) {
					novoEhFinal = true;
				}

			} 
			else {
				aux.add(t);
			}
		}
		removerRedundancia(comp);
		ordenarComposicao(comp);
		for(Estado e : comp){
			novoNome += e.getNome();
		}

		Estado novoDestino = new Estado(novoEhFinal, novoNome);
		aux.add(new Transicao(s, novoDestino));
		novoDestino.setComposicao(comp);

		this.transicoes = aux;
		return novoDestino;
	}

	@Override
	public boolean equals(Object obj) {

		if (((Estado) obj).getNome().compareTo(this.nome) == 0) {
			return true;
		}

		return super.equals(obj);
	}

	public void removerTransicoes(Estado estado) {
		List<Transicao> paraRemover = new ArrayList<Transicao>();
		for (Transicao t : transicoes) {
			if (t.getDestino().equals(estado)) {
				paraRemover.add(t);
			}
		}
		transicoes.removeAll(paraRemover);
	}

	public void removerTransicoes(String simbolo) {
		List<Transicao> paraRemover = new ArrayList<Transicao>();
		for (Transicao t : transicoes) {
			if (t.getSimbolo().compareTo(simbolo) == 0) {
				paraRemover.add(t);
			}
		}
		transicoes.removeAll(paraRemover);
	}

	@Override
	public String toString() {
		return this.nome;
	}

	public boolean ehCompleto(List<String> alfabeto) {
		boolean retorno = false;

		for (String s : alfabeto) {
			retorno = false;

			for (Transicao t : transicoes) {
				if (t.getSimbolo().compareTo(s) == 0) {
					retorno = true;
				}
			}
			if (retorno == false) {
				return false;
			}
		}
		return retorno;
	}

	public void completar(List<String> alfabeto, Estado erro) {
		boolean existe = false;

		for (String s : alfabeto) {
			existe = false;

			for (Transicao t : transicoes) {
				if (t.getSimbolo().compareTo(s) == 0) {
					existe = true;
				}
			}
			if (existe == false) {
				adicionarTransicao(new Transicao(s, erro));
			}
		}
	}

	public void adicionarTransicoes(List<Transicao> transicoes) {
		if (!transicoes.isEmpty()) {
			for (Transicao t : transicoes) {
				if (!this.transicoes.contains(t)) {
					this.transicoes.add(t);
				}
			}
		}
	}

	List<Transicao> epislonTrasicoes() {

		List<Transicao> transicoes = new ArrayList<Transicao>();

		for (Transicao t : this.transicoes) {
			if (t.simbolo.compareTo("&") == 0) {
				transicoes.add(t);
			}
		}

		return transicoes;
	}

	@SuppressWarnings("unused")
	private void ordenarComposicao(List<Estado> _composicao) {
		boolean swap;
		
		do{
			 swap = false;
			for (int i = 1; i < _composicao.size() - 1; i++) {
				if(_composicao.get(i-1).id > _composicao.get(i).id){
					Collections.swap(_composicao, i-1, i);
					swap = true;
				}
				
			}
		}while(swap == true);
	}
	
	
	public Estado copy() throws CloneNotSupportedException {
		Estado copy = (Estado) this.clone();	
		copy.transicoes = new ArrayList<Transicao>();
		
		return copy;
	}
	
	public boolean ehCompostoPor(Estado estado){
		
		if(!composicao.isEmpty()){
			if(composicao.size() == 1){
				if(composicao.contains(estado)){
					return true;
				} else{
					return false;
				}
				
			} else{
				for ( Estado e : composicao ){
					if(e.ehCompostoPor(estado)){
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public void removerRedundancia(List<Estado> lista){
		for(int i = 0; i < lista.size(); i++){
			for(int j = i+1; j < lista.size(); j++){
				if(lista.get(i).ehCompostoPor(lista.get(j))){
					lista.remove(j);
					j--;
				}
			}
		}
	}

	public List<Estado> getComposicao() {
		return composicao;
	}

	public void setComposicao(List<Estado> composicao) {
		this.composicao = composicao;
	}
	

}
