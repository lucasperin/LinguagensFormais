package expressaoRegular;

import static expressaoRegular.RegularExpression.*;
import expressaoRegular.BinaryNode;
import expressaoRegular.BinaryTree;
import static expressaoRegular.RegularExpression.terminals;

public class SyntaxTree extends BinaryTree<Character> {

	private StringBuilder builder;

	public SyntaxTree() {
		super();
	}

	public SyntaxTree(String infixRegex) {
		super();
		RegularExpression expression = new RegularExpression(infixRegex);
		expression.normatize();
		buildTreeFromExpression(expression.toString());
		setLambda();
	}

	public void buildTreeFromExpression(String infixRegex) {
		this.builder = new StringBuilder(infixRegex);
		this.root = expr();
	}

	private BinaryNode<Character> atom() {
		BinaryNode<Character> node = null;
		Character c = peek();
		if (c.equals(OPENING_PARENTESIS)) {
			c = pop();
			node = expr();
			c = pop();
			if (!c.equals(CLOSING_PARENTESIS)) {
				assert false;
			}
		} else if (terminals.contains(c)) {
			c = pop();
			node = new BinaryNode<Character>(c);
		}
		return node;
	}

	private BinaryNode<Character> single() {
		BinaryNode<Character> left = atom();
		Character c = peek();
		BinaryNode<Character> node = null;
		if (c.equals(KLEENE_STAR) || c.equals(OPTIONAL)) {
			c = pop();
			node = new BinaryNode<Character>(c);
			node.setLeftChild(left);
		} else {
			node = left;
		}
		return node;
	}

	private BinaryNode<Character> concat() {
		BinaryNode<Character> left = single();
		Character c = peek();
		BinaryNode<Character> node = null;
		if (c.equals(CONCATENATION)) {
			c = pop();
			node = new BinaryNode<Character>(c);
			node.setLeftChild(left);
			BinaryNode<Character> right = concat();
			node.setRightChild(right);
		} else {
			node = left;
		}
		return node;
	}

	private BinaryNode<Character> expr() {
		BinaryNode<Character> left = concat();
		BinaryNode<Character> node = null;
		Character c = peek();
		if (c.equals(OR)) {
			c = pop();
			node = new BinaryNode<Character>(c);
			node.setLeftChild(left);
			BinaryNode<Character> right = expr();
			node.setRightChild(right);
		} else {
			node = left;
		}
		return node;
	}

	public String prefixPrint() {
		return recursivePrefixPrint(root);
	}

	public Character peek() {
		return builder.length() > 0 ? builder.charAt(0) : '\0';
	}

	public Character pop() {
		Character c = builder.charAt(0);
		builder.deleteCharAt(0);
		return c;
	}

	private String recursivePrefixPrint(BinaryNode<Character> root) {
		String result = "";
		if (root != null) {
			result += root.getValue();
			result += recursivePrefixPrint(root.getLeftChild());
			result += recursivePrefixPrint(root.getRightChild());
			System.err.println(result);
		}
		return result;
	}

}
