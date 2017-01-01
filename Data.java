/* COURSE      : COP 4534
 * Section     : U01
 * Semester    : Fall 2016
 * Instructor  : Alexander Pelin
 * Authors     : 
 * CARLOS SANCHEZ VILA PID: 5446178 && DANIEL GONZALEZ PID: 4926400
 * Assignment #: 4 Huffman Codes
 * Description : The following program provides a function to encrypt and decrypt
 * files using the Huffman codes approach. 
 *
 *  I certify that the work ours and we did not consult with
 *  anyone.
 *                                  
 */
public class Data{
	
	private char symbol;
	private double freq;
	
	public Data(char c, double f){
		symbol = c;
		freq = f;
	}
	
	public char getSymbol(){
		return symbol;
	};

	public double getFreq(){
		return freq;
	}

	public String toString(){
		String answer = " Symbol: " + symbol + 
						" Frequency: " + freq;
		return answer;
	}
}
