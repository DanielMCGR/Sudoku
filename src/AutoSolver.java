import java.util.*;

public class AutoSolver {

    boolean OneSolution=false;
    static int[] one_nine = new int[]{1,2,3,4,5,6,7,8,9};

    public static void main(String[] args) {

        /*
        In Sudoku the same number cannot repeat itself twice in a row, column, or inside each of the 9 smaller boxes (3x3 boxes).
        Having this in account i will try to make a function that auto solves it, and try various methods to doing it.

        The first idea is, for each position there is, to find what possible values could reside in it.

        //Testing for the CornerOfBlock Function

        Random r = new Random();
        int random = r.nextInt(81); //between 0 and 80
        System.out.println(random);
        System.out.println(CornerOfBlock(random));

         */
        //Getting the default board using the main class
        Main.board=new int[81];
        Main.board = Main.GetBoard("040805200020040050500000004090003120106078003370904080000006700008359010019007600");
        Main.isDefault = Main.SetDefault();
        //System.out.print(Arrays.toString(isDefault)); //To make sure the command is working properly
        Main.printBoard();
        System.out.println(" ");

    }

    public static int PossibleNumber(int pos) {
        int[] possible = one_nine;
        int Col = BaseCol(pos);
        int Row = BaseRow(pos);
        int Corner = CornerOfBlock(pos);
        //check the column
        for (int i = 0; i < 9; i++){
            one_nine = RemoveFromArray(one_nine, Main.board[Col+(i*9)]);
        }
        //check the row
        for (int i = 0; i < 9; i++){
            one_nine = RemoveFromArray(one_nine, Main.board[Row+(i*9)]);
        }
        //and last, check the block (starting from its top left)
        for (int i = 0; i < 3; i++){    //row
            for (int j = 0; j < 3; j++){        //column

                one_nine = RemoveFromArray(one_nine, Main.board[(j*3)+Corner+i]);
            }
        }

        return 0;

    }
    //might delete this function later or merge into CornerOfBlock, since the use is limited
    public static int GetBlock(int pos) {
        int Block = 0;
        int Col = 0;
        int Row = 0;
        //getting the number of the column
        while(pos>9) {
            pos-=9;
            Col++;
        }
        //getting the number of the row
        while(pos>0) {
            pos--;
            Row++;
        }
       //both column and row can be divided by 3, thus instead of 9 columns and rows, we have 3 possibilities
       Col /= 3;
       Row /= 3;
       //since row 1, column 1 is in reality block 4 (using the top left to bottom right idea), below we have the block number
       Block = Row+(Col*3);
       return Block;
    }

    //gets the position of the top left corner of said block, making further calculations easier
    public static int CornerOfBlock(int pos) {
        int Corner = 0;
        int Block = GetBlock(pos);
        Corner = (Block%3)*3;    //Block/3 gives us the row of the block. row number*27 is the position. To this we have to add the column (3*base column)
        Corner += (Block/3)*27;
        return Corner;
    }

    public static int BaseRow(int pos) {
        while(pos>=9) {
            pos-=9;
        }
        return pos;
    }
    public static int BaseCol(int pos) {
        pos = BaseRow(pos);
        while(pos>0) {
            pos-=1;
        }
        return pos;
    }

    public static int[] RemoveFromArray(int[] arr, int key)
    {
        int index = 0;
        for (int i=0; i<arr.length; i++)
            if (arr[i] != key)
                arr[index++] = arr[i];

        return Arrays.copyOf(arr, index);
    }

}
