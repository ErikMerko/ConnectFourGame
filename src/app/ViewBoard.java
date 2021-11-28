package app;

import java.util.BitSet;
import java.util.stream.IntStream;

import stdLib_presentation.IPresentation;


class ViewBoard implements IBoard{

	private BitSet playerMask;
	private BitSet mask;
	private final int HEIGHT;
	private IPresentation presentation;
	private int count;
	private int pos;
	private int i;

	ViewBoard(IPresentation presentation) {

		this.presentation = presentation;
		this.playerMask = new BitSet();
		this.mask = new BitSet();
		this.HEIGHT = Property.getRowCount();
	}

	@Override
	public boolean canPlay(int column) {
		
		return !mask.get(((column - 1) * HEIGHT) + (HEIGHT - 1));
	}

	@Override
	public int playColumn(int column) {
		
		mask.set(mask.nextClearBit(((column - 1) * HEIGHT)));
		playerMask.xor(mask);
		pos = mask.previousSetBit(((column - 1) * HEIGHT) + (HEIGHT - 1));
		checkWinnerInternally();
		return (pos % HEIGHT) + 1;
	}

	private void checkWinnerInternally() {
		
		// Vertical
		count = 1;
		for (i = pos - 1; ((i + 1) % HEIGHT != 0); i--) {

			if (playerMask.get(i)) {
				count++;
			} else {
				break;
			}
		}

		if (count >= WINCOUNT) {

			mask.set(mask.size()-1);

			if (presentation != null) {

				i++;
				IntStream.range(0, count).forEach(e -> presentation.markWinner(((i + HEIGHT) / HEIGHT), ((i % HEIGHT) + 1) + e));
			}
		}

		// Horizontal left
		count = 1;
		for (i = pos - HEIGHT; i >= 0; i -= HEIGHT) {

			if (playerMask.get(i)) {
				count++;
			} else {
				break;
			}
		}

		// Horizontal right
		for (i = pos + HEIGHT; i < playerMask.length(); i += HEIGHT) {

			if (playerMask.get(i)) {
				count++;
			} else {
				break;
			}
		}

		if (count >= WINCOUNT) {

			mask.set(mask.size()-1);

			if (presentation != null) {

				i -= HEIGHT;
				IntStream.range(0, count).forEach(e -> presentation.markWinner(((i + HEIGHT) / HEIGHT) - e, (i % HEIGHT) + 1));
			}

		}

		// Left to right Diagonal upwards
		count = 1;
		for (i = pos + (HEIGHT + 1); (((i - (HEIGHT + 1)) % HEIGHT) != (HEIGHT - 1)) && i < playerMask.length(); i += (HEIGHT + 1)) {

			if (playerMask.get(i)) {
				count++;
			} else {
				break;
			}
		}

		// Left to right Diagonal downwards
		for (i = pos - (HEIGHT + 1); ((i + (HEIGHT + 1)) % HEIGHT != 0) && i >= 0; i -= (HEIGHT + 1)) {

			if (playerMask.get(i)) {
				count++;
			} else {
				break;
			}
		}

		if (count >= WINCOUNT) {

			mask.set(mask.size()-1);

			if (presentation != null) {

				i += (HEIGHT + 1);
				IntStream.range(0, count).forEach(e -> presentation.markWinner(((i + HEIGHT) / HEIGHT) + e, ((i % HEIGHT) + 1) + e));
			}

		}

		// Right to left Diagonal upwards
		count = 1;
		for (i = pos - (HEIGHT - 1); ((i + (HEIGHT - 1)) % HEIGHT != HEIGHT - 1) && i > 0; i -= (HEIGHT - 1)) {

			if (playerMask.get(i)) {
				count++;
			} else {
				break;
			}
		}

		// Right to left Diagonal downwards
		for (i = pos + (HEIGHT - 1); ((i - (HEIGHT - 1)) % HEIGHT != 0) && i < playerMask.length(); i += (HEIGHT - 1)) {

			if (playerMask.get(i)) {
				count++;
			} else {
				break;
			}
		}

		if (count >= WINCOUNT) {

			mask.set(mask.size()-1);

			if (presentation != null) {

				i -= (HEIGHT - 1);
				IntStream.range(0, count).forEach(e -> presentation.markWinner(((i + HEIGHT) / HEIGHT) - e, ((i % HEIGHT) + 1) + e));
			}

		}
	}

	@Override
	public boolean checkWinner() {
		
		return mask.get(mask.size()-1);
	}

	@Override
	public int getMoveCount() {

		return mask.cardinality();
	}

	@Override
	public IBoard getClone() {
		
		return new ThinkBoard(playerMask.get(0, playerMask.length()), mask.get(0, mask.length()), HEIGHT);
	}

}
