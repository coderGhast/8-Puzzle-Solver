package uk.co.jameseuesden.eightpuzzle;

import java.util.Scanner;

/**
 * <p>Link between the Main method and the rest of the application,
 * in particular the class to open the Files for getting the start
 * and goal states of the 8 puzzle.
 * <p>
 * @author James Euesden - jee22
 *
 */
public class Driver {
    private FileHandler fH;
    private String startGrid;
    private String goalGrid;
    private Solver solver;
    private Scanner scan;
    
    /**
     * <p>New Driver loads in the files and solves the
     * puzzle
     * </p>
     * @param start The users requested start state file
     * @param goal The users requested goal state file
     * @param mode The users requested mode
     */
    public Driver(String start, String goal, String mode){
        fH = new FileHandler();
        loadPuzzle(start, goal);
        // Assuming the files are not null
        if(startGrid != null && goalGrid != null && mode != null){
            solvePuzzle(mode);
            startGrid = null;
            goalGrid = null;
        }
    }
    
    /**
     * <p>Loads the puzzle files into the class.
     * </p>
     * @param start
     * @param goal
     */
    private void loadPuzzle(String start, String goal){
     // Open the files requested
        startGrid = fH.readFile(fH.transmogrify(start));
        goalGrid = fH.readFile(fH.transmogrify(goal));
    }
    
    /**
     * <p>Uses the data from the files and given mode to
     * solve the puzzle in Solver class. Also keeps track
     * of the run time of the solve.
     * </p>
     * @param mode
     */
    private void solvePuzzle(String mode){
        // Start timer
        long startTime = System.currentTimeMillis();
        // Make a new solver based on the input.
        solver = new Solver(startGrid, goalGrid, mode);
        solver.begin();
        // Stop the timer after the solver and inform user.
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Runtime: " + totalTime + "ms");
        // Ask the user if they want to solve another puzzle.
        solveAgain();
    }
    
    /**
     * <p>Gets input from the user if they want to solve the
     * puzzle again using another method, or new files.
     * </p>
     */
    private void solveAgain(){
        System.out.println("Run puzzle again? (y/n)");
        scan = new Scanner(System.in); 
        if(scan.next().equals("y")){
            System.out.println("Start file?");
            String start = scan.next();
            System.out.println("Goal file?");
            String goal = scan.next();
            System.out.println("Mode? (bfs/dfs/astar1/astar2)");
            String mode = scan.next();
            loadPuzzle(start,goal);
            solvePuzzle(mode);
        }
        else {
            System.exit(0);
        }
    }
}
