package de.quantumrange.qmath;

import de.quantumrange.qmath.exception.MathException;
import de.quantumrange.qmath.models.QFunction;

public interface MathParser {

	void variables(String... names);

	QFunction build() throws MathException;

}
