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
public class Gomoku {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Board board = new Board();
        
        for(int k=0; k<5; k++){
            board.set(k, 0, 'A');
        }
        
        board.show();
        if(board.winsVertical('A')){
            System.out.println("AB");
        }
    }
    
}
