package teste;

import automato.Automato;
import automato.Estado;
import automato.Transicao;
//Exemplo http://www.inf.ufsc.br/~olinto/ine5421-cap3.pdf - PAG 8 - EXERCICIO 4
//Determinizado 
// ERRO NO METODO DETERMINIZAR
public class AutomatoTest9Determinizado extends Automato {
	private static final long serialVersionUID = 1L;

	public AutomatoTest9Determinizado() {
		super();
		Estado q0 = new Estado(true, "S");
		estados.add(q0);
		Estado q1 = new Estado(true, "A");
		estados.add(q1);
		Estado q2 = new Estado(true, "B");
		estados.add(q2);
		Estado q3 = new Estado(true, "C");
		estados.add(q3);
		Estado q4 = new Estado(true, "D");
		estados.add(q4);
		Estado q5 = new Estado(true, "AC");
		estados.add(q5);
		Estado q6 = new Estado(true, "AD");
		estados.add(q6);
		Estado q7 = new Estado(true, "BC");
		estados.add(q7);
		
		q0.adicionarTransicao(new Transicao("a", q5));
		//q0.adicionarTransicao(new Transicao("a", q3));
		q0.adicionarTransicao(new Transicao("b", q6));
		//q0.adicionarTransicao(new Transicao("b", q4));
		q0.adicionarTransicao(new Transicao("c", q7));
		//q0.adicionarTransicao(new Transicao("c", q3));
		
		q1.adicionarTransicao(new Transicao("a", q1));
		q1.adicionarTransicao(new Transicao("b", q1));
		q1.adicionarTransicao(new Transicao("c", q2));
		
		q2.adicionarTransicao(new Transicao("a", q1));
		q2.adicionarTransicao(new Transicao("b", q1));		
		
		q3.adicionarTransicao(new Transicao("a", q3));
		q3.adicionarTransicao(new Transicao("b", q4));
		q3.adicionarTransicao(new Transicao("c", q3));
		
		q4.adicionarTransicao(new Transicao("a", q3));
		q4.adicionarTransicao(new Transicao("c", q3));
		
		q5.adicionarTransicao(new Transicao("a", q5));
		q5.adicionarTransicao(new Transicao("b", q6));
		q5.adicionarTransicao(new Transicao("c", q7));
		

		q6.adicionarTransicao(new Transicao("a", q5));
		q6.adicionarTransicao(new Transicao("b", q1));
		q6.adicionarTransicao(new Transicao("c", q7));
		

		q7.adicionarTransicao(new Transicao("a", q5));
		q7.adicionarTransicao(new Transicao("b", q6));
		q7.adicionarTransicao(new Transicao("c", q3));
		
		inicial = q0;
		
		alfabeto.add("a");
		alfabeto.add("b");
		alfabeto.add("c");
	}
}
