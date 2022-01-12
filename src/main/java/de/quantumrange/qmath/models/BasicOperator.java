package de.quantumrange.qmath.models;

public enum BasicOperator implements QOperator {

	ADD("+", 1),
	SUBTRACT("-", 1),
	MULTIPLY("*", 2),
	DIVIDE("/", 2);

	private final String operator;
	private final int priority;

	BasicOperator(String operator, int priority) {
		this.operator = operator;
		this.priority = priority;
	}

	public String getOperator() {
		return operator;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public double evaluate(double left, double right) {
		return switch (this) {
			case ADD -> left + right;
			case SUBTRACT -> left - right;
			case MULTIPLY -> left * right;
			case DIVIDE -> left / right;
		};
	}

	@Override
	public String toMathString() {
		return operator;
	}
}
