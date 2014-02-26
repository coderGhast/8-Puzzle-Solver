package uk.co.jameseuesden.eightpuzzle;

/**
 * <p>
 * Heuristics must have a cost method.
 * </p>
 * @author James Euesden - jee22
 *
 */
public interface Heuristic {

    public abstract int getHeuristicCost(GridState child, GridState goal);
    
}
