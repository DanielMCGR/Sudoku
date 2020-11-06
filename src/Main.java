import java.util.*;

public class Main {
    static Scanner input;
    static int[] board;
    static boolean[] isDefault;

    public static void main(String[] args) {
        input = new Scanner(System.in);
        // the sudoku board has 9x9 (81), being counted from top left to bottom right
        board = new int[81];
        isDefault = new boolean[81]; //
        boolean start = false;
        boolean win = false;

        //This is for when the game starts
        System.out.println("Welcome to the sudoku game. A default board will be used");
        System.out.println("You can type 'help' at any given time to get instructions");
        System.out.println("You can type 'start' to begin the game");
        while (!start) { //commands before the beginning of the game
            System.out.println("Please input a command:");
            String first_input =input.nextLine().toLowerCase();
            switch(first_input) {
                case "help":
                    help();
                    break;
                case "start": start=true;
                    break;
                case "example": printExampleBoard();
                    break;
                default: {System.out.println("That command is not recognized, here is a list of commands:");
                    System.out.println("Help; Start; Example"); }
                    break;
            }
        }

        //The default board is generated
        board = GetBoard("040805200020040050500000004090003120106078003370904080000006700008359010019007600");
        SetDefault();
        //System.out.print(Arrays.toString(isDefault)); //To make sure the command is working properly
        System.out.println(" ");

        while (!win) { //The game has started here, using the default board
            System.out.println(" ");
            System.out.println("Current State of the board:");
            printBoard();
            System.out.println(" ");
            System.out.println("Select the position to change");
            int pos =input.nextInt();
            if(pos<0||pos>80) {
                System.out.println("The selected position does not exist");
            } else if (isDefault[pos]==true){
                System.out.println("The selected position cannot be change as it is a starter one, please try again");
            }else {
                System.out.println("Select the number for that position (If there was a number there, it will be changed)");
                int num =input.nextInt();
                board[pos]=num;
            }
            if(BoardIsComplete()) {
                win=true;
                System.out.println("Congratulations. You Won!");
                System.out.println("Final Board: "+Arrays.toString(board));
            }
        }
    }

    public static boolean BoardIsComplete(){
        boolean isComplete=true;
        for (int i = 0; i < 9; i++){
            if(!RowIsComplete(i)){
                isComplete=false;
            }
            if(!ColumnIsComplete(i)){
                isComplete=false;
            }
        }
        return isComplete;
    }

    public static boolean RowIsComplete(int num){
        int soma=0;
        for (int i = 0; i < 9; i++){
            soma=board[i+(num*9)]+soma;
        }
        if(soma==45){ //adding all numbers from 1 to 9 = 45
            return true;
        } else {
            return false;
        }
    }
    public static boolean ColumnIsComplete(int num){
        int soma=0;
        for (int i = 0; i < 9; i++){
            soma=board[i*9+(num)]+soma;
        }
        if(soma==45){ //adding all numbers from 1 to 9 = 45
            return true;
        } else {
            return false;
        }
    }

    //Simple mechanism to input a board, by typing all numbers from top left to bottom right in order (if there is no number, it's set as 0 (which can't be used in sudoku )
    //since this isn't a fast or reliable method, it will just be used for testing
    //example: 040805200020040050500000004090003120106078003370904080000006700008359010019007600
    //Final Board: [9, 4, 7, 8, 3, 5, 2, 6, 1, 6, 2, 3, 7, 4, 1, 8, 5, 9, 5, 8, 1, 6, 9, 2, 3, 7, 4, 8, 9, 4, 5, 6, 3, 1, 2, 7, 1, 5, 6, 2, 7, 8, 9, 4, 3, 3, 7, 2, 9, 1, 4, 5, 8, 6, 4, 3, 5, 1, 2, 6, 7, 9, 8, 7, 6, 8, 3, 5, 9, 4, 1, 2, 2, 1, 9, 4, 8, 7, 6, 3, 5]
    public static int[] GetBoard(String conv) {
        int[] num = new int[conv.length()];
        for (int i = 0; i < conv.length(); i++){
            num[i] = conv.charAt(i) - '0';
        }
        return num;
    }

    //the array isDefault stores whether or not the number in a position was one of the "starting" ones, that cant be changed
    static void SetDefault() {
        for (int i = 0; i < 81; i++){
            if(board[i]==0){
                isDefault[i]=false;
            }
            if(board[i]!=0){
                isDefault[i]=true;
            }
        }
    }

