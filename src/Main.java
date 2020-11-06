import java.util.Scanner;

public class Main {
    static Scanner input;
    static int[] board;

    public static void main(String[] args) {
        input = new Scanner(System.in);
        // the sudoku board has 9x9 (81), being counted from top left to bottom right
        board = new int[81];

        //This is for when the game starts
        /*System.out.println("Welcome to the sudoku game");
        System.out.println("You can type 'help' at any given time to get instructions");
        System.out.println("You can type 'start' to begin the game");*/

        //This is just to test out how a board looks like
        board = GetBoard("040805200020040050500000004090003120106078003370904080000006700008359010019007600");
        System.out.println("Example board:");
        printExampleBoard();
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("Test board:");
        printBoard();
        // String str= input.nextLine();//reads string

    }

    //Simple mechanism to input a board, by typing all numbers from top left to bottom right in order (no number is 0)
    //since this isn't a fast or reliable method, it will just be used for testing
    //example: 040805200020040050500000004090003120106078003370904080000006700008359010019007600
    public static int[] GetBoard(String conv) {
        int[] num = new int[conv.length()];
        for (int i = 0; i < conv.length(); i++){
            num[i] = conv.charAt(i) - '0';
        }
        return num;
    }

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
}
