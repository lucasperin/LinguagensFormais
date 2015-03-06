package expressaoRegular;

public class BinaryTree<T> {

	protected BinaryNode<T> root;

	public BinaryTree() {
		this.root = new BinaryNode<T>();
	}

	public BinaryNode<T> getRoot() {
		return root;
	}

	public void setRoot(BinaryNode<T> root) {
		this.root = root;
	}
	
	public void setLambda(){
		this.root.setLambdaNode();
	}
}