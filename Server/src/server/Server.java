package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {
    static final int PORT = 8020;

    public static void main(String[] args) throws IOException,
            ClassNotFoundException, SQLException {
        ServerSocket servSocket = new ServerSocket(PORT);
        System.out.println("Сервер запущен");

        try {
            while (true) {
                Socket socket = servSocket.accept();
                System.out.println("Был подключен новый клиент");
                try {
                    new ThreadServer(socket);
                } catch (IOException e) {
                    // Если завершится неудачей, закрывается сокет,
                    // в противном случае, нить закроет его:
                    socket.close();
                }
            }
        } finally {
            servSocket.close();
        }
    }
}
