/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;

import java.net.*;
import java.io.*;

public class Client {
    private ClientGUI gui;
    
    private String server;
    private int port;
    private String username;
    private char symbol;
    private int playerid;
    
    private Socket socket;    
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    Client(String server, int port, String username, char symbol, ClientGUI gui) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.symbol = symbol;
        this.gui = gui;
    }
	
    public boolean start() {
        try {
            socket = new Socket(server, port);
        } catch (Exception ex) {
            display("Error connecting to server:" + ex);
            return false;
        }
		
	String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
	display(msg);
	
	try {
            inputStream  = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
	} catch (IOException ex) {
            display("Exception creating new Input/output Streams: " + ex);
            return false;
	}
 
	new ServerListener().start();
	
        try {
            MessageToServer mts = new MessageToServer(MessageToServer.LOGIN,0,0,0,username,symbol);
            outputStream.writeObject(mts);
	} catch (IOException eIO) {
            display("Exception doing login : " + eIO);
            disconnect();
            return false;
	}
        return true;
    }

    void move(int row, int col) {
        MessageToServer msg = new MessageToServer(MessageToServer.MOVE, row, col, playerid, username, symbol);
        sendMessage(msg);
    }
    
    private void display(String event) {
        System.out.println(event);
    }
    
    void startGame() {
        MessageToServer msg = new MessageToServer(MessageToServer.STARTROOM, 0, 0, 0, "", ' ');
        sendMessage(msg);
    }
    
    void sendMessage(MessageToServer msg) {
        try {
            outputStream.writeObject(msg);
        } catch (IOException e) {
            display("Exception writing to server: " + e);
        }
    }
        
    private void disconnect() {
        try { 
            if(inputStream != null) 
                inputStream.close();
            if(outputStream != null) 
                outputStream.close();
            if(socket != null) 
                socket.close();
        } catch (Exception e) {

        }
    }
        
    /* public static void main(String[] args) {
        int portNumber = 4848;
        String serverAddress = "localhost";
        String userName = "Anonymous";

        Client client = new Client(serverAddress, portNumber, userName, null);

        if (!client.start())
            return;

        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String msg = scan.nextLine();
            if(msg.equalsIgnoreCase("LOGOUT")) {
                client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
                break;
            } else if(msg.equalsIgnoreCase("WHOISIN")) {
                client.sendMessage(new ChatMessage(ChatMessage.WHOISIN, ""));				
            } else {
                client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg));
            }
        }
        client.disconnect();	
    } */
    
    char getSymbol() {
        return symbol;
    }
    
    class ServerListener extends Thread {

        public void run() {
            while (true) {
                try {
                    MessageToClient msg = (MessageToClient) inputStream.readObject();
                    process(msg);
                } catch (IOException e) {
                    display("Server has close the connection: " + e);
                    break;
                } catch (ClassNotFoundException e2) {

                }              
            }
        }
        
        private void process(MessageToClient msg) {
            switch(msg.getType()) {
                case MessageToClient.LOGGEDIN:
                {
                    playerid = msg.getParam3();
                    display("Logged in. Your player id is: " + playerid);
                    break;
                }                
                case MessageToClient.MOVE:
                {
                    gui.addMove(msg.getParam1(), msg.getParam2(), msg.getSymbol());
                    break;
                }
                case MessageToClient.MOVEINVALID:
                {                    
                    display("Move invalid!");
                    break;
                }
                case MessageToClient.NOTYOURTURN:
                {
                    display("Not your turn!");
                    break;
                }
                case MessageToClient.WINNER:
                {
                    display("Winner is: " + msg.getMessage());
                    break;
                }
            }
        }
    }
}