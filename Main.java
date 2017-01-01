
/*
 * Authors: CARLOS SANCHEZ VILA  && DANIEL GONZALEZ 
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class Main {

	public static void main(String[] args) {

		char [] characters = {'A','T','C','G'};
		double [] frequencies = {.45,.45,.05,.05};

		BinaryNode<Data> bnode = build_Huffman_Tree(characters, frequencies);

		System.out.println("Creating the encryption table for: " + Arrays.toString(characters));
		System.out.println("with frequencies: " + Arrays.toString(frequencies));
		System.out.println("************************************************************");
		Encode [] code = getKey(characters, bnode);

		String encodeThis = "ATACGACGTACGT";
		System.out.println("\nEncrypting the text String " + encodeThis);
		String encodedThis = encrypt(encodeThis, code);
		System.out.println("yields: " + encodedThis);
		System.out.println("************************************************************");

		//String stringToDecrypt ="11001101011111010111011010";
		System.out.println("Decrypting the bit String: " + encodedThis);
		String decryptedString  = decrypt(encodedThis, code);
		System.out.println("yields the decrypted string: " + decryptedString);
		System.out.println("************************************************************");
		
		System.out.println("The String " + encodeThis + " and String " + decryptedString
				+ " are the same? " + encodeThis.equals(decryptedString));
	}


	/**	The following function takes an array of symbols with their frequencies and creates
	 * binary nodes which contain them. It introduces each node into a priority queue which 
	 * uses a min-heap. After each node has been inserted into the queue the program then extracts
	 * the the two smallest elements and creates a new node whose children are set to be the two 
	 * newly extracted nodes. This node is then introduced back into the queue until there is one
	 * one node left which is the root of the huffman tree that will allow to encrypt and 
	 * decrypt sequences of data of which the frequency of each character present is known.
	 * It is worth to note that each tree is specifically tailored to a certain group of data. 
	 * and that decryption of separate files should use different trees unless both files share 
	 * the same character frequencies. 
	 * 
	 * @param s: This is the array of symbols.
	 * @param freq: This is the array of the frequencies of each symbol.
	 * @return
	 */
	public static BinaryNode<Data> build_Huffman_Tree(char[] s, double[] freq){

		//Create a new priority Queue
		PriorityQueue<BinaryNode<Data>> priorityQ = new PriorityQueue<BinaryNode<Data>>(
				new Comparator<BinaryNode<Data>>() {

					public int compare(BinaryNode<Data> o1, BinaryNode<Data> o2) {
						int answer = Double.compare(o1.getElement().getFreq(), o2.getElement().getFreq());
						return answer;
					}
				});

		//Populate the queue using the data from both arrays.
		for(int i = 0; i < s.length; i++){
			priorityQ.add(new BinaryNode<Data>(new Data(s[i], freq[i]), null, null));
		}

		//initialize nodeX and nodeY by retrieving two minimum nodes from the priorityQ and
		//create a new node named newTreeNode and set nodeX and nodeY as its children.  
		while(priorityQ.size() > 1){
			BinaryNode<Data> nodeX = priorityQ.remove();
			BinaryNode<Data> nodeY = priorityQ.remove();

			BinaryNode<Data> newTreeNode = new BinaryNode<Data>(
					new Data(' ', nodeX.getElement().getFreq() + nodeY.getElement().getFreq()),
					nodeX, nodeY);

			priorityQ.add(newTreeNode);
		}
		return priorityQ.peek();
	}




	/** The following program takes an array of symbols and a huffman tree node and creates an
	 * encoding table which contains the huffman string representation of each symbol.
	 * 
	 * @param s: The set of unique symbols to be translated into huffman strings.
	 * @param root: This is the root of the huffman tree which is needed to construct the table.
	 * @return: This function returns an encoding table which contains a string representation of every 
	 * symbol entered or it throws a no such element exception if the function encounters an entry in
	 * one of the tree leaves that is not present in the given symbols array. 
	 */
	public static Encode[] getKey(char[] s, BinaryNode<Data> root){

		List<Encode> codesTable = new ArrayList<Encode>();
		findCode(codesTable, s, root, "");

		Encode [] tableAsArray = new Encode[codesTable.size()];
		codesTable.toArray(tableAsArray);

		return tableAsArray;
	}

	private static void findCode(List<Encode> codes, char [] symbols, 
			BinaryNode<Data> root, String path){
		try {
			if(root.getLeft() == null && root.getRight() == null){

				int index = isPresent(symbols, root.getElement().getSymbol());

				if(index >= 0){
					codes.add(new Encode(symbols[index], path));	
					System.out.println("Letter: " + symbols[index] + " Path: " + path );
				} else throw new IllegalArgumentException("ERROR, The symbol " + root.getElement().getSymbol()
						+ " is not present in the given array " + Arrays.toString(symbols));
			}
			if(root.getLeft() != null){
				findCode(codes, symbols, root.getLeft(), path + "0");
			}
			if(root.getRight() != null){
				findCode(codes, symbols, root.getRight(), path + "1");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/** This function verifies if the character characterToFind is present in the given
	 *  character array.
	 * 
	 * @param symbols: This is the array of symbols given to translate into huffman strings.
	 * @param charToFind: This s the symbol to be searched for in the given character array symbol.
	 * @return: This function returns the index of occurrence of the character to find or -1 if 
	 * it is not found.
	 */
	private static int isPresent(char [] symbols, char charToFind){
		int answer = -1;
		for(int i=0; i< symbols.length; i++){
			if(((Character)symbols[i]).equals(charToFind)){
				answer = i;
				break;
			}
		}
		return answer;
	}

	/**
	 *  that takes as input a string containing and encodes it according to
    code. Note that the output contains only 0's and 1's.
    Throw an illegal argument exception if this is not possible.
	 */
	/**The following function takes a String of symbols to be encoded and 
	 * translates it using an encoding table. The program returns the encoded
	 * string if possible, and throws a no such element exception if there are
	 * symbols in the given string that have no possible translation in the 
	 * encoding table given.   
	 * @param in: This is the string to be encrypted using the encryption table.
	 * @param code: This is the table which will be used to encrypt the given string.
	 * @return The encoded string if it was possible to encode in full, or an exception
	 * there is a symbol which does not have a translation in the given table.
	 */
	public static String encrypt(String in, Encode[] code){
		String answer = "";
		int count = 0;
		try{
			while(count < in.length()){
				Character currentChar = in.charAt(count);
				int index = getIndexOfOcurrence(code, currentChar);

				if(index >= 0){
					answer+= code[index].getCode();
				}else{
					throw new IllegalArgumentException("The elemenent " 
							+ currentChar + " is not present int the list " + in);
				}
				count++;
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return answer;
	}

	private static int getIndexOfOcurrence(Encode [] code, char characterToFind){
		int answer = -1;
		for(int i=0; i<code.length; i++){
			if(code[i].getSymbol() == characterToFind){
				answer = i;
				break;
			}
		}
		return answer;
	}



	/**
	 * takes as input a string of 0'1 and 1's and returns the original string.
	 * Throw an illegal argument exception if this is not possible.
	 */
	/**The following function takes in two parameters, an encoded string of zeroes 
	 * and ones and a decoding table, and outputs (if possible ) a decoded string
	 * by using the given encoding table. 
	 * 
	 * @param in: This is the string to be decoded.
	 * @param code: This is the encryption table.
	 * @return
	 */
	private static String decrypt(String in, Encode[] code){
		String decryptedString = "";
		int givenStringIndex = 0;
		int tableIndex = 0;
		
		try{
			while(givenStringIndex < in.length() && tableIndex < code.length){
				boolean foundMatch = false;
				int codeIndex = 0;
				while(codeIndex < code[tableIndex].getCode().length() 
						&& givenStringIndex < in.length()
						&& tableIndex < code.length
						&& (code[tableIndex].getCode()).charAt(codeIndex) == in.charAt(givenStringIndex)){

					givenStringIndex++;
					codeIndex++;
					foundMatch = true;
				}
				if(foundMatch && codeIndex == code[tableIndex].getCode().length()){
					decryptedString += code[tableIndex].getSymbol();
					tableIndex = 0;
				}else if(codeIndex != code[tableIndex].getCode().length()){
					givenStringIndex-=codeIndex;
					tableIndex++;
				}
			}
			if(tableIndex >= code.length){
				throw new IllegalArgumentException("Error, the substring \"" + in.substring(givenStringIndex) 
				+ "\" could not be translated, since it doesnt have a proper translation in the given table.");
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return decryptedString;
	}
}








