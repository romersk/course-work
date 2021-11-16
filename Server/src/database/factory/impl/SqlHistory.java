package database.factory.impl;

import database.MyDatabase;
import database.factory.IHistory;
import model.History;

import java.sql.SQLException;
import java.util.ArrayList;

public class SqlHistory implements IHistory {

    private static SqlHistory instance;
    private MyDatabase dbConnection;

    private SqlHistory() throws SQLException, ClassNotFoundException {
        dbConnection = MyDatabase.getInstance();
    }

    public static synchronized SqlHistory getInstance()
            throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new SqlHistory();
        }
        return instance;
    }

    @Override
    public void insert(History obj) {

    }

    @Override
    public IHistory selectHistory(History obj) {
        return null;
    }

    @Override
    public void delete(History obj) {

    }

    @Override
    public ArrayList<History> findAll() {
        return null;
    }
}
