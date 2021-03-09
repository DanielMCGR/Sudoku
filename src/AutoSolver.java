import java.util.*;

/**
 * In Sudoku the same number cannot repeat itself twice in a row,
 * column, or inside each of the 9 smaller boxes (3x3 boxes).
 * Having this in account i will try to make a class that
 * auto-solves it, and try various methods to doing it.
 * These are ways I would normally solve a Sudoku puzzle.
 * Read more about it in the solve() method.
 *
 * @author      Daniel Rocha
 * @version     1.0
 * @see #solve() 
 */

public class AutoSolver {

    boolean oneSolution=false;
    static final int[] ONE_NINE = new int[]{1,2,3,4,5,6,7,8,9};
    static final int[] CORNERS = new int[]{0,3,6,27,30,33,54,57,60};
    static int[][] possibilities = new int[81][];

    /**
     * The main function of this class, used to solve the active
     * board on Sudoku.main. While the board isn't complete,
     * the method will cycle trough other methods trying to complete
     * the board (finding positions where there's only one
     * possible value to filled). Several println are commented
     * in order to make the console cleaner and save some resources,
     * but can be uncommented for an easier understanding of
     * the solving process. Each different way is properly described
     * in it's own method, as best as possible.
     *
     * @see Sudoku
     */
    public static void solve() {
        boolean completed=false;
        int inc = 0;
        while(!completed) {
            inc++;
            //System.out.println("This is try number " + inc);
            if(inc>10) {break;} //enables forcing the solver the stop at 'inc' attempt

            //first, checks if the given position has only one possible value
            //based on it's row, column and block (by using the possibleNumber method)
            for (int i = 0; i < 81; i++) {
                if (Sudoku.board[i] != 0) {
                } else {
                    possibilities[i] = possibleNumber(i);
                    int solution = 0;
                    if (possibleNumber(i).length == 1) {
                        solution = possibleNumber(i)[0];
                    }
                    //System.out.println("For position " + i + " this the array: " + Arrays.toString(possibleNumber(i)));
                    if (solution > 0) {
                        Sudoku.board[i] = solution;
                        //System.out.println("Position: " + i + ", Changed To: " + solution);
                        possibilities[i]=null;
                        updatePos(i); } } }
            //Sudoku.printBoard();

            //checks for unique values in possibilities (using checkBlocks, checkCol and checkRow)
            for (int i = 0; i < 81; i++) {
                if (possibilities[i] == null || possibilities[i].length == 1 || possibilities[i].length == 0)
                    continue;
                int result = checkBlocks(i);
                if (result != 0) {
                    Sudoku.board[i] = result;
                    //System.out.println("Position: " + i + ", Changed To: " + result+" (CheckBlocks)");
                    possibilities[i]=null;
                    updatePos(i);
                    //Sudoku.printBoard();
                    continue;
                }
                result = checkCol(i);
                if (result != 0) {
                    Sudoku.board[i] = result;
                    //System.out.println("Position: " + i + ", Changed To: " + result+" (CheckCol)");
                    possibilities[i]=null;
                    updatePos(i);
                    //Sudoku.printBoard();
                    continue;
                }
                result = checkRow(i);
                if (result != 0) {
                    Sudoku.board[i] = result;
                    //System.out.println("Position: " + i + ", Changed To: " + result+" (CheckRow)");
                    possibilities[i]=null;
                    updatePos(i);
                    //Sudoku.printBoard();
                    continue;
                }
            }

            //checks for "unfilled lines" using the hasUnfilledLine method
            for(int i=0;i<CORNERS.length;i++) {
                int[][] lines = hasUnfilledLine(CORNERS[i]);
                //checks if there are multiple lines in the same block, and if there are, runs code for all of them
                if(lines==null||lines.length<1) continue;
                for(int l=0;l<lines.length;l++) {
                    int[] result = lines[l];
                    if (result.length != 4) continue;
                    int base_pos = result[0];
                    int val = result[1];
                    int count = result[3];
                    int col = columnNum(base_pos);
                    int row = rowNum(base_pos);
                    //System.out.println("There's an 'unfilled line' on Position: " + base_pos + ", Value: " + val + ", Size: " + count);
                    if (result[2] == 1) {
                        for (int j = 0; j < 9; j++) {
                            int pos = col + (9 * j);
                            if (count == 3)
                                if (pos == base_pos || pos == base_pos + 9 || pos == base_pos + 18 || possibilities[pos] == null || possibilities[pos].length == 0)
                                    continue;
                            if (count == 2)
                                if (pos == base_pos || pos == base_pos + 9 || possibilities[pos] == null || possibilities[pos].length == 0)
                                    continue;
                            possibilities[pos] = removeFromArray(possibilities[pos], val);
                            if (possibilities[pos].length == 1) {
                                Sudoku.board[pos] = possibilities[pos][0];
                                //System.out.println("Position: " + pos + ", Changed To: " + possibilities[pos][0]);
                                possibilities[pos] = null;
                                updatePos(i);
                            }
                            //System.out.println("Possibilities at " + pos + " are " + Arrays.toString(possibilities[pos]));
                        }
                    } else {
                        for (int j = 0; j < 9; j++) {
                            int pos = (row * 9) + j;
                            if (count == 3)
                                if (pos == base_pos || pos == base_pos + 1 || pos == base_pos + 2 || possibilities[pos] == null || possibilities[pos].length == 0)
                                    continue;
                            if (count == 2)
                                if (pos == base_pos || pos == base_pos + 1 || possibilities[pos] == null || possibilities[pos].length == 0)
                                    continue;
                            possibilities[pos] = removeFromArray(possibilities[pos], val);
                            if (possibilities[pos].length == 1) {
                                Sudoku.board[pos] = possibilities[pos][0];
                                //System.out.println("Position: " + pos + ", Changed To: " + possibilities[pos][0]);
                                possibilities[pos] = null;
                                updatePos(i);
                            }
                            //System.out.println("Possibilities at " + pos + " are " + Arrays.toString(possibilities[pos]));
                        }
                    }
                    updatePos(i);
                    //Sudoku.printBoard();
                }
            }
            //checks for hidden singles
            hiddenSingle();
            //nullifies the filled board positions
            for(int i=0;i<81;i++) {
                if (Sudoku.board[i]!=0) possibilities[i]=null;
            }
            completed= Sudoku.isBoardComplete(Sudoku.board); }
        System.out.println(" ");
        System.out.println("Auto Solve completed after " + inc + " tries!");
        System.out.println(" ");
    }

