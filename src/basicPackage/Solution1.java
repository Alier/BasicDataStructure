package basicPackage;

import java.util.ArrayList;

public class Solution1 {

	/***** INCOMPLETE !!!! ******/
	/*
	 * The gray code is a binary numeral system where two successive values
	 * differ in only one bit.
	 * 
	 * Given a non-negative integer n representing the total number of bits in
	 * the code, print the sequence of gray code. A gray code sequence must
	 * begin with 0.
	 * 
	 * For example, given n = 2, return [0,1,3,2]. Its gray code sequence is:
	 * 
	 * 00 - 0 / 01 - 1 / 11 - 3 / 10 - 2
	 * 
	 * I guess for n=3, it should be :
	 * 000
	 * 001
	 * 011
	 * 010
	 * 110
	 * 111
	 * 101
	 * 100
	 * 
	 * Noticing that the 
	 * lowest digit flips from (0,1) to (1,0) every 2 numbers;
	 * 2nd lowest digit flips from(0,0,1,1) to (1,1,0,0) every 4 numbers;
	 * 3nd lowest digit flips from(0,0,0,0,1,1,1,1) to (1,1,1,1,0,0,0,0) every 8 numbers; 
	 */

	public ArrayList<Integer> grayCode(int n) {
		ArrayList<Integer> result = new ArrayList<Integer>();

		if (n == 0)
			return result;

		// form two arrays, digitArray is digit from lower position to higher
		// position, so digitsArray[0] meaning 0/1 on the lowest position of n,
		// valueArray is decimal value represented corresponding to the position
		// in digitsArray, so valueArray[0] would be 2 power 0 = 1;
		int[] digitsArray = new int[n];
		int[] valueArray = new int[n];

		for (int i = 0; i < n; i++) {
			if(i == 0 ){
				valueArray[i] = 1;
			} else {
				valueArray[i] = 2*valueArray[i-1];
			}
		}
		
		printIntArray(digitsArray);
		printIntArray(valueArray);
		
		int totalNumbers = (int)Math.pow(2, n);
		
		int curResult = 0;
		for(int i = 0 ;i < totalNumbers; i++){
			//form digitsArray according to i;
			for(int j =0 ;j < n;j++){
				
				curResult += valueArray[j]*digitsArray[j];
			}
			System.out.println("Cur result:"+curResult);
			result.add(new Integer(curResult));
		}
					
		return result;
	}

	/*
	 * Given an array with n objects colored red, white or blue, sort them so
	 * that objects of the same color are adjacent, with the colors in the order
	 * red, white and blue.
	 * 
	 * Here, we will use the integers 0, 1, and 2 to represent the color red,
	 * white, and blue respectively.
	 * 
	 * Note: You are not suppose to use the library's sort function for this
	 * problem.
	 * 
	 * click to show follow up.
	 * 
	 * Follow up: A rather straight forward solution is a two-pass algorithm
	 * using counting sort. First, iterate the array counting number of 0's,
	 * 1's, and 2's, then overwrite array with total number of 0's, then 1's and
	 * followed by 2's.
	 * 
	 * Could you come up with an one-pass algorithm using only constant space?
	 */
	public void sortColors1Pass(int[] A) {
		/*
		 * Two pointers to record the foremost position of 1 and foremost
		 * position of 2; one pointer p going forward for next element; if
		 * p.value = 0, and it's only later than first_1, exchange it with
		 * first_1, move first_1 forward; if it's later than first_2 , exchange
		 * it with first_1 and then exchange it again with first_2;
		 * 
		 * if p.value = 1, and it's later than first_2, exchange it with
		 * first_2, move first_2 forward;
		 */
		if (A.length <= 1)
			return;

		System.out.println("Before");
		printIntArray(A);

		int first_1 = -1;
		int first_2 = -1; // meaning not initialized with real value;

		for (int p = 0; p < A.length; p++) {
			switch (A[p]) {
			case 0:
				if (first_1 < 0 && first_2 < 0) // no 1 or 2 in front;
					break;

				if (first_1 >= 0 && p > first_1) { // first 1 is in front;
													// exchange
					A[first_1] = A[p];
					A[p] = 1;
					first_1++;
				}

				if (first_2 >= 0 && p > first_2) { // first 2 in front; exchange
					A[first_2] = A[p];
					A[p] = 2;
					first_2++;
				}
				break;
			case 1:
				if (first_1 < 0) // not initialized;
					first_1 = p;

				if (first_2 >= 0 && p > first_2) { // first_2 valid and former
													// than current element,
													// exchange
					A[first_2] = 1;
					A[p] = 2;
					if (first_1 == p) {
						first_1 = first_2;
					}
					first_2++;
				}
				break;
			case 2:
				if (first_2 < 0) // not initialized;
					first_2 = p;

				break;
			}
		}

		System.out.println("After");
		printIntArray(A);
	}

