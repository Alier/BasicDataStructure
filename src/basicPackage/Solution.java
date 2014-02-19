package basicPackage;

import java.util.ArrayList;

	public class Solution {
		
		public class Transaction{
			int buyDate;
			int sellDate;
			int profit;
			
			public Transaction(int buydate, int selldate, int Profit){
				buyDate = buydate;
				sellDate = selldate;
				profit = Profit;
			}
		}
		
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
		
		/* will make a int[][] with buying day as row, selling day as column. Calculate the biggest sum of any two qualified pair. 
		 * e.g: one is int[row1][column1], second is int[row2][column2] , then these two have to fulfill the following to be a qualified pair:
		 * row1 < column 1 < row2 < column2 , which means: first one have to buy first then sell, and the sell date has to be earlier than buy date for second transaction
		 */
		public int maxProfit3(int[] prices){
			if(prices.length <= 1) //can't do any transactions.
				return 0;
			
			if(prices.length == 2) {//only one transaction possible
				int result = prices[1]-prices[0];
				if(result > 0) 
					return result;
				else 
					return 0;
			}
			
			int[][] profits = new int[prices.length][prices.length];
			/* Index of prices are the date:
			 * buy could only happen between index 0 to (prices.length-2); 
			 * sell could only happen between index 1 to (prices.lengh-1);
			 */
			//form the matrix
			//each arraylist in the posTransByBuyDate would be all pos transactions by the starting date.
			ArrayList<ArrayList<Transaction>> posTransByBuyDate = new ArrayList<ArrayList<Transaction>>();
			
			long time1 = System.currentTimeMillis();
			for(int i=0;i<prices.length-1;i++){
				ArrayList<Transaction> posTransactions = new ArrayList<Transaction>();
				for(int j=i+1;j<prices.length;j++){
					//profit from buying at i day and sell at j day is profit[i][j]
					profits[i][j] = prices[j]-prices[i];
					//System.out.println("["+i+","+j+"]:"+profits[i][j]);
					//if it's positive, put in the arrayList as candidates to choose from
					if(profits[i][j] > 0){
						posTransactions.add(new Transaction(i,j,profits[i][j]));
					}
				}
				posTransByBuyDate.add(posTransactions);
			}
			
			long time2=System.currentTimeMillis();
			System.out.println("time used: "+(time2-time1));
			//print posTransactions
//			for(int i=0;i<posTransactions.size();i++){
//				Transaction curTran = posTransactions.get(i);
//				System.out.println("["+curTran.buyDate+","+curTran.sellDate+"] = "+curTran.profit);
//			}
			
			//find the biggest sum of any two numbers in all transactions.
			long time3 = System.currentTimeMillis();
			//more than two positive transactions
			int maxSum = 0;
			for(int i=0;i<posTransByBuyDate.size()-1;i++){
				ArrayList<Transaction> transOfFirstDay = posTransByBuyDate.get(i);
				if(transOfFirstDay.size() == 0)
					continue;
				else {
					for(int j=0;j<transOfFirstDay.size();j++){
						Transaction firstTran = transOfFirstDay.get(j); //specific transaction
						int firstTranSellDate = firstTran.sellDate;
						for(int k = firstTranSellDate + 1;k < posTransByBuyDate.size();k++){
							ArrayList<Transaction> transOfSecondDay  = posTransByBuyDate.get(k);
							int newSum = firstTran.profit + getMaxProfit(transOfSecondDay);
							if(newSum > maxSum)
								maxSum = newSum;
							}
					}
				}
			}
			long time4=System.currentTimeMillis();
			System.out.println("time used: "+(time4-time3));
//			for(int i=0;i<prices.length-1;i++){ 
//				for(int j=i+1;j<prices.length;j++){
//					if(profits[i][j] > 0) {//row1=i <column1 = j < row2(starting at j+1);
//						int newSum = profits[i][j] + getMax(profits,j+1);
//						if(newSum > maxSum){
//							maxSum = newSum;
//						}
//					}
//				}
//			}
			
			return maxSum;
		}
		
		public int getMaxProfit(ArrayList<Transaction> transByDate){
			int maxProfit = 0;
			
			for(int i = 0; i< transByDate.size();i++){
				if(transByDate.get(i).profit > maxProfit)
					maxProfit = transByDate.get(i).profit;
			}
			return maxProfit;
		}
		
		/* utility function, return the biggest integer from this square matrix, only looking in:
		 * row starting from StartRow, column has to be bigger than the row number. 
		 * 
		 * e.g, for matric 
		 * 1 2 3 4
		 * 4 5 6 7
		 * 7 8 9 8
		 * 3 2 4 1
		 * 
		 * if startRow is 1, then can only choose from [1][2],[1][3],[2][3]
		 */
		public int getMax(int[][] matrix,int startRow){
			int curMax = 0;
			
			for(int i=startRow;i<matrix.length-1;i++){
				for(int j=i+1;j<matrix.length;j++){
					if(matrix[i][j] > curMax){
						curMax = matrix[i][j];
					}
				}
			}
			
			return curMax;
		}
		
		/**** INCOMPLETE !!!! ****/
		//Try to use the different to calculate maximum sum
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
		int[] array = new int[] { 397, 6621, 4997, 7506, 8918, 1662, 9187,
				3278, 3890, 514, 18, 9305, 93, 5508, 3031, 2692, 6019, 1134,
				1691, 4949, 5071, 799, 8953, 7882, 4273, 302, 6753, 4657, 8368,
				3942, 1982, 5117, 563, 3332, 2623, 9482, 4994, 8163, 9112,
				5236, 5029, 5483, 4542, 1474, 991, 3925, 4166, 3362, 5059,
				5857, 4663, 6482, 3008, 3616, 4365, 3634, 270, 1118, 8291,
				4990, 1413, 273, 107, 1976, 9957, 9083, 7810, 4952, 7246, 3275,
				6540, 2275, 8758, 7434, 3750, 6101, 1359, 4268, 5815, 2771,
				126, 478, 9253, 9486, 446, 3618, 3120, 7068, 1089, 1411, 2058,
				2502, 8037, 2165, 830, 7994, 1248, 4993, 9298, 4846, 8268,
				2191, 3474, 3378, 9625, 7224, 9479, 985, 1492, 1646, 3756,
				7970, 8476, 3009, 7457, 8922, 2980, 577, 2342, 4069, 8341,
				4400, 2923, 2730, 2917, 105, 724, 518, 5098, 6375, 5364, 3366,
				8566, 8838, 3096, 8191, 2414, 2575, 5528, 259, 573, 5636, 4581,
				9049, 4998, 2038, 4323, 7978, 8968, 6665, 8399, 7309, 7417,
				1322, 6391, 335, 1427, 7115, 853, 2878, 9842, 2569, 2596, 4760,
				7760, 5693, 9304, 6526, 8268, 4832, 6785, 5194, 6821, 1367,
				4243, 1819, 9757, 4919, 6149, 8725, 7936, 4548, 2386, 5354,
				2222, 8777, 2041, 1, 2245, 9246, 2879, 8439, 1815, 5476, 3200,
				5927, 7521, 2504, 2454, 5789, 3688, 9239, 7335, 6861, 6958,
				7931, 8680, 3068, 2850, 1181, 1793, 7138, 2081, 532, 2492,
				4303, 5661, 885, 657, 4258, 131, 9888, 9050, 1947, 1716, 2250,
				4226, 9237, 1106, 6680, 1379, 1146, 2272, 8714, 8008, 9230,
				6645, 3040, 2298, 5847, 4222, 444, 2986, 2655, 7328, 1830,
				6959, 9341, 2716, 3968, 9952, 2847, 3856, 9002, 1146, 5573,
				1252, 5373, 1162, 8710, 2053, 2541, 9856, 677, 1256, 4216,
				9908, 4253, 3609, 8558, 6453, 4183, 5354, 9439, 6838, 2682,
				7621, 149, 8376, 337, 4117, 8328, 9537, 4326, 7330, 683, 9899,
				4934, 2408, 7413, 9996, 814, 9955, 9852, 1491, 7563, 421, 7751,
				1816, 4030, 2662, 8269, 8213, 8016, 4060, 5051, 7051, 1682,
				5201, 5427, 8371, 5670, 3755, 7908, 9996, 7437, 4944, 9895,
				2371, 7352, 3661, 2367, 4518, 3616, 8571, 6010, 1179, 5344,
				113, 9347, 9374, 2775, 3969, 3939, 792, 4381, 8991, 7843, 2415,
				544, 3270, 787, 6214, 3377, 8695, 6211, 814, 9991, 2458, 9537,
				7344, 6119, 1904, 8214, 6087, 6827, 4224, 7266, 2172, 690,
				2966, 7898, 3465, 3287, 1838, 609, 7668, 829, 8452, 84, 7725,
				8074, 871, 3939, 7803, 5918, 6502, 4969, 5910, 5313, 4506,
				9606, 1432, 2762, 7820, 3872, 9590, 8397, 1138, 8114, 9087,
				456, 6012, 8904, 3743, 7850, 9514, 7764, 5031, 4318, 7848,
				9108, 8745, 5071, 9400, 2900, 7341, 5902, 7870, 3251, 7567,
				2376, 9209, 9000, 1491, 7030, 2872, 7433, 1779, 362, 5547,
				7218, 7171, 7911, 2474, 914, 2114, 8340, 8678, 3497, 2659,
				2878, 2606, 7756, 7949, 2006, 656, 5291, 4260, 8526, 4894,
				1828, 7255, 456, 7180, 8746, 3838, 6404, 6179, 5617, 3118,
				8078, 9187, 289, 5989, 1661, 1204, 8103, 2, 6234, 7953, 9013,
				5465, 559, 6769, 9766, 2565, 7425, 1409, 3177, 2304, 6304,
				5005, 9559, 6760, 2185, 4657, 598, 8589, 836, 2567, 1708, 5266,
				1754, 8349, 1255, 9767, 5905, 5711, 9769, 8492, 3664, 5134,
				3957, 575, 1903, 3723, 3140, 5681, 5133, 6317, 4337, 7789,
				7675, 3896, 4549, 6212, 8553, 1499, 1154, 5741, 418, 9214,
				1007, 2172, 7563, 8614, 8291, 3469, 677, 4413, 1961, 4341,
				9547, 5918, 4916, 7803, 9641, 4408, 3484, 111733, 7685, 6074,
				257, 5249, 4688, 8549, 5070, 5366, 2962, 7031, 6059, 8861,
				9301, 7328, 6664, 5294, 8088, 6500, 6421, 1518, 4321, 5336,
				2623, 8742, 1505, 9941, 1716, 2820, 4764, 6783, 906, 2450,
				2857, 7515, 4051, 7546, 2416, 9121, 9264, 1730, 6152, 1675,
				592, 1805, 9003, 7256, 7099, 3444, 3757, 9872, 4962, 4430,
				1561, 7586, 3173, 3066, 3879, 1241, 2238, 8643, 8025, 3144,
				7445, 882, 7012, 1496, 4780, 9428, 617, 396, 1159, 3121, 2072,
				1751, 4926, 7427, 5359, 8378, 871, 5468, 8250, 5834, 9899,
				9811, 9772, 9424, 2877, 3651, 7017, 5116, 8646, 5042, 4612,
				6092, 2277, 1624, 7588, 3409, 1053, 8206, 3806, 8564, 7679,
				2230, 6667, 8958, 6009, 2026, 7336, 6881, 3847, 5586, 9067, 98,
				1750, 8839, 9522, 4627, 8842, 2891, 6095, 7488, 7934, 708,
				3580, 6563, 8684, 7521, 9972, 6089, 2079, 130, 4653, 9758,
				2360, 1320, 8716, 8370, 9699, 6052, 1603, 3546, 7991, 670,
				3644, 6093, 9509, 9518, 7072, 4703, 2409, 3168, 2191, 6695,
				228, 2124, 3258, 5264, 9645, 9583, 1354, 1724, 9713, 2359,
				1482, 8426, 3680, 6551, 3148, 9731, 8955, 4751, 9629, 6946,
				5421, 9625, 9391, 1282, 5495, 6464, 5985, 4256, 5984, 4528,
				952, 6212, 6652, 562, 1476, 6297, 145, 9182, 8021, 6211, 1542,
				5856, 4637, 1574, 2407, 7785, 1305, 1362, 2536, 934, 4661,
				4309, 559, 4052, 1943, 2406, 516, 4280, 6662, 2852, 8808, 7614,
				9064, 1813, 4529, 6893, 8110, 4674, 2427, 2484, 7237, 3969,
				8340, 1874, 5543, 7099, 6011, 3200, 8461, 8547, 486, 9474,
				9208, 7397, 9879, 7503, 9803, 6747, 1783, 6466, 9600, 6944,
				432, 8664, 8757, 4961, 1909, 6867, 5988, 4337, 5703, 3225,
				4658, 4043, 1452, 6554, 1142, 7463, 9754, 5956, 2363, 241,
				1782, 7923, 7638, 1661, 5427, 3794, 8409, 7210, 260, 8009,
				4154, 692, 3025, 9263, 2006, 4935, 2483, 7994, 5624, 8186,
				7571, 282, 8582, 9023, 6836, 6076, 6487, 6591, 2032, 8850,
				3184, 3815, 3125, 7174, 5476, 8552, 968, 3885, 2115, 7580,
				8246, 2621, 4625, 1272, 1885, 6631, 6207, 4368, 4625, 8183,
				2554, 8548, 8465, 1136, 7572, 1654, 7213, 411, 4597, 5597,
				5613, 7781, 5764, 8738, 1307, 7593, 7291, 8628, 7830, 9406,
				6208, 6077, 2027, 833, 7349, 3912, 7464, 9908, 4632, 8441,
				8091, 7187, 6990, 2908, 4675, 914, 4562, 8240, 1325, 9159, 190,
				6938, 3292, 5954, 2028, 4600, 9899, 9319, 3228, 7730, 5077,
				9436, 159, 7105, 6622, 7508, 7369, 4086, 3768, 2002, 8880,
				8211, 5541, 2222, 1119, 216, 3136, 5682, 4809, 813, 1193, 4999,
				4103, 4486, 7305, 6131, 9086, 7205, 5451, 2314, 1287, 528,
				8102, 1446, 3985, 4724, 5306, 1355, 5163, 9074, 9709, 4043,
				7285, 5250, 2617, 4756, 1818, 2105, 6790, 6627, 2918, 7984,
				7978, 7021, 2470, 1636, 3152, 7908, 8841, 4955, 222, 6480,
				5484, 4676, 7926, 5821, 9401, 3232, 7176, 916, 8658, 3237,
				1311, 5943, 8487, 3928, 7051, 306, 6033, 3842, 3285, 8951,
				1826, 7616, 2324, 648, 9252, 5476, 8556, 4445, 6784 };
	    	//int[] array2 = new int[]{1,2,3,4,5};
			System.out.println(obj.maxProfit3(array));
	    }
	}
