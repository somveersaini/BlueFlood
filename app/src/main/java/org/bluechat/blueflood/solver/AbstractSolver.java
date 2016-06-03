

package org.bluechat.blueflood.solver;

import org.bluechat.blueflood.model.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * an abstract implementation of interface Solver.
 */
public abstract class AbstractSolver implements Solver {

    protected final Board board;
    protected final List<Solution> solutions = new ArrayList<Solution>();
    protected int solutionSize = 0;

    /**
     * store the Board reference.
     * @param board to be solved
     */
    protected AbstractSolver(final Board board) {
        this.board = board;
    }

    /**
     * the actual solver main method, to be implemented by descendants of this class.
     * should call {addSolution(List)} to collect the solution(s).
     * 
     * @param startPos position of the board cell where the color flood starts (0 == top left)
     * @throws InterruptedException
     */
    protected abstract void executeInternal(int startPos) throws InterruptedException;

    /* (non-Javadoc)
     * @see colorfill.solver.Solver#execute(int)
     */
    @Override
    public int execute(final int startPos) throws InterruptedException {
        this.solutions.clear();
        this.solutionSize = Integer.MAX_VALUE;

        this.executeInternal(startPos);

        return this.solutionSize;
    }

    /* (non-Javadoc)
     * @see colorfill.solver.Solver#getSolution()
     */
    @Override
    public Solution getSolution() {
        if (this.solutions.size() > 0) {
            return this.solutions.get(0);
        } else {
            return new Solution(new byte[0], this.getSolverName());
        }
    }

    /**
     * add a copy of this solution to the list of solutions if it's shorter than
     * or same length as the current best solution(s).
     * in the list of solutions only the best (shortest) solutions
     * will be stored, longer solutions will be removed when a shorter solution
     * is added.
     * 
     * @param solution to be added
     * @return true if this solution was added
     */
    protected boolean addSolution(final byte[] solution) {
        if (this.solutionSize > solution.length) {
            this.solutionSize = solution.length;
            this.solutions.clear();
        }
        if (this.solutionSize == solution.length) {
            this.solutions.add(new Solution(solution, this.getSolverName()));
            return true;
        }
        return false;
    }
}
