package server;

import database.MyDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class ServerWork {

    private BufferedReader in;
    private PrintWriter out;
    private MyDatabase database;

    public ServerWork (BufferedReader in, PrintWriter out, MyDatabase database){
        this.in = in;
        this.out = out;
        this.database = database;
    }

    public void getId (int idOperation) throws IOException, SQLException {
        switch(idOperation){
            case 1:
                //
                break;
            case 2:
                //
                break;
            default:
                break;
        }
    }
}
