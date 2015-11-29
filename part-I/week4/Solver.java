import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


/**
 * Class that is able to find the fastest solution for a given {@link Board}.
 *
 */
public class Solver {

    private List<Board> solutionBoards = new ArrayList<>();
    private boolean solved;


    //find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {          
        MinPQ<SolverStep> steps = new MinPQ<>(new SolverStepComparator());
        steps.insert(new SolverStep(initial, 0, null));

        MinPQ<SolverStep> twin = new MinPQ<>(new SolverStepComparator());
        twin.insert(new SolverStep(initial.twin(), 0, null));

        SolverStep step;
        while (!steps.min().getBoard().isGoal() && 
                !twin.min().getBoard().isGoal()) {
            step = steps.delMin();
            for (Board n : step.getBoard().neighbors()) {
                if (!isAlreadyInSolutionPath(step, n)) {
                    steps.insert(new SolverStep(n, step.getMoves() + 1, step));
                }
            }

            SolverStep stepTwin = twin.delMin();
            for (Board n : stepTwin.getBoard().neighbors()) {
                if (!isAlreadyInSolutionPath(stepTwin, n)) {
                    twin.insert(new SolverStep(n, stepTwin.getMoves() + 1,
                                                stepTwin));
                }
            }
        }
        step = steps.delMin();
        solved = step.getBoard().isGoal();

        solutionBoards.add(step.getBoard());
        while ((step = step.getPreviousStep()) != null) {
            solutionBoards.add(0, step.getBoard());
        }
    }

    private boolean isAlreadyInSolutionPath(SolverStep lastStep, Board board) {
        SolverStep previousStep = lastStep;
        while ((previousStep = previousStep.getPreviousStep()) != null) {
            if (previousStep.getBoard().equals(board)) {
                return true;
            }
        }
        return false;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solved;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        int moves;
        if (isSolvable()) {
            moves = solutionBoards.size() - 1;
        } else {
            moves = -1;
        }
        return moves;
    }

    //sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        Iterable<Board> iterable;
        if (isSolvable()) {
            iterable = new Iterable<Board>() {
                @Override
                public Iterator<Board> iterator() {
                    return new SolutionIterator();
                }
            };
        } else {
            iterable = null;
        }
        return iterable;
    }


    private static class SolverStep {

        private int moves;
        private Board board;
        private SolverStep previousStep;

        private SolverStep(Board board, int moves, SolverStep previousStep) {
            this.board = board;
            this.moves = moves;
            this.previousStep = previousStep;
        }

        public int getMoves() {
            return moves;
        }

        public int getPriority() {
            return board.manhattan() + moves;
        }

        public Board getBoard() {
            return board;
        }

        public SolverStep getPreviousStep() {
            return previousStep;
        }
    }

    private class SolutionIterator implements Iterator<Board> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < solutionBoards.size();
        }

        @Override
        public Board next() {
            return solutionBoards.get(index++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Can't remove a board.");
        }
    }

    private static class SolverStepComparator implements Comparator<SolverStep> {

        @Override
        public int compare(SolverStep step1, SolverStep step2) {
            return step1.getPriority() - step2.getPriority();
        }
    }

}
