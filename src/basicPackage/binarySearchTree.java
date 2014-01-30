package basicPackage;

import java.util.ArrayList;

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

	/*Given n, how many structurally unique BST's (binary search trees) that store values 1...n?
	For example,
	Given n = 3, there are a total of 5 unique BST's.*/
	public int numTrees(int n) {
		if(n <= 0)
			return 0;
		
		if(n == 1)
			return 1;
		
		int result = 0 ;
		
		for(int i = 1;i <= n; i++) {
			if( i == 1 || i == n )
				result += numTrees(n-1);
			else
				result += numTrees(i-1) * numTrees(n-i) ;
		}
	
		return result;
	}
	
	//Based on numTrees, return all these trees;
	/*Given n, generate all structurally unique BST's (binary search trees) that store values 1...n.
	For example,
	Given n = 3, your program should return all 5 unique BST's shown below.*/
	public ArrayList<treeNode> generateTrees(int n){
		if(n == 0)
			return null;
		
		return generateSubTree(1,n);
	}
	
	//this function should return the root array of all possible subLeftTree
	public ArrayList<treeNode> generateSubTree(int subtree_start, int subtree_end){
		ArrayList<treeNode> subTreeRoots = new ArrayList<treeNode>();
		
		if(subtree_end < subtree_start)
			return null;
		
		if(subtree_end == subtree_start) {
			treeNode subRoot = new treeNode(subtree_start);
			subTreeRoots.add(subRoot);
			return subTreeRoots;
		}
		
		for(int i=subtree_start;i<=subtree_end;i++) {
			ArrayList<treeNode> leftTreeRoots = generateSubTree(subtree_start,i-1);
			ArrayList<treeNode> rightTreeRoots = generateSubTree(i+1,subtree_end);

			//only left subtree
			if(leftTreeRoots != null && rightTreeRoots == null) {
				for(int j = 0;j<leftTreeRoots.size();j++) {
					treeNode subRoot = new treeNode(i);
					subRoot.leftLeaf = leftTreeRoots.get(j);
					subTreeRoots.add(subRoot);
				}
			} else if(leftTreeRoots == null && rightTreeRoots != null){ //only right subtree
				for(int j = 0;j<rightTreeRoots.size();j++) {
					treeNode subRoot = new treeNode(i);
					subRoot.rightLeaf = rightTreeRoots.get(j);
					subTreeRoots.add(subRoot);
				}
			} else { // left and right subtree both exists, all combinations
				for(int j = 0;j<leftTreeRoots.size();j++) {
					for(int k = 0;k<rightTreeRoots.size();k++) {
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
}