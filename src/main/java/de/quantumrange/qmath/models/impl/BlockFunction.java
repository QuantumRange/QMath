package de.quantumrange.qmath.models.impl;

import de.quantumrange.qmath.models.Block;
import de.quantumrange.qmath.models.QFunction;

public class BlockFunction implements QFunction {

	private final MathContext context;
	private final Block root;

	public BlockFunction(Block root) {
		this.root = root;
		this.context = new MathContext();
	}

	@Override
	public void setVariable(String name, double value) {
		// TODO
	}

	@Override
	public double evaluate() {
		return root.evaluate(context);
	}

}
