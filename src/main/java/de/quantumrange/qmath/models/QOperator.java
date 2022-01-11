package de.quantumrange.qmath.models;

public interface QOperator {

	int getPriority();
	double evaluate(double left, double right);

}
