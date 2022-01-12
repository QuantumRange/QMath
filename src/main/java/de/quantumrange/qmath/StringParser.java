package de.quantumrange.qmath;

import de.quantumrange.qmath.exception.MathException;
import de.quantumrange.qmath.models.BasicOperator;
import de.quantumrange.qmath.models.Block;
import de.quantumrange.qmath.models.QFunction;
import de.quantumrange.qmath.models.QOperator;
import de.quantumrange.qmath.models.impl.BlockFunction;
import de.quantumrange.qmath.models.impl.FinalFunction;
import de.quantumrange.qmath.models.impl.block.ArrayBlock;
import de.quantumrange.qmath.models.impl.block.NumberBlock;
import de.quantumrange.qmath.models.impl.block.VariableBlock;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static de.quantumrange.qmath.models.BasicOperator.*;
import static de.quantumrange.qmath.models.impl.MathContext.EMPTY;
import static java.lang.Math.*;

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
	public MathParser variables(@NotNull String... names) {
		variables.addAll(Arrays.asList(names));
		return this;
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

		boolean isNumber = false;
		int depth = 0;
		StringBuilder buffer = new StringBuilder();

		List<Block> blocks = new ArrayList<>();
		List<QOperator> operators = new ArrayList<>();

		int i = -1;
		// str += "+0";

		BasicOperator operator = null;

		for (char c : str.toCharArray()) {
			i++;

			if (c == '(') {
				depth++;

				if (depth == 1) {
					buffer = new StringBuilder();
					continue;
				}
			} else if (c == ')') {
				depth--;

				if (depth == 0) {
					blocks.add(parse(buffer.toString()));
					System.out.println("### " + operator);
					buffer = new StringBuilder();
					continue;
				}
  			}

			if (depth == 0) {
				BasicOperator op = valueOfOperator(buffer.toString());
				if (op != null) {
					operator = op;
					buffer = new StringBuilder();
				} else if ((op = valueOfOperator(String.valueOf(c))) != null && !buffer.isEmpty()) {
					operator = op;

					String variableName = buffer.toString();
					buffer = new StringBuilder();
					StringBuilder variableBuffer = new StringBuilder();

					for (char vC : variableName.toCharArray()) {
						if (Character.isDigit(vC)) buffer.append(vC);
						else variableBuffer.append(vC);
					}

					try {
						if (!buffer.isEmpty()) {
							double num = Double.parseDouble(buffer.toString());

							if (operator == SUBTRACT) {
								operator = ADD;
								num *= -1;
							}

							blocks.add(new NumberBlock(num));
							operators.add(operator);
							operator = null;
						}
					} catch (Exception e) {
						throw new MathException("Cant parse number.", i, str);
					}

					if (!variableBuffer.isEmpty()) {
						if (variables.contains(variableBuffer.toString())) {
							if (operator == null) operator = MULTIPLY;

							// TODO

							blocks.add(new VariableBlock(variableBuffer.toString(), operator != SUBTRACT));
							operators.add(operator == SUBTRACT ? ADD : operator);
							operator = null;
						} else {
							throw new MathException("Cant find variable '%s'.".formatted(variableBuffer.toString()), i, str);
						}
					}

					buffer = new StringBuilder();
				}
			}

			buffer.append(c);

			System.out.println("===========");
			System.out.println(buffer + " << " + operator + " << " + c);
			System.out.println(blocks);
			System.out.println(operators);
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
