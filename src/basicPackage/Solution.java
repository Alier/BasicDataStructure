package basicPackage;

import java.util.ArrayList;

	public class Solution {
		/*
		 * Best Time to Buy and Sell Stock III 
		 * Say you have an array for which the i'th element is the price of a given stock on day i.
		 * 
		 * Design an algorithm to find the maximum profit. You may complete at most
		 * two transactions.
		 * 
		 * Note: You may not engage in multiple transactions at the same time (ie,
		 * you must sell the stock before you buy again).
		 */
		
		/**** INCOMPLETE !!!! ****/
		public int maxProfit2(int[] prices){	
			if(prices.length <= 1)
				return 0;
			
			//have at least 2 members
			//form a new array with profit between two adjacent prices;
			int[] profits = new int[prices.length-1];
			//go through profits array, record all index for positive profit in one day turn-over transactions
			ArrayList<Integer> posIndex = new ArrayList<Integer>();
			for(int i=0;i<prices.length-1;i++){
				profits[i]=prices[i+1]-prices[i];
				if(profits[i] > 0)
					posIndex.add(new Integer(i));
			}
			
			/* this profit array should have positive and negative numbers. 
			 * e.g [-1,2,-3,4,5,6,-7,8]
			 * from head, merge positive nodes in sequence that could be merged(indicating merging the two transactions into one)
			 * merged new node should have value > both positive nodes, otherwise don't merge. 
			 * Put the new node in a new ArrayList.
			 */
			//merge consecutive positives first
			for(int j=0;j<posIndex.size()-1;j++){
				int curPos = posIndex.get(j);
				int nextPos = posIndex.get(j+1);
				if(nextPos == curPos+1){
					profits[nextPos] +=profits[curPos];
					profits[curPos] = 0;
					posIndex.remove(j);
					j--;
				}
			}
			
			if(posIndex.size() == 0) //no positive transaction
				return 0;
			
			if(posIndex.size() == 1) 
				return profits[posIndex.get(0)];
			
			if(posIndex.size() == 2)
				return profits[posIndex.get(0)]+ profits[posIndex.get(1)];
			
			//at least three positive transactions. Merge any two if could
			for(int j=0;j<posIndex.size()-1 && posIndex.size()>3;j++){
				int curPos = posIndex.get(j);
				int nextPos = posIndex.get(j+1);
				int newProfit = profits[curPos];
				for(int k=curPos+1;k<=nextPos;k++){
				    newProfit += profits[k];
				}
				if(newProfit >= profits[curPos] && newProfit >= profits[nextPos]) {
					profits[nextPos] = newProfit;
					profits[curPos] = 0;
					posIndex.remove(j);
					j--;
				}
			}
			
			//after second merge, find the biggest two in the profits. Index in posIndex.
			if(posIndex.size() == 1) 
				return profits[posIndex.get(0)];
			
			if(posIndex.size() == 2)
				return profits[posIndex.get(0)]+ profits[posIndex.get(1)];
				
			if(posIndex.size() == 3) {
			    
			}
			//get max
			int maxProfit = 0;
			int maxIndex = 0;
			for(int m=0;m<posIndex.size();m++){
			    int curProfit = profits[posIndex.get(m)];
			    if(curProfit > maxProfit) {
			        maxProfit = curProfit;
			        maxIndex = m;
			    }
			}
			
			int result = maxProfit;
			//get second max
			posIndex.remove(maxIndex);
			int secondMaxProfit = 0;
			for(int n=0;n<posIndex.size();n++){
			    int curProfit = profits[posIndex.get(n)];
			    if(curProfit > secondMaxProfit) {
			        secondMaxProfit = curProfit;
			    }
			}
			
			result+=secondMaxProfit;
			
			return result;
		}
		
		//this method is time-consuming, not very good
		public int maxProfit1(int[] prices) {
			int maxProfit = 0;
			if(prices.length <=1 )
				return maxProfit;
			
			if(prices.length == 2) {
				int profit = prices[1]-prices[0];
				if(profit > maxProfit)
					maxProfit = profit;
				return maxProfit;
			}
			
			//have at least three members
			for(int midIndex = 0;midIndex<prices.length;midIndex++){
				int leftPartProfit =  maxProfitRange(0,midIndex,prices);
				int rightPartProfit = maxProfitRange(midIndex+1,prices.length-1,prices);
				
				int curProfit = leftPartProfit + rightPartProfit;
				if(curProfit > maxProfit)
					maxProfit = curProfit;
			}
			
			return maxProfit;
		}
		
		//return the max Profit within this range;
		public int maxProfitRange(int startIndex, int endIndex,int[] prices){
			int maxProfit = 0;
			
			if(endIndex == startIndex) {
				return maxProfit;
			}
				
			//only two numbers in range
			if(endIndex-startIndex == 1) {
				int curProfit = prices[endIndex]-prices[startIndex];
				if(curProfit > maxProfit)
					maxProfit = curProfit;
				return maxProfit;
			}
			
			for(int i=startIndex;i<endIndex;i++){
				for(int j=i+1;j<=endIndex;j++){
					int curProfit = prices[j]-prices[i];
					if(curProfit > maxProfit)
						maxProfit = curProfit;
				}
			}
			
			return maxProfit;
		}
	
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
			
			if(num.length == 1) {
				ArrayList<Integer> onlyList = new ArrayList<Integer>();
				onlyList.add(new Integer(num[0]));
				result.add(onlyList);
				return result;
			}
			
			ArrayList<Integer> visited = new ArrayList<Integer>();
			for(int i=0;i<num.length;i++){
				Integer curNode = new Integer(num[i]);
				if(!visited.contains(curNode)) {
					//mark as visited
					visited.add(curNode);
					
					//create rest num's array
					int[] restNums = new int[num.length-1];
					for(int j=0,k=0;j<num.length;j++){
						if(j != i) {
							restNums[k] = num[j];
							k++;
						}
					}
					
					ArrayList<ArrayList<Integer>> restPermutes =  permuteUnique(restNums);
					
					//merge the restPermutes with curNumber to create curNumber's permute
					for(int m=0;m<restPermutes.size();m++){
						ArrayList<Integer> curPermute = restPermutes.get(m);
						ArrayList<Integer> nodeCombined = new ArrayList<Integer>();
						nodeCombined.add(curNode);
						for(int n=0;n<curPermute.size();n++){
							nodeCombined.add(new Integer(curPermute.get(n)));
						}
						result.add(nodeCombined);
					}
				}
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
	    	System.out.println("Max profit: "+obj.maxProfit(new int[]{3,1,2,4,0,6,7}));
	    	//obj.printCombs(obj.permuteUnique(new int[]{1,3,2}));
	    }
	}
