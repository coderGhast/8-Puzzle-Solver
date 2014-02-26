package uk.co.jameseuesden.eightpuzzle;

import java.util.InputMismatchException;
/**
 * <p>Main method of the 8 Puzzle solver,
 * takes input from the commands line:
 * StartState - GoalState - Mode.
 * <br />
 * Sends this data to the Driver class to begin the
 * solving method.
 * </p>
 * @author James Euesden - jee22
 *
 */
public class Solve {
    
    public static void main(String[] args) {
        
        if (args.length == 3) {
            String startState = args[0];
            String goalState = args[1];
            String mode = args[args.length-1];
            
            @SuppressWarnings("unused")
            Driver driver = new Driver(startState, goalState, mode);
        }
        else {
            throw new InputMismatchException();
        }

    }

}
