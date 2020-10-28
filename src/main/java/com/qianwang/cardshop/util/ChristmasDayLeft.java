package com.qianwang.cardshop.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * This class provides method to calculate days to next Christmas day
 * @author qianwang
 *
 */
public class ChristmasDayLeft {
	
	public static long now() {
		LocalDate currentDate = LocalDate.now();
		LocalDate chrismasDate = LocalDate.of(currentDate.getYear(), 12, 25);
		
		if(currentDate.isAfter(chrismasDate)) {
			chrismasDate = LocalDate.of(currentDate.getYear()+1, 12, 25);
		}
		
		long noOfDaysBetween = ChronoUnit.DAYS.between(currentDate, chrismasDate);
		return noOfDaysBetween;
	}
}
