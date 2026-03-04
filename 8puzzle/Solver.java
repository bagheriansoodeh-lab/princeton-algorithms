import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Collections;

public class Solver {

    private final boolean solvable;
    private final int moves;
    private final Iterable<Board> solution;

    private static class SearchNode implements Comparable<SearchNode> {
        final Board board;
        final int moves;
        final SearchNode prev;
        final int priority;

        SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.priority = board.manhattan() + moves;
        }

        public int compareTo(SearchNode other) {
            return Integer.compare(this.priority, other.priority);
        }
    }

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Initial board is null");

        MinPQ<SearchNode> pq     = new MinPQ<>();
        MinPQ<SearchNode> twinPQ = new MinPQ<>();

        pq.insert(new SearchNode(initial, 0, null));
        twinPQ.insert(new SearchNode(initial.twin(), 0, null));

        SearchNode goal = null;

        while (true) {
            SearchNode node = pq.delMin();
            if (node.board.isGoal()) { goal = node; break; }
            for (Board nb : node.board.neighbors()) {
                if (node.prev == null || !nb.equals(node.prev.board))
                    pq.insert(new SearchNode(nb, node.moves + 1, node));
            }

            SearchNode twinNode = twinPQ.delMin();
            if (twinNode.board.isGoal()) { goal = null; break; }
            for (Board nb : twinNode.board.neighbors()) {
                if (twinNode.prev == null || !nb.equals(twinNode.prev.board))
                    twinPQ.insert(new SearchNode(nb, twinNode.moves + 1, twinNode));
            }
        }

        if (goal != null) {
            solvable = true;
            moves = goal.moves;
            ArrayList<Board> path = new ArrayList<>();
            for (SearchNode n = goal; n != null; n = n.prev) path.add(n.board);
            Collections.reverse(path);
            solution = path;
        } else {
            solvable = false;
            moves = -1;
            solution = null;
        }
    }

    public boolean isSolvable() { return solvable; }
    public int moves() { return moves; }
    public Iterable<Board> solution() { return solution; }
}
