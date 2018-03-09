package me.gosimple.nbvcxz;

import java.math.BigDecimal;
import java.math.RoundingMode;

import me.gosimple.nbvcxz.scoring.Result;

public class RequirementChecker {
	
	static boolean strongEnough(String password) {
		Nbvcxz nbvcxz = new Nbvcxz();
		Result result = nbvcxz.estimate(password);
		BigDecimal guesses = result.getGuesses();
		Double threshold = Math.pow(10, 12);
		BigDecimal thresholdinBD = new BigDecimal(threshold.isInfinite() ? Double.MAX_VALUE : threshold).setScale(0, RoundingMode.HALF_UP);
		
		//if greater than or equal to 
		boolean isStrong = (guesses.compareTo(thresholdinBD) > -1);
		return isStrong;
	}

}
