package basicPackage;

import java.util.ArrayList;

public class singleLinkedList {
	ListNode head;
	
	public singleLinkedList(){
		head = null;
	}
	
	public singleLinkedList(int rootVal){
		head = new ListNode(rootVal);
	}
	
	public class ListNode {
		int val;
		ListNode next;
		ListNode(int x) {
			val = x;
			next = null;
		}
	}
	
	/*
	 * Given a sorted linked list, delete all duplicates such that each element
	 * appear only once.
	 * 
	 * For example, Given 1->1->2, return 1->2. Given 1->1->2->3->3, return
	 * 1->2->3.
	 */
	public ListNode deleteDuplicates(ListNode head) {
        ArrayList<Integer> visited = new ArrayList<Integer>();
        
        if(head == null) return null;
        
        ListNode p = head;
        visited.add(new Integer(head.val));
        
        while(p != null && p.next != null) {
            if(visited.contains(new Integer(p.next.val))){
                //remove p.next
                p.next = p.next.next;
            }else {
                visited.add(new Integer(p.next.val));
                p = p.next;
            }
        }
        
        return head;
    }
	
}
