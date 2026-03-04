import java.util.ArrayList;

public class Board {

    private final int[][] tiles;
    private final int n;
    private final int hamming;
    private final int manhattan;

    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                this.tiles[i][j] = tiles[i][j];

        int h = 0, m = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int val = this.tiles[i][j];
                if (val == 0) continue;
                int goalRow = (val - 1) / n;
                int goalCol = (val - 1) % n;
                if (goalRow != i || goalCol != j) h++;
                m += Math.abs(i - goalRow) + Math.abs(j - goalCol);
            }
        }
        hamming = h;
        manhattan = m;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%2d ", tiles[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public int dimension() { return n; }
    public int hamming() { return hamming; }
    public int manhattan() { return manhattan; }
    public boolean isGoal() { return hamming == 0; }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.n != this.n) return false;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (this.tiles[i][j] != that.tiles[i][j]) return false;
        return true;
    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        int blankRow = 0, blankCol = 0;
        outer:
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (tiles[i][j] == 0) { blankRow = i; blankCol = j; break outer; }

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for
