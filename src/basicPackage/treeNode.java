package basicPackage;

public class treeNode {
	int value;
	treeNode parent;
	treeNode leftLeaf;
	treeNode rightLeaf;
	
	public treeNode(int itemValue){
		value = itemValue;
		parent = null;
		leftLeaf = null;
		rightLeaf = null;
	}
	
	public treeNode(){
		value = -1;
		parent = null;
		leftLeaf = null;
		rightLeaf = null;
	}
	
	public treeNode(treeNode orig){
		value = orig.value;
		parent = orig.parent;
		leftLeaf = orig.leftLeaf;
		rightLeaf = orig.rightLeaf;
	}
}
