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

	protected static final long serialVersionUID = 1112122200L;

	static final int WINNER = 0, NOWINNER = 1, MOVE = 2, MOVEINVALID = 3, NOTYOURTURN = 4, LOGGEDIN = 5;
	private int type;
	private int param1;
        private int param2;
        private int param3;      
        private String message;
        private char symbol;
	
	MessageToClient(int type, int param1, int param2, int param3, String message, char symbol) {
		this.type = type;
		this.param1 = param1;
                this.param2 = param2;
                this.param3 = param3;
                this.message = message;
                this.symbol = symbol;
	}
	
	public int getType() {
		return type;
	}
        
        public int getParam1() {
		return param1;
	}
        
        public int getParam2() {
		return param2;
	}
        
        public int getParam3() {
		return param3;
	}
        
        public char getSymbol() {
		return symbol;
	}
        
        public String getMessage() {
            return message;
        }
        

}
