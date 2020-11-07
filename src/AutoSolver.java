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
        boolean completed=false;
        int inc = 0;
        Main.board=new int[81];
        Main.board = Main.GetBoard("040805200020040050500000004090003120106078003370904080000006700008359010019007600");
        Main.isDefault = Main.SetDefault();
        //System.out.print(Arrays.toString(isDefault)); //To make sure the command is working properly
        System.out.println("This is the base board");
        Main.printBoard();
        System.out.println(" ");

        while(!completed) {
            inc++;

            for (int i = 0; i <= 80; i++){
                if(Main.isDefault[i]==true) { }
                else {
                    int solution = 0;
                    if(PossibleNumber(i).length==1) {
                        solution = PossibleNumber(i)[0];
                    }
                    System.out.println("This is the array: " + Arrays.toString(PossibleNumber(i)));
                    if(solution>0) {
                        Main.board[i]=solution;
                        System.out.println("Position: " + i + ", Changed To: " + solution);
                        Main.isDefault[i]=true;
                    }
                }
            }
            completed=Main.BoardIsComplete(Main.board);
        }
        System.out.println(" ");
        System.out.println("Completed after " + inc + " tries!");
        System.out.println(" ");
        System.out.println("This is the board after the code");
        Main.printBoard();
        System.out.println(" ");
        //this is just to check if it is the same as the manual solution
        //System.out.println("[9, 4, 7, 8, 3, 5, 2, 6, 1, 6, 2, 3, 7, 4, 1, 8, 5, 9, 5, 8, 1, 6, 9, 2, 3, 7, 4, 8, 9, 4, 5, 6, 3, 1, 2, 7, 1, 5, 6, 2, 7, 8, 9, 4, 3, 3, 7, 2, 9, 1, 4, 5, 8, 6, 4, 3, 5, 1, 2, 6, 7, 9, 8, 7, 6, 8, 3, 5, 9, 4, 1, 2, 2, 1, 9, 4, 8, 7, 6, 3, 5]".equals(Arrays.toString(Main.board)));
    }

    public static int[] PossibleNumber(int pos) {
        //int number = 0;           //changed from number to array to help testing
        int[] possible = one_nine;
        int Col = BaseCol(pos);
        int Row = BaseRow(pos);
        int Corner = CornerOfBlock(pos);
        //check the column
        for (int i = 0; i < 9; i++){
            possible = RemoveFromArray(possible, Main.board[Col+(i*9)]);
        }
        //check the row
        for (int i = 0; i < 9; i++){
            possible = RemoveFromArray(possible, Main.board[(Row*9)+i]);
        }
        //and last, check the block (starting from its top left)
        for (int i = 0; i < 3; i++){    //row
            for (int j = 0; j < 3; j++){        //column
                possible = RemoveFromArray(possible, Main.board[(j*9)+Corner+i]);
            }
        }
       // if(possible.length==1) {
       //     number = possible[0];
       // }
        return possible;

    }
    //might delete this function later or merge into CornerOfBlock, since the use is limited
    public static int GetBlock(int pos) {
        int Block = 0;
        int Col = 0;
        int Row = 0;
        //getting the number of the column
        while(pos>=9) {
            pos-=9;
            Row++;
        }
        //getting the number of the row
        while(pos>0) {
            pos--;
            Col++;
        }
       //both column and row can be divided by 3, thus instead of 9 columns and rows, we have 3 possibilities
       Col /= 3;
       Row /= 3;
       //since row 1, column 1 is in reality block 4 (using the top left to bottom right idea), below we have the block number
       Block = Col+(Row*3);
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

    public static int BaseCol(int pos) {
        while(pos>=9) {
            pos-=9;
        }
        return pos;
    }
    public static int BaseRow(int pos) {
        return pos/9;
    }

    public static int[] RemoveFromArray(int[] array, int key) {
        int z=0;
        int new_size=0;
        for(int i=0; i<array.length; i++) {
            if(array[i]!=key) {
                new_size++;
            }
        }
        int[] output = new int[new_size];
        for(int i=0; i<array.length; i++) {
            if (array[i] != key) {
                output[z]=array[i];
            } else {
                z--;
            }
            z++;
        }
        return output;
    }

}
