package uk.co.jameseuesden.eightpuzzle;

import java.util.Collection;

/**
 * <p>
 * Abstract class for all types of search methods.
 * Holds shared methods between each.
 * </p>
 * @author Narctiss
 * @version 1.0
 */
public abstract class GenericType{
    public GridState goal;
    
    public abstract void findPath();
    
    public abstract void movement(int zeroIndex, int direction, GridState current);
    
    /**
     * <p>Static directions allow us to always find the legal set of possible
     * directions of the empty state and create children from the current parent
     * state.</p>
     * 
     * @param current grid state just explored
     */
    public void findChildren(GridState current) {
        int zeroIndex = current.getElements().indexOf('0');
        /*
         * Directions are always -3, -1, +1 or +3 in the array of chars in the
         * String, as we are using a 3x3 grid
         */
        this.movement(zeroIndex, 3, current);
        this.movement(zeroIndex, -3, current);
        this.movement(zeroIndex, -1, current);
        this.movement(zeroIndex, 1, current);
    }
   
    
    /**
     * Get the first element from the open list. This is the next one to be
     * searched.
     * 
     * @return The first element in the open list
     */
    public abstract GridState getFirstInOpen();
    
    /**
     * Add a GridState to the open list
     * 
     * @param state
     *            The GridState to be added to the open list
     */
    public abstract void addToOpen(GridState state);
    
    /**
     * Check if a node is in the open list
     * 
     * @param state
     *            The GridState to check for
     * @return True if the GridState given is in the open list
     */
    public abstract boolean inOpenList(GridState state);
    
    /**
     * Remove a GridState from the open list
     * 
     * @param state
     *            The GridState to remove from the open list
     */
    public abstract void removeFromOpen(GridState state);
    
    /**
     * Add a GridState to the closed list
     * 
     * @param state
     *            The GridState to add to the closed list
     */
    public abstract void addToClosed(GridState state);
    
    /**
     * Check if the GridState is in the closed list
     * 
     * @param node
     *            The GridState to search for
     * @return True if the GridState specified is in the closed list
     */
    public abstract boolean inClosedList(GridState state);

    /**
     * Remove a GridState from the closed list.
     * 
     * @param state
     *            The GridState to remove from the closed list
     */
    public abstract void removeFromClosed(GridState state);
    
    /**
     * <p>Using the location of the blank tile and the intended direction,
     * see if it is a legal move to make, or if it would send the tile
     * out of the bounds of the grid or 'wrap' around from one side
     * of the grid to another.</p>
     * 
     * @param zeroIndex
     *            - Current location of the empty space
     * @param direction
     *            - Movement of the empty space
     * @return
     */
    public boolean rulesForMovement(int zeroIndex, int direction) {
        boolean withinPossibility = false;
        // Check the index is not on the 'edge' and going to 'wrap' over.
        if ((zeroIndex == 2 || zeroIndex == 5 || zeroIndex == 8)
                && direction == 1) {
            withinPossibility = false;
        } else if ((zeroIndex == 0 || zeroIndex == 3 || zeroIndex == 6)
                && direction == -1) {
            withinPossibility = false;
        // Check the zero is not on the top or bottom row and trying to move out of bounds.
        } else if (((zeroIndex <= 2) && (direction == -3))
                || ((zeroIndex >= 6) && (direction == 3))) {
            withinPossibility = false;
        }

        // Check that the calculated movement is within range (0 - 8)
        else {
            int indexToBe = zeroIndex + direction;
            if (indexToBe >= 0 && indexToBe <= 8) {
                withinPossibility = true;
            }
        }
        // return result of checks
        return withinPossibility;
    }

    /**
     * <p>Create a child node based on if it is a legal state after a tile
     * movement.</p>
     * 
     * @param parentElements
     *            state of the parent
     * @param zeroIndex
     *            location of the empty tile in the parent state
     * @param direction
     *            direction of movement of the empty tile
     * @return the created child GridState
     */
    protected GridState createChild(String parentElements, int zeroIndex,
            int direction) {
        // Split the elements in order to move the empty space by
        // 'swapping' two elements at relevant indexes.
        char[] seperateElements = parentElements.toCharArray();
        // Get the character that will be replaced by the blank tile.
        char toMove = seperateElements[zeroIndex + direction];
        // Replace the moved tile with the blank tile
        seperateElements[zeroIndex + direction] = '0';
        // Old position of 0/empty space becomes what it has swapped with
        seperateElements[zeroIndex] = toMove;
        // Put the character array back into a String.
        String childElements = new String(seperateElements);
        // Return a new 'Child' GridState, using the char array with the moved tiles
        return new GridState(childElements);
    }
    
    /**
     * <p>Looks through the given list for the given
     * GridState and returns either the found GridState
     * if it exists in the list, or null if not.
     * @param state
     * @param list
     * @return
     */
    public GridState inList(GridState state, Collection<GridState> list) {
        for (GridState checker : list) {
            if (state.equals(checker)) {
                return checker;
            }
        }

        return null;
    }
    
    
    //------------- PRINT METHODS --------------
    
    /**
     * Prints the grid requested to the console.
     * 
     * @param gridInput
     *            - Any GridState to be printed.
     */
 
    public void printGrid(GridState gridInput) {
        String activeElements = gridInput.getElements();
        System.out.println("-----");
        for (int i = 0; i < activeElements.length(); i++) {
            if (activeElements.charAt(i) == '0') {
                System.out.print("  ");
            } else {
                System.out.print(activeElements.charAt(i) + " ");
            }
            if (((i + 1) % 3) == 0) {
                System.out.println();
            }
        }
    }
    
    /**
     * <p>Prints the success of the application
     * in finding the goal state, including how
     * many GridStates were expanded and the parent
     * of the grid state.
     * </p>
     * @param current grid state to be used
     */
    public void printSuccess(GridState current, int expandedStates){
        System.out.println("------");
        System.out.println("Found goal state match!");
        this.printGrid(current);
        
        System.out.println("------");
        System.out.println("Parent to Goal:");
        // In case the start state and goal state are the same on the first run.
        if(current.getParent() != null){
            this.printGrid(new GridState(current.getParent().getElements()));
        }
        System.out.println("Grid States expanded: " + expandedStates);
    }
    
    public abstract void printType();
    
}
