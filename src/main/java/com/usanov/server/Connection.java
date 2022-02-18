package com.usanov.server;

import com.usanov.models.Player;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection {

    int port;
    Player player;

    public Connection(int port, Player player) {
        this.port = port;
        this.player = player;
    }

    public boolean isConnectedToServer() {

        try {
            Socket socket = new Socket("localhost", port);
            player.setScanner(new Scanner(socket.getInputStream()));
            player.setPrintWriter(new PrintWriter(socket.getOutputStream(), true));
            player.setSocket(socket);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}