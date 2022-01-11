package de.quantumrange.qmath.models;

import de.quantumrange.qmath.models.impl.MathContext;

public abstract class Block {

	public abstract double evaluate(MathContext context);
	public abstract int getVariableCount();

}
