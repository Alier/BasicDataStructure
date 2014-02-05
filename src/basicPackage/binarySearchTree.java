package basicPackage;

import java.util.ArrayList;

public class binarySearchTree extends binaryTree {
	public static int runCount;

	public binarySearchTree() {
		super();
	}

	public binarySearchTree(int rootValue) {
		super(rootValue);
	}

	public binarySearchTree(int[] preorder, int[] inorder) {
		super(preorder, inorder);
	}

	public binarySearchTree(int[] inorder, int[] postorder,
			boolean flagPostorder) {
		super(inorder, postorder, flagPostorder);
	}

	public binarySearchTree(int[] intArray) {
		for (int i = 0; i < intArray.length; i++) {
			// add into tree
			if (i == 0) {// first node
				root = new treeNode(intArray[i]);
				lastInsertNode = root;
				nodeCount = 1;
			} else {
				addNodeSorted(new treeNode(intArray[i]), root);
				nodeCount++;
			}
		}
	}

	/*
	 * Given a singly linked list where elements are sorted in ascending order,
	 * convert it to a height balanced BST.
	 */
	public binarySearchTree(ListNode head) {
		// put all nodes in an arrayList with originally ascending order;
		ArrayList<Integer> nodeList = new ArrayList<Integer>();

		ListNode p = head;
		while (p != null) {
			nodeList.add(new Integer(p.val));
			p = p.next;
		}

		root = generateBalancedBST(nodeList, 0, nodeList.size() - 1);
	}

	/*
	 * Given an array where elements are sorted in ascending order, convert it
	 * to a height balanced BST.
	 */
	public treeNode sortedArrayToBST(int[] num) {
		// put all nodes in an arrayList with originally ascending order;
		ArrayList<Integer> nodeList = new ArrayList<Integer>();

		for (int i = 0; i < num.length; i++) {
			nodeList.add(new Integer(num[i]));
		}

		return generateBalancedBST(nodeList, 0, nodeList.size() - 1);
	}

	// form a balancedBST with nodes whose values are asending order in nodeList
	// from startIndex to endIndex
	// return the balancedBST's rootNode.
	public treeNode generateBalancedBST(ArrayList<Integer> nodeList,
			int startIndex, int endIndex) {
		if (startIndex > endIndex) {
			return null;
		}

		if (startIndex == endIndex) {
			treeNode root = new treeNode(nodeList.get(startIndex));
			return root;
		}

		int rootIndex = (startIndex + endIndex) / 2;
		treeNode root = new treeNode(nodeList.get(rootIndex));
		root.leftLeaf = generateBalancedBST(nodeList, startIndex, rootIndex - 1);
		root.rightLeaf = generateBalancedBST(nodeList, rootIndex + 1, endIndex);

		return root;
	}
	
	// add node in a fashion to form a BST !!!!!!!!! INCOMPLETE !!!!!!!!!
	public void addNodeSorted(treeNode newNode, treeNode curRoot) {
		if (newNode.value < curRoot.value) {// add to left child tree
			if (curRoot.leftLeaf != null) {
				addNodeSorted(newNode, curRoot.leftLeaf);
			} else {
				curRoot.leftLeaf = newNode;
				newNode.parent = curRoot;
			}
		}

		if (newNode.value > curRoot.value) { // add to right child tree
			if (curRoot.rightLeaf != null) {
				addNodeSorted(newNode, curRoot.rightLeaf);
			} else {
				curRoot.rightLeaf = newNode;
				newNode.parent = curRoot;
			}
		}
	}

	/*
	 * No. 46 - Nodes with Sum in a Binary Search Tree Given a binary search
	 * tree, please check whether there are two nodes in it whose sum equals a
	 * given value.
	 */

