package app;

import java.util.BitSet;

class ThinkBoard implements IBoard{

	private BitSet playerMask;
	private BitSet mask;
	private final int HEIGHT;
	private int count;
	private int pos;
	private int i;

	ThinkBoard(BitSet playerMask, BitSet mask, int height) {

		this.playerMask = playerMask;
		this.mask = mask;
		this.HEIGHT = height;
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
		return 0;
	}

	@Override
	public boolean checkWinner() {

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

			return true;
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

			return true;
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

			return true;
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

		return count >= WINCOUNT;
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