    /**
     * checks what are the possible values for a certain position,
     * by checking what are the values of all other positions
     * in the same row, same column and same block, and removing
     * said values from an array with all possibilities (1 to 9),
     * leaving only the values that are possible
     *
     * @param pos the position to check
     * @return array of possible values
     */
    public static int[] possibleNumber(int pos) {
        int[] possible = ONE_NINE;
        int col = columnNum(pos);
        int row = rowNum(pos);
        int corner = cornerOfBlock(pos);
        //fills a column or row if there is one slot left
        for (int i = 0; i < 9; i++){
            possible = removeFromArray(possible, Sudoku.board[col+(i*9)]);
            possible = removeFromArray(possible, Sudoku.board[(row*9)+i]);
        }
        //fills a block if there is one slot left
        for (int i = 0; i < 3; i++){    //col
            for (int j = 0; j < 3; j++){        //row
                possible = removeFromArray(possible, Sudoku.board[(j*9)+corner+i]);
            }
        }
        if(possibilities[pos]!=null&&possible.length>possibilities[pos].length) possible = possibilities[pos];
        return possible;
    }

    /**
     * This method is meant to be used after a position
     * was filled, to make sure the possibilities array
     * is updated (using the possibleNumber method on all
     * the positions in the same column, row and block)
     *
     * @param pos the position that was changed
     * @see #possibleNumber(int)
     */
    public static void updatePos(int pos) {
        int col_base = columnNum(pos);
        int row_base = rowNum(pos)*9;
        int corner = cornerOfBlock(pos);
        for(int i=0;i<9;i++) {
            if(col_base+(i*9)==pos||Sudoku.board[col_base+(i*9)]!=0) continue;
            possibilities[col_base+(i*9)] = possibleNumber(col_base+(i*9));
        }
        for(int i=0;i<9;i++) {
            if(row_base+i==pos||Sudoku.board[row_base+i]!=0) continue;
            possibilities[row_base+i] = possibleNumber(row_base+i);
        }
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                if((i*9)+corner+j==pos||Sudoku.board[(i*9)+corner+j]!=0) continue;
                possibilities[(i*9)+corner+j] = possibleNumber((i*9)+corner+j);
            }
        }
    }

    /**
     * Compares the possible values for the given position
     * with the possible values for every other position
     * in the same block. If one of the possible values
     * is unique, it means it can't be filled anywhere else
     * in the block, thus making it the correct value
     * for the given position.
     *
     * @param pos the position to be checked
     * @return 0 if there is no unique value, or the value if it is unique
     */
    public static int checkBlocks(int pos) {
        int corner = cornerOfBlock(pos);
        boolean[] uniques = new boolean[possibilities[pos].length];
        for(int i=0;i<uniques.length;i++) uniques[i]=true;
        for(int i=0;i<possibilities[pos].length;i++) {
            second_loop:
            for(int j=0;j<3;j++) {
                for(int k=0;k<3;k++) {
                    if((k*9)+corner+j==pos) continue;
                    if(possibilities[(k*9)+corner+j]==null||possibilities[(k*9)+corner+j].length==0) continue;
                    for(int l=0;l<possibilities[(k*9)+corner+j].length;l++) {
                        if(possibilities[pos][i]==possibilities[(k*9)+corner+j][l]){
                            uniques[i]=false;
                            break second_loop;
                        } } } } }
        for(int i=0;i<uniques.length;i++) {
            if(uniques[i]==true) {
                return possibilities[pos][i];
            }
        }
        return 0;
    }

    /**
     * Compares the possible values for the given position
     * with the possible values for every other position
     * in the same row. If one of the possible values
     * is unique, it means it can't be filled anywhere else
     * in the row, thus making it the correct value
     * for the given position.
     *
     * @param pos the position to be checked
     * @return 0 if there is no unique value, or the value if it is unique
     */
    public static int checkRow(int pos) {
        int row_base = rowNum(pos)*9;
        boolean[] uniques = new boolean[possibilities[pos].length];
        for(int i=0;i<uniques.length;i++) uniques[i]=true;
        for(int i=0;i<possibilities[pos].length;i++) {
            second_loop:
            for(int j=0;j<9;j++) {
                if(row_base+j==pos) continue;
                if(possibilities[row_base+j]==null||possibilities[row_base+j].length==0) continue;
                for(int l=0;l<possibilities[row_base+j].length;l++) {
                    if(possibilities[pos][i]==possibilities[row_base+j][l]){
                        uniques[i]=false;
                        break second_loop;
                    } } } }
        for(int i=0;i<uniques.length;i++) {
            if(uniques[i]==true) {
                return possibilities[pos][i];
            }
        }
        return 0;
    }

    /**
     * Compares the possible values for the given position
     * with the possible values for every other position
     * in the same column. If one of the possible values
     * is unique, it means it can't be filled anywhere else
     * in the column, thus making it the correct value
     * for the given position.
     *
     * @param pos the position to be checked
     * @return 0 if there is no unique value, or the value if it is unique
     */
    public static int checkCol(int pos) {
        int col_base = columnNum(pos);
        boolean[] uniques = new boolean[possibilities[pos].length];
        for(int i=0;i<uniques.length;i++) uniques[i]=true;
        for(int i=0;i<possibilities[pos].length;i++) {
            second_loop:
            for(int j=0;j<9;j++) {
                if((j*9)+col_base==pos) continue;
                if(possibilities[(j*9)+col_base]==null||possibilities[(j*9)+col_base].length==0) continue;
                for(int l=0;l<possibilities[(j*9)+col_base].length;l++) {
                    if(possibilities[pos][i]==possibilities[(j*9)+col_base][l]){
                        uniques[i]=false;
                        break second_loop;
                    } } } }
        for(int i=0;i<uniques.length;i++) {
            if(uniques[i]==true) {
                return possibilities[pos][i];
            }
        }
        return 0;
    }

    /**
     * In a Sudoku board, sometimes adjacent positions
     * have the same possible value(s), and no other positions
     * in the same block have said value(s). This creates
     * "unfilled lines" that can impact other blocks and positions
     * (example: in a block if 2 adjacent (horizontally or vertically)
     * positions can have the same value, and no other position can,
     * that means in that row/column it is mandatory that the value
     * must be filled in one of those 2 positions, thus empty
     * spaces on other positions in the same row/column can't have
     * that value filled, thus it can be removed from its' possibilities)
     *
     * @param corner the corner of the block to be checked
     * @return an array of the existing unfilled lines in the block (containing vital information for use in 'solve')
     */
    public static int[][] hasUnfilledLine(int corner) {
        int linePos = 0;
        int[][] lines = new int[9][];
        int[] values = new int[9];
        int[][] out = new int[0][];
        int z=0;
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                lines[z]=possibilities[(j*9)+corner+i];
                z++;
            }
        }
        for(int i=1;i<10;i++) {
            int count=0;
            for(int j=0;j<9;j++) {
                if(arrayContains(lines[j],i)) count++;
            }
            values[i-1]=count;
        }
        for(int i=0;i<9;i++) {
            if(values[i]>3||values[i]<2) continue;
            if(values[i]==3) {
                if(arrayContains(possibilities[corner], i+1)&&arrayContains(possibilities[corner+1], i+1)&&arrayContains(possibilities[corner+2], i+1)){
                    linePos=corner;
                    int[] output = new int[]{linePos, i+1, 0, 3};
                    out = addArrayToMatrix(out,output);
                }
                if(arrayContains(possibilities[corner+9], i+1)&&arrayContains(possibilities[corner+10], i+1)&&arrayContains(possibilities[corner+11], i+1)){
                    linePos=corner+9;
                    int[] output = new int[]{linePos, i+1, 0, 3};
                    out = addArrayToMatrix(out,output);
                }
                if(arrayContains(possibilities[corner+18], i+1)&&arrayContains(possibilities[corner+19], i+1)&&arrayContains(possibilities[corner+20], i+1)){
                    linePos=corner+18;
                    int[] output = new int[]{linePos, i+1, 0, 3};
                    out = addArrayToMatrix(out,output);
                }
                if(arrayContains(possibilities[corner], i+1)&&arrayContains(possibilities[corner+9], i+1)&&arrayContains(possibilities[corner+18], i+1)){
                    linePos=corner;
                    int[] output = new int[]{linePos, i+1, 1, 3};
                    out = addArrayToMatrix(out,output);
                }
                if(arrayContains(possibilities[corner+1], i+1)&&arrayContains(possibilities[corner+10], i+1)&&arrayContains(possibilities[corner+19], i+1)){
                    int[] output = new int[]{linePos, i+1, 1, 3};
                    out = addArrayToMatrix(out,output);
                }
                if(arrayContains(possibilities[corner+2], i+1)&&arrayContains(possibilities[corner+11], i+1)&&arrayContains(possibilities[corner+20], i+1)){
                    int[] output = new int[]{linePos, i+1, 1, 3};
                    out = addArrayToMatrix(out,output);
                }
            }
            if(values[i]==2) {
                if(arrayContains(possibilities[corner], i+1)&&arrayContains(possibilities[corner+1], i+1)){
                    linePos=corner;
                    int[] output = new int[]{linePos, i+1, 0, 2};
                    out = addArrayToMatrix(out,output);
                }
                if(arrayContains(possibilities[corner+1], i+1)&&arrayContains(possibilities[corner+2], i+1)){
                    linePos=corner+1;
                    int[] output = new int[]{linePos, i+1, 0, 2};
                    out = addArrayToMatrix(out,output);
                }
                if(arrayContains(possibilities[corner+9], i+1)&&arrayContains(possibilities[corner+10], i+1)){
                    linePos=corner+9;
                    int[] output = new int[]{linePos, i+1, 0, 2};
                    out = addArrayToMatrix(out,output);
                }
                if(arrayContains(possibilities[corner+10], i+1)&&arrayContains(possibilities[corner+11], i+1)){
                    linePos=corner+10;
                    int[] output = new int[]{linePos, i+1, 0, 2};
                    out = addArrayToMatrix(out,output);
                }
                if(arrayContains(possibilities[corner+18], i+1)&&arrayContains(possibilities[corner+19], i+1)){
                    linePos=corner+18;
                    int[] output = new int[]{linePos, i+1, 0, 2};
                    out = addArrayToMatrix(out,output);
                }
                if(arrayContains(possibilities[corner+19], i+1)&&arrayContains(possibilities[corner+20], i+1)){
                    linePos=corner+19;
                    int[] output = new int[]{linePos, i+1, 0, 2};
                    out = addArrayToMatrix(out,output);
                }
                if(arrayContains(possibilities[corner], i+1)&&arrayContains(possibilities[corner+9], i+1)){
                    linePos=corner;
                    int[] output = new int[]{linePos, i+1, 1, 2};
                    out = addArrayToMatrix(out,output);
                }
                if(arrayContains(possibilities[corner+9], i+1)&&arrayContains(possibilities[corner+18], i+1)){
                    linePos=corner+9;
                    int[] output = new int[]{linePos, i+1, 1, 2};
                    out = addArrayToMatrix(out,output);
                }
                if(arrayContains(possibilities[corner+1], i+1)&&arrayContains(possibilities[corner+10], i+1)){
                    linePos=corner+1;
                    int[] output = new int[]{linePos, i+1, 1, 2};
                    out = addArrayToMatrix(out,output);
                }
                if(arrayContains(possibilities[corner+10], i+1)&&arrayContains(possibilities[corner+19], i+1)){
                    linePos=corner+10;
                    int[] output = new int[]{linePos, i+1, 1, 2};
                    out = addArrayToMatrix(out,output);
                }
                if(arrayContains(possibilities[corner+2], i+1)&&arrayContains(possibilities[corner+11], i+1)){
                    linePos=corner+2;
                    int[] output = new int[]{linePos, i+1, 1, 2};
                    out = addArrayToMatrix(out,output);
                }
                if(arrayContains(possibilities[corner+11], i+1)&&arrayContains(possibilities[corner+20], i+1)){
                    linePos=corner+11;
                    int[] output = new int[]{linePos, i+1, 1, 2};
                    out = addArrayToMatrix(out,output);
                }
            }
        }
        if(out.length==0) return null;
        return out;
    }

    /**
     * Checks for a very specific condition that might be needed
     * to clear harder puzzles (ones with much less pre-filled spaces).
     * The method checks all rows and columns of the board, and
     * if one of them has 2 slots left, checks if both are in the same block.
     * If there are only two slots left in a row/column and both are in
     * the same block, that means that those two slots will have either one
     * of two values, and every other slot in the block can't be filled
     * with both of those values. This allows for easier solving in some cases.
     */
    public static void hiddenSingle() {
        int[] rows = new int[9];
        int[] cols = new int[9];
        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                if(Sudoku.board[(i*9)+j]==0) rows[i]++; //rows
                if(Sudoku.board[(j*9)+i]==0) cols[i]++; //cols
            } }
        for(int i=0;i<9;i++) {
            if(rows[i]==2) {
                int first=0;
                int second=0;
                for(int j=0;j<9;j++) {
                    if(Sudoku.board[(i*9)+j]==0) {
                        if(first==0) first = (i*9)+j;
                        else second = (i*9)+j;
                    } }
                if(cornerOfBlock(first)==cornerOfBlock(second)&&possibilities[first].length==2) {
                    int corner = cornerOfBlock(first);
                    int first_value=possibilities[first][0];
                    int second_value=possibilities[first][1];
                    for (int k = 0; k < 3; k++){    //col
                        for (int l = 0; l < 3; l++){        //row
                            if((l*9)+corner+k==first||(l*9)+corner+k==second||Sudoku.board[(l*9)+corner+k]!=0) continue;
                            possibilities[(l*9)+corner+k] = removeFromArray(possibilities[(l*9)+corner+k], first_value);
                            possibilities[(l*9)+corner+k] = removeFromArray(possibilities[(l*9)+corner+k], second_value);
                        } }
                    updatePos(first);
                } }
            if(cols[i]==2) {
                int first=0;
                int second=0;
                for(int j=0;j<9;j++) {
                    if(Sudoku.board[(j*9)+i]==0) {
                        if(first==0) first = (j*9)+i;
                        else second = (j*9)+i;
                    } }
                if(cornerOfBlock(first)==cornerOfBlock(second)&&possibilities[first].length==2) {
                    int corner = cornerOfBlock(first);
                    int first_value=possibilities[first][0];
                    int second_value=possibilities[first][1];
                    for (int k = 0; k < 3; k++){    //col
                        for (int l = 0; l < 3; l++){        //row
                            if((l*9)+corner+k==first||(l*9)+corner+k==second||Sudoku.board[(l*9)+corner+k]!=0) continue;
                            possibilities[(l*9)+corner+k] = removeFromArray(possibilities[(l*9)+corner+k], first_value);
                            possibilities[(l*9)+corner+k] = removeFromArray(possibilities[(l*9)+corner+k], second_value);
                        } }
                    updatePos(first);
                } } }
    }

    /**
     * gets the position of the top left corner of the block
     * in which the entered position is
     *
     * @param pos the position to get the block
     * @return the top left corner position for the block
     */
    public static int cornerOfBlock(int pos) {
        int col = columnNum(pos);
        int row = rowNum(pos);
        //both column and row can be divided by 3, thus instead of 9 columns and rows, we have 3 possibilities (since there are 9 blocks, equals to the 3 columns multiplied by the 3 blocks)
        col /= 3;
        row /= 3;
        //since row 1, column 1 is in reality block 4 (using the top left to bottom right idea), below we have the block number
        int block = (row*3)+col;
        int corner = 0;
        corner = (block%3)*3;    //Block/3 gives us the row of the block. row number*27 is the position. To this we have to add the column (3*base column)
        corner += (block/3)*27;
        return corner;
    }

    /**
     * Gets the column number of the given position
     *
     * @param pos the position to check
     * @return the column number
     */
    public static int columnNum(int pos) {
        while(pos>=9) {
            pos-=9;
        }
        return pos;
    }

    /**
     * Gets the row number of the given position
     *
     * @param pos the position to check
     * @return the row number
     */
    public static int rowNum(int pos) {
        return pos/9;
    }

    /**
     * Removes a chosen int from an int array
     *
     * @param array the array to remove an int from
     * @param key the value to remove
     * @return the array with the removed value
     * @see #addToArray(int[], int)
     */
    public static int[] removeFromArray(int[] array, int key) {
        if(array==null) return null;
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

    /**
     * Adds a chosen int to an int array
     *
     * @param array the array to add an int to
     * @param key the value to add
     * @return the array with the added value
     * @see #removeFromArray(int[], int)
     */
    public static int[] addToArray(int[] array, int key) {
        if(array==null) {
            return new int[]{key};
        }
        int[] new_array = new int[array.length+1];
        for(int i=0; i<array.length; i++) {
            new_array[i]=array[i];
        }
        new_array[array.length]=key;
        return new_array;
    }

    /**
     * Inserts an array into a matrix
     *
     * @param matrix the array to add an array into
     * @param array the array to be added
     * @return the array with the added array
     * @see #addToArray(int[], int)
     */
    public static int[][] addArrayToMatrix(int[][] matrix, int[]array) {
        if(array==null) return matrix;
        if(matrix==null||matrix.length==0) {
            return new int[][]{array};
        }
        int[][] new_matrix = new int[matrix.length+1][];
        for(int i=0; i<matrix.length; i++) {
            new_matrix[i]=matrix[i];
        }
        new_matrix[matrix.length]=array;
        return new_matrix;
    }

    /**
     * Checks if an array contains a given value
     *
     * @param array the array to add an int to
     * @param key the value to check
     * @return whether or not the array contains the key
     */
    public static boolean arrayContains(int[] array, int key) {
        if(array==null) return false;
        for(int i=0; i<array.length; i++) {
            if(array[i]==key) return true;
        }
        return false;
    }
}
