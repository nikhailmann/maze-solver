import java.io.*;
import java.util.Scanner;

/**
 * MazeSolver.java
 * This class reads in a text file of a prebuilt maze 
 * Using stacks, it solves the maze
 * then, it prints out the solution in the form of coordinate points
 * @author Nikhail Mann
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner mazefile;
        mazefile = new Scanner(new BufferedReader(new FileReader("maze.txt")));

        int rows = 0;
        int columns = 0;
        // count rows
        while (mazefile.hasNextLine()) {
            rows++;
            mazefile.nextLine();
        }
        // count columns
        mazefile = new Scanner(new BufferedReader(new FileReader("maze.txt")));
        String columnFinder = mazefile.nextLine();
        columns = columnFinder.length();

        // make 2d array of Maze
        mazefile = new Scanner(new BufferedReader(new FileReader("maze.txt")));
        String[][] maze = new String[rows][columns];
        for (int j = 0; j < rows; j++) {
            String currentRow = mazefile.nextLine();
            for (int k = 0; k < columns; k++) {
                maze[j][k] = currentRow.substring(k, k + 1);
            }
        }

        //finding starting place
        int[] startingPlace = new int[2];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (maze[row][col].equals("@")) {
                    startingPlace[0] = row;
                    startingPlace[1] = col;
                    break;
                }
            }
        }
        //create position stacks, push starting place
        Stack stackR = new Stack();
        Stack stackC = new Stack();
        int rowPosition = startingPlace[0];
        int colPosition = startingPlace[1];
        stackR.push(rowPosition);
        stackC.push(colPosition);

        // use methods to solve the maze in a while loop 
        while (!isdone(maze, stackR, stackC)) {
            if (canMoveUp(maze, stackR, stackC)) {
                move(maze, stackR, stackC, "Up");
            } else if (canMoveDown(maze, stackR, stackC)) {
                move(maze, stackR, stackC, "Down");
            } else if (canMoveLeft(maze, stackR, stackC)) {
                move(maze, stackR, stackC, "Left");
            } else if (canMoveRight(maze, stackR, stackC)) {
                move(maze, stackR, stackC, "Right");
            } else {
                if (stackR.peek() == null) {
                    System.out.println("Not solvable!");
                    break;
                }
                // if at dead end, set current place to wall and pop stacks
                int curRow = stackR.peek();
                int curCol = stackC.peek();
                maze[curRow][curCol] = "#";
                stackR.pop();
                stackC.pop();
            }
        }
        // reverse the stacks so solution can be printed from start to finish
        Stack reversedStackR = new Stack();
        Stack reversedStackC = new Stack();
        while (stackR.peek() != null) {
            reversedStackR.push(stackR.pop());
            reversedStackC.push(stackC.pop());
        }
        // print reversed stacks
        System.out.println("Solution: ");
        while (reversedStackR.peek() != null) {
            int row = reversedStackR.pop();
            int col = reversedStackC.pop();
            System.out.println(row + ", " + col);
        }

    }
    /**
     * The isdone method determines if the finish spot has been reached
     * @param maze - the maze 
     * @param stackR - stack of Row values
     * @param stackC - stack of Column values
     * @return whether maze is finished or not
     */
    public static boolean isdone(String[][] maze, Stack stackR, Stack stackC) {
        int curRow = stackR.peek();
        int curCol = stackC.peek();
        if (maze[curRow][curCol].equals("$")) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * The canMoveUp method determines if computer can move up in maze from current pos
     * @param maze - the maze 
     * @param stackR - stack of Row values
     * @param stackC - stack of Column values
     * @return whether you can move up or not
     */
    public static boolean canMoveUp(String[][] maze, Stack stackR, Stack stackC) {
        int curRow = stackR.peek();
        int curCol = stackC.peek();
        if ((curRow-1) < 0) {
            return false;
        } else if (maze[curRow-1][curCol].equals("#")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * The canMoveDown method determines if computer can move down in maze from current pos
     * @param maze - the maze 
     * @param stackR - stack of Row values
     * @param stackC - stack of Column values
     * @return whether you can move down or not
     */
    public static boolean canMoveDown(String[][] maze, Stack stackR, Stack stackC) {
        int curRow = stackR.peek();
        int curCol = stackC.peek();
        if ((curRow+1) > (maze.length - 1)) {
            return false;
        } else if (maze[curRow+1][curCol].equals("#")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * The canMoveLeft method determines if computer can move left in maze from current pos
     * @param maze - the maze 
     * @param stackR - stack of Row values
     * @param stackC - stack of Column values
     * @return whether you can move left or not
     */
    public static boolean canMoveLeft(String[][] maze, Stack stackR, Stack stackC) {
        int curRow = stackR.peek();
        int curCol = stackC.peek();
        if((curCol - 1) < 0) {
            return false;
        } else if (maze[curRow][curCol-1].equals("#")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * The canMoveRight method determines if computer can move Right in maze from current pos
     * @param maze - the maze 
     * @param stackR - stack of Row values
     * @param stackC - stack of Column values
     * @return whether you can move Right or not
     */
    public static boolean canMoveRight(String[][] maze, Stack stackR, Stack stackC) {
        int curRow = stackR.peek();
        int curCol = stackC.peek();
        if((curCol + 1) > (maze[0].length - 1)) {
            return false;
        } else if (maze[curRow][curCol+1].equals("#")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * The move method moves the current position in a specificed direction and makes the previous place a wall. It also pushes the position onto the stacks
     * @param maze - the maze 
     * @param stackR - stack of Row values
     * @param stackC - stack of Column values
     * @param direction - the direction to move in
     */
    public static void move(String[][] maze, Stack stackR, Stack stackC, String direction) {
        int curRow = stackR.peek();
        int curCol = stackC.peek();
        maze[curRow][curCol] = "#";
        if (direction.equals("Up")) {
            curRow -= 1;
        } else if (direction.equals("Down")) {
            curRow += 1;
        } else if (direction.equals("Left")) {
            curCol -= 1;
        } else if (direction.equals("Right")) {
            curCol += 1;
        }
        stackR.push(curRow);
        stackC.push(curCol);
    }
}
