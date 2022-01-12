package de.quantumrange.qmath;

import de.quantumrange.qmath.exception.MathException;
import de.quantumrange.qmath.models.BasicOperator;
import de.quantumrange.qmath.models.Block;
import de.quantumrange.qmath.models.QFunction;
import de.quantumrange.qmath.models.QOperator;
import de.quantumrange.qmath.models.impl.BlockFunction;
import de.quantumrange.qmath.models.impl.FinalFunction;
import de.quantumrange.qmath.models.impl.MathContext;
import de.quantumrange.qmath.models.impl.block.ArrayBlock;
import de.quantumrange.qmath.models.impl.block.NumberBlock;
import de.quantumrange.qmath.models.impl.block.VariableBlock;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static de.quantumrange.qmath.models.impl.MathContext.EMPTY;
import static java.lang.Math.*;
import static java.util.Objects.requireNonNullElse;

public class StringParser implements MathParser {

	private final boolean debugFlag;
	private final String expression;
	private final List<String> variables;

	public StringParser(@NotNull String expression) {
		this(expression, true);
	}

	public StringParser(String expression, boolean debugFlag) {
		this.debugFlag = debugFlag;
		this.expression = expression;
		this.variables = new ArrayList<>();
	}

	@Override
	public void variables(@NotNull String... names) {
		variables.addAll(Arrays.asList(names));
	}

	@Override
	public @NotNull QFunction build() throws MathException {
		Block block = parse(expression);

		if (variables.isEmpty()) {
			return new FinalFunction(block.evaluate(EMPTY));
		}

		return new BlockFunction(block);
	}

	// TODO: Rename?
	private @NotNull Block parse(@NotNull String str) throws MathException {
		int depth = 0;
		StringBuilder buffer = new StringBuilder();

		boolean isNumber = false;
		List<Block> blocks = new ArrayList<>();
		List<QOperator> operators = new ArrayList<>();

		if (debugFlag) {
			int i = 0;

			for (char c : str.toCharArray()) {
				if (c == '(') i++;
				else if (c == ')') i--;
			}

			if (i != 0) {
				throw new MathException("%d too many %s brackets.".formatted(abs(i), i > 0 ? "Opening" : "Closing"),
						i < 0 ? str.length() - 1 : str.indexOf('('), str);
			}
		}

		int i = -1;

		str += "+0";

		for (char c : str.toCharArray()) {
			i++;
			if (c == '(') {
				if (depth == 0) buffer = new StringBuilder();

				depth++;

				if (depth == 1) continue;
			} else if (c == ')') {
				depth--;

				if (depth == 0) {
					blocks.add(parse(buffer.toString()));
					buffer = new StringBuilder();
					continue;
				}
			}

			if (depth != 0) {
				buffer.append(c);
				continue;
			}

			if (Character.isDigit(c)) {
				buffer.append(c);
				isNumber = true;
			} else {
				if (!buffer.isEmpty()) {
					BasicOperator operator = Arrays.stream(BasicOperator.values())
							.filter(b -> b.getOperator().equals(String.valueOf(c)))
							.findFirst()
							.orElse(null);

					if (isNumber) {
						double num = Double.parseDouble(buffer.toString());

						blocks.add(new NumberBlock(num));
						isNumber = false;
						buffer = new StringBuilder();

						if (operator == null) buffer.append(c);
					} else {
						if (operator != null || Character.isDigit(c)) {
							if (variables.contains(buffer.toString())) {
								blocks.add(new VariableBlock(buffer.toString()));
							} else {
								throw new MathException("Variable '%s' not found!".formatted(buffer.toString()), i, str);
							}

							buffer = new StringBuilder();
						}
					}

					operators.add(operator != null ? operator : BasicOperator.MULTIPLY);
				} else {
					buffer.append(c);
				}
			}
		}

		Block b = correct(new ArrayBlock(blocks, operators));

		System.out.println(str + " -> " + b);

		return abbreviate(b);
	}

	private Block correct(Block b) {
		if (b instanceof ArrayBlock arr) {
			Block[] blocks = arr.getBlocks();
			QOperator[] operators = arr.getOperators();

			for (int i = 0; i < blocks.length; i++) {
				blocks[i] = correct(blocks[i]);
			}


		}
		return b;
	}

	private Block abbreviate(Block b) {
		if (b instanceof ArrayBlock arr) {
			if (arr.getVariableCount() == 0) {
				return new NumberBlock(arr.evaluate(EMPTY));
			} else {
				for (int i = 0; i < arr.getBlocks().length; i++) {
					arr.getBlocks()[i] = abbreviate(arr.getBlocks()[i]);
				}

				arr.abbreviate();
			}
		}

		return b;
	}

}
