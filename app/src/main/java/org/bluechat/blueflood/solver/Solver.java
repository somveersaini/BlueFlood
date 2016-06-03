
package org.bluechat.blueflood.solver;

public interface Solver {

    /**
     * run the solver algorithm that is implemented in this class
     * and store the found solution(s) internally.
     * 
     * @param startPos position of the board cell where the color flood starts (0 == top left)
     * @return number of steps in the solution
     * @throws InterruptedException
     */
    public int execute(final int startPos) throws InterruptedException;

    /**
     * return the first (best) solution.
     * 
     * @return the solution
     */
    public Solution getSolution();

    /**
     * set the strategy to be used by this solver.
     * @param strategyClass the class of the strategy
     */
    public void setStrategy(Class<Strategy> strategyClass);

    /**
     * get the strategies supported by this solver.
     * they should be sorted from fastest to slowest.
     * @return array of strategy classes
     */
    public Class<Strategy>[] getSupportedStrategies();

    /**
     * get the name of the solver and / or the strategy.
     * @return the name
     */
    public String getSolverName();
}
