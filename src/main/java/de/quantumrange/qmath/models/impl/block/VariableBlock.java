package de.quantumrange.qmath.models.impl.block;

import de.quantumrange.qmath.models.Block;
import de.quantumrange.qmath.models.impl.MathContext;

public class VariableBlock extends Block {

	private final String variableName;

	public VariableBlock(String variableName) {
		this.variableName = variableName;
	}


	@Override
	public double evaluate(MathContext context) {
		return context.get(variableName);
	}

	@Override
	public int getVariableCount() {
		return 1;
	}
}
