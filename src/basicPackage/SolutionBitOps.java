package basicPackage;

import java.util.ArrayList;
import java.util.BitSet;

public class SolutionBitOps {
	/* 
	 * N and M are both 32 bits . 
	 * Insert M to N from pos j to pos i; Supposing j <-> i is enough length for M and . 
	 * j is in higher bit than i; So j > i
	 * example: N = 10000000000, M = 10011, i=2, j= 6
	 * output should be :10001001100
	 */
	public BitSet insertMtoN(BitSet M, BitSet N, int i, int j){
		// clear the j -> i in N;
		N.set(i, j, false);
		
		// shift m to left by i;
		BitSet shiftedM = new BitSet(32);
		shiftedM.clear();
		for(int start = i; start <32; start ++ ){
			//System.out.println("shiftedM["+start+"]="+"M["+(start-i)+"]:"+M.get(start-i));
			shiftedM.set(start,M.get(start-i));
		}
		//System.out.println("shiftedM="+shiftedM.toString());
		
		// OR both
		shiftedM.or(N);
		return shiftedM;
	}
	
	/*
	 * given two integers, calculate how many bits need to be changed to convert A to B.
	 * example: A = 31; B = 14;  then A = 00011111, B =00001110 , output should be 2 . Only two bits need to be changed to convert 31 to 14; 
	 */
	public int bitsNeedToConvert(int A, int B){
		//XOR A and B to get a new integer.
		//Whatever bits of 1 in this integer is the return value.
		int C = A ^ B;
		int count = 0;
		
		for(int i=0;i<32;i++){
			if((C & (1 << i)) > 0){
				count ++;
			}
		}
		
		return count;
	}
	
	/*
	 * swap odd and even bits of an integer, use as few instructions as possible;
	 * example: swap 0 and 1 , swap 2 and 3, etc
	 */
	public int swapOddAndEvenBit(int origNum){
		int resultNum = 0;
		//make a new Num with origNum shift right by 1; 
		System.out.println("origNum="+origNum);

		int shiftedNum = origNum >> 1;
		System.out.println("shiftedRight="+shiftedNum);
		
		//XOR origNum and shiftedNum, then bit 0 would be XOR of bit(0,1), bit 1 =XOR(1,2), etc... 
		int newNum = shiftedNum ^ origNum;
		System.out.println("XOR="+newNum);
		
		//just need to check even bits, as bit 0 = (0,1), bit 2 = (2,3), etc. Odd bits are not useful in this case
		//if even bits is 0 , meaning those two bits are same, nothing needs to be done; Otherwise, change the original bit to its oppsite. 
		for(int i=0;i<31;i+=2){ //increase i by 2 to avoid checking odd number bits
			if((newNum & (1 << i)) > 0){
				//modify origNum at bit i and i+1 to its opposite, coz newNum[i]= XOR(orig[i],orig[i+1]);
				resultNum = this.revertBitAtPos(origNum,i);
				resultNum = this.revertBitAtPos(resultNum,i+1);
			}
		}
		
		return resultNum;
	}
	
	//revert the bit at pos in origNum to its opposite, 0 -> 1 and 1 -> 0; 
	public int revertBitAtPos(int origNum, int pos){
		//XOR with 1 at that bit;
		return (origNum ^ (1<<pos));
	}
	
	/*
	 * Given an array containing all number ranging from 0 - n, with only one number missing. Find the missing number.
	 * each number in array can't be accessed by all, only function to use is to get the ith bit of array[j];
	 * 
	 * hint: if it contains all number no missing one, then number of 0 and number of 1 on each bit of all numbers should be even. 
	 */
	public int findMissingNum(int[] intArray){
		int bitCount = (int)(Math.log(intArray.length+1)/Math.log(2)); 
		//in case intArray Length is between 2 pow n and 2 pow n+1, should set bitCount to n+1;
		if(Math.pow(2, bitCount) < intArray.length);
		bitCount++;
		
		System.out.println("Bitcount="+bitCount);
		ArrayList<Integer> indexList = new ArrayList<Integer>(); //to save the remaining indexs of elements to inspect	
		int missingNum = 0;
		
		//initially, all elements need to be examined. n elements. 
		for(int i = 0;i<intArray.length;i++){
			indexList.add(new Integer(i));
		}
		
		// i from 0 to top, get ith bit of each element to find out the ith bit
		// for the missing number.
		// remove irrelevant element from the array for each iteration.
		for (int i = 0; i < bitCount; i++) {
			ArrayList<Integer> List0 = new ArrayList<Integer>(); // to save indexs with bit i as 0
			ArrayList<Integer> List1 = new ArrayList<Integer>(); // to save indexs with bit i as 1
			
			for (Integer e : indexList) {
				if (getBit(intArray[e], i) == 0) {
					List0.add(e);
				} else {
					List1.add(e);
				}
			}
			if (List0.size() > List1.size()) { // bit i of missingNum should be 1
				// add the value to missingNum;
				missingNum += Math.pow(2, i);
				
				// remove irrelevant elements from the array;
				indexList = List1;
			} else { // bit i of missingNum should be 0;
				indexList = List0;
			}
		}
		return missingNum;
	}

