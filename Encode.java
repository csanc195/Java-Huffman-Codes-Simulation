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
public class Encode {
	
	private char symbol;
	private String code;

	public Encode(char c, String cod){
		symbol = c;
		code = cod;
	}
	public Encode() {
		symbol = ' ';
		code = null;
	}
	public char getSymbol(){
		return symbol;
	}
	public String getCode(){
		return code;
	}
	public String toString(){
		return "symbol: " + symbol + " code: " + code;
	}
}
