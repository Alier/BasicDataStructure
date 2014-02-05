package basicPackage;

import java.util.ArrayList;

	public class Solution {
		public Solution(){
			super();
		}
		/*
		 * Given two integers n and k, return all possible combinations of k numbers
		 * out of 1 ... n.
		 * 
		 * For example, If n = 4 and k = 2, a solution is:
		 * 
		 * [ [2,4], [3,4], [2,3], [1,2], [1,3], [1,4], ]
		 */
		public ArrayList<ArrayList<Integer>> combine(int n, int k) {
	        return rangeCombine(1,n,k);
	    }
	    
		//return combination of picking any k data from startN to endN.
	    public ArrayList<ArrayList<Integer>> rangeCombine(int startN, int endN, int k) {
	        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
	        int countInRange = endN-startN+1;
	        
	        if(startN > endN || countInRange < k)
	            return result;
	    
	        //count in range same as k, only one comb
	        if(countInRange == k){
	        	ArrayList<Integer> newList = new ArrayList<Integer>();
	        	for(int i=startN;i<=endN;i++){
	        		newList.add(new Integer(i));
	        	}
	        	result.add(newList);
	        	return result;
	        }
	       
	        //choose one, then every data in range should form one list
	        if(k == 1){
	        	for(int i=startN;i<=endN;i++){
	        		ArrayList<Integer> newList = new ArrayList<Integer>();
	        		newList.add(new Integer(i));
	        		result.add(newList);
	        	}
	        	return result;
	        }
	        
	        for(int i=startN;i<=endN;i++){
	        	ArrayList<ArrayList<Integer>> restComb = rangeCombine(i+1,endN,k-1);
	        	
	           System.out.println("For node "+i+" Rest Combos are:");
	           printCombs(restComb);
	           
	           for(int j=0;j<restComb.size();j++){
	               ArrayList<Integer> curRestComb = restComb.get(j);
	               curRestComb.add(new Integer(i));
	               result.add(curRestComb);
	           }
	        }
	        
	        System.out.println("For node ["+startN+","+endN+"] All combos are:");
	           printCombs(result);
	           
	        return result;
	    }
		
	    public void printCombs(ArrayList<ArrayList<Integer>> comb) {
	    	System.out.println("[");
	    	for(int i=0;i<comb.size();i++){
	    		ArrayList<Integer> curComb = comb.get(i);
	    		System.out.print("[");
	    		for(int j=0;j<curComb.size();j++){
	    			if(j == curComb.size()-1)
	    				System.out.print(curComb.get(j));
	    			else
	    				System.out.print(curComb.get(j)+",");
	    		}
	    		System.out.println("]");
	    	}
	    	System.out.println("]");
	    }
	    
	    /*check if a char[][] board with filled data is valid sudoku */
	    public boolean isValidSudoku(char[][] board) {
	        int[] countPerNumber = new int[9];
	        //0-8 mapping to number 1-9
	        clearCounts(countPerNumber);
	        
	        //checking each row
	        for(int row = 0;row < 9;row++){
	            for(int column = 0; column < 9; column ++){
	                if(board[row][column] != '.') {
	                    int curInt = (int)board[row][column];
	                    countPerNumber[curInt-1]++;
	                }
	            }
	            //check counts;
	            if(overTwoTimes(countPerNumber) == false)
	                return false;
	            
	            //clearcounts;
	            clearCounts(countPerNumber);
	        }
	        
	        //checking each column;
	        for(int column = 0; column < 9; column ++){
	            for(int row = 0;row < 9;row++){
	                if(board[row][column] != '.') {
	                    int curInt = (int)board[row][column];
	                    countPerNumber[curInt-1]++;
	                }
	            }
	            //check counts;
	            if(overTwoTimes(countPerNumber) == false)
	                return false;
	            
	            //clearcounts;
	            clearCounts(countPerNumber);
	        }
	        
	        //checking each nine grid;
	        for(int row = 0; row < 3 ;row ++){
	            for(int column = 0; column < 3; column ++ )
	            if(gridValid(board,3*row,3*column) == false) {
	                return false;
	            }
	        }
	        
	        return true;
	    }
	    
	    public boolean gridValid(char[][] board, int start_row, int start_column){
	        int[] countPerNumber = new int[9];
	        clearCounts(countPerNumber);
	        for(int i=start_row;i<start_row+3;i++){
	            for(int j=start_column;j<start_column+3;j++){
	                if(board[i][j] != '.') {
	                    int curInt = (int)board[i][j];
	                    countPerNumber[curInt-1]++;
	                }
	            }
	        }    
	        return overTwoTimes(countPerNumber);
	    }
	    
	    public boolean overTwoTimes(int[] counts) {
	        for(int i=0;i<counts.length;i++){
	            if(counts[i] > 1)
	                return false;
	        }
	        return true;
	    }
	    
	    public void clearCounts(int[] counts){
	        for(int i=0;i<counts.length;i++){
	            counts[i] = 0;
	        }
	    }
	    
	    public static void main(String[] args) {
	    	Solution obj = new Solution();
	    	obj.combine(5,3);
	    }
	}