	public int getBit(int num, int bitPos){
		return (num & (1<<bitPos));
	}
	
	/*
	 * Screen is stored in a single array of bytes. Each byte represent consecutive 8 pixels.
	 * The width of the screen is W, which is multiple of 8, so each line would be represented by a few bytes in array; 
	 * 
	 * Function is to draw a horizontal line from (x1,y) to (x2, y);
	 */
	
	public void drawHorizontalLine(byte[] screen, int width, int x1, int x2, int y){
		int X1ByteIndex = 0;
		int X2ByteIndex = 0;
	
		int bytesPerLine = width/8;		
		int startIndexY = y*bytesPerLine;
	
		X1ByteIndex = startIndexY + x1/8;
		X2ByteIndex = startIndexY + x2/8;
		
		int x1OffsetInByte = x1%8;
		int x2OffsetInByte = x2%8;
		
		//change anything in the X1Byte from x1OffsetInByte to end of Byte to 1;
		//same to X2Byte;
		//screen[X1ByteIndex] = convertBitsToValue(screen[X1ByteIndex], x1OffsetInByte,7,true);
		screen[X2ByteIndex] = convertBitsToValue(screen[X2ByteIndex], 0, x2OffsetInByte,true);
		
		//change every bits in every bytes between X1Byte and X2Byte to 1;
		for(int i = X1ByteIndex+1;i < X2ByteIndex; i++){
			screen[i] = convertBitsToValue(screen[i], 0,7,true);
		}
		
		this.printScreen(screen,16);
	}
	
	//Example, for byte 00010000, if startBit = 5, endBit = 2, value = 1, result should be 00111100. 
	public byte convertBitsToValue(byte org,int startBit, int endBit, boolean value){
		System.out.println("StartBit="+startBit+"/endBit="+endBit);
		
		int mask1 = (255 >> startBit); //for above example, mask 1 = 00000011;
		System.out.println("mask1="+String.format("%8s", Integer.toBinaryString(mask1)).replace(' ', '0'));
		int mask2 = (255 >> (endBit+1)); //for above example, mask 2 = 00111111;
		System.out.println("mask2="+String.format("%8s", Integer.toBinaryString(mask2)).replace(' ', '0'));
		int mask = (mask1 ^ mask2); // mask = 00111100;
		System.out.println("mask="+String.format("%8s", Integer.toBinaryString(mask)).replace(' ', '0'));
		//!!!!! when mask is larger than 127, there is problem with result, bigger than 8 bits!
		
		byte fmask = (byte)(mask);
		System.out.println("fmask="+String.format("%8s", Integer.toBinaryString(fmask & 0xff)).replace(' ', '0'));
		
		if(value == true){ //convert from startBit to endBit to 1;
			return (byte)(org | (fmask & 0xff));
		}else {
			return (byte)(org & ~ mask);
		}
	}
	
	public void printScreen(byte[] screen, int width){
		int bytesPerLine = width/8;
		int height = screen.length / bytesPerLine;
		
		for(int i = 0; i < height; i++){
			//print each line;
			int startByteIndex = i*bytesPerLine;
			int endByteIndex = startByteIndex + bytesPerLine-1;
			for(int j = startByteIndex; j<=endByteIndex; j++){
				//print each byte;
				String s = String.format("%8s", Integer.toBinaryString(screen[j])).replace(' ', '0');
				System.out.print(s);
			}
			System.out.println();
		}
	}
	
	public void printByte(byte x){
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		    int i;
//		    byte y;
//		    i = 1024;
//		    for(i=1024;i>0;i--)
//		    {
//		    y = (byte)i;
//		    System.out.print(i + " mod 128 = " + i%128 + " also ");
//		    System.out.println(i + " cast to byte " + " = " + y);
//		    }

//		
		SolutionBitOps objc = new SolutionBitOps();
		
		byte[] test= {0,0,0,0,0,0,0,0};
		objc.printScreen(test,16);
		System.out.println("Drawing the line from x1="+5+" to x2="+9+" on row y="+2+":");
		objc.drawHorizontalLine(test, 16, 5, 9, 2);
//		int[] intArray = new int[]{0,1,2,3,4,5,6,7,8,9,10,12,13,14,15,16};
//		
//		System.out.println("Result="+objc.findMissingNum(intArray));
		
//		int A = 35;
//		int B = 14;
//		
//		System.out.println(objc.bitsNeedToConvert(A, B));
		
//		BitSet M = new BitSet(32); //M is 10011
//		M.clear();
//		M.set(0);
//		M.set(1);
//		M.set(4);
//		System.out.println("M="+M.toString());
//		
//		BitSet N = new BitSet(32);//N is 10001100000
//		N.clear();  
//		N.set(10);
//		N.set(6);
//		N.set(5);
//		System.out.println("N="+N.toString());
//		
//		BitSet output = objc.insertMtoN(M,N,2,6);
//		System.out.println(output.toString());
	}

}
