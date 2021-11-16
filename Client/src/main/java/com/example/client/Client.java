package com.example.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public Client(InetAddress ip, int port) {
        try {
            this.socket = new Socket(ip, port);
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLine(String message) throws IOException {
        writer.write(message);
        writer.newLine();
        writer.flush();
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }
}