	public void sortColors2Pass(int[] A) {
		int[] counts = new int[3]; // count for color 0,1,2

		System.out.println("Before");
		printIntArray(A);

		for (int i = 0; i < A.length; i++) {
			switch (A[i]) {
			case 0:
				counts[0]++;
				break;
			case 1:
				counts[1]++;
				break;
			case 2:
				counts[2]++;
				break;
			default:
				break;
			}
		}

		// fill in new array
		for (int i = 0; i < A.length; i++) {
			if (i < counts[0]) {
				A[i] = 0;
			} else if (i < counts[1] + counts[0]) {
				A[i] = 1;
			} else {
				A[i] = 2;
			}
		}

		System.out.println("After");
		printIntArray(A);
	}

	public void printIntArray(int[] A) {
		for (int i = 0; i < A.length; i++) {
			System.out.print(A[i] + " ");
		}
		System.out.println();
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
		if (num.length == 0)
			return result;

		if (num.length == 1) {
			ArrayList<Integer> onePermute = new ArrayList<Integer>();
			onePermute.add(new Integer(num[0]));
			result.add(onePermute);
			return result;
		}

		ArrayList<Integer> visited = new ArrayList<Integer>();

		for (int i = 0; i < num.length; i++) {
			Integer curInt = new Integer(num[i]);
			if (visited.contains(curInt)) {
				break;
			} else {
				visited.add(curInt);
				ArrayList<Integer> restInts = new ArrayList<Integer>();
				for (int j = 0; j < num.length; j++) {
					if (j != i)
						restInts.add(new Integer(num[j]));
				}
				// ArrayList<ArrayList<Integer>> restPermute =
				// getPermute(restInts);
			}
		}
		return result;
	}

	public ArrayList<ArrayList<Integer>> getPermute(ArrayList<Integer> numsToUse) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		if (numsToUse.size() == 0)
			return result;

		if (numsToUse.size() == 1) {
			result.add(numsToUse);
			return result;
		}

