/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Gomoku class, class which uses Player and Board to create Gomoku Free Style 
 * game with more than three players (multiplayers board game)
 * @author acel
 */
public class Gomoku {

    static final int WAITING = 0, READY = 1, PLAYING = 2, FINISHED = 3;
    private static int roomCount;
    
    /**
     * Array of players
     */
    private ArrayList<Player> players;

    /**
     * Gomoku board
     */
    private Board board;

    /**
     * Number of players in game
     */
    private int numPlayer;
    
    private int roomid;
    private int status = WAITING;
    private int currentTurn;
    private String roomname;
    
    /**
     * Players getter
     * @return list of players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }
    
    public int getStatus() {
        return status;
    }
    
    public int getRoomid() {
        return roomid;
    }
    
    /**
     * Player setter at an index
     * @param index
     * @return player at index
     */
    public Player getPlayer(int index) {
        return players.get(index);
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
    
    public String getRoomname() {
        return roomname;
    }
    
    /**
     * Constructor with parameter
     * @param numPlayer
     */
    public Gomoku(String gamename){
        roomname = gamename;
        board = new Board();
        players = new ArrayList<>();
        roomid = ++roomCount;
    }
    
    /**
     * Adding player to list of players by checking previous players first
     * @param nickname
     * @param symbol
     * @param index
     * @return true if player is unique, false otherwise
     */
    /*public boolean addPlayer(String nickname, char symbol, int index){
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
    }*/
    
    public int addPlayer(Player player) {
        boolean flag = true;
        if(players.size() == 0){
            players.add(player);
        } else {
            int i=0;
            while(flag && (i<players.size())){
                flag = flag 
                        && !(players.get(i).getNickname().equals(player.getNickname())) 
                        && !(players.get(i).getSymbol() == player.getSymbol());
                i++;
            }
            if(flag){
                players.add(player);
                flag = true;
            } 
        }
        if (players.size() >= 3) {
            status = READY;
        }
        return (players.size()-1);
    }
    
    /**
     * Adding move to board
     * @param row
     * @param col
     * @param playerid
     * @return true if move is valid, false otherwise
     */
    public MessageToClient insertMove(int row, int col, int playerid) {
        System.out.println(currentTurn);
        MessageToClient mt;
        Player p = getPlayer(playerid);
        if (status == PLAYING) {
            if (playerid == currentTurn) {
                if (row>=0 && row<getBoard().getSize() && col>=0 && col<getBoard().getSize() && getBoard().isTileEmpty(row, col)) {            
                    getBoard().setTile(row,col,p.getSymbol());
                    mt = new MessageToClient(MessageToClient.MOVE, row, col, playerid, 0, p.getNickname(), p.getSymbol());
                    if(currentTurn == (getPlayers().size())-1){
                        currentTurn = 0;
                    } else {
                        currentTurn++;
                    }
                } else {
                    mt = new MessageToClient(MessageToClient.MOVEINVALID, 0, 0, 0, 0, "", ' ');
                }
            } else {
                mt = new MessageToClient(MessageToClient.NOTYOURTURN, 0, 0, 0, 0, "", ' ');
            }
        } else {
            mt = new MessageToClient(MessageToClient.ROOMNOTPLAYING, 0, 0, 0, 0, "", ' ');
        }
        return mt;
    }
        
    public MessageToClient wins(int playerid) {
        MessageToClient mc;        
        Player p = getPlayer(playerid);
        if (board.wins(getPlayer(playerid).getSymbol())) {
            mc = new MessageToClient(MessageToClient.WINNER, 0, 0, playerid, 0, p.getNickname(), p.getSymbol());
            status = FINISHED;
        } else {
            mc = new MessageToClient(MessageToClient.NOWINNER, 0, 0, 0, 0, "", ' ');
        }
        return mc;
    }
    
    public void initGame() {
        if (status != PLAYING) {
            currentTurn = 0;
            status = PLAYING;
        }
    }
}
