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
    private Gomoku game;
    private static int clientCount;

    public Server(int port) {
        this.port = port;
        clients = new ArrayList<>();
        players = new ArrayList<>();
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
    
    public void startGame() {
        game = new Gomoku(clients.size());
        for (int i = 0; i < clients.size(); ++i) {
            game.addPlayer(players.get(i),i);
            ClientThread ct = clients.get(i);
            MessageToClient msg = new MessageToClient(MessageToClient.LOGGEDIN, 0, 0, i, players.get(i).getNickname(), players.get(i).getSymbol());
            display(players.get(i).getNickname() + " joined the game.");
            ct.writeMsg(msg);
        }
        game.initGame();
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
                    /* case MessageToServer.GETROOMLIST:
                        sendRoomList();
                    case MessageToServer.CREATEROOM:
                        writeMsg(new MessageToClient(MessageToClient.CREATEDROOM,createRoom(cm.getMessage()));
                    case MessageToServer.JOINROOM:
                     */    
                    case MessageToServer.STARTROOM:
                    {
                        startGame();
                        break;
                    }
                    case MessageToServer.MOVE:
                    {                         
                        MessageToClient mc = game.insertMove(cm.getParam1(), cm.getParam2(), cm.getParam3());
                        if (mc.getType() == MessageToClient.MOVE) {
                            broadcast(mc);
                            MessageToClient isWin = game.wins(cm.getParam3());
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

        private boolean writeMsg(MessageToClient msg) {
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
