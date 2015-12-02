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
            
            while(!addPlayer(player_name, player_symbol, i)){
                System.out.println("Wrong input!");
                System.out.println("Player-" + i);
                System.out.print("Name: ");
                player_name = scanner.next();
                System.out.print("Symbol: ");
                player_symbol = scanner.next().charAt(0);
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
        boolean flag = false;
        if(index == 0){
            players[index] = new Player(nickname, symbol);
            flag = true;
        } else {
            int i=0;
            while(!flag && (i<index)){
                flag = flag 
                        || (players[i].getNickname().equals(nickname)) 
                        || (players[i].getSymbol() == symbol);
                i++;
            }
            if(!flag){
                players[i] = new Player(nickname, symbol);
                flag = true;
            } 
        }
        return flag;
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
        
        int i =0;
        int winnerIndex = -1;
        while(i<numPlayer){
            System.out.println("****************************************************************\n");
            System.out.println(gomoku.getPlayer(i).getNickname() + " turn!");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            if(row>=0 && row<gomoku.getBoard().getSize() && col>=0 && col<gomoku.getBoard().getSize() && gomoku.getBoard().isTileEmpty(row, col)){
                gomoku.getBoard().setTile(row,col,gomoku.getPlayer(i).getSymbol());
                gomoku.getBoard().show();
            } else {
                while(!(row>=0 && row<gomoku.getBoard().getSize() && col>=0 && col<gomoku.getBoard().getSize() && gomoku.getBoard().isTileEmpty(row, col))){
                    System.out.println("Wrong input!");
                    row = scanner.nextInt();
                    col = scanner.nextInt();
                }              
                gomoku.getBoard().setTile(row,col,gomoku.getPlayer(i).getSymbol());
                gomoku.getBoard().show();
            }
            if(gomoku.getBoard().wins(gomoku.getPlayer(i).getSymbol())){
                winnerIndex = i;
                break;
            }
            if(i == (gomoku.getPlayers().length-1)){
                i = 0;
            } else {
                i++;
            }
        }
        System.out.println(gomoku.getPlayer(winnerIndex).getNickname() + " wins!");
    }
    
}
