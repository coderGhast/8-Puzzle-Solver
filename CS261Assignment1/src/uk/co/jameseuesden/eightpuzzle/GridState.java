package uk.co.jameseuesden.eightpuzzle;

/**
 * <p>A state of the grid, used to represent each state and their
 * children/successors. Implements comparable to be used for determining
 * heuristic values with a priority queue in A* Search.
 * </p>
 * @author James Euesden - jee22
 *
 */
public class GridState implements Comparable<GridState>{
    private String gridElements;
    private GridState parent = null;
    private int cost = 0;
    private int depth = 1;
    private int heuristicCost = 0;

    public GridState(String input) {
        gridElements = input;
    }

    public String getElements() {
        return gridElements;
    }

    public void setElements(String newElements) {
        gridElements = newElements;
    }

    public int setParent(GridState newParent) {
        parent = newParent;
        if(parent != null){
            depth = parent.getDepth() + 1;
        }
        return depth;
    }

    public GridState getParent() {
        return parent;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    public void setHeuristicCost(int heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    public int getHeuristicCost() {
        return heuristicCost;
    }

    @Override
    public boolean equals(Object obj) {
        GridState testGrid = (GridState) obj;
        String test = testGrid.getElements();
        if (test.equals(gridElements)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(GridState oGrid) {
      
        int tCost = this.getCost() + this.getHeuristicCost() + this.getDepth();
        int oCost = oGrid.getCost() + oGrid.getHeuristicCost() + oGrid.getDepth();
        
        if(tCost < oCost){
            return -1;
        }
        if(tCost > oCost){
            return 1;
        } else {
            return 0;
        }
    }

}
