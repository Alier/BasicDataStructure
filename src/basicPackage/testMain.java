package basicPackage;

public class testMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		int[] inorder = new int[]{1,2,3,4,5,6,7,8,9,10};
//		int[] postorder = new int[]{1,3,2,5,6,10,9,8,7,4};
//		int[] preorder = new int[]{4,2,1,3,7,6,5,8,9,10};
		
		binaryTree newTree = new binaryTree(new int[]{1,2,2,1,3,3,0});

		//binaryTree newTree = new binaryTree(inorder, postorder,true);
		//binaryTree newTree2 = new binaryTree(preorder,inorder);
		//binarySearchTree newTree = new binarySearchTree(intArray);
		
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
		
	}

}
