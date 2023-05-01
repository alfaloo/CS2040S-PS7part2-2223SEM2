import static org.junit.Assert.*;
import org.junit.Test;

public class MazeTester {
    @Test
    public void testPathSearch1() {
        try {
            Maze maze = Maze.readMaze("maze-sample.txt");
            IMazeSolver solver = new MazeSolverWithPower();
            solver.initialize(maze);
            int expected = 9;
            int answer = solver.pathSearch(0, 0, 4, 3);
            ImprovedMazePrinter.printMaze(maze, 0, 0);
            assertEquals(expected, answer);

        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("no exceptions", "exceptions");
        }
    }

    @Test
    public void testPathSearch2() {
        try {
            Maze maze = Maze.readMaze("maze-dense.txt");
            IMazeSolver solver = new MazeSolverWithPower();
            solver.initialize(maze);
            Integer expected = null;
            Integer answer = solver.pathSearch(0, 0, 3, 3);
            ImprovedMazePrinter.printMaze(maze, 0, 0);
            assertEquals(expected, answer);

        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("no exceptions", "exceptions");
        }
    }

    @Test
    public void testPathSearch3() {
        try {
            Maze maze = Maze.readMaze("maze-dense.txt");
            IMazeSolver solver = new MazeSolverWithPower();
            solver.initialize(maze);
            Integer expected = 0;
            Integer answer = solver.pathSearch(0, 0, 0, 0);
            ImprovedMazePrinter.printMaze(maze, 0, 0);
            assertEquals(expected, answer);
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("no exceptions", "exceptions");
        }
    }

    @Test
    public void testPathSearch4() {
        try {
            Maze maze = Maze.readMaze("maze-horizontal.txt");
            IMazeSolver solver = new MazeSolverWithPower();
            solver.initialize(maze);
            Integer expected = 1;
            Integer answer = solver.pathSearch(0, 4, 0, 5);
            ImprovedMazePrinter.printMaze(maze, 0, 4);
            assertEquals(expected, answer);
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("no exceptions", "exceptions");
        }
    }

    @Test
    public void testPathSearch5() {
        try {
            Maze maze = Maze.readMaze("maze-horizontal.txt");
            IMazeSolver solver = new MazeSolverWithPower();
            solver.initialize(maze);
            Integer expected = 1;
            Integer answer = solver.pathSearch(0, 1, 0, 0);
            ImprovedMazePrinter.printMaze(maze, 0, 1);
            assertEquals(expected, answer);
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("no exceptions", "exceptions");
        }
    }

    @Test
    public void testPathSearch6() {
        try {
            Maze maze = Maze.readMaze("maze-sample.txt");
            IMazeSolver solver = new MazeSolverWithPower();
            solver.initialize(maze);
            int expected = 5;
            int answer = solver.pathSearch(4, 3, 3, 3);
            ImprovedMazePrinter.printMaze(maze, 4, 3);
            assertEquals(expected, answer);
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("no exceptions", "exceptions");
        }
    }

    @Test
    public void testPathSearch7() {
        try {
            Maze maze = Maze.readMaze("maze-empty.txt");
            IMazeSolver solver = new MazeSolverWithPower();
            solver.initialize(maze);

            solver.pathSearch(0, 0, 2, 3);
            ImprovedMazePrinter.printMaze(maze, 0, 0);

            int expected = 6;
            int answer = solver.pathSearch(0, 0, 3, 3);
            ImprovedMazePrinter.printMaze(maze, 0, 0);
            assertEquals(expected, answer);
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("no exceptions", "exceptions");
        }
    }

    @Test
    public void testPathSearch8() {
        try {
            Maze maze = Maze.readMaze("maze-dense.txt");
            IMazeSolverWithPower solver = new MazeSolverWithPower();
            solver.initialize(maze);
            int expected = 1;
            int answer = solver.pathSearch(0, 0, 0, 1, 1);
            ImprovedMazePrinter.printMaze(maze, 0, 0);
            assertEquals(expected, answer);
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("no exceptions", "exceptions");
        }
    }

    @Test
    public void testPathSearch9() {
        try {
            Maze maze = Maze.readMaze("maze-sample.txt");
            IMazeSolverWithPower solver = new MazeSolverWithPower();
            solver.initialize(maze);
            int expected = 5;
            int answer = solver.pathSearch(0, 0, 4, 1, 2);
            ImprovedMazePrinter.printMaze(maze, 0, 0);
            assertEquals(expected, answer);
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("no exceptions", "exceptions");
        }
    }

    @Test
    public void testPathSearch10() {
        try {
            Maze maze = Maze.readMaze("maze-dense.txt");
            IMazeSolverWithPower solver = new MazeSolverWithPower();
            solver.initialize(maze);
            int expected = 6;
            int answer = solver.pathSearch(0, 0, 3, 3, 6);
            ImprovedMazePrinter.printMaze(maze, 0, 0);
            assertEquals(expected, answer);
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("no exceptions", "exceptions");
        }
    }

    @Test
    public void testPathSearch11() {
        try {
            Maze maze = Maze.readMaze("maze-hard.txt");
            IMazeSolverWithPower solver = new MazeSolverWithPower();
            solver.initialize(maze);
            int expected = 20;
            int answer = solver.pathSearch(0, 0, 8, 4, 1);
            ImprovedMazePrinter.printMaze(maze, 0, 0);
            assertEquals(expected, answer);
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("no exceptions", "exceptions");
        }
    }

    @Test
    public void testNumReachable1() {
        try {
            Maze maze = Maze.readMaze("maze-sample.txt");
            IMazeSolver solver = new MazeSolverWithPower();
            solver.initialize(maze);
            solver.pathSearch(0, 0, 4, 3);
            ImprovedMazePrinter.printMaze(maze, 0, 0);
            int[] expected = new int[] {1, 1, 2, 1, 2, 4, 5, 5, 3, 1};
            for (int i = 0; i < 10; i++) {
                int answer = solver.numReachable(i);
                System.out.println("Steps " + i + " Rooms: " + answer);
                assertEquals(expected[i], answer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("no exceptions", "exceptions");
        }
    }

    @Test
    public void testNumReachable2() {
        try {
            Maze maze = Maze.readMaze("maze-sample.txt");
            IMazeSolver solver = new MazeSolverWithPower();
            solver.initialize(maze);
            solver.pathSearch(0, 0, 4, 3);
            ImprovedMazePrinter.printMaze(maze, 0, 0);
            int[] expected = new int[] {1, 1, 2, 1, 2, 4, 5, 5, 3, 1, 0, 0};
            for (int i = 0; i < expected.length; i++) {
                int answer = solver.numReachable(i);
                System.out.println("Steps " + i + " Rooms: " + answer);
                assertEquals(expected[i], answer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("no exceptions", "exceptions");
        }
    }

    @Test
    public void testNumReachable3() {
        try {
            Maze maze = Maze.readMaze("maze-dense.txt");
            IMazeSolverWithPower solver = new MazeSolverWithPower();
            solver.initialize(maze);
            solver.pathSearch(0, 0, 3, 3, 6);
            ImprovedMazePrinter.printMaze(maze, 0, 0);
            int[] expected = new int[] {1, 2, 3, 4, 3, 2, 1, 0, 0, 0, 0, 0};
            for (int i = 0; i < expected.length; i++) {
                int answer = solver.numReachable(i);
                System.out.println("Steps " + i + " Rooms: " + answer);
                assertEquals(expected[i], answer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("no exceptions", "exceptions");
        }
    }
}