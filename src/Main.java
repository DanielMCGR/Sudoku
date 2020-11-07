import java.util.*;

public class Main {
    static Scanner input;
    static int[] board;
    static boolean[] isDefault;

    public static void main(String[] args) {
        input = new Scanner(System.in);
        // the sudoku board has 9x9 (81), being counted from top left to bottom right
        board = new int[81];
        // for each of the positions this stores a boolean telling us if it is one of the positions that was filled in the initial board
        isDefault = new boolean[81];
        // whether or not the game has started, if it has ended (the player has won), and if the input is valid in the first prompt
        boolean start = false;
        boolean win = false;
        boolean valid = false;
        boolean isAuto = false;

        //This is for when the game starts
        System.out.println("Welcome to the sudoku game. You can type help at any time to get help.");
        while(!valid){
            System.out.println();
            System.out.println("Would you like to use a default board or input one yourself? (type either default or input)");
            String first_input =input.nextLine().toLowerCase();
            switch (first_input) {
                case "help" -> help();
                case "default" -> {
                    board = GetBoard("040805200020040050500000004090003120106078003370904080000006700008359010019007600");
                    printBoard();
                    valid=true;

                    }
                case "input" -> {
                    while(!valid) {
                        System.out.println("Please type the numbers on the board (From top left to bottom right, if there is a blank space count it as a zero):");
                        String input_board =input.nextLine().toLowerCase();
                        if(ValidBoard(input_board)) {
                            board = GetBoard(input_board);
                            printBoard();
                            valid=true;
                        } else if(input_board.equals("leave")){
                            System.out.println("Leaving");
                            break;
                        } else {
                            System.out.println("That board is not valid, please try again or type 'leave' to leave");
                        }
                    }

                }
                default -> {
                    System.out.println("That command is not recognized, here is a list of commands you can input:");
                    System.out.println("Help; Default; Input");
                }
            }
        }

        System.out.println();
        System.out.println("You can type 'start' to begin the game or example to see an example board (with the position numbers)");
        while (!start) { //commands before the beginning of the game
            System.out.println("Please input a command:");
            String first_input =input.nextLine().toLowerCase();
            switch (first_input) {
                case "help" -> help();
                case "start" -> start = true;
                case "example" -> printExampleBoard();
                case "board" -> printBoard();
                case "autosolve1" -> {AutoSolver.main(); isAuto=true; start=true;}
                case "autosolve2" -> {SolveBacktracking.main(); isAuto=true; start=true;}
                default -> {
                    System.out.println("That command is not recognized, here is a list of commands:");
                    System.out.println("Help; Start; Example; Board; AutoSolve1; AutoSolve2");
                }
            }
        }

        //The booleans for the default positions are generated, the board has already been set by the user
        isDefault = SetDefault();
        //System.out.print(Arrays.toString(isDefault)); //To make sure the command is working properly
        System.out.println();

        while (!win) { //The game has started here, using the default board
            if(isAuto) {
                System.out.println("The board as been Auto Completed.");
                printBoard();
                break;
            }
            printBoard();
            System.out.println();
            System.out.println("Select the position to change");
            int pos =input.nextInt();
            if(pos<0||pos>80) {
                System.out.println("The selected position does not exist");
            } else if (isDefault[pos]){
                System.out.println("The selected position cannot be change as it is a starter one, please try again");
            }else {
                System.out.println("Select the value for position " + pos + " (The current value for it is " + board[pos] + " )");
                int num =input.nextInt();
                if(num<1||pos>9) {
                    System.out.println("The selected value is not in range (1 to 9)");
                } else {
                    board[pos]=num;
                    System.out.println("The value for position " + pos + " has been changed to " + num);
                }
            }
            if(BoardIsComplete(board)) {
                win=true;
                System.out.println("Congratulations. You Won!");
                //System.out.println("Final Board: "+Arrays.toString(board));
                printBoard();
            }
        }
        System.out.println();
        System.out.println("The game is now over. Type 'again' to play again, or anything else to leave");
        String first_input =input.nextLine().toLowerCase();
        if ("again".equals(first_input)) {
            main(null);
        } else {
            System.out.println("Goodbye");
        }

    }

    public static boolean ValidBoard(String board){
        return GetBoard(board).length == 81 && isInteger(board);
    }

    public static boolean BoardIsComplete(int[] board){
        boolean isComplete=true;
        for (int i = 0; i < 9; i++){
            if(!RowIsComplete(i, board)){
                isComplete=false;
            }
            if(!ColumnIsComplete(i, board)){
                isComplete=false;
            }
        }
        return isComplete;
    }

    public static boolean RowIsComplete(int num, int[] board){
        int soma=0;
        for (int i = 0; i < 9; i++){
            soma=board[i+(num*9)]+soma;
        }
        return soma==45; //adding all numbers from 1 to 9 = 45
    }
    public static boolean ColumnIsComplete(int num, int[] board){
        int soma=0;
        for (int i = 0; i < 9; i++){
            soma=board[i*9+(num)]+soma;
        }
        return soma==45; //adding all numbers from 1 to 9 = 45
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

    //used to make sure the input string can be converted to an int and to avoid errors, might delete later
    public static boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }

    //the array isDefault stores whether or not the number in a position was one of the "starting" ones, that cant be changed
    //made slight changes to this so it would work easily in other classes
    public static boolean[] SetDefault() {
        boolean[] list = new boolean[81];
        for (int i = 0; i < 81; i++){
            if(board[i]==0){
                list[i]=false;
            }
            if(board[i]!=0){
                list[i]=true;
            }
        }
        return list;
    }

    //prints the board according to the board array
    static void printBoard() {
        System.out.println("This is the current state of the board:");
        System.out.println();
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
        System.out.println("This is the example board:");
        System.out.println();
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
