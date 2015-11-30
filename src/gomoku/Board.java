/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;

/**
 *
 * @author acel
 */
public final class Board {

    /**
     * table of char
     */
    public char[][] tile;

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
     * @param i row index
     * @param j column index
     * @return
     */
    public char getTile(int i, int j) {
        return tile[i][j];
    }

    /**
     * tile setter at [i,j]
     * @param i row index
     * @param j column index
     * @param symbol
     */
    public void setTile(int i, int j, char symbol) {
        tile[i][j] = symbol;
    }

    /**
     * Constructor
     */
    public Board() {
        tile = new char[size][size];

        //initialize board
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                setTile(i, j, '-');
            }
        }
    }

    /**
     * Print all tiles on the board
     * Create size row and size column of char
     */
    public void show() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(getTile(i, j) + " ");
            }
            System.out.println();
        }
    }

    /**
     *
     * @param symbol char to be checked in board
     * @return true if board has 5 or more consecutive symbol
     */
    public boolean wins(char symbol) {
        return (winsHorizontal(symbol) || winsVertical(symbol) || winsDiagonal(symbol));
    }

    /**
     *
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
     *
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
     *
     * @param symbol char to be checked in board
     * @return true if board has 5 or more consecutive symbol diagonally
     */
    public boolean winsDiagonal(char symbol) {
        boolean flag = false;
        int counter = 0;
        int i, j;
        //northeast
        //[0][0] to [19][19]
        i = 0;
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
        i = 0;
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
