/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;

/**
 * Player class which consists of nickname and symbol that are used in game
 * @author acel
 */
public class Player {

    /**
     * Nickname to be used in game
     */
    public String nickname;

    /**
     * Symbol the player has in playing gomoku
     */
    public char symbol;
    
    /**
     * Nickname getter
     * @return nickname in String
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Nickname setter
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Symbol getter
     * @return
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Symbol setter
     * @param symbol
     */
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    
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
