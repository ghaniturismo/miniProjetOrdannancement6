package algorithm;

import problem.Problem;

public abstract class BaseSolver implements Solver{

    protected Problem problem;

    public BaseSolver(Problem problem) {
        this.problem = problem;
    }
}
