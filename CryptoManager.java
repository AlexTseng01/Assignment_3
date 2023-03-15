/*
 * Class :CMSC203
 * Instructor: Dr. Monshi
 * Description: Takes user input of a text message and depending on which cipher they choose, it will encrypt message.
 * Users can also decrypt the message. Any invalid inputs not within ASCII " " and "_" will return an error message.
 * Due: 3/15/2023
 * Platform/compiler: eclipse
 * I pledge that I have completed the programming 
 * assignment independently. I have not copied the code 
 * from a student or any source. I have not given my code 
 * to any student.
 * Name: Alex Tseng
 */

/**
 * This is a utility class that encrypts and decrypts a phrase using two
 * different approaches. The first approach is called the Caesar Cipher and is a
 * simple “substitution cipher” where characters in a message are replaced by a
 * substitute character. The second approach, due to Giovan Battista Bellaso,
 * uses a key word, where each character in the word specifies the offset for
 * the corresponding character in the message, with the key word wrapping around
 * as needed.
 * 
 * @author Farnaz Eivazi
 * @version 7/16/2022
 */
public class CryptoManager {
	
	private static final char LOWER_RANGE = ' ';
	private static final char UPPER_RANGE = '_';
	private static final int RANGE = UPPER_RANGE - LOWER_RANGE + 1;

	/**
	 * This method determines if a string is within the allowable bounds of ASCII codes 
	 * according to the LOWER_RANGE and UPPER_RANGE characters
	 * @param plainText a string to be encrypted, if it is within the allowable bounds
	 * @return true if all characters are within the allowable bounds, false if any character is outside
	 */
	public static boolean isStringInBounds (String plainText) {
		int counter = 0;
		
		for (int i = 0; i < plainText.length(); i++) {
			if (plainText.charAt(i) >= LOWER_RANGE && plainText.charAt(i) <= UPPER_RANGE) {
				counter++;
			}
		}
		
		// if counter = plainText.length(), all characters inside plainText are within bounds
		if (counter == plainText.length()) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Encrypts a string according to the Caesar Cipher.  The integer key specifies an offset
	 * and each character in plainText is replaced by the character \"offset\" away from it 
	 * @param plainText an uppercase string to be encrypted.
	 * @param key an integer that specifies the offset of each character
	 * @return the encrypted string
	 */
	public static String caesarEncryption(String plainText, int key) {
		// check validity
		if (isStringInBounds(plainText)) {
			char[] temp = new char[plainText.length()]; // create a new array to store encrypted characters
			
			// takes the i-th character from original text and adds key, checking boundaries
			for (int i = 0; i < plainText.length(); i++) {
				temp[i] = plainText.charAt(i);
				temp[i] += key;
				
				while (temp[i] > UPPER_RANGE) {
					temp[i] -= RANGE;
				}
			}
			
			String newText = "";
			
			// adding everything from the temp[] list to the new text
			for (int i = 0; i < plainText.length(); i++) {
				newText += temp[i];
			}
			
			return newText;
		}
		else {
			return "The selected string is not in bounds, Try again.";
		}
	}
	
	/**
	 * Encrypts a string according the Bellaso Cipher.  Each character in plainText is offset 
	 * according to the ASCII value of the corresponding character in bellasoStr, which is repeated
	 * to correspond to the length of plainText
	 * @param plainText an uppercase string to be encrypted.
	 * @param bellasoStr an uppercase string that specifies the offsets, character by character.
	 * @return the encrypted string
	 */
	public static String bellasoEncryption (String plainText, String bellasoStr) {
		// check validity
		if (isStringInBounds(plainText)) {
			String newText = "";
			int difference = plainText.length() - bellasoStr.length(); // gets new key length
		
			// create new key
			for (int i = 0, count = 0; i < difference; i++, count++) {
				if (count == bellasoStr.length()) {
					count = 0;
				}
				bellasoStr += bellasoStr.charAt(count);
			}

			// use the offset of original text and the new key to get a new character
			for (int i = 0; i < plainText.length(); i++) {
				int offset = plainText.charAt(i) + bellasoStr.charAt(i);
				while (offset > UPPER_RANGE) {
					offset -= RANGE;
				}
				newText += (char)offset;
			}
		
			return newText;
		}
		else {
			return "The selected string is not in bounds, Try again.";
		}
	}
		
	
	/**
	 * Decrypts a string according to the Caesar Cipher.  The integer key specifies an offset
	 * and each character in encryptedText is replaced by the character \"offset\" characters before it.
	 * This is the inverse of the encryptCaesar method.
	 * @param encryptedText an encrypted string to be decrypted.
	 * @param key an integer that specifies the offset of each character
	 * @return the plain text string
	 */
	public static String caesarDecryption (String encryptedText, int key) {
		char[] temp = new char[encryptedText.length()]; // create a new array to store decrypted characters
		
		// for each i-th element, subtrac thte key and test if new character is above or below the range limits
		for (int i = 0; i < encryptedText.length(); i++) {
			temp[i] = encryptedText.charAt(i);
			temp[i] -= key;
			while (temp[i] > UPPER_RANGE || temp[i] < LOWER_RANGE) {
				temp[i] -= RANGE;
			}
		}
		
		String newText = "";
		
		// adding everything from temp[] list to new text
		for (int i = 0; i < encryptedText.length(); i++) {
			newText += temp[i];
		}
		
		return newText;
	}
	
	/**
	 * Decrypts a string according the Bellaso Cipher.  Each character in encryptedText is replaced by
	 * the character corresponding to the character in bellasoStr, which is repeated
	 * to correspond to the length of plainText.  This is the inverse of the encryptBellaso method.
	 * @param encryptedText an uppercase string to be encrypted.
	 * @param bellasoStr an uppercase string that specifies the offsets, character by character.
	 * @return the decrypted string
	 */
	public static String bellasoDecryption(String encryptedText, String bellasoStr) {
		String newText = "";
		
		int difference = encryptedText.length() - bellasoStr.length(); // gets the original key length
		
		// construct the original key
		for (int i = 0, count = 0; i < difference; i++, count++) {
			if (count == bellasoStr.length()) {
				count = 0;
			}
			bellasoStr += bellasoStr.charAt(count);
		}

		// use the offset of the encrypted text and the original key to find original characters
		for (int i = 0; i < encryptedText.length(); i++) {
			int offset = encryptedText.charAt(i) - bellasoStr.charAt(i);
			while (offset < LOWER_RANGE) {
				offset += RANGE;
			}

			newText += (char)offset;
		}
		
		return newText;
	}
}
