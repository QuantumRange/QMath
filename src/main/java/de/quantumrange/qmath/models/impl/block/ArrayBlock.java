package de.quantumrange.qmath.models.impl.block;

import de.quantumrange.qmath.models.Block;
import de.quantumrange.qmath.models.QOperator;
import de.quantumrange.qmath.models.impl.MathContext;

import java.util.Arrays;
import java.util.List;

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
		double sum = 0.0;

		for (int i = 0; i < blocks.length; i++) {
			
		}

		return sum;
	}

	@Override
	public int getVariableCount() {
		return Arrays.stream(blocks)
				.mapToInt(Block::getVariableCount)
				.distinct()
				.max()
				.orElse(0);
	}
}
