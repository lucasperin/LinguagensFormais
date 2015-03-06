package teste;

import automato.Automato;
import automato.Estado;
import automato.Transicao;

public class AutomatoTest8 extends Automato {
	private static final long serialVersionUID = 1L;

	public AutomatoTest8() {
		super();
		Estado q0 = new Estado(true, "S");
		estados.add(q0);
		Estado q1 = new Estado(false, "A");
		estados.add(q1);
		Estado q2 = new Estado(true, "B");
		estados.add(q2);
		Estado q3 = new Estado(false, "C");
		estados.add(q3);
		Estado q4 = new Estado(true, "D");
		estados.add(q4);
		Estado q5 = new Estado(false, "E");
		estados.add(q5);
		
		q0.adicionarTransicao(new Transicao("a", q1));
		q0.adicionarTransicao(new Transicao("b", q2));//se trocar por a da erro ao determiniza o.O
		
		q1.adicionarTransicao(new Transicao("a", q0));
		q1.adicionarTransicao(new Transicao("b", q3));
		q1.adicionarTransicao(new Transicao("b", q5));
		
		q2.adicionarTransicao(new Transicao("a", q1));
		q2.adicionarTransicao(new Transicao("a", q3));
		
		q3.adicionarTransicao(new Transicao("a", q2));
		
		q4.adicionarTransicao(new Transicao("a", q5));
		
		q5.adicionarTransicao(new Transicao("a", q0));
		q5.adicionarTransicao(new Transicao("a", q4));
		
		inicial = q0;
		
		alfabeto.add("a");
		alfabeto.add("b");
	}
}
