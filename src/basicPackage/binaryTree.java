package basicPackage;

public class binaryTree {
	public treeNode root;
	public int nodeCount;
	public treeNode lastInsertNode; // pointing to last node inserted into
									// balanced tree

	public binaryTree() {
		root = null;
		nodeCount = 0;
	}

	public binaryTree(int rootValue) {
		root = new treeNode(rootValue);
		lastInsertNode = root;
		nodeCount = 1;
	}

	public binaryTree(int[] intArray) {
		for (int i = 0; i < intArray.length; i++) {
			// add into tree
			if (i == 0) {// first node
				root = new treeNode(intArray[i]);
				lastInsertNode = root;
				nodeCount = 1;
			} else {
				addNodeBalanced(intArray[i]);
				nodeCount++;
			}
		}
	}

	public treeNode getRoot() {
		return root;
	}

	// get the left most leaf on the lowest level
	public treeNode getLeftMostNode(treeNode curRoot) {
		if (curRoot.leftLeaf == null)
			return curRoot;

		return this.getLeftMostNode(curRoot.leftLeaf);
	}

	// get the next node on the same level on the right
	public treeNode getNextRightSibling(treeNode curNode) {
		treeNode parent = curNode.parent;

		if (parent == null) // curNode is root , no right sibling
			return null;

		if (parent.leftLeaf == curNode)
			return parent.rightLeaf;

		treeNode parentRightSibling = getNextRightSibling(parent);
		if (parentRightSibling != null && parentRightSibling.leftLeaf != null) {
			return parentRightSibling.leftLeaf;
		}

		return null;
	}

	// return the parent for next insert, next insert would be its left child
	public treeNode getNextInsertParent() {
		treeNode parent = this.lastInsertNode.parent;

		// last insert is right child of parent
		// scenario 1锛�current node is not the right most on its level, then
		// parent's next right sibling's left child is the next spot to insert
		if (!isRightMost(this.lastInsertNode)) {
			return getNextRightSibling(parent);
		} else {// scenario 2: parent is the right most on its level, then next
				// insert spot should be the left child of the left most node on
				// current node level
			return this.getLeftMostNode(this.root);
		}
	}

	// return true if cur Node is the right most node on its level, else return
	// false
	public boolean isRightMost(treeNode curNode) {
		treeNode parent = curNode.parent;

		if (parent == null) // root
			return true;

		if (parent.rightLeaf == curNode) // is parent's right leaf, see if
											// parent's is right most
			return isRightMost(parent);

		return false;
	}

	// returns true if successful, return false otherwise
	public void addNodeBalanced(int newLeafValue) {
		treeNode newNode = new treeNode(newLeafValue);

		// root node is last insert, add to left child
		if (this.lastInsertNode == this.root) {
			this.lastInsertNode.leftLeaf = newNode;
			newNode.parent = this.lastInsertNode;
			this.lastInsertNode = newNode;
			return;
		}

		treeNode lastParent = this.lastInsertNode.parent;
		// if last insert node is left child, right child must be empty
		if (lastParent.leftLeaf == this.lastInsertNode) {
			// right sibling must be empty
			lastParent.rightLeaf = newNode;
			newNode.parent = lastParent;
			this.lastInsertNode = newNode;
			return;
		}

		// if last insert node is right child
		treeNode nextInsertParent = this.getNextInsertParent();
		nextInsertParent.leftLeaf = newNode;
		newNode.parent = nextInsertParent;
		this.lastInsertNode = newNode;
		return;
	}

	/*
	 * orderIndex = 1 : In-order orderIndex = 2 : Pre-order orderIndex = 3 :
	 * Post-order
	 */
	public void printTree(treeNode rootNode, int orderIndex) {
		if (rootNode != null) {
			// System.out.println("Printing tree with root:" + rootNode.value);
			switch (orderIndex) {
			case 1: { // In-order
				printTree(rootNode.leftLeaf, orderIndex);
				System.out.print(' ');
				System.out.print(rootNode.value);
				System.out.print(' ');
				printTree(rootNode.rightLeaf, orderIndex);
				break;
			}
			case 2: { // Pre-order
				System.out.print(' ');
				System.out.print(rootNode.value);
				System.out.print(' ');
				printTree(rootNode.leftLeaf, orderIndex);
				printTree(rootNode.rightLeaf, orderIndex);
				break;
			}
			case 3: { // Post-order
				printTree(rootNode.leftLeaf, orderIndex);
				printTree(rootNode.rightLeaf, orderIndex);
				System.out.print(' ');
				System.out.print(rootNode.value);
				System.out.print(' ');
				break;
			}

			default:
				System.out.println("orderIndex " + orderIndex
						+ " not recognized!\n");
				break;
			}
		}
	}

	/*
	 * No. 04 - Paths with Specified Sum in Binary Tree All nodes along children
	 * pointers from root to leaf nodes form a path in a binary tree. Given a
	 * binary tree and a number, please print out all of paths where the sum of
	 * all nodes value is same as the given number.
	 */
	public static int runCount = 0;

	public void addToPath(int sum, treeNode node, int[] path, int index) {
		// clear the path from index on;
		for (int i = index; i < path.length; i++) {
			if (path[i] > 0)
				path[i] = 0;
		}
		runCount++;

		if (node == null)
			return;

		// last node in the path, add to path and print out path
		if (node.value == sum) {
			path[index] = node.value;
			System.out.println();
			for (int i = 0; i < path.length; i++) {
				if (path[i] > 0) {
					System.out.print(path[i]);
					System.out.print(' ');
				}
			}
			System.out.println();
		}

		if (node.value > sum)
			return;

		if (node.value < sum) {
			// add current node if it has children
			if (node.leftLeaf != null || node.rightLeaf != null) {
				path[index] = node.value;
				addToPath(sum - node.value, node.leftLeaf, path, index + 1);
				addToPath(sum - node.value, node.rightLeaf, path, index + 1);
			}
		}
		return;
	}

	/*
	 * No. 31 - Binary Search Tree Verification How to verify whether a binary
	 * tree is a binary search tree?
	 */
	// method: print tree node in in-order into an array, if the array is
	// sorted, then yes. Otherwise no.
	public boolean isBST() {
		int[] array = new int[this.nodeCount];
		addToArray(this.root, array, 0);

		if (arrayInOrder(array, 1)) {
			return true;
		}

		return false;
	}

	// flag = 0, descending order; flag = 1, ascending order
	public boolean arrayInOrder(int[] array, int flag) {
		for (int i = 0; i < array.length - 2; i++) { // i is from 0 to 2nd last
			if ((flag == 0 && array[i] < array[i + 1])
					|| (flag == 1 && array[i] > array[i + 1])) {
				return false;
			}
		}
		return true;
	}

	// add node to array in in-order order
	public int addToArray(treeNode node, int[] array, int index) {
		int newIndex = index;

		if (node == null)
			return index;

		if (node.leftLeaf != null) {
			newIndex = addToArray(node.leftLeaf, array, index);
		}
		array[newIndex] = node.value;
		newIndex++;
		if (node.rightLeaf != null) {
			newIndex = addToArray(node.rightLeaf, array, newIndex);
		}
		return newIndex;
	}
}
