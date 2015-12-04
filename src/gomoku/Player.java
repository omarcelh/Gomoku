package gomoku;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
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
/* public class Player {
    private String name;
    private int turnid;
    private static int totalUser = 0;

    public Player(String name) {
        this.turnid = totalUser++;
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public int getTurnId() {
        return turnid;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public static int getTotalUser() {
        return totalUser;
    }
    
    public static void setTotalUser(int totalUser) {
        Player.totalUser = totalUser;
    }
} */