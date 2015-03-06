package teste;

import automato.Automato;
import automato.Estado;
import automato.Transicao;

public class AutomatoTest2 extends Automato{
	private static final long serialVersionUID = 1L;

	//Gerador de #a's par
	public AutomatoTest2() {
		super();
		Estado q0 = new Estado(true, "q0");
		estados.add(q0);
		Estado q1 = new Estado(false, "q1");
		estados.add(q1);

		q0.adicionarTransicao(new Transicao("a", q1));
		
		q1.adicionarTransicao(new Transicao("a", q0));

		
		inicial = q0;
		
		alfabeto.add("a");
	}
}
