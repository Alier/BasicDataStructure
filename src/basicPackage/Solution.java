package basicPackage;

import java.util.ArrayList;

	public class Solution {
	/*
	 * Given a collection of numbers that might contain duplicates, return all
	 * possible unique permutations.
	 * 
	 * For example, [1,1,2] have the following unique permutations: [1,1,2],
	 * [1,2,1], and [2,1,1].
	 */
		public ArrayList<ArrayList<Integer>> permuteUnique(int[] num) {
			ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
			if(num.length == 0)
				return result;
			
			if(num.length ==1){
				ArrayList<Integer> onePermute = new ArrayList<Integer>();
				onePermute.add(new Integer(num[0]));
				result.add(onePermute);
				return result;
			}
			
			ArrayList<Integer> visited = new ArrayList<Integer>();
			
			for(int i=0;i<num.length;i++){
				Integer curInt = new Integer(num[i]);
				if(visited.contains(curInt)){
					break;
				} else {
					visited.add(curInt);
					ArrayList<Integer> restInts = new ArrayList<Integer>();
					for(int j=0;j<num.length;j++){
						if(j!=i)
							restInts.add(new Integer(num[j]));
					}
					ArrayList<ArrayList<Integer>> restPermute = getPermute(restInts);
				}
			}
	        return result;
	    }
		
		public ArrayList<ArrayList<Integer>> getPermute(ArrayList<Integer> numsToUse) {
			ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
			if(numsToUse.size() == 0)
				return result;
			
			if(numsToUse.size() ==1){
				result.add(numsToUse);
				return result;
			}
			
			
			return result;
		}
		
		/*Implement pow(x, n).*/
		public double pow(double x, int n) {
	        if(n==0) return 1;
	        
	        if(n==1) return x;
	        
	        if(n == -1) {
	            if(x != 0){
	                return 1/x;
	            } else {
	                return Integer.MAX_VALUE;
	            }
	        }
	         
	        //n>=2 || n<=-2;
	        double square = x*x;
	        if(n>0) {
	            int powerForSquare = n/2;
	            int restPower = n%2;
	        
	            return pow(square,powerForSquare)*pow(x,restPower);
	        } else{
	            int tmpN = -n;
	            int powerForSquare = tmpN/2;
	            int restPower = tmpN%2;
	            
	            return 1/(pow(square,powerForSquare)*pow(x,restPower));
	        }
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
	               ArrayList<Integer> newComb = new ArrayList<Integer>();
	               //add cur in the first place
	               newComb.add(new Integer(i));
	               for(int m=0;m<curRestComb.size();m++){
	            	   newComb.add(curRestComb.get(m));
	               }
	               result.add(newComb);
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