	// method 1: go over the tree once, print into an array in in-order , so
	// it's in-order. then operate on the array
	public boolean nodesWithSum(int sum, treeNode root) {
		int[] newArray = new int[this.nodeCount];
		addToArray(this.root, newArray, 0);
		int indexClosetLarger = newArray.length;
		int lowIndex = 0;
		int matchIndex = -1;
		int matchCount = 0;

		// find the closet number larger than sum
		for (int i = 0; i < newArray.length; i++) {
			if (newArray[i] > sum) {
				indexClosetLarger = i;
				break;
			}
		}

		// the smallest node i==0 or the 2nd smallest node i==1 is already
		// bigger than sum
		if (indexClosetLarger <= 1)
			return false;

		// start from biggest which is still smaller than sum, find from 0 for
		// the match
		for (int j = indexClosetLarger - 1; j > 0 && lowIndex < j; j--) {
			matchIndex = this.binarySearch(sum - newArray[j], newArray,
					lowIndex, j - 1);
			if (matchIndex >= 0) {// find match
				System.out.println("One pair: (" + newArray[matchIndex] + ","
						+ newArray[j] + ")");
				matchCount++;
				lowIndex = matchIndex + 1;
			}
		}

		if (matchCount > 0)
			return true;

		return false;
	}

	// given a value, find the index of the node that has the value in the array
	// from lowIndex to highIndex, return index, since the array is sorted, we
	// can do binary search for performance
	public int binarySearch(int value, int[] array, int lowIndex, int highIndex) {
		if (value == 0)
			return -1;

		runCount++;
		if (lowIndex == highIndex) {
			if (value == array[lowIndex])
				return lowIndex;
			else
				return -1;
		}

		int midIndex = (lowIndex + highIndex) / 2;
		// System.out.println("Looking in ("+array[lowIndex]+","+array[highIndex]+") midIndex="+array[midIndex]);

		if (value < array[midIndex]) // in left half
			return binarySearch(value, array, lowIndex, midIndex - 1);
		if (value > array[midIndex]) // in right half
			return binarySearch(value, array, midIndex + 1, highIndex);
		if (value == array[midIndex])
			return midIndex;

		return -1;
	}

	/*
	 * Given n, how many structurally unique BST's (binary search trees) that
	 * store values 1...n? For example, Given n = 3, there are a total of 5
	 * unique BST's.
	 */
	public int numTrees(int n) {
		if (n <= 0)
			return 0;

		if (n == 1)
			return 1;

		int result = 0;

		for (int i = 1; i <= n; i++) {
			if (i == 1 || i == n)
				result += numTrees(n - 1);
			else
				result += numTrees(i - 1) * numTrees(n - i);
		}

		return result;
	}

	// Based on numTrees, return all these trees;
	/*
	 * Given n, generate all structurally unique BST's (binary search trees)
	 * that store values 1...n. For example, Given n = 3, your program should
	 * return all 5 unique BST's shown below.
	 */
	public ArrayList<treeNode> generateTrees(int n) {
		if (n == 0) {
			ArrayList<treeNode> roots = new ArrayList<treeNode>();
			roots.add(null);
			return roots;
		}

		return generateSubTree(1, n);
	}

	// this function should return the root array of all possible subLeftTree
	public ArrayList<treeNode> generateSubTree(int subtree_start,
			int subtree_end) {
		ArrayList<treeNode> subTreeRoots = new ArrayList<treeNode>();

		if (subtree_end < subtree_start)
			return subTreeRoots;

		if (subtree_end == subtree_start) {
			treeNode subRoot = new treeNode(subtree_start);
			subTreeRoots.add(subRoot);
			return subTreeRoots;
		}

		for (int i = subtree_start; i <= subtree_end; i++) {
			ArrayList<treeNode> leftTreeRoots = generateSubTree(subtree_start,
					i - 1);
			ArrayList<treeNode> rightTreeRoots = generateSubTree(i + 1,
					subtree_end);

			// only left subtree
			if (leftTreeRoots.size() != 0 && rightTreeRoots.size() == 0) {
				for (int j = 0; j < leftTreeRoots.size(); j++) {
					treeNode subRoot = new treeNode(i);
					subRoot.leftLeaf = leftTreeRoots.get(j);
					subTreeRoots.add(subRoot);
				}
			} else if (leftTreeRoots.size() == 0 && rightTreeRoots.size() != 0) { // only
																					// right
																					// subtree
				for (int j = 0; j < rightTreeRoots.size(); j++) {
					treeNode subRoot = new treeNode(i);
					subRoot.rightLeaf = rightTreeRoots.get(j);
					subTreeRoots.add(subRoot);
				}
			} else { // left and right subtree both exists, all combinations
				for (int j = 0; j < leftTreeRoots.size(); j++) {
					for (int k = 0; k < rightTreeRoots.size(); k++) {
						treeNode subRoot = new treeNode(i);
						subRoot.leftLeaf = leftTreeRoots.get(j);
						subRoot.rightLeaf = rightTreeRoots.get(k);
						subTreeRoots.add(subRoot);
					}
				}
			}
		}

		return subTreeRoots;
	}

