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
    public char[][] tile;
    public final int size = 20;
    
    public char get(int i, int j){
        return tile[i][j];
    }
    
    public void set(int i, int j, char symbol){
        tile[i][j] = symbol;
    }
    
    public Board(){
        tile = new char[size][size];
        
        //initialize board
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                set(i,j,'-');
            }
        }
    }
    
    public void show(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                System.out.print(get(i,j) + " ");
            }
            System.out.println();
        }
    }
    
    public boolean wins(char symbol){
        return (winsHorizontal(symbol) || winsVertical(symbol) || winsDiagonal(symbol));
    }
    
    public boolean winsHorizontal(char symbol){
        boolean flag = false;
        int counter = 0;
        
        int i=0;
        int j=0;
        
        while(i<size && !flag){
            while(j<size && !flag){
                if(get(i,j) == symbol){
                    counter++;
                    if(counter >= 5){
                        flag = true;
                    }
                } else {
                    counter = 0;
                }
                j++;
            }
            j=0;
            counter = 0;
            i++;
        }
        return flag;
    }
    
    public boolean winsVertical(char symbol){
        boolean flag = false;
        int counter = 0;
        
        int j=0;
        int i=0;
        while(j<size && !flag){
            while(i<size && !flag){
                if(get(i,j) == symbol){
                    counter++;
                    if(counter >= 5){
                        flag = true;
                    }
                } else {
                    counter = 0;
                }
                i++;
            }
            i=0;
            counter = 0;
            j++;
        }
        return flag;
    }
    
    public boolean winsDiagonal(char symbol){
        boolean flag = false;
        int counter = 0;
        int i,j;
        //northwest
        //[0][0] to [19][19]
        i=0;
        j=0;
        while((i>=0 && i<size) && (j>=0 && j<size) && !flag){
            if(get(i,j) == symbol){
                counter++;
                if(counter >= 5){
                    flag = true;
                }
            } else {
                counter=0;
            }
            i--;
            j++;
            if(i<0 || i>=size || j<0 || j>=size){
                if(j>=size){
                    //half diagonal reached
                    j = i + 2;
                    i = size-1;
                } else {
                    i = Integer.min(size-1, j);
                    j = 0;
                }
                counter = 0;
            }
        }
        
        //southwest
        //[0][19] to [19][0]
        i=0;
        j=size-1;
        while((i>=0 && i<size) && (j>=0 && j<size)){
            if(get(i,j) == symbol){
                counter++;
                if(counter >= 5){
                    flag = true;
                }
            } else {
                counter=0;
            }
            i--;
            j--;
            if(i<0 || i>=size || j<0 || j>=size){
                i = Integer.min(i+size-j, size);
                j = j + i;
                counter = 0;
            }
        }
        return flag;
    }
}
