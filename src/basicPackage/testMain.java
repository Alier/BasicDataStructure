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
		int[] intArray = new int[]{7,4,13,2,6,10,14,1,3,5,11,12,8,9,15};
		int sum = 18;
		//int[] path = new int[intArray.length];
		//binaryTree newTree = new binaryTree(intArray);
		binarySearchTree newTree = new binarySearchTree(intArray);
		System.out.println("======In-order======= node count:"+newTree.nodeCount);
		newTree.printTree(newTree.getRoot(),1);
		System.out.println("\nisBST ? "+newTree.isBST());
		System.out.println("has pair sum at "+sum+" ? "+newTree.nodesWithSum(sum, newTree.root));
		System.out.println("Running binarySearch : "+binarySearchTree.runCount);
		System.out.println("minDepth: "+newTree.minDepth(newTree.getRoot()));
		System.out.println("maxPathSum: "+newTree.maxPathSum(newTree.getRoot()));
		//newTree.addToPath(7,newTree.getRoot(), path,0);
		//System.out.println("running times:"+binaryTree.runCount);
//		System.out.println("\n======Pre-order=======");
//		newTree.printTree(newTree.getRoot(),2);
//		System.out.println("\n======Post-order=======");
//		newTree.printTree(newTree.getRoot(),3);
	}
}
