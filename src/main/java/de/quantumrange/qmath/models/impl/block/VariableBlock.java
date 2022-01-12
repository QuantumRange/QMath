package de.quantumrange.qmath.models.impl.block;

import de.quantumrange.qmath.models.Block;
import de.quantumrange.qmath.models.impl.MathContext;

public class VariableBlock extends Block {

	/**
	 * true = +
	 * false = -
	 */
	private boolean sign;
	private final String variableName;

	public VariableBlock(String variableName) {
		this.variableName = variableName;
		this.sign = true;
	}

	/**
	 *
	 * @param variableName is the variablen name.
	 * @param sign is the sign of the variable, true is + and false is -.
	 */
	public VariableBlock(String variableName, boolean sign) {
		this.variableName = variableName;
		this.sign = sign;
	}

	@Override
	public double evaluate(MathContext context) {
		return context.get(variableName);
	}

	@Override
	public int getVariableCount() {
		return 1;
	}

	@Override
	public String toString() {
		return (sign ? "" : "-") + variableName;
	}

	public boolean isSign() {
		return sign;
	}

	public void setSign(boolean sign) {
		this.sign = sign;
	}
}
