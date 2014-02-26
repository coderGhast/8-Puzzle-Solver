package uk.co.jameseuesden.eightpuzzle;

import java.util.LinkedList;

/**
 * <p>Breath First Search method, solving
 * the 8 Puzzle by searching all top level
 * states first, then expanding their children.
 * </p>
 * @author James Euesden - jee22
 *
 */
public class BFSmethod extends GenericType {
    private GridState parent;
    private LinkedList<GridState> open;
    private LinkedList<GridState> closed;
    private int expandedStates = 0;

    /**
     * <p>New BFS creates new lists, sets the start and goal
     * state and adds the parent to the closed list. BFS uses
     * a List (Or a Queue), where the first in is the first
     * out, ensuring that it is always the top most level
     * unexplored node explored next.
     * </p>
     * @param input
     * @param goal
     */
    public BFSmethod(GridState input, GridState goal) {
        parent = input;
        this.goal = goal;
        open = new LinkedList<GridState>();
        closed = new LinkedList<GridState>();
        closed.add(parent);
    }

    /**
     * <p>Finds the path from the start state to the goal
     * state.
     * </p>
     */
    public void findPath() {
        boolean solutionFound = false;
        open.clear();
        closed.clear();
        // Add the starting GridState to the open list to expand.
        open.add(parent);

        // While there are still GridStates to be searched
        while (open.size() != 0) {
           
            // Get the head of the frontier list.
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
            /*
             * Once this state has been explored, remove it
             * from the open list, add it to the closed list
             * and then find it's children (successors) to expand later.
             */
            this.removeFromOpen(current);
            this.addToClosed(current);
            this.printGrid(current);
            this.findChildren(current);
        }

        if (!solutionFound) {
            System.out.println("No GridStates left to explore");
        }

    }

    /**
     * <p>Creates a new grid, based on the current grid and the 'direction'
     * of the empty tile.
     * </p>
     */
    public void movement(int zeroIndex, int direction, GridState current) {
        // If the movement is legal
        if (rulesForMovement(zeroIndex, direction)) {
            // Make a new child state
            GridState child = createChild(current.getElements(), zeroIndex,
                    direction);
            // If this state does not already belong to the closed and open states
            if (!inOpenList(child) && !inClosedList(child)) {
                // Set it's parent and add to the open list.
                child.setParent(current);
                this.addToOpen(child);
            }
        }
    }

    /**
     * <p>
     * Get the first element from the open list. This is the next one to be
     * searched.
     * </p>
     * @return The first element in the open list
     */
    public GridState getFirstInOpen() {
        return open.get(0);
    }

    /**
     * <p>
     * Add a GridState to the open list
     * </p>
     * @param state
     *            The GridState to be added to the open list
     */
    public void addToOpen(GridState state) {
        open.add(state);
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
        return (open.contains(state));
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
     * <p>Inform user of Search method</p>
     */
    @Override
    public void printType() {
        System.out.println("BFS Method");
    }

}
