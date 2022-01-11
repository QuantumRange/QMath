package de.quantumrange.qmath.models;

public interface QFunction {

	void setVariable(String name, double value);
	double evaluate();

}
