package basicPackage;

	public class Solution {
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
	}