	/*
	 * Two elements of a binary search tree (BST) are swapped by mistake.
	 * Recover the tree without changing its structure. Note: A solution using
	 * O(n) space is pretty straight forward. Could you devise a constant space
	 * solution?
	 */
	public void recoverTree(treeNode root) {
		// the method is very easy. If the tree has no swap, it should have
		// rightMin > root > leftMax
		/*
		 * case 1: if a node in lefttree swapped with root, then the symptom is
		 * : rightMin > leftMax > root solution: swap leftMax <-> root to
		 * recover; case 2: if a node in righttree swapped with root, then the
		 * symptom is : root > rightMin > leftMax solution: swap rightMin <->
		 * root ; case 3: if a node in lefttree swapped with a node in right
		 * tree, symptom is: leftMax > root > rightMin solution: swap leftMax
		 * <-> rightMin;
		 */
		// Do this recursively.

		if (root == null || (root.leftLeaf == null && root.rightLeaf == null))// no
																				// swap
																				// needed
			return;

		treeNode leftMax = getMaxNode(root.leftLeaf);
		treeNode rightMin = getMinNode(root.rightLeaf);

		// only left tree .
		if (leftMax != null && rightMin == null) {
			if (leftMax.value > root.value) {
				// swap leftMax and root;
				swapNodes(leftMax, root);
			} else {
				recoverTree(root.leftLeaf);
			}
			return;
		}

		// only right tree
		if (leftMax == null && rightMin != null) {
			if (rightMin.value < root.value) {
				// swap rightMin and root;
				swapNodes(rightMin, root);
			} else {
				recoverTree(root.rightLeaf);
			}
			return;
		}

		// both tree exist
		// case 1: rightMin > leftMax > root , swap root and leftMax.
		if (rightMin.value > leftMax.value && leftMax.value > root.value) {
			swapNodes(root, leftMax);
			return;
		}

		// case 2: root > rightMin > leftMax , swap root and rightMin.
		if (root.value > rightMin.value && rightMin.value > leftMax.value) {
			swapNodes(root, rightMin);
			return;
		}

		// case 3: leftMax > root > rightMin, swap leftMax and rightMin.
		if (leftMax.value > root.value && root.value > rightMin.value) {
			swapNodes(leftMax, rightMin);
			return;
		}

		// Root is fine, recover each subtree;
		recoverTree(root.leftLeaf);
		recoverTree(root.rightLeaf);
	}

	public void swapNodes(treeNode a, treeNode b) {
		int tempValue = a.value;

		a.value = b.value;
		b.value = tempValue;
	}

	public treeNode getMaxNode(treeNode root) {
		if (root == null)
			return null;

		treeNode leftMax = null;
		treeNode rightMax = null;
		treeNode maxNode = root;

		if (root.leftLeaf != null) {
			leftMax = getMaxNode(root.leftLeaf);
			if (leftMax.value > maxNode.value) {
				maxNode = leftMax;
			}
		}
		if (root.rightLeaf != null) {
			rightMax = getMaxNode(root.rightLeaf);
			if (rightMax.value > maxNode.value) {
				maxNode = rightMax;
			}
		}

		return maxNode;
	}

	public treeNode getMinNode(treeNode root) {
		if (root == null)
			return null;

		treeNode leftMax = null;
		treeNode rightMax = null;
		treeNode minNode = root;

		if (root.leftLeaf != null) {
			leftMax = getMinNode(root.leftLeaf);
			if (leftMax.value < minNode.value) {
				minNode = leftMax;
			}
		}
		if (root.rightLeaf != null) {
			rightMax = getMinNode(root.rightLeaf);
			if (rightMax.value < minNode.value) {
				minNode = rightMax;
			}
		}

		return minNode;
	}
}