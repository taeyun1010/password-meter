package me.gosimple.nbvcxz;

public class HanguelHandler {
	
	//for one letter
	static boolean isHanguel(char letter) {
		boolean result = false;
		String unicodeStr = Integer.toHexString(letter | 0x10000).substring(1);
		// System.out.println( "\\u" + unicodeStr);
		int unicode = Integer.parseInt(unicodeStr, 16);
		if (((unicode >= 0xAC00) && (unicode <= 0xD7A3))) {
			result = true;
		}
		return result;

	}
	
	//returns true if at least one letter in string is Hanguel
	static boolean isHanguel(String word) {
		boolean result = false;
		for (int i=0; i<word.length(); i++) {
			char thisChar = word.charAt(i);
			String unicodeStr = Integer.toHexString(thisChar | 0x10000).substring(1);
			// System.out.println( "\\u" + unicodeStr);
			int unicode = Integer.parseInt(unicodeStr, 16);
			if (((unicode >= 0xAC00) && (unicode <= 0xD7A3))) {
				result = true;
			}
		}
		return result;
	}
	
	static String convertToEng(String hanguel) {
		String result = "";
        //System.out.println(hanguel);
        //String hanguelLetters = Normalizer.normalize(hanguel, Normalizer.Form.NFD);
//        for (int i = 0; i < string.length(); i++) {
//            System.out.print(String.format("U+%04X ", string.codePointAt(i)));
//        }
        for (int i=0; i<hanguel.length(); i++) {
        	char thisChar = hanguel.charAt(i);
        	String partialResult = convertToEngHelper(thisChar);
        	result = result + partialResult;
        }
        //System.out.println(result);
        return result;
    }
	
	private static String convertToEngHelper(char hanguelLetter) {
		String partialResult = "";

		if(!isHanguel(hanguelLetter)) {
			partialResult = partialResult + hanguelLetter;
			return partialResult;
		}
		//
		//int tes = 9/10;
		//System.out.println(" 9/10 =   " + tes);
		String unicodeStr = Integer.toHexString(hanguelLetter | 0x10000).substring(1);
		//System.out.println( "\\u" + unicodeStr);
		int unicode = Integer.parseInt(unicodeStr, 16);
		unicode = unicode - 0xAC00;
		
		// 
		int firstletter = unicode /(21*28);
		
		//
		int thirdletter = (unicode % 28);
		
		//
		int secondletter = (unicode - thirdletter - (firstletter*21*28))/28;
		
		//System.out.println(firstletter);
		//System.out.println(secondletter);
		//System.out.println(thirdletter);
		
		String letterToBeAdded = "";
		switch(firstletter) {
		case 0:
			letterToBeAdded = "r";
			break;
		case 1:
			letterToBeAdded = "R";
			break;
		case 2:
			letterToBeAdded = "s";
			break;
		case 3:
			letterToBeAdded = "e";
			break;
		case 4:
			letterToBeAdded = "E";
			break;
		case 5:
			letterToBeAdded = "f";
			break;
		case 6:
			letterToBeAdded = "a";
			break;
		case 7:
			letterToBeAdded = "q";
			break;
		case 8:
			letterToBeAdded = "Q";
			break;
		case 9:
			letterToBeAdded = "t";
			break;
		case 10:
			letterToBeAdded = "T";
			break;
		case 11:
			letterToBeAdded = "d";
			break;
		case 12:
			letterToBeAdded = "w";
			break;
		case 13:
			letterToBeAdded = "W";
			break;
		case 14:
			letterToBeAdded = "c";
			break;
		case 15:
			letterToBeAdded = "z";
			break;
		case 16:
			letterToBeAdded = "x";
			break;
		case 17:
			letterToBeAdded = "v";
			break;
		case 18:
			letterToBeAdded = "g";
			break;
			
		}
		partialResult = partialResult + letterToBeAdded;
		
		switch(secondletter) {
		case 0:
			letterToBeAdded = "k";
			break;
		case 1:
			letterToBeAdded = "o";
			break;
		case 2:
			letterToBeAdded = "i";
			break;
		case 3:
			letterToBeAdded = "O";
			break;
		case 4:
			letterToBeAdded = "j";
			break;
		case 5:
			letterToBeAdded = "p";
			break;
		case 6:
			letterToBeAdded = "u";
			break;
		case 7:
			letterToBeAdded = "P";
			break;
		case 8:
			letterToBeAdded = "h";
			break;
		case 9:
			letterToBeAdded = "hk";
			break;
		case 10:
			letterToBeAdded = "ho";
			break;
		case 11:
			letterToBeAdded = "hl";
			break;
		case 12:
			letterToBeAdded = "y";
			break;
		case 13:
			letterToBeAdded = "n";
			break;
		case 14:
			letterToBeAdded = "nj";
			break;
		case 15:
			letterToBeAdded = "np";
			break;
		case 16:
			letterToBeAdded = "nl";
			break;
		case 17:
			letterToBeAdded = "b";
			break;
		case 18:
			letterToBeAdded = "m";
			break;
		case 19:
			letterToBeAdded = "ml";
			break;
		case 20:
			letterToBeAdded = "l";
			break;
			
		}
		partialResult = partialResult + letterToBeAdded;
		
		switch(thirdletter) {
		case 0:
			letterToBeAdded = "";
			break;
		case 1:
			letterToBeAdded = "r";
			break;
		case 2:
			letterToBeAdded = "R";
			break;
		case 3:
			letterToBeAdded = "rt";
			break;
		case 4:
			letterToBeAdded = "s";
			break;
		case 5:
			letterToBeAdded = "sw";
			break;
		case 6:
			letterToBeAdded = "sg";
			break;
		case 7:
			letterToBeAdded = "e";
			break;
		case 8:
			letterToBeAdded = "f";
			break;
		case 9:
			letterToBeAdded = "fr";
			break;
		case 10:
			letterToBeAdded = "fa";
			break;
		case 11:
			letterToBeAdded = "fq";
			break;
		case 12:
			letterToBeAdded = "ft";
			break;
		case 13:
			letterToBeAdded = "fx";
			break;
		case 14:
			letterToBeAdded = "fv";
			break;
		case 15:
			letterToBeAdded = "fg";
			break;
		case 16:
			letterToBeAdded = "a";
			break;
		case 17:
			letterToBeAdded = "q";
			break;
		case 18:
			letterToBeAdded = "qt";
			break;
		case 19:
			letterToBeAdded = "t";
			break;
		case 20:
			letterToBeAdded = "T";
			break;
		case 21:
			letterToBeAdded = "d";
			break;
		case 22:
			letterToBeAdded = "w";
			break;
		case 23:
			letterToBeAdded = "c";
			break;
		case 24:
			letterToBeAdded = "z";
			break;
		case 25:
			letterToBeAdded = "x";
			break;
		case 26:
			letterToBeAdded = "v";
			break;
		case 27:
			letterToBeAdded = "g";
			break;
			
		}
		partialResult = partialResult + letterToBeAdded;
		
		//
		
		

		return partialResult;
	}
}
