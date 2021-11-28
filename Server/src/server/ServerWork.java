package server;

import database.MyDatabase;
import database.factory.IUser;
import database.factory.impl.SqlUser;
import model.User;

import java.io.*;
import java.sql.SQLException;

public class ServerWork {

    private ObjectInputStream in;
    private MyDatabase database;
    private ObjectOutputStream out;

    public ServerWork (ObjectInputStream in, MyDatabase database, ObjectOutputStream outputStream){
        this.in = in;
        this.database = database;
        this.out = outputStream;
    }

    public void getId (int idOperation) throws IOException, SQLException, ClassNotFoundException {
        switch(idOperation){
            case 1:
                signingIn();
                break;
            case 2:
                //
                break;
            default:
                break;
        }
    }

    private void signingIn() throws IOException, SQLException, ClassNotFoundException {
        String login = (String) in.readObject();
        String password = (String) in.readObject();

        IUser iUser = new SqlUser();
        User user = iUser.selectUser(login, password);

        out.writeObject(user);
    }
}
