package com.usanov.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Game {

    private final int PORT;
    public final InetAddress address;

    public Game(int PORT) throws Exception {
        this.PORT = PORT;
        address = InetAddress.getByName("localhost");
    }

    public void startGame() {

        try (ServerSocket socket = new ServerSocket(PORT, 50, address)) {
            Socket clientFirst = socket.accept();
            Socket clientSecond = socket.accept();
            new Thread(new MainThread(clientFirst, clientSecond, socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
