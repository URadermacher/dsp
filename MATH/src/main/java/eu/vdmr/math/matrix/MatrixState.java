package eu.vdmr.math.matrix;

public enum MatrixState {

    NORMAL (0),
    ECHELON (1),
    REDUCED_ECHELOC (2);

    private int rank;

    MatrixState(int rank) {
        this.rank = rank;
    }

    public boolean isEqualTo(MatrixState other) {
        return this.rank <= other.rank;
    }

}
