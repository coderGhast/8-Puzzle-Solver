package uk.co.jameseuesden.eightpuzzle;

/**
 * <p>The total amount of tiles out of place in a given state vs
 * the goal state</p>
 * @author James Euesden - jee22
 *
 */
public class TileHeuristic implements Heuristic{

    @Override
    /**
     * <p>Calculate the heuristic cost of any given state. Tiles out of place.
     * </p>
     * @param child the state to find the heuristic cost for
     * @return the heuristic cost
     */
    public int getHeuristicCost(GridState child, GridState goal) {
        int cost = 0;
        char[] state = child.getElements().toCharArray();
        char[] goalState = goal.getElements().toCharArray();
        
        /*
         * To calculate the heuristic cost, in this case we
         * look for how many tiles are not in the correct 
         * location.
         */
        for(int i = 0; i < state.length; i++){
            if(state[i] != goalState[i]){
                cost++;
            }
        }       
        return cost;
    }

}
