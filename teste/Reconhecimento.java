package teste;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import automato.Automato;

public class Reconhecimento {

	Automato test, test2, test3;
	@Before
	public void setUp() throws Exception {
		test = new AutomatoTest();
		test2 = new AutomatoTest2();
		test3 = new AutomatoTest3();
	}

	@Test
	public void testReconhecer() {
		assertTrue(test.reconhecer("abc"));
		assertTrue(test.reconhecer("abcabc"));
		assertTrue(test.reconhecer(""));
		assertFalse(test.reconhecer("acb"));
		assertFalse(test.reconhecer("abca"));
		assertFalse(test.reconhecer("abb"));
	}
	@Test
	public void testReconhecer2() {
		assertTrue(test2.reconhecer("aa"));
		assertTrue(test2.reconhecer("aaaaaa"));
		assertTrue(test2.reconhecer(""));
	}
	@Test
	public void testReconhecer3() {
		assertTrue(test3.reconhecer("aabb"));
		assertTrue(test3.reconhecer("aaaabbbbbb"));
		assertTrue(test3.reconhecer("aaabbbbb"));
		assertTrue(test3.reconhecer(""));
	}

}
