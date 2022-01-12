package de.quantumrange.qmath;

import de.quantumrange.qmath.exception.MathException;
import de.quantumrange.qmath.models.QFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringParserTest {

	@Test
	void build() {
		StringParser parser = new StringParser("-x+1+x-5⭐+3x-(6-3*(5-(9x)))");

		parser.variables("⭐", "x");

		try {
			QFunction build = parser.build();

			build.setVariable("x", 20.0);
			build.setVariable("⭐", 5.0);

			System.out.println(build);
			System.out.println(build.evaluate());
		} catch (MathException e) {
			e.printStackTrace();
		}

	}

}