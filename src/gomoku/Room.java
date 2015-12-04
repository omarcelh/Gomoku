package gomoku;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
 */
public class Room {
    private String name;
    private Gomoku game;
    private String status = "WAITING";
    private ArrayList<Player> players = new ArrayList<>();
    // private ArrayList<Chat> chats = new ArrayList<>();
    private int turn = 0;
    
    public Room(String name){
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public String getStatus() {
        return status;
    }
          
    public ArrayList<Player> getPlayers() {
        return players;
    }
    
    public Gomoku getGame() {
        return game;
    }
    
    public void setGame(Gomoku game) {
        this.game = game;
    }
        
    public void setName(String name) {
        this.name = name;
    }
        
    public void setStatus(String status) {
        this.status = status;
    }
        
    public void addPlayer(Player player) {
        players.add(player);
        if(players.size() >= 3 && this.status.equals("WAITING"))
            this.status = "READY";
    }
        
    public void removePlayer(Player player) {
        players.remove(player);
        if(players.size() < 3 && this.status.equals("READY")) 
            this.status = "WAITING";
    }
    
   /* public void getChatMessage() {
        String text = "";
        for(Chat chat : chats) 
            text += chat.getPlayer().getName() + " : " + chat.getMessage() + "\n";
    }
    
    public ArrayList<Chat> getChats() {
        return chats;
    }

    public void setChats(ArrayList<Chat> chats) {
        this.chats = chats;
    }
    
    public void addChat(Chat chat) {
        if(players.contains(chat.getPlayer())) {
            chats.add(chat);
        }
    }
    
    public void removeChat(Chat chat) {
        chats.remove(chat);
    }*/
    
    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
    
    public void nextTurn() {
        this.turn = (turn + 1) % players.size();
    }
    
    public Player getCurrentPlayer() {
        return players.get(turn);
    }
    
}