		return result;
	}

	/* Implement pow(x, n). */
	public double pow(double x, int n) {
		if (n == 0)
			return 1;

		if (n == 1)
			return x;

		if (n == -1) {
			if (x != 0) {
				return 1 / x;
			} else {
				return Integer.MAX_VALUE;
			}
		}

		// n>=2 || n<=-2;
		double square = x * x;
		if (n > 0) {
			int powerForSquare = n / 2;
			int restPower = n % 2;

			return pow(square, powerForSquare) * pow(x, restPower);
		} else {
			int tmpN = -n;
			int powerForSquare = tmpN / 2;
			int restPower = tmpN % 2;

			return 1 / (pow(square, powerForSquare) * pow(x, restPower));
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
		return rangeCombine(1, n, k);
	}

	// return combination of picking any k data from startN to endN.
	public ArrayList<ArrayList<Integer>> rangeCombine(int startN, int endN,
			int k) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		int countInRange = endN - startN + 1;

		if (startN > endN || countInRange < k)
			return result;

		// count in range same as k, only one comb
		if (countInRange == k) {
			ArrayList<Integer> newList = new ArrayList<Integer>();
			for (int i = startN; i <= endN; i++) {
				newList.add(new Integer(i));
			}
			result.add(newList);
			return result;
		}

		// choose one, then every data in range should form one list
		if (k == 1) {
			for (int i = startN; i <= endN; i++) {
				ArrayList<Integer> newList = new ArrayList<Integer>();
				newList.add(new Integer(i));
				result.add(newList);
			}
			return result;
		}

		for (int i = startN; i <= endN; i++) {
			ArrayList<ArrayList<Integer>> restComb = rangeCombine(i + 1, endN,
					k - 1);

			System.out.println("For node " + i + " Rest Combos are:");
			printCombs(restComb);

			for (int j = 0; j < restComb.size(); j++) {
				ArrayList<Integer> curRestComb = restComb.get(j);
				ArrayList<Integer> newComb = new ArrayList<Integer>();
				// add cur in the first place
				newComb.add(new Integer(i));
				for (int m = 0; m < curRestComb.size(); m++) {
					newComb.add(curRestComb.get(m));
				}
				result.add(newComb);
			}
		}

		System.out.println("For node [" + startN + "," + endN
				+ "] All combos are:");
		printCombs(result);

		return result;
	}

	public void printCombs(ArrayList<ArrayList<Integer>> comb) {
		System.out.println("[");
		for (int i = 0; i < comb.size(); i++) {
			ArrayList<Integer> curComb = comb.get(i);
			System.out.print("[");
			for (int j = 0; j < curComb.size(); j++) {
				if (j == curComb.size() - 1)
					System.out.print(curComb.get(j));
				else
					System.out.print(curComb.get(j) + ",");
			}
			System.out.println("]");
		}
		System.out.println("]");
	}

	/* check if a char[][] board with filled data is valid sudoku */
	public boolean isValidSudoku(char[][] board) {
		int[] countPerNumber = new int[9];
		// 0-8 mapping to number 1-9
		clearCounts(countPerNumber);

		// checking each row
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				if (board[row][column] != '.') {
					int curInt = (int) board[row][column];
					countPerNumber[curInt - 1]++;
				}
			}
			// check counts;
			if (overTwoTimes(countPerNumber) == false)
				return false;

			// clearcounts;
			clearCounts(countPerNumber);
		}

		// checking each column;
		for (int column = 0; column < 9; column++) {
			for (int row = 0; row < 9; row++) {
				if (board[row][column] != '.') {
					int curInt = (int) board[row][column];
					countPerNumber[curInt - 1]++;
				}
			}
			// check counts;
			if (overTwoTimes(countPerNumber) == false)
				return false;

			// clearcounts;
			clearCounts(countPerNumber);
		}

		// checking each nine grid;
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++)
				if (gridValid(board, 3 * row, 3 * column) == false) {
					return false;
				}
		}

		return true;
	}

	public boolean gridValid(char[][] board, int start_row, int start_column) {
		int[] countPerNumber = new int[9];
		clearCounts(countPerNumber);
		for (int i = start_row; i < start_row + 3; i++) {
			for (int j = start_column; j < start_column + 3; j++) {
				if (board[i][j] != '.') {
					int curInt = (int) board[i][j];
					countPerNumber[curInt - 1]++;
				}
			}
		}
		return overTwoTimes(countPerNumber);
	}

	public boolean overTwoTimes(int[] counts) {
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] > 1)
				return false;
		}
		return true;
	}

	public void clearCounts(int[] counts) {
		for (int i = 0; i < counts.length; i++) {
			counts[i] = 0;
		}
	}

	public static void main(String[] args) {
		Solution1 obj = new Solution1();
		obj.grayCode(5);
		//obj.sortColors1Pass(new int[] { 2, 1, 0 });
		// obj.sortColors2Pass(new int[] { 0, 1, 2, 0, 1, 0, 1, 2 });
	}
}
