package teste;

import automato.Automato;
import automato.Estado;
import automato.Transicao;

public class AutomatoTest4 extends Automato{
	private static final long serialVersionUID = 1L;

	public AutomatoTest4() {
		super();
		Estado q0 = new Estado(false, "q0");
		estados.add(q0);
		Estado q1 = new Estado(false, "q1");
		estados.add(q1);
		Estado q2 = new Estado(false, "q2");
		estados.add(q2);
		Estado q3 = new Estado(true, "q3");
		estados.add(q3);

		q0.adicionarTransicao(new Transicao("a", q0));
		q0.adicionarTransicao(new Transicao("a", q1));
		q0.adicionarTransicao(new Transicao("b", q0));
		
		q1.adicionarTransicao(new Transicao("b", q2));
		
		q2.adicionarTransicao(new Transicao("b", q3));

		
		inicial = q0;
		alfabeto.add("a");
		alfabeto.add("b");
	}
}
