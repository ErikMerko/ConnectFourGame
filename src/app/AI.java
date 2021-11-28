package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class AI{

	private IBoard board;
	private final int WIDTH;
	private final int HEIGHT;

	AI(IBoard board) {

		this.board = board;
		this.WIDTH = Property.getColumnCount();
		this.HEIGHT = Property.getRowCount();
	}

	private int negamax(IBoard board, int depth, int alpha, int beta) {

		if (depth == 0 || board.getMoveCount() == WIDTH * HEIGHT) {
			return 0;
		}

		if (board.checkWinner()) {

			return ((WIDTH * HEIGHT) - board.getMoveCount()) * (-1);

		}

		int maxEval = Integer.MIN_VALUE;

		for (IBoard c : getChildsOf(board)) {

			int eval = negamax(c, depth - 1, beta * (-1), alpha * (-1)) * (-1);
			maxEval = Integer.max(maxEval, eval);
			alpha = Integer.max(alpha, eval);

			if (beta <= alpha) {
				break;
			}

		};

		return maxEval;

	}

	private List<IBoard> getChildsOf(IBoard board) {

		List<IBoard> childs = new ArrayList<>();

		IntStream.rangeClosed(1, WIDTH).forEach(i -> {

			if (board.canPlay(i)) {

				IBoard clone = board.getClone();
				clone.playColumn(i);
				childs.add(clone);
			}
		});

		List<IBoard> sortedChilds = new ArrayList<>();

		while (!childs.isEmpty()) {

			IBoard e = childs.get(childs.size() / 2);
			childs.remove(childs.size() / 2);
			sortedChilds.add(e);
		}

		return sortedChilds;
	}
	

	int think() {

		Map<Integer, Integer> map = new HashMap<>();
		IBoard clone;
		int score;

		for (int i = 1; i <= WIDTH; i++) {

			if (board.canPlay(i)) {

				clone = board.getClone();
				clone.playColumn(i);

				score = negamax(clone, 10, Integer.MIN_VALUE, Integer.MAX_VALUE) * (-1);
				map.put(i, score);

			}

		}

		int max = Collections.max(map.values());
		List<Entry<Integer, Integer>> intList = map.entrySet().stream().filter(e -> e.getValue() == max).collect(Collectors.toList());
		return intList.get(intList.size() / 2).getKey();

	}
}	