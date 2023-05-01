import java.util.Arrays;
import java.util.LinkedList;

public class MazeSolverWithPowerOld implements IMazeSolverWithPower {
    public class TreeNode implements Comparable<TreeNode>{
        int[] coords;
        int superpowers;
        TreeNode childN;
        TreeNode childS;
        TreeNode childW;
        TreeNode childE;
        TreeNode parent;
        public TreeNode(int[] coords, int superpowers) {
            this.coords = coords;
            this.superpowers = superpowers;
        }
        public void setChildren(TreeNode[] children) {
            if (children[0] != null) {
                this.childN = children[0];
                children[0].parent = this;
            }
            if (children[1] != null) {
                this.childS = children[1];
                children[1].parent = this;
            }
            if (children[2] != null) {
                this.childW = children[2];
                children[2].parent = this;
            }
            if (children[3] != null) {
                this.childE = children[3];
                children[3].parent = this;
            }
        }
        @Override
        public int compareTo(TreeNode node) {
            return 0;
        }

        @Override
        public String toString() {
            return Arrays.toString(coords);
        }
    }
    private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
    private static int[][] DELTAS = new int[][] {
            { -1, 0 }, // North
            { 1, 0 }, // South
            { 0, 1 }, // East
            { 0, -1 } // West
    };

    private Maze maze;
    private int startRow, startCol;
    private int endRow, endCol;
    private int superpowers;
    private TreeNode root;
    int[][] been;
    private LinkedList<int[][]> memo = new LinkedList<>();
    private LinkedList<TreeNode> queue = new LinkedList<>();

    public MazeSolverWithPowerOld() {}

    @Override
    public void initialize(Maze maze) {
        this.maze = maze;
        this.been = new int[this.maze.getRows()][this.maze.getColumns()];
    }

    @Override
    public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
        if (maze == null) {
            throw new Exception("Oh no! You cannot call me without initializing the maze!");
        }

