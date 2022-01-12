package de.quantumrange.qmath.models.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MathContext {

	public static final MathContext EMPTY = new MathContext();

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

	@Override
	public String toString() {
		return '{' +
				variables.keySet().stream()
						.map(key -> key + "=" + variables.get(key))
						.collect(Collectors.joining(",")) +
				'}';
	}

}
