package de.quantumrange.qmath.models.impl;

import java.util.HashMap;
import java.util.Map;

public class MathContext {

	private final Map<String, Double> variables;

	public MathContext() {
		this.variables = new HashMap<>();
	}

	public void put(String variableName, double value) {
		variables.put(variableName, value);
	}

	public double get(String variableName) {
		return variables.get(variableName);
	}

	// TODO: Better interaction

}