    //prints the board according to the board array
    static void printBoard() {
        System.out.println("┌---┬---┬---┬---┬---┬---┬---┬---┬---┐");
        System.out.println("| " + board[0] + " | " + board[1] + " | " + board[2] + " | " + board[3] + " | " + board[4] + " | " + board[5] + " | " + board[6] + " | " + board[7] + " | " + board[8] + " |");
        System.out.println("|---┼---┼---┼---┼---┼---┼---┼---┼---|");
        System.out.println("| " + board[9] + " | " + board[10] + " | " + board[11] + " | " + board[12] + " | " + board[13] + " | " + board[14] + " | " + board[15] + " | " + board[16] + " | " + board[17] + " |");
        System.out.println("|---┼---┼---┼---┼---┼---┼---┼---┼---|");
        System.out.println("| " + board[18] + " | " + board[19] + " | " + board[20] + " | " + board[21] + " | " + board[22] + " | " + board[23] + " | " + board[24] + " | " + board[25] + " | " + board[26] + " |");
        System.out.println("|---┼---┼---┼---┼---┼---┼---┼---┼---|");
        System.out.println("| " + board[27] + " | " + board[28] + " | " + board[29] + " | " + board[30] + " | " + board[31] + " | " + board[32] + " | " + board[33] + " | " + board[34] + " | " + board[35] + " |");
        System.out.println("|---┼---┼---┼---┼---┼---┼---┼---┼---|");
        System.out.println("| " + board[36] + " | " + board[37] + " | " + board[38] + " | " + board[39] + " | " + board[40] + " | " + board[41] + " | " + board[42] + " | " + board[43] + " | " + board[44] + " |");
        System.out.println("|---┼---┼---┼---┼---┼---┼---┼---┼---|");
        System.out.println("| " + board[45] + " | " + board[46] + " | " + board[47] + " | " + board[48] + " | " + board[49] + " | " + board[50] + " | " + board[51] + " | " + board[52] + " | " + board[53] + " |");
        System.out.println("|---┼---┼---┼---┼---┼---┼---┼---┼---|");
        System.out.println("| " + board[54] + " | " + board[55] + " | " + board[56] + " | " + board[57] + " | " + board[58] + " | " + board[59] + " | " + board[60] + " | " + board[61] + " | " + board[62] + " |");
        System.out.println("|---┼---┼---┼---┼---┼---┼---┼---┼---|");
        System.out.println("| " + board[63] + " | " + board[64] + " | " + board[65] + " | " + board[66] + " | " + board[67] + " | " + board[68] + " | " + board[69] + " | " + board[70] + " | " + board[71] + " |");
        System.out.println("|---┼---┼---┼---┼---┼---┼---┼---┼---|");
        System.out.println("| " + board[72] + " | " + board[73] + " | " + board[74] + " | " + board[75] + " | " + board[76] + " | " + board[77] + " | " + board[78] + " | " + board[79] + " | " + board[80] + " |");
        System.out.println("└---┴---┴---┴---┴---┴---┴---┴---┴---┘");
    }

    //just an example board useful for understanding where each array position is
    static void printExampleBoard() {
        System.out.println("┌----┬----┬----┬----┬----┬----┬----┬----┬----┐");
        System.out.println("|  0 |  1 |  2 |  3 |  4 |  5 |  6 |  7 |  8 |");
        System.out.println("|----┼----┼----┼----┼----┼----┼----┼----┼----|");
        System.out.println("|  9 | 10 | 11 | 12 | 13 | 14 | 15 | 16 | 17 |");
        System.out.println("|----┼----┼----┼----┼----┼----┼----┼----┼----|");
        System.out.println("| 18 | 19 | 20 | 21 | 22 | 23 | 24 | 25 | 26 |");
        System.out.println("|----┼----┼----┼----┼----┼----┼----┼----┼----|");
        System.out.println("| 27 | 28 | 29 | 30 | 31 | 32 | 33 | 34 | 35 |");
        System.out.println("|----┼----┼----┼----┼----┼----┼----┼----┼----|");
        System.out.println("| 36 | 37 | 38 | 39 | 40 | 41 | 42 | 43 | 44 |");
        System.out.println("|----┼----┼----┼----┼----┼----┼----┼----┼----|");
        System.out.println("| 45 | 46 | 47 | 48 | 49 | 50 | 51 | 52 | 53 |");
        System.out.println("|----┼----┼----┼----┼----┼----┼----┼----┼----|");
        System.out.println("| 54 | 55 | 56 | 57 | 58 | 59 | 60 | 61 | 62 |");
        System.out.println("|----┼----┼----┼----┼----┼----┼----┼----┼----|");
        System.out.println("| 63 | 64 | 65 | 66 | 67 | 68 | 69 | 70 | 71 |");
        System.out.println("|----┼----┼----┼----┼----┼----┼----┼----┼----|");
        System.out.println("| 72 | 73 | 74 | 75 | 76 | 77 | 78 | 79 | 80 |");
        System.out.println("└----┴----┴----┴----┴----┴----┴----┴----┴----┘");
        }
    static void help() {
        System.out.println("The help command is under construction");
    }
}
