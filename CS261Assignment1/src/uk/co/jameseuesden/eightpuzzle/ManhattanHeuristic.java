package uk.co.jameseuesden.eightpuzzle;

/**
 * <p>The amount of moves needing to be made (physically)
 * on a GridState to put a tile in the correct place in
 * reference to the goal state.
 * </p>
 * @author James Euesden - jee22
 *
 */
public class ManhattanHeuristic implements Heuristic{
    /**
     * <p>
     * Calculate the heuristic cost of any given state. Manhattan Distance.
     * </p>
     * 
     * @param child
     *            the state to find the heuristic cost for
     * @return the heuristic cost
     */
    public int getHeuristicCost(GridState child, GridState goal) {
        char[][] gridArray = new char[3][3];
        char[][] goalArray = new char[3][3];
        int index = 0;
        int hCost = 0;

        /*
         * To calculate the heuristic cost, in this case the Manhattan distance,
         * we need to know the total of how many 'moves' away from the goal
         * state each individual tile is. To do this, we can put the state into
         * a 2D char array, and calculate the amount of moves needed to be taken
         * by using the x and y co-ordinates of each tile's current and goal
         * state. The x and y index of each GridState can be added together and
         * then subtracted from the goal state's added x and y (and made into a
         * positive result), in order to determine how many 'moves' this tile
         * must make.
         */

        // Put the current and goal state into 2D arrays
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gridArray[i][j] = child.getElements().charAt(index);
                goalArray[i][j] = goal.getElements().charAt(index);
                index++;
            }
        }

        // For every tile in the grid
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Get the tile
                char currentChar = gridArray[i][j];

                // for each tile in the goal state
                for (int k = 0; k < 3; k++) {
                    for (int n = 0; n < 3; n++) {
                        /*
                         * Compare the goal to the current state of the tile if
                         * the tile matches, calculate the number of moves it
                         * would take to reach it
                         */
                        if (goalArray[k][n] == currentChar) {
                            hCost = hCost + (Math.abs(i - k) + Math.abs(j - n));
                        }
                    }
                }
            }
        }
        return hCost;
    }
}
