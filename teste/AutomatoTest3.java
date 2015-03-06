package teste;

import automato.Automato;
import automato.Estado;
import automato.Transicao;

public class AutomatoTest3 extends Automato{
	private static final long serialVersionUID = 1L;

	//Gerador de #a's + #b's = par
	public AutomatoTest3() {
		super();
		Estado q0 = new Estado(true, "q0");
		estados.add(q0);
		Estado q1 = new Estado(false, "q1");
		estados.add(q1);
		Estado q2 = new Estado(true, "q2");
		estados.add(q2);
		Estado q3 = new Estado(false, "q3");
		estados.add(q3);

		q0.adicionarTransicao(new Transicao("a", q1));
		q0.adicionarTransicao(new Transicao("b", q3));
		
		q1.adicionarTransicao(new Transicao("a", q0));
		q1.adicionarTransicao(new Transicao("b", q2));
		
		q2.adicionarTransicao(new Transicao("b", q3));

		q3.adicionarTransicao(new Transicao("b", q2));
		
		inicial = q0;
		alfabeto.add("a");
		alfabeto.add("b");
	}
}
