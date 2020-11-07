import java.util.*;

public class SolveBacktracking {

    public static void main(String[] args) {
        //this is a copy of the code from AutoSolver
        Main.board=new int[81];
        Main.board = Main.GetBoard("040805200020040050500000004090003120106078003370904080000006700008359010019007600");
        Main.isDefault = Main.SetDefault();
        //System.out.print(Arrays.toString(isDefault)); //To make sure the command is working properly
        System.out.println("This is the base board");
        Main.printBoard();
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("This is the completed board");
        if (SolveBacktracking(Main.board))
        {
            Main.printBoard();
        }
        System.out.println(" ");
        //this is just to check if it is the same as the manual solution
        System.out.println("[9, 4, 7, 8, 3, 5, 2, 6, 1, 6, 2, 3, 7, 4, 1, 8, 5, 9, 5, 8, 1, 6, 9, 2, 3, 7, 4, 8, 9, 4, 5, 6, 3, 1, 2, 7, 1, 5, 6, 2, 7, 8, 9, 4, 3, 3, 7, 2, 9, 1, 4, 5, 8, 6, 4, 3, 5, 1, 2, 6, 7, 9, 8, 7, 6, 8, 3, 5, 9, 4, 1, 2, 2, 1, 9, 4, 8, 7, 6, 3, 5]".equals(Arrays.toString(Main.board)));
    }

    public static boolean SolveBacktracking(int[] board) {
        boolean isDone = true;
        int pos = -1;
        for (int i = 0; i < 81; i++) {
            if (Main.board[i]==0) {
                pos = i;
                isDone = false;
                break;
            }
        }
        if (isDone) {  return true;    }
            for (int Val = 1; Val <= 9; Val++)
            {
                if (IsPossible(pos, Val)) {
                    Main.board[pos] = Val;
                    if (SolveBacktracking(Main.board)) {
                        return true;
                    } else {
                        Main.board[pos] = 0;
                    }
                }
            }
        return false;
    }

    // System.out.println("Placeholder");

    //based on AutoSolver class PossibleNumber function
    public static boolean IsPossible(int pos, int Val) {
        boolean possible = true;
        int Col = AutoSolver.BaseCol(pos);
        int Row = AutoSolver.BaseRow(pos);
        int Corner = AutoSolver.CornerOfBlock(pos);
        //check the column
        for (int i = 0; i < 9; i++) {
            if (Val == Main.board[Col + (i * 9)]) {
                possible = false;
            }
        }
        //check the row
        for (int i = 0; i < 9; i++) {
            if (Val == Main.board[(Row * 9) + i]) {
                possible = false;
            }
        }
        //and last, check the block (starting from its top left)
        for (int i = 0; i < 3; i++) {    //row
            for (int j = 0; j < 3; j++) {        //column
                if (Val == Main.board[(j * 9) + Corner + i]) {
                    possible = false;
                }
            }
        }
        return possible;
    }
}
