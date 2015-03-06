package teste;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import automato.Automato;

public class Determinismo {

	Automato test;
	Automato test2;
	Automato test3;
	Automato test4;
	@Before
	public void setUp() throws Exception {
		test = new AutomatoTest();
		test2 = new AutomatoTest2();
		test3 = new AutomatoTest3();
		test4 = new AutomatoTest4();
		
	}

	@Test
	public void testEhDeterministico() {
		assertFalse(test.ehDeterministico());
		assertFalse(test4.ehDeterministico());
		assertTrue(test2.ehDeterministico());
		assertTrue(test3.ehDeterministico());
	}

	@Test
	public void testDeterminizar() {
		test.determinizar();
		assertTrue(test.ehDeterministico());
		
		test4.determinizar();
		assertTrue(test4.ehDeterministico());
	}

}
