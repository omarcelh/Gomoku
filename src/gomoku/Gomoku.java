/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;

import java.util.Scanner;

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
        Player p_1 = new Player("Acel", 'Z');
        
        while(!board.wins(p_1.getSymbol())){
            Scanner in = new Scanner(System.in);
            int row = in.nextInt();
            int col = in.nextInt();
            if(row>=0 && row<board.getSize() && col>=0 && col<board.getSize()){
                board.setTile(row,col,p_1.getSymbol());
                board.show();
            } else {
                System.out.println("Input is wrong");
            }
        }
        System.out.println(p_1.getNickname() + " wins!");
    }
    
}
