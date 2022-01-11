package de.quantumrange.qmath.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathExceptionTest {

	@Test
	void getExpression() {
		MathException exception = new MathException("", 5, "0123456789");

		assertEquals("0123456789", exception.getExpression());
	}


	@Test
	void testToString() {
		MathException exception = new MathException("No Number Found", 5, "x^2+y*");

		String string = exception.toString();

		assertTrue(string.contains("No Number Found"));
		assertTrue(string.contains("+y*"));
		assertTrue(string.contains("  ^"));
	}
}