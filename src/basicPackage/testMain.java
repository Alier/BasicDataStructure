package basicPackage;

public class testMain {
	public static void main(String[] args) {
		// print
		// for (String a : obj.getMutations()) {
		// System.out.println(a);
		//circularLinkedList list = new circularLinkedList(charArray);
		//System.out.println(list.findCircleStart());
		
		/* for HanoiTowers */
		/*int towerHeight = 5;
		hanoiTowers newTower = new hanoiTowers(towerHeight);
		System.out.println("Original Stack:");
		newTower.printStack(newTower.stack1);
		newTower.fromStackToStack(1, 5, 1, 3);
		
		System.out.println("New Stack:");
		newTower.printStack(newTower.stack3);*/
		
		/*int[] intArray = new int[]{1,2,3,4,5,6};
		stackOps newObj = new stackOps(intArray);
		newObj.printStack(newObj.forSort);
		System.out.println("After sorting: ");
		newObj.printStack(newObj.sortStack(newObj.forSort));*/
		
		//for binaryTree
		//int[] intArray = new int[]{7,3,4,5,6,2,8,11,10,12,13}; //-- for testing balanced. false
		//int[] intArray = new int[]{4,2,1,3,7,6,5,8,9,10};
//		int[] inorder = new int[]{1,2,3,4,5,6,7,8,9,10};
//		int[] postorder = new int[]{1,3,2,5,6,10,9,8,7,4};
//		int[] preorder = new int[]{4,2,1,3,7,6,5,8,9,10};
		//int[] intArray2 = new int[]{1,2,3,6,10,8,5,4,7,9};
		//int[] intArray2=new int[]{2,1,3};
		//int sum = 18;
		//int[] path = new int[intArray2.length];
		binaryTree newTree = new binaryTree(new int[]{1,2,2,1,3,3,0});
		
		//binaryTree newTree = new binaryTree(inorder, postorder,true);
		//binaryTree newTree2 = new binaryTree(preorder,inorder);
		//binarySearchTree newTree = new binarySearchTree(intArray);
//		newTree.printNodesByLevel(newTree.levelOrder(newTree.getRoot()));
//		System.out.println();
//		newTree.printNodesByLevel(newTree.levelOrderBottom(newTree.getRoot()));
//		System.out.println();
//		newTree.printNodesByLevel(newTree.levelOrderZigzag(newTree.getRoot()));
		//System.out.println(newTree2.maxPathSum3(newTree2.getRoot()));
		System.out.println("======In-order Tree1======= node count:"+newTree.nodeCount);
		newTree.printTree(newTree.getRoot(),1);
		System.out.println();
//		System.out.println("======In-order Tree2======= node count:"+newTree2.nodeCount);
//		newTree2.printTree(newTree2.getRoot(),1);
//		System.out.println();
		System.out.println("======Pre-order Tree1======= node count:"+newTree.nodeCount);
		newTree.printTree(newTree.getRoot(),2);
		System.out.println();
		System.out.println(newTree.isSymmetricIterative(newTree.getRoot()));
//		System.out.println("======Po-order Tree2======= node count:"+newTree2.nodeCount);
//		newTree2.printTree(newTree2.getRoot(),2);
//		System.out.println();
//		System.out.println("======Post-order Tree1======= node count:"+newTree.nodeCount);
//		newTree.printTree(newTree.getRoot(),3);
//		System.out.println();
//		System.out.println("======Post-order Tree2======= node count:"+newTree2.nodeCount);
//		newTree2.printTree(newTree2.getRoot(),3);
//		System.out.println();

//		newTree.printNodeArray(newTree.postorderTraversal(newTree.getRoot()));
//		HashMap<treeNode, Integer> nodeDepthes = new HashMap<treeNode, Integer>();
//		System.out.println("\nisBalanced ? "+newTree.isBalanced(newTree.getRoot(),nodeDepthes));
//		System.out.println("Count :"+binaryTree.count);
//		System.out.println("\nisBalanced ? "+newTree.isBalancedNoStorage(newTree.getRoot()));
//		System.out.println("Count2 :"+binaryTree.count2);
//		System.out.println("\nisBalanced ? "+newTree.isBalancedIterally(newTree.getRoot()));
//		System.out.println("Count3 :"+binaryTree.count3);
		//System.out.println("has pair sum at "+sum+" ? "+newTree.nodesWithSum(sum, newTree.root));
		//System.out.println("Running binarySearch : "+binarySearchTree.runCount);
		System.out.println();
		//System.out.println("minDepth: "+newTree.minDepth(newTree.getRoot()));
		//System.out.println("maxDepth: "+newTree.maxDepth(newTree.getRoot()));

		//System.out.println("maxPathSum: "+newTree.maxPathSum(newTree.getRoot()));
		//System.out.println("maxPathSum: "+newTree.maxPathSum2(newTree.getRoot()));
		//newTree.addToPath(7,newTree.getRoot(), path,0);
		//System.out.println("running times:"+binaryTree.runCount);
//		System.out.println("\n======Pre-order=======");
//		newTree.printTree(newTree.getRoot(),2);
//		System.out.println("\n======Post-order=======");
//		newTree.printTree(newTree.getRoot(),3);
	}
}
