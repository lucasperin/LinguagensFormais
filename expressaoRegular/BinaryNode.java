package expressaoRegular;

public class BinaryNode<T> {

	private boolean lambda;
	private BinaryNode<T> leftChild;
	private BinaryNode<T> rightChild;
	private T value;
	private BinaryNode<T> parent;

	public BinaryNode() {
		this.value = null;
		this.leftChild = null;
		this.rightChild = null;
		this.parent = null;
		this.lambda = false;
	}

	public BinaryNode(T value) {
		this.value = value;
		this.leftChild = null;
		this.rightChild = null;
		this.lambda = false;
	}

	public boolean isLeaf() {
		return (leftChild == null && rightChild == null);
	}

	public BinaryNode<T> getLeftChild() {
		return leftChild;
	}

	public BinaryNode<T> getRightChild() {
		return rightChild;
	}

	public T getValue() {
		return value;
	}


	public boolean isLambda() {
		return lambda;
	}

	private void setLambda(boolean lambda) {
		this.lambda = lambda;
	}

	public void setLeftChild(BinaryNode<T> leftChild) {
		this.leftChild = leftChild;
		if(leftChild != null){
			leftChild.setParent(this);
		}
	}

	private void setParent(BinaryNode<T> parent) {
		this.parent = parent;
	}

	public void setRightChild(BinaryNode<T> rightChild) {
		this.rightChild = rightChild;
		if(rightChild != null){
			rightChild.setParent(this);
		}
	
	}

	public void setValue(T value) {
		this.value = value;
	}

	public BinaryNode<T> getParent() {
		return this.parent;
	}

	public void setLambdaNode() {
		BinaryNode<T> lastRight = getRightChild();
		if(lastRight == null){
			this.setLambda(true);
		}else{
			lastRight.setLambdaNode();
		}
	}

}
