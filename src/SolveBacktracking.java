/**
 * This class uses a much better and simpler
 * method for solving a Sudoku puzzle, via
 * backtracking method.
 *
 * @author      Daniel Rocha
 * @version     1.0
 * @see Sudoku
 */

public class SolveBacktracking {

    /**
     * Solves a Sudoku board using Backtracking
     *
     * @param board the board to be solved
     * @return true if the board is completed, and false if it is not
     */
    public static boolean solveBacktracking(int[] board) {
        boolean isDone = true;
        int pos = -1;
        for (int i = 0; i < 81; i++) {
            if (Sudoku.board[i]==0) {
                pos = i;
                isDone = false;
                break;
            }
        }
        if (isDone) return true;
        for (int Val = 1; Val <= 9; Val++) {
            if (isPossible(pos, Val)) {
                Sudoku.board[pos] = Val;
                if (solveBacktracking(Sudoku.board)) {
                    return true;
                } else {
                    Sudoku.board[pos] = 0;
                }
            }
        }
        return false;
    }

    /**
     * Checks if it is possible to fill a given value
     * onto the selected position.
     * <p>
     * (Based on AutoSolver.possibleNumber)
     *
     * @param pos the position to be checked
     * @param val the value to be checked
     * @return whether or not the value is valid
     */
    //based on AutoSolver class PossibleNumber function
    public static boolean isPossible(int pos, int val) {
        boolean possible = true;
        int col = AutoSolver.columnNum(pos);
        int row = AutoSolver.rowNum(pos);
        int corner = AutoSolver.cornerOfBlock(pos);
        //check the column
        for (int i = 0; i < 9; i++) {
            if (val == Sudoku.board[col + (i * 9)]) {
                possible = false; } }
        //check the row
        for (int i = 0; i < 9; i++) {
            if (val == Sudoku.board[(row * 9) + i]) {
                possible = false; } }
        //and last, check the block (starting from its top left)
        for (int i = 0; i < 3; i++) {    //row
            for (int j = 0; j < 3; j++) {        //column
                if (val == Sudoku.board[(j * 9) + corner + i]) {
                    possible = false; } } }
        return possible;
    }
}
