package teste;

import automato.Automato;
import automato.Estado;
import automato.Transicao;

public class AutomatoTest7 extends Automato {
	private static final long serialVersionUID = 1L;

	public AutomatoTest7() {
		super();
		Estado q0 = new Estado(false, "S");
		estados.add(q0);
		Estado q1 = new Estado(false, "A");
		estados.add(q1);
		Estado q2 = new Estado(false, "B");
		estados.add(q2);
		Estado q3 = new Estado(false, "C");
		estados.add(q3);
		Estado q4 = new Estado(false, "D");
		estados.add(q4);
		Estado q5 = new Estado(true, "E");
		estados.add(q5);
		
		q0.adicionarTransicao(new Transicao("0", q1));
		q0.adicionarTransicao(new Transicao("0", q4));
		q0.adicionarTransicao(new Transicao("1", q5));
		
		q1.adicionarTransicao(new Transicao("0", q1));
		q1.adicionarTransicao(new Transicao("0", q2));
		q1.adicionarTransicao(new Transicao("1", q3));
		q1.adicionarTransicao(new Transicao("1", q5));
		
		q2.adicionarTransicao(new Transicao("0", q2));
		
		q3.adicionarTransicao(new Transicao("0", q1));
		q3.adicionarTransicao(new Transicao("0", q2));
		
		q4.adicionarTransicao(new Transicao("0", q2));
		q4.adicionarTransicao(new Transicao("0", q4));
		q4.adicionarTransicao(new Transicao("1", q3));
		
		q5.adicionarTransicao(new Transicao("0", q5));
		q5.adicionarTransicao(new Transicao("1", q5));
		
		inicial = q0;
		
		alfabeto.add("0");
		alfabeto.add("1");
	}
}
