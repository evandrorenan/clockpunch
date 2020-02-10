package br.com.evandrorenan.learning.clockpunch;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public final class ClockPunchUtils {
	
	private ClockPunchUtils() {		
	}
	
	public static String getMessage(String propertyName, MessageSource messageSource) {

	    return messageSource.getMessage(propertyName, null, LocaleContextHolder.getLocale());
	}
}
