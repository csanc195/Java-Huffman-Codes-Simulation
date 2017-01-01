/*
 * Authors: CARLOS SANCHEZ VILA  && DANIEL GONZALEZ 
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
