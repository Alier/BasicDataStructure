package basicPackage;

public class binarySearchTree extends binaryTree {

	public binarySearchTree() {
		super();
	}

	public binarySearchTree(int rootValue) {
		super(rootValue);
	}

	public binarySearchTree(int[] intArray) {
		for (int i = 0; i < intArray.length; i++) {
			// add into tree
			if (i == 0) {// first node
				root = new treeNode(intArray[i]);
				lastInsertNode = root;
				nodeCount = 1;
			} else {
				addNodeSorted(intArray[i], root);
			}
			nodeCount++;
		}
	}

	//add node in a fashion to form a BST !!!!!!!!! INCOMPLETE !!!!!!!!!
	public void addNodeSorted(int nodeValue, treeNode curRoot) {
		if(nodeValue < curRoot.value) {//add to left child tree
			if(curRoot.leftLeaf != null ) {
				addNodeSorted(nodeValue,curRoot.leftLeaf);
			} else {
				
			}
		} 
		
		if(nodeValue > curRoot.value) { //add to right child tree
			
		}
	}
}
