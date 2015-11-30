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
public class Player {
    public String nickname;
    public char symbol;
    
    public Player(){
        nickname = "";
        symbol = '-';
    }
    
    public Player(String nickname, char symbol){
        this.nickname = nickname;
        this.symbol = symbol;
    }
}
