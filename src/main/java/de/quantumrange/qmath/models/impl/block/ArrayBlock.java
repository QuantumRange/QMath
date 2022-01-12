package de.quantumrange.qmath.models.impl.block;

import de.quantumrange.qmath.models.BasicOperator;
import de.quantumrange.qmath.models.Block;
import de.quantumrange.qmath.models.QOperator;
import de.quantumrange.qmath.models.impl.MathContext;

import java.util.Arrays;
import java.util.List;

import static de.quantumrange.qmath.models.BasicOperator.SUBTRACT;
import static de.quantumrange.qmath.models.impl.MathContext.EMPTY;

public class ArrayBlock extends Block {

	private final Block[] blocks;
	private final QOperator[] operators;

	public ArrayBlock(List<Block> blocks, List<QOperator> operators) {
		this(blocks.toArray(Block[]::new),
				operators.toArray(QOperator[]::new));
	}

	public ArrayBlock(Block[] blocks, QOperator[] operators) {
		this.blocks = blocks;
		this.operators = operators;
	}

	@Override
	public double evaluate(MathContext context) {
		if (blocks.length == 0) return Double.NaN;

		double sum = blocks[0].evaluate(context);

		for (int i = 1; i < blocks.length; i++) {
			sum = operators[i - 1].evaluate(sum, blocks[i].evaluate(context));
		}

		return sum;
	}

	@Override
	public int getVariableCount() {
		return Arrays.stream(blocks)
				.mapToInt(Block::getVariableCount)
				.sum();
	}

	public Block[] getBlocks() {
		return blocks;
	}

	public QOperator[] getOperators() {
		return operators;
	}

	@Override
	public String toString() {
		if (blocks.length == 0) return "";

		StringBuilder sb = new StringBuilder(blocks[0].toString());

		for (int i = 1; i < blocks.length; i++) {
			boolean arrayBlock = blocks[i] instanceof ArrayBlock;

			sb.append(operators[i - 1].toMathString());

			if (arrayBlock) sb.append('(');
			sb.append(blocks[i]);
			if (arrayBlock) sb.append(')');
		}

		return sb.toString();
	}

	public void abbreviate() {
		for (int i = 1; i < blocks.length; i++) {
			if (operators[i - 1] instanceof BasicOperator bo &&
				blocks[i] instanceof NumberBlock nb) {
				if (bo == SUBTRACT && nb.evaluate(EMPTY) < 0) {
					blocks[i] = new NumberBlock(-nb.evaluate(EMPTY));
				}

				operators[i - 1] = BasicOperator.ADD;
			}
		}
	}
}
