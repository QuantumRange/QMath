package de.quantumrange.qmath;

import de.quantumrange.qmath.exception.MathException;
import de.quantumrange.qmath.models.QFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringParserTest {

	@Test
	void build() {
		StringParser parser = new StringParser("1+x-5⭐️+3x-(6-3*(5-(9x)))");

		parser.variables("⭐️", "x");

		try {
			QFunction build = parser.build();
			System.out.println(build.evaluate());
		} catch (MathException e) {
			e.printStackTrace();
		}

	}

}