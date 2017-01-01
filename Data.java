/*
 * Authors: CARLOS SANCHEZ VILA  && DANIEL GONZALEZ 
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
