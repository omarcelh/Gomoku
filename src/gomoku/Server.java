/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {    
    private int port;
    private boolean keepAlive;
    private ArrayList<ClientThread> clients;
    private ArrayList<Player> players;
    private ArrayList<Gomoku> games;
    private static int clientCount;

    public Server(int port) {
        this.port = port;
        clients = new ArrayList<>();
        players = new ArrayList<>();
        games = new ArrayList<>();
    }
	
    public void start() {
        keepAlive = true;
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (keepAlive) {
                display("Server waiting for Clients on port " + port + ".");			
                Socket socket = serverSocket.accept();
                if (!keepAlive) 
                    break;
                ClientThread t = new ClientThread(socket);
                clients.add(t);
                t.start();
            }

            try {
                serverSocket.close();
                
                for(int i = 0; i < clients.size(); ++i) {
                    ClientThread tc = clients.get(i);
                    
                    try {
                        tc.inputStream.close();
                        tc.outputStream.close();
                        tc.socket.close();
                    } catch(IOException ioE) {

                    }                    
                }
            } catch(Exception e) {
                display("Exception closing the server and clients: " + e);
            }
        } catch (IOException e) {
            display(" Exception on new ServerSocket: " + e + "\n");
        }
    }		
    
    protected void stop() {
        keepAlive = false;
        try {
            new Socket("localhost", port);
        } catch(Exception e) {

        }
    }

    private void display(String msg) {
        System.out.println(msg);
    }

    private synchronized void broadcast(MessageToClient message) {
        // System.out.print(message + "\n");

        for (int i = clients.size(); --i >= 0;) {
            ClientThread ct = clients.get(i);

            if (!ct.writeMsg(message)) {
                clients.remove(i);
                display("Disconnected Client " + ct.username + " removed from list.");
            }
        }
    }

    synchronized void remove(int id) {
        for (int i = 0; i < clients.size(); ++i) {
            ClientThread ct = clients.get(i);
            if (ct.id == id) {
                clients.remove(i);
                return;
            }
        }
    }
	
    public static void main(String[] args) {
        Server server = new Server(4848);
        server.start();
    }
    
    public int addRoom(int playerid, String gamename) {        
        Gomoku game = new Gomoku(gamename);        
        game.addPlayer(players.get(playerid));      
        games.add(game);
        
        ClientThread ct = clients.get(playerid);
        MessageToClient msg = new MessageToClient(MessageToClient.LOGGEDIN, 0, 0, playerid, games.size()-1, gamename, players.get(playerid).getSymbol());
        display(players.get(playerid).getNickname() + " created room " + gamename);
        ct.writeMsg(msg);
                
        return games.size()-1;
    } 
    
    public int joinRoom(int playerid, String roomname) {
        int roomid = -1;
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).getRoomname().equals(roomname)) {
                roomid = i;
            }
        }
        Gomoku game = games.get(roomid);
        int gameuid = game.addPlayer(players.get(playerid));
        
        ClientThread ct = clients.get(playerid);
        MessageToClient msg = new MessageToClient(MessageToClient.LOGGEDIN, 0, 0, gameuid, roomid, roomname, players.get(playerid).getSymbol());
        display(players.get(playerid).getNickname() + " joined room " + roomname);
        ct.writeMsg(msg);
        
        return roomid;
    }
    
    public void startGame(String roomname) {
        int roomid = -1;
        for (int i = 0; i < games.size(); i++) {
            display(games.get(i).getRoomname());
            if (games.get(i).getRoomname().equals(roomname)) {
                roomid = i;
            }
        }
        Gomoku game = games.get(roomid);
        game.initGame();
        
        MessageToClient msg = new MessageToClient(MessageToClient.ROOMPLAYING, 0, 0, 0 , roomid, roomname, ' ');            
        broadcast(msg);
    }
    
    public void sendRoomList() {
        String RoomList = "";
        for (int i = 0; i < games.size(); i++) {
            RoomList += games.get(i).getRoomname() + ";";
        }             
        
        MessageToClient msg = new MessageToClient(MessageToClient.ROOMLIST, 0, 0, 0, 0, RoomList, ' ');
            
        broadcast(msg);
    }
    
    class ClientThread extends Thread {
        Socket socket;
        ObjectInputStream inputStream;
        ObjectOutputStream outputStream;
        int id;
        String username;
        char symbol;
        MessageToServer cm;

        ClientThread(Socket socket) {
            id = ++clientCount;
            this.socket = socket;
            System.out.println("Thread trying to create Object Input/Output Streams");
            try {
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream  = new ObjectInputStream(socket.getInputStream());

                cm = (MessageToServer) inputStream.readObject();
                username = cm.getMessage();
                players.add(new Player(cm.getMessage(), cm.getSymbol()));
                int userid = players.size()-1;
                writeMsg(new MessageToClient(MessageToClient.CONNECTED, 0, 0, userid, 0, "", ' '));
                display(username + " just connected.");
            } catch (IOException e) {
                display("Exception creating new Input/output Streams: " + e);
                return;
            } catch (ClassNotFoundException e) {

            }
        }

        public void run() {
            boolean keepAlive = true;

            while(keepAlive) {
                try {
                    cm = (MessageToServer) inputStream.readObject();
                } catch (IOException e) {
                    display(username + " Exception reading Streams: " + e);
                    break;				
                } catch(ClassNotFoundException e2) {
                    break;
                }

                switch(cm.getType()) {
                    case MessageToServer.GETROOMLIST:
                        sendRoomList();
                        break;
                    case MessageToServer.CREATEROOM:
                    {
                        addRoom(cm.getUserid(),cm.getMessage());
                        break;
                    }
                    case MessageToServer.JOINROOM:
                    {
                        joinRoom(cm.getUserid(),cm.getMessage());
                        break;
                    }
                    case MessageToServer.STARTROOM:
                    {
                        startGame(cm.getMessage());
                        break;
                    }
                    case MessageToServer.MOVE:
                    {                         
                        MessageToClient mc = games.get(cm.getRoomid()).insertMove(cm.getRow(), cm.getCol(), cm.getUserid());
                        if (mc.getType() == MessageToClient.MOVE) {
                            broadcast(mc);
                            MessageToClient isWin = games.get(cm.getRoomid()).wins(cm.getUserid());
                            if (isWin.getType() == MessageToClient.WINNER) {
                                broadcast(isWin);
                            }
                        } else {
                            writeMsg(mc);
                        }
                    }
                    break;
                }
            }

            remove(id);
            close();
        }

        private void close() {
            try {
                if(outputStream != null) 
                    outputStream.close();
                if(inputStream != null) 
                    inputStream.close();
                if(socket != null) 
                    socket.close();
            } catch (Exception e) {

            }
        }

        boolean writeMsg(MessageToClient msg) {
            if (!socket.isConnected()) {
                close();
                return false;
            }

            try {
                outputStream.writeObject(msg);
            } catch(IOException e) {
                display("Error sending message to " + username);
                display(e.toString());
            }

            return true;
        }

        
    }
}
