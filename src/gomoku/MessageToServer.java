/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;

import java.io.*;

public class MessageToServer implements Serializable {

    static final int LOGIN = 0, GETROOMLIST = 1, CREATEROOM = 2, JOINROOM = 3, STARTROOM = 4, MOVE = 5;
    private int type;
    private int row;
    private int col;
    private int userid;      
    private int roomid;
    private String message;
    private char symbol;

    MessageToServer(int type, int row, int col, int userid, int roomid, String message, char symbol) {
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
