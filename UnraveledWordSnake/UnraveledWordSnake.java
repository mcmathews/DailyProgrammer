package practice.reddit;

import java.util.*;

public class UnraveledWordSnake {

	private static List<String> solve(CharMap charMap, int x, int y, Direction previousDirection) {
		StringBuilder sb = new StringBuilder();
		sb.append(charMap.getChar(x, y));
		Set<Direction> availableDirections = new HashSet<>(Arrays.asList(Direction.values()));
		if (previousDirection != null) {
			availableDirections.remove(previousDirection.getOpposite());
		}
		Direction nextDirection = null;
		for (Direction d : availableDirections) {
			if (!charMap.isSpace(x, y, d)) {
				nextDirection = d;
				break;
			}
		}

		if (nextDirection == null) {
			return new LinkedList<>();
		}

		do {
			x += nextDirection.getMoveX();
			y += nextDirection.getMoveY();
			sb.append(charMap.getChar(x, y));
		} while (!charMap.isSpace(x, y, nextDirection));

		List<String> words = solve(charMap, x, y, nextDirection);
		words.add(0, sb.toString());
		return words;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int numLines = Integer.parseInt(in.nextLine());

		List<String> lines = new ArrayList<>();
		for (int i = 0; i < numLines; i++) {
			lines.add(in.nextLine());
		}
		CharMap charMap = new CharMap(lines);
		List<String> words = solve(charMap, 0, 0, null);
		System.out.println(String.join(" ", words));
	}

	public static class CharMap {

		private int width;
		private int height;
		private char[][] charMap;

		public CharMap(List<String> lines) {
			this.width = lines.stream().map(line -> line.length()).max(Integer::compare).get();
			this.height = lines.size();
			this.charMap = new char[width][height];
			for (int i = 0; i < lines.size(); i++) {
				for (int j = 0; j < this.width; j++) {
					this.charMap[j][i] = j < lines.get(i).length() ? lines.get(i).charAt(j) : ' ';
				}
			}
		}

		public char getChar(int x, int y) {
			return this.charMap[x][y];
		}

		public boolean isSpace(int x, int y, Direction d) {
			return isSpace(x + d.getMoveX(), y + d.getMoveY());
		}

		public boolean isSpace(int x, int y) {
			return x < 0 || x >= this.width || y < 0 || y >= this.height || Character.isWhitespace(this.charMap[x][y]);
		}
	}

	public static enum Direction {
		UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);

		private int moveX;
		private int moveY;

		private Direction(int moveX, int moveY) {
			this.moveX = moveX;
			this.moveY = moveY;
		}

		public int getMoveX() {
			return moveX;
		}

		public int getMoveY() {
			return moveY;
		}

		public Direction getOpposite() {
			switch (this) {
				case UP: return DOWN;
				case DOWN: return UP;
				case LEFT: return RIGHT;
				case RIGHT: return LEFT;
				default: return null;
			}
		}
	}
}
