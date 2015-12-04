/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku.server;

/**
 *
 * @author Alex
 */
public class NewClass {
     public String requestHandler(String request) {
        request = input.readLine();
        String[] reqsplit = request.split(" ");
        String command = reqsplit[0];         
                
        switch (command) {
            case "login":
                player = new Player(reqsplit[1]);
                players.add(player);
                
                // kirim message ke client
                System.out.println(user.getName() + " connected.\nPlayers in the game: " + users.size());

                
                break;
            case "create_room":
                String roomName = reqsplit[1];

                boolean found = false;
                for (Room room : rooms) {
                    if (room.getName().equals(roomName)) 
                        found = true;
                }

                if (!found) {
                    Room room = new Room(roomName);
                    
                    rooms.add(room);
                    
                    //kirim ke client create room berhasil
                    
                } else {
                    //kirim ke client create room gagal
                    
                }

                break;
            case "join_room":
                String roomToJoin = reqsplit[1];
                
                for (Room room : rooms) {
                    if (room.getName().equals(roomToJoin)) {
                        room.addPlayer(player);
                        
                        //kirim ke client berhasil masuk terus masuk ke room
                        break;
                    }
                }
                break;
            case "start_room":
                String roomToStart = reqsplit[1];
                for (Room room : rooms) {
                    if (room.getName().equals(roomToStart)) {
                        if (room.getStatus().equals("READY")) {
                            room.setStatus("PLAYING");
                            // kirim ke client bisa main
                        } else {
                            // kirim ke client belom bisa main
                        }

                        break;
                    }
                }
                break;
            case "exit_room":
                String roomToExit = reqsplit[1];
                
                for (Room room : rooms) {
                    if (room.getName().equals(roomToExit)) {
                        room.removePlayer(player);

                        //kirim ke client berhasil keluar
                        break;
                    }
                }
                break;
            case "move":
                roomName = reqsplit[1];
                int row = Integer.parseInt(reqsplit[2]);
                int col = Integer.parseInt(reqsplit[3]);

                
                for (Room room : rooms) {
                    if (room.getName().equals(roomName)) {
                        if (room.getStatus().equals("PLAYING") {
                            if (player.getTurnId() == room.getTurn()) {
                                // Masukkin lambang ke board, update board ke smua client
                                                             
                                } else {
                                // Keluarin message bukan giliran dia
                                }
                        } else {
                            // Keluarin message belom start game
                        }
                        break;
                    }
                }
                break;
            case "chat":
                String roomToChat = reqsplit[1];
                String message = reqsplit[2];

               
                for (Room room : rooms) {
                    if (room.getName().equals(roomToChat)) {
                        room.addChat(new Chat(player, message));

                    }
                }
                // update chat ke smua client
                break;
            default:
                
                break;
        }
    }
}
