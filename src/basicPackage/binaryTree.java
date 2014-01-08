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
			}
			nodeCount++;
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
		// scenario 1： current node is not the right most on its level, then
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

	// given a number, print all paths on which the node's value sums to the
	// number, print from root down
	public void addToPath(int sum, treeNode node, int[] path, int nextPos) {
		if (shouldAdd(sum, node)) {
			// add node to curPath;
			path[nextPos] = node.value;

			int newSum = sum - node.value;
			if (newSum == 0) { // finish , print
				System.out.print("Path:");
				for (int i = 0; i < path.length; i++) {
					while (path[i] > 0) {
						System.out.print(path[i]);
						System.out.print(' ');
					}
					System.out.println();
				}
			} else {
				addToPath(sum - node.value, node.leftLeaf, path, nextPos + 1);
				addToPath(sum - node.value, node.rightLeaf, path, nextPos + 1);
			}
		}

		// if (node.value == sum) {
		// // add node to curPath, print; finish
		// path[nextPos] = node.value;
		// return true;
		// } else if (node.value < sum) {
		// int newSum = sum - node.value;
		// // add node to curPath;
		// path[nextPos] = node.value;
		// int newPos = nextPos + 1;
		// if (!addToPath(newSum, node.leftLeaf, path, newPos)
		// && !addToPath(newSum, node.rightLeaf, path, newPos)) {
		// // remove current node from curPath;
		// path[nextPos] = 0;
		// return false;
		// }
		// return true;
		// } else { // node.value > sum. this path so far is not fulfilling
		// // requirement, abandon it
		// // clear curPath
		// return false;
		// }
	}

	public boolean shouldAdd(int sum, treeNode node) {
		if (node == null)
			return false;

		if (node.value == sum) {
			return true;
		}

		if (node.value < sum) {
			if (shouldAdd(sum - node.value, node.leftLeaf)
					|| shouldAdd(sum - node.value, node.rightLeaf)) {
				return true;
			}
		}

		return false;
	}
}
