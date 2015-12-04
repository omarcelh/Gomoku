/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;

import java.io.*;
/*
 * This class defines the different type of messages that will be exchanged between the
 * Clients and the Server. 
 * When talking from a Java Client to a Java Server a lot easier to pass Java objects, no 
 * need to count bytes or to wait for a line feed at the end of the frame
 */
public class MessageToClient implements Serializable {

	static final int    WINNER = 0, 
                            NOWINNER = 1, 
                            MOVE = 2, 
                            MOVEINVALID = 3, 
                            NOTYOURTURN = 4, 
                            LOGGEDIN = 5, 
                            ROOMPLAYING = 6, 
                            ROOMNOTPLAYING = 7,
                            ROOMLIST = 8,
                            CONNECTED = 9;
	private int type;
	private int row;
        private int col;
        private int userid;      
        private int roomid;
        private String message;
        private char symbol;
	
	MessageToClient(int type, int row, int col, int userid, int roomid, String message, char symbol) {
		this.type = type;
		this.row = row;
                this.col = col;
                this.userid = userid;
                this.roomid= roomid;
                this.message = message;
                this.symbol = symbol;
	}
	
	public int getType() {
		return type;
	}
        
        public int getRow() {
		return row;
	}
        
        public int getCol() {
		return col;
	}
        
        public int getUserid() {
		return userid;
	}
        
        public int getRoomid() {
		return roomid;
	}
        
        public char getSymbol() {
		return symbol;
	}
        
        public String getMessage() {
            return message;
        }
        

}
