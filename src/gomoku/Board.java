/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;

/**
 * Class Board with 20 row and 20 column of char
 * @author acel
 */
public final class Board {

    /**
     * table of char
     */
    private char[][] tile;

    /**
     * char to determine whether tile is empty or not
     */
    private final char emptyMark = '-';
    
    /**
     * board size (size row * size column)
     */
    public final int size = 20;
    
    
    /**
     * size getter
     * @return board size
     */
    public int getSize() {
        return size;
    }

    /**
     * tile getter
     * @param row
     * @param col
     * @return
     */
    public char getTile(int row, int col) {
        return tile[row][col];
    }
    
    public char getEmptyMark() {
        return emptyMark;
    }
    
    /**
     * tile setter at [i,j]
     * @param row
     * @param col
     * @param symbol
     */
    public void setTile(int row, int col, char symbol) {
        tile[row][col] = symbol;
    }
    
    /**
     * To check whether tile is empty or not
     * @param row
     * @param col
     * @return true if tile with row=i and col=j is emptyMark or false if it is not
     */
    public boolean isTileEmpty(int row, int col){
        return (tile[row][col] == emptyMark);
    }
    
    /**
     * To check whether tile is full or not
     * @return true if all tiles are not emptyMark, and false if at least one of it is emptyMark
     */
    public boolean isTileFull(){
        boolean flag = true;
        int i=0;
        int j=0;
        while(i<size && flag){
            while(j<size && flag){
                flag = flag && (getTile(i,j) != emptyMark);
            }
        }
        return flag;
    }

    /**
     * Constructor
     */
    public Board() {
        tile = new char[size][size];

        //initialize board
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                setTile(i, j, emptyMark);
            }
        }
    }    
    
    /**
     * Print all tiles on the board
     * Create size row and size column of char
     */
    public void show() {
        System.out.printf("%2s", "");
        for(int j=0; j<size; j++){
            System.out.printf("%3s", j);
        }
        System.out.println(" (j)");
        
        for (int i = 0; i < size; i++) {
            System.out.printf("%2d", i);
            for (int j = 0; j < size; j++) {
                System.out.printf("%3s", getTile(i, j));
            }
            System.out.println();
        }
        
        System.out.println("(i)\n");
    }

    /**
     * Function to determine whether there are at least 5 consecutive symbol on the board 
     * @param symbol char to be checked in board
     * @return true if board has 5 or more consecutive symbol
     */
    public boolean wins(char symbol) {
        return (winsHorizontal(symbol) || winsVertical(symbol) || winsDiagonal(symbol));
    }

    /**
     * Function to determine whether there are at least 5 consecutive symbol on the board horizontally
     * @param symbol char to be checked in board
     * @return true if board has 5 or more consecutive symbol horizontally
     */
    public boolean winsHorizontal(char symbol) {
        boolean flag = false;
        int counter = 0;

        int i = 0;
        int j = 0;

        while (i < size && !flag) {
            while (j < size && !flag) {
                if (getTile(i, j) == symbol) {
                    counter++;
                    if (counter >= 5) {
                        flag = true;
                    }
                } else {
                    counter = 0;
                }
                j++;
            }
            j = 0;
            counter = 0;
            i++;
        }
        return flag;
    }

    /**
     * Function to determine whether there are at least 5 consecutive symbol on the board vertically
     * @param symbol char to be checked in board
     * @return true if board has 5 or more consecutive symbol vertically
     */
    public boolean winsVertical(char symbol) {
        boolean flag = false;
        int counter = 0;

        int j = 0;
        int i = 0;
        while (j < size && !flag) {
            while (i < size && !flag) {
                if (getTile(i, j) == symbol) {
                    counter++;
                    if (counter >= 5) {
                        flag = true;
                    }
                } else {
                    counter = 0;
                }
                i++;
            }
            i = 0;
            counter = 0;
            j++;
        }
        return flag;
    }

    /**
     * Function to determine whether there are at least 5 consecutive symbol on the board diagonally
     * @param symbol char to be checked in board
     * @return true if board has 5 or more consecutive symbol diagonally
     */
    public boolean winsDiagonal(char symbol) {
        boolean flag = false;
        int counter = 0;
        int i, j;
        //northeast
        //[0][0] to [19][19]
        i = 4;
        j = 0;
        while ((i >= 0 && i < size) && (j >= 0 && j < size) && !flag) {
            if (getTile(i, j) == symbol) {
                counter++;
                if (counter >= 5) {
                    flag = true;
                }
            } else {
                counter = 0;
            }
            i--;
            j++;
            if (i < 0 || i >= size || j < 0 || j >= size) {
                if (j >= size) {
                    //half diagonal reached
                    j = i + 2;
                    i = size - 1;
                } else {
                    i = Integer.min(size - 1, j);
                    j = 0;
                }
                counter = 0;
            }
        } //endwhile

        //northwest
        //[0][19] to [19][0]
        i = 4;
        j = size - 1;
        while ((i >= 0 && i < size) && (j >= 0 && j < size) && !flag) {
            if (getTile(i, j) == symbol) {
                counter++;
                if (counter >= 5) {
                    flag = true;
                }
            } else {
                counter = 0;
            }
            i--;
            j--;
            if (i < 0 || i >= size || j < 0 || j >= size) {
                if (j < 0) {
                    j = (size - 3) - i;
                    i = size - 1;
                } else {
                    i = Integer.min(i + size - j, size - 1);
                    j = j + i;
                    counter = 0;
                }
            }
        } //endwhile
        return flag;
    }
}
