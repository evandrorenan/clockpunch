package br.com.evandrorenan.learning.clockpunch.exception;

public class PunchNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -4482133671641516380L;

	public PunchNotFoundException(String message) {
		super(message);
	}
}
