package teste;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import automato.Automato;

public class Minimizacao {

	Automato testeA, testeB;
	Automato teste2A, teste2B;
	@Before
	public void setUp() throws Exception {
		testeA = new AutomatoTest();
		testeB = new AutomatoTest();
		testeB.minimizar();
		teste2A = new AutomatoTest8();
		teste2B = new AutomatoTest8();
		teste2B.minimizar();
	}

	@Test
	public void test() {
		List<String> A = testeA.gerarSentencas(10);
		List<String> B = testeB.gerarSentencas(10);
		
		assertTrue(A.equals(B));
	}

}
