
package org.bluechat.blueflood.solver;

import java.util.Arrays;

/**
 * this class represents a solution that has been produced by a Solver.
 */
public class Solution {

    private final byte[] steps;
    private final String stepsString;
    private final String solverName;

    /**
     * the constructor.
     * @param steps
     * @param solverName
     */
    public Solution(byte[] steps, String solverName) {
        this.solverName = solverName;
        this.steps = Arrays.copyOf(steps, steps.length);
        final StringBuilder sbSteps = new StringBuilder();
        for (final byte step : steps) {
            sbSteps.append(step + 1);
        }
        this.stepsString = sbSteps.toString();
    }

    /**
     * return the number of steps in this solution.
     * @return number of steps
     */
    public int getNumSteps() {
        return this.steps.length;
    }

    /**
     * return (a copy of) the array of steps in this solution.
     * each step is a color value 0...(c-1) with c=number of colors.
     * @return
     */
    public byte[] getSteps() {
        return Arrays.copyOf(this.steps, this.steps.length); // return a copy of the byte array
    }

    /**
     * return the solver name of this solution.
     * @return solver name
     */
    public String getSolverName() {
        return this.solverName;
    }

    /**
     * return a human-readable String representation of this solution.
     * each character is a color value 1...c with c=number of colors.
     * (note the "1" added to the colors compared to getSteps)
     */
    @Override
    public String toString() {
        return this.stepsString;
    }
}
