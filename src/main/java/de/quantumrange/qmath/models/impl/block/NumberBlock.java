package de.quantumrange.qmath.models.impl.block;

import de.quantumrange.qmath.models.Block;
import de.quantumrange.qmath.models.impl.MathContext;

public class NumberBlock extends Block {

	private final double value;

	public NumberBlock(double value) {
		this.value = value;
	}

	@Override
	public double evaluate(MathContext context) {
		return value;
	}

	@Override
	public int getVariableCount() {
		return 0;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
