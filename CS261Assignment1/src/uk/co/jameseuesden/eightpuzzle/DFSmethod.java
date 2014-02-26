package uk.co.jameseuesden.eightpuzzle;

import java.util.LinkedList;
import java.util.Stack;

/**
 * <p>
 * Implementation of Depth First Search (DFS), searching through the state space
 * using the deepest points of search first (successors of parents) before
 * exploring anything else on the top level.
 * </p>
 * 
 * @author James Euesden - jee22
 * 
 */
public class DFSmethod extends GenericType {
    private GridState parent;
    private Stack<GridState> open;
    private LinkedList<GridState> closed;
    private int expandedStates = 0;

    /**
     * <p>
     * New DFS sets the start state, goal state and creates new lists. DFS uses
     * a Stack, as the last state in the stack (the deepest state) should be
     * explored first.
     * </p>
     * 
     * @param input
     *            start state
     * @param goal
     *            goal state
     */
    public DFSmethod(GridState input, GridState goal) {
        parent = input;
        this.goal = goal;
        open = new Stack<GridState>();
        closed = new LinkedList<GridState>();
        closed.add(parent);
    }

    /**
     * <p>
     * Finds the path from the start state to the goal state.
     * </p>
     */
    public void findPath() {
        boolean solutionFound = false;
        open.clear();
        closed.clear();
        // Add the starting GridState to the open list to expand.
        this.addToOpen(parent);

        // While there are still GridStates to be searched
        while (open.size() != 0) {
            
            // Get the first element of the Stack (pop it)
            GridState current = getFirstInOpen();

            // If the state matches the goal state, stop
            if (current.equals(goal)) {
                this.printSuccess(current, expandedStates);
                System.out.println("Depth: " +current.getDepth());
                solutionFound = true;
                break;
            }
         // Keep track of how many GridStates expanded
            expandedStates++;

            // Add state to the closed list
            this.addToClosed(current);
            this.printGrid(current);

            // Search through possible steps (Up, left, right, down) of empty
            // tile
            this.findChildren(current);
        }

        if (!solutionFound) {
            System.out.println("No GridStates left to explore");
        }

    }

    /**
     * <p>
     * Creates a new grid, based on the current grid and the 'direction' of the
     * empty tile.
     * </p>
     */
    public void movement(int zeroIndex, int direction, GridState current) {
        // If the movement is legal
        if (rulesForMovement(zeroIndex, direction)) {
            /*
             * Make a new grid state, based on the current state and the
             * direction of the empty tile
             */
            GridState child = createChild(current.getElements(), zeroIndex,
                    direction);
            // If the state is not in the closed list already, add it to open list
            if (!inClosedList(child)) {
                child.setParent(current);
                this.addToOpen(child);
            }
        }
    }

    /**
     * <p>Get the first element from the open list. This is the next one to be
     * searched.
     * </p>
     * @return The first element in the open list
     */
    public GridState getFirstInOpen() {
        return open.pop();
    }

    /**
     * <p>
     * Add a GridState to the open list
     * </p>
     * @param state
     *            The GridState to be added to the open list
     */
    public void addToOpen(GridState state) {
        open.push(state);
    }

    /**
     * <p>
     * Check if a node is in the open list
     * </p>
     * @param state
     *            The GridState to check for
     * @return True if the GridState given is in the open list
     */
    public boolean inOpenList(GridState state) {
        return open.contains(state);
    }

    /**
     * <p>
     * Remove a GridState from the open list
     * </p>
     * @param state
     *            The GridState to remove from the open list
     */
    public void removeFromOpen(GridState state) {
        open.remove(state);
    }

    /**
     * <p>
     * Add a GridState to the closed list
     * </p>
     * @param state
     *            The GridState to add to the closed list
     */
    public void addToClosed(GridState state) {
        closed.add(state);
    }

    /**
     * <p>
     * Check if the GridState is in the closed list
     * </p>
     * @param node
     *            The GridState to search for
     * @return True if the GridState specified is in the closed list
     */
    public boolean inClosedList(GridState state) {
        return closed.contains(state);
    }

    /**
     * <p>
     * Remove a GridState from the closed list.
     * </p>
     * @param state
     *            The GridState to remove from the closed list
     */
    public void removeFromClosed(GridState state) {
        closed.remove(state);
    }

    /**
     * <p>Inform the user of the search method.
     * </p>
     */
    @Override
    public void printType() {
        System.out.println("DFS Method");
    }

}
