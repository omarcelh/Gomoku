/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;

import java.util.Scanner;

/**
 * Gomoku class, class which uses Player and Board to create Gomoku Free Style 
 * game with more than three players (multiplayers board game)
 * @author acel
 */
public class Gomoku {

    /**
     * Array of players
     */
    private Player[] players;

    /**
     * Gomoku board
     */
    private Board board;

    /**
     * Number of players in game
     */
    private int numPlayer;
    
    /**
     * Players getter
     * @return list of players
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Player setter at an index
     * @param index
     * @return player at index
     */
    public Player getPlayer(int index) {
        return players[index];
    }

    /**
     * Board getter
     * @return
     */
    public Board getBoard() {
        return board;
    }

    /**
     * NumPlayer getter
     * @return
     */
    public int getNumPlayer() {
        return numPlayer;
    }

    /**
     * NumPlayer setter
     * @param numPlayer
     */
    public void setNumPlayer(int numPlayer) {
        this.numPlayer = numPlayer;
    }
    
    /**
     * Constructor with parameter
     * @param numPlayer
     */
    public Gomoku(int numPlayer){
        this.numPlayer = numPlayer;
        board = new Board();
        players = new Player[this.numPlayer];
    }
    
    /**
     * Procedure to add players based on numPlayer
     */
    public void addPlayers(){
        Scanner scanner = new Scanner(System.in);
        for(int i=0; i<getNumPlayer(); i++){
            System.out.println("Player-" + i);
            System.out.print("Name: ");
            String player_name = scanner.next();
            System.out.print("Symbol: ");
            char player_symbol = scanner.next().charAt(0);
            boolean flag = addPlayer(player_name, player_symbol, i);
            while(!flag){
                System.out.println("Wrong input!");
                System.out.println("Player-" + i);
                System.out.print("Name: ");
                player_name = scanner.next();
                System.out.print("Symbol: ");
                player_symbol = scanner.next().charAt(0);
                flag = addPlayer(player_name, player_symbol, i);
            }   
        }
        System.out.println();
    }
    
    /**
     * Adding player to list of players by checking previous players first
     * @param nickname
     * @param symbol
     * @param index
     * @return true if player is unique, false otherwise
     */
    public boolean addPlayer(String nickname, char symbol, int index){
        boolean flag = true;
        if(index == 0){
            players[index] = new Player(nickname, symbol);
        } else {
            int i=0;
            while(flag && (i<index)){
                flag = flag 
                        && !(players[i].getNickname().equals(nickname)) 
                        && !(players[i].getSymbol() == symbol);
                i++;
            }
            if(flag){
                players[i] = new Player(nickname, symbol);
                flag = true;
            } 
        }
        return flag;
    }
    
    public int play(){
        int i = 0;
        int winnerIndex = -1;
        Scanner scanner = new Scanner(System.in);
        while(i<numPlayer){
            System.out.println("****************************************************************\n");
            System.out.println(getPlayer(i).getNickname() + " turn!");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            if(row>=0 && row<getBoard().getSize() && col>=0 && col<getBoard().getSize() && getBoard().isTileEmpty(row, col)){
                getBoard().setTile(row,col,getPlayer(i).getSymbol());
                getBoard().show();
            } else {
                while(!(row>=0 && row<getBoard().getSize() && col>=0 && col<getBoard().getSize() && getBoard().isTileEmpty(row, col))){
                    System.out.println("Wrong input!");
                    row = scanner.nextInt();
                    col = scanner.nextInt();
                }              
                getBoard().setTile(row,col,getPlayer(i).getSymbol());
                getBoard().show();
            }
            if(getBoard().wins(getPlayer(i).getSymbol())){
                winnerIndex = i;
                break;
            }
            if(i == (getPlayers().length-1)){
                i = 0;
            } else {
                i++;
            }
        }
        return winnerIndex;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //add players
        System.out.print("Number of player: ");
        Scanner scanner = new Scanner(System.in);
        int numPlayer = scanner.nextInt();
        while(numPlayer<2 || numPlayer>99){
            System.out.println("Wrong input");
            numPlayer = scanner.nextInt();
        }
        
        Gomoku gomoku = new Gomoku(numPlayer);
        gomoku.addPlayers();
        
        int winnerIndex = gomoku.play();
        
        System.out.println(gomoku.getPlayer(winnerIndex).getNickname() + " wins!");
    }
    
}
