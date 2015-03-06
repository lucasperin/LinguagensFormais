package expressaoRegular;

import java.util.ArrayList;
import java.util.List;


import automato.*;

public class ExpressionAutomatonBuilder extends Automato{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int nextStateNumber;
	
	public ExpressionAutomatonBuilder() {
		super();
	}
	
	public ExpressionAutomatonBuilder(String expression) {
		super();
		
		SyntaxTree genTree = new SyntaxTree(expression);
		this.nextStateNumber = 0;
		this.alfabeto = BuildAlphabet(expression);
		this.alfabeto.add("&");
		
		Automato aux  = buildAutomaton(genTree.getRoot());
		this.estados = aux.getEstados();
		this.inicial = aux.getEstados().get(0);
//		this.removerEpislonTransicao();
		this.removerEtransicao();
	}



	private Automato buildAutomaton(BinaryNode<Character> node) {
		if(node.isLeaf()){
			return charToAut(node);
		}
		else{
			
			if(node.getValue() == RegularExpression.CONCATENATION){
				return concToAut(buildAutomaton(node.getLeftChild()), buildAutomaton(node.getRightChild()));
			}else if (node.getValue() == RegularExpression.ATLEAST_ONE) {
				return atLeastOneToAut(buildAutomaton(node.getLeftChild()));
			}else if (node.getValue() == RegularExpression.OR) {
				return orToAut(buildAutomaton(node.getLeftChild()), buildAutomaton(node.getRightChild()));
			}else if (node.getValue() == RegularExpression.KLEENE_STAR) {
				return starToAut(buildAutomaton(node.getLeftChild()));
			}else if (node.getValue() == RegularExpression.OPTIONAL) {
				return optToAut(buildAutomaton(node.getLeftChild()));
			}
		}
		return null;
	}


	private Automato optToAut(Automato left) {
		Estado init = new Estado(false, "S" + nextStateNumber());
		Estado end = new Estado(true, "S" + nextStateNumber());
		init.adicionarTransicao(new Transicao("&", left.getInicial()));
		init.adicionarTransicao(new Transicao("&", end));
		
		left.getEstados().add(0, init);
		left.setInicial(init);
		
		for (Estado s: left.estadosFinais()){
			s.adicionarTransicao(new Transicao("&", end));
			s.setEstadoFinal(false);
		}
		left.getEstados().add(end);
		return left;
	}

	private Automato starToAut(Automato left) {
		Estado init = new Estado(false, "S" + nextStateNumber());
		
		for (Estado s: left.estadosFinais()){
			s.adicionarTransicao(new Transicao("&", init));
		}
		
		Estado end = new Estado(true,"S" + nextStateNumber());
		init.adicionarTransicao(new Transicao("&", left.getInicial()));
		init.adicionarTransicao(new Transicao("&", end));
		
		left.getEstados().add(0, init);
		left.setInicial(init);
		left.adicionarEstado(end);
		
		return left;
	}

	private Automato orToAut(Automato left, Automato right) {
		Estado init = new Estado(false, "S" + nextStateNumber());
		Estado end = new Estado(true, "S" + nextStateNumber());
		init.adicionarTransicao(new Transicao("&", left.getInicial()));
		init.adicionarTransicao(new Transicao("&", right.getInicial()));
		left.getEstados().addAll(right.getEstados());
		left.getEstados().add(0, init);
		left.setInicial(init);
		
		left.adicionarEstado(end);
		for (Estado e : left.getEstados()){
			if(e.isEstadoFinal()){
				e.adicionarTransicao(new Transicao("&", end));
			}
		}
		return left;
	}

	private Automato atLeastOneToAut(Automato left) {
		
		Automato clone = null;
		try {
			clone = left.clonar();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			concToAut(clone, atLeastOneToAut(left));
		
		return left;
	}

	private Automato concToAut(Automato left, Automato right) {
		List<Estado> leftFinals = left.estadosFinais();
		left.getEstados().addAll(right.getEstados());
		for (Estado s: leftFinals){
			s.adicionarTransicao(new Transicao("&", right.getInicial()));
			s.setEstadoFinal(false);
		}
		
		return left;
	}

	private Automato charToAut(BinaryNode<Character> node) {
		Estado init = new Estado(false, "S"+nextStateNumber());
		Estado end = new Estado(true, "S"+nextStateNumber());
		init.adicionarTransicao(new Transicao(node.getValue().toString(), end));
		
		List<Estado> states = new ArrayList<Estado>();
		states.add(init);
		states.add(end);
		
		Automato aux = new Automato(states, this.alfabeto);
		return aux;
	}

	private List<String> BuildAlphabet(String expression) {
		List<String> alphabet= new ArrayList<String>();
		
		for(char c : expression.toCharArray()){
			if(RegularExpression.terminals.contains(c)){
				if(alphabet.size() == 0){
					alphabet.add(String.valueOf(c));
				}else{
					boolean found = false;
					for (String s : alphabet){
						if(s.compareTo(String.valueOf(c)) == 0)
							found = true;
					}
					if(!found){
					alphabet.add(String.valueOf(c));
					}
				}
			}
		}
		
		return alphabet;
	}
	
	private int nextStateNumber(){
		this.nextStateNumber++;
		return this.nextStateNumber;
	}
	
}
