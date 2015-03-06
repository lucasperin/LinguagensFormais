package teste;

import automato.Automato;
import automato.Estado;
import automato.Transicao;

public class AutomatoTest extends Automato {
	private static final long serialVersionUID = 1L;

	public AutomatoTest() {
		super();
		Estado q0 = new Estado(true, "q0");
		estados.add(q0);
		Estado q1 = new Estado(false, "q1");
		estados.add(q1);
		Estado q2 = new Estado(false, "q2");
		estados.add(q2);
		Estado q3 = new Estado(false, "q3");
		estados.add(q3);
		Estado q4 = new Estado(true, "q4");
		estados.add(q4);
		Estado q5 = new Estado(false, "q5");
		estados.add(q5);
		Estado q6 = new Estado(true, "q6");
		estados.add(q6);
		Estado q7 = new Estado(false, "q7");
		estados.add(q7);
		Estado q8 = new Estado(false, "q8");
		estados.add(q8);
		Estado q9 = new Estado(false, "q9");
		estados.add(q9);
		
		q0.adicionarTransicao(new Transicao("a", q1));
		q0.adicionarTransicao(new Transicao("a", q3));
		
		q1.adicionarTransicao(new Transicao("b", q2));
		q1.adicionarTransicao(new Transicao("c", q8));
		
		q2.adicionarTransicao(new Transicao("a", q7));
		q2.adicionarTransicao(new Transicao("b", q7));
		q2.adicionarTransicao(new Transicao("c", q0));
		
		q3.adicionarTransicao(new Transicao("b", q2));
		
		q5.adicionarTransicao(new Transicao("a", q6));
		
		q6.adicionarTransicao(new Transicao("b", q5));
		
		q8.adicionarTransicao(new Transicao("a", q9));
		q8.adicionarTransicao(new Transicao("b", q9));
		q8.adicionarTransicao(new Transicao("c", q9));
		
		q9.adicionarTransicao(new Transicao("a", q8));
		q9.adicionarTransicao(new Transicao("b", q8));
		q9.adicionarTransicao(new Transicao("c", q9));
		
		inicial = q0;
		
		alfabeto.add("a");
		alfabeto.add("b");
		alfabeto.add("c");
	}
}
