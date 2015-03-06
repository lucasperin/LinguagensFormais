package teste;

import automato.Automato;
import automato.Estado;
import automato.Transicao;

public class AutomatoTest6 extends Automato{
	private static final long serialVersionUID = 1L;

	//Gerador de #a's + #b's = par
	public AutomatoTest6() {
		super();
		Estado q0 = new Estado(false, "q0");
		estados.add(q0);
		Estado q1 = new Estado(false, "q1");
		estados.add(q1);
		Estado q2 = new Estado(false, "q2");
		estados.add(q2);
		Estado q3 = new Estado(false, "q3");
		estados.add(q3);
		Estado q4 = new Estado(false, "q4");
		estados.add(q4);
		Estado q5 = new Estado(true, "q5");
		estados.add(q5);

		q0.adicionarTransicao(new Transicao("&", q2));
		q0.adicionarTransicao(new Transicao("&", q3));
		
		q2.adicionarTransicao(new Transicao("b", q1));
		q2.adicionarTransicao(new Transicao("&", q5));
		
		q1.adicionarTransicao(new Transicao("b", q2));
		
		q3.adicionarTransicao(new Transicao("a", q4));
		q3.adicionarTransicao(new Transicao("&", q5));
		
		q4.adicionarTransicao(new Transicao("a", q3));
		
		q5.adicionarTransicao(new Transicao("a", q4));
		q5.adicionarTransicao(new Transicao("b", q1));
		q5.adicionarTransicao(new Transicao("a", q3));
		q5.adicionarTransicao(new Transicao("b", q2));
		
		inicial = q0;
		alfabeto.add("a");
		alfabeto.add("b");
		alfabeto.add("&");
	}
}
