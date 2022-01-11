package de.quantumrange.qmath.models.impl;

import de.quantumrange.qmath.models.QFunction;

public class FinalFunction implements QFunction {

	private final double result;

	public FinalFunction(double result) {
		this.result = result;
	}

	@Override
	public void setVariable(String name, double value) {

	}

	@Override
	public double evaluate() {
		return result;
	}
}
