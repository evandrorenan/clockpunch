package br.com.evandrorenan.learning.clockpunch.utils;

import java.util.Calendar;
import java.util.Date;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public final class ClockPunchUtils {
	
	private ClockPunchUtils() {		
	}
	
	public static String getMessage(String propertyName, MessageSource messageSource) {

	    return messageSource.getMessage(propertyName, null, LocaleContextHolder.getLocale());
	}
	
	public static Date date(int year, int month, int date, int hour, int minutes, int seconds) {
		Calendar working = Calendar.getInstance();		
		working.set(year, month, date, hour, minutes, seconds);
		return working.getTime();
	}	
	
}