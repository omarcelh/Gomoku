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

    /**
     * Nickname to be used in game
     */
    public String nickname;

    /**
     *
     * @return
     */
    public String getNickname() {
        return nickname;
    }

    /**
     *
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     *
     * @return
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     *
     * @param symbol
     */
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    /**
     * Symbol the player has in playing gomoku
     */
    public char symbol;
    
    /**
     * Constructor
     */
    public Player(){
        nickname = "";
        symbol = '-';
    }
    
    /**
     * Constructor with parameter
     * @param nickname name used in game
     * @param symbol symbol used in game
     */
    public Player(String nickname, char symbol){
        this.nickname = nickname;
        this.symbol = symbol;
    }
}
