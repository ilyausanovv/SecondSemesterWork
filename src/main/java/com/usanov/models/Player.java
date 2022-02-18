package com.usanov.models;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Player {

    private PrintWriter printWriter;
    private Scanner scanner;
    private Socket socket;

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean isWaiting() {
        return scanner.hasNext();
    }
}