        if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
                endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
            throw new IllegalArgumentException("Invalid start/end coordinate");
        }

        // set all visited flag to false
        // before we begin our search
        for (int i = 0; i < maze.getRows(); ++i) {
            for (int j = 0; j < maze.getColumns(); ++j) {
                this.been[i][j] = -1;
                maze.getRoom(i, j).onPath = false;
            }
        }

        this.root = new TreeNode(new int[] {startRow, startCol}, 0);
        this.queue.clear();
        if (this.startRow != startRow || this.startCol != startCol || this.superpowers != 0) {
            this.memo.clear();
        }

        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.superpowers = 0;

        solve(this.root);
        return this.maze.getRoom(startRow, startCol).onPath
                ? this.been[endRow][endCol]
                : null;
    }

    private boolean canGo(TreeNode node, int dir) {
        int row = node.coords[0];
        int col = node.coords[1];
        // not needed since our maze has a surrounding block of wall
        // but Joe the Average Coder is a defensive coder!
        if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= maze.getRows()) return false;
        if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= maze.getColumns()) return false;

        switch (dir) {
            case NORTH:
                return !maze.getRoom(row, col).hasNorthWall() || node.superpowers > 0;
            case SOUTH:
                return !maze.getRoom(row, col).hasSouthWall() || node.superpowers > 0;
            case EAST:
                return !maze.getRoom(row, col).hasEastWall() || node.superpowers > 0;
            case WEST:
                return !maze.getRoom(row, col).hasWestWall() || node.superpowers > 0;
        }

        return false;
    }
    private boolean usePower(int row, int col, int dir) {
        switch (dir) {
            case NORTH:
                return maze.getRoom(row, col).hasNorthWall();
            case SOUTH:
                return maze.getRoom(row, col).hasSouthWall();
            case EAST:
                return maze.getRoom(row, col).hasEastWall();
            case WEST:
                return maze.getRoom(row, col).hasWestWall();
        }

        return false;
    }

    private int[] directionCoord(int row, int col, int direction) {
        return new int[] {row + DELTAS[direction][0], col + DELTAS[direction][1]};
    }

    private boolean ifFoundTarget(int[] coords) {
        return coords[0] == endRow && coords[1] == endCol;
    }

    private void solve(TreeNode node) {
        if (node == null) return;
        int row = node.coords[0];
        int col = node.coords[1];
        if (ifFoundTarget(node.coords) && this.been[row][col] == -1) {
            this.been[row][col] = node.parent == null ? 0 : this.been[node.parent.coords[0]][node.parent.coords[1]] + 1;
            trace(node);
            return;
        }
        if (this.been[row][col] != -1) solve(this.queue.poll());
        this.been[row][col] = node.parent == null ? 0 : this.been[node.parent.coords[0]][node.parent.coords[1]] + 1;
        TreeNode[] children = new TreeNode[4];
        for (int direction = 0; direction < 4; ++direction) {
            if (canGo(node, direction) && been[row + DELTAS[direction][0]][col + DELTAS[direction][1]] == -1) { // can we go in that direction?
                // yes we can :)
                if (usePower(row, col, direction)) {
                    children[direction] = new TreeNode(directionCoord(row, col, direction), node.superpowers - 1);
                } else {
                    children[direction] = new TreeNode(directionCoord(row, col, direction), node.superpowers);
                }
            }
        }
        node.setChildren(children);
        for (int direction = 0; direction < 4; direction++) {
            if (children[direction] != null) {
                this.queue.add(children[direction]);
            }
        }
        solve(this.queue.poll());
    }

    private void trace(TreeNode node) {
        if (node == null) return;
        this.maze.getRoom(node.coords[0], node.coords[1]).onPath = true;
        if (node.parent == null) return;
        trace(node.parent);
    }

    @Override
    public Integer numReachable(int k) throws Exception {
        int result = 0;

        if (memo.isEmpty()) {
//            MazeSolverWithPower solverTopLeft = new MazeSolverWithPower();
//            solverTopLeft.initialize(this.maze);
//            solverTopLeft.pathSearch(startRow, startCol,0,0, superpowers);
//
//            memo.add(solverTopLeft.been);
//
//            MazeSolverWithPower solverTopRight = new MazeSolverWithPower();
//            solverTopRight.initialize(this.maze);
//            solverTopRight.pathSearch(startRow, startCol,0, this.maze.getColumns() - 1, superpowers);
//
//            memo.add(solverTopRight.been);
//
//            MazeSolverWithPower solverBottomRight = new MazeSolverWithPower();
//            solverBottomRight.initialize(this.maze);
//            solverBottomRight.pathSearch(startRow, startCol,this.maze.getRows() - 1, this.maze.getColumns() - 1, superpowers);
//
//            memo.add(solverBottomRight.been);
//
//            MazeSolverWithPower solverBottomLeft = new MazeSolverWithPower();
//            solverBottomLeft.initialize(this.maze);
//            solverBottomLeft.pathSearch(startRow, startCol,this.maze.getRows() - 1, 0, superpowers);
//
//            memo.add(solverBottomLeft.been);
        }

        for (int i = 0; i < maze.getRows(); ++i) {
            for (int j = 0; j < maze.getColumns(); ++j) {
                if(memo.get(0)[i][j] == -1 && memo.get(1)[i][j] == -1 && memo.get(2)[i][j] == -1 && memo.get(3)[i][j] == -1) {
                    MazeSolverWithPower solver = new MazeSolverWithPower();
                    solver.initialize(this.maze);
                    Integer distance = solver.pathSearch(startRow, startCol, i, j, superpowers);
                    if (distance != null && distance == k) result ++;
                }
                else if (memo.get(0)[i][j] == k) result++;
                else if (memo.get(1)[i][j] == k) result++;
                else if (memo.get(2)[i][j] == k) result++;
                else if (memo.get(3)[i][j] == k) result++;
            }
        }
        return result;
    }

    @Override
    public Integer pathSearch(int startRow, int startCol, int endRow,
                              int endCol, int superpowers) throws Exception {
        if (maze == null) {
            throw new Exception("Oh no! You cannot call me without initializing the maze!");
        }

        if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
                endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
            throw new IllegalArgumentException("Invalid start/end coordinate");
        }

        // set all visited flag to false
        // before we begin our search
        for (int i = 0; i < maze.getRows(); ++i) {
            for (int j = 0; j < maze.getColumns(); ++j) {
                this.been[i][j] = -1;
                maze.getRoom(i, j).onPath = false;
            }
        }

        this.root = new TreeNode(new int[] {startRow, startCol}, superpowers);
        this.queue.clear();
        if (this.startRow != startRow || this.startCol != startCol || this.superpowers != superpowers) {
            this.memo.clear();
        }

        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.superpowers = superpowers;

        solve(this.root);
        return this.maze.getRoom(startRow, startCol).onPath
                ? this.been[endRow][endCol]
                : null;
    }

    public static void main(String[] args) {
        try {
            Maze maze = Maze.readMaze("maze-sample.txt");
            IMazeSolverWithPower solver = new MazeSolverWithPower();
            solver.initialize(maze);

            System.out.println(solver.pathSearch(1, 1, 1, 3, 1));
            MazePrinter.printMaze(maze);

            for (int i = 0; i <= 9; ++i) {
                System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
            }

            System.out.println(solver.pathSearch(1, 1, 3, 3, 3));
            MazePrinter.printMaze(maze);

            for (int i = 0; i <= 9; ++i) {
                System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
