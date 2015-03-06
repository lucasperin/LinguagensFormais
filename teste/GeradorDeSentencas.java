package teste;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import automato.Automato;
;

public class GeradorDeSentencas {

	Automato teste2 = new AutomatoTest2();
	Automato teste3 = new AutomatoTest3();
	
	
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGerarSentencas() {
		
		teste2.gerarSentencas(2);
		teste3.gerarSentencas(7);
		
		
		fail("Not yet implemented");
	}

}
