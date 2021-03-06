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

	//inner class
	public static class PathMaxPair{
		public int maxPathFromRoot;
		public int maxPathLeafToLeaf;
		
		public PathMaxPair(int a, int b){
			maxPathFromRoot = a;
			maxPathLeafToLeaf = b;
		}
		
		public int getMaxPathNoRoot(){
			return maxPathLeafToLeaf;
		}
		
		public int getMaxPathFromRoot(){
			return maxPathFromRoot;
		}
	}
	
	public binaryTree() {
		root = null;
		nodeCount = 0;
		highestPointForMaxPath = null;
	}

	public binaryTree(treeNode rootNode) {
		root = rootNode;
		lastInsertNode = root;
		nodeCount = 1;
	}

	public binaryTree(int rootValue) {
		root = new treeNode(rootValue);
		lastInsertNode = root;
		nodeCount = 1;
	}

	public binaryTree(int[] intArray) {
		for (int i = 0; i < intArray.length; i++) {
			// add into tree
			if (i == 0) {// fist node
				root = new treeNode(intArray[i]);
				lastInsertNode = root;
				nodeCount = 1;
			} else {
				addNodeBalanced(intArray[i]);
				nodeCount++;
			}
		}
	}

	/*
	 * Given preorder and inorder traversal of a tree, construct the binary
	 * tree.
	 */
	public binaryTree(int[] preorder, int[] inorder) {
		if (preorder.length == 0 || inorder.length == 0) {
			System.out.println("Neither parameter can't be empty array");
		}

		// the first node in preOrder is the tree root. Add it to the new tree
		// first;
		this.root = new treeNode(preorder[0]);

		// find its index in inorder array, anything to the left should belong
		// to left tree, wise versa;
		int curRootIndexInOrder = -1;
		for (int i = 0; i < inorder.length; i++) {
			if (inorder[i] == root.value) {
				curRootIndexInOrder = i;
				break;
			}
		}

		// get the mapping of in-order nodes' index in pre-order array
		int[] indexInPreOrder = new int[inorder.length];
		for (int i = 0; i < inorder.length; i++) {
			for (int j = 0; j < preorder.length; j++) {
				if (preorder[j] == inorder[i]) {
					indexInPreOrder[i] = j;
					//System.out.println("Mapping "+(i+1)+"--"+j);
					break;
				}
			}
		}
		//System.out.println();
		
		// add left tree
		addSubtree(root, true, inorder, 0, curRootIndexInOrder - 1,
				false, null, null, preorder, indexInPreOrder);
		// add right tree
		addSubtree(root, false, inorder, curRootIndexInOrder + 1, inorder.length - 1, 
				false, null, null, preorder, indexInPreOrder);
	}
	
	/*
	 * Given inorder and postorder traversal of a tree, construct the binary
	 * tree.
	 */
	public binaryTree(int[] inorder, int[] postorder, boolean flagPostorder) {
		if (inorder.length == 0 || postorder.length == 0) {
			System.out.println("Neither parameter can't be empty array");
		}

		// the last node in postOrder is the tree root. Add it to the new tree
		// first;
		this.root = new treeNode(postorder[postorder.length - 1]);

		// in in-order array, anything before root node should go into left
		// tree, anything after root node should go into right tree
		int curRootIndexInOrder = 0;
		for (int i = 0; i < inorder.length; i++) {
			if (inorder[i] == root.value) {
				curRootIndexInOrder = i;
				break;
			}
		}

		// get the index in postorder array of all nodes in order in inorder;
		int[] indexInPostorder = new int[inorder.length];
		for (int i = 0; i < inorder.length; i++) {
			for (int j = 0; j < postorder.length; j++) {
				if (postorder[j] == inorder[i]) {
					indexInPostorder[i] = j;
					break;
				}
			}
		}

		// add left tree
		addSubtree(root, true, inorder, 0, curRootIndexInOrder - 1, true,
				postorder, indexInPostorder, null, null);
		// add right tree
		addSubtree(root, false, inorder, curRootIndexInOrder + 1,
				inorder.length - 1, true, postorder, indexInPostorder, null,
				null);
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
	// Method 3: recurrsive, every node should have two value calculated. 
	// MaxFromRoot : max sum from root to leaf;
	// MaxNoRoot: max sum from leaf to leaf in subtree with curnode as root;
	public int maxPathSum3(treeNode root) {
        if(root == null) 
            return 0;
            
        if(root.leftLeaf == null && root.rightLeaf == null) 
            return root.value;

        PathMaxPair rootPair = maxPathPair(root);
		return rootPair.getMaxPathNoRoot();
	}
	
	public PathMaxPair maxPathPair(treeNode root){
	    if(root == null) {
			PathMaxPair curPair = new PathMaxPair(0,0);
			return curPair;
		}
			
		if(root.leftLeaf == null && root.rightLeaf == null) {
			int MaxFromCurRoot = root.value;
			int MaxLeafToLeaf = 0; 
			PathMaxPair curPair = new PathMaxPair(MaxFromCurRoot,MaxLeafToLeaf);
			return curPair;
		}
		
		int leftMaxFromRoot=0;
		int leftMaxLeaf = 0;
		int rightMaxFromRoot = 0;
		int rightMaxLeaf = 0;
		int MaxFromCurRoot = 0;
		int MaxLeafToLeaf = 0;
		
		if(root.leftLeaf != null) {
		    PathMaxPair leftPair = maxPathPair(root.leftLeaf);
		    leftMaxFromRoot = leftPair.getMaxPathFromRoot();
		    leftMaxLeaf = leftPair.getMaxPathNoRoot();
		}
		
		if(root.rightLeaf != null) {
		    PathMaxPair rightPair = maxPathPair(root.rightLeaf);
	    	rightMaxFromRoot = rightPair.getMaxPathFromRoot();
		    rightMaxLeaf = rightPair.getMaxPathNoRoot();
		}
		
		MaxFromCurRoot  = root.value;
		if(leftMaxFromRoot > 0 && rightMaxFromRoot > 0) {
		    MaxFromCurRoot =  leftMaxFromRoot > rightMaxFromRoot ? 
				(leftMaxFromRoot+root.value):(rightMaxFromRoot+root.value);
		} else if(leftMaxFromRoot > 0 && rightMaxFromRoot <= 0) {
		    MaxFromCurRoot = leftMaxFromRoot+root.value;
		} else if(leftMaxFromRoot <= 0 && rightMaxFromRoot > 0) {
		    MaxFromCurRoot = rightMaxFromRoot+root.value;
		}
				
		//from left tree to right tree passing curNode
		int curLeafMax = root.value;
		if(leftMaxFromRoot > 0)
			curLeafMax += leftMaxFromRoot;
		if(rightMaxFromRoot > 0)
			curLeafMax += rightMaxFromRoot;
		
		//max of the three is the new max for leaf to leaf from curRoot;
		MaxLeafToLeaf = curLeafMax; 
		if(root.leftLeaf != null && leftMaxLeaf > MaxLeafToLeaf)
			MaxLeafToLeaf = leftMaxLeaf;
		if(root.rightLeaf != null && rightMaxLeaf > MaxLeafToLeaf)
			MaxLeafToLeaf = rightMaxLeaf;
				
		PathMaxPair curPair = new PathMaxPair(MaxFromCurRoot,MaxLeafToLeaf);
		return curPair;
	}
	
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

			if (nextLevelNode.isEmpty()) { // finish traversing tree
				break;
			} else {
				listLevelNodes.add(nextLevelNode);
				listLevelValues.add(nextLevelValue);
				curLevel++;
			}
		}

		return listLevelValues;
	}

	/*
	 * Given a binary tree, return the bottom-up level order traversal of its
	 * nodes' values. (ie, from left to right, level by level from leaf to
	 * root).
	 */
	public ArrayList<ArrayList<Integer>> levelOrderBottom(treeNode root) {
		ArrayList<ArrayList<treeNode>> listLevelNodes = new ArrayList<ArrayList<treeNode>>();
		ArrayList<ArrayList<Integer>> listLevelValuesBottomUp = new ArrayList<ArrayList<Integer>>();

		if (root == null)
			return listLevelValuesBottomUp;

		ArrayList<treeNode> newList = new ArrayList<treeNode>();
		newList.add(root);
		listLevelNodes.add(newList);
		int curLevel = 0;

		treeNode curNode = null;
		while (true) { // for each level
			newList = new ArrayList<treeNode>();

			for (int i = 0; i < listLevelNodes.get(curLevel).size(); i++) {
				curNode = listLevelNodes.get(curLevel).get(i);
				if (curNode.leftLeaf != null)
					newList.add(curNode.leftLeaf);
				if (curNode.rightLeaf != null)
					newList.add(curNode.rightLeaf);
			}

			if (newList.isEmpty()) {
				break; // end of tree traversal
			} else {
				listLevelNodes.add(newList);
				curLevel++;
			}
		}

		// set the values from bottom up in the new array array;
		for (int nodeLevel = listLevelNodes.size() - 1; nodeLevel >= 0; nodeLevel--) {
			ArrayList<Integer> valueList = new ArrayList<Integer>();

			for (int j = 0; j < listLevelNodes.get(nodeLevel).size(); j++) { // j
																				// is
																				// nodes
																				// in
																				// the
																				// same
																				// level
				curNode = listLevelNodes.get(nodeLevel).get(j);
				valueList.add(new Integer(curNode.value));
			}

			listLevelValuesBottomUp.add(valueList);
		}

		return listLevelValuesBottomUp;
	}

	/*
	 * Given a binary tree, return the zigzag level order traversal of its
	 * nodes' values. (ie, from left to right, then right to left for the next
	 * level and alternate between).
	 * 
	 * For example: Given binary tree {3,9,20,#,#,15,7},return its zigzag level
	 * order traversal as: [ [3], [20,9], [15,7] ]
	 */
	public ArrayList<ArrayList<Integer>> levelOrderZigzag(treeNode root) {
		ArrayList<ArrayList<treeNode>> listLevelNodes = new ArrayList<ArrayList<treeNode>>();
		ArrayList<ArrayList<Integer>> listLevelValuesZigzag = new ArrayList<ArrayList<Integer>>();

		if (root == null)
			return listLevelValuesZigzag;

		ArrayList<treeNode> newList = new ArrayList<treeNode>();
		newList.add(root);
		listLevelNodes.add(newList);
		int curLevel = 0;

		treeNode curNode = null;
		while (true) { // for each level
			newList = new ArrayList<treeNode>();

			for (int i = 0; i < listLevelNodes.get(curLevel).size(); i++) {
				curNode = listLevelNodes.get(curLevel).get(i);
				if (curNode.leftLeaf != null)
					newList.add(curNode.leftLeaf);
				if (curNode.rightLeaf != null)
					newList.add(curNode.rightLeaf);
			}

			if (newList.isEmpty()) {
				break; // end of tree traversal
			} else {
				listLevelNodes.add(newList);
				curLevel++;
			}
		}

		// set the values from bottom up in the new array array in zigzag
		// fashion
		for (int nodeLevel = 0; nodeLevel < listLevelNodes.size(); nodeLevel++) {
			ArrayList<Integer> valueList = new ArrayList<Integer>();

			if (nodeLevel % 2 == 0) { // even levels, left -> right
				for (int j = 0; j < listLevelNodes.get(nodeLevel).size(); j++) { // j
																					// is
																					// nodes
																					// in
																					// the
																					// same
																					// level
					curNode = listLevelNodes.get(nodeLevel).get(j);
					valueList.add(new Integer(curNode.value));
				}
			} else {// odd levels, right->left
				for (int j = listLevelNodes.get(nodeLevel).size() - 1; j >= 0; j--) {
					curNode = listLevelNodes.get(nodeLevel).get(j);
					valueList.add(new Integer(curNode.value));
				}
			}

			listLevelValuesZigzag.add(valueList);
		}

		return listLevelValuesZigzag;
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

	// add the subtree's root to curRoot.
	// if it's to add left, tree, leftflag is true. otherwise it's fall.
	public void addSubtree(treeNode curRoot, boolean leftFlag, int[] inorder,
			int start_inorder, int end_inorder, boolean postOrderFlag,
			int[] postorder, int[] indexInPostOrder, int[] preorder,
			int[] indexInPreOrder) {
		if (leftFlag)
			System.out.println("CurRoot " + curRoot.value + " left subtree :"
					+ start_inorder + "--" + end_inorder);
		else
			System.out.println("CurRoot " + curRoot.value + " right subtree :"
					+ start_inorder + "--" + end_inorder);

		int subTreeNodeCount = end_inorder - start_inorder + 1;

		if (subTreeNodeCount <= 0) {
			return;
		}

		treeNode subRoot = new treeNode();
		if (subTreeNodeCount == 1) {// only one node in this tree, then it's
									// root
			subRoot.value = inorder[start_inorder];
			subRoot.parent = curRoot;
			if (leftFlag)
				curRoot.leftLeaf = subRoot;
			else
				curRoot.rightLeaf = subRoot;
			return;
		}

		// get the subtreeRoot's value. in all nodes in current inorder sets
		// [start_inorder,end_inorder], whomever is the rightmost in post order
		// array is the subtree root;
		int subtreeRootIndex = -1;
		if (postOrderFlag) {
			for (int i = start_inorder; i <= end_inorder; i++) {
				if (indexInPostOrder[i] > subtreeRootIndex) {
					subtreeRootIndex = indexInPostOrder[i];
				}
			}
			subRoot.value = postorder[subtreeRootIndex];
		} else { // whomever is the leftmost in pre order array is the subtree
					// root;
			subtreeRootIndex = preorder.length-1;
			for (int i = start_inorder; i <= end_inorder; i++) {
				if (indexInPreOrder[i] < subtreeRootIndex) {
					subtreeRootIndex = indexInPreOrder[i];
				}
			}
			subRoot.value = preorder[subtreeRootIndex];
		}

		subRoot.parent = curRoot;
		if (leftFlag)
			curRoot.leftLeaf = subRoot;
		else
			curRoot.rightLeaf = subRoot;

		int indexInOrder = 0;
		for (int i = start_inorder; i <= end_inorder; i++) {
			if (inorder[i] == subRoot.value) {
				indexInOrder = i;
				break;
			}
		}

		addSubtree(subRoot, true, inorder, start_inorder, indexInOrder - 1,
				postOrderFlag, postorder, indexInPostOrder, preorder, indexInPreOrder);
		addSubtree(subRoot, false, inorder, indexInOrder + 1, end_inorder,
				postOrderFlag, postorder, indexInPostOrder, preorder, indexInPreOrder);

		return;
	}

	/*Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).*/
	public boolean isSymmetric(treeNode root) {
        if(root == null)
            return true;
        
        return treesSymmetric(root.leftLeaf,root.rightLeaf);
    }
    
    public boolean treesSymmetric(treeNode leftRoot, treeNode rightRoot){
        if (leftRoot == null && rightRoot == null)
            return true;
        
        if (leftRoot == null || rightRoot == null)
            return false;
            
        return ( leftRoot.value == rightRoot.value && 
        treesSymmetric(leftRoot.rightLeaf, rightRoot.leftLeaf) && 
        treesSymmetric(leftRoot.leftLeaf, rightRoot.rightLeaf));
    }
	
    public boolean isSymmetricIterative(treeNode root) {
    	if(root == null || (root.leftLeaf == null && root.rightLeaf == null))
    		return true;
    	
    	Stack<treeNode> tmp = new Stack<treeNode>();
    	ArrayList<treeNode> inorder = new ArrayList<treeNode>();
    	
    	//push root into stack;
    	tmp.push(root);
    	
    	treeNode curNode = null;
    	do {
    		curNode = tmp.peek();
    		if(curNode.leftLeaf != null && inorder.contains(curNode.leftLeaf) == false) {
    			tmp.push(curNode.leftLeaf);
    		}
    		else {
    			curNode = tmp.pop();
    			inorder.add(curNode);
    			if(curNode.rightLeaf != null)
    				tmp.push(curNode.rightLeaf);
    		}
    	} while(!tmp.isEmpty());
    	
    	//the array should have in-order nodes, check if this array is symmetric; 
    	int rootIndex = inorder.indexOf(root);
    	if(rootIndex == 0 || rootIndex == inorder.size()-1) //root is on one side and more than 2 nodes in the tree
    		return false; 
    	
    	for(int i=0,j=inorder.size()-1;i<rootIndex && j> rootIndex;i++,j--){
    		//System.out.println("data ["+i+"] is "+inorder.get(i).value);
    		//System.out.println("data ["+j+"] is "+inorder.get(j).value);
    		if(subTreeSymmetric(inorder.get(i),inorder.get(j)) == false)
    			return false;
    	}
    	
    	return true;
    }
    
    public boolean subTreeSymmetric(treeNode leftRoot, treeNode rightRoot){
        if(leftRoot.value != rightRoot.value)
            return false;
            
        if((leftRoot.leftLeaf == null && rightRoot.rightLeaf != null ) ||
            (leftRoot.leftLeaf !=null && rightRoot.rightLeaf == null ) ||
            (leftRoot.rightLeaf == null && rightRoot.leftLeaf != null ) ||
            (leftRoot.rightLeaf !=null && rightRoot.leftLeaf == null))
            return false;
            
        if((leftRoot.leftLeaf != null && leftRoot.leftLeaf.value != rightRoot.rightLeaf.value) || 
           (leftRoot.rightLeaf != null && leftRoot.rightLeaf.value != rightRoot.leftLeaf.value))
           return false;
           
        return true;
    }
}