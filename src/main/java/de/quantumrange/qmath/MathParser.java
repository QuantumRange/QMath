package de.quantumrange.qmath;

import de.quantumrange.qmath.exception.MathException;
import de.quantumrange.qmath.models.QFunction;

public interface MathParser {

	MathParser variables(String... names);

	QFunction build() throws MathException;

}
