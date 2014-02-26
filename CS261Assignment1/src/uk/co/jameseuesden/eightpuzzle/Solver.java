package uk.co.jameseuesden.eightpuzzle;

/**
 * <p>
 * The link class between each solving method and the input of the grid from the
 * command line/ read in of files.
 * </p>
 * 
 * @author James Euesden - jee22
 * 
 */
public class Solver {

    private GridState grid;
    private GridState goalState;
    private GenericType search;

    /**
     * New Solver sets up the goal state, the start state
     * and the mode to be used.
     * @param inputS
     * @param inputG
     * @param mode
     */
    public Solver(String inputS, String inputG, String mode) {
        goalState = new GridState(inputG);
        grid = new GridState(inputS);
        this.setMode(mode);
    }

    
    /**
     * <p>Prints out the grid and finds the path of solving</p>
     */
    public void begin() {
        search.printType();
        System.out.println("Start State:");
        search.printGrid(grid);
        System.out.println("-----\nBegin Expanding States");
        search.findPath();
    }

    /**
     * <p>Set the mode of the application based upon user input.
     * If the user gave no mode input, or mispelled an input, it
     * defaults to BFS.
     * </p>
     * @param mode
     */
    private void setMode(String mode) {
        switch (mode) {
        case ("bfs"):
            search = new BFSmethod(grid, goalState);
            break;
        case ("dfs"):
            search = new DFSmethod(grid, goalState);
            break;
        case ("astar1"): // Manhattan
            search = new AStar(grid, goalState, 1);
            break;
        case ("astar2"): // Tiles out of place
            search = new AStar(grid, goalState, 2);
            break;
        default:
            search = new BFSmethod(grid, goalState);
            break;
        }
    }

}
