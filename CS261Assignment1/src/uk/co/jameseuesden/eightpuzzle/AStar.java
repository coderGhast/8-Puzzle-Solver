package uk.co.jameseuesden.eightpuzzle;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * <p>
 * Solve the 8 puzzle using A* Search, with different heuristics. Uses a
 * priority queue to ensure the least costing state is always at the front of
 * the frontier to be expanded upon.
 * </p>
 * 
 * @author James Euesden - jee22
 * 
 */
public class AStar extends GenericType {
    private GridState parent;
    private PriorityQueue<GridState> open;
    private LinkedList<GridState> closed;
    private int expandedStates = 0;
    private Heuristic heuristic;
    private String modeWords;

    /**
     * <p>
     * New A* Search creates new lists and sets the goal and start state.
     * Also determines what 'heuristic mode' the A* Search should use.
     * </p>
     * 
     * @param input
     * @param goal
     */
    public AStar(GridState input, GridState goal, int mode) {
        parent = input;
        this.goal = goal;
        open = new PriorityQueue<GridState>();
        closed = new LinkedList<GridState>();
        defineMode(mode);
    }

    /**
     * <p>Sets the heuristic to either Manhattan or Tile based.
     * Updates a String to be used in informing the user of
     * which heuristic is under use. <br />
     * Default is Manhattan Distance.
     * </p>
     * @param mode
     */
    private void defineMode(int mode) {
        switch (mode) {
        case (1):
            heuristic = new ManhattanHeuristic();
            modeWords = "Manhattan Distance";
            break;
        case (2):
            heuristic = new TileHeuristic();
            modeWords = "Tiles out of place";
            break;
        default:
            heuristic = new ManhattanHeuristic();
            modeWords = "Manhattan Distance";
        }
    }

    /**
     * <p>
     * Finds a path through the state space to the best solution: <br />
     * Using the Manhattan heuristic, comparing States based on the 'distance'
     * between a Cell/Tile (0-8) in it's current position to the goal position.
     * The result is the total of all Cells/Tiles, using the physical distance
     * and how many movements (up, down, left, right) it would take to get it
     * into the goal state. <br />
     * Using the Tiles out of place heuristic, totals the amount fo tiles not in
     * the correct tile space in the GridState evaluated.
     * </p>
     */
    public void findPath() {
        boolean solutionFound = false;

        // Start at 0 cost and depth.
        parent.setCost(0);
        parent.setDepth(0);

        // Ensure all lists are empty.
        open.clear();
        closed.clear();

        // No parent node to the Start state.
        goal.setParent(null);

        // Add the starting GridState to the open list to expand.
        this.addToOpen(parent);

        // While there are still GridStates to be searched..
        while (open.size() != 0) {
            // Keep track of how many GridStates are expanded
            
            // Get the first element of the PriorityQueue
            GridState current = getFirstInOpen();

            // If the state matches the goal state, stop
            if (current.equals(goal)) {
                // print success
                this.printSuccess(current, expandedStates);
                System.out.println("Depth: " +current.getDepth());
                solutionFound = true;
                break;
            }
            expandedStates++;

            /*
             * After this state has been evaluated against the goal state,
             * remove it from the open list and add it to the closed list.
             */
            this.removeFromOpen(current);
            this.addToClosed(current);
            this.printGrid(current);

            /*
             * Search through possible steps (Up, left, right, down) of empty
             * tiles and find the current state's children to be explored next
             */
            this.findChildren(current);
         
        }

        // If by the end of this while loop, no solution has been found, inform
        // the user
        if (!solutionFound) {
            System.out.println("No GridStates left to explore");
           
        }
    }

    /**
     * <p>
     * Using the current GridState, we can make a new child state using the
     * 'direction' movement and location of the blank tile. For A*, we also
     * decide which states to add to the open and closed lists based on their
     * costs (path cost and heuristic costs).
     * </p>
     * 
     * @param zeroIndex
     *            location of the blank tile
     * @param direction
     *            'direction' of the blank tile
     * @param current
     *            current state of the grid
     */
    public void movement(int zeroIndex, int direction, GridState current) {
        // First check that the movement would be valid
        if (rulesForMovement(zeroIndex, direction)) {
            // Create a new child based on the current state
            GridState child = createChild(current.getElements(), zeroIndex,
                    direction);

            // Set cost of getting to the child state and parent
            child.setCost(current.getCost() + 1);
            child.setParent(current);
            /*
             * Set heuristic cost
             */
            child.setHeuristicCost(heuristic.getHeuristicCost(child, goal));

            /*
             * If the child state is not in the open or closed list, we haven't
             * explored it at all yet, so add to the open list.
             */
            if (!inOpenList(child) && !inClosedList(child)) {
                this.addToOpen(child);

                /*
                 * If it is in a list, find out which list
                 */
            } else {
                GridState checker = inList(child, open);
                /*
                 * If it is in the open list, check to see if it's cost is
                 * better now than it was in the previous open list.
                 */
                if (checker != null) {
                    if (child.getCost() < checker.getCost()) {
                        removeFromOpen(checker);
                        addToOpen(child);
                    } else {
                        /*
                         * Else check if it's in the closed list, and if it's
                         * state was better, then we 'update' the closed list
                         * with the new child.
                         */
                        checker = inList(child, closed);
                        if (checker != null) {
                            if (child.getCost() < checker.getCost()) {
                                removeFromClosed(checker);
                                addToClosed(child);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * <p>
     * Get the first element from the open list. This is the next one to be
     * searched.
     * </p>
     * 
     * @return The first element in the open list
     */
    public GridState getFirstInOpen() {
        return open.peek();
    }

    /**
     * <p>
     * Add a GridState to the open list
     * </p>
     * 
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
     * 
     * @param state
     *            The GridState to check for
     * @return True if the GridState given is in the open list
     */
    public boolean inOpenList(GridState state) {
        return open.contains(state);
    }

    /**
     * <p>
     * Remove a GridState from the open list.
     * </p>
     * 
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
     * 
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
     * 
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
     * 
     * @param state
     *            The GridState to remove from the closed list
     */
    public void removeFromClosed(GridState state) {
        closed.remove(state);
    }

    /**
     * <p>
     * Inform the user of the search method used.
     * </p>
     */
    @Override
    public void printType() {
        System.out.println("A* Search - " + modeWords);
    }
}
