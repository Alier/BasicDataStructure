package basicPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class binaryTree {
	public treeNode root;
	public int nodeCount;
	public treeNode lastInsertNode; // pointing to last node inserted into
									// balanced tree
	public treeNode highestPointForMaxPath; // pointing at the node from where
											// the max path formed without going
											// up

	public int running_time_NotRoot = 0;
	public int running_time_asRoot = 0;
	public int running_time_NotRoot2 = 0;
	public int running_time_asRoot2 = 0;

	public binaryTree() {
		root = null;
		nodeCount = 0;
		highestPointForMaxPath = null;
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

	/*
	 * Given a binary tree, find its minimum depth. The minimum depth is the
	 * number of nodes along the shortest path from the root node down to the
	 * nearest leaf node.
	 */
	public int minDepth(treeNode curRoot) {
		if (curRoot == null)
			return 0;

		if (curRoot.leftLeaf == null && curRoot.rightLeaf == null)
			return 1;

		if (curRoot.leftLeaf != null && curRoot.rightLeaf != null) {
			int minLeft = minDepth(curRoot.leftLeaf);
			int minRight = minDepth(curRoot.rightLeaf);

			return minLeft <= minRight ? (minLeft + 1) : (minRight + 1);
		}

		// rightLeaf only
		if (curRoot.leftLeaf == null) {
			return minDepth(curRoot.rightLeaf) + 1;
		} else {// leftLeaf only
			return minDepth(curRoot.leftLeaf) + 1;
		}
	}

	/*
	 * Given a binary tree, find its maximum depth.
	 * 
	 * The maximum depth is the number of nodes along the longest path from the
	 * root node down to the farthest leaf node.
	 */
	public int maxDepth(treeNode root) {
		if (root == null)
			return 0;

		if (root.leftLeaf == null && root.rightLeaf == null)
			return 1;

		if (root.leftLeaf != null && root.rightLeaf != null) {
			int maxLeft = maxDepth(root.leftLeaf);
			int maxRight = maxDepth(root.rightLeaf);

			return maxLeft <= maxRight ? (maxRight + 1) : (maxLeft + 1);
		}

		// rightLeaf only
		if (root.leftLeaf == null) {
			return maxDepth(root.rightLeaf) + 1;
		} else {// leftLeaf only
			return maxDepth(root.leftLeaf) + 1;
		}
	}

	/*
	 * Given a binary tree, find the maximum path sum. The path may start and
	 * end at any node in the tree.--- DON'T need to be in order from root down
	 */
	// Method 1: too time-consuming, as the tree has to be traversed couple
	// times.
	public int maxPathSum(treeNode curRoot) {
		int maxPathNotRoot;
		int maxPathAsRoot;
		int curMax;
		int maxPathFinal = 0;
		treeNode p = curRoot;

		// max for left tree
		do {
			p = p.leftLeaf;
			maxPathNotRoot = maxPathNotRoot(p);
			maxPathAsRoot = maxPathAsRoot(p);

			curMax = maxPathNotRoot > maxPathAsRoot ? maxPathNotRoot
					: maxPathAsRoot;
			if (curMax > maxPathFinal) {
				maxPathFinal = curMax;
				this.highestPointForMaxPath = p;
			}
		} while (p != null);

		p = curRoot;
		// max for right tree
		do {
			p = p.rightLeaf;
			maxPathNotRoot = maxPathNotRoot(p);
			maxPathAsRoot = maxPathAsRoot(p);

			curMax = maxPathNotRoot > maxPathAsRoot ? maxPathNotRoot
					: maxPathAsRoot;
			if (curMax > maxPathFinal) {
				maxPathFinal = curMax;
				this.highestPointForMaxPath = p;
			}
		} while (p != null);

		// max for root
		maxPathNotRoot = maxPathNotRoot(curRoot);
		maxPathAsRoot = maxPathAsRoot(curRoot);

		curMax = maxPathNotRoot > maxPathAsRoot ? maxPathNotRoot
				: maxPathAsRoot;
		if (curMax > maxPathFinal) {
			maxPathFinal = curMax;
			this.highestPointForMaxPath = curRoot;
		}

		System.out.println("Running time for method 1:"
				+ this.running_time_asRoot + "/" + this.running_time_NotRoot);
		return maxPathFinal;
	}

	// return the max sum of curRoot as the last point in the path, only
	// choosing left or right child tree in the path
	public int maxPathNotRoot(treeNode curRoot) {
		this.running_time_NotRoot++;

		if (curRoot == null)
			return 0;

		if (curRoot.leftLeaf == null && curRoot.rightLeaf == null)
			return curRoot.value;

		int leftMax = maxPathNotRoot(curRoot.leftLeaf);
		int rightMax = maxPathNotRoot(curRoot.rightLeaf);

		return (leftMax >= rightMax ? (leftMax + curRoot.value)
				: (rightMax + curRoot.value));
	}

	// return the max sum of curRoot as root in the final path (meaning curRoot
	// is in the path, parent is not, taking both left and right in the path)
	public int maxPathAsRoot(treeNode curRoot) {
		this.running_time_asRoot++;
		if (curRoot == null)
			return 0;

		return maxPathNotRoot(curRoot.leftLeaf)
				+ maxPathNotRoot(curRoot.rightLeaf) + curRoot.value;
	}

	// Method2: traverse the tree once from down to up, calculate both max for
	// each node, get the highest after all traverse done;
	public int maxPathSum2(treeNode curRoot) {
		HashMap<treeNode, Integer> node_notRootMax = new HashMap<treeNode, Integer>();
		HashMap<treeNode, Integer> node_asRootMax = new HashMap<treeNode, Integer>();

		int maxPathNotRoot;
		int maxPathAsRoot;
		int curMax;
		int maxPathFinal = -10000;
		treeNode p = curRoot;

		if (p == null)
			return 0;

		// max for left tree
		while ((p = p.leftLeaf) != null) {
			maxPathNotRoot = maxPathNotRoot2(p, node_notRootMax);
			maxPathAsRoot = maxPathAsRoot2(p, node_notRootMax, node_asRootMax);

			curMax = maxPathNotRoot > maxPathAsRoot ? maxPathNotRoot
					: maxPathAsRoot;
			if (curMax > maxPathFinal) {
				maxPathFinal = curMax;
				this.highestPointForMaxPath = p;
			}
		}

		p = curRoot;
		// max for right tree
		while ((p = p.rightLeaf) != null) {
			maxPathNotRoot = maxPathNotRoot2(p, node_notRootMax);
			maxPathAsRoot = maxPathAsRoot2(p, node_notRootMax, node_asRootMax);

			curMax = maxPathNotRoot > maxPathAsRoot ? maxPathNotRoot
					: maxPathAsRoot;
			if (curMax > maxPathFinal) {
				maxPathFinal = curMax;
				this.highestPointForMaxPath = p;
			}
		}

		// max for root
		maxPathNotRoot = maxPathNotRoot2(curRoot, node_notRootMax);
		maxPathAsRoot = maxPathAsRoot2(curRoot, node_notRootMax, node_asRootMax);

		curMax = maxPathNotRoot > maxPathAsRoot ? maxPathNotRoot
				: maxPathAsRoot;
		if (curMax > maxPathFinal) {
			maxPathFinal = curMax;
			this.highestPointForMaxPath = curRoot;
		}

		System.out.println("Running time for method 2:"
				+ this.running_time_asRoot2 + "/" + this.running_time_NotRoot2);
		return maxPathFinal;
	}

	// return the max sum of curRoot as the last point in the path, only
	// choosing left or right child tree in the path
	public int maxPathNotRoot2(treeNode curRoot,
			HashMap<treeNode, Integer> node_notRootMax) {
		this.running_time_NotRoot2++;
		if (node_notRootMax.containsKey(curRoot))
			return node_notRootMax.get(curRoot);

		if (curRoot.leftLeaf == null && curRoot.rightLeaf == null) {
			node_notRootMax.put(curRoot, curRoot.value);
			return curRoot.value;
		}

		int leftMax = maxPathNotRoot(curRoot.leftLeaf);
		int rightMax = maxPathNotRoot(curRoot.rightLeaf);

		int curMax = (leftMax >= rightMax ? (leftMax + curRoot.value)
				: (rightMax + curRoot.value));
		node_notRootMax.put(curRoot, curMax);
		return curMax;
	}

	// return the max sum of curRoot as root in the final path (meaning curRoot
	// is in the path, parent is not, taking both left and right in the path)
	public int maxPathAsRoot2(treeNode curRoot,
			HashMap<treeNode, Integer> node_notRootMax,
			HashMap<treeNode, Integer> node_asRootMax) {
		this.running_time_asRoot2++;
		int curMax = -10000;

		if (node_asRootMax.containsKey(curRoot))
			return node_asRootMax.get(curRoot);

		if (curRoot.leftLeaf == null && curRoot.rightLeaf == null) {
			curMax = curRoot.value;
		} else if (curRoot.leftLeaf == null && curRoot.rightLeaf != null) {
			curMax = maxPathNotRoot2(curRoot.rightLeaf, node_notRootMax)
					+ curRoot.value;
		} else if (curRoot.leftLeaf != null && curRoot.rightLeaf == null) {
			curMax = maxPathNotRoot2(curRoot.leftLeaf, node_notRootMax)
					+ curRoot.value;
		} else {
			curMax = maxPathNotRoot2(curRoot.leftLeaf, node_notRootMax)
					+ maxPathNotRoot2(curRoot.rightLeaf, node_notRootMax)
					+ curRoot.value;
		}

		node_asRootMax.put(curRoot, curMax);
		return curMax;
	}

	// reversal of binary tree in pre-order, post-order, and in-order, using
	// no-recurrsive way
	public ArrayList<Integer> preorderTraversal(treeNode root) {
		ArrayList<Integer> valsInPreOrder = new ArrayList<Integer>();
		Stack<treeNode> nodesInPreOrder = new Stack<treeNode>();

		if (root == null)
			return valsInPreOrder;

		// push root in to begin with
		nodesInPreOrder.push(root);

		// for each node in stack, pop it up, add right/left , so left is poping
		// up first;
		treeNode curRoot = null;
		while (!nodesInPreOrder.isEmpty()) {
			curRoot = nodesInPreOrder.pop();
			valsInPreOrder.add(new Integer(curRoot.value));
			// add right/left in stack
			if (curRoot.rightLeaf != null)
				nodesInPreOrder.push(curRoot.rightLeaf);
			if (curRoot.leftLeaf != null)
				nodesInPreOrder.push(curRoot.leftLeaf);
		}

		return valsInPreOrder;
	}

	public ArrayList<Integer> inorderTraversal(treeNode root) {
		ArrayList<Integer> valsInOrder = new ArrayList<Integer>();
		Stack<treeNode> nodesInOrder = new Stack<treeNode>();
		ArrayList<treeNode> visited = new ArrayList<treeNode>();

		if (root == null)
			return valsInOrder;

		// push root to stack as starting point
		nodesInOrder.push(root);

		treeNode curRoot = null;
		while (!nodesInOrder.isEmpty()) {
			curRoot = nodesInOrder.peek(); // check value
			if (curRoot.leftLeaf != null && !visited.contains(curRoot)) { // has
																			// left
																			// leaf,
																			// and
																			// it's
																			// visited
																			// for
																			// first
																			// time
				nodesInOrder.push(curRoot.leftLeaf);
				visited.add(curRoot);
			} else { // no left leaf, pop out, and push right leaf in if there
						// is any
				nodesInOrder.pop();
				valsInOrder.add(new Integer(curRoot.value));
				if (curRoot.rightLeaf != null) {
					nodesInOrder.push(curRoot.rightLeaf);
				}
			}
		}

		return valsInOrder;
	}

	public ArrayList<Integer> postorderTraversal(treeNode root) {
		ArrayList<Integer> valsInPostOrder = new ArrayList<Integer>();
		Stack<treeNode> nodesInPostOrder = new Stack<treeNode>();
		ArrayList<treeNode> visited = new ArrayList<treeNode>();

		if (root == null)
			return valsInPostOrder;

		// push root in to begin with
		nodesInPostOrder.push(root);

		treeNode curNode = null;
		while (!nodesInPostOrder.isEmpty()) {
			// peek at the topmost node
			curNode = nodesInPostOrder.peek();
			// if it has no children or visited before then pop and add to array
			if ((curNode.leftLeaf == null && curNode.rightLeaf == null)
					|| visited.contains(curNode)) {
				nodesInPostOrder.pop();
				valsInPostOrder.add(new Integer(curNode.value));
			} else {// else push children in right/left order, so left would be
					// visited sooner later
				visited.add(curNode); // mark as visited
				if (curNode.rightLeaf != null)
					nodesInPostOrder.push(curNode.rightLeaf);
				if (curNode.leftLeaf != null)
					nodesInPostOrder.push(curNode.leftLeaf);
			}
		}

		return valsInPostOrder;
	}

	public void printNodeArray(ArrayList<Integer> nodes) {
		System.out.println();
		for (int i = 0; i < nodes.size(); i++) {
			System.out.print(" " + nodes.get(i).toString() + " ");
		}
		System.out.println();
	}

	/*
	 * Given a binary tree, determine if it is height-balanced. For this
	 * problem, a height-balanced binary tree is defined as a binary tree in
	 * which the depth of the two subtrees of every node never differ by more
	 * than 1.
	 */
	public boolean isBalanced(treeNode root,
			HashMap<treeNode, Integer> nodeDepthes) {
		if (root == null)
			return true;
		int depthLeft = depthOfNode(root.leftLeaf, nodeDepthes);
		int depthRight = depthOfNode(root.rightLeaf, nodeDepthes);

		if (!isBalanced(root.leftLeaf, nodeDepthes))
			return false;

		if (!isBalanced(root.rightLeaf, nodeDepthes))
			return false;

		if (depthLeft + 1 <= depthRight || depthLeft - 1 >= depthRight)
			return true;
		else
			return false;
	}

	public static int count = 0;

	public int depthOfNode(treeNode node, HashMap<treeNode, Integer> nodesDepth) {
		count++;
		if (node == null)
			return 0;

		// already calculated.
		if (nodesDepth.containsKey(node))
			return nodesDepth.get(node);

		int leftDepth = depthOfNode(node.leftLeaf, nodesDepth);
		int rightDepth = depthOfNode(node.rightLeaf, nodesDepth);

		int nodeDepth = (leftDepth >= rightDepth ? leftDepth + 1
				: rightDepth + 1);
		// System.out.println("Depth of node "+node.value +":"+nodeDepth);
		// put into map
		nodesDepth.put(node, new Integer(nodeDepth));
		return nodeDepth;
	}

	// without hashmap storage.
	public boolean isBalancedNoStorage(treeNode root) {
		if (root == null)
			return true;

		if (!isBalancedNoStorage(root.leftLeaf))
			return false;

		if (!isBalancedNoStorage(root.rightLeaf))
			return false;

		int depthLeft = depthOfNodeNoStorage(root.leftLeaf);
		int depthRight = depthOfNodeNoStorage(root.rightLeaf);

		if (depthLeft + 1 <= depthRight || depthLeft - 1 >= depthRight)
			return true;
		else
			return false;
	}

	public static int count2 = 0;

	public int depthOfNodeNoStorage(treeNode node) {
		count2++;
		if (node == null)
			return 0;

		int leftDepth = depthOfNodeNoStorage(node.leftLeaf);
		int rightDepth = depthOfNodeNoStorage(node.rightLeaf);

		return (leftDepth >= rightDepth ? leftDepth + 1 : rightDepth + 1);
	}

	public static int count3 = 0;

	// using stack, so not recursive.
	public boolean isBalancedIterally(treeNode root) {
		if (root == null)
			return true;

		Stack<treeNode> nodes = new Stack<treeNode>();
		HashMap<treeNode, Integer> depthOfNodes = new HashMap<treeNode, Integer>();
		// visited node meaning its children exists and depth has been
		// calculated.
		ArrayList<treeNode> visited = new ArrayList<treeNode>();

		// push root in to start with
		nodes.push(root);

		treeNode curNode = null;
		// while there is node in stack, peek it
		while (!nodes.isEmpty()) {
			count3++;
			curNode = nodes.peek();
			// if it's visited before, then pop it out, check its children's
			// difference and
			// calculate its depth
			if (visited.contains(curNode)) {
				nodes.pop();
				int leftDepth = 0;
				int rightDepth = 0;

				if (depthOfNodes.containsKey(curNode.leftLeaf))
					leftDepth = depthOfNodes.get(curNode.leftLeaf);
				if (depthOfNodes.containsKey(curNode.rightLeaf))
					rightDepth = depthOfNodes.get(curNode.rightLeaf);

				// depth is 0 if null;
				if (leftDepth - rightDepth > 1 || rightDepth - leftDepth > 1)
					return false;
				else {
					int curDepth = (leftDepth > rightDepth ? leftDepth + 1
							: rightDepth + 1);
					depthOfNodes.put(curNode, new Integer(curDepth));
				}
			} else { // first time visiting, adding children if it has.
				if (curNode.leftLeaf == null && curNode.rightLeaf == null) {
					nodes.pop();
					depthOfNodes.put(curNode, new Integer(1)); // leaf depth is
																// 1
				} else {
					visited.add(curNode); // mark as visited as children are
											// added
					if (curNode.leftLeaf != null)
						nodes.push(curNode.leftLeaf);
					if (curNode.rightLeaf != null)
						nodes.push(curNode.rightLeaf);
				}
			}
		}
		return true;
	}

	/*
	 * Given a binary tree, return the level order traversal of its nodes'
	 * values. (ie, from left to right, level by level).
	 * 
	 * For example: Given binary tree {3,9,20,#,#,15,7},return its level order
	 * traversal as: [ [3], [9,20], [15,7] ]
	 */
	public ArrayList<ArrayList<Integer>> levelOrder(treeNode root) {
		ArrayList<ArrayList<treeNode>> listLevelNodes = new ArrayList<ArrayList<treeNode>>();
		ArrayList<ArrayList<Integer>> listLevelValues = new ArrayList<ArrayList<Integer>>();
		int curLevel = 0;

		if (root == null)
			return listLevelValues;

		// put root into the first element of listLevelNodes, set the current
		// level index to 0, corresponding to its index in array;
		ArrayList<treeNode> curLevelNode = new ArrayList<treeNode>();
		curLevelNode.add(root);
		listLevelNodes.add(curLevelNode);
		ArrayList<Integer> curLevelValue = new ArrayList<Integer>();
		curLevelValue.add(new Integer(root.value));
		listLevelValues.add(curLevelValue);

		treeNode curNode = null;
		while (true) {
			ArrayList<treeNode> nextLevelNode = new ArrayList<treeNode>();
			ArrayList<Integer> nextLevelValue = new ArrayList<Integer>();

			// for each level's nodes in the list already, find its children to
			// add to next level's array
			for (int i = 0; i < listLevelNodes.get(curLevel).size(); i++) {
				curNode = listLevelNodes.get(curLevel).get(i);
				if (curNode.leftLeaf != null) {
					nextLevelNode.add(curNode.leftLeaf);
					nextLevelValue.add(new Integer(curNode.leftLeaf.value));
				}
				if (curNode.rightLeaf != null) {
					nextLevelNode.add(curNode.rightLeaf);
					nextLevelValue.add(new Integer(curNode.rightLeaf.value));
				}
			}
			
			if(nextLevelNode.isEmpty()) { //finish traversing tree
				break;
			} else {
				listLevelNodes.add(nextLevelNode);
				listLevelValues.add(nextLevelValue);
				curLevel++;
			} 
		}

		return listLevelValues;
	}

	public void printNodesByLevel(ArrayList<ArrayList<Integer>> levelNodes) {
		if (levelNodes == null)
			return;

		System.out.println("[");
		for (int i = 0; i < levelNodes.size(); i++) { // each level
			System.out.print("[");
			for (int j = 0; j < levelNodes.get(i).size(); j++) {
				if (j == 0)
					System.out.print(levelNodes.get(i).get(j));
				else
					System.out.print("," + levelNodes.get(i).get(j));
			}

			if (i == levelNodes.size())
				System.out.println("]");
			else
				System.out.println("],");
		}
		System.out.println("]");
	}
}
