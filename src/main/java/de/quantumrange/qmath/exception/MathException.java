package de.quantumrange.qmath.exception;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class MathException extends Exception {

	private int hint;
	private String expression;

	public MathException(String message, int hint, String expression) {
		super("""
				%s
				%s
				%s^""".formatted(
				message,
				expression,
				" ".repeat(hint)));
	}

	public String getExpression() {
		return expression;
	}

	public int getHint() {
		return hint;
	}
}
