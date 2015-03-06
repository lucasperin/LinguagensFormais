package expressaoRegular;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegularExpression {

	private String expression;
	public static List<Character> terminals = Arrays.asList('a', 'b', 'c', 'd',
			'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'x', 'w', 'y', 'z', '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9');

	public static final Character OPTIONAL = '?';
	public static final Character KLEENE_STAR = '*';
	public static final Character OR = '|';
	public static final Character CONCATENATION = '.';
	public static final Character ATLEAST_ONE = '+';
	public static final Character CLOSING_PARENTESIS = ')';
	public static final Character OPENING_PARENTESIS = '(';

	public RegularExpression() {
		this.expression = new String();
	}

	public RegularExpression(String regexp) {
		this.expression = new String(regexp);
	}

	public void normatize() {
		List<Character> previousCharacters = new ArrayList<Character>(terminals);
		previousCharacters.add(OPTIONAL);
		previousCharacters.add(KLEENE_STAR);
		previousCharacters.add(CLOSING_PARENTESIS);
		List<Character> followupCharacters = new ArrayList<Character>(terminals);
		followupCharacters.add(OPENING_PARENTESIS);

		StringBuilder builder = new StringBuilder(this.expression);
		for (int i = 0; i < builder.length() - 1; i++) {
			Character current = builder.charAt(i);
			Character next = builder.charAt(i + 1);
			if (previousCharacters.contains(current)
					&& followupCharacters.contains(next)) {
				String replacement = current + "." + next;
				String rest = builder.substring(i + 2);
				builder.delete(i, builder.length());
				builder.append(replacement);
				builder.append(rest);
			}
		}
		this.expression = builder.toString();
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getExpression() {
		return this.expression;
	}

	@Override
	public String toString() {
		return this.expression;
	}
}
