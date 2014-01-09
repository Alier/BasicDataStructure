package basicPackage;

public class binarySearchTree extends binaryTree {
	public static int runCount ;
	
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
				addNodeSorted(new treeNode(intArray[i]), root);
				nodeCount++;
			}
		}
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
		
		//find the closet number larger than sum
		for (int i = 0; i < newArray.length; i++) {
			if(newArray[i] > sum) {
				indexClosetLarger = i;
				break;
			}
		}

		//the smallest node i==0 or the 2nd smallest node i==1 is already bigger than sum
		if( indexClosetLarger <= 1)  return false;

		//start from biggest which is still smaller than sum, find from 0 for the match
		for(int j = indexClosetLarger -1; j > 0 && lowIndex < j; j-- ) {
			matchIndex = this.binarySearch(sum-newArray[j], newArray, lowIndex, j-1);
			if(matchIndex>=0) {//find match
				System.out.println("One pair: ("+newArray[matchIndex]+","+newArray[j]+")");
				matchCount++;
				lowIndex = matchIndex+1;
			} 
		}
		
		if(matchCount > 0)
			return true;
		
		return false;
	}

	// given a value, find the index of the node that has the value in the array
	// from lowIndex to highIndex, return index, since the array is sorted, we
	// can do binary search for performance
	public int binarySearch(int value, int[] array, int lowIndex, int highIndex) {
		if(value == 0) return -1;
		
		runCount ++;
		if(lowIndex == highIndex) {
			if(value == array[lowIndex])
				return lowIndex;
			else 
				return -1;
		}
		
		int midIndex = (lowIndex + highIndex)/2;
		//System.out.println("Looking in ("+array[lowIndex]+","+array[highIndex]+") midIndex="+array[midIndex]);
		
		if(value < array[midIndex]) //in left half
			return binarySearch(value,array, lowIndex,midIndex-1);
		if(value > array[midIndex]) //in right half
			return binarySearch(value,array,midIndex+1,highIndex);
		if(value == array[midIndex])
			return midIndex;
		
		return -1;
	}

}