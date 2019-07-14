package eu.vdmr.math.matrix;

import java.util.ArrayList;
import java.util.List;

public class LinearEquationSolution {

    private boolean solved = false;

    private boolean inconsistent = false;

    private List<Integer> inconsistenRows;

    private List<FixedSolution> solutions = new ArrayList<>();


    public void setInconsistent(boolean inconsistent) {
        this.inconsistent = inconsistent;
    }

    public boolean isInconsistent() {
        return inconsistent;
    }

    public List<Integer> getInconsistenRowsIndices() {
        return inconsistenRows;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public boolean isSolved() {
        return solved;
    }

    public List<FixedSolution> getSolutions() {
        return solutions;
    }

    public void setInconsistenRows(List<Integer> inConsistentRows) {
        this.inconsistenRows = inConsistentRows;
    }

    public void addSolution(FixedSolution solution) {
        solutions.add(solution);
    }

    /**
     * @return the free variables (not sorted, thus may be empty, but not null
     */
    public List<Integer> getFreeVariables() {
        List<Integer> result = new ArrayList<>();
        for (FixedSolution solution : solutions) {
            if (solution.getFreeVariables() != null) {
                List<Integer> partFrees = solution.getFreeVariables();
                for (Integer free : partFrees) {
                    if (! result.contains(free)) {
                        result.add(free);
                    }
                }
            }
        }
        return result;
    }


    public class FixedSolution {
        private int varNr = -1;
        private List<Integer> freeVariables;


        private List<Double> freeVariableValues;
        private double solution;

        public int getVarNr() {
            return varNr;
        }

        public void setVarNr(int varNr) {
            this.varNr = varNr;
        }

        public List<Integer> getFreeVariables() {
            return freeVariables;
        }
        public List<Double> getFreeVariableValues() {
            return freeVariableValues;
        }

        public double getSolution() {
            return solution;
        }

        public void setSolution(double solution) {
            this.solution = solution;
        }


        public void addFreeVariable(int i) {
            if (freeVariables == null) {
                freeVariables = new ArrayList<>();
            }
            freeVariables.add(i);
        }

        public void addFreeVariableValue(double value) {
            if (freeVariableValues == null) {
                freeVariableValues = new ArrayList<>();
            }
            freeVariableValues.add(value);
        }
    }
}
