package database;

import database.command.*;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyDatabase{
    private static MyDatabase instance;

    private String drivName = "com.mysql.jdbc.Driver";
    protected Connection connect;
    protected Statement statemnt;

    public MyDatabase()
            throws ClassNotFoundException, SQLException {
        Class.forName(this.drivName);
        String url = "jdbc:mysql://localhost:3306/kp_edgar";
        String username = "root";
        String password = "root";
        this.connect = DriverManager.getConnection(url, username, password);
        this.statemnt = this.connect.createStatement();
    }

    public static synchronized MyDatabase getInstance()
            throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new MyDatabase();
        }
        return instance;
    }

    public void insert(String query) {
        Command command = new InsertCommand(query, statemnt);
        command.execute();
    }

    public ResultSet select(String query) throws SQLException {
        Command command = new SelectCommand(query, statemnt);
        return command.execute().getResultSet();
    }

    public void delete(String query) {
        Command command = new DeleteCommand(query, statemnt);
        command.execute();
    }

    public void update(String query) {
        Command command = new UpdateCommand(query, statemnt);
        command.execute();
    }

    public void close() {
        try {
            connect.close();
            statemnt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Command.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
